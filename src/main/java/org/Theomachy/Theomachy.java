package org.Theomachy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import de.slikey.effectlib.EffectManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.Theomachy.Data.GameData;
import org.Theomachy.Data.TickData;
import org.Theomachy.Handler.Module.source.CommonModule;
import org.Theomachy.Handler.Module.game.PlayerModule;
import org.Theomachy.Handler.Module.source.SettingModule;
import org.Theomachy.Handler.Module.source.TaskModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Handler.Module.source.BlacklistModule;
import org.Theomachy.Timer.TipTimer;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.Theomachy.Handler.Manager.CommandManager;
import org.Theomachy.Handler.Manager.EventManager;

public class Theomachy extends JavaPlugin {
    private static Plugin plugin;
    public static boolean STARTING_INVENTORY_CLEAR = true;
    public static boolean STARTING_GIVE_ITEM = true;
    public static boolean STARTING_ENTITY_CLEAR = true;
    public static boolean IGNORE_BED = true;
    public static boolean SERVER_AUTO_SAVE = false;
    public static boolean ANIMAL_SPAWN = true;
    public static boolean MONSTER_SPAWN = true;
    public static boolean FAST_START = false;
    public static int DIFFICULTY = 1;
    public static boolean GAMBLING = true;
    public static boolean DEBUG = false;

    public static Logger log = Bukkit.getLogger();
    public static List<BukkitTask> tasks = new ArrayList<>();
    public File file = new File(getDataFolder(), TheomachyMessage.SETTING_BLACKLIST_YML.getMessage());

    public static Plugin getPlugin() {
        return plugin;
    }
    private BukkitAudiences adventure;
    private final EventManager eventManager = new EventManager();
    private final BlacklistModule blacklistModule = new BlacklistModule();
    private final CommonModule commonModule = new CommonModule();
    private final PlayerModule playerModule = new PlayerModule();
    private final UpdateChecker updateChecker = new UpdateChecker();
    private final SettingModule settingModule = new SettingModule();
    private final TaskModule taskModule = new TaskModule();
    private static EffectManager effectManager;
    public static EffectManager getEffectManage(){return effectManager;}
    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public void onEnable() {
        effectManager = new EffectManager(this);
        this.adventure = BukkitAudiences.create(this);

        plugin = this;

        updateChecker.check("5.0");

        //plugin 기본 설정 메세지
        commonModule.defaultPluginMessage();

        // blacklist 세이브
        saveResource(TheomachyMessage.SETTING_BLACKLIST_YML.getMessage(), true);

        // command 등록
        CommandManager commandManager = new CommandManager(this);

        // recipe 등록
        commonModule.settingBlazeRodRecipe(this);

        // event 등록
        eventManager.settingEvent(getServer(), this);

        // blacklist
        blacklistModule.settingBlackList(file);

        // 기본 정보
        settingModule.defaultSettingAndLogMessage(this);
        settingModule.settingStartItem();

        // tip timer
        Theomachy.tasks.add(taskModule.runBukkitTaskTimer(new TipTimer(), 0L, TickData.longTick));

        // 초기화
        GameData.initialize();

        // player 등록
        playerModule.setOnlinePlayer();
    }

    public void onDisable() {
        // blacklist 정리
        blacklistModule.freeBlackList(file);
        // 설정 제거
        GameData.initialize();
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
