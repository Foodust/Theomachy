package org.Theomachy.Handler.Module;

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
    public ItemStack setItem(Material material, int amount, String title) {
        ItemStack prevItem = new ItemStack(material, amount);
        ItemMeta prevItemMeta = prevItem.getItemMeta();
        assert prevItemMeta != null;
        prevItemMeta.setDisplayName(title);
        prevItem.setItemMeta(prevItemMeta);
        return prevItem;
    }
    public void breakDiamond(BlockBreakEvent event){
        for(int i = 0 ; i < 5; i++){
            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + TheomachyMessage.END_WHO_BREAK_DIAMOND.getMessage());
        }
        Firework firework = event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), Firework.class);
        entityManager.spawnRandomFirework(firework);
    }
    public BukkitTask startTimerTask(TimerTask task, long delay, long period){
        return Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(),task , delay,period);
    }

    public void settingBlazeRodRecipe(Plugin plugin){
        NamespacedKey customBlazeRodRecipe = new NamespacedKey(plugin, TheomachyMessage.SETTING_CUSTOM_BLASE_LOD_RECIPE.getMessage());
        ShapedRecipe recipe = new ShapedRecipe(customBlazeRodRecipe, new ItemStack(Material.BLAZE_ROD)).shape("|", "|", "|").setIngredient('|', Material.STICK);
        plugin.getServer().addRecipe(recipe);
    }

    public void defaultPluginMessage(){
        Theomachy.log.info(TheomachyMessage.INFO_PLUGIN_ENABLED.getMessage());
        Theomachy.log.info(TheomachyMessage.INFO_PLUGIN_DEFAULT_SETTING.getMessage());
    }
    public void defaultSettingAndLogMessage(Plugin plugin){
        // ability Count
        Theomachy.log.info(VersionData.name + TheomachyMessage.INFO_LIST_OF_ACCEPT_ABILITY.getMessage());
        Theomachy.log.info(VersionData.name + TheomachyMessage.INFO_GOD.getMessage() + " : " + BlacklistModule.godCanlist.size());
        Theomachy.log.info(VersionData.name + TheomachyMessage.INFO_HUMAN.getMessage() + " : " + BlacklistModule.humanCanlist.size());
        Theomachy.log.info(VersionData.name + TheomachyMessage.INFO_JUJUTSU_KAISEN.getMessage() + " : " + BlacklistModule.jujutsuCanList.size());
        Theomachy.log.info(VersionData.name + TheomachyMessage.INFO_KIMETSU_NO_YAIBA.getMessage() + " : " + BlacklistModule.kimetsuCanlist.size());
        Theomachy.log.info(VersionData.name + TheomachyMessage.INFO_TOTAL_COUNT.getMessage() +  " : " + String.valueOf(
                BlacklistModule.godCanlist.size() + BlacklistModule.humanCanlist.size() +
                        BlacklistModule.jujutsuCanList.size() + BlacklistModule.kimetsuCanlist.size()));

        Theomachy.log.info(TheomachyMessage.INFO_PLUGIN_DEFAULT_SETTING.getMessage());
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();

        // server setting
        Theomachy.STARTING_INVENTORY_CLEAR = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_STARTING_INVENTORY_CLEAR.getMessage()));
        Theomachy.STARTING_GIVE_ITEM = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_STARTING_GIVE_ITEM.getMessage()));
        Theomachy.STARTING_ENTITY_CLEAR = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_STARTING_ENTITY_CLEAR.getMessage()));
        Theomachy.IGNORE_BED = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_IGNORE_BED.getMessage()));
        Theomachy.SERVER_AUTO_SAVE = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_SERVER_AUTO_SAVE.getMessage()));
        Theomachy.ANIMAL_SPAWN = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_ANIMAL_SPAWN.getMessage()));
        Theomachy.MONSTER_SPAWN = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_MONSTER_SPAWN.getMessage()));
        Theomachy.DIFFICULTY = plugin.getConfig().getInt(ChatColor.stripColor(TheomachyMessage.SETTING_DIFFICULT.getMessage()));
        Theomachy.FAST_START = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_FAST_START.getMessage()));
        Theomachy.GAMBLING = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_GAMBLING_ACCEPT.getMessage()));
        Theomachy.DEBUG = plugin.getConfig().getBoolean(ChatColor.stripColor(TheomachyMessage.SETTING_DEBUG_MODE.getMessage()));

        // server setting log
        Theomachy.log.info(TheomachyMessage.INFO_BAR.getMessage());
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_STARTING_INVENTORY_CLEAR.getMessage() + " : " + Theomachy.STARTING_INVENTORY_CLEAR);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_STARTING_GIVE_ITEM.getMessage() + " : " + Theomachy.STARTING_GIVE_ITEM);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_STARTING_ENTITY_CLEAR.getMessage() + " : " + Theomachy.STARTING_ENTITY_CLEAR);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_IGNORE_BED.getMessage() + " : " + Theomachy.IGNORE_BED);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_FAST_START.getMessage() +" : " + Theomachy.FAST_START);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_GAMBLING.getMessage() + " : " + Theomachy.GAMBLING);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_SERVER_AUTO_SAVE.getMessage() +" : " + Theomachy.SERVER_AUTO_SAVE);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_ANIMAL_SPAWN.getMessage() + " : " + Theomachy.ANIMAL_SPAWN);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_MONSTER_SPAWN.getMessage() + " : " + Theomachy.MONSTER_SPAWN);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_DIFFICULT.getMessage() + " : " + Theomachy.DIFFICULTY);
        Theomachy.log.info(VersionData.name + TheomachyMessage.SETTING_DEBUG_MODE.getMessage() + " : " + Theomachy.DEBUG);
        Theomachy.log.info(TheomachyMessage.INFO_BAR.getMessage());

        // developer
        Bukkit.getConsoleSender().sendMessage(VersionData.firstAuthor);
        Bukkit.getConsoleSender().sendMessage(VersionData.secondAuthor);
        Bukkit.getConsoleSender().sendMessage(VersionData.thirdAuthor);
    }
}
