package com.lousiesmods.lousiesutilities.util;

import com.lousiesmods.lousiesutilities.capability.EnergyStorageCapability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnergyHelper
{
    public static final String ENERGY_NBT = "Energy";

    public static ItemStack setDefaultEnergyTag(ItemStack stack, int energy) 
    {
        if (!stack.hasTag())
        {
            stack.setTag(new CompoundNBT());
        }
        
        stack.getOrCreateTag().putInt(ENERGY_NBT, energy);
        return stack;
    }

    public static int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
        if (!container.hasTag()) 
        {
            setDefaultEnergyTag(container, 0);
        }
        
        int stored = getEnergyStored(container);
        int received = Math.min(getMaxEnergyStored(container) - stored, Math.min(getMaxEnergyStored(container) / 4, maxReceive));
        
        if (!simulate)
        {
            stored += received;
            NBTUtil.setInt(container, ENERGY_NBT, stored);
        }
        return received;
    }

    public static int extractEnergy(ItemStack container, int maxExtract, boolean simulate)
    {
        if (!container.getOrCreateTag().contains(ENERGY_NBT))
        {
            return 0;
        }
        int stored = getEnergyStored(container);
        int extracted = Math.min(stored, Math.min(getMaxEnergyStored(container) / 4, maxExtract));

        if (!simulate)
        {
            stored -= extracted;
            NBTUtil.setInt(container, ENERGY_NBT, stored);
        }

        return extracted;
    }

    public static int getEnergyStored(ItemStack container) 
    {
        if (!container.getOrCreateTag().contains(EnergyHelper.ENERGY_NBT)) 
        {
            return 0;
        }
        
        return Math.min(NBTUtil.getInt(container, EnergyHelper.ENERGY_NBT), getMaxEnergyStored(container));
    }

    public static int getMaxEnergyStored(ItemStack container)
    {
        EnergyStorageCapability storage = (EnergyStorageCapability) container.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        return storage != null ? storage.getMaxEnergyStored() : 0;
    }
}
