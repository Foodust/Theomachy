package org.Theomachy.Ability.GOD;

import org.Theomachy.Handler.Event.PlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;


import java.util.Objects;

public class Athena extends Ability {

    private final static String[] des = {
            AbilityInfo.Athena.getName() + "는 지혜의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "지식",
            "책을 얻습니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "강화",
            "게임 당 2번만 인챈트 테이블을 얻습니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "지혜",
            "다른 사람이 죽으면 경험치를 얻습니다. 자신이 죽으면 경험치가 사라집니다."};
    private int abilityLimitCounter;
    public Athena(String playerName) {
        super(playerName, AbilityInfo.Athena, true, true, false, des);
        messageModule.logInfo(playerName + abilityName);

        this.normalSkillCoolTime = 10;
        this.rareSkillCoolTime = 3;
        this.normalSkillStack = 5;
        this.rareSkillStack = 64;
        this.abilityLimitCounter = 2;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            player.getInventory().addItem(new ItemStack(Material.BOOK, 3));
        }
    }

    private void rightAction(Player player) {
        if (abilityLimitCounter > 0) {
            if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
                if (abilityLimitCounter > 1) {
                    skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
                    player.getInventory().addItem(new ItemStack(Material.ENCHANTING_TABLE, 1));
                    messageModule.sendPlayer(player,"남은 교환 횟수 : " + --abilityLimitCounter);
                } else {
                    skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, 0);
                    player.getInventory().addItem(new ItemStack(Material.ENCHANTING_TABLE, 1));
                    messageModule.sendPlayer(player,"남은 교환 횟수 : " + --abilityLimitCounter);
                }
            }
        } else
            messageModule.sendPlayer(player,"이 능력은 더이상 사용할 수 없습니다.");
    }

    public void passiveSkill(PlayerDeathEvent event) {
        if (Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause() != DamageCause.SUICIDE) {
            Player player = GameData.onlinePlayer.get(playerName);
            player.setLevel(player.getLevel() + 1);
        }
    }

    public void initialize() {
        PlayerEvent.PlayerDeathEventList.add(this);
    }

    public void initializeReset() {
        PlayerEvent.PlayerDeathEventList.remove(this);
    }
}

