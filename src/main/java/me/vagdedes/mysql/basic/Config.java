/*     */ package me.vagdedes.mysql.basic;

import tk.skyblocksandbox.skyblocksandbox.SkyblockSandbox;

/*     */
/*     */ 
/*     */ public class Config
/*     */ {
/*     */   private static final String host = "host";
/*     */   private static final String user = "user";
/*     */   private static final String password = "password";
/*     */   private static final String database = "database";
/*     */   private static final String port = "port";
/*     */   private static final String ssl = "use_SSL";
/*     */   
/*     */   public static void clear() {
/*  15 */     set("host", "", false);
/*  16 */     set("user", "", false);
/*  17 */     set("password", "", false);
/*  18 */     set("database", "", false);
/*  19 */     set("port", "3306", false);
/*  20 */     set("use_SSL", Boolean.valueOf(true), false);
/*     */   }
/*     */   
/*     */   public static void create() {
/*  24 */     set("host", "", true);
/*  25 */     set("user", "", true);
/*  26 */     set("password", "", true);
/*  27 */     set("database", "", true);
/*  28 */     set("port", "3306", true);
/*  29 */     set("use_SSL", Boolean.valueOf(true), true);
/*     */   }
/*     */   
/*     */   public static void setHost(String s) {
/*  38 */     if (!getHost().equalsIgnoreCase(s)) {
/*  39 */       set("host", s, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setUser(String s) {
/*  44 */     if (!getUser().equalsIgnoreCase(s)) {
/*  45 */       set("user", s, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setPassword(String s) {
/*  50 */     if (!getPassword().equalsIgnoreCase(s)) {
/*  51 */       set("password", s, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setDatabase(String s) {
/*  56 */     if (!getDatabase().equalsIgnoreCase(s)) {
/*  57 */       set("database", s, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setPort(String s) {
/*  62 */     if (!getPort().equalsIgnoreCase(s)) {
/*  63 */       set("port", s, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setSSL(boolean b) {
/*  68 */     if (getSSL() != b) {
/*  69 */       set("use_SSL", Boolean.valueOf(b), false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getHost() {
/*  74 */     return get("databaseHost");
/*     */   }
/*     */   
/*     */   public static String getUser() {
/*  78 */     return get("databaseUser");
/*     */   }
/*     */   
/*     */   public static String getPassword() {
/*  82 */     return get("databasePassword");
/*     */   }
/*     */   
/*     */   public static String getDatabase() {
/*  86 */     return get("databaseName");
/*     */   }
/*     */   
/*     */   public static String getPort() {
/*  90 */     return get("databasePort");
/*     */   }
/*     */   
/*     */   public static boolean getSSL() {
/*  94 */     return getBoolean("initConnectionWithSSL");
/*     */   }
/*     */
/*     */   private static void set(String name, Object value, boolean checkIfExists) {
/*  98 */     if (name == null || value == null || (checkIfExists && SkyblockSandbox.getInstance().getConfig().contains(name))) {
/*     */       return;
/*     */     }
/* 101 */     SkyblockSandbox.getInstance().getConfig().set(name, value);
/* 102 */     SkyblockSandbox.getInstance().saveConfig();
/*     */   }
/*     */   
/*     */   private static String get(String name) {
/* 106 */     return (name == null || !SkyblockSandbox.getInstance().getConfig().contains(name)) ? "" : SkyblockSandbox.getInstance().getConfig().getString(name);
/*     */   }
/*     */   
/*     */   private static boolean getBoolean(String name) {
/* 110 */     return (name != null && SkyblockSandbox.getInstance().getConfig().contains(name) && SkyblockSandbox.getInstance().getConfig().getBoolean(name));
/*     */   }
/*     */ }


/* Location:              C:\Users\kobed\Desktop\SkyblockRemake\dependencies\mysqlapi.jar!\me\vagdedes\mysql\basic\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */