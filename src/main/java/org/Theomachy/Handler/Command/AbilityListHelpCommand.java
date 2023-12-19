package org.Theomachy.Handler.Command;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Handler.Module.AbilityModule;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Theomachy;

public class AbilityListHelpCommand {

    public static void ShowAbilityCodeNumber(CommandSender sender) {
        if (sender instanceof Player)
            AbilityModule.showCode(sender);
        else
            Theomachy.log.info(TheomachyMessage.ERROR_THIS_COMMAND_EXECUTE_IN_GAME.getMessage());
    }

}
