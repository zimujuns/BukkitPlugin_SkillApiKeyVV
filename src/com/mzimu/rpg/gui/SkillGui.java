package com.mzimu.rpg.gui;

import com.sucy.skill.SkillAPI;
import org.bukkit.entity.Player;

public class SkillGui {
    public static void open(Player p){
        SkillAPI.getPlayerData(p).showSkills();
    }
}
