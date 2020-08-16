package com.mzimu.rpg.trigger;

import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.event.hud.VVSkillHud;
import com.mzimu.rpg.sqlist.SqlistMain;
import com.mzimu.rpg.util.SkillApiUtil;
import com.sucy.skill.SkillAPI;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.VerificationFinishEvent;
import lk.vexview.hud.MinecraftHud;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.List;

public class OnLogin_QuickTrigger implements ZMSkillKeyTrigger, Listener {

    @EventHandler
    public void OnJoin(PlayerJoinEvent e){
//        PlayKeyDate.putPlayKeyData(e.getPlayer());
        try {
            List<String> list = SqlistMain.getPlaySkillNameList(e.getPlayer());
            PlayKeyDate.putPlayKeyData(e.getPlayer(),list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            e.getPlayer().sendMessage("您的数据异常,无法获取到数据,请通知管理员");
            System.out.println("玩家数据异常!!!请注意");
        }
    }

    @EventHandler
    //这里是判断是否一切准备好了 一切准备好了就执行
    public void OnRead(VerificationFinishEvent e){
        VVSkillHud.sendMain(e.getPlayer());
    }

    @EventHandler
    public void OnQuick(PlayerQuitEvent e){
        PlayKeyDate.delPlayKeyData(e.getPlayer());
    }

}
