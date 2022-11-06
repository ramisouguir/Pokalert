package com.outflows.pokalert;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;

import com.outflows.pokalert.alert.AntiAfk;
import com.outflows.pokalert.alert.PokemonDetection;
import com.outflows.pokalert.alert.Relog;
import com.outflows.pokalert.alert.Teleport;
import com.outflows.pokalert.chat.ChatItems;
import com.outflows.pokalert.commands.ListTargetsCommand;
import com.outflows.pokalert.gui.GuiHandler;
import com.outflows.pokalert.gui.MainMenu;
import com.outflows.pokalert.sounds.SoundRegisterListener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = pokalert.MODID, name = pokalert.NAME, version = pokalert.VERSION, dependencies = "required-after:pixelmon")
public class pokalert
{
    public static final String MODID = "pokalert";
    public static final String NAME = "Pokalert";
    public static final String VERSION = "1.3.1";
    public static KeyBinding tp = new KeyBinding("Initiate Teleport Sequence", 21, "Pixalert");
    public static KeyBinding wb = new KeyBinding("Welcome Back Bot", 22, "Pixalert");
    public static KeyBinding glow = new KeyBinding("Pokemon Glow Effect", 24, "Pixalert");
    public static KeyBinding showPokemonWithOwners = new KeyBinding("Detect Pokemon With Owners", 25, "Pixalert");
    public static KeyBinding welcome = new KeyBinding("Welcome Bot", 23, "Pixalert");
    public static KeyBinding box = new KeyBinding("Show Dialog Box", 25, "Pixalert");
    public static KeyBinding antiafk = new KeyBinding("Anti-Afk Bot", 26, "Pixalert");
    public static KeyBinding relog = new KeyBinding("Automatic Relog", 26, "Pixalert");
    private static Logger logger;
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
    	ClientCommandHandler.instance.registerCommand(new ListTargetsCommand(new ArrayList<>(Arrays.asList("listtargets", "lt"))));
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ClientRegistry.registerKeyBinding(tp);
    	ClientRegistry.registerKeyBinding(wb);
    	ClientRegistry.registerKeyBinding(welcome);
    	ClientRegistry.registerKeyBinding(glow);
    	ClientRegistry.registerKeyBinding(showPokemonWithOwners);
    	ClientRegistry.registerKeyBinding(box);
    	ClientRegistry.registerKeyBinding(relog);
    	ClientRegistry.registerKeyBinding(antiafk);
    	//MinecraftForge.EVENT_BUS.register(new SoundRegisterListener());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new Teleport());
    	MinecraftForge.EVENT_BUS.register(new PokemonDetection());
    	MinecraftForge.EVENT_BUS.register(new ChatItems());
    	MinecraftForge.EVENT_BUS.register(new AntiAfk());
    	MinecraftForge.EVENT_BUS.register(new GuiHandler());
    	//MinecraftForge.EVENT_BUS.register(new Relog());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) 
    {
   
    }
    
}
