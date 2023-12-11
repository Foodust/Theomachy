package septagram.Theomachy.Utility;

import org.bukkit.entity.Player;

import septagram.Theomachy.Message.T_Message;
import septagram.Theomachy.Timer.CoolTime;

public class CoolTimeChecker
{
	public static boolean Check(Player player, int abilityCase)
	{
		String key=player.getName();
		
		if (abilityCase == 0)
		{
			if (CoolTime.COOL0.containsKey(key))
			{
				int cool = CoolTime.COOL0.get(key);
				T_Message.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == 1)
		{
			if (CoolTime.COOL1.containsKey(key))
			{
				int cool = CoolTime.COOL1.get(key);
				T_Message.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == 2)
		{
			if (CoolTime.COOL2.containsKey(key))
			{
				int cool = CoolTime.COOL2.get(key);
				T_Message.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else
			return false;
	}
}
