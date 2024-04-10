package org.Theomachy.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.Theomachy.Enum.AbilityInfo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import org.Theomachy.Ability.Ability;
import org.bukkit.inventory.ItemStack;

public class GameData
{
	public static HashMap<String,Player> onlinePlayer = new HashMap<String,Player>(); //온라인플레이어

	public static HashMap<String, Ability> playerAbility = new HashMap<String,Ability>(); //플레이어 지정 능력
	public static HashMap<String, AbilityInfo> playerAbilityInfo = new HashMap<String,AbilityInfo>(); //플레이어 지정 능력
	public static HashMap<String,String> playerTeam = new HashMap<String,String>(); //플레이어, 팀이름
	public static HashMap<String,Location> spawnArea = new HashMap<String,Location>(); //팀 스폰 지역
	public static HashMap<String, ArmorStand> playerHealthBar = new HashMap<>(); // 플레이어 이름, 체력바
	public static HashMap<Location, Material> allWorld = new HashMap<>();
	public static List<ItemStack> startItems =  new ArrayList<>();

	public static void initialize(){
		onlinePlayer.clear();
		playerAbility.clear();
		playerTeam.clear();
		spawnArea.clear();
		clearHealthBar();
		playerHealthBar.clear();
	}
	public static void clearHealthBar(){
		for(ArmorStand armorStand : playerHealthBar.values()){
			armorStand.remove();
		}
	}
}
