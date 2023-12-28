package org.Theomachy.Timer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TimerTask;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Handler.Module.GameModule;
import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.Theomachy.Data.GameData;
import org.Theomachy.Message.AbilityCoolTimeMessage;
import org.checkerframework.checker.units.qual.A;

public class CoolTimeTimer extends TimerTask {
    public static boolean init = false;
    public static HashMap<String, Integer> commonSkillCoolTime = new HashMap<String, Integer>();
    public static HashMap<String, Integer> normalSkillCoolTime = new HashMap<String, Integer>();
    public static HashMap<String, Integer> rareSkillCoolTime = new HashMap<String, Integer>();
    private int count = 1;
    private final AbilityCoolTimeMessage abilityCoolTimeMessage = new AbilityCoolTimeMessage();

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
                        abilityCoolTimeMessage.AbilityReset(AbilityCase.COMMON, playerName);
                    } else {
                        commonSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            abilityCoolTimeMessage.CoolTimeCountTeller(AbilityCase.COMMON, playerName, value);
                    }
                }
            }

            if (!normalSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : normalSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        normalSkillCoolTime.remove(playerName);
                        abilityCoolTimeMessage.AbilityReset(AbilityCase.NORMAL, playerName);
                    } else {
                        normalSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            abilityCoolTimeMessage.CoolTimeCountTeller(AbilityCase.NORMAL, playerName, value);
                    }
                }
            }
            if (!rareSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : rareSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        rareSkillCoolTime.remove(playerName);
                        abilityCoolTimeMessage.AbilityReset(AbilityCase.RARE, playerName);
                    } else {
                        rareSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            abilityCoolTimeMessage.CoolTimeCountTeller(AbilityCase.RARE, playerName, value);
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
            Theomachy.log.info(TheomachyMessage.ERROR_COOL_TIMER.getMessage());
            Theomachy.log.info(e.getLocalizedMessage());
        }
        count++;
    }
}
