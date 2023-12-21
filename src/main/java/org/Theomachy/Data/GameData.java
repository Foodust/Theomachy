package org.Theomachy.Data;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.Theomachy.Ability.Ability;

public class GameData
{
	public static HashMap<String,Player> onlinePlayer = new HashMap<String,Player>(); //온라인플레이어

	public static HashMap<String, Ability> playerAbility = new HashMap<String,Ability>(); //플레이어 지정 능력
	public static HashMap<String,String> playerTeam = new HashMap<String,String>(); //플레이어, 팀이름
	public static HashMap<String,Location> spawnArea = new HashMap<String,Location>(); //팀 스폰 지역

	public static void initialize(){
		onlinePlayer.clear();
		playerAbility.clear();
		playerTeam.clear();
		spawnArea.clear();
	}
}
