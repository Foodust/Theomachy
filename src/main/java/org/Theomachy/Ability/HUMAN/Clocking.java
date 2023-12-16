package org.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Ability.ENUM.AbilityRank;
import org.Theomachy.DB.GameData;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Utility.CoolTimeChecker;
import org.Theomachy.Utility.EventFilter;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Utility.Skill;

public class Clocking extends Ability {
    private List<Player> targetList;
    private final static String[] des = {
            AbilityInfo.Clocking.getName() + " 일정 시간 자신의 몸을 숨길 수 있는 능력입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "감추기",
            "자신의 모습을 7초간 감출 수 있습니다.",
            "감춘 상태에서 상대방을 공격할 시 다시 모습이 나타나게 되며,",
            "공격 당한 상대는 20% 확률로 사망합니다."};

	private final int normalDuration;
    public Clocking(String playerName) {
        super(playerName, AbilityInfo.Clocking, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 60;
        this.normalSkillStack = 25;
		this.normalDuration = 7;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            targetList = player.getWorld().getPlayers();
            for (Player enemy : targetList)
                enemy.hidePlayer(Theomachy.getPlugin(), player);

            for (int count = normalDuration; count >= 0; count--) {
                int finalCount = count;
                if (flag) {
                    Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                        player.sendMessage(ChatColor.WHITE + "은신 시간이" + String.valueOf(finalCount) + "초 남았습니다.");
                    }, (normalDuration - count) * 20L);
                }
            }
            super.flag = true;

            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                GameData.PlayerAbility.get(player.getName()).flag = false;
                for (Player enemy : targetList)
                    enemy.showPlayer(Theomachy.getPlugin(), player);
            }, 8 * 20);
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        if (flag) {
            Player player = (Player) event.getDamager();
            if (player.getName().equals(this.playerName)) {
                targetList = player.getWorld().getPlayers();
                for (Player enemy : targetList)
                    enemy.showPlayer(Theomachy.getPlugin(), player);
                Random random = new Random();
                if (random.nextInt(5) == 0) {
                    Player target = (Player) event.getEntity();
                    event.setDamage(100);
                    target.sendMessage("알 수 없는 이유로 인해 즉사 하였습니다.");
                    player.sendMessage("상대가 즉사 하였습니다.");
                }
            }
            super.flag = false;
        }
    }
}
