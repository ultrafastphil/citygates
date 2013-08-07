/*    */ package citygates;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ 
/*    */ public abstract class Time
/*    */   implements Runnable
/*    */ {
/*    */   public World world;
/*    */   private long day;
/*    */   private long night;
/* 11 */   public static int ZONSOPGANG = 0;
/* 12 */   public static int OCHTEND = 1;
/* 13 */   public static int MIDDAG = 2;
/* 14 */   public static int AVOND = 3;
/* 15 */   public static int ZONSONDERGANG = 4;
/* 16 */   public static int NACHT = 5;
/*    */ 
/*    */   public Time(World world, long day, long night)
/*    */   {
/* 20 */     this.world = world;
/* 21 */     this.day = day;
/* 22 */     this.night = night;
/*    */   }
/*    */ 
/*    */   public void run() {
/* 26 */     long time = this.world.getTime();
/* 27 */     if (this.day > this.night) {
/* 28 */       if (time < this.night)
/* 29 */         setTime(MIDDAG);
/* 30 */       else if ((time > this.night) && (time < this.day))
/* 31 */         setTime(NACHT);
/* 32 */       else if (time > this.day)
/* 33 */         setTime(MIDDAG);
/*    */     }
/* 35 */     else if (this.day < this.night)
/* 36 */       if (time < this.day)
/* 37 */         setTime(NACHT);
/* 38 */       else if ((time > this.day) && (time < this.night))
/* 39 */         setTime(MIDDAG);
/* 40 */       else if (time > this.night)
/* 41 */         setTime(NACHT);
/*    */   }
/*    */ 
/*    */   public abstract void setTime(int paramInt);
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.Time
 * JD-Core Version:    0.6.2
 */