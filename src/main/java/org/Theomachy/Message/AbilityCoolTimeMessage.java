package org.Theomachy.Message;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.TargetType;
import org.Theomachy.Data.GameData;


public class AbilityCoolTimeMessage
{
	public void CoolTimeTeller(Player player, AbilityCase abilityCase, int cool)
	{
        switch (abilityCase) {
            case COMMON ->
					player.sendMessage(TheomachyMessage.INFO_COOL_TIME_IS.getMessage() + cool + TheomachyMessage.INFO_COOL_TIME_LEFT.getMessage());
            case NORMAL ->
                    player.sendMessage(TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + ChatColor.WHITE + TheomachyMessage.INFO_ABILITY_COOL_TIME_IS.getMessage() + cool + TheomachyMessage.INFO_COOL_TIME_LEFT.getMessage());
            case RARE ->
                    player.sendMessage(TheomachyMessage.INFO_RARE_ABILITY.getMessage() + ChatColor.WHITE + TheomachyMessage.INFO_ABILITY_COOL_TIME_IS.getMessage() + cool + TheomachyMessage.INFO_COOL_TIME_LEFT.getMessage());
        }
	}

	
	public void CoolTimeCountTeller(AbilityCase abilityCase ,String playerName, int cool)
	{
		Player player = GameData.onlinePlayer.get(playerName);
		if (player != null)
		{
            switch (abilityCase) {
				case COMMON ->
						player.sendMessage(cool + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
				case NORMAL ->
						player.sendMessage(TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + ChatColor.WHITE + cool + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
				case RARE ->
						player.sendMessage(TheomachyMessage.INFO_RARE_ABILITY.getMessage() + ChatColor.WHITE + cool + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
            }
		}
	}
	
	public void AbilityReset(AbilityCase abilityCase ,String playerName)
	{
		Player player = GameData.onlinePlayer.get(playerName);
		if (player != null)
		{
            switch (abilityCase) {
				case COMMON ->
						player.sendMessage("0" + TheomachyMessage.INFO_RARE_ABILITY.getMessage());
				case NORMAL ->
						player.sendMessage(TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + "0" + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
				case RARE ->
						player.sendMessage(TheomachyMessage.INFO_RARE_ABILITY.getMessage() + "0" + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
            }
		}
	}

	public void Skill_Used(Player player, AbilityCase abilityCase)
	{
        switch (abilityCase) {
			case COMMON ->
					player.sendMessage(TheomachyMessage.INFO_USING_ABILITY.getMessage());
			case NORMAL ->
					player.sendMessage(TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + TheomachyMessage.INFO_USING_ABILITY.getMessage());
			case RARE ->
					player.sendMessage(TheomachyMessage.INFO_RARE_ABILITY.getMessage() + TheomachyMessage.INFO_USING_ABILITY.getMessage());
        }
	}
	
	public void LackItemError(Player player, Material material, int stack)
	{
        switch (material) {
            case COBBLESTONE -> {
                player.sendMessage(TheomachyMessage.ERROR_NOT_ENOUGH_COBBLESTONE.getMessage());
                player.sendMessage(TheomachyMessage.INFO_NEED.getMessage() + ChatColor.RED + stack);
            }
            case OAK_PLANKS -> {
                player.sendMessage(TheomachyMessage.ERROR_NOT_ENOUGH_OAK.getMessage());
                player.sendMessage(TheomachyMessage.INFO_NEED.getMessage() + ChatColor.RED + stack);
            }
            case IRON_INGOT -> {
                player.sendMessage(TheomachyMessage.ERROR_NOT_ENOUGH_IRON.getMessage());
                player.sendMessage(TheomachyMessage.INFO_NEED.getMessage() + ChatColor.RED + stack);
            }
        }
	}
	
	public void TooFarError(Player player, TargetType targetType)
	{
        switch (targetType) {
			case ENTITY_TOO_FAR -> player.sendMessage(TheomachyMessage.ERROR_TOO_FAR_ENTITY.getMessage());
			case TARGET_TOO_FAR -> player.sendMessage(TheomachyMessage.ERROR_TOO_FAR_TARGET.getMessage());
        }
		
	}


	public void PassiveEnable(Player player, int passiveCase)
	{
		switch(passiveCase){
			case 0-> player.sendMessage(TheomachyMessage.INFO_ACTIVATE_ABILITY.getMessage());
			default -> player.sendMessage(TheomachyMessage.ERROR_DEACTIVATE_ABILITY.getMessage());
		}
	}
}
