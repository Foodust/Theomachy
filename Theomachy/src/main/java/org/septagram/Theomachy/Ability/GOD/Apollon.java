package org.septagram.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Apollon extends Ability {

    private final static String[] description = {
            "아폴론은 태양의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "햇볕",
            "밤을 낮으로 바꿉니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "자외선",
            "밤을 낮으로 바꾸고 3초 뒤 온갖 물을 증발시키며, 다른 사람을 태웁니다.",
            "화염속성의 능력자, 그늘, 물속에 있는 플레이어는 피해를 입지 않습니다."};

    public Apollon(String playerName) {
        super(playerName, "아폴론", 6, true, false, false, description);
        Theomachy.log.info(playerName + abilityName);

        this.firstSkillCoolTime = 90;
        this.secondSkillCoolTime = 180;
        this.firstSkillStack = 1;
        this.secondSkillStack = 25;
        this.rank = 2;

    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK -> leftClickAction(player);
                case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> rightClickAction(player);
            }
        }
    }

    private void leftClickAction(Player player) {
        if (CoolTimeChecker.Check(player, 1) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, firstSkillStack, 1, firstSkillCoolTime);
            World world = player.getWorld();
            world.setTime(6000);
            Bukkit.broadcastMessage(ChatColor.YELLOW + "태양의 신이 해를 띄웠습니다.");
        }
    }

    private void rightClickAction(Player player) {
        if (CoolTimeChecker.Check(player, 2) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, secondSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, secondSkillStack, 2, secondSkillCoolTime);
            World world = player.getWorld();
            world.setTime(6000);
            world.setStorm(false);
            Bukkit.broadcastMessage(ChatColor.RED + "태양이 매우 뜨거워집니다.");
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                for (int count = 15; count > 0; count--) {
                    List<Player> playerList = GameData.OnlinePlayer.get(playerName).getWorld().getPlayers();
                    Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(),()->{
                        for (Player players : playerList) {
                            if (!players.getName().equals(playerName) && players.getLocation().getBlock().getLightLevel() == 15)
                                players.setFireTicks(100);
                        }
                    },1 * 20);
                }
            }, 3 * 20);
            Bukkit.broadcastMessage("태양이 힘을 잃었습니다.");
            world.setTime(18000);
        }
    }
}
