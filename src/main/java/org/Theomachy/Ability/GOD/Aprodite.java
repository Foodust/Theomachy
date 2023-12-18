package org.Theomachy.Ability.GOD;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Utility.Checker.CoolTimeChecker;
import org.Theomachy.Utility.Checker.MouseEventChecker;
import org.Theomachy.Handler.Handler.PlayerHandler;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillCoolTimeHandler;

public class Aprodite extends Ability {

    private final static String[] des = {
            AbilityInfo.Aprodite.getName() + "는 미의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "매혹",
            "20블록 이내에 있는 사람들을 끌어올 수 있습니다.",
            "자신이 블록 위에 서 있고 웅크리지 않아야 발동합니다."};

    public Aprodite(String playerName) {
        super(playerName, AbilityInfo.Aprodite, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 500;
        this.normalSkillStack = 64;

        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            if (!player.isSneaking() && !player.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
                SkillCoolTimeHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                try {
                    List<Player> list = PlayerHandler.getNearByNotTeamMembers(player, 20, 20, 20);
                    for (Player e : list) {
                        Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
                            e.teleport(player);
                        });
                        e.sendMessage(ChatColor.YELLOW + "미의 여신 " + AbilityInfo.Aprodite.getName() + "에게 이끌려갑니다!");
                    }
                } catch (Exception ignored) {
                }
                player.sendMessage("미(美)로 다른 사람들을 홀렸습니다.");
            } else {
                player.sendMessage(ChatColor.RED + "웅크리고 있거나 발 밑의 블록이 없어 능력이 발동되지 않았습니다.");
            }
        }

    }

}
