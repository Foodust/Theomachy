package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

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

    public ArmorStand setArmorStand(Player player, String text) {
        Location location = player.getLocation();
        ArmorStand armorStand = (ArmorStand) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND); //Spawn the ArmorStand
        armorStand.setGravity(false); //Make sure it doesn't fall
        armorStand.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
        armorStand.setCustomName(text); //Set this to the text you want
        armorStand.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
        armorStand.setVisible(false); //Makes the ArmorStand invisible
        return armorStand;
    }

    public void moveArmorStand(Player player,ArmorStand armorStand) {
        if (armorStand == null)
            return;
        else {
            armorStand.setCustomName(String.valueOf(player.getHealth()));
            armorStand.teleport(player.getLocation());
        }
    }

}
