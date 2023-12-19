package org.Theomachy.Ability.HUMAN;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.GameData;
import org.Theomachy.Theomachy;

import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillHandler;

public class Voodoo extends Ability {

    private final static String[] des = {
            AbilityInfo.Voodoo.getName() + "는 팻말을 이용해서 상대를 타격할 수 있는 능력입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "부두술",
            "팻말에 타격할 상대의 이름을 적으면 ",
            "그 아이디를 가진 플레이어는 팻말과 연결되며",
            "팻말을 두들겨 팰시 대상 플레이어가 데미지를 입습니다.",
            "설치후 7초동안 효과가 지속되며 7초 후에 자동으로 팻말이 부숴집니다.",
            "데미지는 무기의 영향을 받지 않습니다.",
            "쿨타임은 팻말을 든 채 좌클릭하면 좀 더 쉽게 확인 할 수 있습니다."};
    private String targetName;
    private Block postSign;
    private final int normalDuration;
    public Voodoo(String playerName) {
        super(playerName, AbilityInfo.Voodoo, true, true, false, des);
        this.normalSkillCoolTime = 180;
        this.normalSkillStack = 20;
        this.normalDuration = 7;
        this.targetName = null;
        this.postSign = null;
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(BlockPlaceEvent event) {
        Material material1 = event.getBlock().getType();
        if (material1 == Material.OAK_SIGN || material1 == Material.OAK_HANGING_SIGN || material1 == Material.OAK_WALL_HANGING_SIGN || material1 == Material.OAK_WALL_SIGN) {
            Player player = event.getPlayer();

            if (!(SkillHandler.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, material, normalSkillStack)))
                event.setCancelled(true);
        }
    }

    public void activeSkill(PlayerInteractEvent event) {
        if (this.postSign != null) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();
                assert block != null;
                if ((block.getType() == Material.OAK_SIGN || block.getType() == Material.OAK_HANGING_SIGN || block.getType() == Material.OAK_WALL_HANGING_SIGN || block.getType() == Material.OAK_WALL_SIGN) &&
                        this.postSign.getState().equals(block.getState())) {
                    Player player = GameData.onlinePlayer.get(targetName);
                    if (player != null)
                        player.damage(1, event.getPlayer());

                }
            }
        } else if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.OAK_SIGN) {
            Action action = event.getAction();
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                Player player = event.getPlayer();
                if (SkillHandler.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, material, normalSkillStack))
                    player.sendMessage("스킬을 사용 할 수 있습니다.");
            }
        }
    }

    public void passiveSkill(SignChangeEvent event) {
        Player player = event.getPlayer();
        String targetName = Objects.requireNonNull(event.getLine(0)).toString();
        Player target = GameData.onlinePlayer.get(targetName);
        if (target != null) {
            SkillHandler.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            this.targetName = targetName;
            this.postSign = event.getBlock();
            player.sendMessage(ChatColor.RED + targetName + ChatColor.WHITE + " 를(을) 팻말과 연결시켰습니다.");
            target.sendMessage(ChatColor.RED + "부두술사가 당신을 위협합니다.");
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                this.targetName = null;
                postSign.breakNaturally();
                postSign = null;
            }, normalDuration * 20L);
        } else
            player.sendMessage(ChatColor.RED + targetName + ChatColor.WHITE + "플레이어가 존재 하지 않습니다");
    }

}
