package org.Theomachy.Data;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;

public class ServerSetting
{
	public static boolean PVP = Bukkit.getWorlds().get(0).getPVP();
	public static boolean AUTO_SAVE = Bukkit.getWorlds().get(0).isAutoSave();
	public static boolean ANIMAL = Bukkit.getWorlds().get(0).getAllowAnimals();
	public static boolean MONSTER = Bukkit.getWorlds().get(0).getAllowMonsters();
	public static Difficulty DIFFICULTY = Bukkit.getWorlds().get(0).getDifficulty();
}
