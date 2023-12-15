package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Horeundal extends Ability {

    private final static String[] des = {
            AbilityInfo.Horeundal.getName() + "은 시간과 공간의 신입니다.",
            NamedTextColor.AQUA + "【일반】 " + NamedTextColor.WHITE + "시공 초월",
            "위치 기억 후 10초 뒤 되돌아옵니다. 되돌아 온 뒤에 5초간 투명해집니다."};

    public Horeundal(String playerName) {
        super(playerName, AbilityInfo.Horeundal, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 120;
        this.normalSkillStack = 32;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            Location loc = player.getLocation();
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            player.sendMessage("위치를 기억했습니다! 10초 뒤에 여기로 올 것입니다.");

            for (int count = 10; count >= 0; count--) {
                int finalCount = count;
                Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                    if (finalCount == 0) {
                        player.sendMessage("10초 전의 위치로 되돌아갑니다!");
                        player.teleport(loc);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5 * 20, 1));
                    } else {
                        player.sendMessage(NamedTextColor.AQUA + String.valueOf(finalCount) + NamedTextColor.WHITE + "초 뒤 되돌아갑니다.");
                    }
                }, (7 + (10 - count)) * 20L);
            }
        }
    }
}
