package org.Theomachy.Handler.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.Theomachy.Data.GameData;

public class PlayerHandler
{
	public List<Player> getTeamMember(Player player) {
		String teamName = GameData.playerTeam.get(player.getName());
		ArrayList<String> memberName  = new ArrayList<String>();
		if (GameData.playerTeam.containsValue(teamName))
		{
            for (Entry<String, String> entry : GameData.playerTeam.entrySet()) {
                if (entry.getValue().equals(teamName))
                    memberName.add(entry.getKey());
            }
		}
		ArrayList<Player> memberPlayer = new ArrayList<Player>();
		for (String e : memberName)
		{
			if (GameData.onlinePlayer.containsKey(e))
				memberPlayer.add(GameData.onlinePlayer.get(e));
		}
		return memberPlayer;
	}
	
	public List<Player> getNearByTeamMembers(Player player, double x, double y, double z) {
		String playerName = player.getName();
		String playerTeamName = GameData.playerTeam.get(playerName);

		ArrayList<Player> nearByTeamMembers = new ArrayList<Player>();
		if (playerTeamName != null) {
			List<Entity> nearByEntityList = player.getNearbyEntities(x, y, z);
			if (!nearByEntityList.isEmpty()) {
				for (Entity entity : nearByEntityList) {
					if (entity instanceof Player) {
						String memberName = ((Player)entity).getName();
						String memberTeamName = GameData.playerTeam.get(memberName);
						if (memberTeamName.equals(playerTeamName))
							nearByTeamMembers.add((Player)entity);
					}
				}
			}
		}
		return nearByTeamMembers;
	}
	
	public List<Player> getNearByNotTeamMembers(Player player, double x, double y, double z) {
		String playerName = player.getName();
		String playerTeamName = GameData.playerTeam.get(playerName);

		ArrayList<Player> nearByNotTeamMembers = new ArrayList<Player>();
		List<Entity> nearByEntityList = player.getNearbyEntities(x, y, z);
		if (playerTeamName != null) {
			if (!nearByEntityList.isEmpty()) {
				for (Entity entity : nearByEntityList) {
					if (entity instanceof Player) {
						String memberName = ((Player)entity).getName();
						String memberTeamName = GameData.playerTeam.get(memberName);
						if (!memberTeamName.equals(playerTeamName))
							nearByNotTeamMembers.add((Player)entity);
					}
				}
			}
		}
		else {
			if (!nearByEntityList.isEmpty()) {
				for (Entity entity : nearByEntityList) {
					if (entity instanceof Player)
						nearByNotTeamMembers.add((Player)entity);
				}
			}
		}
		return nearByNotTeamMembers;
	}
}
