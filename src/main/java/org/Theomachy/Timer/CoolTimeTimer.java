package org.Theomachy.Timer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TimerTask;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Theomachy;
import org.Theomachy.Data.GameData;
import org.Theomachy.Handler.Command.StartStopCommand;
import org.Theomachy.Message.AbilityCoolTimeMesage;

public class CoolTimeTimer extends TimerTask {
    public static boolean init = false;
    public static HashMap<String, Integer> commonSkillCoolTime = new HashMap<String, Integer>();
    public static HashMap<String, Integer> normalSkillCoolTime = new HashMap<String, Integer>();
    public static HashMap<String, Integer> rareSkillCoolTime = new HashMap<String, Integer>();
    private int count = 1;

    @Override
    public void run() {
        if (!GameModule.Ready && !Theomachy.DEBUG) {
            commonSkillCoolTime.clear();
            normalSkillCoolTime.clear();
            rareSkillCoolTime.clear();
            this.cancel();
        }
        try {
            if (init) {
                commonSkillCoolTime.clear();
                normalSkillCoolTime.clear();
                rareSkillCoolTime.clear();
                init = false;
            }
            if (!commonSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : commonSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        commonSkillCoolTime.remove(playerName);
                        AbilityCoolTimeMesage.AbilityReset(AbilityCase.COMMON, playerName);
                    } else {
                        commonSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            AbilityCoolTimeMesage.CoolTimeCountTeller(AbilityCase.COMMON, playerName, value);
                    }
                }
            }

            if (!normalSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : normalSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        normalSkillCoolTime.remove(playerName);
                        AbilityCoolTimeMesage.AbilityReset(AbilityCase.NORMAL, playerName);
                    } else {
                        normalSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            AbilityCoolTimeMesage.CoolTimeCountTeller(AbilityCase.NORMAL, playerName, value);
                    }
                }
            }
            if (!rareSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : rareSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        rareSkillCoolTime.remove(playerName);
                        AbilityCoolTimeMesage.AbilityReset(AbilityCase.RARE, playerName);
                    } else {
                        rareSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            AbilityCoolTimeMesage.CoolTimeCountTeller(AbilityCase.RARE, playerName, value);
                    }
                }
            }

            if (count % 150 == 0) {
                Collection<Ability> playerAbilityList = GameData.playerAbility.values();
                for (Ability playerAbility : playerAbilityList) {
                    if (playerAbility.buffType)
                        playerAbility.buff();
                }
            }
        } catch (Exception e) {
            Theomachy.log.info("쿨타이머에 에러가 발생하여 쿨타임이 1초 느려집니다.");
            Theomachy.log.info(e.getLocalizedMessage());
        }
        count++;
    }
}
