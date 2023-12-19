package org.Theomachy.Handler.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.Theomachy.Enum.TheomachyMessage;
import org.Theomachy.Handler.Module.AbilityModule;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;

public class AbilityHelpCommand {
    public static void Module(CommandSender sender, Command command, String label, String[] data) {
        AbilityModule.openAbilityHelpInventory((Player) sender);
    }
}
