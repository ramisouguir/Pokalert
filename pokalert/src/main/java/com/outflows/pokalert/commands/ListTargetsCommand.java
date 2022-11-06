package com.outflows.pokalert.commands;

import java.util.ArrayList;
import java.util.Arrays;

import com.outflows.pokalert.alert.PokemonDetection;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class ListTargetsCommand extends CommandBase
{
	public ListTargetsCommand(ArrayList<String> alias)
	{
		super(alias, "ListTargets");
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args)
	{
		ArrayList<EntityPixelmon> pokes = PokemonDetection.getTargets();
		for(EntityPixelmon poke: pokes)
		{
			PokemonDetection.privateChat(poke.getPokemonName());
		}
	}
}
