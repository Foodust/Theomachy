package org.Theomachy.Ability.KIMETSU_NO_YAIBA;

import org.Theomachy.Data.TickData;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;


import java.util.Objects;

public class Zenitsu extends Ability {
    private final static String[] des = {
            AbilityInfo.Zenitsu.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "제1의 형 「벽력일섬」(霹靂一閃)",
            "전광석화의 기세로 접근해서 일직선의 일격에 목을 벤다.",
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "제7의 형 「화뢰신」(火 雷 神)",
            "온 신경을 다리에 집중시켜서 뛰어 오른 후 돌진하며 번개를 남긴다."};
    private final int normalDistance;
    private final double rareJumpDistance;
    private final int rareDistance;
    private final int rareTime;
    private final double rareDashDistance;

    public Zenitsu(String playerName) {
        super(playerName, AbilityInfo.Zenitsu, true, false, false, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 80;
        this.normalSkillStack = 16;
        this.normalDistance = 20;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 32;
        this.rareJumpDistance = 1.3f;
        this.rareDashDistance = 3f;
        this.rareDistance = 10;
        this.rareTime = 1;

        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Location location = player.getLocation();
            location.setPitch(0f);
            for (int distance = 0; distance < normalDistance; distance += normalDistance / 10) {
                Vector direction = location.getDirection().normalize();
                Location to = location.clone().add(direction.multiply(distance));
                Objects.requireNonNull(to.getWorld()).strikeLightning(to);
                player.getWorld().spawnParticle(Particle.FLASH, location, 500, 1, 2, 1);
                player.teleport(to);
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            player.setVelocity(player.getVelocity().add(new Vector(0, rareJumpDistance, 0)));
            taskModule.runBukkitTaskLater( () -> {
                for (int i = rareDistance; i > 0; i--) {
                    Vector direction = player.getLocation().getDirection().multiply(rareDashDistance);
                    player.setVelocity(direction);
                    int finalI = i;
                    taskModule.runBukkitTaskLater( () -> {
                        Vector lightningDirection = player.getEyeLocation().getDirection().clone();
                        Location startLocation = player.getEyeLocation().clone().add(player.getEyeLocation().getDirection().multiply(-1));
                        for (int distance = -finalI; distance < finalI; distance++) {
                            Vector horizontalOffset = lightningDirection.clone().crossProduct(new Vector(0, 1, 0)).normalize().multiply(distance);
                            Location lightningLocation = startLocation.clone().add(horizontalOffset);
                            Objects.requireNonNull(lightningLocation.getWorld()).strikeLightning(lightningLocation);
                        }
                    }, 5L);
                }
            }, rareTime * TickData.longTick);
        }
    }
}
