package org.Theomachy.Ability.JUJUTSU_KAISEN;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Theomachy;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class RyoikiTenkai extends Ability {
    public RyoikiTenkai(String playerName, AbilityInfo abilityInfo, boolean activeType, boolean passiveType, boolean buffType, String[] des) {
        super(playerName, abilityInfo, activeType, passiveType, buffType, des);
    }

    public int normalDamage;
    public int normalDistance;
    public int rareDistance;
    public int rareDuration;

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
        }
        Bukkit.broadcastMessage(area);
        Bukkit.broadcastMessage(areaName);
        player.sendTitle(area, null, titleFadeIn, titleStay, titleFadeOut);
        player.sendTitle(null, areaName, subFadeIn, subStay, subFadeOut);
        player.setVelocity(new Vector(0,2,0));
        for (Entity entity : locationWorld.getNearbyEntities(playerLocation, 15, 15, 15)) {
            if (entity instanceof Player enemy) {
                enemy.sendTitle(area, null, titleFadeIn, titleStay, titleFadeOut);
                enemy.sendTitle(null, areaName, subFadeIn, subStay, subFadeOut);
                enemy.setVelocity(new Vector(0, 2, 0));
            }
        }
    }

    public void goRyoikiTenkai(Player player, AbilityInfo abilityInfo, Material floor, Material wall) {

        Location centerLocation = player.getLocation().add(0, -1, 0); // 발밑 기준으로 블록을 생성할 위치
        Map<Location, Material> originalBlocks = new HashMap<>();

        // 벽 생성
        for (int x = -rareDistance; x <= rareDistance; x++) {
            for (int z = -rareDistance; z <= rareDistance; z++) {
                for (int y = 1; y <= rareDistance; y++) {
                    Location blockLocation = centerLocation.clone().add(x, y, z);
                    Block block = blockLocation.getBlock();
                    originalBlocks.put(blockLocation, block.getType());
                    if (Math.abs(x) == rareDistance || Math.abs(z) == rareDistance || y == rareDistance) {
                        block.setType(wall);
                    } else {
                        switch (abilityInfo) {
                            case Jogo -> JogoSetLava(block);
                            case Sukuna -> setAir(block);
                        }
                    }
                }
            }
        }
        // 바닥 생성
        for (int x = -rareDistance + 1; x <= rareDistance - 1; x++) {
            for (int z = -rareDistance + 1; z <= rareDistance - 1; z++) {
                Location blockLocation = centerLocation.clone().add(x, 0, z);
                Block block = blockLocation.getBlock();
                originalBlocks.put(blockLocation, block.getType());
                blockLocation.getBlock().setType(floor);
                switch (abilityInfo) {
                    case Jogo -> {
                        JogoSetFire(centerLocation, x, 1, z);
                    }
                    case Sukuna -> {
                        SukunaSetWater(centerLocation, x, 1, z);
                    }
                }
            }
        }
        Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
            for (Map.Entry<Location, Material> entry : originalBlocks.entrySet()) {
                Location loc = entry.getKey();
                Material originalType = entry.getValue();
                loc.getBlock().setType(originalType); // 원래의 블록 타입으로 되돌림
            }
        }, rareDuration * 20L); // 10초 후 (20틱 = 1초 * 10초 = 200틱)
    }

    private void JogoSetLava(Block block) {
        if (Math.random() < 0.008) { // 0.05%의 확률로 용암 배치
            block.setType(Material.LAVA); // 용암 블록 배치
        } else {
            block.setType(Material.AIR); // 나머지는 공기로 설정
        }
    }

    private void JogoSetFire(Location centerLocation, double x, double y, double z) {
        Location blockLocationFire = centerLocation.clone().add(x, y, z);
        blockLocationFire.getBlock().setType(Material.FIRE);
    }

    private void SukunaSetWater(Location centerLocation, double x, double y, double z) {
        Location blockLocationFire = centerLocation.clone().add(x, y, z);
        blockLocationFire.getBlock().setType(Material.WATER);
    }

    private void setAir(Block block) {
        block.setType(Material.AIR);
    }
}
