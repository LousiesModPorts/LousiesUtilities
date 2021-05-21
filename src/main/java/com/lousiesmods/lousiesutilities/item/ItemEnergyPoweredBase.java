package com.lousiesmods.lousiesutilities.item;

import com.lousiesmods.lousiesutilities.api.IEnergyContainer;
import com.lousiesmods.lousiesutilities.capability.EnergyItemCapability;
import com.lousiesmods.lousiesutilities.capability.EnergyStorageCapability;
import com.lousiesmods.lousiesutilities.handler.KeyHandler;
import com.lousiesmods.lousiesutilities.util.EnergyHelper;
import com.lousiesmods.lousiesutilities.util.StringUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

public class ItemEnergyPoweredBase extends ItemEnergyBase implements IEnergyContainer
{
    public ItemEnergyPoweredBase(Properties props)
    {
        super(props.defaultDurability(1));
    }

    @Override
    public int receiveEnergy(ItemStack container, int energy, boolean simulate)
    {
        return EnergyHelper.receiveEnergy(container, energy, simulate);
    }

    @Override
    public int extractEnergy(ItemStack container, int energy, boolean simulate)
    {
        return EnergyHelper.extractEnergy(container, energy, simulate);
    }

    @Override
    public int getEnergyStored(ItemStack container)
    {
        return EnergyHelper.getEnergyStored(container);
    }

    @Override
    public int getMaxEnergyStored(ItemStack container)
    {
        return getMaxEnergy();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        if (stack.getTag() == null)
        {
            EnergyHelper.setDefaultEnergyTag(stack, 0);
        }

        return 1D - ((double) stack.getTag().getInt(EnergyHelper.ENERGY_NBT) / (double) getMaxEnergyStored(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, tooltip, flag);
        if (KeyHandler.isShiftKeyDown())
        {
            tooltip.add(
                    StringUtil.formatNumber(getEnergyStored(stack))
                            .append(" / ")
                            .append(StringUtil.formatNumber(getMaxEnergyStored(stack)))
                            .append(" " + "FE")
            );
        }
    }

    @Override
    public boolean isPowered()
    {
        return true;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
    {
        return new EnergyItemCapability<>(new EnergyStorageCapability(this, stack));
    }
}
