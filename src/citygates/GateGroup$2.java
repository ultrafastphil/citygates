/*     */ package citygates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ final class GateGroup$2 extends BukkitRunnable
/*     */ {
/*     */   GateGroup$2(GateGroup paramGateGroup)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 117 */     while (GateGroup.access$0(this.this$0))
/*     */       try {
/* 119 */         Thread.sleep(100L);
/*     */       } catch (Exception localException1) {
/*     */       }
/* 122 */     this.this$0.open = false;
/* 123 */     GateGroup.access$1(this.this$0, true);
/* 124 */     for (int a = this.this$0.g.size() - 1; a >= 0; a--) {
/*     */       try {
/* 126 */         Gate gate = (Gate)this.this$0.g.get(a);
/* 127 */         gate.Close();
/*     */       } catch (Exception e) {
/*     */         try {
/* 130 */           GateGroup group = (GateGroup)this.this$0.g.get(a);
/* 131 */           group.Close(); } catch (Exception localException2) {
/*     */         }
/*     */       }
/*     */       try {
/* 135 */         Thread.sleep(this.this$0.delay); } catch (Exception localException3) {
/*     */       }
/*     */     }
/* 138 */     for (int a = this.this$0.g.size() - 1; a >= 0; a--)
/*     */       try {
/* 140 */         Gate gate = (Gate)this.this$0.g.get(a);
/* 141 */         gate.Close();
/*     */       } catch (Exception e) {
/*     */         try {
/* 144 */           GateGroup group = (GateGroup)this.this$0.g.get(a);
/* 145 */           group.Close();
/*     */         } catch (Exception localException4) {
/*     */         }
/*     */       }
/* 149 */     GateGroup.access$1(this.this$0, false);
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.GateGroup.2
 * JD-Core Version:    0.6.2
 */