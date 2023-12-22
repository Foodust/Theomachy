package org.Theomachy.Ability.GOD;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;

import org.Theomachy.Checker.MouseEventChecker;



public class Demeter extends Ability {
    private final static String[] des = {
            AbilityInfo.Demeter.getName() + "는 곡식의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "수확",
            "빵을 얻을 수 있습니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "풍요",
            "항상 배고프지 않고, 체력 회복이 빠릅니다."};

    public Demeter(String playerName) {
        super(playerName, AbilityInfo.Demeter, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 30;
        this.normalSkillStack = 10;

        this.rank = AbilityRank.B;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK, RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> Action(player);
            }
        }
    }

    private void Action(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Inventory inventory = player.getInventory();
            inventory.addItem(new ItemStack(Material.BREAD, normalSkillStack));
        }
    }

    public void passiveSkill(FoodLevelChangeEvent event) {
        ((Player) event.getEntity()).setFoodLevel(20);
        event.setCancelled(true);
    }

    public void passiveSkill(EntityRegainHealthEvent event) {
        event.setAmount(event.getAmount() / 2);
    }
}
