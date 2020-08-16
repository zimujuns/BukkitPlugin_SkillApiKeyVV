package com.mzimu.rpg;

import com.mzimu.rpg.command.CommandMain;
import com.mzimu.rpg.data.HudMainData;
import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.sqlist.SqlistMain;
import com.mzimu.rpg.trigger.*;
import lk.vexview.hud.VexShow;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 2020.6.29 01点05分
 */
public class ZMSkillKeyMain extends JavaPlugin {

    //这里存放的是处于设置状态下的玩家


    public static double WIND_Height = 0.0;
    public static HudMainData hudMainData;

    public static ConcurrentMap<String, VexShow[]> concurrentMap = new ConcurrentHashMap<>();


    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        WIND_Height = ZMSkillKeyMain.getPlugin().getConfig().getDouble("Winds.Height");
        hudMainData = new HudMainData();
        this.saveDefaultConfig();
        this.getCommand("zmskill").setExecutor(new CommandMain());

        super.onEnable();

        ZMSkillKeyTrigger.register(this,new OnLogin_QuickTrigger());
        ZMSkillKeyTrigger.register(this,new OnVexViewTrigger());
        ZMSkillKeyTrigger.register(this,new OnInventoryTrigger());
        ZMSkillKeyTrigger.register(this,new OnPlayerClientWindowSizeTrigger());
        ZMSkillKeyTrigger.register(this,new OnSkillApiTrigger());

        try {
            SqlistMain.init();
            System.out.println("数据库完成初始化");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("初始化异常,请注意!");
        }
        cmdSend();


    }

    @Override
    public void onDisable() {
        Iterator<? extends Player> iterator = this.getServer().getOnlinePlayers().iterator();
        while (iterator.hasNext()){
            Player p = iterator.next();
            PlayKeyDate.delPlayKeyData(p);
            p.kickPlayer("服务器重启,已将您踢出,请稍后再登入");;
        }
    }


    public static boolean isPath(Player p){
        return plugin.getConfig().contains("PlayerData."+p.getName());
    }

    public static Plugin getPlugin(){
        return plugin;
    }


    public void cmdSend(){
        System.out.println("=============================================");
        System.out.println("                ////////    ////      ////");
        System.out.println("                     //     // //    // //");
        System.out.println("                    //      //  //  //  //");
        System.out.println("                   //       //   ////   //");
        System.out.println("                  //        //    //    //");
        System.out.println("                 //         //          //");
        System.out.println("                ////////    //          //");
        System.out.println("      基于VexView与SkillApi  ZMSkillKeyForVV");
        System.out.println("            作者QQ:2727295521  请勿倒卖 斗罗封神 使用");
        System.out.println("");
        System.out.println("=============================================");
    }

}
