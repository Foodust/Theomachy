package org.Theomachy.Ability.GOD;

import com.google.common.util.concurrent.AtomicDouble;
import de.slikey.effectlib.effect.WaveEffect;
import org.Theomachy.Data.TickData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Message.AbilityCoolTimeMessage;
import org.Theomachy.Timer.CoolTimeTimer;


import java.util.ArrayList;
import java.util.List;

public class Poseidon extends Ability {
    private final static String[] des = {
            AbilityInfo.Poseidon.getName() + "은 물의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "물 속성",
            "물 속에 있을 때 일정확률로 모든 피격을 33% 확률로 회피합니다.",
            "물 속에서 나온 직후 7초 동안 효과가 지속됩니다.",
            ChatColor.AQUA + "【고급】 " + ChatColor.WHITE + "워터 캐슬",
            "자신의 앞으로 쓰나미를 생성하며 앞으로 보냅니다.",
            "쓰나미는 벽을 뚫을 수 있습니다."};

    private final AbilityCoolTimeMessage abilityCoolTimeMessage = new AbilityCoolTimeMessage();

    public Poseidon(String playerName) {
        super(playerName, AbilityInfo.Poseidon, true, true, false, des);
        messageModule.logInfo(playerName + abilityName);

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 50;
        this.rareDuration = 7;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, skillMaterial)) {
            switch (event.getAction()) {
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    public void passiveSkill(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        if (event.getCause() == DamageCause.DROWNING) {
            event.setCancelled(true);
            CoolTimeTimer.commonSkillCoolTime.put(playerName + "0", 7);
            abilityCoolTimeMessage.PassiveEnable(player, 0);
        } else if (CoolTimeTimer.commonSkillCoolTime.containsKey(player.getName() + "0")) {
            int rn = (int) (Math.random() * 3);
            if (rn == 0) {
                event.setCancelled(true);
                messageModule.sendPlayer(player, "회피");
            }
        }
    }

    public void initialize() {
        Player player = GameData.onlinePlayer.get(playerName);
        taskModule.runBukkitTask(() -> {
            player.setMaximumAir(0);
            player.setRemainingAir(0);
        });
    }

    public void initializeReset() {
        Player player = GameData.onlinePlayer.get(playerName);
        taskModule.runBukkitTask(() -> {
            player.setMaximumAir(300);
            player.setRemainingAir(300);
        });
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            WaveEffect waveEffect = new WaveEffect(effectManage);
            waveEffect.setLocation(player.getLocation());
            waveEffect.height = 7f;
            waveEffect.particlesFront = 100;
            waveEffect.particlesBack = 20;
            waveEffect.period = 1;
            waveEffect.iterations = 3000;
            waveEffect.rows = 50;
            waveEffect.width = 10;
            waveEffect.start();
            taskModule.runBukkitTaskTimer(()->{
                Location location = waveEffect.getLocation();
                playerModule.damageNearEntity(player,location,rareDamage,5,10,5);
            },0L,0L);
            taskModule.runBukkitTaskLater(waveEffect::cancel, rareDuration * TickData.longTick);
        }
    }
}
