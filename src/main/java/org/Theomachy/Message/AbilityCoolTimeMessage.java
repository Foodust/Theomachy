package org.Theomachy.Message;

import org.Theomachy.Handler.Module.source.MessageModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.TargetType;
import org.Theomachy.Data.GameData;


public class AbilityCoolTimeMessage
{
	private final MessageModule messageModule = new MessageModule();
	public void CoolTimeTeller(Player player, AbilityCase abilityCase, int cool)
	{
        switch (abilityCase) {
            case COMMON ->
					messageModule.sendPlayer(player,TheomachyMessage.INFO_COOL_TIME_IS.getMessage() + cool + TheomachyMessage.INFO_COOL_TIME_LEFT.getMessage());
            case NORMAL ->
					messageModule.sendPlayer(player,TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + ChatColor.WHITE + TheomachyMessage.INFO_ABILITY_COOL_TIME_IS.getMessage() + cool + TheomachyMessage.INFO_COOL_TIME_LEFT.getMessage());
            case RARE ->
					messageModule.sendPlayer(player,TheomachyMessage.INFO_RARE_ABILITY.getMessage() + ChatColor.WHITE + TheomachyMessage.INFO_ABILITY_COOL_TIME_IS.getMessage() + cool + TheomachyMessage.INFO_COOL_TIME_LEFT.getMessage());
        }
	}

	
	public void CoolTimeCountTeller(AbilityCase abilityCase ,String playerName, int cool)
	{
		Player player = GameData.onlinePlayer.get(playerName);
		if (player != null)
		{
            switch (abilityCase) {
				case COMMON ->
						messageModule.sendPlayer(player,cool + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
				case NORMAL ->
						messageModule.sendPlayer(player,TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + ChatColor.WHITE + cool + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
				case RARE ->
						messageModule.sendPlayer(player,TheomachyMessage.INFO_RARE_ABILITY.getMessage() + ChatColor.WHITE + cool + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
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
						messageModule.sendPlayer(player,"0" + TheomachyMessage.INFO_RARE_ABILITY.getMessage());
				case NORMAL ->
						messageModule.sendPlayer(player,TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + "0" + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
				case RARE ->
						messageModule.sendPlayer(player,TheomachyMessage.INFO_RARE_ABILITY.getMessage() + "0" + TheomachyMessage.INFO_BEFORE_SECOND.getMessage());
            }
		}
	}

	public void SkillUsed(Player player, AbilityCase abilityCase)
	{
        switch (abilityCase) {
			case COMMON ->
					messageModule.sendPlayer(player,TheomachyMessage.INFO_USING_ABILITY.getMessage());
			case NORMAL ->
					messageModule.sendPlayer(player,TheomachyMessage.INFO_NORMAL_ABILITY.getMessage() + TheomachyMessage.INFO_USING_ABILITY.getMessage());
			case RARE ->
					messageModule.sendPlayer(player,TheomachyMessage.INFO_RARE_ABILITY.getMessage() + TheomachyMessage.INFO_USING_ABILITY.getMessage());
        }
	}
	
	public void LackItemError(Player player, Material material, int stack)
	{
        switch (material) {
            case COBBLESTONE -> {
				messageModule.sendPlayer(player,TheomachyMessage.ERROR_NOT_ENOUGH_COBBLESTONE.getMessage());
				messageModule.sendPlayer(player,TheomachyMessage.INFO_NEED.getMessage() + ChatColor.RED + stack);
            }
            case OAK_PLANKS -> {
				messageModule.sendPlayer(player,TheomachyMessage.ERROR_NOT_ENOUGH_OAK.getMessage());
				messageModule.sendPlayer(player,TheomachyMessage.INFO_NEED.getMessage() + ChatColor.RED + stack);
            }
            case IRON_INGOT -> {
				messageModule.sendPlayer(player,TheomachyMessage.ERROR_NOT_ENOUGH_IRON.getMessage());
				messageModule.sendPlayer(player,TheomachyMessage.INFO_NEED.getMessage() + ChatColor.RED + stack);
            }
        }
	}
	
	public void TooFarError(Player player, TargetType targetType)
	{
        switch (targetType) {
			case ENTITY_TOO_FAR -> messageModule.sendPlayer(player,TheomachyMessage.ERROR_TOO_FAR_ENTITY.getMessage());
			case TARGET_TOO_FAR -> messageModule.sendPlayer(player,TheomachyMessage.ERROR_TOO_FAR_TARGET.getMessage());
        }
		
	}


	public void PassiveEnable(Player player, int passiveCase)
	{
        if (passiveCase == 0) {
			messageModule.sendPlayer(player,TheomachyMessage.INFO_ACTIVATE_ABILITY.getMessage());
        } else {
			messageModule.sendPlayer(player,TheomachyMessage.ERROR_DEACTIVATE_ABILITY.getMessage());
        }
	}
}
