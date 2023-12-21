package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerModule {

    public static void setOnlinePlayer(){
        for(Player player: Bukkit.getOnlinePlayers())
            GameData.onlinePlayer.put(player.getName(),player);
    }
    public static void removeOnlinePlayer(){
        GameData.onlinePlayer.clear();
    }

    public static Player getOnlinePlayerOnce(String playerName){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getName().equals(playerName))
                return player;
        }
        return null;
    }
    public static List<Player> getOnlinePlayer(){
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

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
        Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().clearSlot(DisplaySlot.BELOW_NAME);
//        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
//        Team team = scoreboard.getTeam(player.getName());
//        if (team != null) {
//            team.setPrefix("");
//        }
    }

    public static void setHealthScoreBoard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setHealthScoreBoard(player,DisplaySlot.BELOW_NAME, Criteria.HEALTH,TheomachyMessage.SCOREBOARD_HEALTH_BAR.getMessage(),TheomachyMessage.SCOREBOARD_HEALTH.getMessage());
        }
    }

    public static void setHealthScoreBoard(Player player, DisplaySlot displaySlot,Criteria criteria ,String name ,  String displayName) {
        player.getScoreboard().clearSlot(displaySlot);
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(name,criteria, displayName);
        objective.setDisplaySlot(displaySlot);
        objective.getScore(player.getName()).setScore((int) player.getHealth());
        player.setScoreboard(scoreboard);
    }
}
