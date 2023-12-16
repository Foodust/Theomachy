package org.Theomachy.Handler.CommandModule;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Theomachy;

import java.util.EnumSet;

public class AbilityHelpCommand {

    public static void ShowAbilityCodeNumber(CommandSender sender) {
        if (sender instanceof Player)
            showCode(sender);
        else
            Theomachy.log.info("이 명령어는 게임에서 실행해 주십시오.");
    }

    private static void showCode(CommandSender sender) {

        sender.sendMessage(ChatColor.GOLD + " 【 신 】 ");
        for (AbilityInfo abilityInfo : EnumSet.range(AbilityInfo.Zeus, AbilityInfo.Sans)) {
            sender.sendMessage(ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }
        sender.sendMessage(ChatColor.AQUA + " 【 인간 】 ");
        for (AbilityInfo abilityInfo : EnumSet.range(AbilityInfo.Archer, AbilityInfo.Zet)) {
            sender.sendMessage(ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }
        sender.sendMessage(ChatColor.GREEN + " 【 주술회전 】 ");
        for (AbilityInfo abilityInfo : EnumSet.range(AbilityInfo.Itarodi, AbilityInfo.Jogo)) {
            sender.sendMessage(ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }
        sender.sendMessage(ChatColor.RED + " 【 귀멸의 칼날 】 ");
        for (AbilityInfo abilityInfo : EnumSet.range(AbilityInfo.Zenitsu, AbilityInfo.Zenitsu)) {
            sender.sendMessage(ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }

    }
}
