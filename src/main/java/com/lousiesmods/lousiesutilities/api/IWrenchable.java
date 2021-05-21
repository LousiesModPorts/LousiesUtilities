package com.lousiesmods.lousiesutilities.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable
{
    void harvestBlock(PlayerEntity player, World world, BlockPos pos);
}
