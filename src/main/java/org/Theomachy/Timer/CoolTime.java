package org.Theomachy.Timer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TimerTask;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Theomachy;
import org.Theomachy.Data.GameData;
import org.Theomachy.Handler.CommandModule.StartStopCommand;
import org.Theomachy.Message.T_Message;
import org.bukkit.Bukkit;

public class CoolTime extends TimerTask {
    public static boolean init = false;
    public static HashMap<String, Integer> commonSkillCoolTime = new HashMap<String, Integer>();
    public static HashMap<String, Integer> normalSkillCoolTime = new HashMap<String, Integer>();
    public static HashMap<String, Integer> rareSkillCoolTime = new HashMap<String, Integer>();
    private int count = 1;

    public void run() {
        if (!StartStopCommand.Ready){
            this.cancel();
        }

        try {
            if (!commonSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : commonSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        commonSkillCoolTime.remove(playerName);
                        T_Message.AbilityReset(AbilityCase.COMMON, playerName);
                    } else {
                        commonSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            T_Message.CoolTimeCountTeller(AbilityCase.COMMON, playerName, value);
                    }
                }
            }

            if (!normalSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : normalSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        normalSkillCoolTime.remove(playerName);
                        T_Message.AbilityReset(AbilityCase.NORMAL, playerName);
                    } else {
                        normalSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            T_Message.CoolTimeCountTeller(AbilityCase.NORMAL, playerName, value);
                    }
                }
            }
            if (!rareSkillCoolTime.isEmpty()) {
                for (Entry<String, Integer> entry : rareSkillCoolTime.entrySet()) {
                    String playerName = entry.getKey();
                    int value = entry.getValue() - 1;
                    if (value <= 0) {
                        rareSkillCoolTime.remove(playerName);
                        T_Message.AbilityReset(AbilityCase.RARE, playerName);
                    } else {
                        rareSkillCoolTime.put(playerName, value);
                        if (value <= 3)
                            T_Message.CoolTimeCountTeller(AbilityCase.RARE, playerName, value);
                    }
                }
            }
            if (init) {
                commonSkillCoolTime.clear();
                normalSkillCoolTime.clear();
                rareSkillCoolTime.clear();
                init = false;
            }
            if (count % 150 == 0) {
                Collection<Ability> playerAbilityList = GameData.PlayerAbility.values();
                for (Ability e : playerAbilityList) {
                    if (e.buffType)
                        e.buff();
                }
            }
        } catch (Exception e) {
            Theomachy.log.info("쿨타이머에 에러가 발생하여 쿨타임이 1초 느려집니다.");
            Theomachy.log.info(e.getLocalizedMessage());
        }
        count++;
    }
}
