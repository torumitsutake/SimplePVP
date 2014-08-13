package com.gmail.sitoa.showplugin.pvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin{
	public ListenerClass listener;
	public static ScoreBoardClass scoreboard;
	private static Plugin instance;
	public void onEnable(){
		instance  = this;
		System.out.println(ChatColor.GREEN+"PVPPlugin起動開始");
		scoreboard = new ScoreBoardClass();
		listener = new ListenerClass();
		System.out.println(ChatColor.GREEN+"リスナー登録完了");
        saveDefaultConfig();
 
 

	}


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("sp")){
			if(args.length <= 0){
				if(args[0].equalsIgnoreCase("start")){
					Bukkit.broadcastMessage(ChatColor.GREEN+"ゲームを開始します。");
					scoreboard.setScore("red", getConfig().getInt("winpoint"));
					scoreboard.setScore("blue", getConfig().getInt("winpoint"));
				}
				//sp setteam [playername] [red/white]
				if(args[0].equalsIgnoreCase("setteam")){
					Player  pl = getServer().getPlayer(args[1]);
					pl.sendMessage(scoreboard.addPlayer(pl, args[2]));
					
				}
				if(args[0].equalsIgnoreCase("configreload")){
					this.reloadConfig();
				}
				if(args[0].equalsIgnoreCase("setspawnpoint")){
					if(args[1].equalsIgnoreCase("red")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							Location loc = p.getLocation();
							getConfig().set("redspawn.x", loc.getBlockX());
							getConfig().set("redspawn.y", loc.getBlockY());
							getConfig().set("redspawn.z", loc.getBlockZ());
						}else{
							sender.sendMessage("ゲーム内からのみ使用可能なコマンドです。");
						}
					}else if(args[1].equalsIgnoreCase("white")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							Location loc = p.getLocation();
							getConfig().set("whitespawn.x", loc.getBlockX());
							getConfig().set("whitespawn.y", loc.getBlockY());
							getConfig().set("whitespawn.z", loc.getBlockZ());
							
						}else{
							sender.sendMessage("ゲーム内からのみ使用可能なコマンドです。");
						}
						
					}
						
					
				}
				if(args[0].equalsIgnoreCase("gamereset")){
					scoreboard.reset();
				}
			}else{
				sender.sendMessage(ChatColor.RED+"please set option : オプションを設定してください。");
			}
		}


		return false;

	}
	public static  Plugin getInstance(){
		return instance;
	}
	public static ScoreBoardClass getscoreboeard(){

		return scoreboard;

	}

}
