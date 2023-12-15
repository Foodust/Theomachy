package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Sniper extends Ability {
    public boolean ready = false;
    public boolean sniping = false;
    private final static String[] des = {
            AbilityInfo.Sniper.getName() + "는 빠른 화살을 이용해 상대방을 공격하는 능력입니다.",
            "게임 시작시 활 1개 화살 10개를 지급합니다. ",
            NamedTextColor.AQUA + "【일반】 " + NamedTextColor.WHITE + "스나이핑",
            "앉은 채(shift) 활을 들고 좌클릭하면 4초 뒤 스나이핑 모드가 활성화됩니다.",
            "스나이핑 모드일시 쏜 화살이 타겟방향으로 보이지 않는 속도로",
            "날아가며 맞은 적은 약 100~200의 데미지를 입습니다."};

    public Sniper(String playerName) {
        super(playerName, AbilityInfo.Sniper, true, false, false, des);
        this.normalSkillCoolTime = 50;
        this.normalSkillStack = 5;

        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BOW)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (player.isSneaking() && !ready) {
            ready = true;
            for (int count = 4; count >= 0; count--) {
                int finalCount = count;
                Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                    if (finalCount > 0)
                        player.sendMessage(NamedTextColor.RED + "[스나이핑 모드] " + NamedTextColor.WHITE + finalCount + "초 전");
                    else {
                        player.sendMessage(NamedTextColor.RED + "[스나이핑 모드] " + NamedTextColor.AQUA + "ON");
                        sniping = true;
                    }
                    if (!player.isSneaking()) {
                        ready = false;
                        sniping = false;
                        player.sendMessage(NamedTextColor.RED + "[스나이핑 모드] " + NamedTextColor.RED + "OFF");
                    }
                }, (4 - count) * 20L);
            }
        } else {
            CoolTimeChecker.Check(player, AbilityCase.NORMAL);
        }
    }

    @Override
    public void passiveSkill(ProjectileLaunchEvent event, Player player) {
        if (this.sniping && (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, material, normalSkillStack))) {
            Entity entity = event.getEntity();
            if (entity instanceof Arrow) {
                Skill.Use(player, material, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                entity.remove();
                Arrow arrow = player.launchProjectile(Arrow.class);
                arrow.setVelocity(player.getEyeLocation().getDirection().multiply(100));
            }
        }
    }

    @Override
    public void initialize() {
        Player player = GameData.OnlinePlayer.get(this.playerName);
        player.getInventory().addItem(new ItemStack(Material.BOW, 1));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 10));
    }
}
