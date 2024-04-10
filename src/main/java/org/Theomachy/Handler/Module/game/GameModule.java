package org.Theomachy.Handler.Module.game;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Data.ServerSetting;
import org.Theomachy.Data.TickData;
import org.Theomachy.Handler.Module.source.CommonModule;
import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Handler.Module.source.TaskModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Timer.GameReadyTimer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GameModule {
    public static boolean Ready = false;
    public static boolean Start = false;
    private final MessageModule messageModule =new MessageModule();
    private final TaskModule taskModule = new TaskModule();

    public void startGame(CommandSender sender) {
        Ready = true;
        Bukkit.broadcastMessage(ChatColor.GOLD + "관리자(" + sender.getName() + TheomachyMessage.INFO_ADMIN_START_GAME.getMessage());
        Theomachy.tasks.add(taskModule.runBukkitTaskTimer(new GameReadyTimer(), 0L, Theomachy.FAST_START ? 1L : TickData.longTick));
        Theomachy.tasks.add(taskModule.runBukkitTaskTimer(new CoolTimeTimer(), 0L, TickData.longTick));
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
        messageModule.broadcastMessage(ChatColor.RED + "관리자(" + sender.getName() + TheomachyMessage.INFO_ADMIN_STOP_GAME.getMessage());
    }
    public void stopGame() {
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
        messageModule.broadcastMessage(TheomachyMessage.INFO_STOP_GAME.getMessage());
    }

    public void saveWorld(Location location) {
        GameData.allWorld.clear();
        messageModule.broadcastMessage(TheomachyMessage.INFO_SAVE_THE_WORLD.getMessage());
        for (int x = (int) -location.getX() - 200; x <= (int) location.getX() + 200; x++) {
            for (int z = (int) -location.getZ() - 200; z <= (int) location.getZ() + 200; z++) {
                for (int y = (int) -location.getY() - 200; y <= (int) location.getY() + 200; y++) {
                    Location blockLocation = location.clone().add(x, y, z);
                    Block block = blockLocation.getBlock();
                    GameData.allWorld.put(blockLocation, block.getType());
                }
            }
        }
        messageModule.broadcastMessage(TheomachyMessage.INFO_DONE.getMessage());
    }

    public void reloadWorld() {
        messageModule.broadcastMessage(TheomachyMessage.INFO_RELOAD_THE_WORLD.getMessage());
        for (Map.Entry<Location, Material> entry : GameData.allWorld.entrySet()) {
            Location loc = entry.getKey();
            Material originalType = entry.getValue();
            loc.getBlock().setType(originalType); // 원래의 블록 타입으로 되돌림
        }
        messageModule.broadcastMessage(TheomachyMessage.INFO_DONE.getMessage());
    }

    public void giveItem(Player player, Material material, int amount) {
        player.getInventory().addItem(new ItemStack(material, amount));
    }
    public  void clearItem(Player player){
        Inventory inventory = player.getInventory();
        inventory.clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    // 맨손과 같은 기능을 가지는 도구인지 확인하는 메서드
    public boolean isTool(Material material) {
        return material == Material.WOODEN_PICKAXE ||
                material == Material.STONE_PICKAXE ||
                material == Material.IRON_PICKAXE ||
                material == Material.GOLDEN_PICKAXE ||
                material == Material.DIAMOND_PICKAXE;
        // 여기에 다른 도구 종류도 추가할 수 있습니다.
    }
}
