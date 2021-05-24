package com.lousiesmods.lousiesutilities.item;

import com.lousiesmods.lousiesutilities.api.ILockable;
import com.lousiesmods.lousiesutilities.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;


public class ItemPadlock extends Item
{
    public ItemPadlock()
    {
        super(new Item.Properties().stacksTo(1).tab(Reference.CREATIVE_TAB_ITEMS));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context)
    {
        final World world = context.getLevel();
        final Block block = context.getLevel().getBlockState(context.getClickedPos()).getBlock();

        if(!world.isClientSide)
        {
            TileEntity tile = world.getBlockEntity(context.getClickedPos());

            if(block instanceof ILockable && context.getPlayer().isShiftKeyDown() && tile != null)
            {
                ((ILockable) tile).setOwner(context.getPlayer().getGameProfile());
                ((ILockable) tile).setLevel(ILockable.AccessLevel.PUBLIC);

                if(!context.getPlayer().isCreative())
                {
                    context.getItemInHand().setCount(context.getItemInHand().getCount() - 1);
                }

                context.getPlayer().playSound(SoundEvents.ANVIL_USE, 1.0F, 1.0F);
                context.getPlayer().sendMessage(new TranslationTextComponent("lousiesutilities.messages.lockable.success"), null);
            }

            else
            {
                context.getPlayer().sendMessage(new TranslationTextComponent("lousiesutilities.messages.lockable.invalid"), null);
            }
        }

        return ActionResultType.SUCCESS;
    }
}
