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
            "맨손으로 공격시 인벤토리에 있는 검의 데미지를 줄 수 있습니다."
    };

    public Tajja(String playerName) {
        super(playerName, AbilityInfo.Tajja, false, true, false, des);
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        if (player.getName().equals(this.playerName)) {
            if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                int swordDamage = getSwordDamage(player);
                if(swordDamage == -1)
                    return;
                event.setDamage(swordDamage);
                player.sendMessage("동작그만, 밑장 빼기냐.");
            }
        }
    }

    public int getSwordDamage(Player player) {
        Inventory inventory = player.getInventory();
        for (ItemStack item : inventory.getContents()) {
            if (item != null &&
                    (item.getType() == Material.WOODEN_SWORD ||
                            item.getType() == Material.STONE_SWORD ||
                            item.getType() == Material.IRON_SWORD ||
                            item.getType() == Material.GOLDEN_SWORD ||
                            item.getType() == Material.DIAMOND_SWORD ||
                            item.getType() == Material.NETHERITE_SWORD)) {
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                return (int)Objects.requireNonNull(meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE)).stream().mapToDouble(AttributeModifier::getAmount).sum();
            }
        }
        return -1; // 해당하는 검을 찾지 못한 경우
    }
}
