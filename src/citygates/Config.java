/*     */ package citygates;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class Config
/*     */ {
/*     */   public static ArrayList<GateData> LoadGates(Plugin plugin)
/*     */     throws Exception
/*     */   {
/*  11 */     ArrayList gd = new ArrayList();
/*  12 */     FileInputStream fis = new FileInputStream("plugins/CityGates/gates.yml");
/*  13 */     int lines = fis.available();
/*  14 */     if (lines == 0) {
/*  15 */       throw new IOException("File is empty");
/*     */     }
/*  17 */     BufferedReader bf = new BufferedReader(new FileReader("plugins/CityGates/gates.yml"));
/*     */     String line;
/*  19 */     while ((line = bf.readLine()) != null) {
/*  20 */       if (line.startsWith("Add Gate: ")) {
/*  21 */         GateData gate = new GateData();
/*  22 */         gate.name = line.split("Add Gate: ")[1];
/*  23 */         boolean p1 = false;
/*  24 */         boolean p2 = false;
/*  25 */         boolean World = false;
/*  26 */         while (!(line = bf.readLine()).equals("End Gate")) {
/*  27 */           while (line.startsWith(" "))
/*  28 */             line = line.substring(1);
/*  29 */           if (line.startsWith("p1: ")) {
/*  30 */             line = line.split("p1: ")[1];
/*  31 */             String[] points = line.split(", ");
/*  32 */             if (points.length == 3)
/*     */               try {
/*  34 */                 gate.p1[0] = Integer.parseInt(points[0]);
/*  35 */                 gate.p1[1] = Integer.parseInt(points[1]);
/*  36 */                 gate.p1[2] = Integer.parseInt(points[2]);
/*  37 */                 p1 = true;
/*     */               } catch (Exception e) {
/*     */               }
/*  40 */           } else if (line.startsWith("p2: ")) {
/*  41 */             line = line.split("p2: ")[1];
/*  42 */             String[] points = line.split(", ");
/*  43 */             if (points.length == 3)
/*     */               try {
/*  45 */                 gate.p2[0] = Integer.parseInt(points[0]);
/*  46 */                 gate.p2[1] = Integer.parseInt(points[1]);
/*  47 */                 gate.p2[2] = Integer.parseInt(points[2]);
/*  48 */                 p1 = true;
/*     */               } catch (Exception e) {
/*     */               }
/*  51 */           } else if (line.startsWith("World: ")) {
/*     */             try {
/*  53 */               plugin.getServer().getWorld(line.split("World: ")[1]);
/*  54 */               gate.World = line.split("World: ")[1];
/*  55 */               World = true; } catch (Exception e) {
/*     */             }
/*  57 */           } else if (line.startsWith("Open: ")) {
/*  58 */             line = line.split("Open: ")[1];
/*  59 */             if (line.equalsIgnoreCase("true"))
/*  60 */               gate.open = true;
/*  61 */             else if (line.equalsIgnoreCase("false"))
/*  62 */               gate.open = false;
/*     */           }
/*  64 */           else if (line.startsWith("timeGate: ")) {
/*  65 */             line = line.split("timeGate: ")[1];
/*  66 */             if (line.equalsIgnoreCase("true"))
/*  67 */               gate.timeGate = true;
/*  68 */             else if (line.equalsIgnoreCase("false"))
/*  69 */               gate.timeGate = false;
/*     */           }
/*  71 */           else if (line.startsWith("redstoneListener: ")) {
/*  72 */             line = line.split("redstoneListener: ")[1];
/*  73 */             if (line.equalsIgnoreCase("true"))
/*  74 */               gate.redstoneListener = true;
/*  75 */             else if (line.equalsIgnoreCase("false"))
/*  76 */               gate.redstoneListener = false;
/*     */           }
/*  78 */           else if (line.startsWith("mobKill: ")) {
/*  79 */             line = line.split("mobKill: ")[1];
/*  80 */             if (line.equalsIgnoreCase("true"))
/*  81 */               gate.mobKill = true;
/*  82 */             else if (line.equalsIgnoreCase("false"))
/*  83 */               gate.mobKill = false;
/*     */           }
/*  85 */           else if (line.startsWith("Button Listener: ")) {
/*  86 */             line = line.split("Button Listener: ")[1];
/*  87 */             if (line.equalsIgnoreCase("true"))
/*  88 */               gate.buttonListener = true;
/*  89 */             else if (line.equalsIgnoreCase("false"))
/*  90 */               gate.buttonListener = false;
/*     */           }
/*  92 */           else if (line.startsWith("Mob: ")) {
/*  93 */             gate.mob = line.split("Mob: ")[1];
/*  94 */           } else if (line.startsWith("pr: ")) {
/*  95 */             line = line.split("pr: ")[1];
/*  96 */             String[] points = line.split(", ");
/*  97 */             if (points.length == 3)
/*     */               try {
/*  99 */                 gate.pr[0] = Integer.parseInt(points[0]);
/* 100 */                 gate.pr[1] = Integer.parseInt(points[1]);
/* 101 */                 gate.pr[2] = Integer.parseInt(points[2]);
/*     */               } catch (Exception e) {
/*     */               }
/* 104 */           } else if (line.startsWith("Kill Message: ")) {
/* 105 */             gate.killMsg = line.split("Kill Message: ")[1];
/* 106 */           } else if (line.startsWith("Button: ")) {
/* 107 */             line = line.split("Button: ")[1];
/* 108 */             String[] points = line.split(", ");
/* 109 */             if (points.length == 3)
/*     */               try {
/* 111 */                 gate.button[0] = Integer.parseInt(points[0]);
/* 112 */                 gate.button[1] = Integer.parseInt(points[1]);
/* 113 */                 gate.button[2] = Integer.parseInt(points[2]);
/*     */               } catch (Exception e) {
/*     */               }
/* 116 */           } else if (line.startsWith("ButtonInterval: ")) {
/*     */             try {
/* 118 */               gate.ButtonInterval = Integer.parseInt(line.split("ButtonInterval: ")[1]);
/*     */             } catch (Exception e) {
/*     */             }
/*     */           }
/*     */         }
/* 123 */         gd.add(gate);
/*     */       }
/*     */     }
/* 126 */     bf.close();
/* 127 */     return gd;
/*     */   }
/*     */ 
/*     */   public static ArrayList<GateGroup> LoadGroups(ArrayList<Gate> gd, Plugin p) throws Exception {
/* 131 */     ArrayList gg = new ArrayList();
/* 132 */     FileInputStream fis = new FileInputStream("plugins/CityGates/groups.yml");
/* 133 */     int lines = fis.available();
/* 134 */     if (lines == 0) {
/* 135 */       throw new IOException("File is empty");
/*     */     }
/* 137 */     BufferedReader bf = new BufferedReader(new FileReader("plugins/CityGates/groups.yml"));
/*     */     String line;
/* 139 */     while ((line = bf.readLine()) != null) {
/* 140 */       if (line.startsWith("Add Group: ")) {
/* 141 */         GateGroup group = new GateGroup(line.split("Add Group: ")[1], p);
/* 142 */         while (!(line = bf.readLine()).equals("End Group")) {
/* 143 */           while (line.startsWith(" ")) {
/* 144 */             line = line.substring(1);
/*     */           }
/* 146 */           if (line.startsWith("Delay: ")) {
/*     */             try {
/* 148 */               group.setDelay(Integer.parseInt(line.split("Delay: ")[1])); } catch (Exception e) {
/*     */             }
/* 150 */           } else if (line.startsWith("World: ")) {
/*     */             try {
/* 152 */               p.getServer().getWorld(line.split("World: ")[1]);
/* 153 */               group.world = line.split("World: ")[1]; } catch (Exception e) {
/*     */             }
/* 155 */           } else if (line.startsWith("Open: ")) {
/* 156 */             line = line.split("Open: ")[1];
/* 157 */             if (line.equalsIgnoreCase("true"))
/* 158 */               group.open = true;
/* 159 */             else if (line.equalsIgnoreCase("false"))
/* 160 */               group.open = false;
/*     */           }
/* 162 */           else if (line.startsWith("timeGate: ")) {
/* 163 */             line = line.split("timeGate: ")[1];
/* 164 */             if (line.equalsIgnoreCase("true"))
/* 165 */               group.timegate = true;
/* 166 */             else if (line.equalsIgnoreCase("false"))
/* 167 */               group.timegate = false;
/*     */           }
/* 169 */           else if (line.startsWith("redstoneListener: ")) {
/* 170 */             line = line.split("redstoneListener: ")[1];
/* 171 */             if (line.equalsIgnoreCase("true"))
/* 172 */               group.redstoneListener = true;
/* 173 */             else if (line.equalsIgnoreCase("false"))
/* 174 */               group.redstoneListener = false;
/*     */           }
/* 176 */           else if (line.startsWith("mobKill: ")) {
/* 177 */             line = line.split("mobKill: ")[1];
/* 178 */             if (line.equalsIgnoreCase("true"))
/* 179 */               group.mobKill = true;
/* 180 */             else if (line.equalsIgnoreCase("false"))
/* 181 */               group.mobKill = false;
/*     */           }
/* 183 */           else if (line.startsWith("Button Listener: ")) {
/* 184 */             line = line.split("Button Listener: ")[1];
/* 185 */             if (line.equalsIgnoreCase("true"))
/* 186 */               group.buttonListener = true;
/* 187 */             else if (line.equalsIgnoreCase("false"))
/* 188 */               group.buttonListener = false;
/*     */           }
/* 190 */           else if (line.startsWith("Mob: ")) {
/* 191 */             group.mob = line.split("Mob: ")[1];
/* 192 */           } else if (line.startsWith("pr: ")) {
/* 193 */             line = line.split("pr: ")[1];
/* 194 */             String[] points = line.split(", ");
/* 195 */             if (points.length == 3)
/*     */               try {
/* 197 */                 group.pr[0] = Integer.parseInt(points[0]);
/* 198 */                 group.pr[1] = Integer.parseInt(points[1]);
/* 199 */                 group.pr[2] = Integer.parseInt(points[2]);
/*     */               } catch (Exception e) {
/*     */               }
/* 202 */           } else if (line.startsWith("Kill Message: ")) {
/* 203 */             group.killMsg = line.split("Kill Message: ")[1];
/* 204 */           } else if (line.startsWith("Button: ")) {
/* 205 */             line = line.split("Button: ")[1];
/* 206 */             String[] points = line.split(", ");
/* 207 */             if (points.length == 3)
/*     */               try {
/* 209 */                 group.button[0] = Integer.parseInt(points[0]);
/* 210 */                 group.button[1] = Integer.parseInt(points[1]);
/* 211 */                 group.button[2] = Integer.parseInt(points[2]);
/*     */               } catch (Exception e) {
/*     */               }
/* 214 */           } else if (line.startsWith("ButtonInterval: ")) {
/*     */             try {
/* 216 */               group.ButtonInterval = Integer.parseInt(line.split("ButtonInterval: ")[1]); } catch (Exception e) {
/*     */             }
/*     */           }
/*     */         }
/* 220 */         gg.add(group);
/*     */       }
/*     */     }
/* 223 */     bf.close();
/*     */ 
/* 225 */     int index = 0;
/* 226 */     bf = new BufferedReader(new FileReader("plugins/CityGates/groups.yml"));
/* 227 */     while ((line = bf.readLine()) != null) {
/* 228 */       if (line.startsWith("Add Group: ")) {
/* 229 */         GateGroup group = (GateGroup)gg.get(index);
/* 230 */         index++;
/* 231 */         while (!(line = bf.readLine()).equals("End Group")) {
/* 232 */           while (line.startsWith(" ")) {
/* 233 */             line = line.substring(1);
/*     */           }
/* 235 */           if (line.startsWith("Children: ")) {
/* 236 */             while (!(line = bf.readLine()).equals("  End Children")) {
/* 237 */               while (line.startsWith(" ")) {
/* 238 */                 line = line.substring(1);
/*     */               }
/* 240 */               for (int a = 0; a < gd.size(); a++) {
/* 241 */                 if (((Gate)gd.get(a)).gd.name.equals(line)) {
/* 242 */                   group.add((Gate)gd.get(a));
/*     */                 }
/*     */               }
/* 245 */               for (int a = 0; a < gg.size(); a++) {
/* 246 */                 if ((((GateGroup)gg.get(a)).name.equals(line)) && (!line.equals(group.name))) {
/* 247 */                   group.add((GateGroup)gg.get(a));
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 255 */     return gg;
/*     */   }
/*     */ 
/*     */   public static void SaveGates(ArrayList<GateData> gd) throws Exception {
/* 259 */     BufferedWriter bf = new BufferedWriter(new FileWriter("plugins/CityGates/gates.yml"));
/* 260 */     bf.write("# CityGates - Gates");
/* 261 */     bf.newLine();
/* 262 */     bf.newLine();
/* 263 */     bf.write("# Only edit everything after the ':'");
/* 264 */     bf.newLine();
/* 265 */     bf.write("# There are 3 defirent parameters: value, String, boolean");
/* 266 */     bf.newLine();
/* 267 */     bf.write("# Do not leave any parameter black!");
/* 268 */     bf.newLine();
/* 269 */     bf.newLine();
/* 270 */     bf.write("# value: Any number without dots!");
/* 271 */     bf.newLine();
/* 272 */     bf.write("# String: Text");
/* 273 */     bf.newLine();
/* 274 */     bf.write("# Boolean: true/false");
/* 275 */     bf.newLine();
/* 276 */     bf.newLine();
/*     */ 
/* 278 */     for (int a = 0; a < gd.size(); a++) {
/* 279 */       GateData g = (GateData)gd.get(a);
/* 280 */       bf.write("Add Gate: " + g.name);
/* 281 */       bf.newLine();
/* 282 */       bf.write("  p1: " + g.p1[0] + ", " + g.p1[1] + ", " + g.p1[2]);
/* 283 */       bf.newLine();
/* 284 */       bf.write("  p2: " + g.p2[0] + ", " + g.p2[1] + ", " + g.p2[2]);
/* 285 */       bf.newLine();
/* 286 */       bf.write("  World: " + g.World);
/* 287 */       bf.newLine();
/* 288 */       bf.write("  Open: " + g.open);
/* 289 */       bf.newLine();
/* 290 */       bf.write("  timeGate: " + g.timeGate);
/* 291 */       bf.newLine();
/* 292 */       bf.write("  redstoneListener: " + g.redstoneListener);
/* 293 */       bf.newLine();
/* 294 */       bf.write("  mobKill: " + g.mobKill);
/* 295 */       bf.newLine();
/* 296 */       bf.write("  Button Listener: " + g.buttonListener);
/* 297 */       bf.newLine();
/* 298 */       if ((g.mob == null) || (g.mob.equals("")))
/* 299 */         bf.write("  Mob: testMob");
/*     */       else
/* 301 */         bf.write("  Mob: " + g.mob);
/* 302 */       bf.newLine();
/* 303 */       bf.write("  pr: " + g.pr[0] + ", " + g.pr[1] + ", " + g.pr[2]);
/* 304 */       bf.newLine();
/* 305 */       bf.write("  Kill Message: " + g.killMsg);
/* 306 */       bf.newLine();
/* 307 */       bf.write("  ButtonInterval: " + g.ButtonInterval);
/* 308 */       bf.newLine();
/* 309 */       bf.write("  Button: " + g.button[0] + ", " + g.button[1] + ", " + g.button[2]);
/* 310 */       bf.newLine();
/* 311 */       bf.write("End Gate");
/* 312 */       bf.newLine();
/* 313 */       bf.newLine();
/*     */     }
/* 315 */     bf.flush();
/* 316 */     bf.close();
/*     */   }
/*     */ 
/*     */   public static void SaveGroups(ArrayList<GateGroup> gg) throws Exception {
/* 320 */     BufferedWriter bf = new BufferedWriter(new FileWriter("plugins/CityGates/groups.yml"));
/* 321 */     bf.write("# CityGates - Groups");
/* 322 */     bf.newLine();
/* 323 */     bf.newLine();
/* 324 */     bf.write("# Only edit everything after the ':'");
/* 325 */     bf.newLine();
/* 326 */     bf.write("# There are 3 defirent parameters: value, String, boolean");
/* 327 */     bf.newLine();
/* 328 */     bf.write("# Do not leave any parameter black!");
/* 329 */     bf.newLine();
/* 330 */     bf.newLine();
/* 331 */     bf.write("# value: Any number without dots!");
/* 332 */     bf.newLine();
/* 333 */     bf.write("# String: Text");
/* 334 */     bf.newLine();
/* 335 */     bf.write("# Boolean: true/false");
/* 336 */     bf.newLine();
/* 337 */     bf.newLine();
/*     */ 
/* 339 */     for (int a = 0; a < gg.size(); a++) {
/* 340 */       GateGroup g = (GateGroup)gg.get(a);
/* 341 */       bf.write("Add Group: " + g.name);
/* 342 */       bf.newLine();
/* 343 */       bf.write("  Delay: " + g.delay);
/* 344 */       bf.newLine();
/* 345 */       bf.write("  World: " + g.world);
/* 346 */       bf.newLine();
/* 347 */       bf.write("  Open: " + g.open);
/* 348 */       bf.newLine();
/* 349 */       bf.write("  timeGate: " + g.timegate);
/* 350 */       bf.newLine();
/* 351 */       bf.write("  redstoneListener: " + g.redstoneListener);
/* 352 */       bf.newLine();
/* 353 */       bf.write("  mobKill: " + g.mobKill);
/* 354 */       bf.newLine();
/* 355 */       bf.write("  Button Listener: " + g.buttonListener);
/* 356 */       bf.newLine();
/* 357 */       if ((g.mob == null) || (g.mob.equals("")))
/* 358 */         bf.write("  Mob: testMob");
/*     */       else
/* 360 */         bf.write("  Mob: " + g.mob);
/* 361 */       bf.newLine();
/* 362 */       bf.write("  pr: " + g.pr[0] + ", " + g.pr[1] + ", " + g.pr[2]);
/* 363 */       bf.newLine();
/* 364 */       bf.write("  Kill Message: " + g.killMsg);
/* 365 */       bf.newLine();
/* 366 */       bf.write("  ButtonInterval: " + g.ButtonInterval);
/* 367 */       bf.newLine();
/* 368 */       bf.write("  Button: " + g.button[0] + ", " + g.button[1] + ", " + g.button[2]);
/* 369 */       bf.newLine();
/* 370 */       bf.write("  Children: ");
/* 371 */       bf.newLine();
/* 372 */       for (int b = 0; b < g.g.size(); b++) {
/*     */         try {
/* 374 */           Gate gate = (Gate)g.g.get(b);
/* 375 */           bf.write("    " + gate.gd.name);
/*     */         } catch (Exception e) {
/*     */           try {
/* 378 */             GateGroup group = (GateGroup)g.g.get(b);
/* 379 */             bf.write("    " + group.name); } catch (Exception ee) {
/*     */           }
/*     */         }
/* 382 */         bf.newLine();
/*     */       }
/* 384 */       bf.write("  End Children");
/* 385 */       bf.newLine();
/* 386 */       bf.write("End Group");
/* 387 */       bf.newLine();
/* 388 */       bf.newLine();
/*     */     }
/* 390 */     bf.flush();
/* 391 */     bf.close();
/*     */   }
/*     */ 
/*     */   public static ArrayList<GateData> Load() throws IOException, ClassNotFoundException {
/* 395 */     FileInputStream fis = new FileInputStream("plugins/CityGates/gates.yml");
/* 396 */     int lines = fis.available();
/* 397 */     if (lines == 0) {
/* 398 */       throw new IOException("File is empty");
/*     */     }
/* 400 */     ObjectInputStream in = new ObjectInputStream(fis);
/* 401 */     GateData[] gd = (GateData[])in.readObject();
/* 402 */     in.close();
/* 403 */     ArrayList dd = new ArrayList();
/* 404 */     for (int a = 0; a < gd.length; a++) {
/* 405 */       dd.add(gd[a]);
/*     */     }
/* 407 */     return dd;
/*     */   }
/*     */ 
/*     */   public static void Save(ArrayList<GateData> dd) throws IOException {
/* 411 */     GateData[] gd = new GateData[dd.size()];
/* 412 */     for (int a = 0; a < dd.size(); a++) {
/* 413 */       gd[a] = ((GateData)dd.get(a));
/*     */     }
/*     */ 
/* 416 */     FileOutputStream fos = new FileOutputStream("plugins/CityGates/gates.yml");
/* 417 */     ObjectOutputStream out = new ObjectOutputStream(fos);
/* 418 */     out.writeObject(gd);
/* 419 */     out.flush();
/* 420 */     out.close();
/*     */   }
/*     */ 
/*     */   public static void mdir() {
/*     */     try {
/* 425 */       File root = new File("plugins/CityGates/");
/* 426 */       root.mkdirs();
/* 427 */       File gates = new File("plugins/CityGates/gates.yml");
/* 428 */       gates.createNewFile();
/* 429 */       File groups = new File("plugins/CityGates/groups.yml");
/* 430 */       groups.createNewFile();
/*     */     } catch (Exception e) {
/* 432 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.Config
 * JD-Core Version:    0.6.2
 */