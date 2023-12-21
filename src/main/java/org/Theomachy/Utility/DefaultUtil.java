package org.Theomachy.Utility;

import org.Theomachy.Handler.Command.*;
import org.Theomachy.Handler.Handler.RandomSkillHandler;
import org.Theomachy.Handler.Manager.EntityManager;
import org.Theomachy.Handler.Module.*;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.units.qual.C;

public class DefaultUtil {
    // module
    public AbilityModule abilityModule = new AbilityModule();
    public BlacklistModule blacklistModule = new BlacklistModule();
    public CommonModule commonModule = new CommonModule();
    public GamblingModule gamblingModule = new GamblingModule();
    public GameModule gameModule = new GameModule();
    public HangulModule hangulModule = new HangulModule();
    public PlayerModule playerModule = new PlayerModule();
    public SettingModule settingModule = new SettingModule();
    public SpawnModule spawnModule = new SpawnModule();
    public TeamModule teamModule = new TeamModule();

    // manager
    public EntityManager entityManager = new EntityManager();

    // handler
    public RandomSkillHandler randomSkillHandler = new RandomSkillHandler();

    // command
    public AbilityCommand abilityCommand = new AbilityCommand();
    public BlacklistCommand blacklistCommand = new BlacklistCommand();
    public GamblingCommand gamblingCommand = new GamblingCommand();
    public MainCommand mainCommand = new MainCommand();
    public SettingCommand settingCommand = new SettingCommand();
    public SpawnCommand spawnCommand = new SpawnCommand();
    public StartStopCommand startStopCommand= new StartStopCommand();
    public TeamCommand teamCommand = new TeamCommand();
}
