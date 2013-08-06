/*    */ package citygates;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class Downloader
/*    */   implements Runnable
/*    */ {
/*    */   private Plugin plugin;
/*    */ 
/*    */   public Downloader(Plugin plugin)
/*    */   {
/* 13 */     this.plugin = plugin;
/* 14 */     new Thread(this).start();
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 21 */       File f2 = new File("plugins/CityGates_Updater.jar");
/* 22 */       if (!f2.exists()) {
/* 23 */         InputStream in = getClass().getResourceAsStream("/res/CityGates_Updater.jar");
/* 24 */         f2.createNewFile();
/* 25 */         OutputStream out = new FileOutputStream(f2);
/*    */ 
/* 27 */         byte[] buf = new byte[1024];
/*    */         int len;
/* 29 */         while ((len = in.read(buf)) > 0) {
/* 30 */           out.write(buf, 0, len);
/*    */         }
/* 32 */         in.close();
/* 33 */         out.close();
/* 34 */         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
/*    */       }
/*    */     } catch (Exception e) {
/* 37 */       this.plugin.getLogger().warning(e.getLocalizedMessage());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.Downloader
 * JD-Core Version:    0.6.2
 */