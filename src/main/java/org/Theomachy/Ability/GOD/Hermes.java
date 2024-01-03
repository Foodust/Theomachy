package org.Theomachy.Ability.GOD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;


public class Hermes extends Ability {
    private final static String[] des = {
            AbilityInfo.Hermes.getName() + "는 여행자의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "민첩함",
            "이동 속도가 빠릅니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "비행",
            "5초간 비행할 수 있으며, 점프하면서 비행하면 바로 날 수 있습니다.",
            "비행 중에는 낙하 데미지를 받지 않습니다."};

    private final int flyTime;
    private final int delay;
    private final int passiveDuration;
    public Hermes(String playerName) {
        super(playerName, AbilityInfo.Hermes, true, true, true, des);
        Theomachy.log.info(playerName + abilityName);
        this.flyTime = 5;
        this.delay = 6;
        this.normalSkillCoolTime = 60;
        this.normalSkillStack = 10;
        this.passiveDuration = 7;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            player.setAllowFlight(true);
            player.setFlying(true);
            for (int count = flyTime; count > 0; count--) {
                int finalCount = count;
                Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                    player.sendMessage("비행시간이 " + ChatColor.AQUA + finalCount + ChatColor.WHITE + "초 남았습니다.");
                }, (flyTime - count) * 20L);
            }
            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                player.sendMessage(ChatColor.RED + "비행시간이 종료되었습니다.");
                player.setAllowFlight(false);
                player.setFallDistance(0);
            }, delay * 20L);

        }
    }

    public void buff() {
        Player player = GameData.onlinePlayer.get(playerName);
        if (player != null) {
            Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, passiveDuration * 20, 0));
            }, 1 * 20, 6 * 20);
        }
    }
}
