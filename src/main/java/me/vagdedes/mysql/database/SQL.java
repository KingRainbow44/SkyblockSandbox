/*     */ package me.vagdedes.mysql.database;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class SQL
/*     */ {
/*     */   public static boolean tableExists(String table) {
/*     */     try {
/*  12 */       Connection connection = MySQL.getConnection();
/*     */       
/*  14 */       if (connection == null) {
/*  15 */         return false;
/*     */       }
/*  17 */       DatabaseMetaData metadata = connection.getMetaData();
/*     */       
/*  19 */       if (metadata == null) {
/*  20 */         return false;
/*     */       }
/*  22 */       ResultSet rs = metadata.getTables(null, null, table, null);
/*     */       
/*  24 */       if (rs.next()) {
/*  25 */         return true;
/*     */       }
/*  27 */     } catch (Exception exception) {}
/*     */     
/*  29 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean insertData(String columns, String values, String table) {
/*  33 */     return MySQL.update("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");");
/*     */   }
/*     */   
/*     */   public static boolean deleteData(String column, String logic_gate, String data, String table) {
/*  37 */     if (data != null) {
/*  38 */       data = "'" + data + "'";
/*     */     }
/*  40 */     return MySQL.update("DELETE FROM " + table + " WHERE " + column + logic_gate + data + ";");
/*     */   }
/*     */   
/*     */   public static boolean exists(String column, String data, String table) {
/*  44 */     if (data != null) {
/*  45 */       data = "'" + data + "'";
/*     */     }
/*     */     try {
/*  48 */       ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";");
/*     */       
/*  50 */       if (rs.next()) {
/*  51 */         return true;
/*     */       }
/*  53 */     } catch (Exception exception) {}
/*     */     
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean deleteTable(String table) {
/*  59 */     return MySQL.update("DROP TABLE " + table + ";");
/*     */   }
/*     */   
/*     */   public static boolean truncateTable(String table) {
/*  63 */     return MySQL.update("TRUNCATE TABLE " + table + ";");
/*     */   }
/*     */   
/*     */   public static boolean createTable(String table, String columns) {
/*  67 */     return MySQL.update("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ");");
/*     */   }
/*     */   
/*     */   public static boolean upsert(String selected, Object object, String column, String data, String table) {
/*  71 */     if (object != null) {
/*  72 */       object = "'" + object + "'";
/*     */     }
/*  74 */     if (data != null) {
/*  75 */       data = "'" + data + "'";
/*     */     }
/*     */     try {
/*  78 */       ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";");
/*     */       
/*  80 */       if (rs.next()) {
/*  81 */         MySQL.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + column + "=" + data + ";");
/*     */       } else {
/*  83 */         insertData(column + ", " + selected, "'" + data + "', '" + object + "'", table);
/*     */       } 
/*  85 */     } catch (Exception exception) {}
/*     */     
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean set(String selected, Object object, String column, String logic_gate, String data, String table) {
/*  91 */     if (object != null) {
/*  92 */       object = "'" + object + "'";
/*     */     }
/*  94 */     if (data != null) {
/*  95 */       data = "'" + data + "'";
/*     */     }
/*  97 */     return MySQL.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + column + logic_gate + data + ";");
/*     */   }
/*     */   
/*     */   public static boolean set(String selected, Object object, String[] where_arguments, String table) {
/* 101 */     String arguments = "";
/*     */     
/* 103 */     for (String argument : where_arguments) {
/* 104 */       arguments = arguments + argument + " AND ";
/*     */     }
/* 106 */     if (arguments.length() <= 5) {
/* 107 */       return false;
/*     */     }
/* 109 */     arguments = arguments.substring(0, arguments.length() - 5);
/*     */     
/* 111 */     if (object != null) {
/* 112 */       object = "'" + object + "'";
/*     */     }
/* 114 */     return MySQL.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + arguments + ";");
/*     */   }
/*     */   
/*     */   public static Object get(String selected, String[] where_arguments, String table) {
/* 118 */     String arguments = "";
/*     */     
/* 120 */     for (String argument : where_arguments) {
/* 121 */       arguments = arguments + argument + " AND ";
/*     */     }
/* 123 */     if (arguments.length() <= 5) {
/* 124 */       return Boolean.valueOf(false);
/*     */     }
/* 126 */     arguments = arguments.substring(0, arguments.length() - 5);
/*     */     
/*     */     try {
/* 129 */       ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + arguments + ";");
/*     */       
/* 131 */       if (rs.next()) {
/* 132 */         return rs.getObject(selected);
/*     */       }
/* 134 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 137 */     return null;
/*     */   }
/*     */   
/*     */   public static ArrayList<Object> listGet(String selected, String[] where_arguments, String table) {
/* 141 */     ArrayList<Object> array = new ArrayList();
/* 142 */     String arguments = "";
/*     */     
/* 144 */     for (String argument : where_arguments) {
/* 145 */       arguments = arguments + argument + " AND ";
/*     */     }
/* 147 */     if (arguments.length() <= 5) {
/* 148 */       return array;
/*     */     }
/* 150 */     arguments = arguments.substring(0, arguments.length() - 5);
/*     */     
/*     */     try {
/* 153 */       ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + arguments + ";");
/*     */       
/* 155 */       while (rs.next()) {
/* 156 */         array.add(rs.getObject(selected));
/*     */       }
/* 158 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 161 */     return array;
/*     */   }
/*     */   
/*     */   public static Object get(String selected, String column, String logic_gate, String data, String table) {
/* 165 */     if (data != null) {
/* 166 */       data = "'" + data + "'";
/*     */     }
/*     */     try {
/* 169 */       ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + logic_gate + data + ";");
/*     */       
/* 171 */       if (rs.next()) {
/* 172 */         return rs.getObject(selected);
/*     */       }
/* 174 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 177 */     return null;
/*     */   }
/*     */   
/*     */   public static ArrayList<Object> listGet(String selected, String column, String logic_gate, String data, String table) {
/* 181 */     ArrayList<Object> array = new ArrayList();
/*     */     
/* 183 */     if (data != null) {
/* 184 */       data = "'" + data + "'";
/*     */     }
/*     */     try {
/* 187 */       ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + logic_gate + data + ";");
/*     */       
/* 189 */       while (rs.next()) {
/* 190 */         array.add(rs.getObject(selected));
/*     */       }
/* 192 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 195 */     return array;
/*     */   }
/*     */   
/*     */   public static int countRows(String table) {
/* 199 */     int i = 0;
/*     */     
/* 201 */     if (table == null) {
/* 202 */       return i;
/*     */     }
/* 204 */     ResultSet rs = MySQL.query("SELECT * FROM " + table + ";");
/*     */     
/*     */     try {
/* 207 */       while (rs.next()) {
/* 208 */         i++;
/*     */       }
/* 210 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 213 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\kobed\Desktop\SkyblockRemake\dependencies\mysqlapi.jar!\me\vagdedes\mysql\database\SQL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */