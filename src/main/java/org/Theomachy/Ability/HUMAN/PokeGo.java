package org.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import org.Theomachy.Handler.Handler.RandomSkillHandler;
import org.Theomachy.Handler.Module.AbilityModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.AbilityData;


public class PokeGo extends Ability {

    private final RandomSkillHandler randomSkillHandler = new RandomSkillHandler();
    private final static String[] des = {
            AbilityInfo.PokeGo.getName() + "는 열심히 걸어다니면 능력을 잡을 수 있습니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "넌 내꺼야!",
            "5000걸음 걸으면 무작위로 능력을 얻을 수 있습니다.",
            "이는 블랙리스트를 무시합니다.",
            "중복된 능력이 나올 수 있습니다."};

    private int walking;
    private final int goal;
    private final AbilityModule abilityModule = new AbilityModule();
    public PokeGo(String playerName) {
        super(playerName, AbilityInfo.PokeGo, true, true, false, des);
        this.walking = 0;
        this.goal = 5000;
        this.rank = AbilityRank.A;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK ->
                        player.sendMessage(ChatColor.WHITE + String.valueOf(walking) + ChatColor.YELLOW + " 걸음 걸었습니다.");
            }
        }
    }

    public void passiveSkill(PlayerMoveEvent event) {
        if (walking >= goal) {
            Integer randomAbilityNumber = randomSkillHandler.makeRandomAbilityList().get(0);
            abilityModule.abilityAssignment(randomAbilityNumber, playerName, event.getPlayer());
            event.getPlayer().sendMessage(ChatColor.YELLOW + " ★ 경  " + ChatColor.WHITE + goal + " 보 걷기에 성공했습니다!  " + ChatColor.YELLOW + " 축 ★");
            event.getPlayer().sendMessage(ChatColor.AQUA + AbilityInfo.getAbilityByIndex(randomAbilityNumber).getName() + ChatColor.WHITE + "!! 너로 정했다!!");
            event.getPlayer().sendMessage(TheomachyMessage.INFO_SET_PLAYER_ABILITY.getMessage());
            event.getPlayer().sendMessage(TheomachyMessage.EXPLAIN_CHECK_ABILITY.getMessage());
        } else {
            walking++;
        }
    }
    public void initialize() {
        this.walking = 0;
    }
}
