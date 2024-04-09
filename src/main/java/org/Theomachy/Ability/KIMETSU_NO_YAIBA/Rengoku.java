package org.Theomachy.Ability.KIMETSU_NO_YAIBA;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;

import org.Theomachy.Handler.Module.PlayerModule;
import org.Theomachy.Theomachy;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Objects;

public class Rengoku extends Ability {
    private final static String[] des = {
            AbilityInfo.Rengoku.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "제2형 「상승염천」(昇り炎天)",
            "칼을 밑에서 위로 휘둘러, 맹렬한 불꽃처럼 베는 기술.",
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "오의 • 제9형 「연옥」(煉獄)",
            "작열하는 지옥불처럼 맹렬히 돌진해서, 굉음과 함께 상대방을 도려낸다. "};

    private final int normalDamage;
    private final int normalDistance;
    private final int rareDistance;
    private final int rareTime;
    private final int rareDelay;
    private final int rareDamage;
    private final PlayerModule playerModule = new PlayerModule();

    public Rengoku(String playerName) {
        super(playerName, AbilityInfo.Rengoku, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 80;
        this.normalSkillStack = 16;
        this.normalDistance = 20;
        this.normalDamage = 5;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 50;

        this.rareDistance = 40;
        this.rareDamage = 20;
        this.rareTime = 1;
        this.rareDelay = 1;
        this.rank = AbilityRank.S;
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
            player.setVelocity(new Vector(0, 1, 0)); // Y축 방향으로 속도 부여 (점프 효과)

            int particles = 300;
            int radius = 4;
            World world = player.getWorld();
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
                Location playerLocation = player.getLocation();
                Vector direction = playerLocation.getDirection();
                for (double i = 0; i < particles; i++) {
                    double angle = 2 * Math.PI * i / particles;
                    double x = playerLocation.getX() + radius * Math.sin(angle) * direction.getX();
                    double y = playerLocation.getY() + radius * Math.cos(angle);
                    double z = playerLocation.getZ() + radius * Math.sin(angle) * direction.getZ();
                    Location circleLocation = new Location(playerLocation.getWorld(),x, y, z);
                    world.spawnParticle(Particle.FLAME, circleLocation, 1);

                    // 파티클에 닿은 플레이어 찾기

                    for (Entity enemy : world.getNearbyEntities(circleLocation, 5, 5 ,5 )) {
                        if (enemy instanceof LivingEntity && !enemy.equals(player)) {
                            ((LivingEntity) enemy).damage(normalDamage, player);
                            enemy.setVelocity(enemy.getVelocity().add(new Vector(0, 0.0001, 0)));
                        }
                    }
                }
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 50.0f, 5.0f);
            }, 0, 0L);
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), ()->{Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());},20L);
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);

            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 255));
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                player.removePotionEffect(PotionEffectType.SLOW);
                for (float distance = 0; distance < rareDistance; distance += (float) rareDistance / 20) {
                    Location location = player.getLocation();
                    location.setPitch(0f);
                    Vector direction = location.getDirection().normalize();
                    Location to = location.clone().add(direction.multiply(distance / 15));
                    World world = player.getWorld();
                    world.spawnParticle(Particle.FLAME, location, 1200);
                    world.spawnParticle(Particle.FLASH, location, 500, 1, 2, 1);
                    for (Entity entity : world.getNearbyEntities(location, 20, 5, 20)) {
                        if (entity instanceof LivingEntity && !entity.equals(player)) {
                            ((LivingEntity) entity).damage(rareDamage, player);
                        }
                    }
                    player.teleport(to);
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                    player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
                }
            }, rareDelay * 20L);
        }
    }
}
