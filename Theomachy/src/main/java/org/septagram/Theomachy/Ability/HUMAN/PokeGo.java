package org.septagram.Theomachy.Ability.HUMAN;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.DB.AbilityData;
import org.septagram.Theomachy.Handler.CommandModule.AbilitySet;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;

public class PokeGo extends Ability {

    private final static String[] des = {
            AbilityInfo.PokeGo.getName() + "는 열심히 걸어다니면 능력을 잡을 수 있습니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "넌 내꺼야!",
            "5000걸음 걸으면 무작위로 능력을 얻을 수 있습니다.",
            "이는 블랙리스트를 무시합니다."};

    private int walking;
    private final int goal;

    public PokeGo(String playerName) {
        super(playerName, AbilityInfo.PokeGo, true, true, false, des);
        this.walking = 0;
        this.goal = 5000;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK ->
                        player.sendMessage(ChatColor.WHITE + String.valueOf(walking) + ChatColor.YELLOW + " 걸음 걸었습니다.");
            }
        }
    }

    public void passiveSkill(PlayerMoveEvent event) {

        if (walking >= goal) {

            Random random = new Random();

            int randomNumber = random.nextInt(2);

            int abilityNumber;
            if (randomNumber == 0) {
                abilityNumber = random.nextInt(AbilityData.GOD_ABILITY_NUMBER) + 1;
                AbilitySet.abilityAssignment(abilityNumber, playerName, event.getPlayer());
            } else {
                abilityNumber = random.nextInt(AbilityData.HUMAN_ABILITY_NUMBER) + 101;
                AbilitySet.abilityAssignment(abilityNumber, playerName, event.getPlayer());
            }
            event.getPlayer().sendMessage(ChatColor.YELLOW + " ★ 경  " + ChatColor.WHITE + goal + " 보 걷기에 성공했습니다!  " + ChatColor.YELLOW + " 축 ★");
            event.getPlayer().sendMessage(ChatColor.AQUA + AbilityInfo.getNameByIndex(abilityNumber) + ChatColor.WHITE + "!! 너로 정했다!!");
            event.getPlayer().sendMessage("능력이 할당되었습니다. /t help로 능력을 확인해보세요.");
        } else {
            walking++;
        }
    }

    public void initialize() {
        this.walking = 0;
    }
}
