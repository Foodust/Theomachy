package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;

import java.util.Objects;

public class Tajja extends Ability {

    private final static String[] des = {
            AbilityInfo.Tajja.getName() + "는 손놀림이 빠른 능력입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "밑장빼기",
            "맨손으로 공격시 인벤토리에 있는 검의 데미지의 1/2을 줄 수 있습니다."
    };

    public Tajja(String playerName) {
        super(playerName, AbilityInfo.Tajja, false, true, false, des);
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        if (player.getName().equals(this.playerName)) {
            if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                double swordDamage = getSwordDamage(player);
                if (swordDamage == -1)
                    return;
                event.setDamage(swordDamage);
                player.sendMessage("동작그만, 밑장 빼기냐.");
            }
        }
    }

    public double getSwordDamage(Player player) {
        double damage = -1;
        Inventory inventory = player.getInventory();
        for (ItemStack item : inventory.getContents()) {
            if (item != null)
                switch (item.getType()) {
                    case WOODEN_SWORD, GOLDEN_SWORD -> damage = (double) 4 / 2;
                    case STONE_SWORD -> damage = (double) 5 / 2 ;
                    case IRON_SWORD -> damage = (double) 6 / 2;
                    case DIAMOND_SWORD -> damage = (double) 7 / 2;
                    case NETHERITE_SWORD -> damage = (double) 8 / 2;
                }
        }
        return damage;
    }
}
