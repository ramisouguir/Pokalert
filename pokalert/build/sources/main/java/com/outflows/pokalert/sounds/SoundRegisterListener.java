package com.outflows.pokalert.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundRegisterListener 
{
	
  @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
  public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) 
  {
     event.getRegistry().registerAll(new SoundEvent(new ResourceLocation("pokalert", "bond")).setRegistryName("bond"));
  }
}
