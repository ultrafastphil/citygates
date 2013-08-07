/*     */ package citygates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class GateGroup
/*     */ {
/*  13 */   public ArrayList g = new ArrayList();
/*  14 */   public long delay = 0L;
/*  15 */   private boolean inprogress = false;
/*  16 */   public boolean open = false;
/*     */   public String name;
/*     */   private Plugin plugin;
/*     */   public String world;
/*  21 */   public boolean timegate = false;
/*  22 */   public boolean redstoneListener = false;
/*  23 */   public boolean mobKill = false;
/*  24 */   public boolean buttonListener = false;
/*     */ 
/*  26 */   public String mob = "testMob";
/*  27 */   public int[] pr = new int[3];
/*  28 */   public int[] button = new int[3];
/*  29 */   public String killMsg = "You slayen a mob :p";
/*  30 */   public long ButtonInterval = 0L;
/*  31 */   public boolean openPerm = false; public boolean closePerm = false; public boolean buttonPerm = false; public boolean killPerm = false;
/*     */ 
/*     */   public GateGroup(String name, Plugin plugin)
/*     */   {
/*   9 */     this.name = name;
/*  10 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   public void add(Gate gate)
/*     */   {
/*  34 */     this.g.add(gate);
/*     */   }
/*     */ 
/*     */   public void add(GateGroup group) {
/*  38 */     this.g.add(group);
/*     */   }
/*     */ 
/*     */   public void remove(Gate gate) {
/*  42 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/*  44 */         Gate gate2 = (Gate)this.g.get(a);
/*  45 */         if (gate2.gd.name.equals(gate.gd.name))
/*  46 */           this.g.remove(a);
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void remove(GateGroup group) {
/*  53 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/*  55 */         GateGroup group2 = (GateGroup)this.g.get(a);
/*  56 */         if (group2.name.equals(group.name))
/*  57 */           this.g.remove(a);
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setDelay(long time) {
/*  64 */     this.delay = time;
/*     */   }
/*     */ 
/*     */   public void Clear() {
/*  68 */     this.g = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void Open() {
/*  72 */     new Thread(new Runnable() {
/*     */       public void run() {
/*  74 */         while (GateGroup.this.inprogress)
/*     */           try {
/*  76 */             Thread.sleep(100L);
/*     */           } catch (Exception e) {
/*     */           }
/*  79 */         GateGroup.this.open = true;
/*  80 */         GateGroup.this.inprogress = true;
/*  81 */         for (int a = 0; a < GateGroup.this.g.size(); a++) {
/*     */           try {
/*  83 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/*  84 */             gate.Open();
/*     */           } catch (Exception e) {
/*     */             try {
/*  87 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/*  88 */               group.Open(); } catch (Exception ee) {
/*     */             }
/*     */           }
/*     */           try {
/*  92 */             Thread.sleep(GateGroup.this.delay); } catch (Exception e) {
/*     */           }
/*     */         }
/*  95 */         for (int a = 0; a < GateGroup.this.g.size(); a++)
/*     */           try {
/*  97 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/*  98 */             gate.Open();
/*     */           } catch (Exception e) {
/*     */             try {
/* 101 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/* 102 */               group.Open();
/*     */             } catch (Exception ee) {
/*     */             }
/*     */           }
/* 106 */         GateGroup.this.inprogress = false;
/*     */       }
/*     */     }).start();
/*     */   }
/*     */ 
/*     */   public void Close()
/*     */   {
/* 112 */     new Thread(new Runnable() {
/*     */       public void run() {
/* 114 */         while (GateGroup.this.inprogress)
/*     */           try {
/* 116 */             Thread.sleep(100L);
/*     */           } catch (Exception e) {
/*     */           }
/* 119 */         GateGroup.this.open = false;
/* 120 */         GateGroup.this.inprogress = true;
/* 121 */         for (int a = GateGroup.this.g.size() - 1; a >= 0; a--) {
/*     */           try {
/* 123 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/* 124 */             gate.Close();
/*     */           } catch (Exception e) {
/*     */             try {
/* 127 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/* 128 */               group.Close(); } catch (Exception ee) {
/*     */             }
/*     */           }
/*     */           try {
/* 132 */             Thread.sleep(GateGroup.this.delay); } catch (Exception e) {
/*     */           }
/*     */         }
/* 135 */         for (int a = GateGroup.this.g.size() - 1; a >= 0; a--)
/*     */           try {
/* 137 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/* 138 */             gate.Close();
/*     */           } catch (Exception e) {
/*     */             try {
/* 141 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/* 142 */               group.Close();
/*     */             } catch (Exception ee) {
/*     */             }
/*     */           }
/* 146 */         GateGroup.this.inprogress = false;
/*     */       }
/*     */     }).start();
/*     */   }
/*     */ 
/*     */   public void setTime(int time)
/*     */   {
/* 152 */     if (time == Time.NACHT) {
/* 153 */       if (this.open) {
/* 154 */         Close();
/*     */       }
/*     */     }
/* 157 */     else if (!this.open)
/* 158 */       Open();
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.GateGroup
 * JD-Core Version:    0.6.2
 */