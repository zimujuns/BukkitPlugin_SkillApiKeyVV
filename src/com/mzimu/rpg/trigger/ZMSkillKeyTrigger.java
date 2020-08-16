package com.mzimu.rpg.trigger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface ZMSkillKeyTrigger {
    static void register(Plugin plugin, Listener listener){
        System.out.println("["+plugin.getName() + "]:注册触发器");
        plugin.getServer().getPluginManager().registerEvents(listener,plugin);
    }
}
