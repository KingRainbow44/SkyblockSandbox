/*     */ package me.vagdedes.mysql.database;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import me.vagdedes.mysql.basic.Config;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ 
/*     */ public class MySQL
/*     */ {
/*     */   public static Connection getConnection() {
/*  14 */     return con;
/*     */   }
/*     */   private static Connection con;
/*     */   public static void setConnection(String host, String user, String password, String database, String port) {
/*  18 */     if (host == null || user == null || password == null || database == null) {
/*     */       return;
/*     */     }
/*  21 */     disconnect(false);
/*     */     try {
/*  23 */       con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=" + Config.getSSL(), user, password);
/*  25 */     } catch (Exception e) {
/*  26 */       Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL Connect Error: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void connect() {
/*  31 */     connect(true);
/*     */   }
/*     */   
/*     */   private static void connect(boolean message) {
/*  35 */     String host = Config.getHost();
/*  36 */     String user = Config.getUser();
/*  37 */     String password = Config.getPassword();
/*  38 */     String database = Config.getDatabase();
/*  39 */     String port = Config.getPort();
/*     */     
/*  41 */     if (isConnected()) {
/*  42 */       if (message) {
/*  43 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL Connect Error: Already connected");
/*     */       }
/*  45 */     } else if (host.equalsIgnoreCase("")) {
/*  46 */       Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Host is blank");
/*  47 */     } else if (user.equalsIgnoreCase("")) {
/*  48 */       Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: User is blank");
/*  49 */     } else if (password.equalsIgnoreCase("")) {
/*  50 */       Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Password is blank");
/*  51 */     } else if (database.equalsIgnoreCase("")) {
/*  52 */       Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Database is blank");
/*  53 */     } else if (port.equalsIgnoreCase("")) {
/*  54 */       Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Port is blank");
/*     */     } else {
/*  56 */       setConnection(host, user, password, database, port);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void disconnect() {
/*  61 */     disconnect(true);
/*     */   }
/*     */   
/*     */   private static void disconnect(boolean message) {
/*     */     try {
/*  66 */       if (isConnected()) {
/*  67 */         con.close();
/*     */         
/*  69 */         if (message) {
/*  70 */           Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SQL disconnected.");
/*     */         }
/*  72 */       } else if (message) {
/*  73 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL Disconnect Error: No existing connection");
/*     */       } 
/*  75 */     } catch (Exception e) {
/*  76 */       if (message) {
/*  77 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL Disconnect Error: " + e.getMessage());
/*     */       }
/*     */     } 
/*  80 */     con = null;
/*     */   }
/*     */   
/*     */   public static void reconnect() {
/*  84 */     disconnect();
/*  85 */     connect();
/*     */   }
/*     */   
/*     */   public static boolean isConnected() {
/*  89 */     if (con != null) {
/*     */       try {
/*  91 */         return !con.isClosed();
/*  92 */       } catch (Exception e) {
/*  93 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL Connection:");
/*  94 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: " + e.getMessage());
/*     */       } 
/*     */     }
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean update(String command) {
/* 101 */     if (command == null) {
/* 102 */       return false;
/*     */     }
/* 104 */     boolean result = false;
/*     */     
/* 106 */     connect(false);
/*     */     try {
/* 108 */       Statement st = getConnection().createStatement();
/* 109 */       st.executeUpdate(command);
/* 110 */       st.close();
/* 111 */       result = true;
/* 112 */     } catch (Exception e) {
/* 113 */       String message = e.getMessage();
/*     */       
/* 115 */       if (message != null) {
/* 116 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL Update:");
/* 117 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Command: " + command);
/* 118 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: " + message);
/*     */       } 
/*     */     } 
/* 121 */     disconnect(false);
/* 122 */     return result;
/*     */   }
/*     */   
/*     */   public static ResultSet query(String command) {
/* 126 */     if (command == null) {
/* 127 */       return null;
/*     */     }
/* 129 */     connect(false);
/* 130 */     ResultSet rs = null;
/*     */     try {
/* 132 */       Statement st = getConnection().createStatement();
/* 133 */       rs = st.executeQuery(command);
/* 134 */     } catch (Exception e) {
/* 135 */       String message = e.getMessage();
/*     */       
/* 137 */       if (message != null) {
/* 138 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SQL Query:");
/* 139 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Command: " + command);
/* 140 */         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: " + message);
/*     */       } 
/*     */     } 
/* 143 */     return rs;
/*     */   }
/*     */ }


/* Location:              C:\Users\kobed\Desktop\SkyblockRemake\dependencies\mysqlapi.jar!\me\vagdedes\mysql\database\MySQL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */