package org.Theomachy.Ability.JUJUTSU_KAISEN;

import org.Theomachy.Data.TickData;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.bukkit.scheduler.BukkitTask;


public class Jogo extends RyoikiTenkai {
    private final static String[] des = {
            AbilityInfo.Jogo.getName(),
            ChatColor.YELLOW + "【패시브】 " + ChatColor.GREEN + "불 속성",
            "불 데미지에 면역입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "화력충",
            "전방에 화력충(화염구)를 보냅니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "영역 전개 | 개관철위산 (蓋棺鉄囲山)",
            "영역에 상대방을 가두고 불에 태웁니다. 자신은 불에 면역입니다."};

    public Jogo(String playerName) {
        super(playerName, AbilityInfo.Jogo, true, true, false, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 30;
        this.normalSkillStack = 15;
        this.normalDamage = 3;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 60;
        this.rareDistance = 15;
        this.rareDuration = 10;
        this.rareDamage = 5;
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityDamageEvent event) {
            Player player = (Player) event.getEntity();
            EntityDamageEvent.DamageCause dc = event.getCause();
            if (dc.equals(EntityDamageEvent.DamageCause.LAVA) ||
                    dc.equals(EntityDamageEvent.DamageCause.FIRE) ||
                    dc.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
                event.setCancelled(true);
                player.setFireTicks(0);
            }
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
            Fireball fireball = player.launchProjectile(Fireball.class, player.getEyeLocation().getDirection());
            fireball.setShooter(player);
            fireball.setYield(normalDamage); // 화염구의 피해량 설정 (원하는 값으로 변경 가능)
            fireball.setIsIncendiary(false); // 대상을 불태우는 여부 설정
        }
    }
    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            sendRyoikiTenkai(AbilityInfo.Jogo,player);
            goRyoikiTenkai(player, Material.MAGMA_BLOCK,Material.MAGMA_BLOCK);

            World world = player.getWorld();
            BukkitTask bukkitTask = taskModule.runBukkitTaskTimer(() -> {
                player.getNearbyEntities(10, 5, 10).forEach(nearEntity -> {
                    LivingEntity livingEntity = (LivingEntity) nearEntity;
                    world.spawnParticle(Particle.FLAME, livingEntity.getLocation(), 20, 1, 1, 1);
                    world.spawnParticle(Particle.EXPLOSION_NORMAL, livingEntity.getLocation(), 1, 0.5, 0.5, 0.5);
                    livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_PLAYER_HURT_ON_FIRE, 1f, 1f);
                    livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f);
                    livingEntity.damage(rareDamage, player);
                });
            }, 0L, 1L);
            taskModule.runBukkitTaskLater(() -> {
                taskModule.cancelBukkitTask(bukkitTask);
            }, rareDuration * TickData.longTick);
        }
    }
}
