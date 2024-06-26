package org.Theomachy.Ability.HUMAN;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Data.GameData;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.BlockFilter;


public class Teleporter extends Ability
{

	private final static String[] des= {
			AbilityInfo.Teleporter.getName()+ "는 순간이동을 돕는 마법사입니다.",
		   	ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"텔레포팅",
			"25칸 이내의 목표 지점으로 텔레포트합니다." ,
			ChatColor.RED+"【고급】 "+ChatColor.WHITE+"치환",
			"타겟에 등록해 둔 자신의 팀원과 위치를 치환합니다.",
			"목표 지정: /x <대상>"};

	private String rareTarget;
	private final BlockFilter blockFilter = new BlockFilter();
	public Teleporter(String playerName)
	{
		super(playerName, AbilityInfo.Teleporter, true, false, false, des);
		messageModule.logInfo(playerName+abilityName);
		
		this.normalSkillCoolTime =25;
		this.normalSkillStack =15;
		this.normalDistance = 25;

		this.rareSkillCoolTime =30;
		this.rareSkillStack =25;
		
		this.rank= AbilityRank.B;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (playerModule.InHandItemCheck(player,skillMaterial))
		{
            switch (event.getAction()) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
				case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (skillHandler.Check(player, AbilityCase.NORMAL)&&playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack))
		{
			Block block = player.getTargetBlock(null, (int)normalDistance);
			if (blockFilter.AirToFar(player, block))
			{
				Location location0 = block.getLocation();
				Location location1 = block.getLocation();
				location0.setY(location0.getY()+1);
				location1.setY(location1.getY()+2);
				Block block0 = location0.getBlock();
				Block block1 = location1.getBlock();

				if ((block0.getType()==Material.AIR || block1.getType() == Material.SNOW)&&block1.getType()==Material.AIR)
				{
					skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
					Location plocation = player.getLocation();
					Location tlocation = block.getLocation();
					tlocation.setPitch(plocation.getPitch());
					tlocation.setYaw(plocation.getYaw());
					tlocation.setY(tlocation.getY()+1);
					tlocation.setX(tlocation.getX()+0.5);
					tlocation.setZ(tlocation.getZ()+0.5);
					taskModule.runBukkitTask(()->{player.teleport(tlocation);});
				}
				else
					messageModule.sendPlayer(player,"텔레포트 할 수 있는 공간이 없어 텔레포트에 실패 했습니다.");
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (skillHandler.Check(player, AbilityCase.RARE)&&playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack))
		{
			if (rareTarget != null)
			{
				Player target = GameData.onlinePlayer.get(rareTarget);
				if (target != null)
				{
					Location location = player.getLocation();
					location.setY(location.getY()-1);
					skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
					Location targetLocation = target.getLocation();
					Location playerLocation = player.getLocation();
					taskModule.runBukkitTask(()->{target.teleport(playerLocation);});
					taskModule.runBukkitTask(()->{player.teleport(targetLocation);});
					target.sendMessage("텔레포터의 능력에 의해 위치가 텔레포터의 위치로 변경되었습니다.");
				}
				else
					messageModule.sendPlayer(player,"플레이어가 온라인이 아닙니다.");
			}
			else
				messageModule.sendPlayer(player,"타겟이 지정되지 않았습니다. (타겟 등록법 : /x <Player>)");
		}
	}
	
	public void targetSet(CommandSender sender, String targetName)
	{
		String playerTeamName = GameData.playerTeam.get(playerName);
		String targetTeamName = GameData.playerTeam.get(targetName);
		if (playerTeamName != null && playerTeamName.equals(targetTeamName))
		{
			if (!playerName.equals(targetName))
			{
				this.rareTarget = targetName;
				sender.sendMessage("타겟을 등록했습니다.   "+ChatColor.GREEN+targetName);
			}
			else
				sender.sendMessage("자기 자신을 타겟으로 등록 할 수 없습니다.");
		}
		else
			sender.sendMessage("자신의 팀의 멤버가 아닙니다.");		
	}
}
