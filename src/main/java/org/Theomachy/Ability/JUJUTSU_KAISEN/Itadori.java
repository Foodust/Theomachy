package org.Theomachy.Ability.JUJUTSU_KAISEN;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;


public class Itadori extends Ability {
    private final static String[] des = {
            AbilityInfo.Itarodi.getName(),
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "흑섬",
            "기본 공격시 한번더 타격이 들어갑니다."};

    public Itadori(String playerName) {
        super(playerName, AbilityInfo.Itarodi, false, true, true, des);
        Theomachy.log.info(playerName + abilityName);
        this.rank = AbilityRank.B;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (player.getName().equals(playerName)) {
                if (event.getEntity() instanceof LivingEntity victim) {
                    double damage = event.getDamage();
                    victim.damage(damage);
                }
            }
        }
    }
}
