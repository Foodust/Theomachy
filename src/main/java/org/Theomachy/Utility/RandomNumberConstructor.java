package org.Theomachy.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.Theomachy.DB.AbilityData;
import org.Theomachy.Theomachy;
import org.Theomachy.Handler.CommandModule.Blacklist;

public class RandomNumberConstructor {
    public static int[] nonDuplicate() {

        List<Integer> Canlist = new ArrayList<Integer>();
        Random random = new Random();

        for (int ablityNumber = 0; ablityNumber < AbilityData.GOD_ABILITY_NUMBER; ablityNumber++) {
            if (!Blacklist.Blacklist.contains(ablityNumber + 1)){
                Canlist.add(ablityNumber + 1);
            }
        }
        int humanNumber = 101;
        for (int abilityNumber = 0; abilityNumber < AbilityData.HUMAN_ABILITY_NUMBER; abilityNumber++) {
            if (!Blacklist.Blacklist.contains(humanNumber)) {
                Canlist.add(humanNumber);
            }
            humanNumber++;
        }
        int jujuNumber = 301;
        for (int abilityNumber = 0; abilityNumber < AbilityData.JUJUTSU_KAISEN_ABILITY_NUMBER; abilityNumber++) {
            if (!Blacklist.Blacklist.contains(jujuNumber)) {
                Canlist.add(jujuNumber);
            }
            jujuNumber++;
        }
        int kimeNumber = 401;
        for (int abilityNumber = 0; abilityNumber < AbilityData.KIMETHU_NO_YAIBA_ABILITY_NUMBER; abilityNumber++) {
            if (!Blacklist.Blacklist.contains(kimeNumber)) {
                Canlist.add(kimeNumber);
            }
            kimeNumber++;
        }

        Blacklist.availableList = Canlist.size();

        int[] rn;
        rn = new int[Canlist.size()];

        Object[] rn1 = Canlist.toArray();

        for (int i = 0; i < rn1.length; i++) {
            rn[i] = (Integer) rn1[i];
        }

        for (int i = 0; i < rn.length; i++)//섞
        {
            int r = random.nextInt(rn.length - 1);
            int temp = rn[i];
            rn[i] = rn[r];
            rn[r] = temp;
        }
        StringBuilder sb = new StringBuilder();
        for (int num : rn)
            sb.append(num).append(" ");
        Theomachy.log.info(String.valueOf(sb));
        return rn;
    }
}
