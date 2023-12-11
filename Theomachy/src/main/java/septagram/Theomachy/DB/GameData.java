package septagram.Theomachy.DB;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import septagram.Theomachy.Ability.Ability;

public class GameData
{
	public static HashMap<String,Player> OnlinePlayer = new HashMap<String,Player>(); //온라인플레이어
	public static HashMap<String,Ability> PlayerAbility = new HashMap<String,Ability>(); //플레이어 지정 능력
	public static HashMap<String,String> PlayerTeam = new HashMap<String,String>(); //플레이어, 팀이름
	public static HashMap<String,Location> SpawnArea = new HashMap<String,Location>(); //팀 스폰 지역
}
