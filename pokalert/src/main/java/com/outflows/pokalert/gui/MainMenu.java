package com.outflows.pokalert.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MainMenu extends GuiScreen
{
	private String screenTitle = "Pokalert Settings";
	//private final GuiScreen parentGuiScreen;
	
	public MainMenu()
	{
		//this.parentGuiScreen = pgs;
	}
	public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 27, "Done"));
    }
	
	protected void actionPerformed(GuiButton button) throws IOException
	{
		System.out.println("Button Pressed");
		if(button.id ==200)
		{
			System.out.println("Here");
			closeGui();
		}
	}
	
	public void closeGui()
	{
		this.mc.displayGuiScreen((GuiScreen)null);

        if (this.mc.currentScreen == null)
        {
            this.mc.setIngameFocus();
        }
	}
	
	 public void drawScreen(int mouseX, int mouseY, float partialTicks)
	    {
	        this.drawDefaultBackground();
	        mc.getTextureManager().bindTexture(new ResourceLocation("pokalert", "textures/gui/core.png"));
	       // this.optionsRowList.drawScreen(mouseX, mouseY, partialTicks);
	        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 5, 16777215);
	        super.drawScreen(mouseX, mouseY, partialTicks);
	    }
	 
	 @Override
	    public void onGuiClosed()
	    {
	        super.onGuiClosed();
	        this.mc.gameSettings.onGuiClosed();
	    }
}
