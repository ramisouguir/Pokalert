package com.outflows.pokalert.gui;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler 
{
	@SubscribeEvent
	public void printGui(GuiOpenEvent event)
	{
		System.out.println(event.getGui());
	}
	
	@SubscribeEvent
	public void addMainMenu(ClientChatReceivedEvent event)
	{
		MainMenu mm = new MainMenu();
		if(event.getMessage().getFormattedText().contains("test"))
		{
			Minecraft.getMinecraft().displayGuiScreen(mm);
		}
	}
	
	@SubscribeEvent
	public void handleButtonClicked(GuiScreenEvent.ActionPerformedEvent event)
	{
		System.out.println(event.getButton().id);
	}
	
	public void closeGui(GuiScreen screen)
	{
		screen.mc.displayGuiScreen((GuiScreen)null);

        if (screen.mc.currentScreen == null)
        {
            screen.mc.setIngameFocus();
        }
	}
}
