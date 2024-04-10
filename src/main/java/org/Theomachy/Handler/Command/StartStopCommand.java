package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.game.GameModule;
import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.CommandSender;

import org.Theomachy.Theomachy;
import org.Theomachy.Checker.PermissionChecker;

public class StartStopCommand {
    private final MessageModule messageModule = new MessageModule();
    private final GameModule gameModule = new GameModule();
    private final PermissionChecker permissionChecker = new PermissionChecker();

    public void GameReady(CommandSender sender) {
        if (permissionChecker.Player(sender)) {
            if (!GameModule.Ready) {
                if (Theomachy.DEBUG) {
                    messageModule.sendPlayer(sender, TheomachyMessage.ERROR_DEBUG_IS_ON.getMessage());
                } else {
                    gameModule.startGame(sender);
                }
            } else
                messageModule.sendPlayer(sender,TheomachyMessage.ERROR_GAME_ALREADY_STARTED.getMessage());
        }
    }

    public void GameStop(CommandSender sender) {
        if (permissionChecker.Player(sender)) {
            if (Theomachy.DEBUG) {
                messageModule.sendPlayer(sender,TheomachyMessage.ERROR_DEBUG_IS_ON.getMessage());
            } else if (GameModule.Ready) {
                gameModule.stopGame(sender);
            } else
                messageModule.sendPlayer(sender,TheomachyMessage.ERROR_DOES_NOT_START_GAME.getMessage());
        }
    }
}
