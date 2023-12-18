package org.Theomachy.Ability.GOD;

import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.Theomachy.Data.GameData;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

public class Hephaestus extends Ability {
    private final static String[] des = {
            AbilityInfo.Hephastus.getName() + "는 불의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "화염 속성",
            "불에 관한 데미지를 일절 받지 않으나, 물에 들어가면 데미지를 입습니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "용암",
            "블럭을 클릭하면 용암을 놓습니다. 놓은 용암은 사라지지 않습니다.",};

    public Hephaestus(String playerName) {
        super(playerName, AbilityInfo.Hephastus, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 30;
        this.normalSkillStack = 15;

        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        Location location = player.getTargetBlock(null, 5).getLocation();
        location.setY(location.getY() + 1);
        Block block = location.getBlock();
        if (block.getType() == Material.AIR) {
            if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
                SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                block.setBlockData(Bukkit.createBlockData(Material.LAVA));
                Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
                    new LavaTimer(block);
                }, 2 * 20, 0);
            }
        }
    }

    public void passiveSkill(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        DamageCause dc = event.getCause();
        if (dc.equals(DamageCause.LAVA) ||
                dc.equals(DamageCause.FIRE) ||
                dc.equals(DamageCause.FIRE_TICK)) {
            event.setCancelled(true);
            player.setFireTicks(0);
        } else if (dc.equals(DamageCause.DROWNING))
            event.setDamage(event.getDamage() / 2);
    }

    public void initialize() {
        Player player = GameData.OnlinePlayer.get(playerName);
        Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
            player.setMaximumAir(0);
            player.setRemainingAir(0);
        });

    }

    public void initializeReset() {
        Player player = GameData.OnlinePlayer.get(playerName);
        Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
            player.setMaximumAir(300);
            player.setRemainingAir(300);
        });
    }

    class LavaTimer extends TimerTask {
        Block block;

        LavaTimer(Block block) {
            this.block = block;
        }

        public void run() {
            block.setBlockData(Bukkit.createBlockData(Material.AIR));
        }
    }

}
