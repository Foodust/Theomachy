package org.Theomachy.Ability.JUJUTSU_KAISEN;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;

import org.Theomachy.Theomachy;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.concurrent.atomic.AtomicReference;

public class Sukuna extends RyoikiTenkai {
    private final static String[] des = {
            AbilityInfo.Sukuna.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "해 (解)",
            "전방에 참격을 보냅니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "영역 전개 | 복마어주자 (伏魔御廚子)",
            "영역에 상대방을 가두고 무수한 참격을 날립니다. 자신은 면역입니다."};

    private final int rareDamage;

    public Sukuna(String playerName) {
        super(playerName, AbilityInfo.Sukuna, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 20;
        this.normalSkillStack = 10;
        this.normalDamage = 5;
        this.normalDistance = 30;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 60;
        this.rareDistance = 15;
        this.rareDuration = 10;
        this.rareDamage = 5;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Location startLocation = player.getEyeLocation(); // 플레이어의 눈 위치 가져오기
            World world = player.getWorld();
            for (double distance = 0; distance < normalDistance; distance += 0.5) {
                Vector direction = startLocation.getDirection().multiply(distance);
                Location particleLocation = startLocation.clone().add(direction);
                world.spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 50);
                playerModule.damageNearEntity(player,particleLocation,normalDamage,4,4,4);
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);

            sendRyoikiTenkai(AbilityInfo.Sukuna, player);

            goRyoikiTenkai(player, AbilityInfo.Sukuna, Material.CRYING_OBSIDIAN, Material.OBSIDIAN);

            Location location = player.getLocation();
            location.add(0, 6, 0);
            AtomicReference<BukkitTask> bukkitTask = new AtomicReference<>();
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),
                    () -> bukkitTask.set(Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), 
                            () -> slash(player,location), 0, 2L)), 2 * 20L);
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), 
                    () -> Bukkit.getScheduler().cancelTask(bukkitTask.get().getTaskId()), rareDuration * 20L);
        }
    }
    private void slash(Player player, Location location ){
        World world = location.getWorld();
        assert world != null;
        world.spawnParticle(Particle.SWEEP_ATTACK, location, 3000, 10, 5, 10);
        for (Entity entity : world.getNearbyEntities(location, 15, 15, 15)) {
            if (entity instanceof LivingEntity && !entity.equals(player)) {
                ((LivingEntity) entity).damage(rareDamage, player);
            }
        }
    }
}
