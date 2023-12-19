package org.Theomachy.Ability.HUMAN;

import org.Theomachy.Handler.Handler.SkillHandler;

import org.Theomachy.Checker.MouseEventChecker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.GameData;
import org.Theomachy.Utility.*;

public class Snow extends Ability {

    private final static String[] des = {
            AbilityInfo.Snow + "는 눈을 이용합니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "폭설",
            "죽을 때마다 공격 지수가 1씩 상승 하고 최대 7까지 상승합니다.",
            "눈덩이를 맞춰도 상대가 밀려나지 않습니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "얼음 속성",
            "불에 의한 데미지를 2배로 받습니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "공격 지수",
            "현재 공격 지수를 확인 합니다.",
            ChatColor.AQUA + "【고급】 " + ChatColor.WHITE + "눈덩이 변환",
            "눈덩이를 1개 얻습니다.",};

    private int passiveDamage;
	private final int rareCount;

    public Snow(String playerName) {
        super(playerName, AbilityInfo.Snow, true, true, false, des);
        this.normalSkillCoolTime = 0;
        this.normalSkillStack = 0;
        this.rareSkillCoolTime = 20;
        this.rareSkillStack = 5;
		this.rareCount = 3;
        this.passiveDamage = 0;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> player.sendMessage("공격 지수 : " + passiveDamage);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void rightAction(Player player) {
        if (SkillHandler.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, material, rareSkillStack)) {
            SkillHandler.Use(player, material, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            World world = player.getWorld();
            Location location = player.getLocation();
            world.dropItem(location, new ItemStack(Material.SNOWBALL, rareCount));
        }
    }

    public void passiveSkill(PlayerDeathEvent event) {
        if (passiveDamage < 8) {
            event.getEntity().sendMessage(ChatColor.RED + "공격 지수가 증가하고 있습니다!");
            passiveDamage++;
        }
    }

    public void passiveSkillSnow(EntityDamageByEntityEvent event) {
        Player p = (Player) event.getEntity();
        event.setCancelled(true);
        p.damage(passiveDamage);
    }

    public void passiveSkill(EntityDamageEvent event) {
        DamageCause dc = event.getCause();
        if (dc.equals(DamageCause.FIRE_TICK) || dc.equals(DamageCause.LAVA) || dc.equals(DamageCause.FIRE)) {
            event.setDamage(event.getDamage() * 2);
        }
    }

    @Override
    public void initialize() {
        GameData.onlinePlayer.get(playerName).getInventory().addItem(new ItemStack(Material.SNOWBALL, 8));
    }
}
