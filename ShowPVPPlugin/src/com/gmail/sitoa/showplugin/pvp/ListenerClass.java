package com.gmail.sitoa.showplugin.pvp;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;

public class ListenerClass implements Listener{
	private Plugin p = null;
	public static HashMap<Player,Integer> playerhp = new HashMap<Player,Integer>();
	ListenerClass(){

		p.getServer().getPluginManager().registerEvents(this, p);
		p = MainClass.getInstance();
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e){
		Player pl = e.getEntity();
		String team ="";
		Team red = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("red");
		Team white = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("white");
		if(red.getPlayers().contains(pl)){
			team = "red";
		}else if(white.getPlayers().contains(pl)){
			team = "white";
		}
		if(!(team.equalsIgnoreCase(""))){
			ScoreBoardClass score = MainClass.getscoreboeard();
			score.addScore(team, -1);
			int hp = playerhp.get(pl);
			hp--;
			if(hp <= 0){
				pl.teleport(pl.getWorld().getSpawnLocation());
				Bukkit.broadcastMessage(ChatColor.GOLD+pl.getName()+"が脱落しました");

				if(red.getPlayers().contains(pl)){
					red.removePlayer(pl);
				}else if(white.getPlayers().contains(pl)){
					white.removePlayer(pl);
				}

			}else{
			playerhp.put(pl, hp);
			}
			}

	}

	public static HashMap<Player,Integer> getHashMap(){
		return playerhp;

	}

}
