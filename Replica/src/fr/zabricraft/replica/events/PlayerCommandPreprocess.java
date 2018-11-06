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

package fr.zabricraft.replica.events;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.zabricraft.replica.Replica;
import fr.zabricraft.replica.utils.Game;
import fr.zabricraft.replica.utils.Game.GameState;
import fr.zabricraft.replica.utils.ZabriPlayer;

public class PlayerCommandPreprocess implements Listener {
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){
		ZabriPlayer zp = Replica.getInstance().getPlayer(e.getPlayer().getUniqueId());
		if(zp.getCurrentGame() != 0){
			for(Game g : Replica.getInstance().getGames()){
				if(g.getId() == zp.getCurrentGame() && g.getState().equals(GameState.IN_GAME)){
					for(UUID uuid : g.getAllPlayers()){
						if(e.getPlayer().getUniqueId().equals(uuid)){
							if(!e.getMessage().equalsIgnoreCase("/replica leave")){
								e.setCancelled(true);
								e.getPlayer().sendMessage("§c"+Replica.getInstance().getMessages().get("cmd-error-only-leave"));
							}
							return;
						}
					}
				}
			}
		}
	}

}
