package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.game.GamblingModule;
import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Theomachy;

public class GamblingCommand  {
    private final GamblingModule gamblingModule = new GamblingModule();
    private final MessageModule messageModule = new MessageModule();
    public void module(CommandSender sender) {
        Player player = (Player) sender;
        if (Theomachy.GAMBLING) {
            gamblingModule.openGamblingInventory(player);
        } else {
            messageModule.sendPlayer(player, TheomachyMessage.ERROR_DOES_NOT_ALLOW_GAMBLING.getMessage());
        }
    }
}
