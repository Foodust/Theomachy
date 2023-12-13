package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilitySet;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Horeundal extends Ability {

    private final static String[] des = {
            AbilitySet.Horeundal.getName() + "은 시간과 공간의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "시공 초월",
            "위치 기억 후 10초 뒤 되돌아옵니다. 되돌아 온 뒤에 5초간 투명해집니다."};

    public Horeundal(String playerName) {
        super(playerName, AbilitySet.Horeundal, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.firstSkillCoolTime = 120;
        this.firstSkillStack = 32;
        this.rank = 3;
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
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack)) {
            Location loc = player.getLocation();
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL,firstSkillStack,  firstSkillCoolTime);
            player.sendMessage("위치를 기억했습니다! 10초 뒤에 여기로 올 것입니다.");

            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                for (int count = 3; count >= 0; count--) {
                    int finalCount = count;
                    Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                        if (finalCount == 0) {
                            player.sendMessage("10초 전의 위치로 되돌아갑니다!");
                            player.teleport(loc);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5 * 20, 1));
                        } else {
                            player.sendMessage(ChatColor.AQUA + String.valueOf(finalCount) + ChatColor.WHITE + "초 뒤 되돌아갑니다.");
                        }
                    }, (7 + (3 - count)) * 20L);
                }
            }, 7 * 20);
        }
    }
}
