package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnModule  {
    public void spawnSetting(CommandSender sender, String[] data){
        if(data.length==1)
        {
            Player player=(Player)sender;
            World world=player.getWorld();
            Location location=player.getLocation();
            world.setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            player.sendMessage(TheomachyMessage.INFO_SETTING_THIS_POSITION_DEFAULT_SPAWN_POINT.getMessage());
        }
        else if(data.length == 5){
            World world = Bukkit.getWorlds().get(0);
            String teamName=data[1];
            Location location = new Location(world,Double.parseDouble(data[2]),Double.parseDouble(data[3]),Double.parseDouble(data[4]));
            GameData.spawnArea.put(teamName, location);
            Bukkit.broadcastMessage( ChatColor.WHITE + data[2]+" " + data[3]+ " " + data[4] + "가 팀 "+ChatColor.DARK_AQUA+ teamName + TheomachyMessage.INFO_SETTING_THIS_POSITION_TEAM_SPAWN_POINT.getMessage());
        }
        else if(data.length == 2)
        {
            Player player=(Player)sender;
            String teamName=data[1];
            Location location=player.getLocation().getBlock().getLocation();
            location.setX(location.getX()+0.5);
            location.setZ(location.getZ()+0.5);
            location.setYaw((int)player.getLocation().getYaw());
            GameData.spawnArea.put(teamName, location);
            player.sendMessage("현재 위치가 팀 " + ChatColor.DARK_AQUA + teamName + TheomachyMessage.INFO_SETTING_THIS_POSITION_TEAM_SPAWN_POINT.getMessage());
        }
        else{
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_SPAWN.getMessage());
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_SPAWN_X_Y_Z.getMessage());
            sender.sendMessage(TheomachyMessage.EXPLAIN_HOW_TO_SET_SPAWN_X_Y_Z_EXAMPLE.getMessage());
        }
    }
}
