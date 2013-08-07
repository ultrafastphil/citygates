/*    */ package citygates;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class GateData
/*    */   implements Serializable
/*    */ {
/*  9 */   public int[] p1 = new int[3];
/* 10 */   public int[] p2 = new int[3];
/*    */   public String name;
/*    */   public String World;
/* 13 */   public boolean open = false;
/* 14 */   public boolean timeGate = false;
/* 15 */   public boolean redstoneListener = false;
/* 16 */   public boolean mobKill = false;
/* 17 */   public String mob = "TestMob";
/* 18 */   public int[] pr = new int[3];
/* 19 */   public String killMsg = "You slayen a mob :p";
/* 20 */   public int[] killRegion1 = new int[3]; public int[] killRegion2 = new int[3];
/* 21 */   public boolean killRegion = false;
/* 22 */   public boolean buttonListener = false;
/* 23 */   public int[] button = new int[3];
/* 24 */   public long ButtonInterval = 0L;
/* 25 */   public ArrayList<MaterialId> materials1 = new ArrayList();
/* 26 */   public ArrayList<MaterialId> materials2 = new ArrayList();
/* 27 */   public boolean openPerm = false; public boolean closePerm = false; public boolean buttonPerm = false; public boolean killPerm = false;
/*    */ 
/*    */   public GateData(String name, int[] p1, int[] p2, String world, ArrayList<MaterialId> materials1, ArrayList<MaterialId> materials2)
/*    */   {
/* 31 */     this.p1 = p1;
/* 32 */     this.p2 = p2;
/* 33 */     this.name = name;
/* 34 */     this.World = world;
/* 35 */     this.mob = "";
/* 36 */     this.pr[0] = 0;
/* 37 */     this.pr[1] = 0;
/* 38 */     this.pr[2] = 0;
/* 39 */     this.materials1 = materials1;
/* 40 */     this.materials2 = materials2;
/*    */   }
/*    */ 
/*    */   public GateData() {
/* 44 */     this.materials1.add(new MaterialId(0, Byte.parseByte("0")));
/* 45 */     this.materials2.add(new MaterialId(0, Byte.parseByte("0")));
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.GateData
 * JD-Core Version:    0.6.2
 */