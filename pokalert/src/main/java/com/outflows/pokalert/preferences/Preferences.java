package com.outflows.pokalert.preferences;

public class Preferences
{
	private static boolean glowingEffect = true;
	private static boolean showPokemonWithOwners = false;
	private static boolean showDialogBox = true;
	
	public static boolean getShowDialogBox()
	{
		return showDialogBox;
	}
	public static void setShowDialogBox(boolean value)
	{
		showDialogBox = value;
	}
	public static boolean getShowPokemonWithOwners()
	{
		return showPokemonWithOwners;
	}
	public static void setShowPokemonWithOwners(boolean value)
	{
		showPokemonWithOwners = value;
	}
	public static boolean getGlow()
	{
		return glowingEffect;
	}
	
	public static void setGlow(boolean value)
	{
		glowingEffect = value;
	}
}
