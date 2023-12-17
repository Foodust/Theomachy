package org.Theomachy.Data;

import org.Theomachy.Ability.ENUM.AbilityInfo;

import java.util.EnumSet;

public interface AbilityData
{
	int GOD_ABILITY_NUMBER= EnumSet.range(AbilityInfo.Zeus, AbilityInfo.Sans).size();
	int HUMAN_ABILITY_NUMBER=EnumSet.range(AbilityInfo.Archer, AbilityInfo.Zet).size();
	int KIMETHU_NO_YAIBA_ABILITY_NUMBER= EnumSet.range(AbilityInfo.Zenitsu, AbilityInfo.Zenitsu).size();
	int JUJUTSU_KAISEN_ABILITY_NUMBER =EnumSet.range(AbilityInfo.Itarodi, AbilityInfo.Sukuna).size();
	int ABILITY_NUMBER=GOD_ABILITY_NUMBER + HUMAN_ABILITY_NUMBER + JUJUTSU_KAISEN_ABILITY_NUMBER + KIMETHU_NO_YAIBA_ABILITY_NUMBER;
}
