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
/*    */   public Listeners(Plugin p, General g)
/*    */   {
/* 15 */     p.getServer().getScheduler().runTaskTimerAsynchronously(p, new Time((World)p.getServer().getWorlds().get(0), g.day, g.night)
/*    */     {
/*    */       public void setTime(int time)
/*    */       {
/* 19 */         if (Listeners.this.oldTime != time) {
/* 20 */           Listeners.this.onTimeChanged(time);
/* 21 */           Listeners.this.oldTime = time;
/*    */         }
/*    */       }
/*    */     }
/*    */     , 60L, 20L);
/*    */   }
/*    */ 
/*    */   public abstract void onTimeChanged(int paramInt);
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.Listeners
 * JD-Core Version:    0.6.2
 */