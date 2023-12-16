package org.Theomachy.Handler.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.Theomachy.Data.AbilityData;
import org.Theomachy.Theomachy;
import org.Theomachy.Handler.Ability.Blacklist;

public class RandomSkillHandler {
    public static int[] nonDuplicate() {

        List<Integer> availableAbilities = new ArrayList<Integer>();
        Random random = new Random();
        addAvailableAbilities(availableAbilities, AbilityData.GOD_ABILITY_NUMBER, 0, Blacklist.Blacklist);
        addAvailableAbilities(availableAbilities, AbilityData.HUMAN_ABILITY_NUMBER, 101, Blacklist.Blacklist);
        addAvailableAbilities(availableAbilities, AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER, 301, Blacklist.Blacklist);
        addAvailableAbilities(availableAbilities, AbilityData.KIMETHU_NO_YAIBA_ABILITY_NUMBER, 401, Blacklist.Blacklist);

        Blacklist.availableList = availableAbilities.size();

        int[] randomNumbers = new int[availableAbilities.size()];

        Object[] tempArray = availableAbilities.toArray();

        for (int i = 0; i < tempArray .length; i++) {
            randomNumbers[i] = (Integer) tempArray [i];
        }

        for (int i = 0; i < randomNumbers.length; i++)//섞
        {
            int randomIndex = random.nextInt(randomNumbers.length - 1);
            int temp = randomNumbers[i];
            randomNumbers[i] = randomNumbers[randomIndex];
            randomNumbers[randomIndex] = temp;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int num : randomNumbers)
            stringBuilder.append(num).append(" ");
        Theomachy.log.info(String.valueOf(stringBuilder));
        return randomNumbers;
    }
    private static void addAvailableAbilities(List<Integer> availableAbilities, int abilityCount, int startNumber, List<Integer> blacklist) {
        for (int i = 0; i < abilityCount; i++) {
            int currentNumber = startNumber + i;
            if (!blacklist.contains(currentNumber)) {
                availableAbilities.add(currentNumber);
            }
        }
    }
}
