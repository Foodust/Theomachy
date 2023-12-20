package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TeamModule {
    public static void setTeam(CommandSender sender, String[] data){
        if (data.length > 2) {
            if (Objects.equals(data[1], "random")) {
                List<Player> onlinePlayers = new ArrayList<>( Bukkit.getOnlinePlayers());
                Collections.shuffle(onlinePlayers);
                String[] teams = {data[2], data[3]};
                int teamIndex = 0;
                for (Player player : onlinePlayers) {
                    String playerName = player.getName();
                    String teamNameOld = GameData.playerTeam.get(playerName);
                    String teamName = teams[teamIndex++ % 2];
                    GameData.playerTeam.put(player.getName(), teamName);
                    if (teamNameOld == null) //플레이어 팀 초기화
                    {
                        Bukkit.broadcastMessage(TheomachyMessage.INFO_PLAYER.getMessage() + ChatColor.RED + playerName + ChatColor.WHITE + " (이)가 팀 " + ChatColor.DARK_AQUA + teamName + ChatColor.WHITE + TheomachyMessage.INFO_SET.getMessage());
                    } else //플레이어 팀 변경
                    {
                        Bukkit.broadcastMessage(TheomachyMessage.INFO_PLAYER.getMessage() + ChatColor.RED + playerName + ChatColor.WHITE + TheomachyMessage.INFO_CHANGE_TEAM.getMessage() + ChatColor.BLUE + teamNameOld + ChatColor.WHITE + " > " + ChatColor.DARK_AQUA + teamName);
                    }
                }
            } else {
                for (int i = 2; i < data.length; i++) {
                    if (GameData.onlinePlayer.containsKey(data[i])) {
                        String playerName = data[i];
                        String teamName = data[1];
                        String teamNameOld = GameData.playerTeam.get(playerName);
                        GameData.playerTeam.put(data[i], teamName);
                        if (teamNameOld == null) //플레이어 팀 초기화
                        {
                            Bukkit.broadcastMessage(TheomachyMessage.INFO_PLAYER.getMessage() + ChatColor.RED + playerName + ChatColor.WHITE + " (이)가 팀 " + ChatColor.DARK_AQUA + teamName + ChatColor.WHITE + TheomachyMessage.INFO_SET.getMessage());
                        } else //플레이어 팀 변경
                        {
                            Bukkit.broadcastMessage(TheomachyMessage.INFO_PLAYER.getMessage() + ChatColor.RED + playerName + ChatColor.WHITE + TheomachyMessage.INFO_CHANGE_TEAM.getMessage() + ChatColor.BLUE + teamNameOld + ChatColor.WHITE + " > " + ChatColor.DARK_AQUA + teamName);
                        }
                    } else
                        sender.sendMessage(data[i] + TheomachyMessage.ERROR_DOES_NOT_EXIST_PLAYER_NAME.getMessage());
                }
            }


        } else {
            sender.sendMessage(TheomachyMessage.INFO_HOW_TO_SET_TEAM.getMessage());
            sender.sendMessage(TheomachyMessage.INFO_HOW_TO_SET_TEAM_MESSAGE.getMessage());
            sender.sendMessage(TheomachyMessage.INFO_HOW_TO_SET_TEAM_EXAMPLE.getMessage());

            sender.sendMessage(TheomachyMessage.INFO_HOW_TO_SET_TEAM_RANDOM.getMessage());
            sender.sendMessage(TheomachyMessage.INFO_HOW_TO_SET_TEAM_RANDOM_MESSAGE.getMessage());
            sender.sendMessage(TheomachyMessage.INFO_HOW_TO_SET_TEAM_RANDOM_EXAMPLE.getMessage());
        }
    }
}
