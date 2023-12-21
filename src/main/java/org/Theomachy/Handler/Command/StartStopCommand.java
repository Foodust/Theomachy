package org.Theomachy.Handler.Command;

import java.util.Collection;
import java.util.List;
import java.util.Timer;

import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Utility.DefaultUtil;
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

public class StartStopCommand  {
    private final GameModule gameModule = new GameModule();

    public void GameReady(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            if (!GameModule.Ready) {
                if(Theomachy.DEBUG) {
                    sender.sendMessage(TheomachyMessage.ERROR_DEBUG_IS_ON.getMessage());
                }else{
                    gameModule.startGame(sender);
                }
            } else
                sender.sendMessage(TheomachyMessage.ERROR_GAME_ALREADY_STARTED.getMessage());
        }
    }

    public void GameStop(CommandSender sender) {
        if (PermissionChecker.Sender(sender)) {
            if(Theomachy.DEBUG){
                sender.sendMessage(TheomachyMessage.ERROR_DEBUG_IS_ON.getMessage());
            }
            else if (GameModule.Ready) {
                gameModule.stopGame(sender);
            } else
                sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_START_GAME.getMessage());
        }
    }
}
