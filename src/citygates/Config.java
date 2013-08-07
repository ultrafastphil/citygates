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
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class Config
/*     */ {
/*     */   public static ArrayList<GateData> LoadGates(Plugin plugin)
/*     */     throws Exception
/*     */   {
/*  11 */     ArrayList gd = new ArrayList();
/*  12 */     ArrayList ignore = new ArrayList();
/*  13 */     FileInputStream fis = new FileInputStream("plugins/CityGates/gates.yml");
/*  14 */     int lines = fis.available();
/*  15 */     fis.close();
/*  16 */     if (lines == 0) {
/*  17 */       throw new IOException("File is empty");
/*     */     }
/*  19 */     BufferedReader bf = new BufferedReader(new FileReader("plugins/CityGates/gates.yml"));
/*     */ 
/*  21 */     int count = 0;
/*     */     String line;
/*  22 */     while ((line = bf.readLine()) != null) {
/*  23 */       count++;
/*  24 */       if (line.startsWith("Add Gate: ")) {
/*  25 */         GateData gate = new GateData();
/*  26 */         gate.name = line.split("Add Gate: ")[1];
/*  27 */         boolean p1 = false;
/*  28 */         boolean p2 = false;
/*  29 */         boolean World = false;
/*  30 */         while (!(line = bf.readLine()).equals("End Gate")) {
/*  31 */           count++;
/*     */           try {
/*  33 */             while (line.startsWith(" "))
/*  34 */               line = line.substring(1);
/*  35 */             if (line.startsWith("p1: ")) {
/*  36 */               line = line.split("p1: ")[1];
/*  37 */               String[] points = line.split(", ");
/*  38 */               if (points.length == 3)
/*     */                 try {
/*  40 */                   gate.p1[0] = Integer.parseInt(points[0]);
/*  41 */                   gate.p1[1] = Integer.parseInt(points[1]);
/*  42 */                   gate.p1[2] = Integer.parseInt(points[2]);
/*  43 */                   p1 = true;
/*     */                 } catch (Exception e) {
/*  45 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/*  48 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/*  50 */             else if (line.startsWith("p2: ")) {
/*  51 */               line = line.split("p2: ")[1];
/*  52 */               String[] points = line.split(", ");
/*  53 */               if (points.length == 3)
/*     */                 try {
/*  55 */                   gate.p2[0] = Integer.parseInt(points[0]);
/*  56 */                   gate.p2[1] = Integer.parseInt(points[1]);
/*  57 */                   gate.p2[2] = Integer.parseInt(points[2]);
/*  58 */                   p1 = true;
/*     */                 } catch (Exception e) {
/*  60 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/*  63 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/*  65 */             else if (line.startsWith("World: ")) {
/*     */               try {
/*  67 */                 plugin.getServer().getWorld(line.split("World: ")[1]);
/*  68 */                 gate.World = line.split("World: ")[1];
/*  69 */                 World = true;
/*     */               } catch (Exception e) {
/*  71 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/*  73 */             } else if (line.startsWith("Open: ")) {
/*  74 */               line = line.split("Open: ")[1];
/*  75 */               if (line.equalsIgnoreCase("true"))
/*  76 */                 gate.open = true;
/*  77 */               else if (line.equalsIgnoreCase("false"))
/*  78 */                 gate.open = false;
/*     */               else
/*  80 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/*  82 */             else if (line.startsWith("timeGate: ")) {
/*  83 */               line = line.split("timeGate: ")[1];
/*  84 */               if (line.equalsIgnoreCase("true"))
/*  85 */                 gate.timeGate = true;
/*  86 */               else if (line.equalsIgnoreCase("false"))
/*  87 */                 gate.timeGate = false;
/*     */               else
/*  89 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/*  91 */             else if (line.startsWith("redstoneListener: ")) {
/*  92 */               line = line.split("redstoneListener: ")[1];
/*  93 */               if (line.equalsIgnoreCase("true"))
/*  94 */                 gate.redstoneListener = true;
/*  95 */               else if (line.equalsIgnoreCase("false"))
/*  96 */                 gate.redstoneListener = false;
/*     */               else
/*  98 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 100 */             else if (line.startsWith("mobKill: ")) {
/* 101 */               line = line.split("mobKill: ")[1];
/* 102 */               if (line.equalsIgnoreCase("true"))
/* 103 */                 gate.mobKill = true;
/* 104 */               else if (line.equalsIgnoreCase("false"))
/* 105 */                 gate.mobKill = false;
/*     */               else
/* 107 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 109 */             else if (line.startsWith("Button Listener: ")) {
/* 110 */               line = line.split("Button Listener: ")[1];
/* 111 */               if (line.equalsIgnoreCase("true"))
/* 112 */                 gate.buttonListener = true;
/* 113 */               else if (line.equalsIgnoreCase("false"))
/* 114 */                 gate.buttonListener = false;
/*     */               else
/* 116 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 118 */             else if (line.startsWith("Mob: ")) {
/* 119 */               gate.mob = line.split("Mob: ")[1];
/* 120 */             } else if (line.startsWith("pr: ")) {
/* 121 */               line = line.split("pr: ")[1];
/* 122 */               String[] points = line.split(", ");
/* 123 */               if (points.length == 3)
/*     */                 try {
/* 125 */                   gate.pr[0] = Integer.parseInt(points[0]);
/* 126 */                   gate.pr[1] = Integer.parseInt(points[1]);
/* 127 */                   gate.pr[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception e) {
/* 129 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/* 132 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 134 */             else if (line.startsWith("Kill Message: ")) {
/* 135 */               gate.killMsg = line.split("Kill Message: ")[1];
/* 136 */             } else if (line.startsWith("Button: ")) {
/* 137 */               line = line.split("Button: ")[1];
/* 138 */               String[] points = line.split(", ");
/* 139 */               if (points.length == 3)
/*     */                 try {
/* 141 */                   gate.button[0] = Integer.parseInt(points[0]);
/* 142 */                   gate.button[1] = Integer.parseInt(points[1]);
/* 143 */                   gate.button[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception e) {
/*     */                 }
/* 146 */             } else if (line.startsWith("ButtonInterval: ")) {
/*     */               try {
/* 148 */                 gate.ButtonInterval = Integer.parseInt(line.split("ButtonInterval: ")[1]);
/*     */               } catch (Exception e) {
/* 150 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/* 152 */             } else if (line.startsWith("Button Permission: ")) {
/* 153 */               if (line.split("Button Permission: ")[1].equalsIgnoreCase("true"))
/* 154 */                 gate.buttonPerm = true;
/* 155 */               else if (line.split("Button Permission: ")[1].equalsIgnoreCase("false"))
/* 156 */                 gate.buttonPerm = false;
/*     */               else
/* 158 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 160 */             else if (line.startsWith("Kill Permission: ")) {
/* 161 */               if (line.split("Kill Permission: ")[1].equalsIgnoreCase("true"))
/* 162 */                 gate.killPerm = true;
/* 163 */               else if (line.split("Kill Permission: ")[1].equalsIgnoreCase("false"))
/* 164 */                 gate.killPerm = false;
/*     */               else
/* 166 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 168 */             else if (line.startsWith("Open Permission: ")) {
/* 169 */               if (line.split("Open Permission: ")[1].equalsIgnoreCase("true"))
/* 170 */                 gate.openPerm = true;
/* 171 */               else if (line.split("Open Permission: ")[1].equalsIgnoreCase("false"))
/* 172 */                 gate.openPerm = false;
/*     */               else
/* 174 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 176 */             else if (line.startsWith("Close Permission: ")) {
/* 177 */               if (line.split("Close Permission: ")[1].equalsIgnoreCase("true"))
/* 178 */                 gate.closePerm = true;
/* 179 */               else if (line.split("Close Permission: ")[1].equalsIgnoreCase("false"))
/* 180 */                 gate.closePerm = false;
/*     */               else
/* 182 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 184 */             else if (line.startsWith("Material1: ")) {
/* 185 */               if (line.contains(", ")) {
/* 186 */                 ArrayList m1 = new ArrayList();
/* 187 */                 String[] materials = line.substring("Material1: ".length()).split(", ");
/* 188 */                 for (int a = 0; a < materials.length; a++) {
/* 189 */                   String[] material = materials[a].split(":");
/* 190 */                   m1.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/*     */                 }
/* 192 */                 gate.materials1 = m1;
/*     */               } else {
/* 194 */                 ArrayList m1 = new ArrayList();
/* 195 */                 String[] material = line.substring("Material1: ".length()).split(":");
/* 196 */                 m1.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/* 197 */                 gate.materials1 = m1;
/*     */               }
/* 199 */             } else if (line.startsWith("Material2: ")) {
/* 200 */               if (line.contains(", ")) {
/* 201 */                 ArrayList m2 = new ArrayList();
/* 202 */                 String[] materials = line.substring("Material2: ".length()).split(", ");
/* 203 */                 for (int a = 0; a < materials.length; a++) {
/* 204 */                   String[] material = materials[a].split(":");
/* 205 */                   m2.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/*     */                 }
/* 207 */                 gate.materials2 = m2;
/*     */               } else {
/* 209 */                 ArrayList m2 = new ArrayList();
/* 210 */                 String[] material = line.substring("Material2: ".length()).split(":");
/* 211 */                 m2.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/* 212 */                 gate.materials2 = m2;
/*     */               }
/* 214 */             } else if ((!line.equals("")) && (!line.startsWith("#")) && (!line.equals("Redstone: ")) && (!line.equals("KillListener: ")) && (!line.equals("ButtonListener: ")) && (!line.equals("Permissions: ")))
/*     */             {
/* 216 */               ignore.add(Integer.valueOf(count));
/*     */             }
/*     */           } catch (Exception e) {
/* 219 */             ignore.add(Integer.valueOf(count));
/*     */           }
/*     */         }
/* 222 */         gd.add(gate);
/*     */       }
/*     */     }
/* 225 */     if (!ignore.isEmpty()) {
/* 226 */       String i = "";
/* 227 */       for (int a = 0; a < ignore.size(); a++) {
/* 228 */         if (a == 0)
/* 229 */           i = String.valueOf(ignore.get(a));
/*     */         else {
/* 231 */           i = i + ", " + String.valueOf(ignore.get(a));
/*     */         }
/*     */       }
/* 234 */       plugin.getLogger().warning("Loading Gates: Ignore Lines: " + i);
/*     */     }
/* 236 */     bf.close();
/* 237 */     return gd;
/*     */   }
/*     */ 
/*     */   public static ArrayList<GateGroup> LoadGroups(ArrayList<Gate> gd, Plugin p) throws Exception {
/* 241 */     ArrayList gg = new ArrayList();
/* 242 */     ArrayList ignore = new ArrayList();
/* 243 */     FileInputStream fis = new FileInputStream("plugins/CityGates/groups.yml");
/* 244 */     int lines = fis.available();
/* 245 */     fis.close();
/* 246 */     if (lines == 0) {
/* 247 */       throw new IOException("File is empty");
/*     */     }
/* 249 */     BufferedReader bf = new BufferedReader(new FileReader("plugins/CityGates/groups.yml"));
/*     */ 
/* 251 */     int count = 0;
/*     */     String line;
/* 252 */     while ((line = bf.readLine()) != null) {
/* 253 */       count++;
/* 254 */       if (line.startsWith("Add Group: ")) {
/* 255 */         GateGroup group = new GateGroup(line.split("Add Group: ")[1], p);
/* 256 */         while (!(line = bf.readLine()).equals("End Group")) {
/* 257 */           count++;
/*     */           try {
/* 259 */             while (line.startsWith(" ")) {
/* 260 */               line = line.substring(1);
/*     */             }
/* 262 */             if (line.startsWith("Delay: ")) {
/*     */               try {
/* 264 */                 group.setDelay(Integer.parseInt(line.split("Delay: ")[1]));
/*     */               } catch (Exception e) {
/* 266 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/* 268 */             } else if (line.startsWith("World: ")) {
/*     */               try {
/* 270 */                 p.getServer().getWorld(line.split("World: ")[1]);
/* 271 */                 group.world = line.split("World: ")[1];
/*     */               } catch (Exception e) {
/* 273 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/* 275 */             } else if (line.startsWith("Open: ")) {
/* 276 */               line = line.split("Open: ")[1];
/* 277 */               if (line.equalsIgnoreCase("true"))
/* 278 */                 group.open = true;
/* 279 */               else if (line.equalsIgnoreCase("false"))
/* 280 */                 group.open = false;
/*     */               else
/* 282 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 284 */             else if (line.startsWith("timeGate: ")) {
/* 285 */               line = line.split("timeGate: ")[1];
/* 286 */               if (line.equalsIgnoreCase("true"))
/* 287 */                 group.timegate = true;
/* 288 */               else if (line.equalsIgnoreCase("false"))
/* 289 */                 group.timegate = false;
/*     */               else
/* 291 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 293 */             else if (line.startsWith("redstoneListener: ")) {
/* 294 */               line = line.split("redstoneListener: ")[1];
/* 295 */               if (line.equalsIgnoreCase("true"))
/* 296 */                 group.redstoneListener = true;
/* 297 */               else if (line.equalsIgnoreCase("false"))
/* 298 */                 group.redstoneListener = false;
/*     */               else
/* 300 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 302 */             else if (line.startsWith("mobKill: ")) {
/* 303 */               line = line.split("mobKill: ")[1];
/* 304 */               if (line.equalsIgnoreCase("true"))
/* 305 */                 group.mobKill = true;
/* 306 */               else if (line.equalsIgnoreCase("false"))
/* 307 */                 group.mobKill = false;
/*     */               else
/* 309 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 311 */             else if (line.startsWith("Button Listener: ")) {
/* 312 */               line = line.split("Button Listener: ")[1];
/* 313 */               if (line.equalsIgnoreCase("true"))
/* 314 */                 group.buttonListener = true;
/* 315 */               else if (line.equalsIgnoreCase("false"))
/* 316 */                 group.buttonListener = false;
/*     */               else
/* 318 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 320 */             else if (line.startsWith("Mob: ")) {
/* 321 */               group.mob = line.split("Mob: ")[1];
/* 322 */             } else if (line.startsWith("pr: ")) {
/* 323 */               line = line.split("pr: ")[1];
/* 324 */               String[] points = line.split(", ");
/* 325 */               if (points.length == 3)
/*     */                 try {
/* 327 */                   group.pr[0] = Integer.parseInt(points[0]);
/* 328 */                   group.pr[1] = Integer.parseInt(points[1]);
/* 329 */                   group.pr[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception e) {
/* 331 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/* 334 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 336 */             else if (line.startsWith("Kill Message: ")) {
/* 337 */               group.killMsg = line.split("Kill Message: ")[1];
/* 338 */             } else if (line.startsWith("Button: ")) {
/* 339 */               line = line.split("Button: ")[1];
/* 340 */               String[] points = line.split(", ");
/* 341 */               if (points.length == 3)
/*     */                 try {
/* 343 */                   group.button[0] = Integer.parseInt(points[0]);
/* 344 */                   group.button[1] = Integer.parseInt(points[1]);
/* 345 */                   group.button[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception e) {
/* 347 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/* 350 */                 ignore.add(Integer.valueOf(count));
/*     */             } else {
/* 352 */               if (line.startsWith("Children: ")) {
/* 353 */                 while (!(line = bf.readLine()).equals("  End Children")) {
/* 354 */                   count++;
/* 355 */                   while (line.startsWith(" "))
/* 356 */                     line = line.substring(1);
/*     */                 }
/*     */               }
/* 359 */               if (line.startsWith("ButtonInterval: "))
/*     */                 try {
/* 361 */                   group.ButtonInterval = Integer.parseInt(line.split("ButtonInterval: ")[1]);
/*     */                 } catch (Exception e) {
/* 363 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/* 365 */               else if (line.startsWith("Button Permission: ")) {
/* 366 */                 if (line.split("Button Permission: ")[1].equalsIgnoreCase("true"))
/* 367 */                   group.buttonPerm = true;
/* 368 */                 else if (line.split("Button Permission: ")[1].equalsIgnoreCase("false"))
/* 369 */                   group.buttonPerm = false;
/*     */                 else
/* 371 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 373 */               else if (line.startsWith("Kill Permission: ")) {
/* 374 */                 if (line.split("Kill Permission: ")[1].equalsIgnoreCase("true"))
/* 375 */                   group.killPerm = true;
/* 376 */                 else if (line.split("Kill Permission: ")[1].equalsIgnoreCase("false"))
/* 377 */                   group.killPerm = false;
/*     */                 else
/* 379 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 381 */               else if (line.startsWith("Open Permission: ")) {
/* 382 */                 if (line.split("Open Permission: ")[1].equalsIgnoreCase("true"))
/* 383 */                   group.openPerm = true;
/* 384 */                 else if (line.split("Open Permission: ")[1].equalsIgnoreCase("false"))
/* 385 */                   group.openPerm = false;
/*     */                 else
/* 387 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 389 */               else if (line.startsWith("Close Permission: ")) {
/* 390 */                 if (line.split("Close Permission: ")[1].equalsIgnoreCase("true"))
/* 391 */                   group.closePerm = true;
/* 392 */                 else if (line.split("Close Permission: ")[1].equalsIgnoreCase("false"))
/* 393 */                   group.closePerm = false;
/*     */                 else
/* 395 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 397 */               else if ((!line.equals("")) && (!line.startsWith("#")) && (!line.equals("Redstone: ")) && (!line.equals("KillListener: ")) && (!line.equals("ButtonListener: ")) && (!line.equals("Permissions: ")))
/*     */               {
/* 399 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/*     */             }
/*     */           } catch (Exception e) { ignore.add(Integer.valueOf(count)); }
/*     */ 
/*     */         }
/* 405 */         gg.add(group);
/*     */       }
/*     */     }
/* 408 */     bf.close();
/*     */ 
/* 410 */     int index = 0;
/* 411 */     bf = new BufferedReader(new FileReader("plugins/CityGates/groups.yml"));
/* 412 */     count = 0;
/* 413 */     while ((line = bf.readLine()) != null) {
/* 414 */       count++;
/* 415 */       if (line.startsWith("Add Group: ")) {
/* 416 */         GateGroup group = (GateGroup)gg.get(index);
/* 417 */         index++;
/* 418 */         while (!(line = bf.readLine()).equals("End Group")) {
/* 419 */           count++;
/*     */           try {
/* 421 */             while (line.startsWith(" ")) {
/* 422 */               line = line.substring(1);
/*     */             }
/* 424 */             if (line.startsWith("Children: "))
/* 425 */               while (!(line = bf.readLine()).equals("  End Children")) {
/* 426 */                 while (line.startsWith(" ")) {
/* 427 */                   line = line.substring(1);
/*     */                 }
/* 429 */                 boolean done = false;
/* 430 */                 for (int a = 0; a < gd.size(); a++) {
/* 431 */                   if (((Gate)gd.get(a)).gd.name.equals(line)) {
/* 432 */                     group.add((Gate)gd.get(a));
/* 433 */                     done = true;
/*     */                   }
/*     */                 }
/* 436 */                 for (int a = 0; a < gg.size(); a++) {
/* 437 */                   if ((((GateGroup)gg.get(a)).name.equals(line)) && (!line.equals(group.name))) {
/* 438 */                     group.add((GateGroup)gg.get(a));
/* 439 */                     done = true;
/*     */                   }
/*     */                 }
/* 442 */                 if (!done)
/* 443 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 448 */             ignore.add(Integer.valueOf(count));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 453 */     bf.close();
/* 454 */     if (!ignore.isEmpty()) {
/* 455 */       String i = "";
/* 456 */       for (int a = 0; a < ignore.size(); a++) {
/* 457 */         if (a == 0)
/* 458 */           i = String.valueOf(ignore.get(a));
/*     */         else {
/* 460 */           i = i + ", " + String.valueOf(ignore.get(a));
/*     */         }
/*     */       }
/* 463 */       p.getLogger().warning("Loading Groups: Ignore Lines: " + i);
/*     */     }
/* 465 */     return gg;
/*     */   }
/*     */ 
/*     */   public static void SaveGates(ArrayList<GateData> gd) throws Exception {
/* 469 */     BufferedWriter bf = new BufferedWriter(new FileWriter("plugins/CityGates/gates.yml"));
/* 470 */     bf.write("# CityGates - Gates");
/* 471 */     bf.newLine();
/* 472 */     bf.newLine();
/* 473 */     bf.write("# Only edit everything after the ':'");
/* 474 */     bf.newLine();
/* 475 */     bf.write("# There are 3 defirent parameters: value, String, boolean");
/* 476 */     bf.newLine();
/* 477 */     bf.write("# Do not leave any parameter black!");
/* 478 */     bf.newLine();
/* 479 */     bf.newLine();
/* 480 */     bf.write("# value: Any number without dots!");
/* 481 */     bf.newLine();
/* 482 */     bf.write("# String: Text");
/* 483 */     bf.newLine();
/* 484 */     bf.write("# Boolean: true/false");
/* 485 */     bf.newLine();
/* 486 */     bf.newLine();
/*     */ 
/* 488 */     for (int a = 0; a < gd.size(); a++) {
/* 489 */       GateData g = (GateData)gd.get(a);
/* 490 */       bf.write("Add Gate: " + g.name);
/* 491 */       bf.newLine();
/* 492 */       bf.write("  p1: " + g.p1[0] + ", " + g.p1[1] + ", " + g.p1[2]);
/* 493 */       bf.newLine();
/* 494 */       bf.write("  p2: " + g.p2[0] + ", " + g.p2[1] + ", " + g.p2[2]);
/* 495 */       bf.newLine();
/* 496 */       bf.write("  World: " + g.World);
/* 497 */       bf.newLine();
/* 498 */       bf.write("  Open: " + g.open);
/* 499 */       bf.newLine();
/* 500 */       bf.write("  timeGate: " + g.timeGate);
/* 501 */       bf.newLine();
/* 502 */       bf.write("  Redstone: ");
/* 503 */       bf.newLine();
/* 504 */       bf.write("    redstoneListener: " + g.redstoneListener);
/* 505 */       bf.newLine();
/* 506 */       bf.write("    pr: " + g.pr[0] + ", " + g.pr[1] + ", " + g.pr[2]);
/* 507 */       bf.newLine();
/* 508 */       bf.write("  KillListener: ");
/* 509 */       bf.newLine();
/* 510 */       bf.write("    mobKill: " + g.mobKill);
/* 511 */       bf.newLine();
/* 512 */       if ((g.mob == null) || (g.mob.equals("")))
/* 513 */         bf.write("    Mob: testMob");
/*     */       else
/* 515 */         bf.write("    Mob: " + g.mob);
/* 516 */       bf.newLine();
/* 517 */       bf.write("    Kill Message: " + g.killMsg);
/* 518 */       bf.newLine();
/* 519 */       bf.write("  ButtonListener: ");
/* 520 */       bf.newLine();
/* 521 */       bf.write("    Button Listener: " + g.buttonListener);
/* 522 */       bf.newLine();
/* 523 */       bf.write("    ButtonInterval: " + g.ButtonInterval);
/* 524 */       bf.newLine();
/* 525 */       bf.write("    Button: " + g.button[0] + ", " + g.button[1] + ", " + g.button[2]);
/* 526 */       bf.newLine();
/* 527 */       bf.write("  Permissions: ");
/* 528 */       bf.newLine();
/* 529 */       bf.write("    Button Permission: " + g.buttonPerm);
/* 530 */       bf.newLine();
/* 531 */       bf.write("    Kill Permission: " + g.killPerm);
/* 532 */       bf.newLine();
/* 533 */       bf.write("    Open Permission: " + g.openPerm);
/* 534 */       bf.newLine();
/* 535 */       bf.write("    Close Permission: " + g.closePerm);
/* 536 */       bf.newLine();
/* 537 */       bf.write("  Material1: " + ((MaterialId)g.materials1.get(0)).getID() + ":" + ((MaterialId)g.materials1.get(0)).getData());
/* 538 */       if (g.materials1.size() >= 2) {
/* 539 */         for (int b = 1; b < g.materials1.size(); b++) {
/* 540 */           bf.write(", " + ((MaterialId)g.materials1.get(b)).getID() + ":" + ((MaterialId)g.materials1.get(b)).getData());
/*     */         }
/*     */       }
/* 543 */       bf.newLine();
/* 544 */       bf.write("  Material2: " + ((MaterialId)g.materials2.get(0)).getID() + ":" + ((MaterialId)g.materials2.get(0)).getData());
/* 545 */       if (g.materials2.size() >= 2) {
/* 546 */         for (int b = 1; b < g.materials2.size(); b++) {
/* 547 */           bf.write(", " + ((MaterialId)g.materials2.get(b)).getID() + ":" + ((MaterialId)g.materials2.get(b)).getData());
/*     */         }
/*     */       }
/* 550 */       bf.newLine();
/* 551 */       bf.write("End Gate");
/* 552 */       bf.newLine();
/* 553 */       bf.newLine();
/*     */     }
/* 555 */     bf.flush();
/* 556 */     bf.close();
/*     */   }
/*     */ 
/*     */   public static void SaveGroups(ArrayList<GateGroup> gg) throws Exception {
/* 560 */     BufferedWriter bf = new BufferedWriter(new FileWriter("plugins/CityGates/groups.yml"));
/* 561 */     bf.write("# CityGates - Groups");
/* 562 */     bf.newLine();
/* 563 */     bf.newLine();
/* 564 */     bf.write("# Only edit everything after the ':'");
/* 565 */     bf.newLine();
/* 566 */     bf.write("# There are 3 defirent parameters: value, String, boolean");
/* 567 */     bf.newLine();
/* 568 */     bf.write("# Do not leave any parameter black!");
/* 569 */     bf.newLine();
/* 570 */     bf.newLine();
/* 571 */     bf.write("# value: Any number without dots!");
/* 572 */     bf.newLine();
/* 573 */     bf.write("# String: Text");
/* 574 */     bf.newLine();
/* 575 */     bf.write("# Boolean: true/false");
/* 576 */     bf.newLine();
/* 577 */     bf.newLine();
/*     */ 
/* 579 */     for (int a = 0; a < gg.size(); a++) {
/* 580 */       GateGroup g = (GateGroup)gg.get(a);
/* 581 */       bf.write("Add Group: " + g.name);
/* 582 */       bf.newLine();
/* 583 */       bf.write("  Delay: " + g.delay);
/* 584 */       bf.newLine();
/* 585 */       bf.write("  World: " + g.world);
/* 586 */       bf.newLine();
/* 587 */       bf.write("  Open: " + g.open);
/* 588 */       bf.newLine();
/* 589 */       bf.write("  timeGate: " + g.timegate);
/* 590 */       bf.newLine();
/* 591 */       bf.write("  Redstone: ");
/* 592 */       bf.newLine();
/* 593 */       bf.write("    redstoneListener: " + g.redstoneListener);
/* 594 */       bf.newLine();
/* 595 */       bf.write("    pr: " + g.pr[0] + ", " + g.pr[1] + ", " + g.pr[2]);
/* 596 */       bf.newLine();
/* 597 */       bf.write("  KillListener: ");
/* 598 */       bf.newLine();
/* 599 */       bf.write("    mobKill: " + g.mobKill);
/* 600 */       bf.newLine();
/* 601 */       if ((g.mob == null) || (g.mob.equals("")))
/* 602 */         bf.write("    Mob: testMob");
/*     */       else
/* 604 */         bf.write("    Mob: " + g.mob);
/* 605 */       bf.newLine();
/* 606 */       bf.write("    Kill Message: " + g.killMsg);
/* 607 */       bf.newLine();
/* 608 */       bf.write("  ButtonListener: ");
/* 609 */       bf.newLine();
/* 610 */       bf.write("    Button Listener: " + g.buttonListener);
/* 611 */       bf.newLine();
/* 612 */       bf.write("    ButtonInterval: " + g.ButtonInterval);
/* 613 */       bf.newLine();
/* 614 */       bf.write("    Button: " + g.button[0] + ", " + g.button[1] + ", " + g.button[2]);
/* 615 */       bf.newLine();
/* 616 */       bf.write("  Permissions: ");
/* 617 */       bf.newLine();
/* 618 */       bf.write("    Button Permission: " + g.buttonPerm);
/* 619 */       bf.newLine();
/* 620 */       bf.write("    Kill Permission: " + g.killPerm);
/* 621 */       bf.newLine();
/* 622 */       bf.write("    Open Permission: " + g.openPerm);
/* 623 */       bf.newLine();
/* 624 */       bf.write("    Close Permission: " + g.closePerm);
/* 625 */       bf.newLine();
/* 626 */       bf.write("  Children: ");
/* 627 */       bf.newLine();
/* 628 */       for (int b = 0; b < g.g.size(); b++) {
/*     */         try {
/* 630 */           Gate gate = (Gate)g.g.get(b);
/* 631 */           bf.write("    " + gate.gd.name);
/*     */         } catch (Exception e) {
/*     */           try {
/* 634 */             GateGroup group = (GateGroup)g.g.get(b);
/* 635 */             bf.write("    " + group.name); } catch (Exception ee) {
/*     */           }
/*     */         }
/* 638 */         bf.newLine();
/*     */       }
/* 640 */       bf.write("  End Children");
/* 641 */       bf.newLine();
/* 642 */       bf.write("End Group");
/* 643 */       bf.newLine();
/* 644 */       bf.newLine();
/*     */     }
/* 646 */     bf.flush();
/* 647 */     bf.close();
/*     */   }
/*     */ 
/*     */   public static ArrayList<GateData> Load() throws IOException, ClassNotFoundException {
/* 651 */     FileInputStream fis = new FileInputStream("plugins/CityGates/gates.yml");
/* 652 */     int lines = fis.available();
/* 653 */     fis.close();
/* 654 */     if (lines == 0) {
/* 655 */       throw new IOException("File is empty");
/*     */     }
/* 657 */     ObjectInputStream in = new ObjectInputStream(fis);
/* 658 */     GateData[] gd = (GateData[])in.readObject();
/* 659 */     in.close();
/* 660 */     ArrayList dd = new ArrayList();
/* 661 */     for (int a = 0; a < gd.length; a++) {
/* 662 */       dd.add(gd[a]);
/*     */     }
/* 664 */     return dd;
/*     */   }
/*     */ 
/*     */   public static void Save(ArrayList<GateData> dd) throws IOException {
/* 668 */     GateData[] gd = new GateData[dd.size()];
/* 669 */     for (int a = 0; a < dd.size(); a++) {
/* 670 */       gd[a] = ((GateData)dd.get(a));
/*     */     }
/*     */ 
/* 673 */     FileOutputStream fos = new FileOutputStream("plugins/CityGates/gates.yml");
/* 674 */     ObjectOutputStream out = new ObjectOutputStream(fos);
/* 675 */     out.writeObject(gd);
/* 676 */     out.flush();
/* 677 */     fos.close();
/* 678 */     out.close();
/*     */   }
/*     */ 
/*     */   public static void mdir() {
/*     */     try {
/* 683 */       File root = new File("plugins/CityGates/");
/* 684 */       root.mkdirs();
/* 685 */       File gates = new File("plugins/CityGates/gates.yml");
/* 686 */       gates.createNewFile();
/* 687 */       File groups = new File("plugins/CityGates/groups.yml");
/* 688 */       groups.createNewFile();
/*     */     } catch (Exception e) {
/* 690 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.Config
 * JD-Core Version:    0.6.2
 */