/*     */ package citygates;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class UpdateChecker
/*     */ {
/*     */   private Plugin plugin;
/*     */   private URL filesFeed;
/*     */   private String version;
/*     */   private String link;
/*     */   private String jarLink;
/*  26 */   private boolean updateAvalible = false;
/*     */ 
/*     */   public UpdateChecker(Plugin p, String url, final General general) {
/*  29 */     this.plugin = p;
/*     */     try
/*     */     {
/*  32 */       this.filesFeed = new URL(url);
/*     */     } catch (MalformedURLException e) {
/*  34 */       e.printStackTrace();
/*     */     }
/*  36 */     new BukkitRunnable() {
/*     */       public void run() {
/*     */         while (true) {
/*  39 */           if (general.updateMsg) {
/*  40 */             UpdateChecker.this.plugin.getLogger().info("Checking for updates...");
/*     */           }
/*  42 */           if (UpdateChecker.this.updateNeeded()) {
/*  43 */             UpdateChecker.this.updateAvalible = true;
/*  44 */             UpdateChecker.this.ShowUpdateMsg();
/*  45 */           } else if (general.updateMsg) {
/*  46 */             UpdateChecker.this.plugin.getLogger().info("No updates found!");
/*     */           }
/*     */           try {
/*  49 */             Thread.sleep(general.updateTime); } catch (Exception localException) {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  54 */     .runTask((Plugin)this);
/*     */   }
/*     */ 
/*     */   public boolean updateNeeded()
/*     */   {
/*     */     try
/*     */     {
/*  63 */       InputStream input = this.filesFeed.openConnection().getInputStream();
/*  64 */       Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
/*     */ 
/*  66 */       Node latestFile = document.getElementsByTagName("item").item(0);
/*  67 */       NodeList children = latestFile.getChildNodes();
/*     */ 
/*  69 */       this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
/*  70 */       this.link = children.item(3).getTextContent();
/*     */ 
/*  72 */       input.close();
/*     */ 
/*  74 */       input = new URL(this.link).openConnection().getInputStream();
/*     */ 
/*  76 */       BufferedReader reader = new BufferedReader(new InputStreamReader(input));
/*     */       String line;
/*  78 */       while ((line = reader.readLine()) != null)
/*     */       {
/*     */         String line;
/*  79 */         if (line.trim().startsWith("<li class=\"user-action user-action-download\">")) {
/*  80 */           this.jarLink = line.substring(line.indexOf("href=\"") + 6, line.lastIndexOf("\""));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  85 */       reader.close();
/*  86 */       input.close();
/*  87 */       if (!this.plugin.getDescription().getVersion().equals(this.version))
/*  88 */         return true;
/*     */     }
/*     */     catch (Exception e) {
/*  91 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/*  98 */     return this.version;
/*     */   }
/*     */ 
/*     */   public String getLink() {
/* 102 */     return this.link;
/*     */   }
/*     */ 
/*     */   public String getJarLink() {
/* 106 */     return this.jarLink;
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
/* 110 */     String cmd = cmnd.getName().toLowerCase();
/* 111 */     if (cmd.equalsIgnoreCase("gupdate")) {
/* 112 */       if ((cs instanceof Player)) {
/* 113 */         cs.sendMessage("This command can only be execute by console!");
/* 114 */         return true;
/*     */       }
/* 116 */       if (!this.updateAvalible)
/* 117 */         cs.sendMessage("No update found!");
/*     */       else {
/*     */         try {
/* 120 */           Runtime.getRuntime().exec("cmd /c start " + getLink());
/*     */         } catch (Exception e) {
/* 122 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 125 */       return true;
/* 126 */     }if (cmd.equalsIgnoreCase("gcheck")) {
/* 127 */       if ((cs instanceof Player)) {
/* 128 */         cs.sendMessage("This command can only be execute by console!");
/* 129 */         return true;
/*     */       }
/* 131 */       this.plugin.getLogger().info("Checking for updates...");
/* 132 */       if (updateNeeded())
/* 133 */         ShowUpdateMsg();
/*     */       else {
/* 135 */         this.plugin.getLogger().info("No updates found!");
/*     */       }
/* 137 */       return true;
/*     */     }
/* 139 */     if ((cs instanceof Player)) {
/* 140 */       cs.sendMessage("This command can only be execute by players!");
/* 141 */       return true;
/*     */     }
/*     */ 
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   private void ShowUpdateMsg() {
/* 148 */     this.plugin.getLogger().info("#############################");
/* 149 */     this.plugin.getLogger().info("# !!NEW UPDATE IS RELASED!! #");
/* 150 */     this.plugin.getLogger().info("# #");
/* 151 */     this.plugin.getLogger().info("# Type /gupdate to go to #");
/* 152 */     this.plugin.getLogger().info("# the download pages of #");
/* 153 */     this.plugin.getLogger().info("# DevBukkit #");
/* 154 */     this.plugin.getLogger().info("#############################");
/* 155 */     this.plugin.getLogger().info("DownloadLink: " + getLink());
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.UpdateChecker
 * JD-Core Version:    0.6.2
 */