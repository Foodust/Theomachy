package org.Theomachy.Ability.JUJUTSU_KAISEN;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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
                            case Jogo -> {
                                JogoSetLava(block);
                            }
                            case Sukuna -> {
                                SukunaSetWater(block);
                            }
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
        if (Math.random() < 0.005) { // 0.5%의 확률로 용암 배치
            block.setType(Material.LAVA); // 용암 블록 배치
        } else {
            block.setType(Material.AIR); // 나머지는 공기로 설정
        }
    }

    private void JogoSetFire(Location centerLocation, double x, double y, double z) {
        Location blockLocationFire = centerLocation.clone().add(x, y, z);
        blockLocationFire.getBlock().setType(Material.FIRE);
    }

    private void SukunaSetWater(Block block) {
        if (Math.random() < 0.001) { // 0.1%의 확률로 물 배치
            block.setType(Material.WATER); // 물 블록 배치
        } else {
            block.setType(Material.AIR); // 나머지는 공기로 설정
        }
    }
}
