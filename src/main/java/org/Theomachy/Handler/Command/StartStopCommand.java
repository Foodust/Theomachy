package org.Theomachy.Handler.Command;

import java.util.Collection;
import java.util.List;
import java.util.Timer;

import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Data.ServerSetting;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Timer.GameReadyTimer;
import org.Theomachy.Timer.TipTimer;
import org.Theomachy.Checker.PermissionChecker;
import org.bukkit.scheduler.BukkitTask;

public class StartStopCommand {
    public static boolean Ready = false;
    public static boolean Start = false;

    public static void GameReady(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            if (!Ready) {
                if(Theomachy.DEBUG) {
                    Bukkit.broadcastMessage(TheomachyMessage.ERROR_DEBUG_IS_ON.getMessage());
                    return;
                }
                Ready = true;
                Bukkit.broadcastMessage(ChatColor.GOLD + "관리자(" + sender.getName() + ") 가 게임을 시작하였습니다.");
                Timer t = new Timer();
                if (!Theomachy.FAST_START) {
                    GameReadyTimer gameReadyTimer = new GameReadyTimer();
                    BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), gameReadyTimer, 0, 20L);
                    Theomachy.tasks.add(bukkitTask);
                } else {
                    GameReadyTimer gameReadyTimer = new GameReadyTimer();
                    BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), gameReadyTimer, 0, 2L);
                    Theomachy.tasks.add(bukkitTask);
                }
                TipTimer tipTimer = new TipTimer();
                BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(),tipTimer , 0L, 20L);
                Theomachy.tasks.add(bukkitTask);
                CoolTimeTimer coolTimeTimer = new CoolTimeTimer();
                bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), coolTimeTimer, 0L, 20L);
                Theomachy.tasks.add(bukkitTask);

            } else
                sender.sendMessage(TheomachyMessage.ERROR_GAME_ALREADY_STARTED.getMessage());
        }
    }

    public static void GameStop(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            if (Ready) {
                Collection<Ability> playerAbilityList = GameData.playerAbility.values();
                for (Ability e : playerAbilityList)
                    e.initializeReset();
                Ready = false;
                Start = false;
                CoolTimeTimer.init = true;
                List<World> worlds = Bukkit.getWorlds();
                for (World world : worlds) {
                    world.setPVP(ServerSetting.PVP);
                    world.setSpawnFlags(ServerSetting.MONSTER, ServerSetting.ANIMAL);
                    world.setAutoSave(ServerSetting.AUTO_SAVE);
                    world.setDifficulty(ServerSetting.DIFFICULTY);
                }
                Bukkit.broadcastMessage(ChatColor.RED + "관리자(" + sender.getName() + ") 가 게임을 종료하였습니다.");
            } else
                sender.sendMessage("게임이 시작되지 않았습니다.");
        }
    }
}
