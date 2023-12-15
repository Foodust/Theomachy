package org.septagram.Theomachy.Ability.HUMAN;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityExplodeEvent;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;

public class Bulter extends Ability {

    private final static String[] des = {
            AbilityInfo.Bulter.getName() + "는 굉장히 젠틀한 능력입니다.",
            NamedTextColor.YELLOW + "【패시브】 " + NamedTextColor.WHITE + "진정",
            "모든 폭발을 억제합니다."};

    public Bulter(String playerName) {
        super(playerName, AbilityInfo.Bulter, false, true, false, des);

        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityExplodeEvent event) {
        event.setCancelled(true);
        if (!event.getEntity().getType().equals(EntityType.FIREBALL))
            Bukkit.broadcast(Component.text( NamedTextColor.GREEN + "집사에 의해 폭발이 진정되었습니다."));
    }

}