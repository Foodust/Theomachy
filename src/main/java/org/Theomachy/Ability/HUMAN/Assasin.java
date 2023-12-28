package org.Theomachy.Ability.HUMAN;

import java.util.List;

import org.Theomachy.Handler.Module.PlayerModule;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.GameData;
import org.Theomachy.Theomachy;
import org.Theomachy.Timer.CoolTimeTimer;

import org.Theomachy.Checker.DirectionChecker;
import org.Theomachy.Checker.MouseEventChecker;



public class Assasin extends Ability {

    private final static String[] des = {
            AbilityInfo.Assasin.getName() + "는 민첩한 몸놀림을 가지는 능력입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "더블 점프",
            "점프한 후 현재 보는 방향으로 점프를 한 번 더 할 수 있습니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "기습",
            "주변에 있는 적의 등으로 순간이동 합니다."};

    public Assasin(String playerName) {
        super(playerName, AbilityInfo.Assasin, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 1;
        this.normalSkillStack = 0;
        this.rareSkillCoolTime = 15;
        this.rareSkillStack = 15;

        this.rank = AbilityRank.B;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        Location temp = player.getLocation();
        Block b = temp.add(0, -1, 0).getBlock();
        if ((b.getType() == Material.AIR) || (b.getType() == Material.SNOW) || (b.getType() == Material.STONE_SLAB)) {
            if ((!CoolTimeTimer.commonSkillCoolTime.containsKey(playerName + "0") && (playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)))) {
                CoolTimeTimer.commonSkillCoolTime.put(playerName + "0", normalSkillCoolTime);
                World world = player.getWorld();
                Location location = player.getLocation();
                Vector v = player.getEyeLocation().getDirection();
                v.setY(0.5);
                player.setVelocity(v);
                world.playEffect(location, Effect.ENDER_SIGNAL, 1);
            }
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            boolean flag = true;
            List<Entity> entityList = player.getNearbyEntities(10, 10, 10);
            for (Entity entity : entityList) {
                if (entity instanceof Player target) {

                    String targetTeamName = GameData.playerTeam.get(target.getName());
                    String playerTeamName = GameData.playerTeam.get(player.getName());
                    if ((targetTeamName == null) || !(targetTeamName.equals(playerTeamName))) {
                        skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
                        Location fakeLocation = player.getLocation();
                        Location location = target.getLocation();
                        World world = player.getWorld();
                        List<Player> playerlist = world.getPlayers();
                        for (Player each : playerlist)
                            each.hidePlayer(Theomachy.getPlugin(), player);
                        switch (DirectionChecker.PlayerDirection(target)) {
                            case 0 -> location.add(0, 0, -1);
                            case 1 -> location.add(0.7, 0, -0.7);
                            case 2 -> location.add(1, 0, 0);
                            case 3 -> location.add(0.7, 0, 0.7);
                            case 4 -> location.add(0, 0, 1);
                            case 5 -> location.add(-0.7, 0, 0.7);
                            case 6 -> location.add(-1, 0, 0);
                            case 7 -> location.add(-0.7, 0, -0.7);
                        }
                        Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
                            player.teleport(location);
                        });
                        world.dropItem(fakeLocation.add(0, 1, 0), new ItemStack(Material.POPPY, 1));
                        for (Player each : playerlist)
                            each.showPlayer(Theomachy.getPlugin(), player);
                        flag = false;
                        break;
                    }
                }
            }
            if (flag)
                player.sendMessage("스킬을 사용 할 수 있는 상대가 없습니다.");
        }
    }
}
