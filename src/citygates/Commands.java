/*     */ package citygates;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class Commands
/*     */ {
/*     */   private Plugin plugin;
/*     */   private ArrayList<Gate> g;
/*     */   private ArrayList<GateGroup> gg;
/*     */   private Location p1;
/*     */   private Location p2;
/*     */   private Block[] blocks;
/*     */   private Material[] backup;
/* 133 */   public static final String[] help = { "------ Help page 1 ------", "/ghelp <page number> - help funcie", "/ggroup             - help funcite", "------Gate Managing------", "/gp1                - select point 1", "/gp2                - select point 2", "/gcreate [GateName] - create a gate", "/gremove [GateName] - remove a gate", "/ginfo [Gate/Group] - info about a gate", "/glist <Group/Gate> - list all the Gates and/or Groups", "------Group Managing------", "/ggroup create [Group]              - create a group", "/ggroup del [Group]                 - delete a group", "/ggroup add [Group] [Gate/Group]    - Add to group", "/ggroup remove [Group] [Gate/Group] - Remove from group", "/ggroup clear [Group]               - Clear a group", "/ggroup setDelay [Group] [time delay (in milli sec)c] - Set delay" };
/*     */ 
/* 152 */   public static final String[] help2 = { "------ Help page 2 ------", "/ghelp <page number> - help funcie", "/ggroup             - help funcite", "------Gate Listeners------", "/gOpen [Gate/Group] - Open a Gate/Group", "/gClose[Gate/Group] - Close a Gate/Group", "/gGroup timeGate [Gate/Group] [true/false] - Gate open and close by time!", "/gGroup redstone [Gate/Group] [true/false] look at the redstone - Add a redstone listener", "/gGroup mobKill [Gate/Group] [mob/del] <Kill Msg> - Gate open and close when the mob is slayen!", "/gGroup button [Gate/Group] [true/false] [time delay (in milli sec)] look at the button - Add a non-toggle button listener" };
/*     */ 
/*     */   public Commands(Plugin p, ArrayList<Gate> g, ArrayList<GateGroup> gg)
/*     */   {
/*  17 */     this.plugin = p;
/*  18 */     this.g = g;
/*  19 */     this.gg = gg;
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
/*  23 */     Player p = null;
/*  24 */     if ((cs instanceof Player)) {
/*  25 */       p = (Player)cs;
/*     */ 
/*  27 */       String cmd = cmnd.getName().toLowerCase();
/*     */ 
/*  29 */       if (cmd.equalsIgnoreCase("gp1")) {
/*  30 */         gp1(p);
/*  31 */         return true;
/*  32 */       }if (cmd.equalsIgnoreCase("gp2")) {
/*  33 */         gp2(p);
/*  34 */         return true;
/*  35 */       }if (cmd.equalsIgnoreCase("gcreate")) {
/*  36 */         if (strings.length == 1) {
/*  37 */           gcreate(p, strings);
/*  38 */           return true;
/*     */         }
/*  40 */         return false;
/*     */       }
/*  42 */       if (cmd.equalsIgnoreCase("gredstone")) {
/*  43 */         if (strings.length == 2) {
/*  44 */           gRedstone(p, strings);
/*  45 */           return true;
/*     */         }
/*  47 */         return false;
/*     */       }
/*  49 */       if (cmd.equalsIgnoreCase("gtimegate")) {
/*  50 */         if (strings.length == 2) {
/*  51 */           gTimeGate(p, strings);
/*  52 */           return true;
/*     */         }
/*  54 */         return false;
/*     */       }
/*  56 */       if (cmd.equalsIgnoreCase("gkill")) {
/*  57 */         if (strings.length >= 2) {
/*  58 */           gKill(p, strings);
/*  59 */           return true;
/*     */         }
/*  61 */         return false;
/*     */       }
/*  63 */       if (cmd.equalsIgnoreCase("glist")) {
/*  64 */         if (strings.length < 3) {
/*  65 */           gList(p, strings);
/*  66 */           return true;
/*     */         }
/*  68 */       } else if (cmd.equalsIgnoreCase("gremove")) {
/*  69 */         if (strings.length == 1) {
/*  70 */           gRemove(p, strings);
/*  71 */           return true;
/*     */         }
/*  73 */       } else if (cmd.equalsIgnoreCase("gopen")) {
/*  74 */         if (strings.length == 1) {
/*  75 */           gOpen(p, strings);
/*  76 */           return true;
/*     */         }
/*  78 */       } else if (cmd.equalsIgnoreCase("gclose")) {
/*  79 */         if (strings.length == 1) {
/*  80 */           gClose(p, strings);
/*  81 */           return true;
/*     */         }
/*  83 */       } else if (cmd.equalsIgnoreCase("ginfo")) {
/*  84 */         if (strings.length == 1) {
/*  85 */           gInfo(p, strings);
/*  86 */           return true;
/*     */         }
/*  88 */       } else if (cmd.equalsIgnoreCase("clear")) {
/*  89 */         Clear(p, strings);
/*  90 */       } else if (cmd.equalsIgnoreCase("gkillarea")) {
/*  91 */         if (strings.length == 1) {
/*  92 */           gKillArea(p, strings);
/*  93 */           return true;
/*     */         }
/*  95 */       } else if (cmd.equalsIgnoreCase("gbutton")) {
/*  96 */         if (strings.length == 2) {
/*  97 */           if (strings[1].equalsIgnoreCase("false")) {
/*  98 */             gRemoveButton(p, strings[0]);
/*  99 */             return true;
/*     */           }
/* 101 */         } else if (strings.length == 3) {
/* 102 */           gButton(p, strings);
/* 103 */           return true;
/*     */         }
/*     */       } else { if (cmd.equalsIgnoreCase("ggroup")) {
/* 106 */           gGroup(p, strings);
/* 107 */           return true;
/* 108 */         }if (cmd.equalsIgnoreCase("ghelp")) {
/* 109 */           if (strings.length == 0)
/* 110 */             p.sendMessage(help);
/* 111 */           else if (strings[0].equalsIgnoreCase("1"))
/* 112 */             p.sendMessage(help);
/* 113 */           else if (strings[0].equalsIgnoreCase("2"))
/* 114 */             p.sendMessage(help2);
/*     */           else {
/* 116 */             p.sendMessage("Max pages = 2");
/*     */           }
/* 118 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */   private void gp1(Player p)
/*     */   {
/* 166 */     if (p != null) {
/* 167 */       Block b = p.getTargetBlock(null, 50);
/* 168 */       this.p1 = b.getLocation();
/* 169 */       p.sendMessage("[CityGates] Select Point 1 - X: " + this.p1.getBlockX() + " Y: " + this.p1.getBlockY() + " Z: " + this.p1.getBlockZ());
/*     */     } else {
/* 171 */       this.plugin.getLogger().info("This command can only be used by players!");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gp2(Player p) {
/* 176 */     if (p == null) {
/* 177 */       this.plugin.getLogger().info("This command can only be used by players!");
/*     */     } else {
/* 179 */       this.p2 = p.getTargetBlock(null, 50).getLocation();
/* 180 */       p.sendMessage("[CityGates] Select Point 2 - X: " + this.p2.getBlockX() + " Y: " + this.p2.getBlockY() + " Z: " + this.p2.getBlockZ());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gcreate(final Player player, final String[] args) {
/* 185 */     if (player == null) {
/* 186 */       this.plugin.getLogger().info("This command can only be used by players!");
/*     */     } else {
/* 188 */       boolean exists = false;
/* 189 */       for (int a = 0; a < this.g.size(); a++) {
/* 190 */         if (args[0].equals(((Gate)this.g.get(a)).gd.name)) {
/* 191 */           exists = true;
/* 192 */           player.sendMessage("[CityGates] This Gate already exists!");
/*     */         }
/*     */       }
/* 195 */       for (int a = 0; a < this.gg.size(); a++) {
/* 196 */         if (args[0].equals(((GateGroup)this.gg.get(a)).name)) {
/* 197 */           exists = true;
/* 198 */           player.sendMessage("[CityGates] A Group with this name already exists!");
/*     */         }
/*     */       }
/* 201 */       if (this.p1 == null) {
/* 202 */         player.sendMessage("[CityGates] You have to select point 1 first");
/* 203 */         player.sendMessage("[CityGates] Command: /gp1");
/* 204 */       } else if (this.p2 == null) {
/* 205 */         player.sendMessage("[CityGates] You have to select point 2 first");
/* 206 */         player.sendMessage("[CityGates] Command: /gp2");
/* 207 */       } else if (!this.p1.getWorld().equals(this.p2.getWorld())) {
/* 208 */         if (!this.p1.getWorld().equals(player.getWorld())) {
/* 209 */           player.sendMessage("[CityGates] You have to select point 1 first");
/* 210 */           player.sendMessage("[CityGates] Command: /gp1");
/*     */         } else {
/* 212 */           player.sendMessage("[CityGates] You have to select point 2 first");
/* 213 */           player.sendMessage("[CityGates] Command: /gp2");
/*     */         }
/* 215 */       } else if (exists) {
/* 216 */         player.sendMessage("[CityGates] " + args[0] + " already exists!");
/*     */       } else {
/* 218 */         int x1 = 0;
/* 219 */         int x2 = 0;
/* 220 */         int y1 = 0;
/* 221 */         int y2 = 0;
/* 222 */         int z1 = 0;
/* 223 */         int z2 = 0;
/*     */ 
/* 225 */         if (this.p1.getBlockX() < this.p2.getBlockX()) {
/* 226 */           x1 = this.p1.getBlockX();
/* 227 */           x2 = this.p2.getBlockX();
/*     */         } else {
/* 229 */           x2 = this.p1.getBlockX();
/* 230 */           x1 = this.p2.getBlockX();
/*     */         }
/* 232 */         if (this.p1.getBlockY() < this.p2.getBlockY()) {
/* 233 */           y1 = this.p1.getBlockY();
/* 234 */           y2 = this.p2.getBlockY();
/*     */         } else {
/* 236 */           y2 = this.p1.getBlockY();
/* 237 */           y1 = this.p2.getBlockY();
/*     */         }
/* 239 */         if (this.p1.getBlockZ() < this.p2.getBlockZ()) {
/* 240 */           z1 = this.p1.getBlockZ();
/* 241 */           z2 = this.p2.getBlockZ();
/*     */         } else {
/* 243 */           z2 = this.p1.getBlockZ();
/* 244 */           z1 = this.p2.getBlockZ();
/*     */         }
/*     */ 
/* 247 */         World w = player.getWorld();
/*     */ 
/* 249 */         this.blocks = new Block[500];
/* 250 */         int bc = 0;
/* 251 */         for (int a = x1; a <= x2; a++) {
/* 252 */           for (int b = y1; b <= y2; b++) {
/* 253 */             for (int c = z1; c <= z2; c++) {
/* 254 */               if (bc < this.blocks.length) {
/* 255 */                 this.blocks[bc] = w.getBlockAt(a, b, c);
/* 256 */                 bc++;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 261 */         this.backup = new Material[this.blocks.length];
/* 262 */         for (int a = 0; a < this.blocks.length; a++) {
/* 263 */           if (this.blocks[a] != null) {
/* 264 */             this.backup[a] = this.blocks[a].getType();
/*     */           }
/*     */         }
/* 267 */         for (int a = 0; a < this.blocks.length; a++) {
/* 268 */           if (this.blocks[a] != null) {
/* 269 */             this.blocks[a].setTypeId(20);
/*     */           }
/*     */         }
/*     */ 
/* 273 */         this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
/*     */         {
/*     */           public void run() {
/* 276 */             for (int a = 0; a < Commands.this.blocks.length; a++) {
/* 277 */               if (Commands.this.blocks[a] != null) {
/* 278 */                 Commands.this.blocks[a].setType(Commands.this.backup[a]);
/*     */               }
/*     */             }
/* 281 */             int[] l1 = { Commands.this.p1.getBlockX(), Commands.this.p1.getBlockY(), Commands.this.p1.getBlockZ() };
/*     */ 
/* 286 */             int[] l2 = { Commands.this.p2.getBlockX(), Commands.this.p2.getBlockY(), Commands.this.p2.getBlockZ() };
/*     */ 
/* 291 */             Commands.this.g.add(new Gate(new GateData(args[0], l1, l2, player.getWorld().getName()), Commands.this.plugin));
/*     */           }
/*     */         }
/*     */         , 20L);
/*     */ 
/* 296 */         player.sendMessage("[CityGates] " + args[0] + " succesfull created!");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gRedstone(Player player, String[] args) {
/* 302 */     if (player == null) {
/* 303 */       this.plugin.getLogger().info("This command can only be used by players!");
/*     */     }
/*     */     else
/*     */     {
/*     */       Gate gate;
/* 306 */       if ((gate = testGate(args[0])) == null) {
/* 307 */         GateGroup group = testGateGroup(args[0]);
/* 308 */         if (group == null) {
/* 309 */           player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */         }
/* 311 */         else if (args[1].equals("true")) {
/* 312 */           Location l = player.getTargetBlock(null, 50).getLocation();
/* 313 */           group.pr[0] = l.getBlockX();
/* 314 */           group.pr[1] = l.getBlockY();
/* 315 */           group.pr[2] = l.getBlockZ();
/* 316 */           group.redstoneListener = true;
/* 317 */           player.sendMessage("[CityGates] RedstoneListener add to " + args[0] + "on Block -" + " X: " + group.pr[0] + " Y: " + group.pr[1] + " Z: " + group.pr[2]);
/*     */         }
/* 319 */         else if (args[1].equals("false")) {
/* 320 */           group.redstoneListener = false;
/* 321 */           player.sendMessage("[CityGates] RedstoneListener Removed!");
/*     */         } else {
/* 323 */           player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*     */         }
/*     */ 
/*     */       }
/* 327 */       else if (args[1].equals("true")) {
/* 328 */         gate.setRedstoenListener(player.getTargetBlock(null, 50).getLocation());
/* 329 */         player.sendMessage("[CityGates] RedstoneListener add to " + args[0] + "on Block -" + " X: " + gate.gd.pr[0] + " Y: " + gate.gd.pr[1] + " Z: " + gate.gd.pr[2]);
/*     */       }
/* 331 */       else if (args[1].equals("false")) {
/* 332 */         gate.removeRedstoneListener();
/* 333 */         player.sendMessage("[CityGates] RedstoneListener Removed!");
/*     */       } else {
/* 335 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gKill(Player player, String[] args)
/*     */   {
/*     */     Gate gate;
/* 343 */     if ((gate = testGate(args[0])) == null) {
/* 344 */       GateGroup group = testGateGroup(args[0]);
/* 345 */       if (group == null)
/* 346 */         player.sendMessage("[CityGates] " + args[0] + "not found!");
/*     */       else
/*     */         try {
/* 349 */           if (args[1].equalsIgnoreCase("del")) {
/* 350 */             group.mobKill = false;
/*     */           } else {
/* 352 */             EntityType mob = EntityType.fromName(args[1]);
/* 353 */             group.mob = mob.getName();
/* 354 */             group.mobKill = true;
/* 355 */             player.sendMessage("[CityGates] " + args[0] + " Open when a " + mob.getName() + " is slayen in this World!");
/* 356 */             if (args.length > 2) {
/* 357 */               String mes = "";
/* 358 */               for (int a = 2; a < args.length; a++) {
/* 359 */                 mes = mes + " " + args[a];
/* 360 */                 group.killMsg = mes;
/*     */               }
/*     */             }
/*     */           }
/*     */         } catch (Exception e) {
/* 365 */           player.sendMessage("[CityGates] " + args[1] + " Invallid Entity!");
/*     */         }
/*     */     }
/*     */     else {
/*     */       try {
/* 370 */         if (args[1].equalsIgnoreCase("del")) {
/* 371 */           gate.removeMobKill();
/*     */         } else {
/* 373 */           EntityType mob = EntityType.fromName(args[1]);
/* 374 */           gate.setMobKill(mob);
/* 375 */           player.sendMessage("[CityGates] " + args[0] + " Open when a " + mob.getName() + " is slayen in this World!");
/* 376 */           if (args.length > 2) {
/* 377 */             String mes = "";
/* 378 */             for (int a = 2; a < args.length; a++) {
/* 379 */               mes = mes + " " + args[a];
/* 380 */               gate.gd.killMsg = mes;
/*     */             }
/*     */           }
/*     */         }
/*     */       } catch (Exception e) {
/* 385 */         player.sendMessage("[CityGates] " + args[1] + " Invallid Entity!");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gTimeGate(Player player, String[] args)
/*     */   {
/*     */     Gate gate;
/* 393 */     if ((gate = testGate(args[0])) == null) {
/* 394 */       GateGroup group = testGateGroup(args[0]);
/* 395 */       if (group == null) {
/* 396 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */       }
/* 398 */       else if (args[1].equalsIgnoreCase("true")) {
/* 399 */         group.timegate = true;
/* 400 */         player.sendMessage("[CityGates] " + args[0] + " Open and Close by time!");
/* 401 */       } else if (args[1].equalsIgnoreCase("false")) {
/* 402 */         group.timegate = false;
/* 403 */         player.sendMessage("[CityGates] Succesfully removed timeGates!");
/*     */       } else {
/* 405 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*     */       }
/*     */ 
/*     */     }
/* 409 */     else if (args[1].equalsIgnoreCase("true")) {
/* 410 */       gate.setTimeGate();
/* 411 */       player.sendMessage("[CityGates] " + args[0] + " Open and Close by time!");
/* 412 */     } else if (args[1].equalsIgnoreCase("false")) {
/* 413 */       gate.removeTimeGate();
/* 414 */       player.sendMessage("[CityGates] Succesfully removed timeGates!");
/*     */     } else {
/* 416 */       player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gList(Player player, String[] args)
/*     */   {
/* 422 */     if (args.length == 0) {
/* 423 */       if (this.g.size() == 0)
/* 424 */         player.sendMessage("No Gates Found!");
/*     */       else {
/* 426 */         for (int a = 0; a < this.g.size(); a++) {
/* 427 */           player.sendMessage("[Gate]" + String.valueOf(a + 1) + ": " + ((Gate)this.g.get(a)).gd.name);
/*     */         }
/*     */       }
/* 430 */       if (this.gg.size() == 0)
/* 431 */         player.sendMessage("No Groups Found!");
/*     */       else {
/* 433 */         for (int a = 0; a < this.gg.size(); a++)
/* 434 */           player.sendMessage("[Group] " + String.valueOf(a + 1) + ": " + ((GateGroup)this.gg.get(a)).name);
/*     */       }
/*     */     }
/* 437 */     else if (args.length == 1) {
/* 438 */       if (args[0].equalsIgnoreCase("group")) {
/* 439 */         if (this.gg.size() == 0)
/* 440 */           player.sendMessage("No Groups Found!");
/*     */         else {
/* 442 */           for (int a = 0; a < this.gg.size(); a++)
/* 443 */             player.sendMessage("[Group] " + String.valueOf(a + 1) + ": " + ((GateGroup)this.gg.get(a)).name);
/*     */         }
/*     */       }
/* 446 */       else if (args[0].equalsIgnoreCase("gate")) {
/* 447 */         if (this.g.size() == 0)
/* 448 */           player.sendMessage("No Gates Found!");
/*     */         else {
/* 450 */           for (int a = 0; a < this.g.size(); a++)
/* 451 */             player.sendMessage("[Gate]" + String.valueOf(a + 1) + ": " + ((Gate)this.g.get(a)).gd.name);
/*     */         }
/*     */       }
/*     */       else
/* 455 */         player.sendMessage("[CityGate] /list [group/gate]");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gRemove(Player player, String[] args)
/*     */   {
/*     */     Gate gate;
/* 462 */     if ((gate = testGate(args[0])) == null)
/* 463 */       player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */     else
/* 465 */       for (int a = 0; a < this.g.size(); a++)
/* 466 */         if (((Gate)this.g.get(a)).gd.name.equalsIgnoreCase(args[0])) {
/* 467 */           this.g.remove(a);
/* 468 */           player.sendMessage("[CityGates] Removing: " + args[0]);
/*     */         }
/*     */   }
/*     */ 
/*     */   private void gOpen(Player player, String[] args)
/*     */   {
/*     */     Gate gate;
/* 476 */     if ((gate = testGate(args[0])) == null) {
/* 477 */       GateGroup group = testGateGroup(args[0]);
/* 478 */       if (group == null)
/* 479 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */       else
/* 481 */         group.Open();
/*     */     }
/*     */     else {
/* 484 */       gate.Open();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gClose(Player player, String[] args)
/*     */   {
/*     */     Gate gate;
/* 490 */     if ((gate = testGate(args[0])) == null) {
/* 491 */       GateGroup group = testGateGroup(args[0]);
/* 492 */       if (group == null)
/* 493 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */       else
/* 495 */         group.Close();
/*     */     }
/*     */     else {
/* 498 */       gate.Close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void gInfo(Player player, String[] args)
/*     */   {
/*     */     Gate gate;
/* 504 */     if ((gate = testGate(args[0])) == null) {
/* 505 */       GateGroup group = testGateGroup(args[0]);
/* 506 */       if (group == null) {
/* 507 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */       } else {
/* 509 */         player.sendMessage("[CityGates] Name: " + group.name);
/* 510 */         player.sendMessage("[CityGates] World: " + group.world);
/* 511 */         player.sendMessage("[CityGates] Delay: " + group.delay);
/* 512 */         player.sendMessage("[CityGates] Open: " + group.open);
/* 513 */         player.sendMessage("[CityGates] TimeGate: " + group.timegate);
/* 514 */         player.sendMessage("[CityGates] redstoneListener: " + group.redstoneListener);
/* 515 */         player.sendMessage("[CityGates] Redstone Point: " + group.pr[0] + ", " + group.pr[1] + ", " + group.pr[2]);
/* 516 */         player.sendMessage("[CityGates] Kill Listener: " + group.mobKill);
/* 517 */         player.sendMessage("[CityGates] Kill Message: " + group.killMsg);
/* 518 */         player.sendMessage("[CityGates] Button Listener: " + group.buttonListener);
/* 519 */         player.sendMessage("[CityGates] Button Interval: " + group.delay);
/* 520 */         String Children = "";
/* 521 */         if (group.g.size() == 0)
/* 522 */           Children = "No Gates!";
/*     */         else
/* 524 */           for (int a = 0; a < group.g.size(); a++)
/*     */             try {
/* 526 */               Gate gate2 = (Gate)group.g.get(a);
/* 527 */               if (a == 0)
/* 528 */                 Children = gate2.gd.name;
/*     */               else
/* 530 */                 Children = Children + ", " + gate2.gd.name;
/*     */             }
/*     */             catch (Exception e) {
/*     */               try {
/* 534 */                 GateGroup gategroup = (GateGroup)group.g.get(a);
/* 535 */                 if (a == 0)
/* 536 */                   Children = gategroup.name;
/*     */                 else
/* 538 */                   Children = Children + ", " + gategroup.name;
/*     */               }
/*     */               catch (Exception ee)
/*     */               {
/*     */               }
/*     */             }
/* 544 */         player.sendMessage("[CityGates] Gates: " + Children);
/*     */       }
/*     */     } else {
/* 547 */       GateData gd = gate.gd;
/* 548 */       player.sendMessage("[CityGates] Name: " + gd.name);
/* 549 */       player.sendMessage("[CityGates] Point 1: " + gd.p1[0] + ", " + gd.p1[1] + ", " + gd.p1[2]);
/* 550 */       player.sendMessage("[CityGates] Point 2: " + gd.p2[0] + ", " + gd.p2[1] + ", " + gd.p2[2]);
/* 551 */       player.sendMessage("[CityGates] World: " + gd.World);
/* 552 */       player.sendMessage("[CityGates] Redstone Listner: " + gd.redstoneListener);
/* 553 */       player.sendMessage("[CityGates] Redstone Point: " + gd.pr[0] + ", " + gd.pr[1] + ", " + gd.pr[2]);
/* 554 */       player.sendMessage("[CityGates] TimeGate: " + gd.timeGate);
/* 555 */       player.sendMessage("[CityGates] Kill Listener: " + gd.mobKill + " Mob: " + gd.mob);
/* 556 */       player.sendMessage("[CityGates] Kill Message: " + gd.killMsg);
/* 557 */       player.sendMessage("[CityGates] Button Listener: " + gd.buttonListener);
/* 558 */       player.sendMessage("[CityGates] Button Interval: " + gd.ButtonInterval);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Gate testGate(String GateName)
/*     */   {
/* 564 */     for (int a = 0; a < this.g.size(); a++) {
/* 565 */       if (((Gate)this.g.get(a)).gd.name.equals(GateName)) {
/* 566 */         return (Gate)this.g.get(a);
/*     */       }
/*     */     }
/* 569 */     return null;
/*     */   }
/*     */ 
/*     */   private GateGroup testGateGroup(String gateGroupname) {
/* 573 */     for (int a = 0; a < this.gg.size(); a++) {
/* 574 */       if (((GateGroup)this.gg.get(a)).name.equals(gateGroupname)) {
/* 575 */         return (GateGroup)this.gg.get(a);
/*     */       }
/*     */     }
/* 578 */     return null;
/*     */   }
/*     */ 
/*     */   private void Clear(Player player, String[] args) {
/* 582 */     if (args.length == 0)
/* 583 */       player.getInventory().clear();
/* 584 */     else if (args.length == 1)
/*     */       try {
/* 586 */         this.plugin.getServer().getPlayer(args[0]).getInventory().clear();
/*     */       } catch (Exception e) {
/* 588 */         player.sendMessage("[CityGates] No Player found with the name: " + args[1]);
/*     */       }
/*     */     else
/* 591 */       player.sendMessage("[CityGates] to many arguments!");
/*     */   }
/*     */ 
/*     */   private void gKillArea(Player player, String[] args)
/*     */   {
/* 596 */     if (player == null) {
/* 597 */       this.plugin.getLogger().info("This command can only be used by players!");
/*     */     }
/*     */     else
/*     */     {
/*     */       Gate gate;
/* 600 */       if ((gate = testGate(args[0])) == null) {
/* 601 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */       }
/* 603 */       else if (this.p1 == null) {
/* 604 */         player.sendMessage("[CityGates] You have to select point 1 first");
/* 605 */         player.sendMessage("[CityGates] Command: /gp1");
/* 606 */       } else if (this.p2 == null) {
/* 607 */         player.sendMessage("[CityGates] You have to select point 2 first");
/* 608 */         player.sendMessage("[CityGates] Command: /gp2");
/* 609 */       } else if (!this.p1.getWorld().equals(this.p2.getWorld())) {
/* 610 */         if (!this.p1.getWorld().equals(player.getWorld())) {
/* 611 */           player.sendMessage("[CityGates] You have to select point 1 first");
/* 612 */           player.sendMessage("[CityGates] Command: /gp1");
/*     */         } else {
/* 614 */           player.sendMessage("[CityGates] You have to select point 2 first");
/* 615 */           player.sendMessage("[CityGates] Command: /gp2");
/*     */         }
/*     */       }
/* 618 */       else if (args[1].equalsIgnoreCase("true")) {
/* 619 */         gate.setKillArea(this.p1, this.p2);
/* 620 */         player.sendMessage("[CityGates] Kill area set!");
/* 621 */       } else if (args[1].equalsIgnoreCase("false")) {
/* 622 */         gate.removeKillArea();
/* 623 */         player.sendMessage("[CityGates] Removed Kill area!");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gRemoveButton(Player player, String gate)
/*     */   {
/* 631 */     Gate g = testGate(gate);
/* 632 */     if (g == null) {
/* 633 */       GateGroup group = testGateGroup(gate);
/* 634 */       if (group == null) {
/* 635 */         player.sendMessage("[CityGates] " + gate + " not found!");
/*     */       } else {
/* 637 */         group.buttonListener = false;
/* 638 */         player.sendMessage("[CityGates] " + gate + " removed!");
/*     */       }
/*     */     } else {
/* 641 */       g.gd.buttonListener = false;
/* 642 */       player.sendMessage("[CityGates] " + gate + " removed!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gButton(Player player, String[] args) {
/* 647 */     Gate g = testGate(args[0]);
/* 648 */     if (g == null) {
/* 649 */       GateGroup group = testGateGroup(args[0]);
/* 650 */       if (group == null) {
/* 651 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */       }
/* 653 */       else if (args[1].equalsIgnoreCase("true"))
/*     */         try {
/* 655 */           group.ButtonInterval = Integer.parseInt(args[2]);
/* 656 */           Location l = player.getTargetBlock(null, 50).getLocation();
/* 657 */           group.button[0] = l.getBlockX();
/* 658 */           group.button[1] = l.getBlockY();
/* 659 */           group.button[2] = l.getBlockZ();
/* 660 */           group.buttonListener = true;
/* 661 */           player.sendMessage("[CityGates] add Button Listener");
/*     */         } catch (Exception e) {
/* 663 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*     */         }
/* 665 */       else if (args[1].equalsIgnoreCase("false"))
/* 666 */         gRemoveButton(player, args[0]);
/*     */       else {
/* 668 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*     */       }
/*     */ 
/*     */     }
/* 672 */     else if (args[1].equalsIgnoreCase("true")) {
/*     */       try {
/* 674 */         g.gd.ButtonInterval = Integer.parseInt(args[2]);
/* 675 */         Location l = player.getTargetBlock(null, 50).getLocation();
/* 676 */         g.gd.button[0] = l.getBlockX();
/* 677 */         g.gd.button[1] = l.getBlockY();
/* 678 */         g.gd.button[2] = l.getBlockZ();
/* 679 */         g.gd.buttonListener = true;
/* 680 */         player.sendMessage("[CityGates] add Button Listener");
/*     */       } catch (Exception e) {
/* 682 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*     */       }
/* 684 */     } else if (args[1].equalsIgnoreCase("false")) {
/* 685 */       gRemoveButton(player, args[0]);
/*     */     } else {
/* 687 */       player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gGroup(Player player, String[] args)
/*     */   {
/* 693 */     if (args.length == 0) {
/* 694 */       player.sendMessage(new String[] { "/gGroup create [Group Name] - create a new Group", "/gGroup del [Group Name] - delete a Group", "/gGroup add [Group] [Gate] - add a Gate to a Group", "/gGroup remove [Group] [Gate] - remove a Gate from a Group", "/gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate", "/gGroup clear - removes all the Gates in a Group" });
/*     */     }
/* 702 */     else if (args.length > 0)
/* 703 */       if (args[0].equalsIgnoreCase("create")) {
/* 704 */         if (args.length == 2) {
/* 705 */           GateGroup group = testGateGroup(args[1]);
/* 706 */           if (group == null) {
/* 707 */             Gate gate = testGate(args[1]);
/* 708 */             if (gate == null) {
/* 709 */               group = new GateGroup(args[1], this.plugin);
/* 710 */               group.world = player.getWorld().getName();
/* 711 */               this.gg.add(group);
/* 712 */               player.sendMessage("[CityGates] Create: " + args[1]);
/*     */             } else {
/* 714 */               player.sendMessage("[CityGates] A Gate with this name already exists!");
/*     */             }
/*     */           } else {
/* 717 */             player.sendMessage("[CityGates] This Group already exists!");
/*     */           }
/*     */         } else {
/* 720 */           player.sendMessage("[CityGates] /gGroup create [Group Name] - create a new Group");
/*     */         }
/* 722 */       } else if (args[0].equalsIgnoreCase("del")) {
/* 723 */         if (args.length == 2) {
/* 724 */           GateGroup group = testGateGroup(args[1]);
/* 725 */           if (group == null)
/* 726 */             player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*     */           else
/* 728 */             for (int a = 0; a < this.gg.size(); a++)
/* 729 */               if (((GateGroup)this.gg.get(a)).name.equals(args[1])) {
/* 730 */                 this.gg.remove(a);
/* 731 */                 player.sendMessage("[CityGates] Deleting: " + args[1]);
/*     */               }
/*     */         }
/*     */         else
/*     */         {
/* 736 */           player.sendMessage("[CityGates] /gGroup del [Group Name] - delete a Group");
/*     */         }
/* 738 */       } else if (args[0].equalsIgnoreCase("add")) {
/* 739 */         if (args.length == 3) {
/* 740 */           GateGroup group = testGateGroup(args[1]);
/* 741 */           if (group == null) {
/* 742 */             player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*     */           } else {
/* 744 */             Gate gate = testGate(args[2]);
/* 745 */             if (gate == null) {
/* 746 */               GateGroup gg = testGateGroup(args[0]);
/* 747 */               if (gg == null) {
/* 748 */                 player.sendMessage("[CityGates] " + args[0] + " not found!");
/* 749 */               } else if (gg.name.equals(group.name)) {
/* 750 */                 player.sendMessage("[CityGates] Parent is the same as the child!");
/*     */               } else {
/* 752 */                 group.add(gg);
/* 753 */                 player.sendMessage("[CityGates]" + gg.name + " Added to: " + group.name);
/*     */               }
/*     */             }
/*     */             else {
/* 757 */               group.add(gate);
/* 758 */               player.sendMessage("[CityGates] " + gate.gd.name + " Added to: " + group.name);
/*     */             }
/*     */           }
/*     */         } else {
/* 762 */           player.sendMessage("[CityGates] /gGroup add [Group] [Gate] - add a Gate to a Group");
/*     */         }
/* 764 */       } else if (args[0].equalsIgnoreCase("remove")) {
/* 765 */         if (args.length == 3) {
/* 766 */           GateGroup group = testGateGroup(args[1]);
/* 767 */           if (group == null) {
/* 768 */             player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*     */           } else {
/* 770 */             Gate gate = testGate(args[2]);
/* 771 */             if (gate == null) {
/* 772 */               GateGroup gg = testGateGroup(args[0]);
/* 773 */               if (gg == null) {
/* 774 */                 player.sendMessage("[CityGates] " + args[0] + " not found!");
/*     */               } else {
/* 776 */                 group.remove(gg);
/* 777 */                 player.sendMessage("[CityGates] " + gg.name + " Removed from: " + group.name);
/*     */               }
/*     */             } else {
/* 780 */               group.remove(gate);
/* 781 */               player.sendMessage("[CityGates] " + gate.gd.name + " Removed from: " + group.name);
/*     */             }
/*     */           }
/*     */         } else {
/* 785 */           player.sendMessage("[CityGates] /gGroup remove [Group] [Gate] - remove a Gate from a Group");
/*     */         }
/* 787 */       } else if (args[0].equalsIgnoreCase("setDelay")) {
/* 788 */         if (args.length == 3) {
/* 789 */           GateGroup group = testGateGroup(args[1]);
/* 790 */           if (group == null)
/* 791 */             player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*     */           else
/*     */             try {
/* 794 */               group.delay = Integer.parseInt(args[2]);
/* 795 */               player.sendMessage("[CityGates] " + args[1] + " setDelay: " + args[2]);
/*     */             } catch (Exception e) {
/* 797 */               player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*     */             }
/*     */         }
/*     */         else {
/* 801 */           player.sendMessage("[CityGates] /gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate");
/*     */         }
/* 803 */       } else if (args[0].equalsIgnoreCase("clear")) {
/* 804 */         if (args.length == 2) {
/* 805 */           GateGroup group = testGateGroup(args[1]);
/* 806 */           if (group == null) {
/* 807 */             player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*     */           } else {
/* 809 */             group.Clear();
/* 810 */             player.sendMessage("[CityGates] " + args[1] + " is Cleared!");
/*     */           }
/*     */         } else {
/* 813 */           player.sendMessage("[CityGates] /gGroup clear - removes all the Gates in a Group");
/*     */         }
/*     */       }
/* 816 */       else player.sendMessage(new String[] { "/gGroup create [Group Name] - create a new Group", "/gGroup del [Group Name] - delete a Group", "/gGroup add [Group] [Gate] - add a Gate to a Group", "/gGroup remove [Group] [Gate] - remove a Gate from a Group", "/gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate", "/gGroup clear - removes all the Gates in a Group" });
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.Commands
 * JD-Core Version:    0.6.2
 */