package org.Theomachy.Handler.CommandModule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.Theomachy.DB.GameData;
import org.Theomachy.Utility.PermissionChecker;

import java.util.*;

public class Team {
    public static void Module(CommandSender sender, Command command, String label, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            if (data.length > 2) {
                if (Objects.equals(data[2], "random")) {
                    List<Player> onlinePlayers = new ArrayList<>( Bukkit.getOnlinePlayers());
                    Collections.shuffle(onlinePlayers);
                    String[] teams = {data[3], data[4]};
                    int teamIndex = 0;
                    for (Player player : onlinePlayers) {
                        String playerName = player.getName();
                        String teamNameOld = GameData.PlayerTeam.get(playerName);
                        String teamName = teams[teamIndex++ % 2];
                        GameData.PlayerTeam.put(player.getName(), teamName);
                        if (teamNameOld == null) //플레이어 팀 초기화
                        {
                            Bukkit.broadcastMessage("플레이어 " + ChatColor.RED + playerName + ChatColor.WHITE + " (이)가 팀 " + ChatColor.DARK_AQUA + teamName + ChatColor.WHITE + " 에 등록되었습니다.");
                        } else //플레이어 팀 변경
                        {
                            Bukkit.broadcastMessage("플레이어 " + ChatColor.RED + playerName + ChatColor.WHITE + " 의 팀이 변경되었습니다.    " + ChatColor.BLUE + teamNameOld + ChatColor.WHITE + " > " + ChatColor.DARK_AQUA + teamName);
                        }
                    }
                } else {
                    for (int i = 2; i < data.length; i++) {
                        if (GameData.OnlinePlayer.containsKey(data[i])) {
                            String playerName = data[i];
                            String teamName = data[1];
                            String teamNameOld = GameData.PlayerTeam.get(playerName);
                            GameData.PlayerTeam.put(data[i], teamName);
                            if (teamNameOld == null) //플레이어 팀 초기화
                            {
                                Bukkit.broadcastMessage("플레이어 " + ChatColor.RED + playerName + ChatColor.WHITE + " (이)가 팀 " + ChatColor.DARK_AQUA + teamName + ChatColor.WHITE + " 에 등록되었습니다.");
                            } else //플레이어 팀 변경
                            {
                                Bukkit.broadcastMessage("플레이어 " + ChatColor.RED + playerName + ChatColor.WHITE + " 의 팀이 변경되었습니다.    " + ChatColor.BLUE + teamNameOld + ChatColor.WHITE + " > " + ChatColor.DARK_AQUA + teamName);
                            }
                        } else
                            sender.sendMessage(data[i] + " 해당하는 유저가 없습니다.");
                    }
                }


            } else {
                sender.sendMessage(ChatColor.YELLOW + ("/t  team(t)   ") + ChatColor.AQUA + ("<TeamName>  ") + ChatColor.RED + ("<Player>  ") + ChatColor.WHITE + ("플레이어를 팀에 등록합니다."));
                sender.sendMessage("한번에 다수의 플레이어를 한 팀에 등록 할 수 있습니다.");
                sender.sendMessage("ex) /t t  팀   플레이어1   플레이어2   플레이어3");

                sender.sendMessage(ChatColor.YELLOW + "/t  team(t)   random" + ChatColor.BLUE + ("<TeamName1>  ") + ChatColor.RED + ("<TeamName2>  "));
                sender.sendMessage("무작위로 팀을 배정합니다");
                sender.sendMessage("ex) /t t  random   팀이름1   팀이름2 ");
            }
        }
    }
}
