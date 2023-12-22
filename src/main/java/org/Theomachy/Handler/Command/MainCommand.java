package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.AbilityModule;
import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;

import java.util.Objects;

public class MainCommand {
	private final StartStopCommand startStopCommand = new StartStopCommand();
	private final AbilityCommand abilityCommand = new AbilityCommand();
	private final AbilityModule abilityModule = new AbilityModule();
	private final SpawnCommand spawnCommand = new SpawnCommand();
	private final TeamCommand teamCommand = new TeamCommand();
	private final BlacklistCommand blacklistCommand = new BlacklistCommand();
	private final SettingCommand settingCommand = new SettingCommand();
	private final GamblingCommand gamblingCommand = new GamblingCommand();
	private final GameModule gameModule = new GameModule();
	public void tCommandHandler(CommandSender sender, Command command, String label, String[] data)
	{
		TheomachyMessage message = TheomachyMessage.getByMessage(data[0]);
        switch (Objects.requireNonNull(message)) {
			case COMMAND_START -> startStopCommand.GameReady(sender);
			case COMMAND_STOP -> startStopCommand.GameStop(sender);
			case COMMAND_ABILITY, COMMAND_ABILITY_A -> abilityCommand.abilitySet(sender, data);
			case COMMAND_ABILITY_LIST -> abilityModule.listOfAbilityPlayer(sender);
			case COMMAND_HELP -> abilityCommand.abilityHelp(sender);
			case COMMAND_SPAWN, COMMAND_SPAWN_S -> spawnCommand.module(sender, command, label, data);
			case COMMAND_TEAM, COMMAND_TEAM_T -> teamCommand.setTeam(sender, command, label, data);
			case COMMAND_INFO -> teamCommand.listOfTeam(sender, command, label, data);
			case COMMAND_CLEAR, COMMAND_CLEAR_C -> abilityCommand.abilityCollTimeClear(sender);
			case COMMAND_BLACKLIST, COMMAND_BLACKLIST_B, COMMAND_BLACKLIST_BLACK -> blacklistCommand.module(sender);
			case COMMAND_SETTING, COMMAND_SETTING_SET -> settingCommand.module(sender);
			case COMMAND_GAMBLING, COMMAND_GAMBLING_G -> gamblingCommand.module(sender);
			case COMMAND_GIVE -> giveTestItemCommand(sender);
//			case COMMAND_SAVE -> saveWorldCommand(sender);
//			case COMMAND_RELOAD -> reloadWorldCommand();
            default -> sender.sendMessage(TheomachyMessage.ERROR_WRONG_COMMAND.getMessage());
        }
	}
	
	public void xCommandHandler(CommandSender sender, Command command, String label, String[] data)
	{
		String playerName = sender.getName();
		String targetName = data[0];
		Ability ability = GameData.playerAbility.get(playerName);
		if (ability != null)
		{
			if (GameData.onlinePlayer.containsKey(targetName))
				ability.targetSet(sender, targetName);
			else
				sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_ONLINE_PLAYER.getMessage() + targetName);
		}
		else
			sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_WHO_HAVE_ABILITY.getMessage());
	}
	public void giveTestItemCommand(CommandSender sender){
		gameModule.giveItem(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())), Material.BLAZE_ROD,1);
		for(int i = 0  ; i <8; i++){
			gameModule.giveItem(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())), Material.COBBLESTONE,64);
		}
	}
	public void saveWorldCommand(CommandSender sender){
		gameModule.saveWorld(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getLocation());
	}
	public void reloadWorldCommand(){
		gameModule.reloadWorld();
	}
}
