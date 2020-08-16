package com.mzimu.rpg.trigger;

import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.event.SettingsEvent;
import com.mzimu.rpg.event.hud.VVSkillHud;
import com.mzimu.rpg.util.KeyUtil;
import com.sucy.skill.api.event.PlayerSkillDowngradeEvent;
import com.sucy.skill.api.event.PlayerSkillUnlockEvent;
import com.sucy.skill.api.event.PlayerSkillUpgradeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnSkillApiTrigger implements ZMSkillKeyTrigger, Listener {

    /**
     * 当处于设置模式的时候 取消升级事件
     * @param e
     */
    @EventHandler
    public void OnSkillUp(PlayerSkillUpgradeEvent e){
        if(SettingsEvent.isEnterSet(e.getPlayerData().getPlayer())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnSkillUn(PlayerSkillDowngradeEvent e){
        if(SettingsEvent.isEnterSet(e.getPlayerData().getPlayer())){
            e.setCancelled(true);
        }

    }

}
