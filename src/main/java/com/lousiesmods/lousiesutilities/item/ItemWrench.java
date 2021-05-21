package com.lousiesmods.lousiesutilities.item;

import com.lousiesmods.lousiesutilities.api.IWrenchable;
import com.lousiesmods.lousiesutilities.handler.registry.SoundRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.ToolType;


public class ItemWrench extends ItemEnergyBase
{
    private static final int HARVEST_LEVEL;
    private static final int MAX_ENERGY;

    static
    {
        HARVEST_LEVEL = 3;
        MAX_ENERGY = 15000;
    }

    public ItemWrench()
    {
        super(new Properties()
                .defaultDurability(MAX_ENERGY)
                .rarity(Rarity.EPIC)
                .addToolType(ToolType.get("wrench"), HARVEST_LEVEL)
        );
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context)
    {
        if(context.getLevel().isClientSide() && context.getPlayer().isShiftKeyDown())
        {
            Block block = context.getLevel().getBlockState(context.getClickedPos()).getBlock();

            if(block instanceof IWrenchable)
            {
                ((IWrenchable) block).harvestBlock(context.getPlayer(), context.getLevel(), context.getClickedPos());
                context.getPlayer().playSound(SoundRegistry.WRENCH_USE.get(),  10.0F, 10.0F);

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.FAIL;
    }

    @Override
    public int getHarvestLevel()
    {
        return HARVEST_LEVEL;
    }

}
