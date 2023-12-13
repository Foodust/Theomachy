package org.septagram.Theomachy.Ability.HUMAN;

import java.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.DB.GameData;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Timer.Skill.SnipingDuration;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Sniper extends Ability
{
	private final int coolTime0=70;
	private final Material material = Material.COBBLESTONE;
	private final int stack0=1;
	public boolean ready = false;
	public boolean sniping = false;
	private final static String[] des= {
			   "저격수는 빠른 화살을 이용해 상대방을 공격하는 능력입니다.",
			   "게임 시작시 활 1개 화살 10개를 지급합니다. ",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"스나이핑",
			   "활을 들고 앉은 채(shift) 좌클릭하면 4초 뒤 스나이핑 모드가 활성화됩니다." ,
			   "스나이핑 모드일시 쏜 화살이 타겟방향으로 보이지 않는 속도로",
			   "날아가며 맞은 적은 약 100~200의 데미지를 입습니다."};
	
	public Sniper(String playerName)
	{
		super(playerName, "저격수", 118, true, false, false, des);
		this.cool1=50;
		this.sta1=5;
		
		this.rank=3;
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BOW))
		{
            switch (EventFilter.PlayerInteract(event)) {
                case 0, 1 -> leftAction(player);
            }
		}
	}

	private void leftAction(Player player)
	{
		if (player.isSneaking() && !ready)
		{
			ready=true;
//			Timer t = new Timer();
//			t.schedule(new SnipingDuration(player, this),0,1000);
			for (int i = 4 ; i>0; i--){
				Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), new SnipingDuration(player,this, i), 1 * 20);
			}
		}
	}
	
	@Override
	public void T_Passive(ProjectileLaunchEvent event, Player player)
	{
		if (this.sniping && (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, material, stack0)))
		{
			Entity entity = event.getEntity();
			if (entity instanceof Arrow)
			{
				Skill.Use(player, material, stack0, 0, coolTime0);
				entity.remove();
				@SuppressWarnings("deprecation")
				Arrow arrow = player.launchProjectile(Arrow.class);
				arrow.setVelocity(player.getEyeLocation().getDirection().multiply(100));
			}
		}
	}
	
	@Override
	public void conditionSet()
	{
		Player player = GameData.OnlinePlayer.get(this.playerName);
		player.getInventory().addItem(new ItemStack(Material.BOW, 1));
		player.getInventory().addItem(new ItemStack(Material.ARROW, 10));
	}
}
