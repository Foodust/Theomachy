package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamModule {
    public static void listTeam(CommandSender sender, String[] data){
        if (data.length>=2)
        {
            String teamName=data[1];
            if (GameData.playerTeam.containsValue(teamName))
            {
                sender.sendMessage(ChatColor.GREEN+"======  "+ChatColor.DARK_AQUA+teamName+ChatColor.GREEN+"  ======");
                Iterator<Map.Entry<String, String>> iterator = GameData.playerTeam.entrySet().iterator();
                for (int i=1;iterator.hasNext();i++)
                {
                    Map.Entry<String, String> entry=iterator.next();
                    if (entry.getValue().equals(teamName))
                    {
                        String playerName=(String)entry.getKey();
                        sender.sendMessage(i+".  "+ChatColor.GOLD+playerName);
                    }
                }
            }
            else
                sender.sendMessage("해당 팀에 팀원이 없습니다.");
        }
        else
        {
            sender.sendMessage(ChatColor.YELLOW+"자신의 팀을 확인합니다");
            String teamName = GameData.playerTeam.get(sender.getName());
            if (teamName != null)
            {
                if (GameData.playerTeam.containsValue(teamName))
                {
                    sender.sendMessage(ChatColor.GREEN+"======  "+ChatColor.DARK_AQUA+teamName+ChatColor.GREEN+"  ======");
                    Iterator<Map.Entry<String, String>> iterator = GameData.playerTeam.entrySet().iterator();
                    for (int i=1;iterator.hasNext();i++)
                    {
                        Map.Entry<String, String> entry=iterator.next();
                        if (entry.getValue().equals(teamName))
                        {
                            String playerName=(String)entry.getKey();
                            sender.sendMessage(i+".  "+ChatColor.GOLD+playerName);
                        }
                    }
                }
                else
                    sender.sendMessage("소속된 팀이 없습니다.");
            }
        }
    }
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
