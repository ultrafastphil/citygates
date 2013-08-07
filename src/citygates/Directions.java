/*    */ package citygates;
/*    */ 
/*    */ public class Directions
/*    */ {
/*    */   public static final int UNKNOW = 0;
/*    */   public static final int NORTH = 1;
/*    */   public static final int EAST = 2;
/*    */   public static final int SOUTH = 3;
/*    */   public static final int WEST = 4;
/*    */   public static final int UP = 5;
/*    */   public static final int DOWN = 6;
/*    */ 
/*    */   public static final int getDirection(String line)
/*    */   {
/* 14 */     line = line.toLowerCase();
/* 15 */     if ((line.equals("n")) || (line.equals("north")) || (line.equals("1")))
/* 16 */       return 1;
/* 17 */     if ((line.equals("e")) || (line.equals("east")) || (line.equals("2")))
/* 18 */       return 2;
/* 19 */     if ((line.equals("s")) || (line.equals("south")) || (line.equals("3")))
/* 20 */       return 3;
/* 21 */     if ((line.equals("w")) || (line.equals("west")) || (line.equals("4")))
/* 22 */       return 4;
/* 23 */     if ((line.equals("u")) || (line.equals("up")) || (line.equals("5")))
/* 24 */       return 5;
/* 25 */     if ((line.equals("d")) || (line.equals("down")) || (line.equals("6"))) {
/* 26 */       return 6;
/*    */     }
/* 28 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.Directions
 * JD-Core Version:    0.6.2
 */