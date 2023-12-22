package org.Theomachy.Handler.Module;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Data.ServerSetting;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Timer.GameReadyTimer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GameModule {
    public static boolean Ready = false;
    public static boolean Start = false;
    private final CommonModule commonModule = new CommonModule();


    public void startGame(CommandSender sender) {
        Ready = true;
        Bukkit.broadcastMessage(ChatColor.GOLD + "관리자(" + sender.getName() + TheomachyMessage.INFO_ADMIN_START_GAME.getMessage());
        Theomachy.tasks.add(commonModule.startTimerTask(new GameReadyTimer(), 0L, Theomachy.FAST_START ? 1L : 20L));
        Theomachy.tasks.add(commonModule.startTimerTask(new CoolTimeTimer(), 0L, 20L));
    }

    public void stopGame(CommandSender sender) {
        Collection<Ability> playerAbilityList = GameData.playerAbility.values();
        for (Ability e : playerAbilityList)
            e.initializeReset();
        GameModule.Ready = false;
        GameModule.Start = false;
        CoolTimeTimer.init = true;
        List<World> worlds = Bukkit.getWorlds();
        for (World world : worlds) {
            world.setPVP(ServerSetting.PVP);
            world.setSpawnFlags(ServerSetting.MONSTER, ServerSetting.ANIMAL);
            world.setAutoSave(ServerSetting.AUTO_SAVE);
            world.setDifficulty(ServerSetting.DIFFICULTY);
        }
        Bukkit.broadcastMessage(ChatColor.RED + "관리자(" + sender.getName() + TheomachyMessage.INFO_ADMIN_STOP_GAME.getMessage());
    }

    public void saveWorld(Location location) {
        GameData.allWorld.clear();
        Bukkit.broadcastMessage(TheomachyMessage.INFO_SAVE_THE_WORLD.getMessage());
        for (int x = (int) -location.getX() - 200; x <= (int) location.getX() + 200; x++) {
            for (int z = (int) -location.getZ() - 200; z <= (int) location.getZ() + 200; z++) {
                for (int y = (int) -location.getY() - 200; y <= (int) location.getY() + 200; y++) {
                    Location blockLocation = location.clone().add(x, y, z);
                    Block block = blockLocation.getBlock();
                    GameData.allWorld.put(blockLocation, block.getType());
                }
            }
        }
        Bukkit.broadcastMessage(TheomachyMessage.INFO_DONE.getMessage());
    }

    public void reloadWorld() {
        Bukkit.broadcastMessage(TheomachyMessage.INFO_RELOAD_THE_WORLD.getMessage());
        for (Map.Entry<Location, Material> entry : GameData.allWorld.entrySet()) {
            Location loc = entry.getKey();
            Material originalType = entry.getValue();
            loc.getBlock().setType(originalType); // 원래의 블록 타입으로 되돌림
        }
        Bukkit.broadcastMessage(TheomachyMessage.INFO_DONE.getMessage());
    }

    public void giveItem(Player player, Material material, int amount) {
        player.getInventory().addItem(new ItemStack(material, amount));
    }
}
