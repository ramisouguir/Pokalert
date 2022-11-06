package com.outflows.pokalert.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundRegistrator 
{
public static final SoundEvent BOND = addSoundsToRegistry("bond");
	
	private static SoundEvent addSoundsToRegistry(String soundId) {
		ResourceLocation shotSoundLocation = new ResourceLocation("pokalert", soundId);
		SoundEvent soundEvent = new SoundEvent(shotSoundLocation);
		soundEvent.setRegistryName(shotSoundLocation);
		return soundEvent;
	}
}
