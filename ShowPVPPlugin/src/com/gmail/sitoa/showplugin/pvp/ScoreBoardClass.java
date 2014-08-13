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
    private static final String TEAM_BLUE_NAME = "team_blue";

    /** 赤チーム */
    private Team teamRed;
    /** しろチーム */
    private Team teamWhite;
	private Plugin p = null;
	ScoreBoardClass(){
		p = MainClass.getInstance();
		mainscoreboard();

	}
	public void mainscoreboard(){
		 // メインスコアボードを取得します。
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

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
        	teamWhite.setPrefix(ChatColor.BLUE.toString());
        	teamWhite.setSuffix(ChatColor.RESET.toString());
        	teamWhite.setDisplayName("team blue");
        	teamWhite.setAllowFriendlyFire(false);
        }
	}
	Score redp =null;
	Score whitep = null;
	public void sidescore(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        board.clearSlot(DisplaySlot.SIDEBAR);
        Objective tobj = board.registerNewObjective("TeamPoint", "dummy");
        tobj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score redp = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Red"));
        Score whitep = tobj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Red"));
        redp.setScore(0);
        whitep.setScore(0);

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
		ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        board.clearSlot(DisplaySlot.SIDEBAR);
        Objective obj = board.getObjective("TeamPoint");
        Score redp = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Red"));
        Score whitep = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Red"));
        redp.setScore(0);
        whitep.setScore(0);
        obj.unregister();
        Bukkit.getScheduler().cancelAllTasks();
        teamRed.unregister();
        teamWhite.unregister();
        
	}
	public void setScore(String team,Integer point){
		if(team.equalsIgnoreCase("red")){
			redp.setScore(point);

		}else if(team.equalsIgnoreCase("white")){
			whitep.setScore(point);
		}else{

		}
	}

	public String addPlayer(Player p,String team){
		if(team.equalsIgnoreCase("white")){
			teamRed.addPlayer(p);
			HashMap<Player,Integer> players = ListenerClass.getHashMap();
			players.put(p, 3);
			return "Whiteに所属しました";
		}else if(team.equalsIgnoreCase("red")){
			teamWhite.addPlayer(p);
			HashMap<Player,Integer> players = ListenerClass.getHashMap();
			players.put(p, 3);
			return "Redに所属しました";

		}else{
			return "チーム名はred/whiteでお願いします。";
		}
	}

}
