package org.Theomachy.Handler.Event;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityTag;
import org.Theomachy.Handler.Command.StartStopCommand;
import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Theomachy;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class DamageEvent implements Listener {

    @EventHandler
    public void onPlayerDamageByMagma(BlockDamageEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.MAGMA_BLOCK) {
            // 블록 위에 있는 플레이어의 피해를 방지
            if (event.getPlayer().getLocation().getBlock().getType() == Material.MAGMA_BLOCK) {
                event.setCancelled(true); // 데미지 이벤트 취소
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball snowball) {
            if (snowball.getScoreboardTags().contains(AbilityTag.BONEATTACK.getTag())) {
                if (event.getHitEntity() instanceof LivingEntity target) {
                    target.damage(2);
                    int durationInSeconds = 3 * 20; // 위더 효과 지속 시간 (틱 단위로 20으로 나누어야됨)
                    int amplifier = 1; // 위더 효과 강도
                    PotionEffect poisonEffect = new PotionEffect(PotionEffectType.WITHER, durationInSeconds, amplifier);
                    target.addPotionEffect(poisonEffect);
                }
            }
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (GameModule.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.playerAbility.containsKey(playerName))
                    GameData.playerAbility.get(playerName).passiveSkill(event);
            }
            if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING && event.getEntity() instanceof LivingEntity le) {
                le.setNoDamageTicks(0);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        try {
            if (GameModule.Start) {
                if (event.getDamager() instanceof Player &&
                        (event.getEntity() instanceof Player || event.getEntity() instanceof LivingEntity)) {

                    String entityName = (event.getEntity()).getName();
                    String DamageName = (event.getDamager()).getName();

                    Ability entityAbility = GameData.playerAbility.get(entityName);
                    Ability damagerAbility = GameData.playerAbility.get(DamageName);

                    if (entityAbility != null)
                        entityAbility.passiveSkill(event);
                    if (damagerAbility != null)
                        damagerAbility.passiveSkill(event);

                } else if (event.getDamager() instanceof Arrow arrow && event.getEntity() instanceof Player) {
                    if (arrow.getShooter() instanceof Player player) {
                        String key = player.getName();
                        Ability ability = GameData.playerAbility.get(key);

                        if (ability != null &&
                                ability.abilityCode == AbilityInfo.Artemis.getIndex() ||
                                Objects.requireNonNull(ability).abilityCode == AbilityInfo.Archer.getIndex()) {
                            ability.passiveSkill(event);
                        }
                    }
                } else if (event.getDamager() instanceof Snowball snow && event.getEntity() instanceof Player) {
                    if (snow.getShooter() instanceof Player player) {

                        Ability ability = GameData.playerAbility.get(player.getName());

                        if (ability != null && ability.abilityCode == AbilityInfo.Snow.getIndex())
                            ability.passiveSkillSnow(event);
                    }
                }
            }
        } catch (Exception e) {
            Theomachy.log.info(e.getLocalizedMessage());
        }
    }
}
