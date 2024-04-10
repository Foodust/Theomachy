package org.Theomachy.Ability.JUJUTSU_KAISEN;

import com.google.common.util.concurrent.AtomicDouble;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.*;
import org.Theomachy.Data.TickData;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Satoru extends RyoikiTenkai {
    private final static String[] des = {
            AbilityInfo.Satoru.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "허식 「자」",
            "전방에 구체를 보냅니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "영역 전개 | 무량공처 (無量空処)",
            "영역에 상대방을 가두고 무수한 정보를 보냅니다. 자신은 면역입니다."
    };
    private final int normalDamage;
    private final double normalDistance;
    private final long normalDuration;
    private final int rareDamage;

    public Satoru(String playerName) {
        super(playerName, AbilityInfo.Satoru, true, true, false, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 80;
        this.normalSkillStack = 16;
        this.normalDistance = 0.5;
        this.normalDuration = 10L;
        this.normalDamage = 5;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 50;
        this.rareDistance = 15;
        this.rareDuration = 10;
        this.rareDamage = 20;
        this.rank = AbilityRank.S;
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
            makeSphereParticle(player);
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);

            sendRyoikiTenkai(AbilityInfo.Satoru, player);
            goRyoikiTenkai(player,AbilityInfo.Satoru,Material.SEA_LANTERN,Material.IRON_BLOCK);

            List<BigBangEffect> bigBangEffects = new ArrayList<>();
            player.getNearbyEntities(rareDistance,rareDistance,rareDistance).forEach(nearEntity->{
                BukkitTask bukkitTask = taskModule.runBukkitTaskLater(() -> {
                    BigBangEffect bigBangEffect = new BigBangEffect(effectManage);
                    bigBangEffect.setLocation(nearEntity.getLocation().add(new Vector(0, 1, 0)));
                    bigBangEffect.particle = Particle.WHITE_ASH;
                    bigBangEffect.color = Color.WHITE;
                    bigBangEffect.color2 = Color.GRAY;
                    bigBangEffect.color3 = Color.BLACK;
                    bigBangEffect.soundVolume = 0.1f;
                    bigBangEffect.start();
                    bigBangEffects.add(bigBangEffect);
                }, TickData.longTick);
                taskModule.runBukkitTaskLater(()->{
                    taskModule.cancelBukkitTask(bukkitTask);
                    bigBangEffects.forEach(Effect::cancel);
                },rareDuration * TickData.longTick);
            });
        }
    }

    private void makeSphereParticle(Player player){
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        Location center = playerLocation.add(playerLocation.getDirection().multiply(1)).add(0,player.getEyeHeight(),0);
        AtomicDouble distance = new AtomicDouble();

        AtomEffect atomEffect = new AtomEffect(effectManage);
        atomEffect.particleOrbital = Particle.REDSTONE;
        atomEffect.particleNucleus = Particle.REDSTONE;
        atomEffect.particlesOrbital = 30;
        atomEffect.particlesNucleus = 30;
        atomEffect.colorOrbital = Color.PURPLE;
        atomEffect.colorNucleus = Color.PURPLE;
        atomEffect.rotation = 160;
        atomEffect.radius = 2;
        atomEffect.particleCount = 1;
        atomEffect.setLocation(center);
        atomEffect.start();
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
            Location particleLocation = center.add(center.getDirection().multiply(distance.getAndAdd(0.015)));
            atomEffect.setLocation(particleLocation);
            world.playSound(particleLocation,Sound.ITEM_TOTEM_USE,0.2f,10f);
            playerModule.damageNearEntity(player,particleLocation,normalDamage,5,5,5);
        }, 0L, 1L);

        taskModule.runBukkitTaskLater(()->{
            taskModule.cancelBukkitTask(bukkitTask);
            atomEffect.cancel();
        },normalDuration * TickData.longTick);
    }
}
