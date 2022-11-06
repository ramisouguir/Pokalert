package com.outflows.pokalert.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.outflows.pokalert.pokalert;
import com.outflows.pokalert.alert.PokemonDetection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ChatItems 
{
	private static boolean wbInProgress = false;
	private static boolean welcomeInProgress = false;
	private static KeyBinding wb = pokalert.wb;
	private static KeyBinding welcome = pokalert.welcome;
	private static ArrayList<KeyBinding> kbs = new ArrayList<>(Arrays.asList(wb, welcome));
	@SubscribeEvent
	public void giveItems(ServerChatEvent event)
	{
		if(event.getMessage().contains("potato")) {
			event.getPlayer().inventory.addItemStackToInventory(new ItemStack(Items.POTATO, 64));
		}
	}
	/*@SubscribeEvent
	public void listTargets(ServerChatEvent event)
	{
		if(event.getMessage().equals("listpokes"))
		{
			PokemonDetection a = new PokemonDetection();
			System.out.println(a.getTargets().toString());
			System.out.println(a.getTargets());
		}
	}*/
	
	@SubscribeEvent 
	public void welcome(ClientChatReceivedEvent event)
	{
		if(event.getMessage().getFormattedText().contains("Welcome") && welcomeInProgress) {
			if(event.getType().equals(ChatType.SYSTEM))
			{
				Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getConnection().getPlayerInfoMap();
				//System.out.println("WELCOME MESSAGE WAS DISPLAYED");
			    pChat("Welcome To Kandorus! Population: "+players.size()+" players online!");
			}
		}
	}
	
	@SubscribeEvent
	public void welcomeBack(ClientChatReceivedEvent event)
	{
		if(event.getMessage().getUnformattedText().contains("[+]") && wbInProgress)
		{
			if(event.getType().equals(ChatType.SYSTEM))
					{
						pChat("Welcome Back"+event.getMessage().getUnformattedText().substring(3));
					}
		}
	}
	
	@SubscribeEvent
	public void updateKeys(InputEvent.KeyInputEvent event)
	{
			if(wb.isPressed())
			{
				if(wbInProgress)
				{
				 privateChat("Welcome Back Bot Deactivated");
				 wbInProgress = false;
				}
				else
				{
					privateChat("Welcome Back Bot Activated");
				 wbInProgress = true;
				}
			}
			if(welcome.isPressed())
			{
				if(welcomeInProgress)
				{
				 privateChat("Welcome Bot Deactivated");
				 welcomeInProgress = false;
				}
				else
				{
					privateChat("Welcome Bot Activated");
				 welcomeInProgress = true;
				}
			}
		
	}
	public void pChat(String message)
	{
		if(Minecraft.getMinecraft().player!=null)
		{
			Minecraft.getMinecraft().player.sendChatMessage(message);
		}
	}
	public void privateChat(String message)
	{
		ITextComponent m = new TextComponentString(message);
		Minecraft.getMinecraft().player.sendMessage(m);
	}
}
