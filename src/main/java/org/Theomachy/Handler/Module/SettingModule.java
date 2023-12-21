package org.Theomachy.Handler.Module;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Timer.TipTimer;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingModule  {
    static int settingSize = 2 * 9;
    static List<BukkitTask> taskList = new ArrayList<>();
    private final CommonModule commonModule = new CommonModule();

    public  void openSettingInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, settingSize, TheomachyMessage.SETTING.getMessage());
        inventory.setItem(0, commonModule.setItem(Theomachy.STARTING_INVENTORY_CLEAR ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_STARTING_INVENTORY_CLEAR.getMessage()));
        inventory.setItem(2, commonModule.setItem(Theomachy.STARTING_GIVE_ITEM ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_STARTING_GIVE_ITEM.getMessage()));
        inventory.setItem(4, commonModule.setItem(Theomachy.STARTING_ENTITY_CLEAR ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_STARTING_ENTITY_CLEAR.getMessage()));
        inventory.setItem(6, commonModule.setItem(Theomachy.IGNORE_BED ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_IGNORE_BED.getMessage()));
        inventory.setItem(8, commonModule.setItem(Theomachy.FAST_START ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_FAST_START.getMessage()));
        inventory.setItem(9, commonModule.setItem(Theomachy.SERVER_AUTO_SAVE ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_SERVER_AUTO_SAVE.getMessage()));
        inventory.setItem(11, commonModule.setItem(Theomachy.ANIMAL_SPAWN ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_ANIMAL_SPAWN.getMessage()));
        inventory.setItem(13, commonModule.setItem(Theomachy.MONSTER_SPAWN ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_MONSTER_SPAWN.getMessage()));
        inventory.setItem(15, commonModule.setItem(Theomachy.GAMBLING ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_GAMBLING_ACCEPT.getMessage()));
        inventory.setItem(17, commonModule.setItem(Theomachy.DEBUG ? Material.WHITE_WOOL : Material.RED_WOOL, 1, TheomachyMessage.SETTING_DEBUG_MODE.getMessage()));
        player.openInventory(inventory);
    }

    public  void serverSetting(ItemStack wool) {
        TheomachyMessage byMessage = TheomachyMessage.getByMessage(Objects.requireNonNull(wool.getItemMeta()).getDisplayName());
        String broadcastMessage = byMessage.getMessage();
        switch (Objects.requireNonNull(byMessage)) {
            case SETTING_STARTING_INVENTORY_CLEAR -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.STARTING_INVENTORY_CLEAR = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.STARTING_INVENTORY_CLEAR = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_STARTING_GIVE_ITEM -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.STARTING_GIVE_ITEM = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.STARTING_GIVE_ITEM = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_STARTING_ENTITY_CLEAR -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.STARTING_ENTITY_CLEAR = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.STARTING_ENTITY_CLEAR = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_IGNORE_BED -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.IGNORE_BED = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.IGNORE_BED = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_FAST_START -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.FAST_START = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.FAST_START = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_SERVER_AUTO_SAVE -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.SERVER_AUTO_SAVE = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.SERVER_AUTO_SAVE = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_ANIMAL_SPAWN -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.ANIMAL_SPAWN = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.ANIMAL_SPAWN = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_MONSTER_SPAWN -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.MONSTER_SPAWN = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.MONSTER_SPAWN = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_GAMBLING_ACCEPT -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.GAMBLING = false;
                    wool.setType(Material.RED_WOOL);
                } else {
                    Theomachy.GAMBLING = true;
                    wool.setType(Material.WHITE_WOOL);
                }
            }
            case SETTING_DEBUG_MODE -> {
                if (checkWhiteWool(wool.getType(),broadcastMessage)) {
                    Theomachy.DEBUG = false;
                    GameModule.Start = false;
                    wool.setType(Material.RED_WOOL);
                    for(BukkitTask task : taskList){
                        task.cancel();
                    }
                    taskList.clear();
                } else {
                    Theomachy.DEBUG = true;
                    GameModule.Start = true;
                    wool.setType(Material.WHITE_WOOL);
                    taskList.add(commonModule.startTimerTask(new CoolTimeTimer(), 0L, 20L));
                }
            }
        }
    }

    public  boolean checkWhiteWool(Material material, String message) {
        if (material == Material.WHITE_WOOL) {
            message += TheomachyMessage.INFO_DISABLED.getMessage();
        } else {
            message += TheomachyMessage.INFO_ABLE.getMessage();
        }
        Bukkit.broadcastMessage(message + TheomachyMessage.INFO_SET_MESSAGE.getMessage());
        return material == Material.WHITE_WOOL;
    }
}
