package org.Theomachy.Ability.GOD;



import de.slikey.effectlib.effect.ImageEffect;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityTag;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;

public class Sans extends Ability {
    private final static String[] des = {
            "와 " + AbilityInfo.Sans.getName(),
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "가스트 블래스터 Ⅱ",
            "가스트 블래스터를 발사합니다"};

    private final int passiveDuration;
    private final int rareDistance;
    private final int rareDamage;
    public Sans(String playerName) {
        super(playerName, AbilityInfo.Sans, true, false, true, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 1;
        this.normalSkillStack = 3;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 30;
        this.rareDamage = 10;

        this.passiveDuration = 3;
        this.rareDistance = 800;
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
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            Location startLocation = player.getEyeLocation(); // 플레이어의 눈 위치 가져오기
            World world = player.getWorld();
            for (double distance = 0; distance < rareDistance; distance += 0.5) {
                Vector direction = startLocation.getDirection().multiply(distance);
                Location particleLocation = startLocation.clone().add(direction);
                world.spawnParticle(Particle.DRAGON_BREATH, particleLocation, 500);
                playerModule.damageNearEntity(player, particleLocation, rareDamage,12,12,12);
            }
        }
    }
}
