package septagram.Theomachy.Timer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TimerTask;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Handler.CommandModule.GameHandler;
import septagram.Theomachy.Message.T_Message;

public class CoolTime extends TimerTask
{
	public static boolean ini=false;
	public static HashMap<String, Integer> COOL0 = new HashMap<String,Integer>();
	public static HashMap<String, Integer> COOL1 = new HashMap<String,Integer>();
	public static HashMap<String, Integer> COOL2 = new HashMap<String,Integer>();
	private int count=1;
	public void run()
	{
		if (!GameHandler.Ready)
			this.cancel();
		
		try{
		if (!COOL0.isEmpty())
		{
			Iterator<Entry<String, Integer>> iter = COOL0.entrySet().iterator();
			while(iter.hasNext())
			{
				Entry<String, Integer> entry = iter.next();
				String playerName = entry.getKey();
				int value = entry.getValue()-1;
				if (value <= 0)
				{
					COOL0.remove(playerName);
					T_Message.AbilityReset(0, playerName);
				}
				else
				{
					COOL0.put(playerName, value);
					if (value <=3)
						T_Message.CoolTimeCountTeller(0, playerName, value);
				}
			}
		}
		
		if (!COOL1.isEmpty())
		{
			Iterator<Entry<String, Integer>> iter = COOL1.entrySet().iterator();
			while(iter.hasNext())
			{
				Entry<String, Integer> entry = iter.next();
				String playerName = entry.getKey();
				int value = entry.getValue()-1;
				if (value <= 0)
				{
					COOL1.remove(playerName);
					T_Message.AbilityReset(1, playerName);
				}
				else
				{
					COOL1.put(playerName, value);
					if (value <=3)
						T_Message.CoolTimeCountTeller(1, playerName, value);
				}
			}
		}
		if (!COOL2.isEmpty())
		{
			Iterator<Entry<String, Integer>> iter = COOL2.entrySet().iterator();
			while(iter.hasNext())
			{
				Entry<String, Integer> entry = iter.next();
				String playerName = entry.getKey();
				int value = entry.getValue()-1;
				if (value <= 0)
				{
					COOL2.remove(playerName);
					T_Message.AbilityReset(2, playerName);
				}
				else
				{
					COOL2.put(playerName, value);
					if (value <=3)
						T_Message.CoolTimeCountTeller(2, playerName, value);
				}
			}
		}
		if (ini)
		{
			COOL0.clear();
			COOL1.clear();
			COOL2.clear();
			ini=false;
		}
		if (count%150 == 0)
		{
			Collection<Ability> playerAbilityList = GameData.PlayerAbility.values();
			for (Ability e : playerAbilityList)
			{
				if (e.buffType)
					e.buff();
			}
		}
		}
		catch(Exception e)
		{
			Theomachy.log.info("쿨타이머에 에러가 발생하여 쿨타임이 1초 느려집니다.");
			Theomachy.log.info(e.getLocalizedMessage()+"");
			
		}
		count++;
	}
}
