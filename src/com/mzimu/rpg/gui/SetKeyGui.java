package com.mzimu.rpg.gui;

import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.util.KeyUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetKeyGui {
    public static final String TITLE = "按键设置界面";
    private Inventory inventory = Bukkit.createInventory(null,9,TITLE);


    /**
     * 进行对界面的初始化
     * @param playKeyDate 通过数据来显示GUI
     */
    public void builde(PlayKeyDate playKeyDate){
        List<Integer> v = playKeyDate.getKeyList();
        List<String> s = playKeyDate.getSkillListFoString();
        int i=0;
        for(;i<v.size();i++){
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(KeyUtil.getKeyName(v.get(i)));
            List<String> lore = new ArrayList<>();
            lore.add(s.get(i));
            lore.add("点击进行设置按钮");
            im.setLore(lore);
            item.setItemMeta(im);
            inventory.setItem(i,item);
        }
        for(;i<9;i++){
            inventory.setItem(i,new ItemStack(Material.BARRIER));
        }
    }


    /**
     * 打开界面的主要事件
     */
    public void open(Player p){
        builde(PlayKeyDate.getPlayKeyData(p));
        p.openInventory(inventory);
    }

}
