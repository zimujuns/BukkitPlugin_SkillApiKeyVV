package com.mzimu.rpg.sqlist;

import com.mzimu.rpg.ZMSkillKeyMain;
import com.mzimu.rpg.data.PlayKeyDate;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlistMain {
    private static Connection c;
    private static String TableName = "PlayData";
    private static int i=0;

    /**
     * 保持连接 断开 Statement
     * @throws SQLException
     */
    public static void init() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:plugins/" + ZMSkillKeyMain.getPlugin().getName() + "/PlayData.db");
            SqlistMain.c = c;
            Statement s = c.createStatement();
            s.executeQuery("Select * from " + TableName);
            s.close();
        } catch (ClassNotFoundException | SQLException e) {
            Statement s = c.createStatement();
            s.executeUpdate("Create Table " + TableName + "(" +
                    "uuid INTEGER not null primary key," +
                    "Skill_0 varchar(20)," +
                    "Skill_1 varchar(20)," +
                    "Skill_2 varchar(20)," +
                    "Skill_3 varchar(20)," +
                    "Skill_4 varchar(20)," +
                    "Skill_5 varchar(20)," +
                    "Skill_6 varchar(20)," +
                    "Skill_7 varchar(20)," +
                    "Skill_8 varchar(20))");
            s.close();
        }

    }

    /**
     * 获取第i个技能 在Sql中的名字
     * @param i 从0开始
     * @return
     */
    public static String getTableToSkill(int i){
        return "Skill_"+i;
    }

    public static List<String> getPlaySkillNameList(Player p) throws SQLException {
        try {
            Statement s = c.createStatement();
            List<String> skillList = new ArrayList<>();
            for(int i=0;i<9;i++){
                String a = s.executeQuery("Select * from " + TableName + " where uuid ==" +getUUID(p)).getString(getTableToSkill(i));
                if(a==null || a== ""){
                    a = PlayKeyDate.getNotSkill();
                }
                skillList.add(a);
            }
            i=0;
            s.close();
            return skillList;
        } catch (SQLException throwables) {
            if(i==0){
                initPlaySkillToSqlite(p);
                return getPlaySkillNameList(p);
            }else{
                throwables.printStackTrace();
                return null;
            }
        }
    }

    public static void initPlaySkillToSqlite(Player p) throws SQLException {
        Statement s = c.createStatement();
        s.executeUpdate("insert into "+TableName+"(uuid)"+" values("+getUUID(p)+")");
        s.close();
        i++;
    }

    public static void savePlayDataToSqlit(Player p,List<String> skill) throws SQLException {
        Statement s = c.createStatement();
//        String values = "";
//        String tab = "";
        for(int i=0;i<skill.size();i++){
//            if(i!=0){
//                tab+=",";
//                values+=",";
//            }
//            if(!skill.get(i).equals(PlayKeyDate.getNotSkill())){
//                tab += getTableToSkill(i);
//                values += "\"" + skill.get(i)+ "\"";
//            }
            s.executeUpdate("Update " + TableName + " set " + getTableToSkill(i)+"= \""+skill.get(i)+"\" where uuid == " + getUUID(p));
        }
//        s.executeUpdate("Update "+TableName+"Set " +tab+") values(" + getUUID(p)+","+values+")");
        s.close();
    }


    public static int getUUID(Player p){
        int uuid = p.getUniqueId().hashCode();
        if(uuid>0){
            return uuid;
        }else{
            return -uuid;
        }
    }






}
