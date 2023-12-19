package org.Theomachy.Utility.Checker;

import org.bukkit.entity.Player;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Timer.CoolTime;
import org.Theomachy.Message.AbilityCoolTimeMesage;

public class CoolTimeChecker
{
	public static boolean Check(Player player, AbilityCase abilityCase)
	{
		String key=player.getName();
		
		if (abilityCase == AbilityCase.COMMON)
		{
			if (CoolTime.commonSkillCoolTime.containsKey(key))
			{
				int cool = CoolTime.commonSkillCoolTime.get(key);
				AbilityCoolTimeMesage.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == AbilityCase.NORMAL)
		{
			if (CoolTime.normalSkillCoolTime.containsKey(key))
			{
				int cool = CoolTime.normalSkillCoolTime.get(key);
				AbilityCoolTimeMesage.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == AbilityCase.RARE)
		{
			if (CoolTime.rareSkillCoolTime.containsKey(key))
			{
				int cool = CoolTime.rareSkillCoolTime.get(key);
				AbilityCoolTimeMesage.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else
			return false;
	}
}
