/*     */ package citygates;
/*     */ 
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class Gate
/*     */ {
/*     */   private Block[] blocks;
/*     */   private Material[] backup;
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
/*  43 */     this.blocks = new Block[500];
/*  44 */     this.backup = new Material[500];
/*  45 */     int bc = 0;
/*  46 */     for (int a = x1; a <= x2; a++) {
/*  47 */       for (int b = y1; b <= y2; b++) {
/*  48 */         for (int c = z1; c <= z2; c++) {
/*  49 */           if (bc < this.blocks.length) {
/*  50 */             this.blocks[bc] = p.getServer().getWorld(gd.World).getBlockAt(a, b, c);
/*  51 */             this.backup[bc] = this.blocks[bc].getType();
/*  52 */             bc++;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  58 */     if (gd.open)
/*  59 */       Open();
/*     */   }
/*     */ 
/*     */   public void Open()
/*     */   {
/*  64 */     for (int a = 0; a < this.blocks.length; a++) {
/*  65 */       if (this.blocks[a] != null) {
/*  66 */         this.blocks[a].setTypeId(0);
/*     */       }
/*     */     }
/*  69 */     this.gd.open = true;
/*     */   }
/*     */ 
/*     */   public void Close() {
/*  73 */     for (int a = 0; a < this.blocks.length; a++) {
/*  74 */       if (this.blocks[a] != null) {
/*  75 */         this.blocks[a].setType(this.backup[a]);
/*     */       }
/*     */     }
/*  78 */     this.gd.open = false;
/*     */   }
/*     */ 
/*     */   public void setRedstoenListener(Location l) {
/*  82 */     this.gd.pr[0] = l.getBlockX();
/*  83 */     this.gd.pr[1] = l.getBlockY();
/*  84 */     this.gd.pr[2] = l.getBlockZ();
/*  85 */     this.gd.redstoneListener = true;
/*     */   }
/*     */ 
/*     */   public void removeRedstoneListener() {
/*  89 */     this.gd.redstoneListener = false;
/*     */   }
/*     */ 
/*     */   public boolean getRedstoneListener() {
/*  93 */     return this.gd.redstoneListener;
/*     */   }
/*     */ 
/*     */   public void setTimeGate() {
/*  97 */     this.gd.timeGate = true;
/*     */   }
/*     */ 
/*     */   public void removeTimeGate() {
/* 101 */     this.gd.timeGate = false;
/*     */   }
/*     */ 
/*     */   public boolean getTimeGate() {
/* 105 */     return this.gd.timeGate;
/*     */   }
/*     */ 
/*     */   public void setMobKill(EntityType mob) {
/* 109 */     this.gd.mob = mob.getName();
/* 110 */     this.gd.mobKill = true;
/*     */   }
/*     */ 
/*     */   public void removeMobKill() {
/* 114 */     this.gd.mobKill = false;
/*     */   }
/*     */ 
/*     */   public boolean getMobKill() {
/* 118 */     return this.gd.mobKill;
/*     */   }
/*     */ 
/*     */   public String getMob() {
/* 122 */     return this.gd.mob;
/*     */   }
/*     */ 
/*     */   public void setTime(int time) {
/* 126 */     if (time == Time.NACHT) {
/* 127 */       if (this.gd.open) {
/* 128 */         Close();
/*     */       }
/*     */     }
/* 131 */     else if (!this.gd.open)
/* 132 */       Open();
/*     */   }
/*     */ 
/*     */   public void setKillArea(Location p1, Location p2)
/*     */   {
/* 138 */     this.gd.killRegion1[0] = p1.getBlockX();
/* 139 */     this.gd.killRegion1[1] = p1.getBlockY();
/* 140 */     this.gd.killRegion1[2] = p1.getBlockZ();
/* 141 */     this.gd.killRegion2[0] = p2.getBlockX();
/* 142 */     this.gd.killRegion2[1] = p2.getBlockY();
/* 143 */     this.gd.killRegion2[2] = p2.getBlockZ();
/* 144 */     this.gd.killRegion = true;
/*     */   }
/*     */ 
/*     */   public void removeKillArea() {
/* 148 */     this.gd.killRegion = false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.Gate
 * JD-Core Version:    0.6.2
 */