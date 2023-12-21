package org.Theomachy.Ability.JUJUTSU_KAISEN;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class Itadori extends Ability {
    Set<UUID> damagedEntities = new HashSet<>();
    private final static String[] des = {
            AbilityInfo.Itarodi.getName(),
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "흑섬",
            "기본 공격시 1/2 데미지로 타격이 한번더 들어갑니다."};

    public Itadori(String playerName) {
        super(playerName, AbilityInfo.Itarodi, false, true, true, des);
        Theomachy.log.info(playerName + abilityName);
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (player.getName().equals(playerName) && event.getEntity() instanceof LivingEntity enemy) {
                if (!damagedEntities.contains(enemy.getUniqueId())) {
                    double damage = event.getDamage();
                    enemy.damage(damage / 2);
                    damagedEntities.add(enemy.getUniqueId());
                }else{
                    damagedEntities.remove(enemy.getUniqueId());
                }
            }
        }
    }
}
