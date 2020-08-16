package com.mzimu.rpg.trigger;

import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.event.SettingsEvent;
import com.mzimu.rpg.gui.SetKeyGui;
import lk.vexview.event.KeyBoardPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnVexViewTrigger implements Listener, ZMSkillKeyTrigger {

    @EventHandler
    public void main(KeyBoardPressEvent e){
        if(e.getEventKeyState()){
            Player p = e.getPlayer();
            if (!SettingsEvent.isEnterSet(p) && PlayKeyDate.getPlayKeyData(p).getKeyList().contains(e.getKey())) {
                PlayKeyDate.getPlayKeyData(p).castKeySkill(p, e.getKey());
            }
//            else{
//                SettingsEvent.main(p,e.getKey(),null);
//                new SetKeyGui().open(p);
//            }
        }

    }


}
