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
/*     */ import javax.swing.JOptionPane;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class Config
/*     */ {
/*     */   public static ArrayList<GateData> LoadGates(Plugin plugin)
/*     */     throws Exception
/*     */   {
/*  26 */     ArrayList gd = new ArrayList();
/*  27 */     ArrayList ignore = new ArrayList();
/*  28 */     FileInputStream fis = new FileInputStream("plugins/CityGates/gates.yml");
/*  29 */     int lines = fis.available();
/*  30 */     fis.close();
/*  31 */     if (lines == 0) {
/*  32 */       throw new IOException("File is empty");
/*     */     }
/*  34 */     BufferedReader bf = new BufferedReader(new FileReader("plugins/CityGates/gates.yml"));
/*     */ 
/*  36 */     int count = 0;
/*     */     String line;
/*  38 */     while ((line = bf.readLine()) != null)
/*     */     {
/*     */       String line;
/*  39 */       count++;
/*  40 */       if (line.startsWith("Add Gate: ")) {
/*  41 */         GateData gate = new GateData();
/*  42 */         gate.name = line.split("Add Gate: ")[1];
/*  43 */         boolean p1 = false;
/*  44 */         boolean p2 = false;
/*  45 */         boolean World = false;
/*  46 */         while (!(line = bf.readLine()).equals("End Gate")) {
/*  47 */           count++;
/*     */           try {
/*  49 */             while (line.startsWith(" "))
/*  50 */               line = line.substring(1);
/*  51 */             if (line.startsWith("p1: ")) {
/*  52 */               line = line.split("p1: ")[1];
/*  53 */               String[] points = line.split(", ");
/*  54 */               if (points.length == 3)
/*     */                 try {
/*  56 */                   gate.p1[0] = Integer.parseInt(points[0]);
/*  57 */                   gate.p1[1] = Integer.parseInt(points[1]);
/*  58 */                   gate.p1[2] = Integer.parseInt(points[2]);
/*  59 */                   p1 = true;
/*     */                 } catch (Exception e) {
/*  61 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/*  64 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/*  66 */             else if (line.startsWith("p2: ")) {
/*  67 */               line = line.split("p2: ")[1];
/*  68 */               String[] points = line.split(", ");
/*  69 */               if (points.length == 3)
/*     */                 try {
/*  71 */                   gate.p2[0] = Integer.parseInt(points[0]);
/*  72 */                   gate.p2[1] = Integer.parseInt(points[1]);
/*  73 */                   gate.p2[2] = Integer.parseInt(points[2]);
/*  74 */                   p1 = true;
/*     */                 } catch (Exception e) {
/*  76 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/*  79 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/*  81 */             else if (line.startsWith("World: ")) {
/*     */               try {
/*  83 */                 plugin.getServer().getWorld(line.split("World: ")[1]);
/*  84 */                 gate.World = line.split("World: ")[1];
/*  85 */                 World = true;
/*     */               } catch (Exception e) {
/*  87 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/*  89 */             } else if (line.startsWith("Open: ")) {
/*  90 */               line = line.split("Open: ")[1];
/*  91 */               if (line.equalsIgnoreCase("true"))
/*  92 */                 gate.open = true;
/*  93 */               else if (line.equalsIgnoreCase("false"))
/*  94 */                 gate.open = false;
/*     */               else
/*  96 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/*  98 */             else if (line.startsWith("timeGate: ")) {
/*  99 */               line = line.split("timeGate: ")[1];
/* 100 */               if (line.equalsIgnoreCase("true"))
/* 101 */                 gate.timeGate = true;
/* 102 */               else if (line.equalsIgnoreCase("false"))
/* 103 */                 gate.timeGate = false;
/*     */               else
/* 105 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 107 */             else if (line.startsWith("redstoneListener: ")) {
/* 108 */               line = line.split("redstoneListener: ")[1];
/* 109 */               if (line.equalsIgnoreCase("true"))
/* 110 */                 gate.redstoneListener = true;
/* 111 */               else if (line.equalsIgnoreCase("false"))
/* 112 */                 gate.redstoneListener = false;
/*     */               else
/* 114 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 116 */             else if (line.startsWith("mobKill: ")) {
/* 117 */               line = line.split("mobKill: ")[1];
/* 118 */               if (line.equalsIgnoreCase("true"))
/* 119 */                 gate.mobKill = true;
/* 120 */               else if (line.equalsIgnoreCase("false"))
/* 121 */                 gate.mobKill = false;
/*     */               else
/* 123 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 125 */             else if (line.startsWith("Button Listener: ")) {
/* 126 */               line = line.split("Button Listener: ")[1];
/* 127 */               if (line.equalsIgnoreCase("true"))
/* 128 */                 gate.buttonListener = true;
/* 129 */               else if (line.equalsIgnoreCase("false"))
/* 130 */                 gate.buttonListener = false;
/*     */               else
/* 132 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 134 */             else if (line.startsWith("Mob: ")) {
/* 135 */               gate.mob = line.split("Mob: ")[1];
/* 136 */             } else if (line.startsWith("pr: ")) {
/* 137 */               line = line.split("pr: ")[1];
/* 138 */               String[] points = line.split(", ");
/* 139 */               if (points.length == 3)
/*     */                 try {
/* 141 */                   gate.pr[0] = Integer.parseInt(points[0]);
/* 142 */                   gate.pr[1] = Integer.parseInt(points[1]);
/* 143 */                   gate.pr[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception e) {
/* 145 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/* 148 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 150 */             else if (line.startsWith("Kill Message: ")) {
/* 151 */               gate.killMsg = line.split("Kill Message: ")[1];
/* 152 */             } else if (line.startsWith("Button: ")) {
/* 153 */               line = line.split("Button: ")[1];
/* 154 */               String[] points = line.split(", ");
/* 155 */               if (points.length == 3)
/*     */                 try {
/* 157 */                   gate.button[0] = Integer.parseInt(points[0]);
/* 158 */                   gate.button[1] = Integer.parseInt(points[1]);
/* 159 */                   gate.button[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception localException1) {
/*     */                 }
/* 162 */             } else if (line.startsWith("ButtonInterval: ")) {
/*     */               try {
/* 164 */                 gate.ButtonInterval = Integer.parseInt(line.split("ButtonInterval: ")[1]);
/*     */               } catch (Exception e) {
/* 166 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/* 168 */             } else if (line.startsWith("Button Permission: ")) {
/* 169 */               if (line.split("Button Permission: ")[1].equalsIgnoreCase("true"))
/* 170 */                 gate.buttonPerm = true;
/* 171 */               else if (line.split("Button Permission: ")[1].equalsIgnoreCase("false"))
/* 172 */                 gate.buttonPerm = false;
/*     */               else
/* 174 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 176 */             else if (line.startsWith("Kill Permission: ")) {
/* 177 */               if (line.split("Kill Permission: ")[1].equalsIgnoreCase("true"))
/* 178 */                 gate.killPerm = true;
/* 179 */               else if (line.split("Kill Permission: ")[1].equalsIgnoreCase("false"))
/* 180 */                 gate.killPerm = false;
/*     */               else
/* 182 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 184 */             else if (line.startsWith("Open Permission: ")) {
/* 185 */               if (line.split("Open Permission: ")[1].equalsIgnoreCase("true"))
/* 186 */                 gate.openPerm = true;
/* 187 */               else if (line.split("Open Permission: ")[1].equalsIgnoreCase("false"))
/* 188 */                 gate.openPerm = false;
/*     */               else
/* 190 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 192 */             else if (line.startsWith("Close Permission: ")) {
/* 193 */               if (line.split("Close Permission: ")[1].equalsIgnoreCase("true"))
/* 194 */                 gate.closePerm = true;
/* 195 */               else if (line.split("Close Permission: ")[1].equalsIgnoreCase("false"))
/* 196 */                 gate.closePerm = false;
/*     */               else
/* 198 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 200 */             else if (line.startsWith("Material1: ")) {
/* 201 */               if (line.contains(", ")) {
/* 202 */                 ArrayList m1 = new ArrayList();
/* 203 */                 String[] materials = line.substring("Material1: ".length()).split(", ");
/* 204 */                 for (int a = 0; a < materials.length; a++) {
/* 205 */                   String[] material = materials[a].split(":");
/* 206 */                   m1.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/*     */                 }
/* 208 */                 gate.materials1 = m1;
/*     */               } else {
/* 210 */                 ArrayList m1 = new ArrayList();
/* 211 */                 String[] material = line.substring("Material1: ".length()).split(":");
/* 212 */                 m1.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/* 213 */                 gate.materials1 = m1;
/*     */               }
/* 215 */             } else if (line.startsWith("Material2: ")) {
/* 216 */               if (line.contains(", ")) {
/* 217 */                 ArrayList m2 = new ArrayList();
/* 218 */                 String[] materials = line.substring("Material2: ".length()).split(", ");
/* 219 */                 for (int a = 0; a < materials.length; a++) {
/* 220 */                   String[] material = materials[a].split(":");
/* 221 */                   m2.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/*     */                 }
/* 223 */                 gate.materials2 = m2;
/*     */               } else {
/* 225 */                 ArrayList m2 = new ArrayList();
/* 226 */                 String[] material = line.substring("Material2: ".length()).split(":");
/* 227 */                 m2.add(new MaterialId(Integer.parseInt(material[0]), Byte.parseByte(material[1])));
/* 228 */                 gate.materials2 = m2;
/*     */               }
/* 230 */             } else if ((!line.equals("")) && (!line.startsWith("#")) && (!line.equals("Redstone: ")) && (!line.equals("KillListener: ")) && (!line.equals("ButtonListener: ")) && (!line.equals("Permissions: ")))
/*     */             {
/* 232 */               ignore.add(Integer.valueOf(count));
/*     */             }
/*     */           } catch (Exception e) {
/* 235 */             ignore.add(Integer.valueOf(count));
/*     */           }
/*     */         }
/* 238 */         gd.add(gate);
/*     */       }
/*     */     }
/* 241 */     if (!ignore.isEmpty()) {
/* 242 */       String i = "";
/* 243 */       for (int a = 0; a < ignore.size(); a++) {
/* 244 */         if (a == 0)
/* 245 */           i = String.valueOf(ignore.get(a));
/*     */         else {
/* 247 */           i = i + ", " + String.valueOf(ignore.get(a));
/*     */         }
/*     */       }
/* 250 */       plugin.getLogger().warning("Loading Gates: Ignore Lines: " + i);
/*     */     }
/* 252 */     bf.close();
/* 253 */     return gd;
/*     */   }
/*     */ 
/*     */   public static ArrayList<GateGroup> LoadGroups(ArrayList<Gate> gd, Plugin p) throws Exception {
/* 257 */     ArrayList gg = new ArrayList();
/* 258 */     ArrayList ignore = new ArrayList();
/* 259 */     FileInputStream fis = new FileInputStream("plugins/CityGates/groups.yml");
/* 260 */     int lines = fis.available();
/* 261 */     fis.close();
/* 262 */     if (lines == 0) {
/* 263 */       throw new IOException("File is empty");
/*     */     }
/* 265 */     BufferedReader bf = new BufferedReader(new FileReader("plugins/CityGates/groups.yml"));
/*     */ 
/* 267 */     int count = 0;
/*     */     String line;
/* 269 */     while ((line = bf.readLine()) != null)
/*     */     {
/*     */       String line;
/* 270 */       count++;
/* 271 */       if (line.startsWith("Add Group: ")) {
/* 272 */         GateGroup group = new GateGroup(line.split("Add Group: ")[1], p);
/* 273 */         while (!(line = bf.readLine()).equals("End Group")) {
/* 274 */           count++;
/*     */           try {
/* 276 */             while (line.startsWith(" ")) {
/* 277 */               line = line.substring(1);
/*     */             }
/* 279 */             if (line.startsWith("Delay: ")) {
/*     */               try {
/* 281 */                 group.setDelay(Integer.parseInt(line.split("Delay: ")[1]));
/*     */               } catch (Exception e) {
/* 283 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/* 285 */             } else if (line.startsWith("World: ")) {
/*     */               try {
/* 287 */                 p.getServer().getWorld(line.split("World: ")[1]);
/* 288 */                 group.world = line.split("World: ")[1];
/*     */               } catch (Exception e) {
/* 290 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/* 292 */             } else if (line.startsWith("Open: ")) {
/* 293 */               line = line.split("Open: ")[1];
/* 294 */               if (line.equalsIgnoreCase("true"))
/* 295 */                 group.open = true;
/* 296 */               else if (line.equalsIgnoreCase("false"))
/* 297 */                 group.open = false;
/*     */               else
/* 299 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 301 */             else if (line.startsWith("timeGate: ")) {
/* 302 */               line = line.split("timeGate: ")[1];
/* 303 */               if (line.equalsIgnoreCase("true"))
/* 304 */                 group.timegate = true;
/* 305 */               else if (line.equalsIgnoreCase("false"))
/* 306 */                 group.timegate = false;
/*     */               else
/* 308 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 310 */             else if (line.startsWith("redstoneListener: ")) {
/* 311 */               line = line.split("redstoneListener: ")[1];
/* 312 */               if (line.equalsIgnoreCase("true"))
/* 313 */                 group.redstoneListener = true;
/* 314 */               else if (line.equalsIgnoreCase("false"))
/* 315 */                 group.redstoneListener = false;
/*     */               else
/* 317 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 319 */             else if (line.startsWith("mobKill: ")) {
/* 320 */               line = line.split("mobKill: ")[1];
/* 321 */               if (line.equalsIgnoreCase("true"))
/* 322 */                 group.mobKill = true;
/* 323 */               else if (line.equalsIgnoreCase("false"))
/* 324 */                 group.mobKill = false;
/*     */               else
/* 326 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 328 */             else if (line.startsWith("Button Listener: ")) {
/* 329 */               line = line.split("Button Listener: ")[1];
/* 330 */               if (line.equalsIgnoreCase("true"))
/* 331 */                 group.buttonListener = true;
/* 332 */               else if (line.equalsIgnoreCase("false"))
/* 333 */                 group.buttonListener = false;
/*     */               else
/* 335 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 337 */             else if (line.startsWith("Mob: ")) {
/* 338 */               group.mob = line.split("Mob: ")[1];
/* 339 */             } else if (line.startsWith("pr: ")) {
/* 340 */               line = line.split("pr: ")[1];
/* 341 */               String[] points = line.split(", ");
/* 342 */               if (points.length == 3)
/*     */                 try {
/* 344 */                   group.pr[0] = Integer.parseInt(points[0]);
/* 345 */                   group.pr[1] = Integer.parseInt(points[1]);
/* 346 */                   group.pr[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception e) {
/* 348 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/* 351 */                 ignore.add(Integer.valueOf(count));
/*     */             }
/* 353 */             else if (line.startsWith("Kill Message: ")) {
/* 354 */               group.killMsg = line.split("Kill Message: ")[1];
/* 355 */             } else if (line.startsWith("Button: ")) {
/* 356 */               line = line.split("Button: ")[1];
/* 357 */               String[] points = line.split(", ");
/* 358 */               if (points.length == 3)
/*     */                 try {
/* 360 */                   group.button[0] = Integer.parseInt(points[0]);
/* 361 */                   group.button[1] = Integer.parseInt(points[1]);
/* 362 */                   group.button[2] = Integer.parseInt(points[2]);
/*     */                 } catch (Exception e) {
/* 364 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/*     */               else
/* 367 */                 ignore.add(Integer.valueOf(count));
/*     */             } else {
/* 369 */               if (line.startsWith("Children: ")) {
/* 370 */                 for (; !(line = bf.readLine()).equals(" End Children"); 
/* 372 */                   line.startsWith(" "))
/*     */                 {
/* 371 */                   count++;
/* 372 */                   continue;
/* 373 */                   line = line.substring(1);
/*     */                 }
/*     */               }
/* 376 */               if (line.startsWith("ButtonInterval: "))
/*     */                 try {
/* 378 */                   group.ButtonInterval = Integer.parseInt(line.split("ButtonInterval: ")[1]);
/*     */                 } catch (Exception e) {
/* 380 */                   ignore.add(Integer.valueOf(count));
/*     */                 }
/* 382 */               else if (line.startsWith("Button Permission: ")) {
/* 383 */                 if (line.split("Button Permission: ")[1].equalsIgnoreCase("true"))
/* 384 */                   group.buttonPerm = true;
/* 385 */                 else if (line.split("Button Permission: ")[1].equalsIgnoreCase("false"))
/* 386 */                   group.buttonPerm = false;
/*     */                 else
/* 388 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 390 */               else if (line.startsWith("Kill Permission: ")) {
/* 391 */                 if (line.split("Kill Permission: ")[1].equalsIgnoreCase("true"))
/* 392 */                   group.killPerm = true;
/* 393 */                 else if (line.split("Kill Permission: ")[1].equalsIgnoreCase("false"))
/* 394 */                   group.killPerm = false;
/*     */                 else
/* 396 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 398 */               else if (line.startsWith("Open Permission: ")) {
/* 399 */                 if (line.split("Open Permission: ")[1].equalsIgnoreCase("true"))
/* 400 */                   group.openPerm = true;
/* 401 */                 else if (line.split("Open Permission: ")[1].equalsIgnoreCase("false"))
/* 402 */                   group.openPerm = false;
/*     */                 else
/* 404 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 406 */               else if (line.startsWith("Close Permission: ")) {
/* 407 */                 if (line.split("Close Permission: ")[1].equalsIgnoreCase("true"))
/* 408 */                   group.closePerm = true;
/* 409 */                 else if (line.split("Close Permission: ")[1].equalsIgnoreCase("false"))
/* 410 */                   group.closePerm = false;
/*     */                 else
/* 412 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/* 414 */               else if ((!line.equals("")) && (!line.startsWith("#")) && (!line.equals("Redstone: ")) && (!line.equals("KillListener: ")) && (!line.equals("ButtonListener: ")) && (!line.equals("Permissions: ")))
/*     */               {
/* 416 */                 ignore.add(Integer.valueOf(count));
/*     */               }
/*     */             }
/*     */           } catch (Exception e) {
/* 420 */             ignore.add(Integer.valueOf(count));
/*     */           }
/*     */         }
/* 423 */         gg.add(group);
/*     */       }
/*     */     }
/* 426 */     bf.close();
/*     */ 
/* 428 */     int index = 0;
/* 429 */     bf = new BufferedReader(new FileReader("plugins/CityGates/groups.yml"));
/* 430 */     count = 0;
/* 431 */     while ((line = bf.readLine()) != null) {
/* 432 */       count++;
/* 433 */       if (line.startsWith("Add Group: ")) {
/* 434 */         GateGroup group = (GateGroup)gg.get(index);
/* 435 */         index++;
/* 436 */         while (!(line = bf.readLine()).equals("End Group")) {
/* 437 */           count++;
/*     */           try {
/* 439 */             while (line.startsWith(" ")) {
/* 440 */               line = line.substring(1);
/*     */             }
/* 442 */             if (line.startsWith("Children: "))
/* 443 */               while (!(line = bf.readLine()).equals(" End Children")) {
/* 444 */                 while (line.startsWith(" ")) {
/* 445 */                   line = line.substring(1);
/*     */                 }
/* 447 */                 boolean done = false;
/* 448 */                 for (int a = 0; a < gd.size(); a++) {
/* 449 */                   if (((Gate)gd.get(a)).gd.name.equals(line)) {
/* 450 */                     group.add((Gate)gd.get(a));
/* 451 */                     done = true;
/*     */                   }
/*     */                 }
/* 454 */                 for (int a = 0; a < gg.size(); a++) {
/* 455 */                   if ((((GateGroup)gg.get(a)).name.equals(line)) && (!line.equals(group.name))) {
/* 456 */                     group.add((GateGroup)gg.get(a));
/* 457 */                     done = true;
/*     */                   }
/*     */                 }
/* 460 */                 if (!done)
/* 461 */                   ignore.add(Integer.valueOf(count));
/*     */               }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 466 */             ignore.add(Integer.valueOf(count));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 471 */     bf.close();
/* 472 */     if (!ignore.isEmpty()) {
/* 473 */       String i = "";
/* 474 */       for (int a = 0; a < ignore.size(); a++) {
/* 475 */         if (a == 0)
/* 476 */           i = String.valueOf(ignore.get(a));
/*     */         else {
/* 478 */           i = i + ", " + String.valueOf(ignore.get(a));
/*     */         }
/*     */       }
/* 481 */       p.getLogger().warning("Loading Groups: Ignore Lines: " + i);
/*     */     }
/* 483 */     return gg;
/*     */   }
/*     */ 
/*     */   public static General LoadGeneral(Plugin plugin) throws Exception {
/* 487 */     General g = new General();
/* 488 */     FileInputStream fis = new FileInputStream("plugins/CityGates/general.yml");
/* 489 */     int lines = fis.available();
/* 490 */     fis.close();
/* 491 */     if (lines == 0) {
/* 492 */       throw new IOException("File is empty");
/*     */     }
/* 494 */     BufferedReader bf = new BufferedReader(new FileReader("plugins/CityGates/general.yml"));
/*     */ 
/* 496 */     ArrayList ignore = new ArrayList();
/* 497 */     int count = 0;
/*     */     String line;
/* 499 */     while ((line = bf.readLine()) != null)
/*     */     {
/*     */       String line;
/* 500 */       count++;
/*     */       try {
/* 502 */         if (line.startsWith("UpdateTime: ")) {
/* 503 */           double hour = Double.parseDouble(line.substring("UpdateTime: ".length()));
/* 504 */           g.updateTime = (()(hour * 3600000.0D));
/* 505 */           if (g.updateTime == 0L) {
/* 506 */             plugin.getLogger().warning("Error parsing updateTime! Cannot be 0, set updateTime to 1 hour");
/* 507 */             g.updateTime = 3600000L;
/*     */           }
/* 509 */         } else if (line.startsWith("Update msg: ")) {
/* 510 */           if (line.substring("Update msg: ".length()).equalsIgnoreCase("true"))
/* 511 */             g.updateMsg = true;
/* 512 */           else if (line.substring("Update msg: ".length()).equalsIgnoreCase("false"))
/* 513 */             g.updateMsg = false;
/*     */           else
/* 515 */             ignore.add(Integer.valueOf(count));
/*     */         }
/* 517 */         else if (line.startsWith("Night: ")) {
/* 518 */           g.night = Integer.parseInt(line.substring("Night: ".length()));
/* 519 */         } else if (line.startsWith("Day: ")) {
/* 520 */           g.day = Integer.parseInt(line.substring("Day: ".length()));
/* 521 */         } else if ((!line.startsWith("#")) && (!line.equals("")))
/*     */         {
/* 523 */           ignore.add(Integer.valueOf(count));
/*     */         }
/*     */       } catch (Exception e) {
/* 526 */         ignore.add(Integer.valueOf(count));
/*     */       }
/*     */     }
/* 529 */     if (!ignore.isEmpty()) {
/* 530 */       String i = "";
/* 531 */       for (int a = 0; a < ignore.size(); a++) {
/* 532 */         if (a == 0)
/* 533 */           i = String.valueOf(ignore.get(a));
/*     */         else {
/* 535 */           i = i + ", " + String.valueOf(ignore.get(a));
/*     */         }
/*     */       }
/* 538 */       plugin.getLogger().warning("Loading General: Ignore Lines: " + i);
/*     */     }
/* 540 */     bf.close();
/* 541 */     return g;
/*     */   }
/*     */ 
/*     */   public static void SaveGates(ArrayList<GateData> gd) throws Exception {
/* 545 */     BufferedWriter bf = new BufferedWriter(new FileWriter("plugins/CityGates/gates.yml"));
/* 546 */     bf.write("# CityGates - Gates");
/* 547 */     bf.newLine();
/* 548 */     bf.newLine();
/* 549 */     bf.write("# Only edit everything after the ':'");
/* 550 */     bf.newLine();
/* 551 */     bf.write("# There are 3 defirent parameters: value, String, boolean");
/* 552 */     bf.newLine();
/* 553 */     bf.write("# Do not leave any parameter black!");
/* 554 */     bf.newLine();
/* 555 */     bf.newLine();
/* 556 */     bf.write("# value: Any number without dots!");
/* 557 */     bf.newLine();
/* 558 */     bf.write("# String: Text");
/* 559 */     bf.newLine();
/* 560 */     bf.write("# Boolean: true/false");
/* 561 */     bf.newLine();
/* 562 */     bf.newLine();
/*     */ 
/* 564 */     for (int a = 0; a < gd.size(); a++) {
/* 565 */       GateData g = (GateData)gd.get(a);
/* 566 */       bf.write("Add Gate: " + g.name);
/* 567 */       bf.newLine();
/* 568 */       bf.write(" p1: " + g.p1[0] + ", " + g.p1[1] + ", " + g.p1[2]);
/* 569 */       bf.newLine();
/* 570 */       bf.write(" p2: " + g.p2[0] + ", " + g.p2[1] + ", " + g.p2[2]);
/* 571 */       bf.newLine();
/* 572 */       bf.write(" World: " + g.World);
/* 573 */       bf.newLine();
/* 574 */       bf.write(" Open: " + g.open);
/* 575 */       bf.newLine();
/* 576 */       bf.write(" timeGate: " + g.timeGate);
/* 577 */       bf.newLine();
/* 578 */       bf.write(" Redstone: ");
/* 579 */       bf.newLine();
/* 580 */       bf.write(" redstoneListener: " + g.redstoneListener);
/* 581 */       bf.newLine();
/* 582 */       bf.write(" pr: " + g.pr[0] + ", " + g.pr[1] + ", " + g.pr[2]);
/* 583 */       bf.newLine();
/* 584 */       bf.write(" KillListener: ");
/* 585 */       bf.newLine();
/* 586 */       bf.write(" mobKill: " + g.mobKill);
/* 587 */       bf.newLine();
/* 588 */       if ((g.mob == null) || (g.mob.equals("")))
/* 589 */         bf.write(" Mob: testMob");
/*     */       else
/* 591 */         bf.write(" Mob: " + g.mob);
/* 592 */       bf.newLine();
/* 593 */       bf.write(" Kill Message: " + g.killMsg);
/* 594 */       bf.newLine();
/* 595 */       bf.write(" ButtonListener: ");
/* 596 */       bf.newLine();
/* 597 */       bf.write(" Button Listener: " + g.buttonListener);
/* 598 */       bf.newLine();
/* 599 */       bf.write(" ButtonInterval: " + g.ButtonInterval);
/* 600 */       bf.newLine();
/* 601 */       bf.write(" Button: " + g.button[0] + ", " + g.button[1] + ", " + g.button[2]);
/* 602 */       bf.newLine();
/* 603 */       bf.write(" Permissions: ");
/* 604 */       bf.newLine();
/* 605 */       bf.write(" Button Permission: " + g.buttonPerm);
/* 606 */       bf.newLine();
/* 607 */       bf.write(" Kill Permission: " + g.killPerm);
/* 608 */       bf.newLine();
/* 609 */       bf.write(" Open Permission: " + g.openPerm);
/* 610 */       bf.newLine();
/* 611 */       bf.write(" Close Permission: " + g.closePerm);
/* 612 */       bf.newLine();
/* 613 */       bf.write(" Material1: " + ((MaterialId)g.materials1.get(0)).getID() + ":" + ((MaterialId)g.materials1.get(0)).getData());
/* 614 */       if (g.materials1.size() >= 2) {
/* 615 */         for (int b = 1; b < g.materials1.size(); b++) {
/* 616 */           bf.write(", " + ((MaterialId)g.materials1.get(b)).getID() + ":" + ((MaterialId)g.materials1.get(b)).getData());
/*     */         }
/*     */       }
/* 619 */       bf.newLine();
/* 620 */       bf.write(" Material2: " + ((MaterialId)g.materials2.get(0)).getID() + ":" + ((MaterialId)g.materials2.get(0)).getData());
/* 621 */       if (g.materials2.size() >= 2) {
/* 622 */         for (int b = 1; b < g.materials2.size(); b++) {
/* 623 */           bf.write(", " + ((MaterialId)g.materials2.get(b)).getID() + ":" + ((MaterialId)g.materials2.get(b)).getData());
/*     */         }
/*     */       }
/* 626 */       bf.newLine();
/* 627 */       bf.write("End Gate");
/* 628 */       bf.newLine();
/* 629 */       bf.newLine();
/*     */     }
/* 631 */     bf.flush();
/* 632 */     bf.close();
/*     */   }
/*     */ 
/*     */   public static void SaveGroups(ArrayList<GateGroup> gg) throws Exception {
/* 636 */     BufferedWriter bf = new BufferedWriter(new FileWriter("plugins/CityGates/groups.yml"));
/* 637 */     bf.write("# CityGates - Groups");
/* 638 */     bf.newLine();
/* 639 */     bf.newLine();
/* 640 */     bf.write("# Only edit everything after the ':'");
/* 641 */     bf.newLine();
/* 642 */     bf.write("# There are 3 defirent parameters: value, String, boolean");
/* 643 */     bf.newLine();
/* 644 */     bf.write("# Do not leave any parameter black!");
/* 645 */     bf.newLine();
/* 646 */     bf.newLine();
/* 647 */     bf.write("# value: Any number without dots!");
/* 648 */     bf.newLine();
/* 649 */     bf.write("# String: Text");
/* 650 */     bf.newLine();
/* 651 */     bf.write("# Boolean: true/false");
/* 652 */     bf.newLine();
/* 653 */     bf.newLine();
/*     */ 
/* 655 */     for (int a = 0; a < gg.size(); a++) {
/* 656 */       GateGroup g = (GateGroup)gg.get(a);
/* 657 */       bf.write("Add Group: " + g.name);
/* 658 */       bf.newLine();
/* 659 */       bf.write(" Delay: " + g.delay);
/* 660 */       bf.newLine();
/* 661 */       bf.write(" World: " + g.world);
/* 662 */       bf.newLine();
/* 663 */       bf.write(" Open: " + g.open);
/* 664 */       bf.newLine();
/* 665 */       bf.write(" timeGate: " + g.timegate);
/* 666 */       bf.newLine();
/* 667 */       bf.write(" Redstone: ");
/* 668 */       bf.newLine();
/* 669 */       bf.write(" redstoneListener: " + g.redstoneListener);
/* 670 */       bf.newLine();
/* 671 */       bf.write(" pr: " + g.pr[0] + ", " + g.pr[1] + ", " + g.pr[2]);
/* 672 */       bf.newLine();
/* 673 */       bf.write(" KillListener: ");
/* 674 */       bf.newLine();
/* 675 */       bf.write(" mobKill: " + g.mobKill);
/* 676 */       bf.newLine();
/* 677 */       if ((g.mob == null) || (g.mob.equals("")))
/* 678 */         bf.write(" Mob: testMob");
/*     */       else
/* 680 */         bf.write(" Mob: " + g.mob);
/* 681 */       bf.newLine();
/* 682 */       bf.write(" Kill Message: " + g.killMsg);
/* 683 */       bf.newLine();
/* 684 */       bf.write(" ButtonListener: ");
/* 685 */       bf.newLine();
/* 686 */       bf.write(" Button Listener: " + g.buttonListener);
/* 687 */       bf.newLine();
/* 688 */       bf.write(" ButtonInterval: " + g.ButtonInterval);
/* 689 */       bf.newLine();
/* 690 */       bf.write(" Button: " + g.button[0] + ", " + g.button[1] + ", " + g.button[2]);
/* 691 */       bf.newLine();
/* 692 */       bf.write(" Permissions: ");
/* 693 */       bf.newLine();
/* 694 */       bf.write(" Button Permission: " + g.buttonPerm);
/* 695 */       bf.newLine();
/* 696 */       bf.write(" Kill Permission: " + g.killPerm);
/* 697 */       bf.newLine();
/* 698 */       bf.write(" Open Permission: " + g.openPerm);
/* 699 */       bf.newLine();
/* 700 */       bf.write(" Close Permission: " + g.closePerm);
/* 701 */       bf.newLine();
/* 702 */       bf.write(" Children: ");
/* 703 */       bf.newLine();
/* 704 */       for (int b = 0; b < g.g.size(); b++) {
/*     */         try {
/* 706 */           Gate gate = (Gate)g.g.get(b);
/* 707 */           bf.write(" " + gate.gd.name);
/*     */         } catch (Exception e) {
/*     */           try {
/* 710 */             GateGroup group = (GateGroup)g.g.get(b);
/* 711 */             bf.write(" " + group.name); } catch (Exception localException1) {
/*     */           }
/*     */         }
/* 714 */         bf.newLine();
/*     */       }
/* 716 */       bf.write(" End Children");
/* 717 */       bf.newLine();
/* 718 */       bf.write("End Group");
/* 719 */       bf.newLine();
/* 720 */       bf.newLine();
/*     */     }
/* 722 */     bf.flush();
/* 723 */     bf.close();
/*     */   }
/*     */ 
/*     */   public static void SaveGereral(General g) throws IOException {
/* 727 */     BufferedWriter bf = new BufferedWriter(new FileWriter("plugins/CityGates/general.yml"));
/* 728 */     bf.write("# CityGates - General");
/* 729 */     bf.newLine();
/* 730 */     bf.newLine();
/* 731 */     bf.write("# Only edit everything after the ':'");
/* 732 */     bf.newLine();
/* 733 */     bf.write("# There are 2 defirent parameters: value, boolean");
/* 734 */     bf.newLine();
/* 735 */     bf.write("# Do not leave any parameter black!");
/* 736 */     bf.newLine();
/* 737 */     bf.newLine();
/* 738 */     bf.write("# value: Any number (can with dots!)");
/* 739 */     bf.newLine();
/* 740 */     bf.write("# String: Text");
/* 741 */     bf.newLine();
/* 742 */     bf.write("# Boolean: true/false");
/* 743 */     bf.newLine();
/* 744 */     bf.newLine();
/* 745 */     bf.write("#Time in hours");
/* 746 */     bf.newLine();
/* 747 */     double ms = g.updateTime;
/* 748 */     bf.write("UpdateTime: " + ms / 3600000.0D);
/* 749 */     bf.newLine();
/* 750 */     bf.write("#Show update Message");
/* 751 */     bf.newLine();
/* 752 */     bf.write("Update msg: " + g.updateMsg);
/* 753 */     bf.newLine();
/* 754 */     bf.write("#Time when it toggles to day (number between 0-24000");
/* 755 */     bf.newLine();
/* 756 */     bf.write("Day: " + g.day);
/* 757 */     bf.newLine();
/* 758 */     bf.write("#Time when it toggles to night (number between 0-24000");
/* 759 */     bf.newLine();
/* 760 */     bf.write("Night: " + g.night);
/* 761 */     bf.close();
/*     */   }
/*     */ 
/*     */   public static ArrayList<GateData> Load() throws IOException, ClassNotFoundException {
/* 765 */     FileInputStream fis = new FileInputStream("plugins/CityGates/gates.yml");
/* 766 */     int lines = fis.available();
/* 767 */     fis.close();
/* 768 */     if (lines == 0) {
/* 769 */       throw new IOException("File is empty");
/*     */     }
/* 771 */     ObjectInputStream in = new ObjectInputStream(fis);
/* 772 */     GateData[] gd = (GateData[])in.readObject();
/* 773 */     in.close();
/* 774 */     ArrayList dd = new ArrayList();
/* 775 */     for (int a = 0; a < gd.length; a++) {
/* 776 */       dd.add(gd[a]);
/*     */     }
/* 778 */     return dd;
/*     */   }
/*     */ 
/*     */   public static void Save(ArrayList<GateData> dd) throws IOException {
/* 782 */     GateData[] gd = new GateData[dd.size()];
/* 783 */     for (int a = 0; a < dd.size(); a++) {
/* 784 */       gd[a] = ((GateData)dd.get(a));
/*     */     }
/*     */ 
/* 787 */     FileOutputStream fos = new FileOutputStream("plugins/CityGates/gates.yml");
/* 788 */     ObjectOutputStream out = new ObjectOutputStream(fos);
/* 789 */     out.writeObject(gd);
/* 790 */     out.flush();
/* 791 */     fos.close();
/* 792 */     out.close();
/*     */   }
/*     */ 
/*     */   public static void mdir() {
/*     */     try {
/* 797 */       File root = new File("plugins/CityGates/");
/* 798 */       root.mkdirs();
/* 799 */       File gates = new File("plugins/CityGates/gates.yml");
/* 800 */       gates.createNewFile();
/* 801 */       File groups = new File("plugins/CityGates/groups.yml");
/* 802 */       groups.createNewFile();
/* 803 */       File gerneral = new File("plugins/CityGates/gerneral.yml");
/* 804 */       if (gerneral.exists()) {
/* 805 */         gerneral.delete();
/*     */       }
/* 807 */       File general = new File("plugins/CityGates/general.yml");
/* 808 */       general.createNewFile();
/*     */     } catch (Exception e) {
/* 810 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void RemoveOldDownloader(Plugin p) {
/* 815 */     Plugin[] plugins = p.getServer().getPluginManager().getPlugins();
/* 816 */     for (int a = 0; a < plugins.length; a++)
/* 817 */       if (plugins[a].getName().equalsIgnoreCase("CityGates_Updater"))
/* 818 */         JOptionPane.showConfirmDialog(null, "A Outdated plugin has been found: CityGates_Updater.jar\n\nPlease, Stop the server and delete this file manually", "Outdated file Found!", 0);
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.Config
 * JD-Core Version:    0.6.2
 */