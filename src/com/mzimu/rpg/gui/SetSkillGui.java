package com.mzimu.rpg.gui;

import com.mzimu.rpg.data.PlayKeyDate;
import com.mzimu.rpg.util.KeyUtil;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetSkillGui {
    public static final String TITLE = "技能设置界面";
    private Inventory inventory = Bukkit.createInventory(null,9,TITLE);

    /**
     * 进行对界面的初始化
     * @param playKeyDate 通过数据来显示GUI
     * @param playerData
     */
    public void builde(PlayKeyDate playKeyDate, PlayerData playerData){
        List<Integer> v = playKeyDate.getKeyList();
        List<String> s = playKeyDate.getSkillListFoString();
        int i=0;
        for(;i<v.size();i++){
            ItemStack item;
            if(s.get(i)==null || s.get(i).equals(PlayKeyDate.getNotSkill())){
                item = new ItemStack(Material.STAINED_GLASS_PANE);
                ItemMeta im = item.getItemMeta();
                im.setDisplayName("§6"+(i+1)+"号魂技位");
                List<String> lore = new ArrayList<>();
                lore.add("§7<点击该位置后选择技能即可绑定>");
                im.setLore(lore);
                item.setItemMeta(im);
                inventory.setItem(i,item);
            }else{
                item = getIron(playerData,s.get(i));
                if(item !=null || item.getType() != Material.AIR){
                    inventory.setItem(i,item);
                }

            }


        }
    }

    public void open(Player p) {
        builde(PlayKeyDate.getPlayKeyData(p),SkillAPI.getPlayerData(p));
        p.openInventory(inventory);
    }

    public ItemStack getIron(PlayerData pd, String skill){
        return pd.getSkill(skill).getData().getIcon(pd);
    }
}
