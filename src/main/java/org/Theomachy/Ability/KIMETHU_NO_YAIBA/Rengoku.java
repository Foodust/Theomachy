package org.Theomachy.Ability.KIMETHU_NO_YAIBA;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Ability.ENUM.AbilityRank;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Rengoku extends Ability {
    private final static String[] des = {
            AbilityInfo.Rengoku.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "제2의 형 「상승염천」(昇り炎天)",
            "칼을 밑에서 위로 휘둘러, 맹렬한 불꽃처럼 베는 기술.",
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "오의 • 제9의 형 「연옥」(煉獄)",
            "작열하는 지옥불처럼 맹렬히 돌진해서, 굉음과 함께 상대방을 도려낸다. "};

    private final int normalDistance;
    private final int rareDistance;
    private final int rareTime;

    public Rengoku(String playerName) {
        super(playerName, AbilityInfo.Rengoku, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 80;
        this.normalSkillStack = 16;
        this.normalDistance = 20;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 32;
        this.rareDistance = 20;
        this.rareTime = 1;

        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);

        }
    }

    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            Location location = player.getLocation();
            location.setPitch(0f);
            for (float distance = 0; distance < rareDistance; distance += (float) rareDistance / 20) {
                Vector direction = location.getDirection().normalize();
                Vector diagonalDirection = new Vector(direction.getX() * 0.75, -0.5, direction.getZ() * 0.75).normalize();
                Location to = location.clone().add(diagonalDirection.multiply(distance));
                player.teleport(to);
            }
        }
    }
}
