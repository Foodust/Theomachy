package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.util.Vector;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;

import org.Theomachy.Checker.MouseEventChecker;



import java.util.Random;

public class Meteor extends Ability {
    private final static String[] des = {
            AbilityInfo.Meteor.getName() + "는 유성을 소환하는 능력입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "유성 소환",
            " 자신의 위치를 기준으로 2초 뒤 넓은 범위에 30개의 메테오를 떨어뜨립니다.",
            "블럭은 메테오의 폭발에 파괴되지 않습니다."};

    int normalCount;

    public Meteor(String playerName) {
        super(playerName, AbilityInfo.Meteor, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 100;
        this.normalSkillStack = 20;
        this.normalCount = 30;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Location location = player.getLocation();
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                World world = player.getWorld();
                Vector vector = new Vector(0d, -20d, 0d);
                Vector speed = new Vector(0d, -3d, 0d);
                for (int count = normalCount; count > 0; count--) {
                    Random random = new Random();
                    int X = random.nextInt(15) - 5;
                    int Z = random.nextInt(15) - 5;
                    Fireball fireball = world.spawn(location.add(X, 10, Z), Fireball.class);
                    fireball.setShooter(player);
                    fireball.setDirection(vector);
                    fireball.setVelocity(speed);
                    location.add(-X, 0, -Z);
                }
            }, 2 * 20);//}
        }
    }
}
