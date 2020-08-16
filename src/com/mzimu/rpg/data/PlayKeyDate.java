package com.mzimu.rpg.data;

import com.mzimu.rpg.ZMSkillKeyMain;
import com.mzimu.rpg.event.hud.VVSkillHud;
import com.mzimu.rpg.sqlist.SqlistMain;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;
import com.sucy.skill.dynamic.mechanic.MessageMechanic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayKeyDate {
    private static Map<String, PlayKeyDate> playKeyDataMap = new HashMap<>();
    private List<Integer> keyList = initializeListForKey();
    private List<PlayerSkill> skillList = initializeListForSkill();
    private static String skillPath = ".PlayerSkillData";
    private static String keyPath = ".KeyData";
    private static final int MAXSKILL = 9;
    private static final String NOT_SKILL = "暂未设置";

    /**
     * 用来直接触发存储List中对应的技能
     * @param player
     * @param key
     */
    public void castKeySkill(Player player, int key){
        int skillIndex = keyList.indexOf(key);
        PlayerSkill s = getSkill(skillIndex);
        try{
            SkillAPI.getPlayerData(player).cast(s);
            VVSkillHud.sendCdHud(player,false);
        }catch(NullPointerException e){
            return;
        }

    }

    public PlayerSkill getSkill(int i) {
        return skillList.size()<=i?null:skillList.get(i);
    }

    public List<PlayerSkill> getSkillList() {
        return skillList;
    }

    public List<Integer> getKeyList() {
        return keyList;
    }

    /**
     * 将玩家技能表转化为能存储在Config中的String表
     * @return
     */
    public List<String> getSkillListFoString(){
        List<String> v = new ArrayList<>();
        for(PlayerSkill skill:skillList){
            try{
                v.add(skill.getData().getName());
            }catch (NullPointerException e){
                v.add(getNotSkill());
            }
        }
        return v;
    }

    /**
     * 首先判断按键是否是设定的按键
     * 再进行设定
     * @param playerSkill 玩家的技能数据
     * @param key 玩家的按键数据
     * @return
     */
    public boolean setSkill(PlayerSkill playerSkill,int key){
        int i = keyList.indexOf(keyList.get(key));
        if(i!=-1){
            skillList.set(i,playerSkill);
            return true;
        }
        return false;

    }


    /**
     * 修改原有的技能key
     * @param loc 在list的位置
     * @param newKey 替换的Key
     */
    public void setKey(int loc,int newKey){
        if(loc > MAXSKILL){
            throw new ArrayIndexOutOfBoundsException("当前设置的值超过所预设的值");
        }
        keyList.set(loc,newKey);
    }

    /**
     * 初始化按键技能表
     * @return
     */
    public List<PlayerSkill> initializeListForSkill(){
        List<PlayerSkill> skillList = new ArrayList<>();
        for(int i=0;i<MAXSKILL;i++){
            skillList.add(new PlayerSkill(null,null,null));
        }
        return skillList;
    }
    /**
     * 初始化按键表
     */
    public static List<Integer> initializeListForKey(){
        List<Integer> skillList = new ArrayList<>();
        for(int i=0;i<MAXSKILL;i++){
            skillList.add(i+2);
        }
        return skillList;
    }


    /**
     * 将Config中的玩家数据内容 在玩家登入的时候进行替换
     * @param skillList 技能名称表
     * @param keyList 按键表
     * @param p 目标玩家
     */
    public void replaceAll(List<String> skillList,List<Integer> keyList,Player p){
        List<PlayerSkill> list = new ArrayList<>();
        PlayerData data = SkillAPI.getPlayerData(p);
        //开辟长度为keyList.size的空间
        if(skillList.size() == 0){
            list = initializeListForSkill();
        }else{
            for(String skillName : skillList){
                list.add(data.getSkill(skillName));
            }
            //开辟剩余的长度 保证两个list长度一致
            if(skillList.size() != keyList.size()){
                for(int i=skillList.size()-1;i<keyList.size();i++){
                    list.add(new PlayerSkill(null,null,null));
                }
            }
        }

        this.skillList = list;
        this.keyList = keyList;

    }

    /**
     * 这个是从config中获取的方法
     * @param p
     */
//    public static void putPlayKeyData(Player p){
//        //当不存在路径 就直接初始化一个
//        // 存在路径就直接获取
//        if(ZMSkillKeyMain.isPath(p)) {
//            putPlayKeyData(p, ZMSkillKeyMain.getPlugin().getConfig());
//        }else {
//            playKeyDataMap.put(p.getName(), new PlayKeyDate());
//        }
//    }

    /**
     * 这个是从Sqlite中获取数据的方法
     * @param p
     */
    public static void putPlayKeyData(Player p,List<String> list){
        PlayKeyDate pkd = new PlayKeyDate();
        pkd.replaceAll(list,initializeListForKey(),p);
        playKeyDataMap.put(p.getName(),pkd);
    }

    /**
     * 获取存储在Config的玩家数据
     * 并且转换成在插件中存储的PlayKeyData 对应的两个List
     * @param p
     * @param fc
     */
//    public static void putPlayKeyData(Player p, FileConfiguration fc){
//        List<String> skillList = fc.getStringList("PlayerData."+p.getName()+skillPath);
////        List<Integer> keyList = fc.getIntegerList("PlayerData."+p.getName()+ keyPath);
//        PlayKeyDate pkd = new PlayKeyDate();
//        pkd.replaceAll(skillList,initializeListForKey(),p);
//        playKeyDataMap.put(p.getName(),pkd);
//    }

    /**
     * 删除玩家数据的操作
     *
     * 在玩家离开游戏的时候触发
     * @param p
     */
    public static void delPlayKeyData(Player p){
        PlayKeyDate pdk = playKeyDataMap.remove(p.getName());
//        save(ZMSkillKeyMain.getPlugin().getConfig(),pdk,p.getName());
        save(pdk,p);
    }

    /**
     * 删除玩家数据的操作
     *
     * 在玩家右键表的时候进行触发
     * @param p
     */
    public static void delPlayKeyData(Player p,int i){
        playKeyDataMap.get(p.getName()).getSkillList().set(i,new PlayerSkill(null,null,null));
    }

    /**
     * 主要的保存操作 保存到Config中
     */
//    public static void save(FileConfiguration fc, PlayKeyDate pdk, String playName){
//        fc.set("PlayerData."+playName+skillPath,pdk.getSkillListFoString());
////        fc.set("PlayerData."+playName+ keyPath,pdk.getKeyList());
//        ZMSkillKeyMain.getPlugin().saveConfig();
//    }

    /**
     * 数据库的保存操作
     */
    public static void save(PlayKeyDate pdk,Player p){
        try {
            SqlistMain.savePlayDataToSqlit(p,pdk.getSkillListFoString());
        } catch (SQLException throwables) {
            System.out.println("玩家数据存储失败,请检查!");
            throwables.printStackTrace();
        }
    }

    public static PlayKeyDate getPlayKeyData(Player p){
        return playKeyDataMap.get(p.getName());
    }

    public static String getNotSkill() {
        return NOT_SKILL;
    }

    public boolean isEmpty() {
        for(Integer i : keyList)
            if(i!=-1)
                return true;
        return false;
    }
}
