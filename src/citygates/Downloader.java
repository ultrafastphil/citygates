/*     */ package citygates;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class Downloader
/*     */   implements Runnable
/*     */ {
/*     */   private URL url;
/*  41 */   private String downloadLink = null;
/*     */   private int downloaded;
/*     */   private int status;
/*     */   private int size;
/*     */   private double version;
/*     */   private Plugin plugin;
/*     */   public static final int DOWNLOADING = 0;
/*     */   public static final int PAUSED = 1;
/*     */   public static final int COMPLETE = 2;
/*     */   public static final int CANCELLED = 3;
/*     */   public static final int ERROR = 4;
/*     */   private static final int MAX_BUFFER_SIZE = 1024;
/*     */ 
/*     */   public Downloader(Plugin plugin)
/*     */   {
/*  16 */     this.plugin = plugin;
/*  17 */     Downloader();
/*     */   }
/*     */ 
/*     */   public boolean Downloader()
/*     */   {
/*  22 */     this.version = Double.parseDouble(this.plugin.getDescription().getVersion());
/*     */     try {
/*  24 */       this.url = new URL("https://dl.dropbox.com/sh/6arkt2yn7tghnnq/m2rfNHEnrU/CityGatesVersion.yml?dl=1"); } catch (Exception e) {
/*     */     }
/*  26 */     new Thread(new Runnable() {
/*     */       public void run() {
/*     */         while (true) {
/*  29 */           Downloader.this.plugin.getLogger().info("Checking for updates...");
/*  30 */           Downloader.this.Download();
/*     */           try {
/*  32 */             Thread.sleep(1800000L);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */     }).start();
/*     */ 
/*  37 */     return true;
/*     */   }
/*     */ 
/*     */   public void Download()
/*     */   {
/*  57 */     this.downloaded = 0;
/*  58 */     this.size = -1;
/*  59 */     this.status = 0;
/*  60 */     new Thread(this).start();
/*     */   }
/*     */ 
/*     */   public void run() {
/*  64 */     RandomAccessFile file = null;
/*  65 */     InputStream stream = null;
/*  66 */     String fileName = null;
/*     */     try {
/*  68 */       HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
/*  69 */       connection.setRequestProperty("Range", "bytes=" + this.downloaded + "-");
/*  70 */       if (connection.getResponseCode() / 100 != 2) {
/*  71 */         error(0);
/*     */       }
/*     */ 
/*  74 */       int contentLength = connection.getContentLength();
/*  75 */       if (contentLength < 1) {
/*  76 */         error(1);
/*     */       }
/*     */ 
/*  79 */       if (this.size == -1) {
/*  80 */         this.size = contentLength;
/*     */       }
/*     */ 
/*  83 */       fileName = this.url.getFile();
/*  84 */       new File("plugins/Citygates/CityGatesVersion.yml").createNewFile();
/*  85 */       file = new RandomAccessFile("plugins/Citygates/CityGatesVersion.yml", "rw");
/*  86 */       file.seek(this.downloaded);
/*     */ 
/*  88 */       stream = connection.getInputStream();
/*  89 */       while (this.status == 0)
/*     */       {
/*     */         byte[] buffer;
/*     */         byte[] buffer;
/*  91 */         if (this.size - this.downloaded > 1024)
/*  92 */           buffer = new byte[1024];
/*     */         else {
/*  94 */           buffer = new byte[this.size - this.downloaded];
/*     */         }
/*     */ 
/*  97 */         int read = stream.read(buffer);
/*  98 */         if (read == -1)
/*     */         {
/*     */           break;
/*     */         }
/* 102 */         file.write(buffer, 0, read);
/* 103 */         this.downloaded += read;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       File f;
/* 106 */       error(2);
/* 107 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       File f;
/* 109 */       if (file != null) {
/* 110 */         File f = new File("plugins/Citygates/CityGatesVersion.yml");
/* 111 */         CheckVersion(f);
/*     */         try {
/* 113 */           file.close();
/* 114 */           f.delete(); } catch (Exception e) {
/*     */         }
/*     */       }
/* 117 */       if (stream != null)
/*     */         try {
/* 119 */           stream.close();
/*     */         } catch (Exception e) {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void error(int a) {
/* 126 */     this.plugin.getLogger().warning("Error: " + a);
/*     */   }
/*     */ 
/*     */   public void CheckVersion(File f) {
/*     */     try {
/* 131 */       BufferedReader bf = new BufferedReader(new FileReader(f));
/*     */       String line;
/* 133 */       while ((line = bf.readLine()) != null) {
/* 134 */         if (!line.startsWith("#")) {
/*     */           try {
/* 136 */             double v = Double.parseDouble(line.split(" ")[0]);
/* 137 */             this.plugin.getLogger().info("current verion: " + this.version + ", recommendend version: " + v);
/* 138 */             if (v > this.version) {
/* 139 */               this.downloadLink = line.split(" ")[1];
/* 140 */               new Thread(new Runnable() {
/*     */                 public void run() {
/* 142 */                   int result = JOptionPane.showConfirmDialog(null, "A new Version of CityGates is released!\n\nDo you want to download it?", "Update", 0);
/* 143 */                   if (result == 0)
/*     */                     try {
/* 145 */                       Runtime.getRuntime().exec("cmd.exe /c start " + Downloader.this.downloadLink);
/*     */                     } catch (Exception e) {
/* 147 */                       Downloader.this.error(3);
/* 148 */                       e.printStackTrace();
/*     */                     }
/*     */                 }
/*     */               }).start();
/*     */ 
/* 153 */               this.plugin.getLogger().info("#############################");
/* 154 */               this.plugin.getLogger().info("# !!NEW UPDATE IS RELASED!! #");
/* 155 */               this.plugin.getLogger().info("#                           #");
/* 156 */               this.plugin.getLogger().info("# Type /gupdate to go to    #");
/* 157 */               this.plugin.getLogger().info("# the download pages of     #");
/* 158 */               this.plugin.getLogger().info("# DevBukkit                 #");
/* 159 */               this.plugin.getLogger().info("#############################");
/* 160 */               this.plugin.getLogger().info("DownloadLink: " + this.downloadLink);
/*     */             } else {
/* 162 */               this.plugin.getLogger().info("No updates found!");
/*     */             }
/*     */           } catch (Exception e) {
/* 165 */             error(4);
/* 166 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/* 170 */       bf.close();
/* 171 */       f.delete();
/*     */     } catch (Exception e) {
/* 173 */       error(5);
/* 174 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean Copy() {
/*     */     try {
/* 180 */       File f1 = new File("plugins/CityGates/Download/CityGates.jar");
/* 181 */       File f2 = new File("plugins/CityGates.jar");
/* 182 */       InputStream in = new FileInputStream(f1);
/*     */ 
/* 184 */       OutputStream out = new FileOutputStream(f2);
/*     */ 
/* 186 */       byte[] buf = new byte[1024];
/*     */       int len;
/* 188 */       while ((len = in.read(buf)) > 0) {
/* 189 */         out.write(buf, 0, len);
/*     */       }
/* 191 */       in.close();
/* 192 */       out.close();
/* 193 */       return true; } catch (Exception ex) {
/*     */     }
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
/*     */   {
/* 200 */     String cmd = cmnd.getName().toLowerCase();
/* 201 */     if (cmd.equalsIgnoreCase("gupdate")) {
/* 202 */       if ((cs instanceof Player)) {
/* 203 */         cs.sendMessage("This command can only be execute by console!");
/* 204 */         return true;
/*     */       }
/* 206 */       if (this.downloadLink == null)
/* 207 */         cs.sendMessage("No update found!");
/*     */       else {
/*     */         try {
/* 210 */           Runtime.getRuntime().exec("cmd /c start " + this.downloadLink);
/*     */         } catch (Exception e) {
/* 212 */           error(6);
/* 213 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 216 */       return true;
/* 217 */     }if (cmd.equalsIgnoreCase("gcheck")) {
/* 218 */       if ((cs instanceof Player)) {
/* 219 */         cs.sendMessage("This command can only be execute by console!");
/* 220 */         return true;
/*     */       }
/* 222 */       this.plugin.getLogger().info("Checking for updates...");
/* 223 */       Download();
/* 224 */       return true;
/*     */     }
/* 226 */     if ((cs instanceof Player)) {
/* 227 */       cs.sendMessage("This command can only be execute by players!");
/* 228 */       return true;
/*     */     }
/*     */ 
/* 231 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.Downloader
 * JD-Core Version:    0.6.2
 */