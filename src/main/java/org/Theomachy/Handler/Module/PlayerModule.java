package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerModule  {

    public void setOnlinePlayer(){
        for(Player player: Bukkit.getOnlinePlayers())
            GameData.onlinePlayer.put(player.getName(),player);
    }
    public Player getOnlinePlayerOnce(String playerName){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getName().equals(playerName))
                return player;
        }
        return null;
    }
    public List<Player> getOnlinePlayer(){
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

}
