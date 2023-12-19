package org.Theomachy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

public class UpdateChecker {
    public static void check(String thisVersion) {
        try {
            Bukkit.getConsoleSender().sendMessage("[신들의 전쟁] " + ChatColor.AQUA + "최신 버전은" + ChatColor.GREEN + "Theomachy: 5.0 입니다.");
            Bukkit.getConsoleSender().sendMessage("[신들의 전쟁] 개발자 블로그: http://blog.naver.com/seunhoo");
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("[신들의 전쟁] " + ChatColor.RED + "업데이트 체크에 실패했습니다...");
        }
    }
}