package org.Theomachy.Handler.Module.source;

import org.Theomachy.Data.GameData;
import org.Theomachy.Data.VersionData;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingModule {
    static int settingSize = 2 * 9;
    static List<BukkitTask> taskList = new ArrayList<>();
    private final CommonModule commonModule = new CommonModule();
    private final ItemModule itemModule = new ItemModule();
    private final ConfigModule configModule = new ConfigModule();
    private final MessageModule messageModule =new MessageModule();
    private final TaskModule taskModule = new TaskModule();

    public void openSettingInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, settingSize, TheomachyMessage.SETTING.getMessage());
        inventory.setItem(0, itemModule.setItem(Theomachy.STARTING_INVENTORY_CLEAR ? Material.WHITE_WOOL : Material.RED_WOOL, TheomachyMessage.SETTING_STARTING_INVENTORY_CLEAR.getMessage(),1));
        inventory.setItem(2, itemModule.setItem(Theomachy.STARTING_GIVE_ITEM ? Material.WHITE_WOOL : Material.RED_WOOL,  TheomachyMessage.SETTING_STARTING_GIVE_ITEM.getMessage(),1));
        inventory.setItem(4, itemModule.setItem(Theomachy.STARTING_ENTITY_CLEAR ? Material.WHITE_WOOL : Material.RED_WOOL,  TheomachyMessage.SETTING_STARTING_ENTITY_CLEAR.getMessage(),1));
        inventory.setItem(6, itemModule.setItem(Theomachy.IGNORE_BED ? Material.WHITE_WOOL : Material.RED_WOOL, TheomachyMessage.SETTING_IGNORE_BED.getMessage(),1));
        inventory.setItem(8, itemModule.setItem(Theomachy.FAST_START ? Material.WHITE_WOOL : Material.RED_WOOL, TheomachyMessage.SETTING_FAST_START.getMessage(),1));
        inventory.setItem(9, itemModule.setItem(Theomachy.SERVER_AUTO_SAVE ? Material.WHITE_WOOL : Material.RED_WOOL, TheomachyMessage.SETTING_SERVER_AUTO_SAVE.getMessage(),1));
        inventory.setItem(11, itemModule.setItem(Theomachy.ANIMAL_SPAWN ? Material.WHITE_WOOL : Material.RED_WOOL,  TheomachyMessage.SETTING_ANIMAL_SPAWN.getMessage(),1));
        inventory.setItem(13, itemModule.setItem(Theomachy.MONSTER_SPAWN ? Material.WHITE_WOOL : Material.RED_WOOL, TheomachyMessage.SETTING_MONSTER_SPAWN.getMessage(),1));
        inventory.setItem(15, itemModule.setItem(Theomachy.GAMBLING ? Material.WHITE_WOOL : Material.RED_WOOL,  TheomachyMessage.SETTING_GAMBLING_ACCEPT.getMessage(),1));
        inventory.setItem(17, itemModule.setItem(Theomachy.DEBUG ? Material.WHITE_WOOL : Material.RED_WOOL,  TheomachyMessage.SETTING_DEBUG_MODE.getMessage(),1));
        player.openInventory(inventory);
    }

    public void serverSetting(ItemStack wool) {
        TheomachyMessage byMessage = TheomachyMessage.getByMessage(Objects.requireNonNull(wool.getItemMeta()).getDisplayName());
        String broadcastMessage = byMessage.getMessage();
        switch (Objects.requireNonNull(byMessage)) {
            case SETTING_STARTING_INVENTORY_CLEAR -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.STARTING_INVENTORY_CLEAR = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.STARTING_INVENTORY_CLEAR = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_STARTING_GIVE_ITEM -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.STARTING_GIVE_ITEM = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.STARTING_GIVE_ITEM = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_STARTING_ENTITY_CLEAR -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.STARTING_ENTITY_CLEAR = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.STARTING_ENTITY_CLEAR = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_IGNORE_BED -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.IGNORE_BED = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.IGNORE_BED = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_FAST_START -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.FAST_START = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.FAST_START = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_SERVER_AUTO_SAVE -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.SERVER_AUTO_SAVE = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.SERVER_AUTO_SAVE = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_ANIMAL_SPAWN -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.ANIMAL_SPAWN = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.ANIMAL_SPAWN = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_MONSTER_SPAWN -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.MONSTER_SPAWN = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.MONSTER_SPAWN = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_GAMBLING_ACCEPT -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.GAMBLING = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.GAMBLING = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_DEBUG_MODE -> {
                if (checkWhiteWool(wool.getType(), broadcastMessage)) {
                    Theomachy.DEBUG = false;
                    wool.setType(Material.RED_WOOL);
                    for (BukkitTask task : taskList) {
                        task.cancel();
                    }
                    taskList.clear();
                } else {
                    Theomachy.DEBUG = true;
                    wool.setType(Material.WHITE_WOOL);
//                    taskList.add(taskModule.runBukkitTaskTimer(new CoolTimeTimer(), 0L, TickData.longTick));
                }
            }
        }
    }

    public boolean checkWhiteWool(Material material, String message) {
        if (material == Material.WHITE_WOOL) {
            message += TheomachyMessage.INFO_DISABLED.getMessage();
        } else {
            message += TheomachyMessage.INFO_ABLE.getMessage();
        }
        messageModule.broadcastMessage(ChatColor.WHITE + message + TheomachyMessage.INFO_SET_MESSAGE.getMessage());
        return material == Material.WHITE_WOOL;
    }
    public void defaultSettingAndLogMessage(Plugin plugin){
        // ability Count
        messageModule.logInfo(VersionData.name + TheomachyMessage.INFO_LIST_OF_ACCEPT_ABILITY.getMessage());
        messageModule.logInfo(VersionData.name + TheomachyMessage.INFO_GOD.getMessage() + " : " + BlacklistModule.godCanlist.size());
        messageModule.logInfo(VersionData.name + TheomachyMessage.INFO_HUMAN.getMessage() + " : " + BlacklistModule.humanCanlist.size());
        messageModule.logInfo(VersionData.name + TheomachyMessage.INFO_JUJUTSU_KAISEN.getMessage() + " : " + BlacklistModule.jujutsuCanList.size());
        messageModule.logInfo(VersionData.name + TheomachyMessage.INFO_KIMETSU_NO_YAIBA.getMessage() + " : " + BlacklistModule.kimetsuCanlist.size());
        messageModule.logInfo(VersionData.name + TheomachyMessage.INFO_TOTAL_COUNT.getMessage() +  " : " + String.valueOf(
                BlacklistModule.godCanlist.size() + BlacklistModule.humanCanlist.size() +
                        BlacklistModule.jujutsuCanList.size() + BlacklistModule.kimetsuCanlist.size()));

        messageModule.logInfo(TheomachyMessage.INFO_PLUGIN_DEFAULT_SETTING.getMessage());
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
        messageModule.logInfo(TheomachyMessage.INFO_BAR.getMessage());
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_STARTING_INVENTORY_CLEAR.getMessage() + " : " + Theomachy.STARTING_INVENTORY_CLEAR);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_STARTING_GIVE_ITEM.getMessage() + " : " + Theomachy.STARTING_GIVE_ITEM);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_STARTING_ENTITY_CLEAR.getMessage() + " : " + Theomachy.STARTING_ENTITY_CLEAR);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_IGNORE_BED.getMessage() + " : " + Theomachy.IGNORE_BED);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_FAST_START.getMessage() +" : " + Theomachy.FAST_START);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_GAMBLING.getMessage() + " : " + Theomachy.GAMBLING);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_SERVER_AUTO_SAVE.getMessage() +" : " + Theomachy.SERVER_AUTO_SAVE);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_ANIMAL_SPAWN.getMessage() + " : " + Theomachy.ANIMAL_SPAWN);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_MONSTER_SPAWN.getMessage() + " : " + Theomachy.MONSTER_SPAWN);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_DIFFICULT.getMessage() + " : " + Theomachy.DIFFICULTY);
        messageModule.logInfo(VersionData.name + TheomachyMessage.SETTING_DEBUG_MODE.getMessage() + " : " + Theomachy.DEBUG);
        messageModule.logInfo(TheomachyMessage.INFO_BAR.getMessage());

        // developer
        Bukkit.getConsoleSender().sendMessage(VersionData.firstAuthor);
        Bukkit.getConsoleSender().sendMessage(VersionData.secondAuthor);
        Bukkit.getConsoleSender().sendMessage(VersionData.thirdAuthor);
    }

    public void settingStartItem(){
        FileConfiguration config = configModule.getConfig("config.yml");
        String mainSection = TheomachyMessage.SETTING_STARING_ITEM.getMessage();
        for (String materialName : Objects.requireNonNull(config.getConfigurationSection(mainSection)).getKeys(false)) {
            int amount = config.getInt(mainSection +"."+ materialName, 1);
            Material material = Material.valueOf(materialName.toUpperCase());
            ItemStack itemStack = itemModule.setItem(material, material.name(), amount);
            GameData.startItems.add(itemStack);
        }
    }
}
