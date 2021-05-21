package com.lousiesmods.lousiesutilities.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import org.lwjgl.glfw.GLFW;

public class KeyHandler
{
    private static long window = Minecraft.getInstance().getWindow().getWindow();

    public static boolean isShiftKeyDown()
    {
        return InputMappings.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static boolean isCtrlKeyDown()
    {
        return InputMappings.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL) || InputMappings.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    public static boolean isAltKeyDown()
    {
        return InputMappings.isKeyDown(window, GLFW.GLFW_KEY_LEFT_ALT) || InputMappings.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_ALT);
    }
}
