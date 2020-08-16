package com.mzimu.rpg.trigger;

import com.mzimu.rpg.event.SettingsEvent;
import com.mzimu.rpg.event.tirgger.ClickEvent;
import com.mzimu.rpg.event.tirgger.CloseEvent;
import com.mzimu.rpg.gui.SetSkillGui;
import com.sucy.skill.SkillAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class OnInventoryTrigger implements ZMSkillKeyTrigger, Listener {

    @EventHandler
    public void OnOpen(InventoryOpenEvent e){
        String title = e.getInventory().getTitle();
        switch (title){
            case SetSkillGui
                    .TITLE:
                SettingsEvent.EnterSetSlot((Player) e.getPlayer(),-1);
            default:
                if(!title.matches(SkillAPI.getPlayerData((OfflinePlayer) e.getPlayer()).getShownClassName()+"\\S*")) {
                    SettingsEvent.StopSetSlot((Player) e.getPlayer());
                }
        }
    }

    @EventHandler
    public void OnClick(InventoryClickEvent e){
        ClickEvent.OnClick(e);
    }

    @EventHandler
    public void OnClose(InventoryCloseEvent e){
        CloseEvent.OnClose(e);
    }

}
