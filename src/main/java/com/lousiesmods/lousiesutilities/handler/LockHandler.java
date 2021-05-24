package com.lousiesmods.lousiesutilities.handler;

import com.google.common.base.Strings;

import com.lousiesmods.lousiesutilities.api.ILockable;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;

public class LockHandler
{
    public static final GameProfile DEFAULT_GAME_PROFILE;
    private static UUID cachedId;

    private LockHandler()
    {
    }

    public static boolean isDefaultUUID(final UUID uuid)
    {
        return uuid == null || (uuid.version() == 4 && uuid.variant() == 0);
    }

    public static boolean isDefaultProfile(final GameProfile profile)
    {
        return LockHandler.DEFAULT_GAME_PROFILE.equals((Object)profile);
    }

    public static UUID getID(final Entity entity)
    {
        if (entity == null)
        {
            return LockHandler.DEFAULT_GAME_PROFILE.getId();
        }
        if (!(entity instanceof PlayerEntity))
        {
            return entity.getUUID();
        }

        final PlayerEntity player = (PlayerEntity)entity;

        if (player instanceof ServerPlayerEntity)
        {
            return player.getGameProfile().getId();
        }
        return getClientID(player);
    }

    private static UUID getClientID(final PlayerEntity player)
    {
        if (player != Minecraft.getInstance().player)
        {
            return player.getGameProfile().getId();
        }

        if (LockHandler.cachedId == null)
        {
            LockHandler.cachedId = Minecraft.getInstance().player.getGameProfile().getId();
        }

        return LockHandler.cachedId;
    }

    public static boolean hasSecurity(final TileEntity tile)
    {
        return tile instanceof ILockable && !isDefaultProfile(((ILockable)tile).getOwner());
    }

    public static String getOwnerName(final TileEntity tile)
    {
        if (hasSecurity(tile))
        {
            return ((ILockable)tile).getOwnerName();
        }

        return LockHandler.DEFAULT_GAME_PROFILE.getName();
    }

    public static CompoundNBT getSecurityTag(final ItemStack stack)
    {
        final CompoundNBT nbt = stack.getTagElement("BlockEntityTag");

        if (nbt != null)
        {
            return nbt.contains("Lockable") ? nbt.getCompound("Lockable") : null;
        }

        return stack.getTagElement("Lockable");
    }

    public static boolean hasSecurity(final ItemStack stack)
    {
        return getSecurityTag(stack) != null;
    }

    public static void setAccess(final ItemStack stack, final ILockable.AccessLevel access)
    {
        final CompoundNBT secureTag = getSecurityTag(stack);

        if (secureTag != null)
        {
            secureTag.putByte("AccessLevel", (byte)access.ordinal());
        }
    }

    public static void setOwner(final ItemStack stack, final GameProfile profile)
    {
        final CompoundNBT secureTag = getSecurityTag(stack);

        if (secureTag != null)
        {
            secureTag.putString("OwnerUUID", profile.getId().toString());
            secureTag.putString("OwnerName", profile.getName());
        }
    }

    public static ILockable.AccessLevel getAccess(final ItemStack stack)
    {
        final CompoundNBT secureTag = getSecurityTag(stack);

        if (secureTag != null)
        {
            return ILockable.AccessLevel.VALUES[secureTag.getByte("AccessLevel")];
        }

        return ILockable.AccessLevel.PUBLIC;
    }

    public static GameProfile getOwner(final ItemStack stack)
    {
        final CompoundNBT secureTag = getSecurityTag(stack);

        if (secureTag != null)
        {
            final String uuid = secureTag.getString("OwwnerUUID");
            final String name = secureTag.getString("OwnerName");

            if (!Strings.isNullOrEmpty(uuid))
            {
                return new GameProfile(UUID.fromString(uuid), name);
            }

            if (!Strings.isNullOrEmpty(name))
            {
                return new GameProfile(PreYggdrasilConverter.convertMobOwnerIfNecessary(ServerLifecycleHooks.getCurrentServer(), name), name);
            }
        }

        return LockHandler.DEFAULT_GAME_PROFILE;
    }

    public static String getOwnerName(final ItemStack stack)
    {
        final CompoundNBT secureTag = getSecurityTag(stack);

        if (secureTag != null)
        {
            return secureTag.getString("OwnerName");
        }

        return LockHandler.localize("infher_player");
    }

    static
    {
        DEFAULT_GAME_PROFILE = new GameProfile(UUID.fromString("1ef1a6f0-87bc-4e78-0a0b-c6824eb787ea"), "[None]");
    }

    public static String localize(final String key)
    {
        return I18n.get(key, new Object[0]);
    }
}
