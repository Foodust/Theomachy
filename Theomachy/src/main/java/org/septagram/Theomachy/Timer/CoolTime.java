package org.septagram.Theomachy.Timer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TimerTask;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Handler.CommandModule.GameHandler;
import org.septagram.Theomachy.Message.T_Message;

public class CoolTime extends TimerTask
{
	public static boolean ini=false;
	public static HashMap<String, Integer> commonSkillCoolTime = new HashMap<String,Integer>();
	public static HashMap<String, Integer> normalSkillCoolTime = new HashMap<String,Integer>();
	public static HashMap<String, Integer> rareSkillCoolTime = new HashMap<String,Integer>();
	private int count=1;
	public void run()
	{
		if (!GameHandler.Ready)
			this.cancel();
		
		try{
		if (!commonSkillCoolTime.isEmpty())
		{
			Iterator<Entry<String, Integer>> iter = commonSkillCoolTime.entrySet().iterator();
			while(iter.hasNext())
			{
				Entry<String, Integer> entry = iter.next();
				String playerName = entry.getKey();
				int value = entry.getValue()-1;
				if (value <= 0)
				{
					commonSkillCoolTime.remove(playerName);
					T_Message.AbilityReset(AbilityCase.COMMON, playerName);
				}
				else
				{
					commonSkillCoolTime.put(playerName, value);
					if (value <=3)
						T_Message.CoolTimeCountTeller(AbilityCase.COMMON, playerName, value);
				}
			}
		}
		
		if (!normalSkillCoolTime.isEmpty())
		{
			Iterator<Entry<String, Integer>> iter = normalSkillCoolTime.entrySet().iterator();
			while(iter.hasNext())
			{
				Entry<String, Integer> entry = iter.next();
				String playerName = entry.getKey();
				int value = entry.getValue()-1;
				if (value <= 0)
				{
					normalSkillCoolTime.remove(playerName);
					T_Message.AbilityReset(AbilityCase.NORMAL, playerName);
				}
				else
				{
					normalSkillCoolTime.put(playerName, value);
					if (value <=3)
						T_Message.CoolTimeCountTeller(AbilityCase.NORMAL, playerName, value);
				}
			}
		}
		if (!rareSkillCoolTime.isEmpty())
		{
			Iterator<Entry<String, Integer>> iter = rareSkillCoolTime.entrySet().iterator();
			while(iter.hasNext())
			{
				Entry<String, Integer> entry = iter.next();
				String playerName = entry.getKey();
				int value = entry.getValue()-1;
				if (value <= 0)
				{
					rareSkillCoolTime.remove(playerName);
					T_Message.AbilityReset(AbilityCase.RARE, playerName);
				}
				else
				{
					rareSkillCoolTime.put(playerName, value);
					if (value <=3)
						T_Message.CoolTimeCountTeller(AbilityCase.RARE, playerName, value);
				}
			}
		}
		if (ini)
		{
			commonSkillCoolTime.clear();
			normalSkillCoolTime.clear();
			rareSkillCoolTime.clear();
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
