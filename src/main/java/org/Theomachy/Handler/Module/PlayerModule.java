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
    public static void setHealthScoreBoard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setScoreBoard(player,DisplaySlot.BELOW_NAME, Criteria.HEALTH,TheomachyMessage.SCOREBOARD_HEALTH_BAR.getMessage(),TheomachyMessage.SCOREBOARD_HEALTH.getMessage());
        }
    }

    public static void setScoreBoard(Player player, DisplaySlot displaySlot, Criteria criteria , String name , String displayName) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.registerNewObjective(name,criteria, displayName);
        objective.setDisplaySlot(displaySlot);
        objective.getScore(player.getName()).setScore((int) player.getHealth());
        player.setScoreboard(scoreboard);
    }
    public static void removeScoreboard(Player player, String objectiveName) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective(objectiveName);
        if (objective != null) {
            objective.unregister();
        }
    }
}
