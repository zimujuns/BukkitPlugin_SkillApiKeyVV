package com.mzimu.rpg.event.tirgger;

import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.event.SettingsEvent;
import com.mzimu.rpg.gui.SetKeyGui;
import com.mzimu.rpg.gui.SetSkillGui;
import com.mzimu.rpg.gui.SkillGui;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.Settings;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class ClickEvent {

    public static void OnClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null || e.getCurrentItem()==null){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        String title = e.getClickedInventory().getTitle();
        switch (title){
//            case SetKeyGui.TITLE:
//                //进入设置模式的事件
//                e.setCancelled(true);
//                SettingsEvent.EnterSetSlot(p,e.getSlot());
//                p.sendMessage("设置的你按键吧,请在键盘中选择点击的按键,进行点击");
//                p.closeInventory();
//                break;
            case SetSkillGui.TITLE:
                e.setCancelled(true);
//                if(PlayKeyDate.getPlayKeyData(p).getKeyList().get(e.getSlot())==-1){
//                    p.sendMessage("您当前点击的这个位置没有设置按键,无法设置技能");
//                    return;
//                }
                //进入设置模式的事件
                if(e.isLeftClick()) {
                    SettingsEvent.EnterSetSlot(p, e.getSlot());
                    SkillGui.open(p);
                    p.sendMessage("§a[技能助手]§7设置的按键技能吧，请选择你的技能栏中的技能,进行点击");
                }else{
                    PlayKeyDate.delPlayKeyData(p,e.getSlot());
                    new SetSkillGui().open(p);
                    p.sendMessage("§a[技能助手]§7已移除在这个位置的技能!");
                }
                break;
            default:
                String className = SkillAPI.getPlayerData(p).getShownClassName();
                if(SettingsEvent.isEnterSet(p) &&
                        title.matches(className+"\\S*")){
                        e.setCancelled(true);
                        PlayerData pd = SkillAPI.getPlayerData(p);
                        Iterator<PlayerSkill> iterator = pd.getSkills().iterator();
                        String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
                        //根据玩家按键技能来循环
                        for(PlayerSkill psk :PlayKeyDate.getPlayKeyData(p).getSkillList()){
                            try {
                                String var = psk.getData().getIcon(pd).getItemMeta().getDisplayName();
                                if(var.equals(itemName)){
                                    p.sendMessage("§a[技能助手]§7你已经设置了该技能的按键,请勿再设置第二个");
                                    return;
                                }
                            }catch (NullPointerException n){
                                continue;
                            }
                        }
                        //根据玩家的技能列表来循环
                        while (iterator.hasNext()){
                            PlayerSkill skill = iterator.next();
                            if(skill.getData().getIndicator().getType()==Material.AIR){
                                continue;
                            }
                            try {
                                //这里判断点击的Item与之对应的在Skill中谁的Iron
                                String sN = skill.getData().getIcon(pd).getItemMeta().getDisplayName();
                                if (itemName.equals(sN)) {
                                    if(skill.getLevel()>0) {
                                        SettingsEvent.main(p, -1, skill);
                                        p.closeInventory();
                                        new SetSkillGui().open(p);
                                    }else{
                                        p.sendMessage("§a[技能助手]§7你没有学习对应的技能!");
                                    }
                                    break;
                                }
                            }catch (NullPointerException nP){
                                return;
                            }
                        }
                    }


        }

    }

}
