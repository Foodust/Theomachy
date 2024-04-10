package org.Theomachy.Handler.Module.source;

import org.Theomachy.Data.VersionData;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Handler.Manager.EntityManager;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Firework;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.TimerTask;

public class CommonModule {
    private final EntityManager entityManager = new EntityManager();
    private final MessageModule messageModule = new MessageModule();
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // 숫자로 변환 시도
            return true; // 변환이 성공하면 숫자임
        } catch (NumberFormatException e) {
            return false; // 변환이 실패하면 숫자가 아님
        }
    }

    public ChatColor findColor(String message){
        switch (message.toLowerCase()){
            case "blue" -> {
                return ChatColor.BLUE;
            }
            case "red" -> {
                return ChatColor.RED;
            }
            case "green" -> {
                return ChatColor.GREEN;
            }
            case "yellow" -> {
                return  ChatColor.YELLOW;
            }
        }
        return ChatColor.WHITE;
    }

    public void breakDiamond(BlockBreakEvent event){
        for(int i = 0 ; i < 5; i++){
            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + TheomachyMessage.END_WHO_BREAK_DIAMOND.getMessage());
        }
        Firework firework = event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), Firework.class);
        entityManager.spawnRandomFirework(firework);
    }
    public void settingBlazeRodRecipe(Plugin plugin){
        NamespacedKey customBlazeRodRecipe = new NamespacedKey(plugin, TheomachyMessage.SETTING_CUSTOM_BLASE_LOD_RECIPE.getMessage());
        ShapedRecipe recipe = new ShapedRecipe(customBlazeRodRecipe, new ItemStack(Material.BLAZE_ROD)).shape("|", "|", "|").setIngredient('|', Material.STICK);
        plugin.getServer().addRecipe(recipe);
    }

    public void defaultPluginMessage(){
        messageModule.logInfo(TheomachyMessage.INFO_PLUGIN_ENABLED.getMessage());
        messageModule.logInfo(TheomachyMessage.INFO_PLUGIN_DEFAULT_SETTING.getMessage());
    }

}
