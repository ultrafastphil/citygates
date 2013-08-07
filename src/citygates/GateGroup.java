/*     */ package citygates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class GateGroup
/*     */ {
/*   9 */   public ArrayList g = new ArrayList();
/*  10 */   public long delay = 0L;
/*  11 */   private boolean inprogress = false;
/*  12 */   public boolean open = false;
/*     */   public String name;
/*     */   private Plugin plugin;
/*     */   public String world;
/*  16 */   public boolean timegate = false;
/*  17 */   public boolean redstoneListener = false;
/*  18 */   public boolean mobKill = false;
/*  19 */   public boolean buttonListener = false;
/*     */ 
/*  21 */   public String mob = "testMob";
/*  22 */   public int[] pr = new int[3];
/*  23 */   public int[] button = new int[3];
/*  24 */   public String killMsg = "You slayen a mob :p";
/*  25 */   public long ButtonInterval = 0L;
/*  26 */   public boolean openPerm = false; public boolean closePerm = false; public boolean buttonPerm = false; public boolean killPerm = false;
/*     */ 
/*     */   public GateGroup(String name, Plugin plugin)
/*     */   {
/*  30 */     this.name = name;
/*  31 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   public void add(Gate gate)
/*     */   {
/*  36 */     this.g.add(gate);
/*     */   }
/*     */ 
/*     */   public void add(GateGroup group) {
/*  40 */     this.g.add(group);
/*     */   }
/*     */ 
/*     */   public void remove(Gate gate) {
/*  44 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/*  46 */         Gate gate2 = (Gate)this.g.get(a);
/*  47 */         if (gate2.gd.name.equals(gate.gd.name))
/*  48 */           this.g.remove(a);
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void remove(GateGroup group) {
/*  55 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/*  57 */         GateGroup group2 = (GateGroup)this.g.get(a);
/*  58 */         if (group2.name.equals(group.name))
/*  59 */           this.g.remove(a);
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setDelay(long time) {
/*  66 */     this.delay = time;
/*     */   }
/*     */ 
/*     */   public void Clear() {
/*  70 */     this.g = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void Open()
/*     */   {
/* 117 */     this.open = true;
/* 118 */     this.inprogress = true;
/* 119 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/* 121 */         Gate gate = (Gate)this.g.get(a);
/* 122 */         gate.Open();
/*     */       } catch (Exception e) {
/*     */         try {
/* 125 */           GateGroup group = (GateGroup)this.g.get(a);
/* 126 */           group.Open(); } catch (Exception localException1) {
/*     */         }
/* 128 */         this.inprogress = false;
/*     */       }
/*     */   }
/*     */ 
/*     */   public void Close()
/*     */   {
/* 180 */     this.open = false;
/* 181 */     this.inprogress = true;
/* 182 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/* 184 */         Gate gate = (Gate)this.g.get(a);
/* 185 */         gate.Close();
/*     */       } catch (Exception e) {
/*     */         try {
/* 188 */           GateGroup group = (GateGroup)this.g.get(a);
/* 189 */           group.Close(); } catch (Exception localException1) {
/*     */         }
/* 191 */         this.inprogress = false;
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setTime(int time)
/*     */   {
/* 202 */     if (time == Time.NACHT) {
/* 203 */       if (this.open) {
/* 204 */         Close();
/*     */       }
/*     */     }
/* 207 */     else if (!this.open)
/* 208 */       Open();
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.GateGroup
 * JD-Core Version:    0.6.2
 */