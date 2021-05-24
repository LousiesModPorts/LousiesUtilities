package com.lousiesmods.lousiesutilities.api;

import com.lousiesmods.lousiesutilities.handler.LockHandler;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.Entity;

import java.util.UUID;

public interface ILockable
{
    AccessLevel getAccess();

    GameProfile getOwner();

    void setLevel(final AccessLevel p0);

    boolean setOwner(final GameProfile p0);

    default String getOwnerName()
    {
        return this.getOwner().getName();
    }

    default boolean canAccess(final Entity entity)
    {
        return this.getAccess().matches(this.getOwner(), entity);
    }

    default boolean isLockable()
    {
        return true;
    }

    default boolean hasLock()
    {
        return !LockHandler.isDefaultProfile(this.getOwner());
    }

    public enum AccessLevel
    {
        PUBLIC,
        PRIVATE,
        FRIENDS,
        TEAM;

        public static final AccessLevel[] VALUES;

        public boolean matches(final GameProfile owner, final Entity entity)
        {
            final UUID ownerID = owner.getId();

            if (LockHandler.isDefaultUUID(ownerID))
            {
                return true;
            }

            final UUID otherID = LockHandler.getID(entity);

            switch (this)
            {
                case PRIVATE:
                {
                    return ownerID.equals(otherID);
                }

                default:
                {
                    return true;
                }
            }
        }

        public boolean isPublic()
        {
            return this == AccessLevel.PUBLIC;
        }

        public boolean isPrivate()
        {
            return this == AccessLevel.PRIVATE;
        }

        static
        {
            VALUES = values();
        }
    }
}
