package me.zackpollard.ExemptPvP;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.zackpollard.ExemptPvP.executors.ExemptExecutor;
import me.zackpollard.ExemptPvP.util.ListStore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ExemptPvP extends JavaPlugin {
	
	public ListStore exemptPlayers;
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable(){
		log.log(Level.INFO, "ExemptPvP Version 0.4 Enabled");
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		this.exemptPlayers = new ListStore(new File(pluginFolder + File.separator + "exempt-players.txt"));
		this.exemptPlayers.load();
		this.getCommand("pvp").setExecutor(new ExemptExecutor(this));
		this.getCommand("exemptpvp").setExecutor(new ExemptExecutor(this));
		
		final FileConfiguration config = this.getConfig();
        config.options().header("Set worlds where Exempt Players will be ignored. Could be used for arena worlds.");
        String[] ignoredworlds = {"world1", "world2", "world3"};
        config.addDefault("ExemptPvP.IgnoredWorlds", Arrays.asList(ignoredworlds));
        config.options().copyDefaults(true);
        saveConfig();
		
		this.getServer().getPluginManager().registerEvents(new MyPvPListener(this), this);
	}
	public void onDisable(){
		log.log(Level.INFO, "ExemptPvP Version 0.4 Disabled");
		this.exemptPlayers.save();
	}
}