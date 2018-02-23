package me.zackpollard.ExemptPvP.executors;

import me.zackpollard.ExemptPvP.ExemptPvP;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ExemptExecutor implements CommandExecutor{
	
	private ExemptPvP plugin;
	
	public ExemptExecutor(ExemptPvP plugin){
		
		this.plugin = plugin;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("exemptpvp.command.add") || sender.hasPermission("exemptpvp.admin")){
			if(args.length == 1 && args[0].equalsIgnoreCase("exempt")){
				if(plugin.exemptPlayers.contains(sender.getName())){
					
					sender.sendMessage(ChatColor.RED + "You are already exempt from PvP so you can't become exempt again...");
					
					return true;
					
				}
				
				plugin.exemptPlayers.add(sender.getName());
				plugin.exemptPlayers.save();
		
				sender.sendMessage(ChatColor.GREEN + "You are now exempt from all Player vs Player damage and can't damage others :D");
		
				return true;
			}
			
		}
		if(sender.hasPermission("exemptpvp.command.remove") || sender.hasPermission("exemptpvp.admin")){
			if(args.length == 1 && args[0].equalsIgnoreCase("remove")){
				if(!plugin.exemptPlayers.contains(sender.getName())){
					
					sender.sendMessage(ChatColor.RED + "You aren't exempt from PvP yet so you can't remove yourself from the ExemptPvP list...");
					
					return true;
				}
				
				plugin.exemptPlayers.remove(sender.getName());
				plugin.exemptPlayers.save();
				
				sender.sendMessage(ChatColor.GREEN + "You are now removed from the exempt players list... Let the battles, commence!");
				return true;
			}
		}
		if(sender.hasPermission("exemptpvp.command.list") || sender.hasPermission("exemptpvp.admin")){
			if(args.length == 1 && args[0].equalsIgnoreCase("list")){
				
				sender.sendMessage(ChatColor.RED + "[ExemptPvP]" + ChatColor.GREEN + "All of the following players are exempt from PvP");
				
				for(String s : plugin.exemptPlayers.getValues()){

					sender.sendMessage(ChatColor.GRAY + "====== " + ChatColor.GOLD + s + ChatColor.GRAY + " ======");
					
				}
				return true;
			}
		}
		if(sender.hasPermission("exemptpvp.command.add.others") || sender.hasPermission("exemptpvp.admin")){
			if(args.length == 2 && args[0].equalsIgnoreCase("exempt")){
				if(plugin.exemptPlayers.contains(args[1])){
					
					sender.sendMessage(ChatColor.RED + args[1] + " is already exempt from PvP so they can't become exempt again...");
					
					return true;
				}
				
				plugin.exemptPlayers.add(args[1]);
				plugin.exemptPlayers.save();
				
				sender.sendMessage(ChatColor.GREEN + args[1] + " is now exempt from all Player vs Player damage and can't damage others :D");
				
				return true;
			}
		}
		if(sender.hasPermission("exemptpvp.command.remove.others") || sender.hasPermission("exemptpvp.admin")){
			if(args.length == 2 && args[0].equalsIgnoreCase("remove")){
				if(!plugin.exemptPlayers.contains(args[1])){
					
					sender.sendMessage(ChatColor.RED + args[1] + " isn't exempt from PvP yet so how can they be removed from the ExemptPvP list...");
					
					return true;
				}
				
				plugin.exemptPlayers.remove(args[1]);
				plugin.exemptPlayers.save();
				
				sender.sendMessage(ChatColor.GREEN + args[1] + " is now removed from the exempt players list... Let the battles, commence!");
				
				return true;
				
			}
		}
		if(sender.hasPermission("exemptpvp.command.reload") || sender.hasPermission("exemptpvp.admin")){
			if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "ExemptPvP config reloaded");
				
				return true;
			}
		}
		sender.sendMessage(ChatColor.RED + "Proper usage is /pvp (exempt/remove/list/reload)");
	
		return true;
	}
}