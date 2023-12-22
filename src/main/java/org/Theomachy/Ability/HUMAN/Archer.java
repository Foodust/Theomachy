package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;

import org.Theomachy.Checker.MouseEventChecker;



public class Archer extends Ability {
    private final static String[] des = {
            AbilityInfo.Archer.getName() + "입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "정확함",
            "활 공격 데미지가 1.4배로 상승합니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "화살 생성",
            "화살을 만듭니다.",
            ChatColor.BLUE + "【고급】 " + ChatColor.WHITE + "활 생성",
            "활을 만듭니다."};

	private final double passiveDamage;
	private final int normalCount;
	private final int rareCount;

    public Archer(String playerName) {
        super(playerName, AbilityInfo.Archer, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);
		this.passiveDamage = 1.4f;

        this.normalSkillCoolTime = 10;
        this.normalSkillStack = 5;
		this.normalCount = 1;

		this.rareSkillCoolTime = 60;
        this.rareSkillStack = 15;
		this.rareCount = 1;

        this.rank = AbilityRank.B;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            World world = player.getWorld();
            Location location = player.getLocation();
            world.dropItem(location, new ItemStack(Material.ARROW, normalCount));
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            World world = player.getWorld();
            Location location = player.getLocation();
            world.dropItem(location, new ItemStack(Material.BOW, rareCount));
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        int damage = (int) (event.getDamage());
        event.setDamage((int) (damage * passiveDamage));
    }
}
