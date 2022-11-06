package com.outflows.pokalert.alert;

import java.util.Timer;

import com.outflows.pokalert.pokalert;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Teleport 
{
	private static boolean inProgress = false;
	private static int counter = 0;
	private static KeyBinding kb = pokalert.tp;
	
	@SubscribeEvent
	public void teleportInitiate(InputEvent.KeyInputEvent event)
	{
		//PokemonDetection.privateChat(inProgress+"");
		World world = Minecraft.getMinecraft().world;
		
		 if(world!=null)
		 {
			if(kb.isPressed())
			{
				counter = 0;
				if(!inProgress)
				{
					PokemonDetection.pChat("/rtp");
					inProgress = true;
					PokemonDetection.privateChat("TP Mode Activated");
				}
				else if(inProgress)
				{
					inProgress = false;
					PokemonDetection.privateChat("TP Mode Deactivated");
				}
				else
				{
					PokemonDetection.privateChat("Foobar");
				}
			}
		 }
		//PokemonDetection.privateChat(inProgress+"");
		
	}
	
	@SubscribeEvent
	public void countUp(ClientTickEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		World world = Minecraft.getMinecraft().world;
		if(world!=null)
		{
		  if(kb.isPressed())
		  {
			counter = 0;
	      }
		  if(inProgress)
		  {
		    counter++;
		    if(counter %300 == 0)
		    {
		    	player.move(MoverType.PLAYER, 1.0, 0.0, 0.0);
		    }
		    if(counter == 2480)
		     {
			  counter = 0;
			  PokemonDetection.pChat("/rtp");
		     }
		   }
		}
  }
	
	@SubscribeEvent
	public void leftServer(PlayerEvent.PlayerLoggedOutEvent event)
	{
		System.out.println("Here");
		inProgress = false;
	}
	
	public static boolean getInProgress()
	{
		return inProgress;
	}
	
	public static void cancelTPTask()
	{
	   	inProgress = false;
	   	PokemonDetection.privateChat("TP Mode Deactivated");
	   	counter = 0;
	}
	
}