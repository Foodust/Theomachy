package org.Theomachy.Data;

import org.Theomachy.ENUM.AbilityInfo;

import java.util.EnumSet;

public interface AbilityData
{
	EnumSet<AbilityInfo> godEnum = EnumSet.range(AbilityInfo.Zeus, AbilityInfo.Sans);
	EnumSet<AbilityInfo> humanEnum = EnumSet.range(AbilityInfo.Archer, AbilityInfo.Zet);
	EnumSet<AbilityInfo> jujuEnum = EnumSet.range(AbilityInfo.Itarodi, AbilityInfo.Sukuna);
	EnumSet<AbilityInfo> kimetsuEnum = EnumSet.range(AbilityInfo.Zenitsu, AbilityInfo.Rengoku);

	int GOD_ABILITY_NUMBER= godEnum.size();
	int HUMAN_ABILITY_NUMBER=humanEnum.size();
	int JUJUTSU_KAISEN_ABILITY_NUMBER = jujuEnum.size();
	int KIMETHU_NO_YAIBA_ABILITY_NUMBER= kimetsuEnum.size();
	int ABILITY_NUMBER=GOD_ABILITY_NUMBER + HUMAN_ABILITY_NUMBER + JUJUTSU_KAISEN_ABILITY_NUMBER + KIMETHU_NO_YAIBA_ABILITY_NUMBER;
}
