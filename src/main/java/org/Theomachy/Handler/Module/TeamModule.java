package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class TeamModule {

    public static void listTeam(CommandSender sender, String[] data) {
        if (data.length >= 2) {
            String teamName = data[1];
            if (GameData.playerTeam.containsValue(teamName)) {
                sender.sendMessage(ChatColor.GREEN + "======  " + ChatColor.DARK_AQUA + teamName + ChatColor.GREEN + "  ======");
                Iterator<Map.Entry<String, String>> iterator = GameData.playerTeam.entrySet().iterator();
                for (int i = 1; iterator.hasNext(); i++) {
                    Map.Entry<String, String> entry = iterator.next();
                    if (entry.getValue().equals(teamName)) {
                        String playerName = (String) entry.getKey();
                        sender.sendMessage(i + ".  " + ChatColor.GOLD + playerName);
                    }
                }
            } else
                sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_HAVE_PLAYER_IN_THERE_TEAM.getMessage());
        } else {
            sender.sendMessage(TheomachyMessage.INFO_CHECK_MY_TEAM.getMessage());
            String teamName = GameData.playerTeam.get(sender.getName());
            if (teamName != null) {
                if (GameData.playerTeam.containsValue(teamName)) {
                    sender.sendMessage(ChatColor.GREEN + "======  " + ChatColor.DARK_AQUA + teamName + ChatColor.GREEN + "  ======");
                    Iterator<Map.Entry<String, String>> iterator = GameData.playerTeam.entrySet().iterator();
                    for (int i = 1; iterator.hasNext(); i++) {
                        Map.Entry<String, String> entry = iterator.next();
                        if (entry.getValue().equals(teamName)) {
                            String playerName = (String) entry.getKey();
                            sender.sendMessage(i + ".  " + ChatColor.GOLD + playerName);
                        }
                    }
                } else
                    sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_HAVE_TEAM.getMessage());
            }
        }
    }

    public static void setTeam(CommandSender sender, String[] data) {
        if (data.length > 2) {
            // random 팀
            if (Objects.equals(data[1], TheomachyMessage.COMMAND_RANDOM.getMessage())) {
                List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                Collections.shuffle(onlinePlayers);
                String[] teams = {data[2], data[3]};
                int teamIndex = 0;

                for (Player player : onlinePlayers) {
                    String playerName = player.getName();
                    String teamNameOld = GameData.playerTeam.get(playerName);
                    String teamName = teams[teamIndex++ % 2];

                    ChatColor newTeamColor = CommonModule.findColor(teamName);
                    if (teamNameOld == null) {
                        Bukkit.broadcastMessage(
                                TheomachyMessage.INFO_PLAYER.getMessage() +
                                        ChatColor.RED + playerName +
                                        ChatColor.WHITE + " (이)가 팀 " +
                                        newTeamColor + teamName +
                                        ChatColor.WHITE + TheomachyMessage.INFO_SET.getMessage());
                    } else {
                        GameData.playerTeam.remove(playerName);
                        ChatColor oldTeamColor = CommonModule.findColor(teamNameOld);
                        Bukkit.broadcastMessage(
                                TheomachyMessage.INFO_PLAYER.getMessage() +
                                        ChatColor.RED + playerName +
                                        ChatColor.WHITE + TheomachyMessage.INFO_CHANGE_TEAM.getMessage() +
                                        oldTeamColor + teamNameOld +
                                        ChatColor.WHITE + " > " + newTeamColor  + teamName);
                    }
                    PlayerModule.setScoreboard(player, DisplaySlot.BELOW_NAME,newTeamColor + teamName +  TheomachyMessage.SCOREBOARD_HEALTH.getMessage());
                    GameData.playerTeam.put(player.getName(), teamName);
                }
            }
            // 플레이어 지정
            else {
                for (int i = 2; i < data.length; i++) {
                    if (GameData.onlinePlayer.containsKey(data[i])) {
                        String playerName = data[i];
                        String teamName = data[1];
                        String teamNameOld = GameData.playerTeam.get(playerName);
                        ChatColor newTeamColor = CommonModule.findColor(teamName);
                        if (teamNameOld == null) {
                            Bukkit.broadcastMessage(TheomachyMessage.INFO_PLAYER.getMessage() + ChatColor.RED + playerName + ChatColor.WHITE + " (이)가 팀 " + newTeamColor + teamName + ChatColor.WHITE + TheomachyMessage.INFO_SET.getMessage());
                        } else {
                            GameData.playerTeam.remove(playerName);
                            ChatColor oldTeamColor = CommonModule.findColor(teamNameOld);
                            Bukkit.broadcastMessage(TheomachyMessage.INFO_PLAYER.getMessage() + ChatColor.RED + playerName + ChatColor.WHITE + TheomachyMessage.INFO_CHANGE_TEAM.getMessage() + oldTeamColor +teamNameOld + ChatColor.WHITE + " > " + newTeamColor + teamName);

                        }
                        PlayerModule.setScoreboard(Objects.requireNonNull(Bukkit.getPlayer(playerName)), DisplaySlot.BELOW_NAME,newTeamColor + teamName +  TheomachyMessage.SCOREBOARD_HEALTH.getMessage());
                        GameData.playerTeam.put(data[i], teamName);
                    } else
                        sender.sendMessage(data[i] + TheomachyMessage.ERROR_DOES_NOT_EXIST_PLAYER_NAME.getMessage());
                }
            }
        } else {
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_TEAM.getMessage());
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_TEAM_MESSAGE.getMessage());
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_TEAM_EXAMPLE.getMessage());
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_TEAM_RANDOM.getMessage());
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_TEAM_RANDOM_MESSAGE.getMessage());
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_TEAM_RANDOM_EXAMPLE.getMessage());
        }
    }
}
