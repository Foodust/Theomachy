package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class PlayerModule {
    private static final Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

    public static void setPlayerMessage(Player player, String teamName, String prefix) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
            team.addEntry(player.getName());
        }
        team.setPrefix(prefix);
    }

    public static void removePlayerPrefix(Player player) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        // 플레이어의 이름을 표시하는 팀 가져오기
        Team team = scoreboard.getTeam(player.getName());
        if (team != null) {
            team.setPrefix("");
        }
    }

    public static void setScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setScoreboard(player,DisplaySlot.BELOW_NAME ,TheomachyMessage.SCOREBOARD_HEALTH.getMessage());
        }
    }

    public static void setScoreboard(Player player, DisplaySlot displaySlot , String message) {
        player.getScoreboard().clearSlot(displaySlot);
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(TheomachyMessage.SCOREBOARD_HEALTH_BAR.getMessage(), Criteria.HEALTH, message);
        objective.setDisplaySlot(displaySlot);
        objective.getScore(player.getName()).setScore((int) player.getHealth());
        player.setScoreboard(scoreboard);
    }
}
