package com.outflows.pokalert.alert;

import com.outflows.pokalert.pokalert;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class Relog
{
	private static KeyBinding relog = pokalert.relog;
	
	@SubscribeEvent
	public void joinBack(PlayerLoggedOutEvent event)
	{
		System.out.println("Leave");
		
		
	}
}
