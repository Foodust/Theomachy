package org.Theomachy.Handler.Command;

import java.util.ArrayList;
import java.util.List;

import org.Theomachy.Enum.CommonMessage;
import org.Theomachy.Handler.Module.GamblingModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.Theomachy.Theomachy;

public class GamblingCommand {
    public static void Module(CommandSender sender) {
        Player p = (Player) sender;
        if (Theomachy.GAMBLING) {
            p.openInventory(GamblingModule.gui());
        } else {
            p.sendMessage(ChatColor.RED + "이 기능은 잠겨있습니다!");
        }
    }
}
