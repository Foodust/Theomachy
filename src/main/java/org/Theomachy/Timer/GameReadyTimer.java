package org.Theomachy.Timer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimerTask;

import org.Theomachy.Handler.Module.game.GameModule;
import org.Theomachy.Handler.Module.game.PlayerModule;
import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Handler.Module.source.TaskModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Theomachy;
import org.Theomachy.Data.GameData;


public class GameReadyTimer extends TimerTask {
    private int count = 1;
    private final List<Player> playerList;
    private final String[] setting = new String[9];
    private Difficulty difficulty;
    private final World world;
    private final PlayerModule playerModule = new PlayerModule();
    private final MessageModule messageModule = new MessageModule();
    private final TaskModule taskModule = new TaskModule();

    public GameReadyTimer() {
        this.playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        setting[0] = Theomachy.STARTING_INVENTORY_CLEAR ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        setting[1] = Theomachy.STARTING_GIVE_ITEM ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        setting[2] = Theomachy.IGNORE_BED ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        setting[3] = Theomachy.SERVER_AUTO_SAVE ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        setting[4] = Theomachy.ANIMAL_SPAWN ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        setting[5] = Theomachy.MONSTER_SPAWN ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        setting[7] = Theomachy.STARTING_ENTITY_CLEAR ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        setting[8] = Theomachy.DEBUG ? ChatColor.AQUA + TheomachyMessage.COMMAND_ON.getMessage() : ChatColor.RED + TheomachyMessage.COMMAND_OFF.getMessage();
        difficulty = null;
        switch (Theomachy.DIFFICULTY) {
            case 0 -> {
                setting[6] = TheomachyMessage.DIFFICULT_PEACEFUL.getMessage();
                difficulty = Difficulty.PEACEFUL;
            }
            case 2 -> {
                setting[6] = TheomachyMessage.DIFFICULT_NORMAL.getMessage();
                difficulty = Difficulty.NORMAL;
            }
            case 3 -> {
                setting[6] = TheomachyMessage.DIFFICULT_HARD.getMessage();
                difficulty = Difficulty.HARD;
            }
            default -> {
                setting[6] = TheomachyMessage.DIFFICULT_EASY.getMessage();
                difficulty = Difficulty.EASY;
            }
        }
        world = Bukkit.getWorlds().get(0);
    }

