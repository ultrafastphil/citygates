/*    */ package citygates;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class GateData
/*    */   implements Serializable
/*    */ {
/* 22 */   public int[] p1 = new int[3];
/* 23 */   public int[] p2 = new int[3];
/*    */   public String name;
/*    */   public String World;
/* 26 */   public boolean open = false;
/* 27 */   public boolean timeGate = false;
/* 28 */   public boolean redstoneListener = false;
/* 29 */   public boolean mobKill = false;
/* 30 */   public String mob = "TestMob";
/* 31 */   public int[] pr = new int[3];
/* 32 */   public String killMsg = "You slayen a mob :p";
/* 33 */   public int[] killRegion1 = new int[3]; public int[] killRegion2 = new int[3];
/* 34 */   public boolean killRegion = false;
/* 35 */   public boolean buttonListener = false;
/* 36 */   public int[] button = new int[3];
/* 37 */   public long ButtonInterval = 0L;
/*    */ 
/*    */   public GateData(String name, int[] p1, int[] p2, String world)
/*    */   {
/*  8 */     this.p1 = p1;
/*  9 */     this.p2 = p2;
/* 10 */     this.name = name;
/* 11 */     this.World = world;
/* 12 */     this.mob = "";
/* 13 */     this.pr[0] = 0;
/* 14 */     this.pr[1] = 0;
/* 15 */     this.pr[2] = 0;
/*    */   }
/*    */ 
/*    */   public GateData()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.GateData
 * JD-Core Version:    0.6.2
 */