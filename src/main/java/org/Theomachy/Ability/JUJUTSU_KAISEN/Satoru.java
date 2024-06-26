package org.Theomachy.Ability.JUJUTSU_KAISEN;

import com.google.common.util.concurrent.AtomicDouble;
import de.slikey.effectlib.effect.*;
import org.Theomachy.Data.TickData;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;

import java.util.ArrayList;
import java.util.List;

public class Satoru extends RyoikiTenkai {
    private final List<TextDisplay> textDisplayList = new ArrayList<>();
    private final static String[] des = {
            AbilityInfo.Satoru.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "허식 「자」",
            "전방에 구체를 보냅니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "영역 전개 | 무량공처 (無量空処)",
            "영역에 상대방을 가두고 무수한 정보를 보냅니다. 자신은 면역입니다."
    };

    public Satoru(String playerName) {
        super(playerName, AbilityInfo.Satoru, true, true, false, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 80;
        this.normalSkillStack = 16;
        this.normalDistance = 0.5;
        this.normalDuration = 10;
        this.normalDamage = 5;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 50;
        this.rareDistance = 15;
        this.rareDuration = 10;
        this.rareDamage = 5;
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
            goRyoikiTenkai(player,  Material.BLACK_CONCRETE, Material.BLACK_CONCRETE_POWDER);

            BukkitTask bukkitTask = taskModule.runBukkitTaskTimer(() -> {
                player.getNearbyEntities(rareDistance, rareDistance, rareDistance).forEach(nearEntity -> {
                    for (int i = 0; i < 10; i++) {
                        taskModule.runBukkitTaskLater(() -> {
                            TextDisplay textDisplay = displayModule.makeTextDisplay(player, nearEntity.getLocation(), "§kkkkkkkkkkkkkkkkkkkk", 1.0);
                            textDisplay.setRotation(nearEntity.getLocation().getYaw(), nearEntity.getLocation().getPitch());
                            textDisplayList.add(textDisplay);
                            LivingEntity livingEntity = (LivingEntity) nearEntity;
                            livingEntity.damage(rareDamage,player);
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,2 * TickData.intTick,2));
                        }, (float) (TickData.longTick * i) / 5);
                    }
                });
            }, TickData.longTick, TickData.longTick);
            taskModule.runBukkitTaskLater(() -> {
                textDisplayList.forEach(Entity::remove);
                textDisplayList.clear();
                taskModule.cancelBukkitTask(bukkitTask);
            }, rareDuration * TickData.longTick);
        }
    }

    private void makeSphereParticle(Player player) {
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        Location center = playerLocation.add(playerLocation.getDirection().multiply(1)).add(0, player.getEyeHeight(), 0);
        AtomicDouble distance = new AtomicDouble();
        VortexEffect vortexEffect = new VortexEffect(effectManage);
        vortexEffect.setLocation(center);
        vortexEffect.particle = Particle.REDSTONE;
        vortexEffect.color = Color.PURPLE;
        vortexEffect.radius = 2;
        vortexEffect.radials = 1.6;
        vortexEffect.helixes = 1;
        vortexEffect.particleCount = 6;
        vortexEffect.circles = 3;
        AtomEffect atomEffect = new AtomEffect(effectManage);
        atomEffect.particleOrbital = Particle.REDSTONE;
        atomEffect.particleNucleus = Particle.REDSTONE;
        atomEffect.particlesOrbital = 0;
        atomEffect.particlesNucleus = 50;
        atomEffect.colorNucleus = Color.PURPLE;
        atomEffect.radius = 2;
        atomEffect.particleCount = 1;
        atomEffect.setLocation(center);
        atomEffect.start();
        vortexEffect.start();
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
            Location particleLocation = center.add(center.getDirection().multiply(distance.getAndAdd(0.01)));
            Location particleLocation2 = center.add(center.getDirection().multiply(distance.getAndAdd(0.005)));
            atomEffect.setLocation(particleLocation);
            vortexEffect.setLocation(particleLocation2);
            world.playSound(particleLocation, Sound.ITEM_TOTEM_USE, 0.2f, 10f);
            playerModule.damageNearEntity(player, particleLocation, normalDamage, 5, 5, 5);
        }, 0L, 1L);

        taskModule.runBukkitTaskLater(() -> {
            taskModule.cancelBukkitTask(bukkitTask);
            atomEffect.cancel();
        }, normalDuration * TickData.longTick);
    }
}
