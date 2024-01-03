package org.Theomachy.Handler.Event;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Handler.Module.PlayerModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;

public class PlayerEvent implements Listener {

    public static ArrayList<Ability> PlayerDeathEventList = new ArrayList<Ability>();
    private final PlayerModule playerModule = new PlayerModule();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (GameModule.Start) {
            for (Ability e : PlayerDeathEventList)
                e.passiveSkill(event);
            Player player = event.getEntity();
            Ability ability = GameData.playerAbility.get(player.getName());
            if (ability != null)
                if (ability.abilityCode == AbilityInfo.Creeper.getIndex() ||
                        ability.abilityCode == AbilityInfo.Hades.getIndex() ||
                        ability.abilityCode == AbilityInfo.Snow.getIndex()) {
                    ability.passiveSkill(event);
                }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (GameModule.Start) {
            Player player = event.getPlayer();
            if (Theomachy.IGNORE_BED) {
                if (GameData.playerTeam.containsKey(player.getName())) {
                    String teamName = GameData.playerTeam.get(player.getName());
                    Location respawnLocation = GameData.spawnArea.get(teamName);
                    if (respawnLocation != null)
                        event.setRespawnLocation(respawnLocation);
                }
            } else {
                if (!event.isBedSpawn() && GameData.playerTeam.containsKey(player.getName())) {
                    String teamName = GameData.playerTeam.get(player.getName());
                    Location respawnLocation = GameData.spawnArea.get(teamName);
                    if (respawnLocation != null)
                        event.setRespawnLocation(respawnLocation);
                }
            }
            Ability ability = GameData.playerAbility.get(player.getName());
            if (ability != null) {
                if (ability.buffType)
                    ability.buff();
                if (ability.abilityCode == AbilityInfo.Hades.getIndex() || ability.abilityCode == AbilityInfo.Goldspoon.getIndex())
                    ability.passiveSkill(event);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // 플레이어가 서버에 접속했을 때 실행될 작업을 정의합니다.

        Player player = event.getPlayer();

        Bukkit.broadcastMessage(player.getName() + TheomachyMessage.INFO_GREETING.getMessage());

        GameData.onlinePlayer.put(player.getName(), player);
        if (GameModule.Start) {
            Ability ability = GameData.playerAbility.get(player.getName());
            if (ability != null && (ability.abilityCode == AbilityInfo.Poseidon.getIndex() || ability.abilityCode == AbilityInfo.Hephastus.getIndex())) {
                ability.initialize();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        GameData.onlinePlayer.remove(player.getName());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Theomachy.log.info(event.getReason());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (GameModule.Start) {
            Ability ability = GameData.playerAbility.get(event.getPlayer().getName());
            assert ability != null;
            if (ability.abilityCode == AbilityInfo.PokeGo.getIndex() || ability.abilityCode == AbilityInfo.Tanjiro.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
            if (event.getEntity() instanceof Arrow arrow) {
                if (arrow.getShooter() instanceof Player player) {
                    Ability ability = GameData.playerAbility.get(player.getName());
                    if (ability != null && ability.abilityCode == AbilityInfo.Sniper.getIndex())
                        ability.passiveSkill(event, player);
                }
            }
        });
    }


    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (GameModule.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.playerAbility.get(playerName);
            if (ability != null && ability.activeType) {
                ability.activeSkill(event);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (GameModule.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.playerAbility.containsKey(playerName))
                    GameData.playerAbility.get(playerName).passiveSkill(event);
            }
        }
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (GameModule.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.playerAbility.containsKey(playerName))
                    GameData.playerAbility.get(playerName).passiveSkill(event);
            }
        }
    }

}
