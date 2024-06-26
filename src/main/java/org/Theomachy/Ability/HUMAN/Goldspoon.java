package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;

public class Goldspoon extends Ability {

    private final static String[] des = {
            AbilityInfo.Goldspoon.getName() + "를 물고 태어난 능력입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "재산 상속",
            "리스폰될 때마다 금레깅스를 받습니다. 물론, 상속세는 없습니다."};

    public Goldspoon(String playerName) {
        super(playerName, AbilityInfo.Goldspoon, false, true, false, des);
        this.rank = AbilityRank.C;
    }

    public void passiveSkill(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_LEGGINGS));
    }
}
