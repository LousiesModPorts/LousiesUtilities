package com.lousiesmods.lousiesutilities.api;

import net.minecraft.item.ItemStack;

public interface IEnergyContainer
{
    int receiveEnergy(ItemStack container, int maxReceive, boolean simulate);

    int extractEnergy(ItemStack container, int maxExtract, boolean simulate);

    int getEnergyStored(ItemStack container);

    int getMaxEnergyStored(ItemStack container);
}
