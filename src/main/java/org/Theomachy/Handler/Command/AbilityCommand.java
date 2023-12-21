package org.Theomachy.Handler.Command;

import org.Theomachy.Checker.PermissionChecker;
import org.Theomachy.Handler.Module.CommonModule;
import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Utility.DefaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AbilityCommand extends DefaultUtil {
    public void abilityCollTimeClear(CommandSender sender)
    {
        if (PermissionChecker.Sender(sender))
        {
            CoolTimeTimer.init =true;
            Bukkit.broadcastMessage(TheomachyMessage.INFO_COOL_TIME_CLEAR.getMessage());
        }
    }
    public void abilityHelp(CommandSender sender) {
        abilityModule.openAbilityHelpInventory((Player) sender);
    }
    public void abilityList(CommandSender sender) {
        if (sender instanceof Player)
            abilityModule.showCode(sender);
        else
            Theomachy.log.info(TheomachyMessage.ERROR_THIS_COMMAND_EXECUTE_IN_GAME.getMessage());
    }

    public void abilitySet(CommandSender sender, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            if (!GameModule.Ready) {
                // ability
                if (data.length <= 1) {
                    abilityModule.explainCommand(sender);
                }// ability ?
                else if (data.length == 2) {
                    TheomachyMessage byMessage = TheomachyMessage.getByMessage(data[1]);
                    switch (byMessage) {
                        case COMMAND_HELP -> abilityList(sender);
                        case COMMAND_RESET -> abilityModule.Reset();
                        case COMMAND_RANDOM -> abilityModule.RandomAssignment(sender);
                        default -> abilityModule.errorMessage(sender);
                    }
                }// ability ? or remove name
                else if (data.length == 3) {
                    if (Objects.equals(data[1], TheomachyMessage.COMMAND_REMOVE.getMessage())) {
                        abilityModule.remove(sender, data[2]);
                    } else if (commonModule.isNumeric(data[1])) {
                        abilityModule.forceAssignment(sender, data);
                    } else {
                        abilityModule.errorMessage(sender);
                    }
                }
            }
        } else
            sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_CHANGE_ABILITY_IN_GAME.getMessage());
    }
}
