package org.Theomachy.Ability.KIMETSU_NO_YAIBA;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Checker.MouseEventChecker;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Handler.Module.PlayerModule;
import org.Theomachy.Theomachy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Tanjiro extends Ability {
    private final static String[] des = {
            AbilityInfo.Tanjiro.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "제1형 「수면 베기」 (水面切り)",
            "칼을 횡방향으로 베어내는 간단한 참격 기술",
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "오의・제10형 「생생유전」(生生流転)",
            "물로 이루어진 용 형상의 검기를 휘두르며 회전을 더해가면서 검격의 위력을 점점 증가시키는 기술."};

    private final int normalDamage;
    private final int normalDistance;
    private final int rareDistance;
    private final int rareTime;
    private final Long rareDuration;
    private final int rareDamage;
    private final PlayerModule playerModule = new PlayerModule();

    public Tanjiro(String playerName) {
        super(playerName, AbilityInfo.Tanjiro, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 40;
        this.normalSkillStack = 10;
        this.normalDistance = 10;
        this.normalDamage = 5;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 50;

        this.rareDistance = 40;
        this.rareDamage = 20;
        this.rareTime = 1;
        this.rareDuration = 5L;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Location startLocation = player.getEyeLocation().clone().add(player.getEyeLocation().getDirection().multiply(3));
            World world = player.getWorld();
            Vector direction = player.getEyeLocation().getDirection().clone();
            for (double distance = (double) -normalDistance / 2; distance < (double) normalDistance / 2; distance += 0.1) {
                Vector horizontalOffset = direction.clone().crossProduct(new Vector(0, 1, 0)).normalize().multiply(distance);
                Location particleLocation = startLocation.clone().add(horizontalOffset);
                world.spawnParticle(Particle.WATER_SPLASH, particleLocation, 100);
                playerModule.damageNearEntity(player,particleLocation, normalDamage, 4, 4, 4);
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            Location playerLocation = player.getLocation();
            int radius = 2;
            // 파티클을 생성하고 플레이어 주변에 회전하도록 설정
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
                for (double t = 0; t < Math.PI * 2; t += Math.PI / 16) {
                    double x = Math.cos(t) * radius;
                    double z = Math.sin(t) * radius;

                    Location particleLocation = playerLocation.clone().add(x, 0, z);
                    World world = player.getWorld();

                    // 원하는 파티클을 설정하여 생성
                    world.spawnParticle(Particle.WATER_WAKE, particleLocation, 20);
                }
            }, 0, 1L);
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),()->{
                Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
            },rareDuration);
        }
    }
}
