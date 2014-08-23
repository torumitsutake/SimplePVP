package com.gmail.sitoa.showplugin.pvp;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
		p = MainClass.getInstance();
		p.getServer().getPluginManager().registerEvents(this, p);
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e){
		Player pl = e.getEntity();
		Bukkit.broadcastMessage(ChatColor.GOLD+pl.getName()+"がやられた");
		String team ="";
		Team red = p.getServer().getScoreboardManager().getMainScoreboard().getTeam("red");
		Team white = p.getServer().getScoreboardManager().getMainScoreboard().getTeam("white");
		if(red.getPlayers().contains(Bukkit.getOfflinePlayer(pl.getName()))){
			team = "red";
		}else if(white.getPlayers().contains(Bukkit.getOfflinePlayer(pl.getName()))){
			team = "white";
		}
		if(!(team.equalsIgnoreCase(""))){
			ScoreBoardClass score = MainClass.getscoreboeard();
			score.addScore(team, -1);
			int hp = playerhp.get(pl);
			hp--;
			if(hp <= 0){
				pl.teleport(pl.getWorld().getSpawnLocation());
				Location loc = pl.getWorld().getSpawnLocation();
				Bukkit.broadcastMessage(ChatColor.GOLD+pl.getName()+"が脱落しました");
				p.getServer().dispatchCommand(p.getServer().getConsoleSender(),"spawnpoint "+pl.getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
				if(pl.getDisplayName().equalsIgnoreCase(ChatColor.RED+pl.getName()+"")){
					red.removePlayer(pl);
				}else if(pl.getDisplayName().equalsIgnoreCase(ChatColor.WHITE+pl.getName()+"")){
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
