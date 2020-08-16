package com.mzimu.rpg.data;


import com.mzimu.rpg.ZMSkillKeyMain;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class SlotLocDate {
    private static Map<Integer, HudLocData> slotBigLocMap = init(ZMSkillKeyMain.getPlugin().getConfig(),true);
    private static Map<Integer, HudLocData> slotSmallLocMap = init(ZMSkillKeyMain.getPlugin().getConfig(),false);

    private static Map<Integer, HudLocData> init(FileConfiguration fc,boolean is) {
        Map<Integer, HudLocData> slotLoc = new HashMap<>();
        if(is){
            for(int i=0;i<9;i++){
                int x = fc.getInt("Slot." + i + ".x");
                int y = fc.getInt("Slot." + i + ".y");
                int z = fc.getInt("Slot." + i + ".z");
                slotLoc.put(i,new HudLocData(x,y,z));
            }
            return slotLoc;
        }else{
            for(int i=0;i<9;i++){
                int x = fc.getInt("Slot2." + i + ".x");
                int y = fc.getInt("Slot2." + i + ".y");
                int z = fc.getInt("Slot2." + i + ".z");
                slotLoc.put(i,new HudLocData(x,y,z));
            }
            return slotLoc;
        }

    }




    public static int getBigLocX(int slot){
        return slotBigLocMap.containsKey(slot)? slotBigLocMap.get(slot).getX():0;
    }

    public static int getBigLocY(int slot){
        return slotBigLocMap.containsKey(slot)? slotBigLocMap.get(slot).getY():0;
    }

    public static int getBigLocZ(int slot){
        return slotBigLocMap.containsKey(slot)? slotBigLocMap.get(slot).getZ():0;
    }

    public static int getSmallLocX(int slot){
        return slotBigLocMap.containsKey(slot)? slotSmallLocMap.get(slot).getX():0;
    }

    public static int getSmallLocY(int slot){
        return slotBigLocMap.containsKey(slot)? slotSmallLocMap.get(slot).getY():0;
    }

    public static int getSmallLocZ(int slot){
        return slotBigLocMap.containsKey(slot)? slotSmallLocMap.get(slot).getZ():0;
    }

}
