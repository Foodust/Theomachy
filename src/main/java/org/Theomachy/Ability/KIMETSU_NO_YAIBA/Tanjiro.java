package org.Theomachy.Ability.KIMETSU_NO_YAIBA;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Checker.MouseEventChecker;
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
    private final int rareDelay;
    private final int rareDamage;
    private final PlayerModule playerModule = new PlayerModule();

    public Tanjiro(String playerName) {
        super(playerName, AbilityInfo.Rengoku, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 40;
        this.normalSkillStack = 10;
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
            Vector direction = player.getLocation().getDirection();
            Location location = player.getLocation();
            player.getWorld().spawnParticle(Particle.WATER_SPLASH,location, 400);
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
        }
    }
}
