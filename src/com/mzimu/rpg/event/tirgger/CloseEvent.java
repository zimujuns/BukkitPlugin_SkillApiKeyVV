package com.mzimu.rpg.event.tirgger;

import com.mzimu.rpg.event.SettingsEvent;
import com.mzimu.rpg.event.hud.VVSkillHud;
import com.mzimu.rpg.gui.SetKeyGui;
import com.mzimu.rpg.gui.SetSkillGui;
import com.sucy.skill.SkillAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent {
    public static void OnClose(InventoryCloseEvent e){
        String title = e.getInventory().getTitle();
        switch (title){
            case SetSkillGui.TITLE:
                VVSkillHud.sendMain((Player) e.getPlayer());
                break;
            default:
                if(title.matches(SkillAPI.getPlayerData((OfflinePlayer) e.getPlayer()).getShownClassName()+"\\S*")){
                    SettingsEvent.StopSetSlot((Player) e.getPlayer());
                    break;
                }
        }
    }
}
