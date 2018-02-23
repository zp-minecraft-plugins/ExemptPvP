package me.zackpollard.ExemptPvP;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MyPvPListener implements Listener{
	
	private ExemptPvP plugin;
	
	public MyPvPListener(ExemptPvP plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamageEvent(EntityDamageEvent event){
		List<String> ignoredworlds = plugin.getConfig().getStringList("ExemptPvP.IgnoredWorlds");
		for(String worldname : ignoredworlds){
			if(event.getEntity().getWorld().getName().equals(worldname)){
				return;
			}
		}
		if(event instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
            if(event.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
            	if(event.getEntity() instanceof Player){
        			Player p = (Player) event.getEntity();
        			String playerName = p.getName();
        			Player d = (Player) e.getDamager();
        			String damagerName = d.getName();
        			if(plugin.exemptPlayers.contains(playerName) || p.hasPermission("exemptpvp.exempt")){
        				event.setCancelled(true);
        				d.sendMessage(ChatColor.RED + "You cannot damage that player because they are PvP neutral!");
        				return;
        			}
        			if(plugin.exemptPlayers.contains(damagerName) || d.hasPermission("exemptpvp.exempt")){
        				event.setCancelled(true);
        				d.sendMessage(ChatColor.RED + "Sorry, you cannot damage this player because you have decided to be PvP neutral");
        				return;
        			}
            	}
            }
            if(event.getEntity() instanceof Player && e.getDamager() instanceof Arrow) {
            	Player p = (Player) event.getEntity();
            	String playerName = p.getName();
            	Arrow i = (Arrow) e.getDamager();
            	Entity damagingEntity = (Entity) i.getShooter();
            	if(damagingEntity instanceof Player){
            		Player d = (Player) i.getShooter();
		        	String damagerName = d.getName();
		        	if(plugin.exemptPlayers.contains(playerName) || p.hasPermission("exemptpvp.exempt")){
						event.setCancelled(true);
						d.sendMessage(ChatColor.RED + "You cannot damage that player because they are PvP neutral!");
						return;
					}
					if(plugin.exemptPlayers.contains(damagerName) || d.hasPermission("exemptpvp.exempt")){
						event.setCancelled(true);
						d.sendMessage(ChatColor.RED + "Sorry, you cannot damage this player because you have decided to be PvP neutral");
						return;
					}
    			}
            }
		}
    }
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPotionSplashEvent(PotionSplashEvent event){
		List<String> ignoredworlds = plugin.getConfig().getStringList("ExemptPvP.IgnoredWorlds");
		for(String worldname : ignoredworlds){
			if(event.getEntity().getWorld().getName().equals(worldname)){
				return;
			}
		}
		ArrayList<Player> affectedPlayers = new ArrayList<Player>();
		affectedPlayers.clear();
		for(LivingEntity p : event.getAffectedEntities()){
			if(p instanceof Player){
				Player d = (Player) p;
				affectedPlayers.add(d);
			}
		}
		Player d = (Player) event.getEntity().getShooter();
		String damagerName = d.getName();
		for(Player player : affectedPlayers){
			Player p = player;
			String playerName = p.getName();
        	if(plugin.exemptPlayers.contains(playerName) || p.hasPermission("exemptpvp.exempt")){
				event.setCancelled(true);
				d.sendMessage(ChatColor.RED + "You cannot damage that player because they are PvP neutral!");
			}
		}
		if(plugin.exemptPlayers.contains(damagerName) || d.hasPermission("exemptpvp.exempt")){
			event.setCancelled(true);
			d.sendMessage(ChatColor.RED + "Sorry, you cannot damage this player because you have decided to be PvP neutral");
		}
	}
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event){
		List<String> ignoredworlds = plugin.getConfig().getStringList("ExemptPvP.IgnoredWorlds");
		for(String worldname : ignoredworlds){
			if(event.getPlayer().getWorld().getName().equals(worldname)){
				return;
			}
		}
		Player d = event.getPlayer();
		String damagerName = d.getName();
		if(d.getItemInHand().getTypeId() == 259 || d.getItemInHand().getTypeId() == 385 || d.getItemInHand().getTypeId() == 46){
			if(plugin.exemptPlayers.contains(damagerName) || d.hasPermission("exemptpvp.exempt")){
				for(Entity e : d.getNearbyEntities(5, 5, 5)){
					if(e instanceof Player){
						Player p = (Player) e;
						if(!(p.getName() == damagerName)){
							event.setCancelled(true);
							d.sendMessage(ChatColor.RED + "Sorry, you cannot do this near another player because you have decided to be PvP neutral");
							return;
						}
					}
				}
			}
			for(Entity e : d.getNearbyEntities(5, 5, 5)){
				if(e instanceof Player){
					Player p = (Player) e;
					String playerName = p.getName();
		        	if(plugin.exemptPlayers.contains(playerName) || p.hasPermission("exemptpvp.exempt")){
						event.setCancelled(true);
						d.sendMessage(ChatColor.RED + "You cannot do this near an exempt player!");
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event){
		List<String> ignoredworlds = plugin.getConfig().getStringList("ExemptPvP.IgnoredWorlds");
		for(String worldname : ignoredworlds){
			if(event.getPlayer().getWorld().getName().equals(worldname)){
				return;
			}
		}
		Player d = event.getPlayer();
		String damagerName = d.getName();
		if(d.getItemInHand().getTypeId() == 327){
			if(plugin.exemptPlayers.contains(damagerName) || d.hasPermission("exemptpvp.exempt")){
				for(Entity e : d.getNearbyEntities(5, 5, 5)){
					if(e instanceof Player){
						Player p = (Player) e;
						if(!(p.getName() == damagerName)){
							event.setCancelled(true);
							d.sendMessage(ChatColor.RED + "Sorry, you cannot empty a lava bucket near another player because you have decided to be PvP neutral");
							return;
						}
					}
				}
			}
			for(Entity e : d.getNearbyEntities(5, 5, 5)){
				if(e instanceof Player){
					Player p = (Player) e;
					String playerName = p.getName();
		        	if(plugin.exemptPlayers.contains(playerName) || p.hasPermission("exemptpvp.exempt")){
						event.setCancelled(true);
						d.sendMessage(ChatColor.RED + "You cannot empty a lava bucket near an exempt player!");
					}
				}
			}
		}
	}
}