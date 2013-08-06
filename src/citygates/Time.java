/*    */ package citygates;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ 
/*    */ public abstract class Time
/*    */   implements Runnable
/*    */ {
/*    */   public World world;
/* 24 */   public static int ZONSOPGANG = 0;
/* 25 */   public static int OCHTEND = 1;
/* 26 */   public static int MIDDAG = 2;
/* 27 */   public static int AVOND = 3;
/* 28 */   public static int ZONSONDERGANG = 4;
/* 29 */   public static int NACHT = 5;
/*    */ 
/*    */   public Time(World world)
/*    */   {
/*  8 */     this.world = world;
/*    */   }
/*    */ 
/*    */   public void run() {
/* 12 */     long time = this.world.getTime();
/* 13 */     if ((time >= 0L) && (time <= 14000L))
/* 14 */       setTime(MIDDAG);
/*    */     else
/* 16 */       setTime(NACHT);
/*    */   }
/*    */ 
/*    */   public abstract void setTime(int paramInt);
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.Time
 * JD-Core Version:    0.6.2
 */