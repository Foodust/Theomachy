package org.Theomachy.Message;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Ability.ENUM.TargetType;
import org.Theomachy.Data.GameData;


public class T_Message
{
	public static void CoolTimeTeller(Player player, AbilityCase abilityCase, int cool)
	{
        switch (abilityCase) {
            case COMMON ->
					player.sendMessage("쿨타임이 " + cool + "초 남았습니다!");
            case NORMAL ->
                    player.sendMessage(ChatColor.AQUA + "[일반]  " + ChatColor.WHITE + "능력의 쿨타임이 " + cool + "초 남았습니다!");
            case RARE ->
                    player.sendMessage(ChatColor.RED + "[고급]  " + ChatColor.WHITE + "능력의 쿨타임이 " + cool + "초 남았습니다!");
        }
	}

	
	public static void CoolTimeCountTeller(AbilityCase abilityCase ,String playerName, int cool)
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		if (player != null)
		{
            switch (abilityCase) {
				case COMMON ->
						player.sendMessage(cool + "초 전");
				case NORMAL ->
						player.sendMessage(ChatColor.AQUA + "[일반]  " + ChatColor.WHITE + cool + "초 전");
				case RARE ->
						player.sendMessage(ChatColor.RED + "[고급]  " + ChatColor.WHITE + cool + "초 전");
            }
		}
	}
	
	public static void AbilityReset(AbilityCase abilityCase ,String playerName)
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		if (player != null)
		{
            switch (abilityCase) {
				case COMMON ->
						player.sendMessage(ChatColor.GOLD + "능력을 다시 사용할 수 있습니다.");
				case NORMAL ->
						player.sendMessage(ChatColor.AQUA + "[일반]  " + ChatColor.GOLD + "능력을 다시 사용할 수 있습니다.");
				case RARE ->
						player.sendMessage(ChatColor.RED + "[고급]  " + ChatColor.GOLD + "능력을 다시 사용할 수 있습니다.");
            }
		}
	}

	public static void Skill_Used(Player player, AbilityCase abilityCase)
	{
        switch (abilityCase) {
			case COMMON ->
					player.sendMessage(ChatColor.YELLOW + "능력을 사용했습니다!");
			case NORMAL ->
					player.sendMessage(ChatColor.AQUA + "[일반]  " + ChatColor.YELLOW + "능력을 사용했습니다!");
			case RARE ->
					player.sendMessage(ChatColor.RED + "[고급]  " + ChatColor.YELLOW + "능력을 사용했습니다!");
        }
	}
	
	public static void LackItemError(Player player, Material material, int stack)
	{
        switch (material) {
            case COBBLESTONE -> {
                player.sendMessage("조약돌이 부족합니다.");
                player.sendMessage("필요한 개수 : " + ChatColor.RED + stack);
            }
            case OAK_PLANKS -> {
                player.sendMessage("가공된 나무가 부족합니다.");
                player.sendMessage("필요한 개수 : " + ChatColor.RED + stack);
            }
            case IRON_INGOT -> {
                player.sendMessage("철괴가 부족합니다.");
                player.sendMessage("필요한 개수 : " + ChatColor.RED + stack);
            }
        }
	}
	
	public static void TooFarError(Player player, TargetType targetType)
	{
        switch (targetType) {
			case ENTITY_TOO_FAR -> player.sendMessage(ChatColor.RED + "대상과의 거리가 너무 멉니다.");
			case TARGET_TOO_FAR -> player.sendMessage(ChatColor.RED + "목표와의 거리가 너무 멉니다.");
        }
		
	}


	public static void PassiveEnable(Player player, int passiveCase)
	{
		switch(passiveCase){
			case 0-> player.sendMessage(ChatColor.YELLOW+"능력이 활성화 되었습니다.");
		}
	}
}
