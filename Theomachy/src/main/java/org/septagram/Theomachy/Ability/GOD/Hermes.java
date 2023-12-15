package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Hermes extends Ability {
    private final static String[] des = {
            AbilityInfo.Hermes.getName() + "는 여행자의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "민첩함",
            "이동 속도가 빠릅니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "비행",
            "5초간 비행할 수 있으며, 점프하면서 비행하면 바로 날 수 있습니다.",
            "비행 중에는 낙하 데미지를 받지 않습니다."};

	int flyTime;
    int delay;
    public Hermes(String playerName) {
        super(playerName, AbilityInfo.Hermes, true, true, true, des);
        Theomachy.log.info(playerName + abilityName);
		this.flyTime = 5;
        this.delay = 6;
        this.firstSkillCoolTime = 60;
        this.firstSkillStack = 10;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, firstSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, firstSkillStack, firstSkillCoolTime);
            player.setAllowFlight(true);
            player.setFlying(true);
            for (int count = flyTime; count > 0; count--) {
                int finalCount = count;
                Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                    player.sendMessage("비행시간이 " + ChatColor.AQUA + finalCount + ChatColor.WHITE + "초 남았습니다.");
                }, (flyTime - count) * 20L);
            }
			Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), ()->{
				player.sendMessage(ChatColor.RED + "비행시간이 종료되었습니다.");
				player.setAllowFlight(false);
				player.setFallDistance(0);
			},delay * 20L);

        }
    }

    public void buff() {
        Player player = GameData.OnlinePlayer.get(playerName);
        if (player != null) {
            Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), () -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
            }, 1 * 20, 6 * 20);
        }
    }
}
