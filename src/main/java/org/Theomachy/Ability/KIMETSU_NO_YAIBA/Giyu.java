package org.Theomachy.Ability.KIMETSU_NO_YAIBA;

import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.SquareEffect;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
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

public class Giyu extends Ability {
    private final static String[] des = {
            AbilityInfo.Giyu.getName(),
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "제11형 「잔잔한 물결」 (拾壱ノ型凪) ",
            "3초간 자신에게 날아오는 어떤 형태의 공격이든 받아넘겨 흘려내 방어하는 기술 "};

    private final double rareDistance;
    private final Long rareDuration;

    public Giyu(String playerName) {
        super(playerName, AbilityInfo.Giyu, true, false, true, des);

        this.rareSkillCoolTime = 110;
        this.rareSkillStack = 50;

        this.rareDistance = 15;
        this.rareDuration = 3L;
        this.rank = AbilityRank.B;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, skillMaterial)) {
            switch (event.getAction()) {
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            World world = player.getWorld();

            Location playerLocation = player.getLocation();
            SquareEffect squareEffect = new SquareEffect(effectManage);
            squareEffect.type = EffectType.REPEATING;
            squareEffect.particle = Particle.WATER_DROP;
            squareEffect.radius = 5;
            squareEffect.particles = 500;
            squareEffect.setLocation(playerLocation);
            squareEffect.start();
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, PotionEffect.INFINITE_DURATION, 255));
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                squareEffect.cancel();
            }, rareDuration * 20L);
        }
    }
}
