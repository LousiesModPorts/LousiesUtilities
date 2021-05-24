package com.lousiesmods.lousiesutilities.event;

import com.lousiesmods.lousiesutilities.handler.UpdateHandler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LoginEvent
{
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        Thread updateCheck = new Thread(new UpdateHandler(event.getPlayer()), "Update Checker");
        updateCheck.start();
    }
}
