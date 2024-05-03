package org.Theomachy.Ability.JUJUTSU_KAISEN;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.TickData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Handler.Module.game.DisplayModule;
import org.Theomachy.Theomachy;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class RyoikiTenkai extends Ability {
    public final DisplayModule displayModule = new DisplayModule();

    public RyoikiTenkai(String playerName, AbilityInfo abilityInfo, boolean activeType, boolean passiveType, boolean buffType, String[] des) {
        super(playerName, abilityInfo, activeType, passiveType, buffType, des);
    }

    public void sendRyoikiTenkai(AbilityInfo abilityInfo, Player player) {

        int titleFadeIn = 1 * 20;
        int titleFadeOut = 1 * 20;
        int titleStay = 5 * 20;

        int subFadeIn = 5 * 20;
        int subFadeOut = 1 * 20;
        int subStay = 5 * 20;

        Location playerLocation = player.getLocation();
        World locationWorld = playerLocation.getWorld();
        assert locationWorld != null;
        String area = "";
        String areaName = "";
        switch (abilityInfo) {
            case Jogo -> {
                area = ChatColor.RED + "영 역 전 개";
                areaName = ChatColor.DARK_RED + "개관철위산 (蓋棺鉄囲山)";
            }
            case Sukuna -> {
                area = ChatColor.RED + "영 역 전 개";
                areaName = ChatColor.DARK_RED + "복마어주자 (伏魔御廚子)";
            }
            case Satoru -> {
                area = ChatColor.WHITE + "영 역 전 개";
                areaName = ChatColor.AQUA + "무량공처 (無量空処)";
            }
        }
        Bukkit.broadcastMessage(area);
        Bukkit.broadcastMessage(areaName);
        messageModule.sendTitle(player, area, titleFadeIn, titleStay, titleFadeOut);
        messageModule.sendTitle(player, null, areaName, subFadeIn, subStay, subFadeOut);
        for (Entity entity : locationWorld.getNearbyEntities(playerLocation, 15, 15, 15)) {
            if (entity instanceof Player enemy) {
                messageModule.sendTitle(enemy, area, titleFadeIn, titleStay, titleFadeOut);
                messageModule.sendTitle(enemy, null, areaName, subFadeIn, subStay, subFadeOut);
            }
        }
    }

    public void goRyoikiTenkai(Player player, Material floor, Material wall) {

        Location centerLocation = player.getLocation().add(0, -1, 0); // 발밑 기준으로 블록을 생성할 위치
        Map<Location, BlockState> originalBlockMap = new HashMap<>();
        long delay = 0;
        // 바닥 생성
        for (int x = (int) (-rareDistance + 1); x <= rareDistance - 1; x++) {
            for (int z = (int) (-rareDistance + 1); z <= rareDistance - 1; z++) {
                Location blockLocation = centerLocation.clone().add(x, 0, z);
                Block block = blockLocation.getBlock();
                if (block.getState() instanceof Chest) continue;
                originalBlockMap.put(blockLocation, block.getState());
                taskModule.runBukkitTaskLater(() -> {
                    block.setType(floor);
                }, delay++);
            }
        }
        delay = 0;
        if (wall != null) {
            for (int x = (int) -rareDistance; x <= rareDistance; x++) {
                for (int z = (int) -rareDistance; z <= rareDistance; z++) {
                    for (int y = 1; y <= rareDistance; y++) {
                        Location blockLocation = centerLocation.clone().add(x, y, z);
                        Block block = blockLocation.getBlock();
                        if (block.getState() instanceof Chest) continue;
                        originalBlockMap.put(blockLocation, block.getState());
                        if (Math.abs(x) == rareDistance || Math.abs(z) == rareDistance || y == rareDistance) {
                            taskModule.runBukkitTaskLater(() -> {
                                block.setType(wall);
                            }, delay++);
                        }
                    }
                }
            }
        }
        taskModule.runBukkitTaskLater(() -> {
            for (Map.Entry<Location, BlockState> entry : originalBlockMap.entrySet()) {
                Location originalBlockLocation = entry.getKey();
                BlockState originalBlock = entry.getValue();
                originalBlockLocation.getBlock().setBlockData(originalBlock.getBlockData()); // 원래의 블록 타입으로 되돌림
            }
            originalBlockMap.clear();
        }, rareDuration * TickData.longTick); // 10초 후 (20틱 = 1초 * 10초 = 200틱)
    }
}
