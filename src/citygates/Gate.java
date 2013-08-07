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
/*  13 */   private ArrayList<Block> blocks = new ArrayList();
/*     */   public GateData gd;
/*     */ 
/*     */   public Gate(GateData gd, Plugin p)
/*     */   {
/*  18 */     this.gd = gd;
/*     */ 
/*  20 */     int x1 = 0;
/*  21 */     int x2 = 0;
/*  22 */     int y1 = 0;
/*  23 */     int y2 = 0;
/*  24 */     int z1 = 0;
/*  25 */     int z2 = 0;
/*     */ 
/*  27 */     if (gd.p1[0] < gd.p2[0]) {
/*  28 */       x1 = gd.p1[0];
/*  29 */       x2 = gd.p2[0];
/*     */     } else {
/*  31 */       x2 = gd.p1[0];
/*  32 */       x1 = gd.p2[0];
/*     */     }
/*  34 */     if (gd.p1[1] < gd.p2[1]) {
/*  35 */       y1 = gd.p1[1];
/*  36 */       y2 = gd.p2[1];
/*     */     } else {
/*  38 */       y2 = gd.p1[1];
/*  39 */       y1 = gd.p2[1];
/*     */     }
/*  41 */     if (gd.p1[2] < gd.p2[2]) {
/*  42 */       z1 = gd.p1[2];
/*  43 */       z2 = gd.p2[2];
/*     */     } else {
/*  45 */       z2 = gd.p1[2];
/*  46 */       z1 = gd.p2[2];
/*     */     }
/*     */ 
/*  49 */     int bc = 0;
/*  50 */     for (int a = x1; a <= x2; a++) {
/*  51 */       for (int b = y1; b <= y2; b++) {
/*  52 */         for (int c = z1; c <= z2; c++) {
/*  53 */           Block block = p.getServer().getWorld(gd.World).getBlockAt(a, b, c);
/*  54 */           this.blocks.add(block);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  59 */     if (gd.open)
/*  60 */       Open();
/*     */   }
/*     */ 
/*     */   public void Open()
/*     */   {
/*  65 */     if (this.gd.materials2.size() == 1)
/*  66 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  67 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials2.get(0)).getID());
/*  68 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials2.get(0)).getData());
/*     */       }
/*     */     else {
/*  71 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  72 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials2.get(a)).getID());
/*  73 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials2.get(a)).getData());
/*     */       }
/*     */     }
/*  76 */     this.gd.open = true;
/*     */   }
/*     */ 
/*     */   public void Close() {
/*  80 */     if (this.gd.materials1.size() == 1)
/*  81 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  82 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials1.get(0)).getID());
/*  83 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials1.get(0)).getData());
/*     */       }
/*     */     else {
/*  86 */       for (int a = 0; a < this.blocks.size(); a++) {
/*  87 */         ((Block)this.blocks.get(a)).setTypeId(((MaterialId)this.gd.materials1.get(a)).getID());
/*  88 */         ((Block)this.blocks.get(a)).setData(((MaterialId)this.gd.materials1.get(a)).getData());
/*     */       }
/*     */     }
/*  91 */     this.gd.open = false;
/*     */   }
/*     */ 
/*     */   public void setRedstoneListener(Location l) {
/*  95 */     this.gd.pr[0] = l.getBlockX();
/*  96 */     this.gd.pr[1] = l.getBlockY();
/*  97 */     this.gd.pr[2] = l.getBlockZ();
/*  98 */     this.gd.redstoneListener = true;
/*     */   }
/*     */ 
/*     */   public void removeRedstoneListener() {
/* 102 */     this.gd.redstoneListener = false;
/*     */   }
/*     */ 
/*     */   public boolean getRedstoneListener() {
/* 106 */     return this.gd.redstoneListener;
/*     */   }
/*     */ 
/*     */   public void setTimeGate() {
/* 110 */     this.gd.timeGate = true;
/*     */   }
/*     */ 
/*     */   public void removeTimeGate() {
/* 114 */     this.gd.timeGate = false;
/*     */   }
/*     */ 
/*     */   public boolean getTimeGate() {
/* 118 */     return this.gd.timeGate;
/*     */   }
/*     */ 
/*     */   public void setMobKill(EntityType mob) {
/* 122 */     this.gd.mob = mob.getName();
/* 123 */     this.gd.mobKill = true;
/*     */   }
/*     */ 
/*     */   public void removeMobKill() {
/* 127 */     this.gd.mobKill = false;
/*     */   }
/*     */ 
/*     */   public boolean getMobKill() {
/* 131 */     return this.gd.mobKill;
/*     */   }
/*     */ 
/*     */   public String getMob() {
/* 135 */     return this.gd.mob;
/*     */   }
/*     */ 
/*     */   public void setTime(int time) {
/* 139 */     if (time == Time.NACHT) {
/* 140 */       if (this.gd.open) {
/* 141 */         Close();
/*     */       }
/*     */     }
/* 144 */     else if (!this.gd.open)
/* 145 */       Open();
/*     */   }
/*     */ 
/*     */   public void setKillArea(Location p1, Location p2)
/*     */   {
/* 150 */     this.gd.killRegion1[0] = p1.getBlockX();
/* 151 */     this.gd.killRegion1[1] = p1.getBlockY();
/* 152 */     this.gd.killRegion1[2] = p1.getBlockZ();
/* 153 */     this.gd.killRegion2[0] = p2.getBlockX();
/* 154 */     this.gd.killRegion2[1] = p2.getBlockY();
/* 155 */     this.gd.killRegion2[2] = p2.getBlockZ();
/* 156 */     this.gd.killRegion = true;
/*     */   }
/*     */ 
/*     */   public void removeKillArea() {
/* 160 */     this.gd.killRegion = false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.Gate
 * JD-Core Version:    0.6.2
 */