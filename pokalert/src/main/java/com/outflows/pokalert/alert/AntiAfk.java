package com.outflows.pokalert.alert;

import com.outflows.pokalert.pokalert;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ChatType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiAfk
{
	private static KeyBinding antiafk = pokalert.antiafk;
	private static boolean inProgress = false;
	private static int count = 0;
	@SubscribeEvent
	public void afkAvoid(TickEvent.ClientTickEvent event)
	{
		if(inProgress)
		{
			count++;
			if(count==2400)
			{
				Minecraft.getMinecraft().player.jump();
				Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
				count = 0;
			}
			
		}
	}
	
	@SubscribeEvent
	public void updateKeys(InputEvent.KeyInputEvent event)
	{
		if(antiafk.isPressed())
		{
			count = 0;
			if(inProgress)
			{
				inProgress = false;
				PokemonDetection.privateChat("Anti-Afk Bot Deactivated");
			}
			else
			{
				inProgress = true;
				PokemonDetection.privateChat("Anti-Afk Bot Activated");
			}
		}
	}
}
