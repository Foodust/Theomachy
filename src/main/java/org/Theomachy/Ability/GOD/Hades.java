package org.Theomachy.Ability.GOD;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.ENUM.AbilityCase;
import org.Theomachy.ENUM.AbilityInfo;
import org.Theomachy.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

public class Hades extends Ability {
    private final static String[] des = {
            AbilityInfo.Hades.getName() + "는 죽음의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "사망 지배",
            "사망 시 60% 확률로 아이템을 잃지 않습니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "나락 Ⅰ",
            "자신과 주변의 모든 것이 나락으로 떨어집니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "나락 Ⅱ",
            "자신을 제외한 주변의 모든 것이 나락으로 떨어집니다."};

    public Hades(String playerName) {
        super(playerName, AbilityInfo.Hades, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 100;
        this.rareSkillCoolTime = 300;
        this.normalSkillStack = 20;
        this.rareSkillStack = 35;

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
            Location location = player.getLocation();
            location.setY(-2.0d);
            List<Entity> entitylist = player.getNearbyEntities(2, 2, 2);
            for (Entity e : entitylist) {

                if (e instanceof LivingEntity) {
                    Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
                        e.teleport(location);
                    });
                    if (e.getType() == EntityType.PLAYER)
                        ((Player) e).sendMessage("죽음의 신의 능력에 의해 나락으로 떨어집니다.");
                }
            }
            Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
                player.teleport(location);
            });
        }
    }

    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            Location location = player.getLocation();
            location.setY(-2.0d);
            List<Entity> entitylist = player.getNearbyEntities(4, 4, 4);
            for (Entity e : entitylist) {
                if (e instanceof LivingEntity) {
                    Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
                        e.teleport(location);
                    });
                    if (e.getType() == EntityType.PLAYER)
                        ((Player) e).sendMessage(ChatColor.RED + "죽음의 신의 능력에 의해 나락으로 떨어집니다.");
                }
            }
        }
    }

    private ItemStack[] inventory;
    private ItemStack[] armor;

    public void passiveSkill(PlayerDeathEvent event) {
        Random r = new Random();
        if (r.nextInt(10) <= 6) {
            this.inventory = event.getEntity().getInventory().getContents();
            this.armor = event.getEntity().getInventory().getArmorContents();
            event.getDrops().clear();
        } else
            event.getEntity().sendMessage("아이템을 모두 잃었습니다.");
    }

    public void passiveSkill(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (inventory != null)
            player.getInventory().setContents(inventory);
        if (armor != null)
            player.getInventory().setArmorContents(armor);
        inventory = null;
        armor = null;
    }
}

