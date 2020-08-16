package com.mzimu.rpg.event;


import com.mzimu.rpg.data.PlayKeyDate;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;

public class SettingsEvent {

    private static Map<String,Integer> setKeyList = new HashMap<>();

    /**
     * 判断玩家是否进入了设置模式，若进入就执行对应的事件
     * 执行后关闭玩家的设置模式
     * @param p 目标对象
     * @param v 是点击的按键Id
     * @param skill 是点击的技能
     */
    public static void main(Player p, int v, PlayerSkill skill) {
        if (skill == null){
            PlayKeyDate.getPlayKeyData(p).setKey(getSetSlot(p), v);
        }else{
            if(skill.getLevel() == 0){
                return;
            }
            PlayKeyDate.getPlayKeyData(p).setSkill(skill,getSetSlot(p));
        }
        StopSetSlot(p);
    }


    public static void EnterSetSlot(Player p, int i){
        setKeyList.put(p.getName(),i);
    }

    public static Integer getSetSlot(Player p){
        return setKeyList.get(p.getName());
    }

    public static void StopSetSlot(Player p){
        setKeyList.remove(p.getName());
    }

    public static boolean isEnterSet(Player p){
        return setKeyList.containsKey(p.getName());
    }

}
