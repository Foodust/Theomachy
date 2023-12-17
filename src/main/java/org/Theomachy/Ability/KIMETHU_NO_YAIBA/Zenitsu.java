package org.Theomachy.Ability.KIMETHU_NO_YAIBA;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Ability.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

import java.util.Objects;

public class Zenitsu extends Ability {
    private final static String[] des = {
            AbilityInfo.Zenitsu.getName(),
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "제1의 형 「벽력일섬」(霹靂一閃)",
            "전광석화의 기세로 접근해서 일직선의 일격에 목을 벤다."};
//            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "제7의 형 「화뢰신」(火 雷 神)",
//            "온 신경을 다리에 집중시켜서 눈 깜짝할 새도 없이 상현에게 참격을 날렸다."};
    private final int normalDistance;
    private final double rareJumpDistance;
    private final int rareDistance;
    private final int rareTime;

    public Zenitsu(String playerName) {
        super(playerName, AbilityInfo.Zenitsu, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 80;
        this.normalSkillStack = 16;
        this.normalDistance = 20;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 32;
        this.rareJumpDistance = 1.8f;
        this.rareDistance = 10;
        this.rareTime = 1;

        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
//                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Location location = player.getLocation();
            location.setPitch(0f);
            for (int distance = 0; distance < normalDistance; distance += normalDistance / 10) {
                Vector direction = location.getDirection().normalize();
                Location to = location.clone().add(direction.multiply(distance));
                Objects.requireNonNull(to.getWorld()).strikeLightning(to);
                player.teleport(to);
            }
        }
    }

    // 화뢰신 만들다가 포기함
    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            player.setVelocity(player.getVelocity().add(new Vector(0, rareJumpDistance, 0)));
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),()->{
                Location location = player.getLocation();
                for(int distance = 0 ;distance < rareDistance ; distance += rareDistance / 10){
                    Vector direction = location.getDirection().normalize();
                    Vector diagonalDirection = new Vector(direction.getX() * 0.75, -0.5, direction.getZ() * 0.75).normalize();
                    Location to = location.clone().add(diagonalDirection.multiply(distance));
                    World world = to.getWorld();
                    assert world != null;
                    world.strikeLightningEffect(to.add(-10, 0, 10));
                    player.teleport(to);
                }
            },rareTime * 20L);
        }
    }
}
