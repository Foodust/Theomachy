package org.septagram.Theomachy.Ability.HUMAN;

import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.BlockFilter;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Teleporter extends Ability
{

	private final static String[] des= {
			AbilityInfo.Teleporter.getName()+ "는 순간이동을 돕는 마법사입니다.",
		   	NamedTextColor.AQUA+"【일반】 "+NamedTextColor.WHITE+"텔레포팅",
			"25칸 이내의 목표 지점으로 텔레포트합니다." ,
			NamedTextColor.RED+"【고급】 "+NamedTextColor.WHITE+"치환",
			"타겟에 등록해 둔 자신의 팀원과 위치를 치환합니다.",
			"목표 지정: /x <대상>"};

	private String rareTarget;
	private final int normalDistance;
	public Teleporter(String playerName)
	{
		super(playerName, AbilityInfo.Teleporter, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
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
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
				case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack))
		{
			Block block = player.getTargetBlock(null, normalDistance);
			if (BlockFilter.AirToFar(player, block))
			{
				Location location0 = block.getLocation();
				Location location1 = block.getLocation();
				location0.setY(location0.getY()+1);
				location1.setY(location1.getY()+2);
				Block block0 = location0.getBlock();
				Block block1 = location1.getBlock();

				if ((block0.getType()==Material.AIR || block1.getType() == Material.SNOW)&&block1.getType()==Material.AIR)
				{
					Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
					Location plocation = player.getLocation();
					Location tlocation = block.getLocation();
					tlocation.setPitch(plocation.getPitch());
					tlocation.setYaw(plocation.getYaw());
					tlocation.setY(tlocation.getY()+1);
					tlocation.setX(tlocation.getX()+0.5);
					tlocation.setZ(tlocation.getZ()+0.5);
					Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{player.teleport(tlocation);});
				}
				else
					player.sendMessage("텔레포트 할 수 있는 공간이 없어 텔레포트에 실패 했습니다.");
			}
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, AbilityCase.RARE)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack))
		{
			if (rareTarget != null)
			{
				Player target = GameData.OnlinePlayer.get(rareTarget);
				if (target != null)
				{
					Location location = player.getLocation();
					location.setY(location.getY()-1);
					Skill.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
					Location targetLocation = target.getLocation();
					Location playerLocation = player.getLocation();
					Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{target.teleport(playerLocation);});
					Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{player.teleport(targetLocation);});
					target.sendMessage("텔레포터의 능력에 의해 위치가 텔레포터의 위치로 변경되었습니다.");
				}
				else
					player.sendMessage("플레이어가 온라인이 아닙니다.");
			}
			else
				player.sendMessage("타겟이 지정되지 않았습니다. (타겟 등록법 : /x <Player>)");
		}
	}
	
	public void targetSet(CommandSender sender, String targetName)
	{
		String playerTeamName = GameData.PlayerTeam.get(playerName);
		String targetTeamName = GameData.PlayerTeam.get(targetName);
		if (playerTeamName != null && playerTeamName.equals(targetTeamName))
		{
			if (!playerName.equals(targetName))
			{
				this.rareTarget = targetName;
				sender.sendMessage("타겟을 등록했습니다.   "+NamedTextColor.GREEN+targetName);
			}
			else
				sender.sendMessage("자기 자신을 타겟으로 등록 할 수 없습니다.");
		}
		else
			sender.sendMessage("자신의 팀의 멤버가 아닙니다.");		
	}
}
