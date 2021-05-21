package com.lousiesmods.lousiesutilities.item;

import com.lousiesmods.lousiesutilities.handler.KeyHandler;
import com.lousiesmods.lousiesutilities.util.IWrench;
import com.lousiesmods.lousiesutilities.util.NBTUtil;
import com.lousiesmods.lousiesutilities.util.Reference;
import com.lousiesmods.lousiesutilities.util.StringUtil;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class ItemEnergyBase extends Item implements IWrench
{
    public ItemEnergyBase(Properties props)
    {
        super(props.tab(Reference.CREATIVE_TAB_ITEMS));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.isDamaged();
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, tooltip, flag);

        setDefaultTagCompound(stack);
        CompoundNBT compound = com.lousiesmods.lousiesutilities.util.NBTUtil.getTag(stack);
        int mode = compound.getInt("mode");

        if (!KeyHandler.isShiftKeyDown())
        {
            tooltip.add(StringUtil.getShiftText(Reference.MOD_ID));
        }
        if (KeyHandler.isShiftKeyDown())
        {
            tooltip.add(StringUtil.localize(Reference.MOD_ID, "tooltip.max_harvest_level", StringUtil.formatHarvestLevel(Reference.MOD_ID, getHarvestLevel()).withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.WHITE));
        }

        if (!KeyHandler.isCtrlKeyDown())
        {
            tooltip.add(StringUtil.getCtrlText(Reference.MOD_ID));
        }

        if (KeyHandler.isCtrlKeyDown())
        {
            tooltip.add(StringUtil.localize(Reference.MOD_ID, "tooltip.extra1").withStyle(TextFormatting.WHITE));
            tooltip.add(StringUtil.localize(Reference.MOD_ID, "tooltip.extra2").withStyle(TextFormatting.WHITE));
            tooltip.add(StringUtil.localize(Reference.MOD_ID, "tooltip.extra3", new TranslationTextComponent("B").withStyle(TextFormatting.GREEN)).withStyle(TextFormatting.WHITE));
        }

        if (KeyHandler.isShiftKeyDown())
        {
            tooltip.add(StringUtil.getTierText(Reference.MOD_ID, getTier()));

            if (!isPowered())
            {
                tooltip.add(StringUtil.formatNumber(stack.getMaxDamage() - stack.getDamageValue()).append(" / ").append(StringUtil.formatNumber(stack.getMaxDamage())).append(" ").append(StringUtil.localize(Reference.MOD_ID, "tooltip.durability")));
            }
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    public static void setDefaultTagCompound(ItemStack stack) 
    {
        if (!stack.hasTag()) 
        {
            CompoundNBT compound = new CompoundNBT();
            stack.setTag(compound);
        }
        Set<String> keySet = NBTUtil.getTag(stack).getAllKeys();

        if (!keySet.contains("mode"))
        {
            NBTUtil.getTag(stack).putInt("mode", 0);
        }
        
        if (!keySet.contains("range"))
        {
            NBTUtil.getTag(stack).putInt("range", 0);
        }
        
        if (!keySet.contains("forceDropItems"))
        {
            NBTUtil.getTag(stack).putBoolean("forceDropItems", false);
        }
        if (!keySet.contains("directionalPlacement"))
        {
            NBTUtil.getTag(stack).putBoolean("directionalPlacement", false);
        }
        if (!keySet.contains("fuzzyPlacement")) 
        {
            NBTUtil.getTag(stack).putBoolean("fuzzyPlacement", false);
        }
        
        if (!keySet.contains("fuzzyPlacementChance")) 
        {
            NBTUtil.getTag(stack).putInt("fuzzyPlacementChance", 100);
        }
        
        if (!keySet.contains("voidItems"))
        {
            NBTUtil.getTag(stack).putBoolean("voidItems", false);
        }
    }
}
