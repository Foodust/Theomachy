package org.Theomachy.Handler.Module;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class PlayerModule {

    public static void setPlayerMessage(Player player,String teamName ,String prefix) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
            team.addEntry(player.getName());
        }
        // 접두사 설정
//        team.setPrefix(prefix);
    }

    public static void removePlayerPrefix(Player player) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        // 플레이어의 이름을 표시하는 팀 가져오기
        Team team = scoreboard.getTeam(player.getName());
        if (team != null) {
            team.setPrefix("");
        }
    }
}
