package org.Theomachy.Ability.GOD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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


public class Horeundal extends Ability {

    private final static String[] des = {
            AbilityInfo.Horeundal.getName() + "은 시간과 공간의 신입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "시공 초월",
            "위치 기억 후 10초 뒤 되돌아옵니다. 되돌아 온 뒤에 5초간 투명해집니다."};

    public Horeundal(String playerName) {
        super(playerName, AbilityInfo.Horeundal, true, false, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 120;
        this.normalSkillStack = 32;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            Location loc = player.getLocation();
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            player.sendMessage("위치를 기억했습니다! 10초 뒤에 여기로 올 것입니다.");

            for (int count = 10; count >= 0; count--) {
                int finalCount = count;
                Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                    if (finalCount == 0) {
                        player.sendMessage("10초 전의 위치로 되돌아갑니다!");
                        player.teleport(loc);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5 * 20, 1));
                    } else {
                        player.sendMessage(ChatColor.AQUA + String.valueOf(finalCount) + ChatColor.WHITE + "초 뒤 되돌아갑니다.");
                    }
                }, (10 - count) * 20L);
            }
        }
    }
}
