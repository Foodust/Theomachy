package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Ability.Ability;
import org.Theomachy.ENUM.AbilityCase;
import org.Theomachy.ENUM.AbilityInfo;
import org.Theomachy.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

public class Bomber extends Ability {

    private Location tntLocation;
    private final static String[] des = {
            AbilityInfo.Bomber.getName() + "는 폭발을 다루는 능력입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "폭파",
            "좌클릭으로 해당 위치에 보이지 않는 TNT를 설치하며",
            "우클릭으로 어디서든 폭발시킬 수 있습니다."};

    private final float normalDamage;
    public Bomber(String playerName) {
        super(playerName, AbilityInfo.Bomber, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 30;
        this.normalSkillStack = 25;
        this.normalDamage = 4.0f;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        Block block = player.getTargetBlock(null, 5);
        if (block.getType() != Material.AIR) {
            Location location = block.getLocation();
            location.setY(location.getY() + 1);
            this.tntLocation = location;
            player.sendMessage("해당 블럭에 폭탄이 설치되었습니다.");
        }
    }

    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            if (tntLocation != null) {
                SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                World world = player.getWorld();
                world.createExplosion(tntLocation, normalDamage, true);
                tntLocation = null;
                player.sendMessage("TNT가 폭발했습니다!");

            } else
                player.sendMessage("TNT가 설치되지 않았습니다.");
        }
    }
}
