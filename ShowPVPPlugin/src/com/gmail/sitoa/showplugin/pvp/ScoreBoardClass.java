package com.gmail.sitoa.showplugin.pvp;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreBoardClass {
	 /** 赤チームの名前 */
    private static final String TEAM_RED_NAME = "team_red";
    /** 青チームの名前 */
    private static final String TEAM_BLUE_NAME = "team_white";

    /** 赤チーム */
    private Team teamRed;
    /** しろチーム */
    private Team teamWhite;
	private Plugin p = null;
	private Score redp =null;
	private Score whitep = null;
	private Objective tobj = null ;
	private ScoreboardManager manager = null;
	private Scoreboard board = null;
	ScoreBoardClass(){
		p = MainClass.getInstance();
	       manager = Bukkit.getScoreboardManager();
	       board = manager.getMainScoreboard();
		mainscoreboard();
		sidescore();
	}
	private  void mainscoreboard(){
		 // メインスコアボードを取得します。
        // チームが既に登録されているかどうか確認し、
        // 登録されていないなら新規作成します。
         teamRed = board.getTeam(TEAM_RED_NAME);
        if ( teamRed == null ) {
            teamRed = board.registerNewTeam(TEAM_RED_NAME);
            teamRed.setPrefix(ChatColor.RED.toString());
            teamRed.setSuffix(ChatColor.RESET.toString());
            teamRed.setDisplayName("team red");
            teamRed.setAllowFriendlyFire(false);
        }
        teamWhite = board.getTeam(TEAM_BLUE_NAME);
        if ( teamWhite == null ) {
        	teamWhite = board.registerNewTeam(TEAM_BLUE_NAME);
        	teamWhite.setPrefix(ChatColor.WHITE.toString());
        	teamWhite.setSuffix(ChatColor.RESET.toString());
        	teamWhite.setDisplayName("team white");
        	teamWhite.setAllowFriendlyFire(false);
        }
	}
	private  void sidescore(){
        tobj = board.registerNewObjective("TeamPoint", "dummy");
        redp = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Red"));
        whitep = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE+"White"));
        redp.setScore(0);
        whitep.setScore(0);
        tobj.setDisplaySlot(DisplaySlot.SIDEBAR);

	}
	public void addScore(String team,Integer point){
		if(team.equalsIgnoreCase("red")){
			int nowpoint = redp.getScore();
			nowpoint = nowpoint + point;
			if(nowpoint <= 0){
				Bukkit.broadcastMessage("レッドチームのポイントがなくなりました。");
				reset();
			}else{
			redp.setScore(nowpoint);
			}
		}else if(team.equalsIgnoreCase("white")){
			int nowpoint = whitep.getScore();
			nowpoint = nowpoint + point;
			if(nowpoint <= 0){
				Bukkit.broadcastMessage("ホワイトチームのポイントがなくなりました。");
				reset();

			}else{

				whitep.setScore(nowpoint);
			}
		}else{

		}
	}

	public void reset (){
		for(Player target :p.getServer().getOnlinePlayers()){
			target.teleport(target.getWorld().getSpawnLocation());
		}
        Objective tobj = board.registerNewObjective("TeamPoint", "dummy");
        redp = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Red"));
        whitep = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE+"White"));
        board.clearSlot(DisplaySlot.SIDEBAR);
        Objective obj = board.getObjective("TeamPoint");
        redp.setScore(0);
        whitep.setScore(0);
        obj.unregister();
        Bukkit.getScheduler().cancelAllTasks();
        teamRed.unregister();
        teamWhite.unregister();
        mainscoreboard();
        sidescore();

	}
	public void setScore(String team,Integer point){
        redp = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Red"));
        whitep = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE+"White"));
		if(team.equalsIgnoreCase("red")){
			redp.setScore(point);

		}else if(team.equalsIgnoreCase("white")){
			whitep.setScore(point);
		}else{

		}
	}

	public String addPlayer(Player p,String team){
		if(team.equalsIgnoreCase("white")){
			teamWhite.addPlayer(p);
			HashMap<Player,Integer> players = ListenerClass.getHashMap();
			players.put(p, 3);
			return "Whiteに所属しました";
		}else if(team.equalsIgnoreCase("red")){
			teamRed.addPlayer(p);
			HashMap<Player,Integer> players = ListenerClass.getHashMap();
			players.put(p, 3);
			return "Redに所属しました";

		}else{
			return "チーム名はred/whiteでお願いします。";
		}
	}

}
