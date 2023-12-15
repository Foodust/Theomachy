package org.septagram.Theomachy.Handler.CommandModule;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Utility.PermissionChecker;

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
                            Bukkit.broadcast(Component.text("플레이어 " + NamedTextColor.RED + playerName + NamedTextColor.WHITE + " (이)가 팀 " + NamedTextColor.DARK_AQUA + teamName + NamedTextColor.WHITE + " 에 등록되었습니다."));
                        } else //플레이어 팀 변경
                        {
                            Bukkit.broadcast(Component.text("플레이어 " + NamedTextColor.RED + playerName + NamedTextColor.WHITE + " 의 팀이 변경되었습니다.    " + NamedTextColor.BLUE + teamNameOld + NamedTextColor.WHITE + " > " + NamedTextColor.DARK_AQUA + teamName));
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
                                Bukkit.broadcast(Component.text("플레이어 " + NamedTextColor.RED + playerName + NamedTextColor.WHITE + " (이)가 팀 " + NamedTextColor.DARK_AQUA + teamName + NamedTextColor.WHITE + " 에 등록되었습니다."));
                            } else //플레이어 팀 변경
                            {
                                Bukkit.broadcast(Component.text("플레이어 " + NamedTextColor.RED + playerName + NamedTextColor.WHITE + " 의 팀이 변경되었습니다.    " + NamedTextColor.BLUE + teamNameOld + NamedTextColor.WHITE + " > " + NamedTextColor.DARK_AQUA + teamName));
                            }
                        } else
                            sender.sendMessage(data[i] + " 해당하는 유저가 없습니다.");
                    }
                }


            } else {
                sender.sendMessage(NamedTextColor.YELLOW + ("/t  team(t)   ") + NamedTextColor.AQUA + ("<TeamName>  ") + NamedTextColor.RED + ("<Player>  ") + NamedTextColor.WHITE + ("플레이어를 팀에 등록합니다."));
                sender.sendMessage("한번에 다수의 플레이어를 한 팀에 등록 할 수 있습니다.");
                sender.sendMessage("ex) /t t  팀   플레이어1   플레이어2   플레이어3");

                sender.sendMessage(NamedTextColor.YELLOW + "/t  team(t)   random" + NamedTextColor.BLUE + ("<TeamName1>  ") + NamedTextColor.RED + ("<TeamName2>  "));
                sender.sendMessage("무작위로 팀을 배정합니다");
                sender.sendMessage("ex) /t t  random   팀이름1   팀이름2 ");
            }
        }
    }
}
