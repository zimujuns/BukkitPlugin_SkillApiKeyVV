package com.mzimu.rpg.data;

import com.mzimu.rpg.ZMSkillKeyMain;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class SkillImgData {
    private static Map<String, Map<String,String>> skillImgPathMap = imgPathIni(ZMSkillKeyMain.getPlugin().getConfig());


    /**
     * 这里是通过获取Config中的数据
     * 且转换成存储在插件内的数据
     * 存储在Config中的格式大致如下
     * SkillData
     *   SkillName:
     *     ReadPath:
     *     CdPath:
     *
     * 在初始化的时候就执行
     * @param fc FILE<目标
     * @return
     */
    public static Map<String, Map<String,String>> imgPathIni(FileConfiguration fc){
        Map<String, Map<String,String>> imgPathMap = new HashMap<>();
        ConfigurationSection cs = fc.getConfigurationSection("SkillData");
        for(String skillName : cs.getKeys(false)){
            Map<String,String> map = new HashMap<>();
            map.put("ReadPath",fc.getString("SkillData."+skillName+".ReadPath"));
            map.put("CdPath",fc.getString("SkillData."+skillName+".CdPath"));
            imgPathMap.put(skillName,map);
        }
        return imgPathMap;
    }

    /**
     * 获取 ReadPath[等待释放的图像] 的图像路径
     * @param skillName
     * @return
     */
    public static String getReadSkillImgPath(String skillName){
        return skillImgPathMap.containsKey(skillName)?skillImgPathMap.get(skillName).get("ReadPath"):SkillImgData.getNullSkillImgPath();
    }


    /**
     * 获取 CdPath[正在CD中的图像] 的图像路径
     * @param skillName
     * @return
     */
    public static String getCdSkillImgPath(String skillName){
        return skillImgPathMap.get(skillName).get("CdPath");
    }

    public static String getNoSkillImgPath() {
        return skillImgPathMap.get("NotSkill").get("ReadPath");
    }
    public static String getNullSkillImgPath() {
        return skillImgPathMap.get("NullSkill").get("ReadPath");
    }

}
