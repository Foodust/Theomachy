package org.Theomachy.Handler.Module.game;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.AbilityCoolTimeMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerModule {
    private final GameModule gameModule = new GameModule();
    private final AbilityCoolTimeMessage abilityCoolTimeMessage = new AbilityCoolTimeMessage();

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
    public boolean InHandItemCheck(Player player, Material material) {
        return player.getInventory().getItemInMainHand().getType() == material;
    }

    public boolean ItemCheck(Player player, Material material, int stack) {
        Inventory inventory = player.getInventory();
        if (inventory.contains(material, stack))
            return true;
        else
        {
            abilityCoolTimeMessage.LackItemError(player, material, stack);
            return false;
        }
    }

    public void ItemRemove(Player player, Material material, int stack)
    {
        Inventory inventory = player.getInventory();
        inventory.removeItem(new ItemStack(material, stack));
    }

    public void giveStartingItem(Player player)
    {
        if (Theomachy.STARTING_INVENTORY_CLEAR) {
            gameModule.clearItem(player);
        }
        if (Theomachy.STARTING_GIVE_ITEM){
            GameData.startItems.forEach(itemStack -> {
                gameModule.giveItem(player, itemStack.getType(), itemStack.getAmount());
            });
        }
    }
    public void damageNearEntity(Player player,Location location, float damage, int x, int y, int z){
        for (Entity entity : player.getWorld().getNearbyEntities(location, x, y ,z )) {
            if (entity instanceof LivingEntity && !entity.equals(player)) {
                ((LivingEntity) entity).damage(damage, player);
            }
        }
    }
}
