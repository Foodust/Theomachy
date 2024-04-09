package org.Theomachy.Handler.Handler;

import java.util.*;

import org.Theomachy.Data.AbilityData;
import org.Theomachy.Handler.Module.BlacklistModule;

public class RandomSkillHandler  {
    public List<Integer> makeRandomAbilityList() {
        List<Integer> availableAbilities = new ArrayList<Integer>();
        addAvailableAbilities(availableAbilities, AbilityData.GOD_ABILITY_NUMBER, 0);
        addAvailableAbilities(availableAbilities, AbilityData.HUMAN_ABILITY_NUMBER, 101);
        addAvailableAbilities(availableAbilities, AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER, 301);
        addAvailableAbilities(availableAbilities, AbilityData.KIMETSU_NO_YAIBA_ABILITY_NUMBER, 401);
        BlacklistModule.availableList = availableAbilities.size();
        Collections.shuffle(availableAbilities);
        return availableAbilities;
    }
    private void addAvailableAbilities(List<Integer> availableAbilities, int abilityCount, int startNumber) {
        for (int i = 0; i < abilityCount; i++) {
            int currentNumber = startNumber + i;
            if (!BlacklistModule.blacklist.contains(currentNumber)) {
                availableAbilities.add(currentNumber);
            }
        }
    }
}
