package org.Theomachy.Ability.HUMAN;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityExplodeEvent;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Ability.ENUM.AbilityRank;

public class Bulter extends Ability {

    private final static String[] des = {
            AbilityInfo.Bulter.getName() + "는 굉장히 젠틀한 능력입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "진정",
            "모든 폭발을 억제합니다."};

    public Bulter(String playerName) {
        super(playerName, AbilityInfo.Bulter, false, true, false, des);

        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityExplodeEvent event) {
        event.setCancelled(true);
        if (!event.getEntity().getType().equals(EntityType.FIREBALL))
            Bukkit.broadcastMessage( ChatColor.GREEN + "집사에 의해 폭발이 진정되었습니다.");
    }

}