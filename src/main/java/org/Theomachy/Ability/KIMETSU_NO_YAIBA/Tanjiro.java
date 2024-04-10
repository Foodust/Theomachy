package org.Theomachy.Ability.KIMETSU_NO_YAIBA;

import de.slikey.effectlib.effect.*;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.TickData;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Handler.Module.game.PlayerModule;
import org.Theomachy.Theomachy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Tanjiro extends Ability {
    private final static String[] des = {
            AbilityInfo.Tanjiro.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "물의 호흡 | 제1형 「원무」 (円舞)",
            "해의 호흡으로 생긴 기운을 담아 종으로 베어내는 기술이다",
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "히노카미 카구라 | 제6형 「비틀린 소용돌이」(ねじれ渦)",
            "몸을 크게 비틀어서, 강력한 소용돌이처럼 회전하는 기술"};

    private final int normalDamage;
    private final int normalDistance;
    private float rareDistance;
    private final Long rareDuration;
    private float rareDamage;
    private boolean rareCheck;
    private final PlayerModule playerModule = new PlayerModule();

    public Tanjiro(String playerName) {
        super(playerName, AbilityInfo.Tanjiro, true, false, false, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 40;
        this.normalSkillStack = 10;
        this.normalDistance = 2;
        this.normalDamage = 5;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 50;
        this.rareDistance = 2f;
        this.rareDamage = 10;
        this.rareDuration = 5 * TickData.longTick;
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
            int particles = 300;
            int radius = 2;
            World world = player.getWorld();
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
                Location playerLocation = player.getLocation().add(new Vector(0,0.5,0));
                Vector direction = playerLocation.getDirection();
                for (double i = 0; i < particles; i++) {
                    double angle = 2 * Math.PI * i / particles;
                    double x = playerLocation.getX() + radius * Math.sin(angle) * direction.getX();
                    double y = playerLocation.getY() + radius * Math.cos(angle);
                    double z = playerLocation.getZ() + radius * Math.sin(angle) * direction.getZ();
                    Location circleLocation = new Location(playerLocation.getWorld(),x, y, z);
                    world.spawnParticle(Particle.FLAME, circleLocation, 1,0,0,0,0);
                    playerModule.damageNearEntity(player,circleLocation,normalDamage,3,3,3);
                }
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 5.0f, 5.0f);
            }, 0, 0L);
            taskModule.runBukkitTaskLater( ()->{taskModule.cancelBukkitTask(bukkitTask);},5L);
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            rareCheck = true;
            Location location = player.getLocation();

            TornadoEffect tornadoEffect = new TornadoEffect(effectManage);
            tornadoEffect.setLocation(location.add(new Vector(0,-2,0)));
            tornadoEffect.tornadoParticle = Particle.WATER_SPLASH;
            tornadoEffect.showCloud = false;
            tornadoEffect.maxTornadoRadius = 4f;
            tornadoEffect.tornadoHeight = 7f;
            tornadoEffect.start();
            playerModule.damageNearEntity(player,location,rareDamage,5,10,5);
            taskModule.runBukkitTaskLater(tornadoEffect::cancel,TickData.longTick);
       }
    }
}
