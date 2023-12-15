package org.septagram.Theomachy.Utility;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.DB.AbilityData;
import org.septagram.Theomachy.Theomachy;

import java.util.Arrays;
import java.util.EnumSet;

public class CodeHelper {

    public static void ShowAbilityCodeNumber(CommandSender sender) {
        if (sender instanceof Player)
            showCode(sender);
        else
            Theomachy.log.info("이 명령어는 게임에서 실행해 주십시오.");
    }

    private static void showCode(CommandSender sender) {
        sender.sendMessage(NamedTextColor.GOLD + " 【 신 】 ");
        for (AbilityInfo abilityInfo : EnumSet.range(AbilityInfo.Zeus, AbilityInfo.Sans)) {
            sender.sendMessage(NamedTextColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + NamedTextColor.WHITE + abilityInfo.getName());
        }
        sender.sendMessage(NamedTextColor.AQUA + " 【 인간 】 ");
        for (AbilityInfo abilityInfo : EnumSet.range(AbilityInfo.Archer, AbilityInfo.Zet)) {
            sender.sendMessage(NamedTextColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + NamedTextColor.WHITE + abilityInfo.getName());
        }
    }
}
