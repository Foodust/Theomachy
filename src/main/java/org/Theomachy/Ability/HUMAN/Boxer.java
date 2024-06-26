package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;

public class Boxer extends Ability {

    private final static String[] des = {
            AbilityInfo.Boxer.getName() + "는 빠른 주먹을 사용하는 능력입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "속성 가격",
            "맨손 데미지의 딜레이가 사라집니다."};

    public Boxer(String playerName) {
        super(playerName, AbilityInfo.Boxer, false, true, false, des);
        messageModule.logInfo(playerName + abilityName);

        this.rank = AbilityRank.S;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR && player.getName().equals(this.playerName)) {
            Player target = (Player) event.getEntity();
            if (!target.isBlocking())
                target.setNoDamageTicks(0);
        }
    }

}
