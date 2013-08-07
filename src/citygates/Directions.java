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
/* 15 */     line = line.toLowerCase();
/* 16 */     if ((line.equals("n")) || (line.equals("north")) || (line.equals("1")))
/* 17 */       return 1;
/* 18 */     if ((line.equals("e")) || (line.equals("east")) || (line.equals("2")))
/* 19 */       return 2;
/* 20 */     if ((line.equals("s")) || (line.equals("south")) || (line.equals("3")))
/* 21 */       return 3;
/* 22 */     if ((line.equals("w")) || (line.equals("west")) || (line.equals("4")))
/* 23 */       return 4;
/* 24 */     if ((line.equals("u")) || (line.equals("up")) || (line.equals("5")))
/* 25 */       return 5;
/* 26 */     if ((line.equals("d")) || (line.equals("down")) || (line.equals("6"))) {
/* 27 */       return 6;
/*    */     }
/* 29 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.Directions
 * JD-Core Version:    0.6.2
 */