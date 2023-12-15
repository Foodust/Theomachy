package org.septagram.Theomachy.Ability.HUMAN;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;

public class Reflection extends Ability {
    private final static String[] des = {
            AbilityInfo.Reflection.getName() + "은 자신이 받은 데미지의 반을 반사합니다.",
            NamedTextColor.YELLOW + "【패시브】 " + NamedTextColor.WHITE + "작용 - 반작용",
            "자신이 받은 데미지의 반을 상대방에게 반사합니다.",
            "화살 등 간접적으로 받는 데미지는 반사하지 못합니다.",
            "반사한 데미지는 방어를 무시합니다."};

    public Reflection(String playerName) {
        super(playerName, AbilityInfo.Reflection, false, true, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        if (event.getEntity().getName().equals(playerName)) {
            Player enemy = (Player) event.getDamager();
            enemy.damage(event.getDamage() / 2);
        }
    }
}
