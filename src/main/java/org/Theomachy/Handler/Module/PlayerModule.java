package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class PlayerModule {

    public void setOnlinePlayer() {
        for (Player player : Bukkit.getOnlinePlayers())
            GameData.onlinePlayer.put(player.getName(), player);
    }

    public Player getOnlinePlayerOnce(String playerName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(playerName))
                return player;
        }
        return null;
    }

    public List<Player> getOnlinePlayer() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    public void setHealthBar(Player player, String text) {
        Location location = player.getLocation();
        ArmorStand healthBar = (ArmorStand) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND); //Spawn the ArmorStand
        healthBar.setGravity(false); //Make sure it doesn't fall
        healthBar.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
        healthBar.setCustomName(text); //Set this to the text you want
        healthBar.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
        healthBar.setVisible(false); //Makes the ArmorStand invisible
        GameData.playerHealthBar.put(player.getName(), healthBar);
    }

    public void runHealthBar() {
        Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ArmorStand armorStand = GameData.playerHealthBar.get(player.getName());
                if (armorStand == null)
                    continue;
                else {
                    armorStand.setCustomName(String.valueOf(player.getHealth()));
                    armorStand.teleport(player.getLocation());
                }
            }
        }, 0, 1L);
    }
}
