/*    */ package citygates;
/*    */ 
/*    */ public class MaterialId
/*    */ {
/*    */   private int id;
/*    */   private byte data;
/*    */ 
/*    */   public MaterialId(int id, byte data)
/*    */   {
/* 10 */     this.id = id;
/* 11 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public void setID(int id)
/*    */   {
/* 16 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public void setData(byte data) {
/* 20 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public void setMaterials(int id, byte data) {
/* 24 */     this.id = id;
/* 25 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public int getID() {
/* 29 */     return this.id;
/*    */   }
/*    */ 
/*    */   public byte getData() {
/* 33 */     return this.data;
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.MaterialId
 * JD-Core Version:    0.6.2
 */