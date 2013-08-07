/*    */ package citygates;
/*    */ 
/*    */ public class MaterialId
/*    */ {
/*    */   private int id;
/*    */   private byte data;
/*    */ 
/*    */   public MaterialId(int id, byte data)
/*    */   {
/*  6 */     this.id = id;
/*  7 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public void setID(int id)
/*    */   {
/* 14 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public void setData(byte data) {
/* 18 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public void setMaterials(int id, byte data) {
/* 22 */     this.id = id;
/* 23 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public int getID() {
/* 27 */     return this.id;
/*    */   }
/*    */ 
/*    */   public byte getData() {
/* 31 */     return this.data;
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.MaterialId
 * JD-Core Version:    0.6.2
 */