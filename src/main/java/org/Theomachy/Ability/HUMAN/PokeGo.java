package org.Theomachy.Ability.HUMAN;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.AbilityData;
import org.Theomachy.Handler.Command.AbilitySetCommand;
import org.Theomachy.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;

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
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK ->
                        player.sendMessage(ChatColor.WHITE + String.valueOf(walking) + ChatColor.YELLOW + " 걸음 걸었습니다.");
            }
        }
    }

    public void passiveSkill(PlayerMoveEvent event) {

        if (walking >= goal) {
            Random random = new Random();
            int randomNumber = random.nextInt(4);

            int abilityNumber = 0;
            if (randomNumber == 0) {
                abilityNumber = random.nextInt(AbilityData.GOD_ABILITY_NUMBER) + 1;
                AbilitySetCommand.abilityAssignment(abilityNumber, playerName, event.getPlayer());
            } else if (randomNumber == 1) {
                abilityNumber = random.nextInt(AbilityData.HUMAN_ABILITY_NUMBER) + 101;
                AbilitySetCommand.abilityAssignment(abilityNumber, playerName, event.getPlayer());
            } else if (randomNumber == 2) {
                abilityNumber = random.nextInt(AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER) + 301;
                AbilitySetCommand.abilityAssignment(abilityNumber, playerName, event.getPlayer());
            } else if (randomNumber == 3) {
                abilityNumber = random.nextInt(AbilityData.KIMETSU_NO_YAIBA_ABILITY_NUMBER) + 401;
                AbilitySetCommand.abilityAssignment(abilityNumber, playerName, event.getPlayer());
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
