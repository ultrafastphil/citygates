/*     */ package citygates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class Gate
/*     */ {
/* 158 */   private ArrayList<Block> blocks = new ArrayList();
/*     */   public GateData gd;
/*     */ 
/*     */   public Gate(GateData gd, Plugin p)
/*     */   {
/*  12 */     this.gd = gd;
/*     */ 
/*  14 */     int x1 = 0;
/*  15 */     int x2 = 0;
/*  16 */     int y1 = 0;
/*  17 */     int y2 = 0;
/*  18 */     int z1 = 0;
/*  19 */     int z2 = 0;
/*     */ 
/*  21 */     if (gd.p1[0] < gd.p2[0]) {
/*  22 */       x1 = gd.p1[0];
/*  23 */       x2 = gd.p2[0];
/*     */     } else {
/*  25 */       x2 = gd.p1[0];
/*  26 */       x1 = gd.p2[0];
/*     */     }
/*  28 */     if (gd.p1[1] < gd.p2[1]) {
/*  29 */       y1 = gd.p1[1];
/*  30 */       y2 = gd.p2[1];
/*     */     } else {
/*  32 */       y2 = gd.p1[1];
/*  33 */       y1 = gd.p2[1];
/*     */     }
/*  35 */     if (gd.p1[2] < gd.p2[2]) {
/*  36 */       z1 = gd.p1[2];
/*  37 */       z2 = gd.p2[2];
/*     */     } else {
/*  39 */       z2 = gd.p1[2];
/*  40 */       z1 = gd.p2[2];
/*     */     }
/*     */ 
/*  43 */     int bc = 0;
/*  44 */     for (int a = x1; a <= x2; a++) {
/*  45 */       for (int b = y1; b <= y2; b++) {
/*  46 */         for (int c = z1; c <= z2; c++) {
/*  47 */           Block block = p.getServer().getWorld(gd.World).getBlockAt(a, b, c);
/*  48 */           this.blocks.add(block);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  53 */     if (gd.open)
/*  54 */       Open();
/*     */   }
/*     */ 
/*     */   public void Open()
/*     */   {
/*  59 */     if (this.gd.materials2.size() == 1)
/*  60 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  61 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials2.get(0)).getID());
/*  62 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials2.get(0)).getData());
/*     */       }
/*     */     else {
/*  65 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  66 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials2.get(a)).getID());
/*  67 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials2.get(a)).getData());
/*     */       }
/*     */     }
/*  70 */     this.gd.open = true;
/*     */   }
/*     */ 
/*     */   public void Close() {
/*  74 */     if (this.gd.materials1.size() == 1)
/*  75 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  76 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials1.get(0)).getID());
/*  77 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials1.get(0)).getData());
/*     */       }
/*     */     else {
/*  80 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  81 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials1.get(a)).getID());
/*  82 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials1.get(a)).getData());
/*     */       }
/*     */     }
/*  85 */     this.gd.open = false;
/*     */   }
/*     */ 
/*     */   public void setRedstoenListener(Location l) {
/*  89 */     this.gd.pr[0] = l.getBlockX();
/*  90 */     this.gd.pr[1] = l.getBlockY();
/*  91 */     this.gd.pr[2] = l.getBlockZ();
/*  92 */     this.gd.redstoneListener = true;
/*     */   }
/*     */ 
/*     */   public void removeRedstoneListener() {
/*  96 */     this.gd.redstoneListener = false;
/*     */   }
/*     */ 
/*     */   public boolean getRedstoneListener() {
/* 100 */     return this.gd.redstoneListener;
/*     */   }
/*     */ 
/*     */   public void setTimeGate() {
/* 104 */     this.gd.timeGate = true;
/*     */   }
/*     */ 
/*     */   public void removeTimeGate() {
/* 108 */     this.gd.timeGate = false;
/*     */   }
/*     */ 
/*     */   public boolean getTimeGate() {
/* 112 */     return this.gd.timeGate;
/*     */   }
/*     */ 
/*     */   public void setMobKill(EntityType mob) {
/* 116 */     this.gd.mob = mob.getName();
/* 117 */     this.gd.mobKill = true;
/*     */   }
/*     */ 
/*     */   public void removeMobKill() {
/* 121 */     this.gd.mobKill = false;
/*     */   }
/*     */ 
/*     */   public boolean getMobKill() {
/* 125 */     return this.gd.mobKill;
/*     */   }
/*     */ 
/*     */   public String getMob() {
/* 129 */     return this.gd.mob;
/*     */   }
/*     */ 
/*     */   public void setTime(int time) {
/* 133 */     if (time == Time.NACHT) {
/* 134 */       if (this.gd.open) {
/* 135 */         Close();
/*     */       }
/*     */     }
/* 138 */     else if (!this.gd.open)
/* 139 */       Open();
/*     */   }
/*     */ 
/*     */   public void setKillArea(Location p1, Location p2)
/*     */   {
/* 145 */     this.gd.killRegion1[0] = p1.getBlockX();
/* 146 */     this.gd.killRegion1[1] = p1.getBlockY();
/* 147 */     this.gd.killRegion1[2] = p1.getBlockZ();
/* 148 */     this.gd.killRegion2[0] = p2.getBlockX();
/* 149 */     this.gd.killRegion2[1] = p2.getBlockY();
/* 150 */     this.gd.killRegion2[2] = p2.getBlockZ();
/* 151 */     this.gd.killRegion = true;
/*     */   }
/*     */ 
/*     */   public void removeKillArea() {
/* 155 */     this.gd.killRegion = false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.Gate
 * JD-Core Version:    0.6.2
 */