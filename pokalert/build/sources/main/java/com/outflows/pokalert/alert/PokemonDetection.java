package com.outflows.pokalert.alert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Arrays;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.pixelmonmod.pixelmon.entities.pixelmon.*;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBase;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.forms.EnumAlolan;
import com.google.common.collect.ImmutableList;
import com.outflows.pokalert.pokalert;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PokemonDetection implements Runnable
{
	private boolean teleportCancelled = false;
	public boolean pokemonSpotted = false;
	private Thread t;
	public static ArrayList<String> legendaries = EnumSpecies.legendaries;
	public static ArrayList<String> ultrabeasts = EnumSpecies.ultrabeasts;
	public static ImmutableList<String> allpokes = EnumSpecies.getNameList();
	private static ArrayList<EntityPixelmon> targets = new ArrayList<EntityPixelmon>();
	public ResourceLocation location = new ResourceLocation("minecraft", "record.cat");
	public SoundEvent sevent = new SoundEvent(location);
	public char[] vowels = {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'};
	private static KeyBinding glowing = pokalert.glow;
	private static boolean glowingInProgress = true;
	private static KeyBinding swo = pokalert.showPokemonWithOwners;
	private static boolean swoInProgress = false;
	private static boolean showDialogBox = true;
	private static KeyBinding box = pokalert.box;
	public static ArrayList<String> badforms = new ArrayList<>(Arrays.asList("Pyroar", "Lycanroc", "Basculin", "Unfezant", "Cherrim", "Oricorio", "Unown", "Deerling", "Castform", "Burmy", "Shellos", "Wormadam", "Gatrodon", "Sawsbuck", "Flabebe", "Floette", "Florges"));
	
	//@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void detectPokemon(EntityJoinWorldEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		World world = Minecraft.getMinecraft().world;
		if(world!=null)
		{
			if(event.getEntity() instanceof EntityPixelmon)
			{
			  EntityPixelmon poke = (EntityPixelmon)event.getEntity();
			  if(swoInProgress||(!poke.hasOwner()))
			  {
				  if(poke.serializeNBT().toString().contains("IsShiny:1b") || legendaries.contains(poke.getPokemonName()) || ultrabeasts.contains(poke.getPokemonName()) || (poke.isBossPokemon()&&!(poke.getBossMode().toString().contains("Uncommon")))||poke.getPokemonName().equals("Ditto")||(((int)poke.serializeNBT().toString().charAt(poke.serializeNBT().toString().indexOf("Variant:")+8))-48>0 &&!badforms.contains(poke.getPokemonName())))
				  {
					 boolean isInTargets = false;
					 for(EntityPixelmon target: targets)
					 {
						 if(poke.equals(target))
						 {
							 isInTargets = true;
						 }
					 }
					 if(!isInTargets)
					 {
						 privateChat("One Pokemon Spotted ("+poke.getPokemonName()+")");
						 targets.add(poke);
						 
						if(!Minecraft.getMinecraft().inGameHasFocus&&!Minecraft.getMinecraft().isFullScreen())
					    {
						 if(poke.getBossMode().toString().equals("Rare"))
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("A Rare Boss Has spawned!");
						 }
						 else if(poke.getBossMode().toString().equals("Legendary"))
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("A Legendary Boss Has spawned!");
						 }
						 else if(poke.getBossMode().toString().equals("Ultimate"))
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("An Ultimate Boss Has spawned!");
						 }
						 else if(legendaries.contains(poke.getPokemonName()))
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("A Legendary Has Spawned!");
						 }
						 else if(poke.serializeNBT().toString().contains("IsShiny:1b"))
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("A Shiny Has Spawned!");
						 }
						 else if(ultrabeasts.contains(poke.getPokemonName()))
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("An UltraBeast Has Spawned!");
						 }
						 else if(poke.getPokemonName().equals("Ditto"))
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("A Ditto Has Spawned!");
						 }
						 else if(((int)poke.serializeNBT().toString().charAt(poke.serializeNBT().toString().indexOf("Variant:")+8))-48>0)
						 {
							 java.awt.Toolkit.getDefaultToolkit().beep();
							 if(showDialogBox)
							 displayPopUp("A Special Form Pokemon Has Spawned!");
						 }
						 /*else
						 {
							 if(isInArray(poke.getName().charAt(0), vowels))
							  displayPopUp("An "+poke.getName()+" with "+calculateIVPerc(poke)+"% IVs has spawned!");
							 else
								 displayPopUp("A "+poke.getName()+" with "+calculateIVPerc(poke)+"% IVs has spawned!");
						 }*/
					    }
						//pChat("Sound");
					    //Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMusicRecord(sevent));
					  }
					 pokemonSpotted=true;
					 if(glowingInProgress)
					  poke.setGlowing(true);
					 else
						 poke.setGlowing(false);
				  }
			  }
			  
			}
			
		}	
	}
	
	
	//@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void updateTargets(TickEvent.ClientTickEvent event)
	{
		World world = Minecraft.getMinecraft().world;
		if(world!=null)
		{
		Iterator<EntityPixelmon> iter = targets.iterator();
		  while(iter.hasNext())
			 {
			  EntityPixelmon target = iter.next();
				 if(!target.isEntityAlive())
				 {
					 privateChat("One Target Is Gone ("+target.getPokemonName()+")");
					 iter.remove();
				 }
			 }
		  if(targets.size()==0)
			 {
				 /*if(Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
				 {
					 
				 }*/
				 pokemonSpotted=false;
			 }
		  else if(Teleport.getInProgress())
		  {
			  Teleport.cancelTPTask();
		  }
		}
	}
	
	public void printCurrentSounds() throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		Minecraft mc = Minecraft.getMinecraft();
		SoundHandler sh = mc.getSoundHandler();
		//SoundManager mng = ObfuscationReflectionHelper.getPrivateValue(SoundManager.class, "sngManager");
	}
	
	//@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void printSound(PlaySoundEvent event)
	{
		World world = Minecraft.getMinecraft().world;
		if(world!=null)
		{
		  //pChat(event.getName());
	    }
	}
	
	public static ArrayList<EntityPixelmon> getTargets()
	{
		return targets;
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void grabId(AttackEntityEvent event) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
	  if(event.getTarget() instanceof EntityPixelmon)
	  {
	   EntityPixelmon poke = (EntityPixelmon)event.getTarget();
	   System.out.println(poke.serializeNBT().toString());
	   System.out.println(poke.serializeNBT().toString().indexOf("Variant:")+8);
	   System.out.println((poke.serializeNBT().toString().charAt(poke.serializeNBT().toString().indexOf("Variant:")+8))+"");
	  }
	}
	
	public int getDefIV(EntityPixelmon poke)
	{
		String nbt = poke.serializeNBT().toString();
		int first = nbt.indexOf("IVDefence:")+10;
		if(nbt.charAt(first+1)=='b')
		{
			return Integer.parseInt(nbt.substring(first, first+1));
		}
		else
			return Integer.parseInt(nbt.substring(first, first+2));
	}
	
	public int getSpDefIV(EntityPixelmon poke)
	{
		String nbt = poke.serializeNBT().toString();
		int first = nbt.indexOf("IVSpDef:")+8;
		if(nbt.charAt(first+1)=='b')
		{
			return Integer.parseInt(nbt.substring(first, first+1));
		}
		else
			return Integer.parseInt(nbt.substring(first, first+2));
	}
	
	public int getSpeedIV(EntityPixelmon poke)
	{
		String nbt = poke.serializeNBT().toString();
		int first = nbt.indexOf("IVSpeed:")+8;
		if(nbt.charAt(first+1)=='b')
		{
			return Integer.parseInt(nbt.substring(first, first+1));
		}
		else
			return Integer.parseInt(nbt.substring(first, first+2));
	}
	
	public int getAttackIV(EntityPixelmon poke)
	{
		String nbt = poke.serializeNBT().toString();
		int first = nbt.indexOf("IVAttack:")+9;
		if(nbt.charAt(first+1)=='b')
		{
			return Integer.parseInt(nbt.substring(first, first+1));
		}
		else
			return Integer.parseInt(nbt.substring(first, first+2));
	}
	
	public int getHPIV(EntityPixelmon poke)
	{
		String nbt = poke.serializeNBT().toString();
		int first = nbt.indexOf("IVHP:")+5;
		if(nbt.charAt(first+1)=='b')
		{
			return Integer.parseInt(nbt.substring(first, first+1));
		}
		else
			return Integer.parseInt(nbt.substring(first, first+2));
	}
	
	public int getSpAttIV(EntityPixelmon poke)
	{
		String nbt = poke.serializeNBT().toString();
		int first = nbt.indexOf("IVSpAtt:")+8;
		if(nbt.charAt(first+1)=='b')
		{
			return Integer.parseInt(nbt.substring(first, first+1));
		}
		else
			return Integer.parseInt(nbt.substring(first, first+2));
	}
	
	public int calculateIVPerc(int att, int def, int spdef, int spatt, int spe, int hp)
	{
		return (int)((((double)(att+def+spdef+spatt+spe+hp))/186)*100);
	}
	
	public int calculateIVPerc(EntityPixelmon poke)
	{
		return (int)((((double)(calculateTotalIVS(poke)))/186)*100);
	}
	
	public int calculateTotalIVS(EntityPixelmon poke)
	{
		return getDefIV(poke)+getAttackIV(poke)+getSpDefIV(poke)+getSpAttIV(poke)+getSpeedIV(poke)+getHPIV(poke);
	}
	
	public void printIVS(EntityPixelmon poke, boolean usePChat)
	{
		if(usePChat)
		{
	      pChat("Defense: "+getDefIV(poke));
		  pChat("Attack: "+getAttackIV(poke));
		  pChat("Special Defense: "+getSpDefIV(poke));
		  pChat("Special Attack: "+getSpAttIV(poke));
		  pChat("HP: "+getHPIV(poke));
		  pChat("Speed: "+getSpeedIV(poke));
		  pChat("Total: "+calculateTotalIVS(poke)+"/186  ("+calculateIVPerc(poke)+"%)");
		}
		else
		{
			privateChat(poke.getPokemonName());
			privateChat("Defense: "+getDefIV(poke));
		    privateChat("Attack: "+getAttackIV(poke));
			privateChat("Special Defense: "+getSpDefIV(poke));
			privateChat("Special Attack: "+getSpAttIV(poke));
			privateChat("HP: "+getHPIV(poke));
			privateChat("Speed: "+getSpeedIV(poke));
			privateChat("Total: "+calculateTotalIVS(poke)+"/186  ("+calculateIVPerc(poke)+"%)");
		}
	}
	/*public String testing(int number, String nbt)
	{
		return nbt.substring(number, number+2);
	}*/
	
	public void displayPopUp(String message)
	{
		JOptionPane pane = new JOptionPane();
		pane.setMessage(message);
		JDialog dialog = pane.createDialog("Pokalert Message");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		//JOptionPane.showMessageDialog(null, message, "Pokalert", JOptionPane.WARNING_MESSAGE);
		/*JFrame f;
		f = new JFrame("title");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100,100,300,300);
		f.setVisible(true);
		JOptionPane.showMessageDialog(f, message, "Test", JOptionPane.WARNING_MESSAGE);*/
	}

	public static void pChat(String message)
	{
		Minecraft.getMinecraft().player.sendChatMessage(message);
	}
	
	public static void privateChat(String message)
	{
		ITextComponent m = new TextComponentString(message);
		Minecraft.getMinecraft().player.sendMessage(m);
	}
	
	public boolean isInArray(char x, char[] letters)
	{
		for(char f: letters)
		{
			if(x==f)
				return true;
		}
		return false;
	}
	
	
	@SubscribeEvent
	public void printButtons(InitGuiEvent event)
	{
	   	//System.out.println(event.getButtonList());
		
	}
	
	@SubscribeEvent
	public void updateKeys(InputEvent.KeyInputEvent event)
	{
			if(glowing.isPressed())
			{
				if(glowingInProgress)
				{
					for(EntityPixelmon target: targets)
					{
						privateChat(target.getName());
						target.setGlowing(false);
						
					}
				 privateChat("Glowing Effect Deactivated");
				 glowingInProgress = false;
				}
				else
				{
					for(EntityPixelmon target: targets)
					{
					   target.setGlowing(true);
						
					}
				 privateChat("Glowing Effect Activated");
				 glowingInProgress = true;
				}
			}
			if(swo.isPressed())
			{
				if(swoInProgress)
				{
					for(EntityPixelmon target: targets)
					{
							if(target.hasOwner())
							{
								target.setGlowing(false);
							}
						
					}
				 privateChat("Pokemon With Owners Will Not Be Detected");
				 swoInProgress = false;
				}
				else
				{
					for(EntityPixelmon target: targets)
					{
							if(target.hasOwner())
							{
								target.setGlowing(true);
							}
						
					}
				privateChat("Pokemon With Owners Will Be Detected");
				 swoInProgress = true;
				}
			}
			if(box.isPressed())
			{
				if(showDialogBox)
				{
					showDialogBox = false;
					privateChat("Dialog Box Will Not Be Shown");
				}
				else
				{
					showDialogBox = true;
					privateChat("Dialog Box Will Be Shown");
				}
			}
	}
	@Override
	public void run() 
	{
		
	}
}
