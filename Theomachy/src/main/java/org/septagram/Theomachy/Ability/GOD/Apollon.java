package org.septagram.Theomachy.Ability.GOD;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Apollon extends Ability {

    private final static String[] description = {
            AbilityInfo.Apollon.getName() + "은 태양의 신입니다.",
            NamedTextColor.AQUA + "【일반】 " + NamedTextColor.WHITE + "햇볕",
            "밤을 낮으로 바꿉니다.",
            NamedTextColor.RED + "【고급】 " + NamedTextColor.WHITE + "자외선",
            "밤을 낮으로 바꾸고 3초 뒤 온갖 물을 증발시키며, 다른 사람을 태웁니다.",
            "화염속성의 능력자, 그늘, 물속에 있는 플레이어는 피해를 입지 않습니다."};

    int sunTime;
    int delay;
    public Apollon(String playerName) {
        super(playerName, AbilityInfo.Apollon, true, false, false, description);
        Theomachy.log.info(playerName + abilityName);

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
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftClickAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightClickAction(player);
            }
        }
    }

    private void leftClickAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            World world = player.getWorld();
            world.setTime(6000);
            Bukkit.broadcast(Component.text( NamedTextColor.YELLOW + "태양의 신이 해를 띄웠습니다."));
        }
    }

    private void rightClickAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            World world = player.getWorld();
            world.setTime(6000);
            world.setStorm(false);
            Bukkit.broadcastMessage(NamedTextColor.RED + "태양이 매우 뜨거워집니다.");
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                for (int count = sunTime; count >= 0; count--) {
                    List<Player> playerList = GameData.OnlinePlayer.get(playerName).getWorld().getPlayers();
                    int finalCount = count;
                    Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                        for (Player players : playerList) {
                            if (!players.getName().equals(playerName) && players.getLocation().getBlock().getLightLevel() == 15)
                                players.setFireTicks(100);
                        }
                        player.sendMessage(NamedTextColor.WHITE + "지속시간이 " + finalCount + "초 남았습니다");
                    }, (sunTime - count) * 20L);
                }
            }, delay * 20L);
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                Bukkit.broadcastMessage("태양이 힘을 잃었습니다.");
                world.setTime(18000);
            }, (sunTime + delay + 1) * 20L);
        }
    }
}
