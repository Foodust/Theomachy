package org.septagram.Theomachy.Ability.GOD;

import java.util.Random;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.BlockFilter;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Zeus extends Ability {
    private final static String[] des = {
            AbilityInfo.Zeus.getName() + "는 신들의 왕이자, 번개의 신입니다.",
            NamedTextColor.YELLOW + "【패시브】 " + NamedTextColor.WHITE + "전기 속성",
            "패시브 능력으로 번개와 폭발 데미지를 받지 않습니다.",
            NamedTextColor.AQUA + "【일반】 " + NamedTextColor.WHITE + "번개 Ⅰ",
            "목표 지역에 번개를 떨어뜨립니다.",
            NamedTextColor.RED + "【고급】 " + NamedTextColor.WHITE + "태풍 Ⅱ",
            "목표 지역에 대량의 번개를 떨어뜨립니다."};

    private final int normalDistance;
    private final int rareDistance;
    private final int rareCount;
    public Zeus(String playerName) {
        super(playerName, AbilityInfo.Zeus, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);

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
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            Block block = player.getTargetBlock(null, normalDistance);
            if (BlockFilter.AirToFar(player, block)) {
                Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                World world = player.getWorld();
                Location location = block.getLocation();
                world.strikeLightning(location);
            }
        }
    }

    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            Block block = player.getTargetBlock(null, rareDistance);
            if (BlockFilter.AirToFar(player, block)) {
                Skill.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
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
