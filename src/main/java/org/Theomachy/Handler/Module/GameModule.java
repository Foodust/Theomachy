package org.Theomachy.Handler.Module;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Data.ServerSetting;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Timer.GameReadyTimer;
import org.Theomachy.Timer.TipTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.List;

public class GameModule {
    public static boolean Ready = false;
    public static boolean Start = false;

    public static void startGame(CommandSender sender){
        Ready = true;
        Bukkit.broadcastMessage(ChatColor.GOLD + "관리자(" + sender.getName() + TheomachyMessage.INFO_ADMIN_START_GAME.getMessage());
        Theomachy.tasks.add(CommonModule.startTimerTask(new GameReadyTimer(), 0L,  Theomachy.FAST_START ? 1L : 20L));
        Theomachy.tasks.add(CommonModule.startTimerTask(new TipTimer(), 0L, 20L));
        Theomachy.tasks.add(CommonModule.startTimerTask(new CoolTimeTimer(), 0L, 20L));
    }
    public static void stopGame(CommandSender sender){
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
}
