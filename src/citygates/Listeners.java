/*    */ package citygates;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public abstract class Listeners
/*    */ {
/*    */   private int oldTime;
/*    */ 
/*    */   public Listeners(Plugin p)
/*    */   {
/*  8 */     p.getServer().getScheduler().scheduleAsyncRepeatingTask(p, new Time((World)p.getServer().getWorlds().get(0))
/*    */     {
/*    */       public void setTime(int time)
/*    */       {
/* 12 */         if (Listeners.this.oldTime != time) {
/* 13 */           Listeners.this.onTimeChanged(time);
/* 14 */           Listeners.this.oldTime = time;
/*    */         }
/*    */       }
/*    */     }
/*    */     , 60L, 20L);
/*    */   }
/*    */ 
/*    */   public abstract void onTimeChanged(int paramInt);
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.Listeners
 * JD-Core Version:    0.6.2
 */