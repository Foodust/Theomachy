package org.Theomachy.Handler.Module;

import org.Theomachy.Data.GameData;
import org.Theomachy.Message.AbilityCoolTimeMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerModule {
    private final GameModule gameModule = new GameModule();

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
    public boolean InHandItemCheck(Player player, Material material)
    {
        return player.getInventory().getItemInMainHand().getType() == material;
    }

    public boolean ItemCheck(Player player, Material material, int stack)
    {
        Inventory inventory = player.getInventory();
        if (inventory.contains(material, stack))
            return true;
        else
        {
            AbilityCoolTimeMessage.LackItemError(player, material, stack);
            return false;
        }
    }

    public void ItemRemove(Player player, Material material, int stack)
    {
        Inventory inventory = player.getInventory();
        inventory.removeItem(new ItemStack(material, stack));
    }

    public void startItem(Player player)
    {
        Inventory inventory = player.getInventory();
        if (Theomachy.STARTING_INVENTORY_CLEAR)
        {
            inventory.clear();
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setBoots(new ItemStack(Material.AIR));
        }
        if (Theomachy.STARTING_GIVE_ITEM)
        {
            // 원본
            gameModule.giveItem(player,Material.CHEST, 1);

            inventory.addItem(new ItemStack(Material.CHEST, 1));
            inventory.addItem(new ItemStack(Material.LAVA_BUCKET, 1));
            inventory.addItem(new ItemStack(Material.ICE, 2));
            inventory.addItem(new ItemStack(Material.OAK_PLANKS, 3));
            inventory.addItem(new ItemStack(Material.WHEAT, 9));
            inventory.addItem(new ItemStack(Material.BONE_MEAL, 3));
        }
    }
    public void damageNearEntity(World world, Location location, Player player, float damage, int x, int y, int z){
        for (Entity entity : world.getNearbyEntities(location, x, y ,z )) {
            if (entity instanceof LivingEntity && !entity.equals(player)) {
                ((LivingEntity) entity).damage(damage, player);
            }
        }
    }
}
