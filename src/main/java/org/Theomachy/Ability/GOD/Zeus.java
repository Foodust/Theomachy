package org.Theomachy.Ability.GOD;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Utility.BlockFilter;


public class Zeus extends Ability {
    private final static String[] des = {
            AbilityInfo.Zeus.getName() + "는 신들의 왕이자, 번개의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "전기 속성",
            "패시브 능력으로 번개와 폭발 데미지를 받지 않습니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "번개 Ⅰ",
            "목표 지역에 번개를 떨어뜨립니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "태풍 Ⅱ",
            "목표 지역에 대량의 번개를 떨어뜨립니다."};

    private final int rareCount;
    private final BlockFilter blockFilter = new BlockFilter();
    public Zeus(String playerName) {
        super(playerName, AbilityInfo.Zeus, true, true, false, des);
        messageModule.logInfo(playerName + abilityName);

        this.normalSkillCoolTime = 90;
        this.rareSkillCoolTime = 180;
        this.normalSkillStack = 15;
        this.rareSkillStack = 30;
        this.normalDistance = 50;
        this.rareDistance = 30;
        this.rareCount = 10;
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
            Block block = player.getTargetBlock(null, (int)normalDistance);
            if (blockFilter.AirToFar(player, block)) {
                skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                World world = player.getWorld();
                Location location = block.getLocation();
                world.strikeLightning(location);
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            Block block = player.getTargetBlock(null, (int)rareDistance);
            if (blockFilter.AirToFar(player, block)) {
                skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
                World world = player.getWorld();
                Location location = block.getLocation();
                Random random = new Random();
                for (int i = 0; i < rareCount; i++) {
                    int X = random.nextInt(11) - 5;
                    int Z = random.nextInt(11) - 5;
                    location.add(X, 0, Z);
                    world.strikeLightning(location);
                    location.add(-X, 0, -Z);
                }
            }
        }
    }

    public void passiveSkill(EntityDamageEvent event) {
        if (event.getCause() == DamageCause.LIGHTNING || event.getCause() == DamageCause.ENTITY_EXPLOSION)
            event.setCancelled(true);
    }
}
