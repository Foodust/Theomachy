package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.AbilityModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Checker.PermissionChecker;

public class AbilitySetCommand {
    public static void Module(CommandSender sender, Command command, String label, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            if (!StartStopCommand.Ready) {
                // ability
                if (data.length <= 1) {
                    AbilityModule.explainCommand(sender);
                }// ability ?
                else if (data.length == 2) {
                    TheomachyMessage byMessage = TheomachyMessage.getByMessage(data[1]);
                    switch (byMessage) {
                        case COMMAND_HELP -> AbilityListHelpCommand.ShowAbilityCodeNumber(sender);
                        case COMMAND_RESET -> AbilityModule.Reset();
                        case COMMAND_RANDOM -> AbilityModule.RandomAssignment(sender);
                        default -> AbilityModule.errorMessage(sender);
                    }
                }// ability ? or remove name
                else if (data.length == 3) {
                    TheomachyMessage byMessage = TheomachyMessage.getByMessage(data[1]);
                    switch (byMessage) {
                        case COMMAND_REMOVE -> AbilityModule.Remove(sender, data[2]);
                        case COMMAND_ABILITY, COMMAND_ABILITY_A -> AbilityModule.forceAssignment(sender, data);
                        default -> AbilityModule.errorMessage(sender);
                    }
                }
            }
        } else
            sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_CHANGE_ABILITY_IN_GAME.getMessage());
    }
}
