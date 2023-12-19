package org.Theomachy.Ability.GOD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Message.AbilityCoolTimeMesage;
import org.Theomachy.Timer.CoolTimeTimer;

import org.Theomachy.Checker.MouseEventChecker;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Handler.Handler.SkillHandler;

import java.util.ArrayList;
import java.util.List;

public class Poseidon extends Ability {
    private boolean flag = true;
    private final static String[] des = {
            AbilityInfo.Poseidon.getName() + "은 물의 신입니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "물 속성",
            "물 속에 있을 때 일정확률로 모든 피격을 33% 확률로 회피합니다.",
            "물 속에서 나온 직후 7초 동안 효과가 지속됩니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "워터 캐슬",
            "자신의 앞으로 물벽을 생성하며 이후 물벽에 접근하는 사람을 넉백시킵니다.",
            "물벽은 조약돌을 뚫을 수 있습니다."};

    public Poseidon(String playerName) {
        super(playerName, AbilityInfo.Poseidon, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);

        this.normalSkillCoolTime = 240;
        this.normalSkillStack = 5;

        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (MouseEventChecker.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (SkillHandler.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            if (flag) {
                SkillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                Location location = player.getLocation();
                Vector v = player.getEyeLocation().getDirection();
                v.setX(Math.round(v.getX()));
                v.setY(0);
                v.setZ(Math.round(v.getZ()));
                KnockBack k = new KnockBack(player, v);
                Wave w = new Wave(player, location, v);
                k.start();
                w.start();
            } else
                player.sendMessage("스킬의 지속시간이 끝나지 않아 사용할 수 없습니다.");
        }
    }

    class KnockBack extends Thread {
        final Player player;
        Vector vector;

        KnockBack(Player player, Vector v) {
            this.player = player;
            this.vector = v.clone();
            this.vector.multiply(5);
            this.vector.setY(2);
        }

        public void run() {
            flag = false;
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            for (int i = 0; i < 5; i++) {
                for (Player player : players)
                    if (player != this.player && (player.getLocation().getBlock().getType() == Material.WATER ||
                            player.getLocation().getBlock().getType() == Material.WATER))
                        player.setVelocity(vector);
                try {
                    sleep(1500);
                } catch (InterruptedException ignored) {
                }
            }
            flag = true;
        }
    }

    static class Wave extends Thread {
        final Player player;
        final Location location;
        final Location remove;
        final Vector v;


        Wave(Player player, Location location, Vector v) {
            this.player = player;
            this.location = location.add(0, 2, 0);
            this.remove = location.clone();
            this.v = v;
        }

        public void run() {
            try {
                for (int i = 0; i < 9; i++) {
                    Block block = location.add(v).getBlock();
                    if (block.getType() == Material.AIR || block.getType() == Material.COBBLESTONE) {
                        block.setBlockData(Bukkit.createBlockData(Material.WATER));
                        block.getState().update(true, false);
                    }
                }
                Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                    for (int i = 0; i < 9; i++) {
                        Block block = remove.add(v).getBlock();
                        if (block.getType() == Material.WATER) {
                            block.setBlockData(Bukkit.createBlockData(Material.AIR));
                        }
                    }
                }, 3 * 20);

            } catch (Exception ignored) {
            }
        }
    }


    public void passiveSkill(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        if (event.getCause() == DamageCause.DROWNING) {
            event.setCancelled(true);
            CoolTimeTimer.commonSkillCoolTime.put(playerName + "0", 7);
            AbilityCoolTimeMesage.PassiveEnable(player, 0);
        } else if (CoolTimeTimer.commonSkillCoolTime.containsKey(player.getName() + "0")) {
            int rn = (int) (Math.random() * 3);
            if (rn == 0) {
                event.setCancelled(true);
                player.sendMessage("회피");
            }
        }
    }

    public void initialize() {
        Player player = GameData.OnlinePlayer.get(playerName);

        Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{
            player.setMaximumAir(0);
            player.setRemainingAir(0);
        });
    }

    public void initializeReset() {
        Player player = GameData.OnlinePlayer.get(playerName);
        Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()-> {
            player.setMaximumAir(300);
            player.setRemainingAir(300);
        });
    }
}
