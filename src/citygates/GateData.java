/*    */ package citygates;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class GateData
/*    */   implements Serializable
/*    */ {
/* 26 */   public int[] p1 = new int[3];
/* 27 */   public int[] p2 = new int[3];
/*    */   public String name;
/*    */   public String World;
/* 30 */   public boolean open = false;
/* 31 */   public boolean timeGate = false;
/* 32 */   public boolean redstoneListener = false;
/* 33 */   public boolean mobKill = false;
/* 34 */   public String mob = "TestMob";
/* 35 */   public int[] pr = new int[3];
/* 36 */   public String killMsg = "You slayen a mob :p";
/* 37 */   public int[] killRegion1 = new int[3]; public int[] killRegion2 = new int[3];
/* 38 */   public boolean killRegion = false;
/* 39 */   public boolean buttonListener = false;
/* 40 */   public int[] button = new int[3];
/* 41 */   public long ButtonInterval = 0L;
/* 42 */   public ArrayList<MaterialId> materials1 = new ArrayList();
/* 43 */   public ArrayList<MaterialId> materials2 = new ArrayList();
/* 44 */   public boolean openPerm = false; public boolean closePerm = false; public boolean buttonPerm = false; public boolean killPerm = false;
/*    */ 
/*    */   public GateData(String name, int[] p1, int[] p2, String world, ArrayList<MaterialId> materials1, ArrayList<MaterialId> materials2)
/*    */   {
/*  9 */     this.p1 = p1;
/* 10 */     this.p2 = p2;
/* 11 */     this.name = name;
/* 12 */     this.World = world;
/* 13 */     this.mob = "";
/* 14 */     this.pr[0] = 0;
/* 15 */     this.pr[1] = 0;
/* 16 */     this.pr[2] = 0;
/* 17 */     this.materials1 = materials1;
/* 18 */     this.materials2 = materials2;
/*    */   }
/*    */ 
/*    */   public GateData() {
/* 22 */     this.materials1.add(new MaterialId(0, Byte.parseByte("0")));
/* 23 */     this.materials2.add(new MaterialId(0, Byte.parseByte("0")));
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.GateData
 * JD-Core Version:    0.6.2
 */