package org.Theomachy.Ability.KIMETSU_NO_YAIBA;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Handler.Module.PlayerModule;
import org.Theomachy.Theomachy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Tanjiro extends Ability {
    private final static String[] des = {
            AbilityInfo.Tanjiro.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "제1형 「수면 베기」 (水面切り)",
            "칼을 횡방향으로 베어내는 간단한 참격 기술",
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "오의・제10형 「생생유전」(生生流転)",
            "물로 이루어진 용 형상의 검기를 휘두르며 움직일 때 마다 주변 검격의 위력을 점점 증가시키는 기술."};

    private final int normalDamage;
    private final int normalDistance;
    private float rareDistance;
    private final Long rareDuration;
    private double rareDamage;
    private boolean rareCheck;
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
        this.rareDistance = 2f;
        this.rareDamage = 0;
        this.rareDuration = 5 * 20L;
        this.rareCheck = false;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, skillMaterial)) {
            switch (event.getAction()) {
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
            Vector direction = startLocation.getDirection().clone();

            // 삼각형의 세 꼭지점 계산
            Location temp = startLocation.add(direction);
            Location startVertex = temp.clone().add(startLocation.getDirection().clone().rotateAroundAxis(direction, Math.toRadians(-80)).multiply(normalDistance));
            Location endVertex = temp.clone().add(startLocation.getDirection().clone().rotateAroundAxis(direction, Math.toRadians(80)).multiply(normalDistance));

            int normalParticleCount = 500;
            for (double t = 0; t <= 1; t += 1.0 / normalParticleCount) {
                Location particleLocation = interpolate(startVertex, endVertex, t);
                world.spawnParticle(Particle.WATER_SPLASH, particleLocation, 50);
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            rareCheck = true;
            int radius = 1;
            // 파티클을 생성하고 플레이어 주변에 회전하도록 설정
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
                for (double t = 0; t < Math.PI * rareDistance; t += Math.PI / 16) {
                    double x = Math.cos(t) * (radius + rareDistance);
                    double z = Math.sin(t) * (radius + rareDistance);
                    Location playerLocation = player.getLocation();
                    Location particleLocation = playerLocation.clone().add(x, 1 + rareDistance / 2, z);
                    World world = player.getWorld();
                    world.spawnParticle(Particle.WATER_WAKE, particleLocation, (int) rareDamage);
                    world.playSound(particleLocation,Sound.ENTITY_BOAT_PADDLE_WATER,0.1f,1f);
                }
            }, 0, 1L);
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
            }, rareDuration);
        }
    }

    public void passiveSkill(PlayerMoveEvent event) {
        if (rareCheck) {
            rareDamage += 0.1f;
            rareDistance += 0.04f;
        }
    }

    private Location interpolate(Location start, Location end, double t) {
        // t 값은 [0, 1] 범위의 비율을 나타냅니다. 0은 시작점, 1은 끝점을 의미하며, 그 사이의 값은 선분 상의 어디에 위치하는지를 나타냅니다.
        // start와 end 사이를 일정 비율로 이동한 지점을 구합니다.
        double x = (1 - t) * start.getX() + t * end.getX();
        double y = (1 - t) * start.getY() + t * end.getY();
        double z = (1 - t) * start.getZ() + t * end.getZ();
        // 구한 좌표를 이용하여 새로운 위치를 생성하고 반환합니다.
        return new Location(start.getWorld(), x, y, z);
    }

}
