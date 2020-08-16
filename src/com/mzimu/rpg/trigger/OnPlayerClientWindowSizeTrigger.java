package com.mzimu.rpg.trigger;

import com.mzimu.rpg.event.hud.VVSkillHud;
import lk.vexview.event.PlayerClientWindowSizeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerClientWindowSizeTrigger implements ZMSkillKeyTrigger, Listener {

    @EventHandler
    public void main(PlayerClientWindowSizeEvent e){
//        int height = e.getClientWindowHeight(),width = e.getClientWindowWidth();
        VVSkillHud.sendMain(e.getPlayer());
//        VVSkillHud.hud_UpdateOfWinSize(height,width,e.getPlayer());
    }

}
