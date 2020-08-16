package com.mzimu.rpg.data;

import com.mzimu.rpg.ZMSkillKeyMain;
import lk.vexview.api.VexViewAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HudMainData {


    private static Map<String, HudMainData> hudMainDataMap = new HashMap<>();
    private int x,y,z,slotStartX,slotStartY,slotInterval;
    private double scale;
    private String path;

    public HudMainData() {
        FileConfiguration fc = ZMSkillKeyMain.getPlugin().getConfig();
        x=fc.getInt("Main.x");
        y=fc.getInt("Main.y");
        z=fc.getInt("Main.z");
        slotStartX = fc.getInt("Main.SlotStartX");
        slotStartY = fc.getInt("Main.SlotStartY");
        slotInterval = fc.getInt("Main.SlotInterval");
        path = fc.getString("Main.Path");
    }

    public HudMainData(HudMainData hudMainData,Player p) {
        x = hudMainData.getX();
        y = hudMainData.getY();
        z = hudMainData.getZ();
        slotStartX = hudMainData.getSlotStartX();
        slotStartY = hudMainData.getSlotStartY();
        slotInterval = hudMainData.getSlotInterval();
        path = hudMainData.getPath();
        setScale(p);
    }

    public static HudMainData getData(Player p) {
        return hudMainDataMap.containsKey(p.getName())?hudMainDataMap.get(p.getName()):new HudMainData(ZMSkillKeyMain.hudMainData,p);
    }

    public static void updata(Player p, HudMainData data) {
        hudMainDataMap.put(p.getName(),data);
    }


    public void upScale(Player p) {
        setScale(p);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getSlotStartX() {
        return slotStartX;
    }

    public int getSlotStartY() {
        return slotStartY;
    }

    public int getSlotInterval() {
        return slotInterval;
    }

    public String getPath() {
        return path;
    }

    public void setScale(Player p) {
        int a = (int) (VexViewAPI.getPlayerClientWindowHeight(p)/ZMSkillKeyMain.WIND_Height * 10) ;
        this.scale = a>4?0.6:0.5;
    }

    public boolean isScale(){
        return scale>0.5;
    }

    public double getScale() {
        return scale;
    }

    public void setY(double max) {
        this.y = (int) max;
    }
}
