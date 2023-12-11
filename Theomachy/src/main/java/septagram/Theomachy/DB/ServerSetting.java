package septagram.Theomachy.DB;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;

public interface ServerSetting
{
	boolean PVP = Bukkit.getWorlds().get(0).getPVP();
	boolean AUTO_SAVE = Bukkit.getWorlds().get(0).isAutoSave();
	boolean ANIMAL = Bukkit.getWorlds().get(0).getAllowAnimals();
	boolean MONSTER = Bukkit.getWorlds().get(0).getAllowMonsters();
	Difficulty DIFFICULTY = Bukkit.getWorlds().get(0).getDifficulty();
}
