package org.septagram.Theomachy.Manager;

import java.util.ArrayList;
import java.util.Objects;

import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityTag;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.Gambling;
import org.septagram.Theomachy.Utility.Hangul;
import org.septagram.Theomachy.Handler.CommandModule.Blacklist;
import org.septagram.Theomachy.Handler.CommandModule.GUISetting;
import org.septagram.Theomachy.Handler.CommandModule.GameHandler;

public class EventManager implements Listener {
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball snowball) {
            if (snowball.getScoreboardTags().contains(AbilityTag.BONEATTACK.getTag())) {
                if (event.getHitEntity() instanceof LivingEntity target) {
                    target.damage(2);
                    int durationInSeconds = 3 * 20; // 위더 효과 지속 시간 (틱 단위로 20으로 나누어야됨)
                    int amplifier = 1; // 위더 효과 강도
                    PotionEffect poisonEffect = new PotionEffect(PotionEffectType.WITHER, durationInSeconds, amplifier);
                    target.addPotionEffect(poisonEffect);
                }
            }
        }
    }

    @EventHandler
    public static void onProjectileLaunch(ProjectileLaunchEvent event) {
        Bukkit.getScheduler().runTask(Theomachy.getPlugin(), () -> {
            if (event.getEntity() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getEntity();
                if (arrow.getShooter() instanceof Player) {
                    Player player = (Player) arrow.getShooter();
                    Ability ability = GameData.PlayerAbility.get(player.getName());
                    if (ability != null && ability.abilityCode == 118)
                        ability.passiveSkill(event, player);
                }
            }
        });
    }

    @EventHandler
    public static void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (GameHandler.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.PlayerAbility.get(playerName);
            if (ability != null && ability.activeType) {
                ability.activeSkill(event);
            }
        }
    }

    @EventHandler
    public static void onEntityDamage(EntityDamageEvent event) {
        if (GameHandler.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).passiveSkill(event);
            }
            if (event.getCause() == DamageCause.LIGHTNING && event.getEntity() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) event.getEntity();
                le.setNoDamageTicks(0);
            }
        }
    }

    @EventHandler
    public static void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        try {
            if (GameHandler.Start) {
                if (event.getDamager() instanceof Player &&
                        (event.getEntity() instanceof Player || event.getEntity() instanceof LivingEntity)) {

                    String entityName = (event.getEntity()).getName();
                    String DamageName = (event.getDamager()).getName();

                    Ability ability1 = GameData.PlayerAbility.get(entityName);
                    Ability ability2 = GameData.PlayerAbility.get(DamageName);

                    if (ability1 != null)
                        ability1.passiveSkill(event);
                    if (ability2 != null)
                        ability2.passiveSkill(event);

                } else if (event.getDamager() instanceof Arrow arrow && event.getEntity() instanceof Player) {
                    if (arrow.getShooter() instanceof Player player) {
                        String key = player.getName();
                        Ability ability = GameData.PlayerAbility.get(key);

                        if (ability != null &&
                                ability.abilityCode == AbilityInfo.Artemis.getIndex() ||
                                Objects.requireNonNull(ability).abilityCode == AbilityInfo.Archer.getIndex()) {
                            ability.passiveSkill(event);
                        }
                    }
                } else if (event.getDamager() instanceof Snowball snow && event.getEntity() instanceof Player) {
                    if (snow.getShooter() instanceof Player player) {

                        Ability ability = GameData.PlayerAbility.get(player.getName());

                        if (ability != null && ability.abilityCode == AbilityInfo.Snow.getIndex())
                            ability.passiveSkillSnow(event);
                    }
                }
            }
        } catch (Exception e) {
            Theomachy.log.info(e.getLocalizedMessage());
        }
    }

    public static ArrayList<Ability> PlayerDeathEventList = new ArrayList<Ability>();

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) {
        if (GameHandler.Start) {
            for (Ability e : PlayerDeathEventList)
                e.passiveSkill(event);
            Player player = event.getEntity();
            Ability ability = GameData.PlayerAbility.get(player.getName());
            if (ability != null)
                if (ability.abilityCode == 106 || ability.abilityCode == 3 || ability.abilityCode == 125)
                    ability.passiveSkill(event);


        }
    }

    @EventHandler
    public static void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (GameHandler.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).passiveSkill(event);
            }
        }
    }

    @EventHandler
    public static void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (GameHandler.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).passiveSkill(event);
            }
        }
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        if (GameHandler.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.PlayerAbility.get(playerName);
            if (ability != null)
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        if (GameHandler.Start) {
            Player player = event.getPlayer();
            if (Theomachy.IGNORE_BED) {
                if (GameData.PlayerTeam.containsKey(player.getName())) {
                    String teamName = GameData.PlayerTeam.get(player.getName());
                    Location respawnLocation = GameData.SpawnArea.get(teamName);
                    if (respawnLocation != null)
                        event.setRespawnLocation(respawnLocation);
                }
            } else {
                if (!event.isBedSpawn() && GameData.PlayerTeam.containsKey(player.getName())) {
                    String teamName = GameData.PlayerTeam.get(player.getName());
                    Location respawnLocation = GameData.SpawnArea.get(teamName);
                    if (respawnLocation != null)
                        event.setRespawnLocation(respawnLocation);
                }
            }
            Ability ability = GameData.PlayerAbility.get(player.getName());
            if (ability != null) {
                if (ability.buffType)
                    ability.buff();
                if (ability.abilityCode == 3 || ability.abilityCode == 123)
                    ability.passiveSkill(event);
            }
			
			/*if (!Theomachy.IGNORE_BED )
			{
				Location bedSpawnLocation = player.getBedSpawnLocation();
				if (bedSpawnLocation == null)
				{
					if (GameData.PlayerTeam.containsKey(player.getName()))
					{
						String teamName=GameData.PlayerTeam.get(player.getName());
						Location respawnLocation = GameData.SpawnArea.get(teamName);
						if (respawnLocation != null)
							event.setRespawnLocation(respawnLocation);
					}
				}	
			}
			else
			{
				
				if (GameData.PlayerTeam.containsKey(player.getName()))
				{
					String teamName=GameData.PlayerTeam.get(player.getName());
					Location respawnLocation = GameData.SpawnArea.get(teamName);
					if (respawnLocation != null)
						event.setRespawnLocation(respawnLocation);
				}
			}
			*/
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (GameHandler.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (GameHandler.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameData.OnlinePlayer.put(player.getName(), player);
        if (GameHandler.Start) {
            Ability ability = GameData.PlayerAbility.get(player.getName());
            if (ability != null && (ability.abilityCode == AbilityInfo.Poseidon.getIndex() || ability.abilityCode == AbilityInfo.Hephastus.getIndex()))
                ability.initialize();
        }
    }

    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        GameData.OnlinePlayer.remove(player.getName());
    }

    @EventHandler
    public static void onPlayerKick(PlayerKickEvent event) {
        Theomachy.log.info(event.reason().toString());
    }

    @EventHandler
    public static void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (GameHandler.Start && entity.getType() == EntityType.FIREBALL)
            event.blockList().clear();

        if (GameHandler.Start) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Ability ability = GameData.PlayerAbility.get(p.getName());
                if (ability != null && ability.abilityCode == 121) {
                    ability.passiveSkill(event);
                }
            }
        }

    }

    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent event) {

        if (GameHandler.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.PokeGo.getIndex())
                ability.passiveSkill(event);
        }

    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if (!NamedTextColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":: 블랙리스트 ::") &&
                !NamedTextColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::::: 능력 정보 ::::::::") &&
                !NamedTextColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::: 설정 ::::::") &&
                !NamedTextColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::::: 편의 기능 ::::::::") &&
                !NamedTextColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::::: 팁 ::::::::"))
            return;
        event.setCancelled(true);
        try {
            ItemStack wool = event.getCurrentItem();
            ItemMeta meta = wool.getItemMeta();

            if (NamedTextColor.stripColor(event.getView().getOriginalTitle()).equals(":: 블랙리스트 ::")) {

                if (wool.getDurability() == (short) 5) {
                    wool.setDurability((short) 14);
                    String[] y = meta.getDisplayName().split(" ");
                    int num = Integer.parseInt(y[y.length - 1]);
                    Blacklist.Blacklist.add(num);

                    char josa = '가';
                    try {
                        josa = Hangul.getJosa(y[0].charAt(y[0].toCharArray().length - 1), '이', '가');
                    } catch (Exception e) {
                    }
                    Bukkit.broadcastMessage(NamedTextColor.GREEN + "【 알림 】 " + NamedTextColor.WHITE + y[0] + josa + " " + NamedTextColor.RED + "블랙리스트" + NamedTextColor.WHITE + "에 등록되었습니다.");
                    return;
                }
                if (wool.getDurability() == (short) 14) {
                    wool.setDurability((short) 5);
                    String[] y = meta.getDisplayName().split(" ");
                    int num = Integer.parseInt(y[y.length - 1]);
                    Object o = num;
                    Blacklist.Blacklist.remove(o);

                    char josa = '가';
                    try {
                        josa = Hangul.getJosa(y[0].charAt(y[0].toCharArray().length - 1), '이', '가');
                    } catch (Exception ignored) {
                    }
                    Bukkit.broadcastMessage(NamedTextColor.GREEN + "【 알림 】 " + NamedTextColor.WHITE + y[0] + josa + " " + NamedTextColor.RED + "블랙리스트" + NamedTextColor.WHITE + "에서 벗어났습니다.");
                    return;
                }
            }

            if (NamedTextColor.stripColor(event.getView().getOriginalTitle()).equals(":::::::: 편의 기능 ::::::::")) {

                Player p = (Player) event.getWhoClicked();

                switch (NamedTextColor.stripColor(wool.getItemMeta().getDisplayName())) {
                    case "가챠 ★ 가챠":
                        Gambling.gambling(p);
                        break;
                }
            }

            if (NamedTextColor.stripColor(event.getView().getOriginalTitle()).equals(":::::: 설정 ::::::")) {

                GUISetting.guiListener(wool);

            }
        } catch (NullPointerException e) {
        }

    }

}
