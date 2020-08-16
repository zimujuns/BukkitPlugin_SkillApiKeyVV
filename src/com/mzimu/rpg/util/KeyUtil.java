package com.mzimu.rpg.util;

import lk.vexview.event.MinecraftKeys;
import net.minecraft.server.v1_12_R1.MinecraftKey;

public class KeyUtil {

    private static final String NULLKEY = "未设定";

    public static String getKeyName(int key){
        for(MinecraftKeys mKeys:MinecraftKeys.values()){
            if(mKeys.isTheKey(key)){
                return mKeys.name();
            }
        }
        return NULLKEY;
    }

}
