package com.gmail.sitoa.showplugin.pvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi.Color;

public class MainClass extends JavaPlugin{
	public ListenerClass listener;
	public static ScoreBoardClass scoreboard;
	private static Plugin instance;
	public void onEnable(){

		getConfig().options().copyDefaults(true);
		instance  = this;
		System.out.println(Color.GREEN+"PVPPlugin起動開始");
		scoreboard = new ScoreBoardClass();
		listener = new ListenerClass();
		System.out.println(Color.GREEN+"クラス読み込み完了");
        saveDefaultConfig();
		System.out.println(Color.GREEN+"起動終了");

	}


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("sp")){
			if(!(args.length == 0)){
				//ゲーム開始コマンド
				if(args[0].equalsIgnoreCase("start")){
					Bukkit.broadcastMessage(ChatColor.GREEN+"ゲームを開始します。");
					scoreboard.setScore("red", getConfig().getInt("winpoint"));
					scoreboard.setScore("blue", getConfig().getInt("winpoint"));
				}
				//プレイヤーをチームに所属させる。
				//sp set [playername] [red/white]
				if(args[0].equalsIgnoreCase("set")){
					Player  pl = getServer().getPlayer(args[1]);
				Location loc =null;
				if(args[2].equalsIgnoreCase("red")){
						loc = new Location(getServer().getWorld("world"), getConfig().getDouble("redspawn.x"), getConfig().getDouble("redspawn.y"), getConfig().getDouble("redspawn.z"));
				}else if(args[2].equalsIgnoreCase("white")){
					loc = new Location(getServer().getWorld("world"), getConfig().getDouble("whitespawn.x"), getConfig().getDouble("whitespawn.y"), getConfig().getDouble("whitespawn.z"));
				}else{
					sender.sendMessage(ChatColor.RED+"チーム名は[red/white]で設定してください。");
					return true;
				}
				getServer().dispatchCommand(getServer().getConsoleSender(),"spawnpoint "+pl.getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
						pl.sendMessage(ChatColor.GOLD+scoreboard.addPlayer(pl, args[2]));
						sender.sendMessage(ChatColor.AQUA+"設定完了しました。");

				}
				//Configをリロードさせます。
				if(args[0].equalsIgnoreCase("configreload")){
					this.reloadConfig();
				}
				//スポーンポイントを設定します。
				if(args[0].equalsIgnoreCase("setspawnpoint")){
					if(args[1].equalsIgnoreCase("red")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							Location loc = p.getLocation();
							getConfig().set("redspawn.x", loc.getBlockX());
							getConfig().set("redspawn.y", loc.getBlockY());
							getConfig().set("redspawn.z", loc.getBlockZ());
							saveConfig();
							Bukkit.broadcastMessage("設定完了");
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
							saveConfig();
							Bukkit.broadcastMessage("設定完了");

						}else{
							sender.sendMessage("ゲーム内からのみ使用可能なコマンドです。");
						}

					}


				}
				//ゲームリセットするコマンドです。
				if(args[0].equalsIgnoreCase("gamereset")){
					scoreboard.reset();
				}
				//それ以外のコマンドでの例外設定。
			}else{
				sender.sendMessage(ChatColor.RED+"please set option : オプションを設定してください。");
			}
			return true;
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
