package org.Theomachy.Handler.Command;

import org.Theomachy.Handler.Module.AbilityModule;
import org.Theomachy.Handler.Module.CommonModule;
import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.Theomachy.Checker.PermissionChecker;

import java.nio.Buffer;
import java.util.Objects;

public class AbilitySetCommand {
    public static void Module(CommandSender sender, Command command, String label, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            if (!GameModule.Ready) {
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
                    if (Objects.equals(data[1], TheomachyMessage.COMMAND_REMOVE.getMessage())) {
                        AbilityModule.Remove(sender, data[2]);
                    } else if (CommonModule.isNumeric(data[1])) {
                        AbilityModule.forceAssignment(sender, data);
                    } else {
                        AbilityModule.errorMessage(sender);
                    }
                }
            }
        } else
            sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_CHANGE_ABILITY_IN_GAME.getMessage());
    }
}
