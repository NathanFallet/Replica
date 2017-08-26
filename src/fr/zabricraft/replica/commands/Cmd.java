/*
 *  Copyright (C) 2017 FALLET Nathan
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 */

package fr.zabricraft.replica.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.zabricraft.replica.Replica;
import fr.zabricraft.replica.utils.ZabriPlayer;

public class Cmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 0){
			if(args[0].equalsIgnoreCase("goto")){
				if(sender.hasPermission("replica.admin")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						p.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-goto-success"));
						p.teleport(new Location(Bukkit.getWorld("Replica"), 3.5, 65, 4.5));
					}else{
						sender.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-error-not-a-player"));
					}
				}else{
					sender.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-error-perm"));
				}
			}else if(args[0].equalsIgnoreCase("buildmode")){
				if(sender.hasPermission("replica.admin")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						ZabriPlayer zp = Replica.getInstance().getPlayer(p.getUniqueId());
						if(zp != null){
							zp.setBuildmode(!zp.isBuildmode());
							if(zp.isBuildmode()){
								p.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-buildmode-enable"));
							}else{
								p.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-buildmode-disable"));
							}
						}
					}else{
						sender.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-error-not-a-player"));
					}
				}else{
					sender.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-error-perm"));
				}
			}else if(args[0].equalsIgnoreCase("leave")){
				if(sender instanceof Player){
					Player p = (Player) sender;
					ZabriPlayer zp = Replica.getInstance().getPlayer(p.getUniqueId());
					if(zp.getCurrentGame() != 0){
						zp.setCurrentGame(0);
						zp.setPlaying(false);
						zp.setFinish(false);
						zp.setPlot(0);
						Bukkit.dispatchCommand(p, Replica.getInstance().getConfig().getString("spawn-command"));
						p.setGameMode(GameMode.SURVIVAL);
						p.getInventory().clear();
						p.updateInventory();
					}else{
						p.sendMessage("§c"+Replica.getInstance().getMessages().get("chat-no-game"));
					}
				}else{
					sender.sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-error-not-a-player"));
				}
			}else{
				sendHelp(sender, label);
			}
		}else{
			sendHelp(sender, label);
		}
		return true;
	}
	
	public void sendHelp(CommandSender sender, String label){
		if(sender.hasPermission("replica.admin")){
			sender.sendMessage("§e/"+label+" goto : "+Replica.getInstance().getMessages().get("cmd-help-goto")+"\n"
				+ "§e/"+label+" buildmode : "+Replica.getInstance().getMessages().get("cmd-help-buildmode")+"\n");
		}
		sender.sendMessage("§e/"+label+" leave : "+Replica.getInstance().getMessages().get("cmd-help-leave"));
	}

}
