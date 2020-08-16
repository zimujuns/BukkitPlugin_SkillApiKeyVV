package com.mzimu.rpg.command;

import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.event.SettingsEvent;
import com.mzimu.rpg.gui.SetKeyGui;
import com.mzimu.rpg.gui.SetSkillGui;
import com.mzimu.rpg.util.SkillApiUtil;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMain implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player && commandSender.hasPermission("zmskill.use")){
            Player p = (Player) commandSender;
            switch(strings[0]){
//                case "setKey":
//                    if(strings.length>1){
//                        new SetKeyGui().open(Bukkit.getPlayer(strings[1]));
//                        break;
//                    }
//                    //这里要判断是否有数据 若有就进行覆盖 操作都在Gui类中进行
//                    new SetKeyGui().open(p);
//                    break;
                case "setSkill":
                    //这里要判断是否至少有一个按键是已经被设定好了的 操作都在Gui类中进行
                    if(SkillApiUtil.isClassToNull(SkillAPI.getPlayerData(p))){
                        p.sendMessage("§a[技能助手]§7您没有加入职业 无法设置技能");
                        return false;
                    }
//                    else if(!PlayKeyDate.getPlayKeyData(p).isEmpty()){
//                        p.sendMessage("您没有设置一个按键,无法设置技能");
//                    }
                    else{
                        new SetSkillGui().open(p);
                        return true;
                    }
            }
        }

        return false;
    }



}
