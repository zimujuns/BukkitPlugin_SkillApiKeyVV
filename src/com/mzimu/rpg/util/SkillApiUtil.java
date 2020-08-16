package com.mzimu.rpg.util;

import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;

public class SkillApiUtil {

    public static boolean isClassToNull(PlayerData pd){
        return pd.getClasses().size()==0?true:false;
    }

    public static boolean isClass(PlayerData pd, String title){
        for(PlayerClass playerClass :pd.getClasses())
            if(playerClass.getData().getName().equals(title))
                return true;
        return false;
    }
}
