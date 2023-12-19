package org.Theomachy.Data;

import org.Theomachy.Enum.AbilityInfo;

import java.util.EnumSet;

public class AbilityData
{
	public static EnumSet<AbilityInfo> godEnum = EnumSet.range(AbilityInfo.Zeus, AbilityInfo.Sans);
	public static EnumSet<AbilityInfo> humanEnum = EnumSet.range(AbilityInfo.Archer, AbilityInfo.Zet);
	public static EnumSet<AbilityInfo> jujutsuKaisenEnum = EnumSet.range(AbilityInfo.Itarodi, AbilityInfo.Sukuna);
	public static EnumSet<AbilityInfo> kimetsuNoYaibaEnum = EnumSet.range(AbilityInfo.Zenitsu, AbilityInfo.Rengoku);

	public static int GOD_ABILITY_NUMBER= godEnum.size();
	public static int HUMAN_ABILITY_NUMBER=humanEnum.size();
	public static int JUJUTSU_KAISEN_ABILITY_NUMBER = jujutsuKaisenEnum.size();
	public static int KIMETSU_NO_YAIBA_ABILITY_NUMBER = kimetsuNoYaibaEnum.size();
	public static int ABILITY_NUMBER=GOD_ABILITY_NUMBER + HUMAN_ABILITY_NUMBER + JUJUTSU_KAISEN_ABILITY_NUMBER + KIMETSU_NO_YAIBA_ABILITY_NUMBER;
}
