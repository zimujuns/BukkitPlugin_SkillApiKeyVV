package com.mzimu.rpg.event.hud;

import com.mzimu.rpg.data.*;
import com.sucy.skill.api.player.PlayerSkill;
import lk.vexview.api.VexViewAPI;
import lk.vexview.builders.ImageBuilder;
import lk.vexview.gui.components.VexHoverText;
import lk.vexview.hud.VexShow;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作用于用户界面
 * 用来显示 技能图标的
 *
 *
 *
 */
public class VVSkillHud {

    /**
     * 按顺序进行赋值
     */
    private static Map<String,VexShow[]> PlayHudData = new HashMap<>();

    private static Map<String,CdHudData[]> PlayCdHudData = new HashMap<>();

    /**
     * 对玩家进行界面设置
     *
     * 在玩家登入游戏的时候执行
     * @return
     */
    public static void playInit(Player p){
        HudMainData hMD = HudMainData.getData(p);
        Map<String,VexShow[]> vexShowMap = new HashMap<>();
        PlayKeyDate pkd = PlayKeyDate.getPlayKeyData(p);
        List<PlayerSkill> skillDataList = pkd.getSkillList();
        List<String> skillNameList = pkd.getSkillListFoString();
        VexShow[] viArray = new VexShow[skillDataList.size()];
        CdHudData[] viCdArray = new CdHudData[skillDataList.size()];
        for(int i=0;i<pkd.getKeyList().size();i++){
            int z = SlotLocDate.getBigLocZ(i);
            int w = (int) (32*hMD.getScale());
            int h = (int) (32*hMD.getScale());
            int margin_y = (int) (hMD.getSlotStartY()*hMD.getScale());
            int margin_x = (int) (hMD.getSlotStartX()*hMD.getScale());
            int interval = (int) (hMD.getSlotInterval()*hMD.getScale());
            int x = (int) (hMD.isScale()?SlotLocDate.getBigLocX(i)*hMD.getScale():SlotLocDate.getSmallLocX(i)*hMD.getScale()) + margin_x;
            // 技能栏的Y轴位置 + 技能栏外框 + 技能大小*个数
//            int y = (hMD.getY() +  + (interval*i));
            int y = hMD.isScale()?(hMD.getY() + (margin_y*(i+(i+1))) + (interval*i) + (w*i) + SlotLocDate.getBigLocY(i)):(hMD.getY() + (margin_y*(i+(i+1))) + (interval*i) + (w*i) + SlotLocDate.getSmallLocY(i));
//            int offx = hMD.getScale()>0.4?1:SlotLocDate.getLocX(i);
//            int offy = hMD.getScale()>0.4?margin_y*(i+(i+1)):SlotLocDate.getLocY(i);
//            int offy = (int)(SlotLocDate.getLocY(i)*hMD.getScale())+h*i+i*(int)(hMD.getSlotInterval()*hMD.getScale());
            String skillName = skillNameList.get(i);
            VexShow readVis;
            CdHudData cdVis;
            if(skillName.equals(PlayKeyDate.getNotSkill())){
                readVis = ImageBuilder.builder()
                        .size(w,h)
                        .background(SkillImgData.getNoSkillImgPath())
                        .location(x,y)
//                        .offset(offx,offy)
                        .imageSize(128,128)
                        .toHUD("slot"+i,0,z);
            }else{
                VexHoverText text = new VexHoverText(pkd.getSkill(i).getData().getIcon(pkd.getSkill(i).getPlayerData()).getItemMeta().getLore());
                readVis = ImageBuilder.builder()
                        .size(w,h)
                        .hover(text)
                        .background(SkillImgData.getReadSkillImgPath(skillName))
                        .location(x,y)
//                        .offset(offx,offy)
                        .imageSize(128,128)
                        .toHUD("slot"+i,0,z);
            }
            cdVis = new CdHudData(w,h,x,y,(z+1),("slotCd"+i));
            viArray[i]=readVis;
            viCdArray[i]=cdVis;
        }
        PlayHudData.put(p.getName(),viArray);
        PlayCdHudData.put(p.getName(),viCdArray);

    }


    public static void sendHud(Player p,boolean is){
        VexShow[] vI = getPlayHudRead(p,is);
        for(VexShow vexImage: vI){
            VexViewAPI.sendHUD(p,vexImage);
        }
    }

    public static VexShow[] getPlayHudRead(Player p,boolean is){
        if (!PlayHudData.containsKey(p.getName())||is){
//            PlayHudData.put(p.getName(),playInit(p));
            playInit(p);
        }
        return PlayHudData.get(p.getName());
    }

    /**
     *
     * @param p
     * @param is true时 就直接初始化
     * @return
     */
    public static CdHudData[] getPlayHudCd(Player p, boolean is){
        if (!PlayHudData.containsKey(p.getName()) || is){
            playInit(p);
        }
        return PlayCdHudData.get(p.getName());
    }

    /**
     * 刷新所有的技能HUD
     * @param p
     * @param is
     */
    public static void sendCdHud(Player p,boolean is){
        int i=0;
        for(CdHudData chd : getPlayHudCd(p,is)){
            VexViewAPI.removeHUD(p,chd);
            String name;
            try{
                name = PlayKeyDate.getPlayKeyData(p).getSkill(i).getData().getName();
            }catch (NullPointerException n){
                continue;
            }
            int time = PlayKeyDate.getPlayKeyData(p).getSkill(i).getCooldown();
            if(time == 0){
                continue;
            }
            int x = chd.getX();
            int y = chd.getY();
            int z = chd.getZ();
            int w = chd.getW();
            int h = chd.getH();
            String id = chd.getId();
            VexViewAPI.sendHUD(p,ImageBuilder.builder()
                    .location(x,y)
                    .size(w,h)
                    .background(SkillImgData.getCdSkillImgPath(name))
                    .toHUD(id,time,z)
            );
            i++;
        }
    }

    /**
     * 在玩家加入游戏
     *      改变窗口
     *      关闭对应的背包栏
     *      触发
     * @param p
     */
    public static void sendMain(Player p){
        HudMainData data = HudMainData.getData(p);
        data.upScale(p);
        double max = VexViewAPI.getPlayerClientWindowHeight(p);
//        double max = 56';'
        max = (max/8)>38?(max/8):22;
//        data.setY(max);
        data.setY(max);
        HudMainData.updata(p,data);
        VexViewAPI.sendHUD(p,ImageBuilder.builder()
                .location(0, (int) max)
                .size((int)(54*data.getScale()),(int)(524*data.getScale()))
//                .size(27,262)
                .background(data.getPath())
                .toHUD("main",0,0));
        sendHud(p,true);
        VVSkillHud.sendCdHud(p,true);


    }



    public static class CdHudData{
        private int w,h,x,y,z;
        private String id;

        public CdHudData(int w, int h, int x, int y, int z, String id) {
            this.w = w;
            this.h = h;
            this.x = x;
            this.y = y;
            this.z = z;
            this.id = id;
        }

        public int getW() {
            return w;
        }

        public int getH() {
            return h;
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

        public String getId() {
            return id;
        }
    }

}
