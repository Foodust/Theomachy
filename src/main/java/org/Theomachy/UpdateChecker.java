package org.Theomachy;

import org.Theomachy.Data.VersionData;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

public class UpdateChecker {
    public static void check(String thisVersion) {
        try {
            Bukkit.getConsoleSender().sendMessage(VersionData.name + ChatColor.AQUA + "최신 버전은" + VersionData.version);
            Bukkit.getConsoleSender().sendMessage(VersionData.name + "개발자 연락" + TheomachyMessage.HEY_DEVELOPER.getMessage() );
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(VersionData.name + ChatColor.RED + "업데이트 체크에 실패했습니다...");
        }
    }
}