    @Override
    public void run() {
        if (GameModule.Ready && count < 35) {
            taskModule.runBukkitTask( () -> {
                switch (count) {
                    case 1 -> {
                        messageModule.broadcastMessage(ChatColor.RED + "신들의 전쟁 플러그인은 스카이블럭 전용이며 야생,하드코어로는 부적합합니다");
                        messageModule.broadcastMessage(ChatColor.RED + "이 점을 숙지 하시고 게임을 즐기시길 바랍니다.");
                    }
                    case 3 -> {
                        messageModule.broadcastMessage(ChatColor.AQUA + "플러그인이 배포 중인 곳");
                        messageModule.broadcastMessage(ChatColor.RED + "현재 배포하지 않음");
                    }
                    case 8 -> {
                        messageModule.broadcastMessage(ChatColor.GREEN + "****** 서버 세팅상태 ******");
                        messageModule.broadcastMessage(ChatColor.WHITE + "게임 시작 시 인벤토리 클리어 : " + setting[0]);
                        messageModule.broadcastMessage(ChatColor.WHITE + "게임 시작 시 기본 아이템 지급 : " + setting[1]);
                        messageModule.broadcastMessage(ChatColor.WHITE + "게임 시작 시 엔티티 삭제 : " + setting[7]);
                        messageModule.broadcastMessage(ChatColor.WHITE + "부활 시 침대 무시 : " + setting[2]);
                        messageModule.broadcastMessage(ChatColor.WHITE + "서버 자동 저장 : " + setting[3]);
                        messageModule.broadcastMessage(ChatColor.WHITE + "동물 스폰 : " + setting[4]);
                        messageModule.broadcastMessage(ChatColor.WHITE + "몬스터 스폰 : " + setting[5]);
                        messageModule.broadcastMessage(ChatColor.WHITE + "서버 난이도 : " + setting[6]);
                        messageModule.broadcastMessage(ChatColor.GREEN + "***************************");
                    }
                    case 14 -> {
                        messageModule.broadcastMessage(ChatColor.DARK_GREEN + "현재 인식된 플레이어 목록");
                        int i = 1;
                        for (Player player : playerList) {
                            messageModule.broadcastMessage(i++ + ".  " + ChatColor.GOLD + player.getName());
                        }
                    }
                    case 16 -> {
                        messageModule.broadcastMessage(ChatColor.AQUA + "현재 능력이 적용되지 않은 플레이어 목록");
                        int j = 1;
                        for (Player player : playerList) {
                            if (!GameData.playerAbility.containsKey(player.getName())) {
                                messageModule.broadcastMessage(j + ".  " + ChatColor.GOLD + player.getName());
                            }
                        }
                    }
                    case 18 -> {
                        messageModule.broadcastMessage(ChatColor.BLUE + "현재 팀이 적용되지 않은 플레이어 목록");
                        int k = 1;
                        for (Player player : playerList) {
                            if (!GameData.playerTeam.containsKey(player.getName())) {
                                messageModule.broadcastMessage(k + ".  " + ChatColor.GOLD + player.getName());
                            }
                        }
                    }
                    case 24 -> {
                        messageModule.broadcastMessage("원작자 : " + ChatColor.AQUA + "Septagram(칠각별)");
                        messageModule.broadcastMessage(ChatColor.RED + "[현재 삭제됨]" + ChatColor.WHITE + "원작자 블로그 : http://blog.naver.com/septagram/");
                        messageModule.broadcastMessage("의견 및 버그 제보는");
                        messageModule.broadcastMessage("가장 최근 수정자 : " + ChatColor.DARK_GREEN + "seunhoo@naver.com" + ChatColor.WHITE + "으로 보내주세요.");
                    }
                    case 26 -> messageModule.broadcastMessage(ChatColor.AQUA + "잠시 후 능력이 활성화 되며 팀 스폰지역으로 텔레포트 됩니다");
                    case 28 -> messageModule.broadcastMessage(ChatColor.RED + "5초 전");
                    case 29 -> messageModule.broadcastMessage(ChatColor.RED + "4초 전");
                    case 30 -> messageModule.broadcastMessage(ChatColor.RED + "3초 전");
                    case 31 -> messageModule.broadcastMessage(ChatColor.RED + "2초 전");
                    case 32 -> messageModule.broadcastMessage(ChatColor.RED + "1초 전");
                    case 33 -> {
                        if (Theomachy.STARTING_ENTITY_CLEAR) {
                            try {
                                List<Entity> entities = world.getEntities();
                                for (Entity entity : entities) {
                                    if (entity instanceof Item || entity instanceof Monster || entity instanceof Animals)
                                        entity.remove();
                                }
                            } catch (NullPointerException ignored) {
                            }
                        }
                        Location spawnLocation = world.getSpawnLocation();
                        while (true) {
                            if (spawnLocation.getBlock().getType() == Material.AIR)
                                break;
                            else
                                spawnLocation.setY(spawnLocation.getY() + 1);
                        }
                        messageModule.broadcastMessage(ChatColor.AQUA + "스폰 지역으로 텔레포트 합니다");
                        for (Player player : playerList) {
                            player.setFoodLevel(20);
                            player.setSaturation(10f);
                            player.setLevel(0);
                            player.setExhaustion(0.0F);
                            player.setExp(0.0F);
                            player.setHealth(20);
                            playerModule.giveStartingItem(player);
                            String teamName = GameData.playerTeam.get(player.getName());
                            if (teamName != null) {
                                Location location = GameData.spawnArea.get(teamName);
                                if (location != null)
                                        player.teleport(location);
                                else {
                                    messageModule.sendPlayer(player,ChatColor.RED + "팀의 스폰지역이 설정되지 않아 기본 스폰지역으로 이동합니다.");
                                    taskModule.runBukkitTask( () -> {
                                        player.teleport(spawnLocation);
                                    });
                                }
                            } else {
                                messageModule.sendPlayer(player,ChatColor.RED + "팀이 지정되지 않아 기본 스폰지역으로 이동합니다.");
                                taskModule.runBukkitTask( () -> {
                                    player.teleport(spawnLocation);
                                });
                            }
                        }
                    }
                    case 34 -> {
                        world.setPVP(true);
                        world.setAutoSave(Theomachy.SERVER_AUTO_SAVE);
                        world.setSpawnFlags(Theomachy.MONSTER_SPAWN, Theomachy.ANIMAL_SPAWN);
                        world.setDifficulty(this.difficulty);
                        taskModule.runBukkitTask( () -> {
                            world.setTime(6000);
                        });
                        Collection<Ability> playerAbilityList = GameData.playerAbility.values();
                        for (Ability e : playerAbilityList) {
                            e.initialize();
                            e.buff();
                        }
                        GameModule.Start = true;
                        messageModule.broadcastMessage(ChatColor.GOLD + "게임 시작!");
                    }
                    case 35 -> {
                        this.cancel();
                    }
                }
            });
            count++;
        }
    }
}
