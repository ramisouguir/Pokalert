package com.outflows.pokalert.commands;


	import java.util.ArrayList;
	import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

	public abstract class CommandBase implements ICommand
	{
	private ArrayList<String> aliases = new ArrayList<>();
	private String commandName;
	
	public CommandBase(ArrayList<String> aliasList, String commandName)
	{
		for(String word: aliasList)
		{
			this.aliases.add(word);
		}
		this.commandName = commandName;
	}

	public String getCommandName()
	{
		return this.commandName;
	}

	public String getCommandUsage(ICommandSender icommandsender)
	{
		return this.commandName+" <text/help>";
	}

	public List<String> getCommandAliases()
	{
		return this.aliases;
	}

	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if(astring.length == 0)
		{
			icommandsender.sendMessage(new TextComponentString("Invalid Arguments. Usage: " + this.getCommandUsage(icommandsender)));
			return;
		}
		if (astring[0] == "help")
		{
			icommandsender.sendMessage(new TextComponentString("Usage: " + this.getCommandUsage(icommandsender)));
			return;
		} else {
		TextComponentString msg = new TextComponentString("Output: [");
		for (int i = 0;i < astring.length; ++i)
		{
			msg.appendText(" " + astring[i]);
		}
		msg.appendText(" ]");
		icommandsender.sendMessage(msg);	
		}
	}

	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
	return true;
	}

	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
	String[] astring)
	{
	return aliases;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
	return false;
	}

	@Override
	public int compareTo(ICommand arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.commandName;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return getCommandUsage(sender);
	}

	@Override
	public List<String> getAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) 
	{
		return aliases;
	}
	}

