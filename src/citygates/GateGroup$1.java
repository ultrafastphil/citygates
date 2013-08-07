/*     */ package citygates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ final class GateGroup$1 extends BukkitRunnable
/*     */ {
/*     */   GateGroup$1(GateGroup paramGateGroup)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  76 */     while (GateGroup.access$0(this.this$0))
/*     */       try {
/*  78 */         Thread.sleep(100L);
/*     */       } catch (Exception localException1) {
/*     */       }
/*  81 */     this.this$0.open = true;
/*  82 */     GateGroup.access$1(this.this$0, true);
/*  83 */     for (int a = 0; a < this.this$0.g.size(); a++) {
/*     */       try {
/*  85 */         Gate gate = (Gate)this.this$0.g.get(a);
/*  86 */         gate.Open();
/*     */       } catch (Exception e) {
/*     */         try {
/*  89 */           GateGroup group = (GateGroup)this.this$0.g.get(a);
/*  90 */           group.Open(); } catch (Exception localException2) {
/*     */         }
/*     */       }
/*     */       try {
/*  94 */         Thread.sleep(this.this$0.delay); } catch (Exception localException3) {
/*     */       }
/*     */     }
/*  97 */     for (int a = 0; a < this.this$0.g.size(); a++)
/*     */       try {
/*  99 */         Gate gate = (Gate)this.this$0.g.get(a);
/* 100 */         gate.Open();
/*     */       } catch (Exception e) {
/*     */         try {
/* 103 */           GateGroup group = (GateGroup)this.this$0.g.get(a);
/* 104 */           group.Open();
/*     */         } catch (Exception localException4) {
/*     */         }
/*     */       }
/* 108 */     GateGroup.access$1(this.this$0, false);
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.GateGroup.1
 * JD-Core Version:    0.6.2
 */