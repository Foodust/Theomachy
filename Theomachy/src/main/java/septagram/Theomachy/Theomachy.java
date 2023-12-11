package septagram.Theomachy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import septagram.Theomachy.DB.AbilityData;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.DB.PluginData;
import septagram.Theomachy.Handler.CommandModule.Blacklist;
import septagram.Theomachy.Manager.CommandManager;
import septagram.Theomachy.Manager.EventManager;

public class Theomachy extends JavaPlugin
{
	public static boolean INVENTORY_CLEAR = true;
	public static boolean GIVE_ITEM = true;
	public static boolean IGNORE_BED = true;
	public static boolean ENTITIES_REMOVE = true;
	public static boolean AUTO_SAVE = false;
	public static boolean ANIMAL = true;
	public static boolean MONSTER = true;
	public static boolean FAST_START = false;
	public static int DIFFICULTY = 1;
	public static boolean GAMB = true;
	
	public CommandManager cm;
	public static Logger log=Bukkit.getLogger();
	
	public File file=new File(getDataFolder(), "blacklist.yml");
	
	public void onEnable()
	{
		
		UpdateChecker.check(this.getDescription().getVersion());
		
		log.info("[신들의 전쟁] 플러그인이 활성화되었습니다.   "+PluginData.buildnumber+"  "+PluginData.version);
		log.info("[신들의 전쟁] 플러그인의 기본 설정을 적용 중입니다.");
		
		saveResource("blacklist.yml", true);
		
		cm=new CommandManager(this);
		ShapedRecipe recipe = new ShapedRecipe(new ItemStack(Material.BLAZE_ROD)).shape(new String[]{"|","|","|"}).setIngredient('|', Material.STICK);
		getServer().addRecipe(recipe);
		getServer().getPluginManager().registerEvents(new EventManager(), this);
		FileInputStream fis;
		InputStreamReader isr;
		BufferedReader br;
		try {
			fis = new FileInputStream(file);
			isr=new InputStreamReader(fis);
			br=new BufferedReader(isr);
			String line;
			while((line=br.readLine())!=null){
				Blacklist.Blacklist.add(Integer.parseInt(line));
			}
		}catch(FileNotFoundException e) {} catch (IOException e) {}
		
		for(int i=1;i<=AbilityData.GOD_ABILITY_NUMBER;i++) {
			if(!Blacklist.Blacklist.contains(i)) Blacklist.GodCanlist.add(i);
		}for(int i=101;i<=AbilityData.HUMAN_ABILITY_NUMBER+100;i++) {
			if(!Blacklist.Blacklist.contains(i)) Blacklist.HumanCanlist.add(i);
		}
		
		log.info("[신들의 전쟁] 등록된 능력");
		log.info("[신들의 전쟁] 신: "+Blacklist.GodCanlist.size()+", 인간: "+Blacklist.HumanCanlist.size());
		log.info("[신들의 전쟁] 총합: "+String.valueOf(Blacklist.GodCanlist.size()+Blacklist.HumanCanlist.size()));
		
		log.info("[신들의 전쟁] 게임의 설정 불러오는 중입니다.");
		getConfig().options().copyDefaults(true);
		saveConfig();
		INVENTORY_CLEAR = getConfig().getBoolean("인벤토리 클리어");
		GIVE_ITEM = getConfig().getBoolean("스카이블럭 아이템 제공");
		ENTITIES_REMOVE = getConfig().getBoolean("엔티티 제거");
		IGNORE_BED = getConfig().getBoolean("침대 무시");
		AUTO_SAVE = getConfig().getBoolean("서버 자동 저장");
		ANIMAL = getConfig().getBoolean("동물 생성");
		MONSTER = getConfig().getBoolean("몬스터 생성");
		DIFFICULTY = getConfig().getInt("난이도");
		FAST_START=getConfig().getBoolean("빠른 시작");
		GAMB=getConfig().getBoolean("도박 허용");
		
		log.info("[신들의 전쟁] ========================================");
		log.info("[신들의 전쟁] 게임 시작 시 인벤토리 클리어 : "+String.valueOf(INVENTORY_CLEAR));
		log.info("[신들의 전쟁] 게임 시작 시 스카이블럭 기본 아이템 지급 : "+String.valueOf(GIVE_ITEM));
		log.info("[신들의 전쟁] 게임 시작 시 몬스터,동물,아이템삭제 : "+String.valueOf(ENTITIES_REMOVE));
		log.info("[신들의 전쟁] 리스폰 시 침대 무시 : "+String.valueOf(IGNORE_BED));
		log.info("[신들의 전쟁] 빠른 시작 : "+String.valueOf(FAST_START));
		log.info("[신들의 전쟁] 도박 허용 : "+String.valueOf(GAMB));
		log.info("[신들의 전쟁] 서버 자동저장 : "+String.valueOf(AUTO_SAVE));
		log.info("[신들의 전쟁] 동물 스폰 : "+String.valueOf(ANIMAL));
		log.info("[신들의 전쟁] 몬스터 스폰 : "+String.valueOf(MONSTER));
		log.info("[신들의 전쟁] 난이도 : "+String.valueOf(DIFFICULTY));
		log.info("[신들의 전쟁] ========================================");
	
		Bukkit.getConsoleSender().sendMessage("원작자: "+ChatColor.WHITE+"칠각별(septagram) "+ChatColor.GRAY+"재수정자: "+ChatColor.AQUA+"플로리아(humint2003)");
	
		try {
			for(Player p:Bukkit.getOnlinePlayers()) {
				GameData.OnlinePlayer.put(p.getName(), p);
			}
		}catch(NullPointerException e) {}
		
	}
	
	public void onDisable() {
		BufferedWriter bw;
		try {
			bw=new BufferedWriter(new FileWriter(file));
			
			for(int i:Blacklist.Blacklist) {
				bw.write(String.valueOf(i)); bw.newLine();
			}
			
			bw.close();
		}catch(FileNotFoundException e) {  } catch (IOException e) {  }
		
		
		log.info("[신들의 전쟁] 블랙리스트가 파일로 저장되었습니다. 절대로 플러그인 폴더 내에 blacklist.yml을 건들지 마십시오.");
		
	}
	
}
