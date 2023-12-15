package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityTag;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.*;

public class Sans extends Ability {
    private final static String[] des = {
            "와 " + AbilityInfo.Sans.getName(),
            NamedTextColor.YELLOW + "【패시브】 " + NamedTextColor.GREEN + "독 속성",
            "패시브 능력으로 대상 공격시 3초간 위더에 중독 시킵니다.",
            NamedTextColor.AQUA + "【일반】 " + NamedTextColor.WHITE + "뼈 Ⅰ",
            "뼈를 던져 피해를 입힙니다.",
            NamedTextColor.RED + "【고급】 " + NamedTextColor.AQUA + "가스트 블래스터 Ⅱ",
            "가스트 블래스터를 발사합니다"};

    private final int duration;
    public Sans(String playerName) {
        super(playerName, AbilityInfo.Sans, true, true, true, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 1;
        this.rareSkillCoolTime = 120;
        this.normalSkillStack = 3;
        this.rareSkillStack = 30;
        this.duration = 3;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        if (event.getEntity().getName().equals(playerName)) {
            if (event.getEntity() instanceof LivingEntity victim) {
                int durationInSeconds = duration * 20; // 위더 효과 지속 시간 (틱 단위로 20으로 나누어야됨)
                int amplifier = 1; // 위더 효과 강도
                PotionEffect poisonEffect = new PotionEffect(PotionEffectType.WITHER, durationInSeconds, amplifier);
                victim.addPotionEffect(poisonEffect);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Snowball snowball = player.launchProjectile(Snowball.class);
            snowball.setVelocity(player.getLocation().getDirection().multiply(1.5)); // 조절 가능한 속도
            snowball.addScoreboardTag(AbilityTag.BONEATTACK.getTag()); // 뼈 공격을 식별하기 위한 태그 추가
        }
    }

    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            Location startLocation = player.getEyeLocation(); // 플레이어의 눈 위치 가져오기
            World world = player.getWorld();
            for (double distance = 0; distance < 800; distance += 0.5) {
                Vector direction = startLocation.getDirection().multiply(distance);
                Location particleLocation = startLocation.clone().add(direction);
                world.spawnParticle(Particle.DRAGON_BREATH, particleLocation, 50);
                for (Entity entity : world.getNearbyEntities(particleLocation, 12, 12, 12)) {
                    if (entity instanceof LivingEntity && !entity.equals(player)) {
                        ((LivingEntity) entity).damage(10, player);
                    }
                }
            }
        }
    }
}
