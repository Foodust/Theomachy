package org.Theomachy.Manager;

import java.util.ArrayList;
import java.util.Objects;

import org.Theomachy.Handler.Handler.PlayerHandler;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
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
import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityTag;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Data.GameData;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.Gambling.Gambling;
import org.Theomachy.Utility.Hangul;
import org.Theomachy.Handler.Ability.Blacklist;
import org.Theomachy.Handler.CommandModule.SettingCommand;
import org.Theomachy.Handler.CommandModule.StartStopCommand;

public class EventManager implements Listener {

    @EventHandler
    public void onPlayerDamageByMagma(BlockDamageEvent event){
            Block block = event.getBlock();
            if (block.getType() == Material.MAGMA_BLOCK) {
                // 블록 위에 있는 플레이어의 피해를 방지
                if (event.getPlayer().getLocation().getBlock().getType() == Material.MAGMA_BLOCK) {
                    event.setCancelled(true); // 데미지 이벤트 취소
                }
            }
    }
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
            if (event.getEntity() instanceof Arrow arrow) {
                if (arrow.getShooter() instanceof Player player) {
                    Ability ability = GameData.PlayerAbility.get(player.getName());
                    if (ability != null && ability.abilityCode == 118)
                        ability.passiveSkill(event, player);
                }
            }
        });
    }

    @EventHandler
    public static void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (StartStopCommand.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.PlayerAbility.get(playerName);
            if (ability != null && ability.activeType) {
                ability.activeSkill(event);
            }
        }
    }

    @EventHandler
    public static void onEntityDamage(EntityDamageEvent event) {
        if (StartStopCommand.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).passiveSkill(event);
            }
            if (event.getCause() == DamageCause.LIGHTNING && event.getEntity() instanceof LivingEntity le) {
                le.setNoDamageTicks(0);
            }
        }
    }

    @EventHandler
    public static void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        try {
            if (StartStopCommand.Start) {
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
        if (StartStopCommand.Start) {
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
        if (StartStopCommand.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).passiveSkill(event);
            }
        }
    }

    @EventHandler
    public static void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (StartStopCommand.Start) {
            if (event.getEntity() instanceof Player) {
                String playerName = ((Player) event.getEntity()).getName();
                if (GameData.PlayerAbility.containsKey(playerName))
                    GameData.PlayerAbility.get(playerName).passiveSkill(event);
            }
        }
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        if (StartStopCommand.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.PlayerAbility.get(playerName);
            if (ability != null)
                ability.passiveSkill(event);

            Block block = event.getBlock();
            if (block.getType() == Material.DIAMOND_BLOCK) {
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName()+ ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName()+ ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName()+ ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName()+ ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName()+ ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Firework firework = event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), Firework.class);
                EntityManager.spawnRandomFirework(firework);
            }
        }
    }

    @EventHandler
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        if (StartStopCommand.Start) {
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
        if (StartStopCommand.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (StartStopCommand.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameData.OnlinePlayer.put(player.getName(), player);
        if (StartStopCommand.Start) {
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
        Theomachy.log.info(event.getReason());
    }

    @EventHandler
    public static void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (StartStopCommand.Start && entity.getType() == EntityType.FIREBALL)
            event.blockList().clear();

        if (StartStopCommand.Start) {
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

        if (StartStopCommand.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.PokeGo.getIndex())
                ability.passiveSkill(event);
        }

    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if (!ChatColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":: 블랙리스트 ::") &&
                !ChatColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::::: 능력 정보 ::::::::") &&
                !ChatColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::: 설정 ::::::") &&
                !ChatColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::::: 편의 기능 ::::::::") &&
                !ChatColor.stripColor(event.getView().getOriginalTitle()).equalsIgnoreCase(":::::::: 팁 ::::::::"))
            return;
        event.setCancelled(true);
        try {
            ItemStack wool = event.getCurrentItem();
            assert wool != null;
            ItemMeta meta = wool.getItemMeta();

            if (ChatColor.stripColor(event.getView().getOriginalTitle()).equals(":: 블랙리스트 ::")) {

                if (wool.getDurability() == (short) 5) {
                    wool.setDurability((short) 14);
                    String[] y = Objects.requireNonNull(meta.getDisplayName()).split(" ");
                    int num = Integer.parseInt(y[y.length - 1]);
                    Blacklist.Blacklist.add(num);

                    char josa = '가';
                    try {
                        josa = Hangul.getJosa(y[0].charAt(y[0].toCharArray().length - 1), '이', '가');
                    } catch (Exception ignored) {
                    }
                    Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + y[0] + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에 등록되었습니다.");
                    return;
                }
                if (wool.getDurability() == (short) 14) {
                    wool.setDurability((short) 5);
                    String[] y = Objects.requireNonNull(meta.getDisplayName()).split(" ");
                    Object o = Integer.parseInt(y[y.length - 1]);
                    Blacklist.Blacklist.remove(o);

                    char josa = '가';
                    try {
                        josa = Hangul.getJosa(y[0].charAt(y[0].toCharArray().length - 1), '이', '가');
                    } catch (Exception ignored) {
                    }
                    Bukkit.broadcastMessage(ChatColor.GREEN + "【 알림 】 " + ChatColor.WHITE + y[0] + josa + " " + ChatColor.RED + "블랙리스트" + ChatColor.WHITE + "에서 벗어났습니다.");
                    return;
                }
            }

            if (ChatColor.stripColor(event.getView().getOriginalTitle()).equals(":::::::: 편의 기능 ::::::::")) {

                Player p = (Player) event.getWhoClicked();

                switch (ChatColor.stripColor(Objects.requireNonNull(wool.getItemMeta().getDisplayName()))) {
                    case "가챠 ★ 가챠" -> {
                        Gambling.gambling(p);
                    }

                }
            }

            if (ChatColor.stripColor(event.getView().getOriginalTitle()).equals(":::::: 설정 ::::::")) {

                SettingCommand.guiListener(wool);

            }
        } catch (NullPointerException ignored) {
        }

    }

}
