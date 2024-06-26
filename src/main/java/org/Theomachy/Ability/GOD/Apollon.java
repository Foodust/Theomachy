package org.Theomachy.Ability.GOD;

import java.util.List;

import org.Theomachy.Data.TickData;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.GameData;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;


public class Apollon extends Ability {

    private final static String[] description = {
            AbilityInfo.Apollon.getName() + "은 태양의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "햇볕",
            "밤을 낮으로 바꿉니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "자외선",
            "밤을 낮으로 바꾸고 3초 뒤 온갖 물을 증발시키며, 다른 사람을 태웁니다.",
            "화염속성의 능력자, 그늘, 물속에 있는 플레이어는 피해를 입지 않습니다."};

    int sunTime;
    int delay;
    public Apollon(String playerName) {
        super(playerName, AbilityInfo.Apollon, true, false, false, description);
        messageModule.logInfo(playerName + abilityName);

        this.sunTime = 15;
        this.delay = 3;
        this.normalSkillCoolTime = 90;
        this.rareSkillCoolTime = 180;
        this.normalSkillStack = 1;
        this.rareSkillStack = 25;
        this.rank = AbilityRank.B;

    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftClickAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightClickAction(player);
            }
        }
    }

    private void leftClickAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            World world = player.getWorld();
            world.setTime(6000);
            Bukkit.broadcastMessage( ChatColor.YELLOW + "태양의 신" + AbilityInfo.Apollon.getName() +"이 해를 띄웠습니다.");
        }
    }

    private void rightClickAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            World world = player.getWorld();
            world.setTime(6000);
            world.setStorm(false);
            Bukkit.broadcastMessage(ChatColor.RED + "태양이 매우 뜨거워집니다.");
            taskModule.runBukkitTaskLater( () -> {
                for (int count = sunTime; count >= 0; count--) {
                    List<Player> playerList = GameData.onlinePlayer.get(playerName).getWorld().getPlayers();
                    int finalCount = count;
                    taskModule.runBukkitTaskLater( () -> {
                        for (Player players : playerList) {
                            if (!players.getName().equals(playerName) && players.getLocation().getBlock().getLightLevel() == 15)
                                players.setFireTicks(100);
                        }
                        messageModule.sendPlayer(player,ChatColor.WHITE + "지속시간이 " + finalCount + "초 남았습니다");
                    }, (sunTime - count) * TickData.longTick);
                }
            }, delay * TickData.longTick);
            taskModule.runBukkitTaskLater( () -> {
                Bukkit.broadcastMessage("태양이 힘을 잃었습니다.");
                world.setTime(18000);
            }, (sunTime + delay + 1) * TickData.longTick);
        }
    }
}
