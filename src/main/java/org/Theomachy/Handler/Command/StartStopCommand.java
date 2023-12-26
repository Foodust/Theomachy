package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.CommandSender;

import org.Theomachy.Theomachy;
import org.Theomachy.Checker.PermissionChecker;

public class StartStopCommand  {
    private final GameModule gameModule = new GameModule();
    private final PermissionChecker permissionChecker = new PermissionChecker();
    public void GameReady(CommandSender sender) {
        if (permissionChecker.Sender(sender)) {
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
        if (permissionChecker.Sender(sender)) {
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
