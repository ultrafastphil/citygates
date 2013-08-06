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
/*     */ 
/*     */   public GateGroup(String name, Plugin plugin)
/*     */   {
/*   9 */     this.name = name;
/*  10 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   public void add(Gate gate)
/*     */   {
/*  33 */     this.g.add(gate);
/*     */   }
/*     */ 
/*     */   public void add(GateGroup group) {
/*  37 */     this.g.add(group);
/*     */   }
/*     */ 
/*     */   public void remove(Gate gate) {
/*  41 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/*  43 */         Gate gate2 = (Gate)this.g.get(a);
/*  44 */         if (gate2.gd.name.equals(gate.gd.name))
/*  45 */           this.g.remove(a);
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void remove(GateGroup group) {
/*  52 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/*  54 */         GateGroup group2 = (GateGroup)this.g.get(a);
/*  55 */         if (group2.name.equals(group.name))
/*  56 */           this.g.remove(a);
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setDelay(long time) {
/*  63 */     this.delay = time;
/*     */   }
/*     */ 
/*     */   public void Clear() {
/*  67 */     this.g = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void Open() {
/*  71 */     new Thread(new Runnable() {
/*     */       public void run() {
/*  73 */         while (GateGroup.this.inprogress)
/*     */           try {
/*  75 */             Thread.sleep(100L);
/*     */           } catch (Exception e) {
/*     */           }
/*  78 */         GateGroup.this.open = true;
/*  79 */         GateGroup.this.inprogress = true;
/*  80 */         for (int a = 0; a < GateGroup.this.g.size(); a++) {
/*     */           try {
/*  82 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/*  83 */             gate.Open();
/*     */           } catch (Exception e) {
/*     */             try {
/*  86 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/*  87 */               group.Open(); } catch (Exception ee) {
/*     */             }
/*     */           }
/*     */           try {
/*  91 */             Thread.sleep(GateGroup.this.delay); } catch (Exception e) {
/*     */           }
/*     */         }
/*  94 */         for (int a = 0; a < GateGroup.this.g.size(); a++)
/*     */           try {
/*  96 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/*  97 */             gate.Open();
/*     */           } catch (Exception e) {
/*     */             try {
/* 100 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/* 101 */               group.Open();
/*     */             } catch (Exception ee) {
/*     */             }
/*     */           }
/* 105 */         GateGroup.this.inprogress = false;
/*     */       }
/*     */     }).start();
/*     */   }
/*     */ 
/*     */   public void Close()
/*     */   {
/* 111 */     new Thread(new Runnable() {
/*     */       public void run() {
/* 113 */         while (GateGroup.this.inprogress)
/*     */           try {
/* 115 */             Thread.sleep(100L);
/*     */           } catch (Exception e) {
/*     */           }
/* 118 */         GateGroup.this.open = false;
/* 119 */         GateGroup.this.inprogress = true;
/* 120 */         for (int a = GateGroup.this.g.size() - 1; a >= 0; a--) {
/*     */           try {
/* 122 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/* 123 */             gate.Close();
/*     */           } catch (Exception e) {
/*     */             try {
/* 126 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/* 127 */               group.Close(); } catch (Exception ee) {
/*     */             }
/*     */           }
/*     */           try {
/* 131 */             Thread.sleep(GateGroup.this.delay); } catch (Exception e) {
/*     */           }
/*     */         }
/* 134 */         for (int a = GateGroup.this.g.size() - 1; a >= 0; a--)
/*     */           try {
/* 136 */             Gate gate = (Gate)GateGroup.this.g.get(a);
/* 137 */             gate.Close();
/*     */           } catch (Exception e) {
/*     */             try {
/* 140 */               GateGroup group = (GateGroup)GateGroup.this.g.get(a);
/* 141 */               group.Close();
/*     */             } catch (Exception ee) {
/*     */             }
/*     */           }
/* 145 */         GateGroup.this.inprogress = false;
/*     */       }
/*     */     }).start();
/*     */   }
/*     */ 
/*     */   public void setTime(int time)
/*     */   {
/* 151 */     if (time == Time.NACHT) {
/* 152 */       if (this.open) {
/* 153 */         Close();
/*     */       }
/*     */     }
/* 156 */     else if (!this.open)
/* 157 */       Open();
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.GateGroup
 * JD-Core Version:    0.6.2
 */