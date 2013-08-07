/*      */ package citygates;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.logging.Logger;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.command.Command;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.inventory.PlayerInventory;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ 
/*      */ public abstract class Commands
/*      */ {
/*      */   private Plugin plugin;
/*      */   private ArrayList<Gate> g;
/*      */   private ArrayList<GateGroup> gg;
/*      */   private Location p1;
/*      */   private Location p2;
/*      */   public static final String noPerm = "You don't have Permission to use this Command!";
/*      */   public static final String noPermGate = "You don't have Permssion to use this Gate!";
/*   27 */   public static final String[] help = { "------ Help page 1 ------", "/ghelp <page number> - help funcie", "/ggroup - help funcite", "------Gate Managing------", "/gp1 - select point 1", "/gp2 - select point 2", "/gcreate [GateName] - create a gate", "/gremove [GateName] - remove a gate", "/ginfo [Gate/Group] - info about a gate", "/glist <Group/Gate> - list all the Gates and/or Groups", "------Group Managing------", "/ggroup create [Group] - create a group", "/ggroup del [Group] - delete a group", "/ggroup add [Group] [Gate/Group] - Add to group", "/ggroup remove [Group] [Gate/Group] - Remove from group", "/ggroup clear [Group] - Clear a group", "/ggroup setDelay [Group] [time delay (in milli sec)c] - Set delay" };
/*      */ 
/*   29 */   public static final String[] help2 = { "------ Help page 2 ------", "/ghelp <page number> - help funcie", "/ggroup - help funcite", "------Gate Actions------", "/gOpen [Gate/Group] - Open a Gate/Group", "/gClose[Gate/Group] - Close a Gate/Group", "/gGroup timeGate [Gate/Group] [true/false] - Gate open and close by time!", "/gGroup redstone [Gate/Group] [true/false] look at the redstone - Add a redstone listener", "/gGroup mobKill [Gate/Group] [mob/del] <Kill Msg> - Gate open and close when the mob is slayen!", "/gGroup button [Gate/Group] [true/false] [time delay (in milli sec)] look at the button - Add a non-toggle button listener" };
/*      */ 
/*      */   public Commands(Plugin p, ArrayList<Gate> g, ArrayList<GateGroup> gg)
/*      */   {
/*   33 */     this.plugin = p;
/*   34 */     this.g = g;
/*   35 */     this.gg = gg;
/*      */   }
/*      */ 
/*      */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
/*   39 */     Player p = null;
/*   40 */     if ((cs instanceof Player)) {
/*   41 */       p = (Player)cs;
/*      */ 
/*   43 */       String cmd = cmnd.getName().toLowerCase();
/*      */ 
/*   45 */       if (cmd.equalsIgnoreCase("gp1")) {
/*   46 */         if ((cs.hasPermission("citygates.admin.p1")) || (cs.isOp()))
/*   47 */           gp1(p);
/*      */         else
/*   49 */           cs.sendMessage("You don't have Permission to use this Command!");
/*   50 */         return true;
/*   51 */       }if (cmd.equalsIgnoreCase("gp2")) {
/*   52 */         if ((cs.hasPermission("citygates.admin.p2")) || (cs.isOp()))
/*   53 */           gp2(p);
/*      */         else
/*   55 */           cs.sendMessage("You don't have Permission to use this Command!");
/*   56 */         return true;
/*   57 */       }if (cmd.equalsIgnoreCase("gcreate")) {
/*   58 */         if ((cs.hasPermission("citygates.admin.create")) || (cs.isOp())) {
/*   59 */           if ((strings.length == 1) || (strings.length == 2)) {
/*   60 */             gCreate(p, strings);
/*   61 */             return true;
/*      */           }
/*   63 */           return false;
/*      */         }
/*      */ 
/*   66 */         cs.sendMessage("You don't have Permission to use this Command!");
/*   67 */         return true;
/*      */       }
/*   69 */       if (cmd.equalsIgnoreCase("gredstone")) {
/*   70 */         if ((cs.hasPermission("citygates.admin.redstone")) || (cs.isOp())) {
/*   71 */           if (strings.length == 2) {
/*   72 */             gRedstone(p, strings);
/*   73 */             return true;
/*      */           }
/*   75 */           return false;
/*      */         }
/*      */ 
/*   78 */         cs.sendMessage("You don't have Permission to use this Command!");
/*   79 */         return true;
/*      */       }
/*   81 */       if (cmd.equalsIgnoreCase("gtimegate")) {
/*   82 */         if ((cs.hasPermission("citygates.admin.timegate")) || (cs.isOp())) {
/*   83 */           if (strings.length == 2) {
/*   84 */             gTimeGate(p, strings);
/*   85 */             return true;
/*      */           }
/*   87 */           return false;
/*      */         }
/*      */ 
/*   90 */         cs.sendMessage("You don't have Permission to use this Command!");
/*   91 */         return true;
/*      */       }
/*   93 */       if (cmd.equalsIgnoreCase("gkill")) {
/*   94 */         if ((cs.hasPermission("citygates.admin.kill")) || (cs.isOp())) {
/*   95 */           if (strings.length >= 2) {
/*   96 */             gKill(p, strings);
/*   97 */             return true;
/*      */           }
/*   99 */           return false;
/*      */         }
/*      */ 
/*  102 */         cs.sendMessage("You don't have Permission to use this Command!");
/*  103 */         return true;
/*      */       }
/*  105 */       if (cmd.equalsIgnoreCase("glist")) {
/*  106 */         if ((cs.hasPermission("citygates.user.list")) || (cs.isOp())) {
/*  107 */           if (strings.length < 3) {
/*  108 */             gList(p, strings);
/*  109 */             return true;
/*      */           }
/*      */         } else {
/*  112 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  113 */           return true;
/*      */         }
/*  115 */       } else if (cmd.equalsIgnoreCase("gremove")) {
/*  116 */         if ((cs.hasPermission("citygates.admin.remove")) || (cs.isOp())) {
/*  117 */           if (strings.length == 1) {
/*  118 */             gRemove(p, strings);
/*  119 */             return true;
/*  120 */           }if (strings.length == 2) {
/*  121 */             gRemoveAll(p, strings);
/*  122 */             p.sendMessage("[CityGates] Removing: " + strings[0]);
/*  123 */             return true;
/*      */           }
/*      */         } else {
/*  126 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  127 */           return true;
/*      */         }
/*  129 */       } else if (cmd.equalsIgnoreCase("gopen")) {
/*  130 */         if ((cs.hasPermission("citygates.user.open")) || (cs.isOp())) {
/*  131 */           if (strings.length == 1) {
/*  132 */             gOpen(p, strings);
/*  133 */             return true;
/*      */           }
/*      */         } else {
/*  136 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  137 */           return true;
/*      */         }
/*  139 */       } else if (cmd.equalsIgnoreCase("gclose")) {
/*  140 */         if ((cs.hasPermission("citygates.user.close")) || (cs.isOp())) {
/*  141 */           if (strings.length == 1) {
/*  142 */             gClose(p, strings);
/*  143 */             return true;
/*      */           }
/*      */         } else {
/*  146 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  147 */           return true;
/*      */         }
/*  149 */       } else if (cmd.equalsIgnoreCase("ginfo")) {
/*  150 */         if ((cs.hasPermission("citygates.user.info")) || (cs.isOp())) {
/*  151 */           if (strings.length == 1) {
/*  152 */             gInfo(p, strings);
/*  153 */             return true;
/*      */           }
/*      */         } else {
/*  156 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  157 */           return true;
/*      */         }
/*  159 */       } else if (cmd.equalsIgnoreCase("clear")) {
/*  160 */         if ((cs.hasPermission("citygates.admin.clear")) || (cs.isOp())) {
/*  161 */           Clear(p, strings);
/*      */         } else {
/*  163 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  164 */           return true;
/*      */         }
/*      */       } else {
/*  167 */         if (cmd.equalsIgnoreCase("gkillarea"))
/*      */         {
/*  169 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  170 */           return true;
/*      */         }
/*  172 */         if (cmd.equalsIgnoreCase("gbutton")) {
/*  173 */           if ((cs.hasPermission("citygates.admin.button")) || (cs.isOp())) {
/*  174 */             if (strings.length == 2) {
/*  175 */               if (strings[1].equalsIgnoreCase("false")) {
/*  176 */                 gRemoveButton(p, strings[0]);
/*  177 */                 return true;
/*      */               }
/*  179 */             } else if (strings.length == 3) {
/*  180 */               gButton(p, strings);
/*  181 */               return true;
/*      */             }
/*      */           } else {
/*  184 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  185 */             return true;
/*      */           }
/*      */         } else {
/*  188 */           if (cmd.equalsIgnoreCase("ggroup")) {
/*  189 */             gGroup(p, strings);
/*  190 */             return true;
/*  191 */           }if (cmd.equalsIgnoreCase("ghelp")) {
/*  192 */             if ((cs.hasPermission("citygates.user.help")) || (cs.isOp())) {
/*  193 */               if (strings.length == 0)
/*  194 */                 p.sendMessage(help);
/*  195 */               else if (strings[0].equalsIgnoreCase("1"))
/*  196 */                 p.sendMessage(help);
/*  197 */               else if (strings[0].equalsIgnoreCase("2"))
/*  198 */                 p.sendMessage(help2);
/*      */               else {
/*  200 */                 p.sendMessage("Max pages = 2");
/*      */               }
/*  202 */               return true;
/*      */             }
/*  204 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  205 */             return true;
/*      */           }
/*  207 */           if (cmd.equalsIgnoreCase("greload")) {
/*  208 */             if ((cs.hasPermission("citygates.admin.reload")) || (cs.isOp())) {
/*  209 */               gReloadConfig();
/*  210 */               p.sendMessage("[CityGates] Reload Complete!");
/*  211 */               return true;
/*      */             }
/*  213 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  214 */             return true;
/*      */           }
/*  216 */           if (cmd.equalsIgnoreCase("gshow")) {
/*  217 */             if ((cs.hasPermission("citygates.admin.show")) || (cs.isOp())) {
/*  218 */               showSelection();
/*  219 */               return true;
/*      */             }
/*  221 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  222 */             return true;
/*      */           }
/*  224 */           if (cmd.equalsIgnoreCase("gsetperm")) {
/*  225 */             if ((cs.hasPermission("citygates.admin.setperm")) || (cs.isOp())) {
/*  226 */               if (strings.length == 3) {
/*  227 */                 setPerm(p, strings);
/*  228 */                 return true;
/*      */               }
/*  230 */               return false;
/*      */             }
/*      */ 
/*  233 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  234 */             return true;
/*      */           }
/*  236 */           if (cmd.equalsIgnoreCase("gsetclose")) {
/*  237 */             if ((cs.hasPermission("citygates.admin.setopen")) || (cs.isOp())) {
/*  238 */               if (strings.length == 1) {
/*  239 */                 gsetOpen(p, strings);
/*  240 */                 p.sendMessage("[CityGates] Gate updated!");
/*  241 */                 return true;
/*      */               }
/*  243 */               return false;
/*      */             }
/*      */ 
/*  246 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  247 */             return true;
/*      */           }
/*  249 */           if (cmd.equalsIgnoreCase("gsetopen")) {
/*  250 */             if ((cs.hasPermission("citygates.admin.setopen")) || (cs.isOp())) {
/*  251 */               if (strings.length == 1) {
/*  252 */                 gsetClose(p, strings);
/*  253 */                 p.sendMessage("[CityGates] Gate updated!");
/*  254 */                 return true;
/*      */               }
/*  256 */               return false;
/*      */             }
/*      */ 
/*  259 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  260 */             return true;
/*      */           }
/*  262 */           if (cmd.equalsIgnoreCase("gsetanimation")) {
/*  263 */             if ((cs.hasPermission("citygates.admin.addanimation")) || (cs.isOp())) {
/*  264 */               if (strings.length == 2) {
/*  265 */                 gAnimation(p, strings);
/*  266 */                 return true;
/*      */               }
/*  268 */               return false;
/*      */             }
/*      */ 
/*  271 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  272 */             return true;
/*      */           }
/*      */ 
/*  275 */           if (!(cs instanceof Player)) {
/*  276 */             cs.sendMessage("This command can only be execute by console!");
/*  277 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  283 */     return false;
/*      */   }
/*      */ 
/*      */   private void gp1(Player p)
/*      */   {
/*  288 */     if (p != null) {
/*  289 */       Block b = p.getTargetBlock(null, 50);
/*  290 */       this.p1 = b.getLocation();
/*  291 */       Material m = b.getType();
/*  292 */       p.sendMessage("[CityGates] Select Point 1 - X: " + this.p1.getBlockX() + " Y: " + this.p1.getBlockY() + " Z: " + this.p1.getBlockZ());
/*      */     } else {
/*  294 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gp2(Player p) {
/*  299 */     if (p == null) {
/*  300 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     } else {
/*  302 */       this.p2 = p.getTargetBlock(null, 50).getLocation();
/*  303 */       p.sendMessage("[CityGates] Select Point 2 - X: " + this.p2.getBlockX() + " Y: " + this.p2.getBlockY() + " Z: " + this.p2.getBlockZ());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gCreate(Player player, String[] args) {
/*  308 */     if (player == null) {
/*  309 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     } else {
/*  311 */       boolean exists = false;
/*  312 */       for (int a = 0; a < this.g.size(); a++) {
/*  313 */         if (args[0].equals(((Gate)this.g.get(a)).gd.name)) {
/*  314 */           exists = true;
/*  315 */           player.sendMessage("[CityGates] This Gate already exists!");
/*      */         }
/*      */       }
/*  318 */       for (int a = 0; a < this.gg.size(); a++) {
/*  319 */         if (args[0].equals(((GateGroup)this.gg.get(a)).name)) {
/*  320 */           exists = true;
/*  321 */           player.sendMessage("[CityGates] A Group with this name already exists!");
/*      */         }
/*      */       }
/*  324 */       if (this.p1 == null) {
/*  325 */         player.sendMessage("[CityGates] You have to select point 1 first");
/*  326 */         player.sendMessage("[CityGates] Command: /gp1");
/*  327 */       } else if (this.p2 == null) {
/*  328 */         player.sendMessage("[CityGates] You have to select point 2 first");
/*  329 */         player.sendMessage("[CityGates] Command: /gp2");
/*  330 */       } else if (!this.p1.getWorld().equals(this.p2.getWorld())) {
/*  331 */         if (!this.p1.getWorld().equals(player.getWorld())) {
/*  332 */           player.sendMessage("[CityGates] You have to select point 1 first");
/*  333 */           player.sendMessage("[CityGates] Command: /gp1");
/*      */         } else {
/*  335 */           player.sendMessage("[CityGates] You have to select point 2 first");
/*  336 */           player.sendMessage("[CityGates] Command: /gp2");
/*      */         }
/*  338 */       } else if (exists) {
/*  339 */         player.sendMessage("[CityGates] " + args[0] + " already exists!");
/*      */       } else {
/*  341 */         int[] l1 = { this.p1.getBlockX(), this.p1.getBlockY(), this.p1.getBlockZ() };
/*      */ 
/*  343 */         int[] l2 = { this.p2.getBlockX(), this.p2.getBlockY(), this.p2.getBlockZ() };
/*      */ 
/*  345 */         ArrayList material = new ArrayList();
/*  346 */         byte[] bytes = new byte[1];
/*  347 */         material.add(new MaterialId(0, bytes[0]));
/*  348 */         Gate newGate = new Gate(new GateData(args[0], l1, l2, player.getWorld().getName(), getMaterials(l1, l2, this.p1.getWorld()), material), this.plugin);
/*  349 */         if (args.length == 1) {
/*  350 */           this.g.add(newGate);
/*  351 */           player.sendMessage("[CityGates] " + args[0] + " Succesfull created!");
/*  352 */           showSelection();
/*  353 */         } else if (args.length == 2) {
/*  354 */           int dir = Directions.getDirection(args[1]);
/*  355 */           if (createAnimation(newGate, dir)) {
/*  356 */             player.sendMessage("[CityGates] " + args[0] + " Succesfull created!");
/*  357 */             showSelection();
/*      */           } else {
/*  359 */             player.sendMessage("[CityGates] Unknow Direction: " + args[1]);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRedstone(Player player, String[] args) {
/*  367 */     if (player == null) {
/*  368 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     }
/*      */     else
/*      */     {
/*      */       Gate gate;
/*  373 */       if ((gate = testGate(args[0])) == null) {
/*  374 */         GateGroup group = testGateGroup(args[0]);
/*  375 */         if (group == null) {
/*  376 */           player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */         }
/*  378 */         else if (args[1].equals("true")) {
/*  379 */           Location l = player.getTargetBlock(null, 50).getLocation();
/*  380 */           group.pr[0] = l.getBlockX();
/*  381 */           group.pr[1] = l.getBlockY();
/*  382 */           group.pr[2] = l.getBlockZ();
/*  383 */           group.redstoneListener = true;
/*  384 */           player.sendMessage("[CityGates] RedstoneListener add to " + args[0] + "on Block -" + " X: " + group.pr[0] + " Y: " + group.pr[1] + " Z: " + group.pr[2]);
/*      */         }
/*  386 */         else if (args[1].equals("false")) {
/*  387 */           group.redstoneListener = false;
/*  388 */           player.sendMessage("[CityGates] RedstoneListener Removed!");
/*      */         } else {
/*  390 */           player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */         }
/*      */ 
/*      */       }
/*  394 */       else if (args[1].equals("true")) {
/*  395 */         gate.setRedstoneListener(player.getTargetBlock(null, 50).getLocation());
/*  396 */         player.sendMessage("[CityGates] RedstoneListener add to " + args[0] + "on Block -" + " X: " + gate.gd.pr[0] + " Y: " + gate.gd.pr[1] + " Z: " + gate.gd.pr[2]);
/*      */       }
/*  398 */       else if (args[1].equals("false")) {
/*  399 */         gate.removeRedstoneListener();
/*  400 */         player.sendMessage("[CityGates] RedstoneListener Removed!");
/*      */       } else {
/*  402 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gKill(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  410 */     if ((gate = testGate(args[0])) == null) {
/*  411 */       GateGroup group = testGateGroup(args[0]);
/*  412 */       if (group == null)
/*  413 */         player.sendMessage("[CityGates] " + args[0] + "not found!");
/*      */       else
/*      */         try {
/*  416 */           if (args[1].equalsIgnoreCase("del")) {
/*  417 */             group.mobKill = false;
/*      */           } else {
/*  419 */             EntityType mob = EntityType.fromName(args[1]);
/*  420 */             group.mob = mob.getName();
/*  421 */             group.mobKill = true;
/*  422 */             player.sendMessage("[CityGates] " + args[0] + " Open when a " + mob.getName() + " is slayen in this World!");
/*  423 */             if (args.length > 2) {
/*  424 */               String mes = "";
/*  425 */               for (int a = 2; a < args.length; a++) {
/*  426 */                 mes = mes + " " + args[a];
/*  427 */                 group.killMsg = mes;
/*      */               }
/*      */             }
/*      */           }
/*      */         } catch (Exception e) {
/*  432 */           player.sendMessage("[CityGates] " + args[1] + " Invallid Entity!");
/*      */         }
/*      */     }
/*      */     else {
/*      */       try {
/*  437 */         if (args[1].equalsIgnoreCase("del")) {
/*  438 */           gate.removeMobKill();
/*      */         } else {
/*  440 */           EntityType mob = EntityType.fromName(args[1]);
/*  441 */           gate.setMobKill(mob);
/*  442 */           player.sendMessage("[CityGates] " + args[0] + " Open when a " + mob.getName() + " is slayen in this World!");
/*  443 */           if (args.length > 2) {
/*  444 */             String mes = "";
/*  445 */             for (int a = 2; a < args.length; a++) {
/*  446 */               mes = mes + " " + args[a];
/*  447 */               gate.gd.killMsg = mes;
/*      */             }
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/*  452 */         player.sendMessage("[CityGates] " + args[1] + " Invallid Entity!");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gTimeGate(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  460 */     if ((gate = testGate(args[0])) == null) {
/*  461 */       GateGroup group = testGateGroup(args[0]);
/*  462 */       if (group == null) {
/*  463 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  465 */       else if (args[1].equalsIgnoreCase("true")) {
/*  466 */         group.timegate = true;
/*  467 */         player.sendMessage("[CityGates] " + args[0] + " Open and Close by time!");
/*  468 */       } else if (args[1].equalsIgnoreCase("false")) {
/*  469 */         group.timegate = false;
/*  470 */         player.sendMessage("[CityGates] Succesfully removed timeGates!");
/*      */       } else {
/*  472 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */       }
/*      */ 
/*      */     }
/*  476 */     else if (args[1].equalsIgnoreCase("true")) {
/*  477 */       gate.setTimeGate();
/*  478 */       player.sendMessage("[CityGates] " + args[0] + " Open and Close by time!");
/*  479 */     } else if (args[1].equalsIgnoreCase("false")) {
/*  480 */       gate.removeTimeGate();
/*  481 */       player.sendMessage("[CityGates] Succesfully removed timeGates!");
/*      */     } else {
/*  483 */       player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gList(Player player, String[] args)
/*      */   {
/*  489 */     if (args.length == 0) {
/*  490 */       if (this.g.size() == 0)
/*  491 */         player.sendMessage("No Gates Found!");
/*      */       else {
/*  493 */         for (int a = 0; a < this.g.size(); a++) {
/*  494 */           player.sendMessage("[Gate]" + String.valueOf(a + 1) + ": " + ((Gate)this.g.get(a)).gd.name);
/*      */         }
/*      */       }
/*  497 */       if (this.gg.size() == 0)
/*  498 */         player.sendMessage("No Groups Found!");
/*      */       else {
/*  500 */         for (int a = 0; a < this.gg.size(); a++)
/*  501 */           player.sendMessage("[Group] " + String.valueOf(a + 1) + ": " + ((GateGroup)this.gg.get(a)).name);
/*      */       }
/*      */     }
/*  504 */     else if (args.length == 1) {
/*  505 */       if (args[0].equalsIgnoreCase("group")) {
/*  506 */         if (this.gg.size() == 0)
/*  507 */           player.sendMessage("No Groups Found!");
/*      */         else {
/*  509 */           for (int a = 0; a < this.gg.size(); a++)
/*  510 */             player.sendMessage("[Group] " + String.valueOf(a + 1) + ": " + ((GateGroup)this.gg.get(a)).name);
/*      */         }
/*      */       }
/*  513 */       else if (args[0].equalsIgnoreCase("gate")) {
/*  514 */         if (this.g.size() == 0)
/*  515 */           player.sendMessage("No Gates Found!");
/*      */         else {
/*  517 */           for (int a = 0; a < this.g.size(); a++)
/*  518 */             player.sendMessage("[Gate]" + String.valueOf(a + 1) + ": " + ((Gate)this.g.get(a)).gd.name);
/*      */         }
/*      */       }
/*      */       else
/*  522 */         player.sendMessage("[CityGate] /list [group/gate]");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRemove(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  529 */     if ((gate = testGate(args[0])) == null) {
/*  530 */       GateGroup group = testGateGroup(args[0]);
/*  531 */       if (group == null)
/*  532 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       else
/*  534 */         for (int a = 0; a < this.gg.size(); a++)
/*  535 */           if (((GateGroup)this.gg.get(a)).name.equalsIgnoreCase(args[0])) {
/*  536 */             this.gg.remove(a);
/*  537 */             player.sendMessage("[CityGates] Removing: " + args[0]);
/*      */           }
/*      */     }
/*      */     else
/*      */     {
/*  542 */       for (int a = 0; a < this.g.size(); a++)
/*  543 */         if (((Gate)this.g.get(a)).gd.name.equalsIgnoreCase(args[0])) {
/*  544 */           this.g.remove(a);
/*  545 */           player.sendMessage("[CityGates] Removing: " + args[0]);
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRemoveAll(Player player, String[] args)
/*      */   {
/*  552 */     Gate gate = testGate(args[0]);
/*  553 */     if (gate == null) {
/*  554 */       GateGroup group = testGateGroup(args[0]);
/*  555 */       if (group == null) {
/*  556 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       } else {
/*  558 */         for (int a = 0; a < group.g.size(); a++)
/*      */           try {
/*  560 */             Gate gate2 = (Gate)group.g.get(a);
/*  561 */             gRemoveAll(player, new String[] { gate2.gd.name });
/*      */           } catch (Exception e) {
/*      */             try {
/*  564 */               GateGroup group2 = (GateGroup)group.g.get(a);
/*  565 */               gRemoveAll(player, new String[] { group2.name });
/*      */             } catch (Exception localException1) {
/*      */             }
/*      */           }
/*  569 */         for (int a = 0; a < this.gg.size(); a++)
/*  570 */           if (((GateGroup)this.gg.get(a)).name.equalsIgnoreCase(args[0]))
/*  571 */             this.gg.remove(a);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  576 */       for (int a = 0; a < this.g.size(); a++)
/*  577 */         if (((Gate)this.g.get(a)).gd.name.equalsIgnoreCase(args[0]))
/*  578 */           this.g.remove(a);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gOpen(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  585 */     if ((gate = testGate(args[0])) == null) {
/*  586 */       GateGroup group = testGateGroup(args[0]);
/*  587 */       if (group == null) {
/*  588 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  590 */       else if (((group.openPerm) && (player.hasPermission("citygates.user.gate." + group.name))) || ((group.openPerm) && (player.hasPermission("citygates.user.gate.*"))))
/*  591 */         group.Open();
/*  592 */       else if (!group.openPerm)
/*  593 */         group.Open();
/*      */       else {
/*  595 */         player.sendMessage("You don't have Permssion to use this Gate!");
/*      */       }
/*      */ 
/*      */     }
/*  599 */     else if (((gate.gd.openPerm) && (player.hasPermission("citygates.user.gate." + gate.gd.name))) || ((gate.gd.openPerm) && (player.hasPermission("citygates.user.gate.*")))) {
/*  600 */       gate.Open();
/*  601 */     } else if (!gate.gd.openPerm) {
/*  602 */       gate.Open();
/*      */     } else {
/*  604 */       player.sendMessage("You don't have Permssion to use this Gate!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gClose(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  611 */     if ((gate = testGate(args[0])) == null) {
/*  612 */       GateGroup group = testGateGroup(args[0]);
/*  613 */       if (group == null) {
/*  614 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  616 */       else if (((group.closePerm) && (player.hasPermission("citygates.user.gate." + group.name))) || ((group.closePerm) && (player.hasPermission("citygates.user.gate.*"))))
/*  617 */         group.Close();
/*  618 */       else if (!group.closePerm)
/*  619 */         group.Close();
/*      */       else {
/*  621 */         player.sendMessage("You don't have Permssion to use this Gate!");
/*      */       }
/*      */ 
/*      */     }
/*  625 */     else if (((gate.gd.closePerm) && (player.hasPermission("citygates.user.gate." + gate.gd.name))) || ((gate.gd.closePerm) && (player.hasPermission("citygates.user.gate.*")))) {
/*  626 */       gate.Close();
/*  627 */     } else if (!gate.gd.closePerm) {
/*  628 */       gate.Close();
/*      */     } else {
/*  630 */       player.sendMessage("You don't have Permssion to use this Gate!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gInfo(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  637 */     if ((gate = testGate(args[0])) == null) {
/*  638 */       GateGroup group = testGateGroup(args[0]);
/*  639 */       if (group == null) {
/*  640 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       } else {
/*  642 */         player.sendMessage("[CityGates] Name: " + group.name);
/*  643 */         player.sendMessage("[CityGates] World: " + group.world);
/*  644 */         player.sendMessage("[CityGates] Delay: " + group.delay);
/*  645 */         player.sendMessage("[CityGates] Open: " + group.open);
/*  646 */         player.sendMessage("[CityGates] TimeGate: " + group.timegate);
/*  647 */         player.sendMessage("[CityGates] redstoneListener: " + group.redstoneListener);
/*  648 */         player.sendMessage("[CityGates] Redstone Point: " + group.pr[0] + ", " + group.pr[1] + ", " + group.pr[2]);
/*  649 */         player.sendMessage("[CityGates] Kill Listener: " + group.mobKill);
/*  650 */         player.sendMessage("[CityGates] Kill Mob: " + group.mob);
/*  651 */         player.sendMessage("[CityGates] Kill Message: " + group.killMsg);
/*  652 */         player.sendMessage("[CityGates] Button Listener: " + group.buttonListener);
/*  653 */         player.sendMessage("[CityGates] Button Interval: " + group.delay);
/*  654 */         player.sendMessage("[CityGates] Open Permission: " + group.openPerm);
/*  655 */         player.sendMessage("[CityGates] Close Permission: " + group.closePerm);
/*  656 */         player.sendMessage("[CityGates] Kill Permission: " + group.killPerm);
/*  657 */         player.sendMessage("[CityGates] ButtonPermission: " + group.buttonPerm);
/*  658 */         String Children = "";
/*  659 */         if (group.g.isEmpty())
/*  660 */           Children = "No Gates!";
/*      */         else
/*  662 */           for (int a = 0; a < group.g.size(); a++)
/*      */             try {
/*  664 */               Gate gate2 = (Gate)group.g.get(a);
/*  665 */               if (a == 0)
/*  666 */                 Children = gate2.gd.name;
/*      */               else
/*  668 */                 Children = Children + ", " + gate2.gd.name;
/*      */             }
/*      */             catch (Exception e) {
/*      */               try {
/*  672 */                 GateGroup gategroup = (GateGroup)group.g.get(a);
/*  673 */                 if (a == 0)
/*  674 */                   Children = gategroup.name;
/*      */                 else
/*  676 */                   Children = Children + ", " + gategroup.name;
/*      */               }
/*      */               catch (Exception localException1)
/*      */               {
/*      */               }
/*      */             }
/*  682 */         player.sendMessage("[CityGates] Gates: " + Children);
/*      */       }
/*      */     } else {
/*  685 */       GateData gd = gate.gd;
/*  686 */       player.sendMessage("[CityGates] Name: " + gd.name);
/*  687 */       player.sendMessage("[CityGates] Point 1: " + gd.p1[0] + ", " + gd.p1[1] + ", " + gd.p1[2]);
/*  688 */       player.sendMessage("[CityGates] Point 2: " + gd.p2[0] + ", " + gd.p2[1] + ", " + gd.p2[2]);
/*  689 */       player.sendMessage("[CityGates] World: " + gd.World);
/*  690 */       player.sendMessage("[CityGates] Redstone Listner: " + gd.redstoneListener);
/*  691 */       player.sendMessage("[CityGates] Redstone Point: " + gd.pr[0] + ", " + gd.pr[1] + ", " + gd.pr[2]);
/*  692 */       player.sendMessage("[CityGates] TimeGate: " + gd.timeGate);
/*  693 */       player.sendMessage("[CityGates] Kill Listener: " + gd.mobKill + " Mob: " + gd.mob);
/*  694 */       player.sendMessage("[CityGates] Kill Mob: " + gd.mob);
/*  695 */       player.sendMessage("[CityGates] Kill Message: " + gd.killMsg);
/*  696 */       player.sendMessage("[CityGates] Button Listener: " + gd.buttonListener);
/*  697 */       player.sendMessage("[CityGates] Button Interval: " + gd.ButtonInterval);
/*  698 */       player.sendMessage("[CityGates] Open Permission: " + gd.openPerm);
/*  699 */       player.sendMessage("[CityGates] Close Permission: " + gd.closePerm);
/*  700 */       player.sendMessage("[CityGates] Kill Permission: " + gd.killPerm);
/*  701 */       player.sendMessage("[CityGates] ButtonPermission: " + gd.buttonPerm);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Gate testGate(String GateName) {
/*  706 */     for (int a = 0; a < this.g.size(); a++) {
/*  707 */       if (((Gate)this.g.get(a)).gd.name.equals(GateName)) {
/*  708 */         return (Gate)this.g.get(a);
/*      */       }
/*      */     }
/*  711 */     return null;
/*      */   }
/*      */ 
/*      */   private GateGroup testGateGroup(String gateGroupname) {
/*  715 */     for (int a = 0; a < this.gg.size(); a++) {
/*  716 */       if (((GateGroup)this.gg.get(a)).name.equals(gateGroupname)) {
/*  717 */         return (GateGroup)this.gg.get(a);
/*      */       }
/*      */     }
/*  720 */     return null;
/*      */   }
/*      */ 
/*      */   private void Clear(Player player, String[] args) {
/*  724 */     if (args.length == 0)
/*  725 */       player.getInventory().clear();
/*  726 */     else if (args.length == 1)
/*      */       try {
/*  728 */         this.plugin.getServer().getPlayer(args[0]).getInventory().clear();
/*      */       } catch (Exception e) {
/*  730 */         player.sendMessage("[CityGates] No Player found with the name: " + args[1]);
/*      */       }
/*      */     else
/*  733 */       player.sendMessage("[CityGates] to many arguments!");
/*      */   }
/*      */ 
/*      */   private void gKillArea(Player player, String[] args)
/*      */   {
/*  738 */     if (player == null) {
/*  739 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     }
/*      */     else
/*      */     {
/*      */       Gate gate;
/*  744 */       if ((gate = testGate(args[0])) == null) {
/*  745 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  747 */       else if (this.p1 == null) {
/*  748 */         player.sendMessage("[CityGates] You have to select point 1 first");
/*  749 */         player.sendMessage("[CityGates] Command: /gp1");
/*  750 */       } else if (this.p2 == null) {
/*  751 */         player.sendMessage("[CityGates] You have to select point 2 first");
/*  752 */         player.sendMessage("[CityGates] Command: /gp2");
/*  753 */       } else if (!this.p1.getWorld().equals(this.p2.getWorld())) {
/*  754 */         if (!this.p1.getWorld().equals(player.getWorld())) {
/*  755 */           player.sendMessage("[CityGates] You have to select point 1 first");
/*  756 */           player.sendMessage("[CityGates] Command: /gp1");
/*      */         } else {
/*  758 */           player.sendMessage("[CityGates] You have to select point 2 first");
/*  759 */           player.sendMessage("[CityGates] Command: /gp2");
/*      */         }
/*      */       }
/*  762 */       else if (args[1].equalsIgnoreCase("true")) {
/*  763 */         gate.setKillArea(this.p1, this.p2);
/*  764 */         player.sendMessage("[CityGates] Kill area set!");
/*  765 */       } else if (args[1].equalsIgnoreCase("false")) {
/*  766 */         gate.removeKillArea();
/*  767 */         player.sendMessage("[CityGates] Removed Kill area!");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRemoveButton(Player player, String gate)
/*      */   {
/*  774 */     Gate g = testGate(gate);
/*  775 */     if (g == null) {
/*  776 */       GateGroup group = testGateGroup(gate);
/*  777 */       if (group == null) {
/*  778 */         player.sendMessage("[CityGates] " + gate + " not found!");
/*      */       } else {
/*  780 */         group.buttonListener = false;
/*  781 */         player.sendMessage("[CityGates] " + gate + " removed!");
/*      */       }
/*      */     } else {
/*  784 */       g.gd.buttonListener = false;
/*  785 */       player.sendMessage("[CityGates] " + gate + " removed!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gButton(Player player, String[] args) {
/*  790 */     Gate g = testGate(args[0]);
/*  791 */     if (g == null) {
/*  792 */       GateGroup group = testGateGroup(args[0]);
/*  793 */       if (group == null) {
/*  794 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  796 */       else if (args[1].equalsIgnoreCase("true"))
/*      */         try {
/*  798 */           group.ButtonInterval = Integer.parseInt(args[2]);
/*  799 */           Block b = player.getTargetBlock(null, 50);
/*  800 */           if (b.getType().equals(Material.STONE_BUTTON)) {
/*  801 */             Location l = b.getLocation();
/*  802 */             group.button[0] = l.getBlockX();
/*  803 */             group.button[1] = l.getBlockY();
/*  804 */             group.button[2] = l.getBlockZ();
/*  805 */             group.buttonListener = true;
/*  806 */             player.sendMessage("[CityGates] add Button Listener");
/*      */           } else {
/*  808 */             player.sendMessage("[CityGates] You're not looking at a Block!");
/*      */           }
/*      */         } catch (Exception e) {
/*  811 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */         }
/*  813 */       else if (args[1].equalsIgnoreCase("false"))
/*  814 */         gRemoveButton(player, args[0]);
/*      */       else {
/*  816 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */       }
/*      */ 
/*      */     }
/*  820 */     else if (args[1].equalsIgnoreCase("true")) {
/*      */       try {
/*  822 */         g.gd.ButtonInterval = Integer.parseInt(args[2]);
/*  823 */         Block b = player.getTargetBlock(null, 50);
/*  824 */         if (b.getType().equals(Material.STONE_BUTTON)) {
/*  825 */           Location l = b.getLocation();
/*  826 */           g.gd.button[0] = l.getBlockX();
/*  827 */           g.gd.button[1] = l.getBlockY();
/*  828 */           g.gd.button[2] = l.getBlockZ();
/*  829 */           g.gd.buttonListener = true;
/*  830 */           player.sendMessage("[CityGates] add Button Listener");
/*      */         } else {
/*  832 */           player.sendMessage("[CityGates] You're not looking at a Block!");
/*      */         }
/*      */       } catch (Exception e) {
/*  835 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/*  837 */     } else if (args[1].equalsIgnoreCase("false")) {
/*  838 */       gRemoveButton(player, args[0]);
/*      */     } else {
/*  840 */       player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gGroup(Player player, String[] args)
/*      */   {
/*  846 */     if (args.length == 0) {
/*  847 */       player.sendMessage(new String[] { "/gGroup create [Group Name] - create a new Group", "/gGroup del [Group Name] - delete a Group", "/gGroup add [Group] [Gate] - add a Gate to a Group", "/gGroup remove [Group] [Gate] - remove a Gate from a Group", "/gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate", "/gGroup clear - removes all the Gates in a Group" });
/*      */     }
/*  849 */     else if (args.length > 0)
/*  850 */       if (args[0].equalsIgnoreCase("create")) {
/*  851 */         if (player.hasPermission("citygates.admin.group.create")) {
/*  852 */           if (args.length == 2) {
/*  853 */             GateGroup group = testGateGroup(args[1]);
/*  854 */             if (group == null) {
/*  855 */               Gate gate = testGate(args[1]);
/*  856 */               if (gate == null) {
/*  857 */                 group = new GateGroup(args[1], this.plugin);
/*  858 */                 group.world = player.getWorld().getName();
/*  859 */                 this.gg.add(group);
/*  860 */                 player.sendMessage("[CityGates] Create: " + args[1]);
/*      */               } else {
/*  862 */                 player.sendMessage("[CityGates] A Gate with this name already exists!");
/*      */               }
/*      */             } else {
/*  865 */               player.sendMessage("[CityGates] This Group already exists!");
/*      */             }
/*      */           } else {
/*  868 */             player.sendMessage("[CityGates] /gGroup create [Group Name] - create a new Group");
/*      */           }
/*      */         }
/*  871 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  873 */       else if (args[0].equalsIgnoreCase("del")) {
/*  874 */         if (player.hasPermission("citygates.admin.group.del")) {
/*  875 */           if (args.length == 2) {
/*  876 */             GateGroup group = testGateGroup(args[1]);
/*  877 */             if (group == null)
/*  878 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             else
/*  880 */               for (int a = 0; a < this.gg.size(); a++)
/*  881 */                 if (((GateGroup)this.gg.get(a)).name.equals(args[1])) {
/*  882 */                   this.gg.remove(a);
/*  883 */                   player.sendMessage("[CityGates] Deleting: " + args[1]);
/*      */                 }
/*      */           }
/*      */           else
/*      */           {
/*  888 */             player.sendMessage("[CityGates] /gGroup del [Group Name] - delete a Group");
/*      */           }
/*      */         }
/*  891 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  893 */       else if (args[0].equalsIgnoreCase("add")) {
/*  894 */         if (player.hasPermission("citygates.admin.group.add")) {
/*  895 */           if (args.length == 3) {
/*  896 */             GateGroup group = testGateGroup(args[1]);
/*  897 */             if (group == null) {
/*  898 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             } else {
/*  900 */               Gate gate = testGate(args[2]);
/*  901 */               if (gate == null) {
/*  902 */                 GateGroup gg = testGateGroup(args[2]);
/*  903 */                 if (gg == null) {
/*  904 */                   player.sendMessage("[CityGates] " + args[2] + " not found!");
/*  905 */                 } else if (gg.name.equals(group.name)) {
/*  906 */                   player.sendMessage("[CityGates] Parent is the same as the child!");
/*      */                 } else {
/*  908 */                   group.add(gg);
/*  909 */                   player.sendMessage("[CityGates]" + gg.name + " Added to: " + group.name);
/*      */                 }
/*      */               }
/*      */               else {
/*  913 */                 group.add(gate);
/*  914 */                 player.sendMessage("[CityGates] " + gate.gd.name + " Added to: " + group.name);
/*      */               }
/*      */             }
/*      */           } else {
/*  918 */             player.sendMessage("[CityGates] /gGroup add [Group] [Gate] - add a Gate to a Group");
/*      */           }
/*      */         }
/*  921 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  923 */       else if (args[0].equalsIgnoreCase("remove")) {
/*  924 */         if (player.hasPermission("citygates.admin.group.remove")) {
/*  925 */           if (args.length == 3) {
/*  926 */             GateGroup group = testGateGroup(args[1]);
/*  927 */             if (group == null) {
/*  928 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             } else {
/*  930 */               Gate gate = testGate(args[2]);
/*  931 */               if (gate == null) {
/*  932 */                 GateGroup gg = testGateGroup(args[0]);
/*  933 */                 if (gg == null) {
/*  934 */                   player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */                 } else {
/*  936 */                   group.remove(gg);
/*  937 */                   player.sendMessage("[CityGates] " + gg.name + " Removed from: " + group.name);
/*      */                 }
/*      */               } else {
/*  940 */                 group.remove(gate);
/*  941 */                 player.sendMessage("[CityGates] " + gate.gd.name + " Removed from: " + group.name);
/*      */               }
/*      */             }
/*      */           } else {
/*  945 */             player.sendMessage("[CityGates] /gGroup remove [Group] [Gate] - remove a Gate from a Group");
/*      */           }
/*      */         }
/*  948 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  950 */       else if (args[0].equalsIgnoreCase("setDelay")) {
/*  951 */         if (player.hasPermission("citygates.admin.group.delay")) {
/*  952 */           if (args.length == 3) {
/*  953 */             GateGroup group = testGateGroup(args[1]);
/*  954 */             if (group == null)
/*  955 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             else
/*      */               try {
/*  958 */                 group.delay = Integer.parseInt(args[2]);
/*  959 */                 player.sendMessage("[CityGates] " + args[1] + " setDelay: " + args[2]);
/*      */               } catch (Exception e) {
/*  961 */                 player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */               }
/*      */           }
/*      */           else {
/*  965 */             player.sendMessage("[CityGates] /gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate");
/*      */           }
/*      */         }
/*  968 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  970 */       else if (args[0].equalsIgnoreCase("clear")) {
/*  971 */         if (player.hasPermission("citygates.admin.group.clear")) {
/*  972 */           if (args.length == 2) {
/*  973 */             GateGroup group = testGateGroup(args[1]);
/*  974 */             if (group == null) {
/*  975 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             } else {
/*  977 */               group.Clear();
/*  978 */               player.sendMessage("[CityGates] " + args[1] + " is Cleared!");
/*      */             }
/*      */           } else {
/*  981 */             player.sendMessage("[CityGates] /gGroup clear - removes all the Gates in a Group");
/*      */           }
/*      */         }
/*  984 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*      */       else
/*  987 */         player.sendMessage(new String[] { "/gGroup create [Group Name] - create a new Group", "/gGroup del [Group Name] - delete a Group", "/gGroup add [Group] [Gate] - add a Gate to a Group", "/gGroup remove [Group] [Gate] - remove a Gate from a Group", "/gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate", "/gGroup clear - removes all the Gates in a Group" });
/*      */   }
/*      */ 
/*      */   private boolean createAnimation(Gate g, int dir)
/*      */   {
/*  992 */     g.Close();
/*  993 */     ArrayList material = new ArrayList();
/*  994 */     byte[] bytes = new byte[1];
/*  995 */     material.add(new MaterialId(0, bytes[0]));
/*  996 */     if (dir == 0)
/*  997 */       return false;
/*  998 */     if (dir == 1) {
/*  999 */       for (int a = 0; a < this.g.size(); a++) {
/* 1000 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1001 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1004 */       int[] x = convertPoints(g.gd.p1[0], g.gd.p2[0]);
/* 1005 */       int x1 = x[0];
/* 1006 */       int x2 = x[1];
/* 1007 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1008 */       gategroup.world = g.gd.World;
/* 1009 */       gategroup.setDelay(1000L);
/* 1010 */       int count = 0;
/* 1011 */       for (int a = x2; a >= x1; a--) {
/* 1012 */         count++;
/* 1013 */         int[] l1 = { a, g.gd.p1[1], g.gd.p1[2] };
/*      */ 
/* 1015 */         int[] l2 = { a, g.gd.p2[1], g.gd.p2[2] };
/*      */ 
/* 1017 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1018 */         this.g.add(gate);
/* 1019 */         gategroup.add(gate);
/*      */       }
/* 1021 */       this.gg.add(gategroup);
/* 1022 */       return true;
/* 1023 */     }if (dir == 3) {
/* 1024 */       for (int a = 0; a < this.g.size(); a++) {
/* 1025 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1026 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1029 */       int[] x = convertPoints(g.gd.p1[0], g.gd.p2[0]);
/* 1030 */       int x1 = x[0];
/* 1031 */       int x2 = x[1];
/* 1032 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1033 */       gategroup.world = g.gd.World;
/* 1034 */       gategroup.setDelay(1000L);
/* 1035 */       int count = 0;
/* 1036 */       for (int a = x1; a <= x2; a++) {
/* 1037 */         count++;
/* 1038 */         int[] l1 = { a, g.gd.p1[1], g.gd.p1[2] };
/*      */ 
/* 1040 */         int[] l2 = { a, g.gd.p2[1], g.gd.p2[2] };
/*      */ 
/* 1042 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1043 */         this.g.add(gate);
/* 1044 */         gategroup.add(gate);
/*      */       }
/* 1046 */       this.gg.add(gategroup);
/* 1047 */       return true;
/* 1048 */     }if (dir == 2) {
/* 1049 */       for (int a = 0; a < this.g.size(); a++) {
/* 1050 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1051 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1054 */       int[] z = convertPoints(g.gd.p1[2], g.gd.p2[2]);
/* 1055 */       int z1 = z[0];
/* 1056 */       int z2 = z[1];
/* 1057 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1058 */       gategroup.world = g.gd.World;
/* 1059 */       gategroup.setDelay(1000L);
/* 1060 */       int count = 0;
/* 1061 */       for (int a = z2; a >= z1; a--) {
/* 1062 */         count++;
/* 1063 */         int[] l1 = { g.gd.p1[0], g.gd.p1[1], a };
/*      */ 
/* 1065 */         int[] l2 = { g.gd.p2[0], g.gd.p2[1], a };
/*      */ 
/* 1067 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1068 */         this.g.add(gate);
/* 1069 */         gategroup.add(gate);
/*      */       }
/* 1071 */       this.gg.add(gategroup);
/* 1072 */       return true;
/* 1073 */     }if (dir == 4) {
/* 1074 */       for (int a = 0; a < this.g.size(); a++) {
/* 1075 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1076 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1079 */       int[] z = convertPoints(g.gd.p1[2], g.gd.p2[2]);
/* 1080 */       int z1 = z[0];
/* 1081 */       int z2 = z[1];
/* 1082 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1083 */       gategroup.world = g.gd.World;
/* 1084 */       gategroup.setDelay(1000L);
/* 1085 */       int count = 0;
/* 1086 */       for (int a = z1; a <= z2; a++) {
/* 1087 */         count++;
/* 1088 */         int[] l1 = { g.gd.p1[0], g.gd.p1[1], a };
/*      */ 
/* 1090 */         int[] l2 = { g.gd.p2[0], g.gd.p2[1], a };
/*      */ 
/* 1092 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1093 */         this.g.add(gate);
/* 1094 */         gategroup.add(gate);
/*      */       }
/* 1096 */       this.gg.add(gategroup);
/* 1097 */       return true;
/* 1098 */     }if (dir == 5) {
/* 1099 */       for (int a = 0; a < this.g.size(); a++) {
/* 1100 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1101 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1104 */       int[] y = convertPoints(g.gd.p1[1], g.gd.p2[1]);
/* 1105 */       int y1 = y[0];
/* 1106 */       int y2 = y[1];
/* 1107 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1108 */       gategroup.world = g.gd.World;
/* 1109 */       gategroup.setDelay(1000L);
/* 1110 */       int count = 0;
/* 1111 */       for (int a = y1; a <= y2; a++) {
/* 1112 */         count++;
/* 1113 */         int[] l1 = { g.gd.p1[0], a, g.gd.p1[2] };
/*      */ 
/* 1115 */         int[] l2 = { g.gd.p2[0], a, g.gd.p2[2] };
/*      */ 
/* 1117 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1118 */         this.g.add(gate);
/* 1119 */         gategroup.add(gate);
/*      */       }
/* 1121 */       this.gg.add(gategroup);
/* 1122 */       return true;
/* 1123 */     }if (dir == 6) {
/* 1124 */       for (int a = 0; a < this.g.size(); a++) {
/* 1125 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1126 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1129 */       int[] y = convertPoints(g.gd.p1[1], g.gd.p2[1]);
/* 1130 */       int y1 = y[0];
/* 1131 */       int y2 = y[1];
/* 1132 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1133 */       gategroup.world = g.gd.World;
/* 1134 */       gategroup.setDelay(1000L);
/* 1135 */       int count = 0;
/* 1136 */       for (int a = y2; a >= y1; a--) {
/* 1137 */         count++;
/* 1138 */         int[] l1 = { g.gd.p1[0], a, g.gd.p1[2] };
/*      */ 
/* 1140 */         int[] l2 = { g.gd.p2[0], a, g.gd.p2[2] };
/*      */ 
/* 1142 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1143 */         this.g.add(gate);
/* 1144 */         gategroup.add(gate);
/*      */       }
/* 1146 */       this.gg.add(gategroup);
/* 1147 */       return true;
/*      */     }
/* 1149 */     return false;
/*      */   }
/*      */ 
/*      */   private int[] convertPoints(int i1, int i2) {
/* 1153 */     int x1 = 0;
/* 1154 */     int x2 = 0;
/*      */ 
/* 1156 */     if (i1 < i2) {
/* 1157 */       x1 = i1;
/* 1158 */       x2 = i2;
/*      */     } else {
/* 1160 */       x2 = i1;
/* 1161 */       x1 = i2;
/*      */     }
/* 1163 */     return new int[] { x1, x2 };
/*      */   }
/*      */ 
/*      */   private void showSelection() {
/* 1167 */     int[] x = convertPoints(this.p1.getBlockX(), this.p2.getBlockX());
/* 1168 */     int[] y = convertPoints(this.p1.getBlockY(), this.p2.getBlockY());
/* 1169 */     int[] z = convertPoints(this.p1.getBlockZ(), this.p2.getBlockZ());
/* 1170 */     World w = this.p1.getWorld();
/*      */ 
/* 1172 */     int x1 = x[0]; int x2 = x[1];
/* 1173 */     int y1 = y[0]; int y2 = y[1];
/* 1174 */     int z1 = z[0]; int z2 = z[1];
/*      */ 
/* 1176 */     final ArrayList blocks = new ArrayList();
/* 1177 */     final ArrayList backup = new ArrayList();
/*      */ 
/* 1179 */     for (int a = x1; a <= x2; a++) {
/* 1180 */       for (int b = y1; b <= y2; b++) {
/* 1181 */         for (int c = z1; c <= z2; c++) {
/* 1182 */           blocks.add(w.getBlockAt(a, b, c));
/* 1183 */           backup.add(new MaterialId(w.getBlockAt(a, b, c).getTypeId(), w.getBlockAt(a, b, c).getData()));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1188 */     for (int a = 0; a < blocks.size(); a++) {
/* 1189 */       ((Block)blocks.get(a)).setTypeId(20);
/*      */     }
/*      */ 
/* 1192 */     this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
/*      */     {
/*      */       public void run() {
/* 1195 */         for (int a = 0; a < blocks.size(); a++) {
/* 1196 */           ((Block)blocks.get(a)).setTypeId(((MaterialId)backup.get(a)).getID());
/* 1197 */           ((Block)blocks.get(a)).setData(((MaterialId)backup.get(a)).getData());
/*      */         }
/*      */       }
/*      */     }
/*      */     , 20L);
/*      */   }
/*      */ 
/*      */   private void gReloadConfig()
/*      */   {
/* 1206 */     ArrayList gd = new ArrayList();
/*      */ 
/* 1208 */     Config.mdir();
/*      */     try {
/* 1210 */       gd = Config.LoadGates(this.plugin);
/*      */     } catch (Exception ex) {
/* 1212 */       this.plugin.getLogger().info("No Gates Found!");
/*      */     }
/*      */ 
/* 1215 */     if (gd.isEmpty()) {
/* 1216 */       this.g = new ArrayList();
/*      */     } else {
/* 1218 */       this.g = new ArrayList();
/* 1219 */       for (int a = 0; a < gd.size(); a++) {
/* 1220 */         this.g.add(new Gate((GateData)gd.get(a), this.plugin));
/*      */       }
/*      */     }
/* 1223 */     this.gg = new ArrayList();
/*      */     try {
/* 1225 */       this.gg = Config.LoadGroups(this.g, this.plugin);
/*      */     } catch (Exception e) {
/* 1227 */       this.plugin.getLogger().info("No Groups Found!");
/*      */     }General general;
/*      */     try {
/* 1230 */       general = Config.LoadGeneral(this.plugin);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       General general;
/* 1232 */       this.plugin.getLogger().warning("Unable to load General.yml - Using Standard");
/* 1233 */       general = new General();
/*      */     }
/*      */ 
/* 1236 */     setGateGroup(this.gg);
/* 1237 */     setGate(this.g);
/* 1238 */     setGeneral(general); } 
/*      */   public abstract void setGateGroup(ArrayList<GateGroup> paramArrayList);
/*      */ 
/*      */   public abstract void setGate(ArrayList<Gate> paramArrayList);
/*      */ 
/*      */   public abstract void setGeneral(General paramGeneral);
/*      */ 
/* 1245 */   private void setPerm(Player player, String[] args) { Gate gate = testGate(args[0]);
/* 1246 */     if (gate == null) {
/* 1247 */       GateGroup group = testGateGroup(args[0]);
/* 1248 */       if (group == null) {
/* 1249 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/* 1251 */       else if (args[1].equalsIgnoreCase("all")) {
/* 1252 */         if (args[2].equalsIgnoreCase("true")) {
/* 1253 */           group.openPerm = true;
/* 1254 */           group.closePerm = true;
/* 1255 */           group.killPerm = true;
/* 1256 */           group.buttonPerm = true;
/* 1257 */         } else if (args[2].equalsIgnoreCase("false")) {
/* 1258 */           group.openPerm = false;
/* 1259 */           group.closePerm = false;
/* 1260 */           group.killPerm = false;
/* 1261 */           group.buttonPerm = false;
/*      */         } else {
/* 1263 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */         }
/* 1265 */       } else if (args[1].equalsIgnoreCase("open")) {
/* 1266 */         if (args[2].equalsIgnoreCase("true"))
/* 1267 */           group.openPerm = true;
/* 1268 */         else if (args[2].equalsIgnoreCase("false"))
/* 1269 */           group.openPerm = false;
/*      */         else
/* 1271 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1273 */       else if (args[1].equalsIgnoreCase("close")) {
/* 1274 */         if (args[2].equalsIgnoreCase("true"))
/* 1275 */           group.closePerm = true;
/* 1276 */         else if (args[2].equalsIgnoreCase("false"))
/* 1277 */           group.closePerm = false;
/*      */         else
/* 1279 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1281 */       else if (args[1].equalsIgnoreCase("kill")) {
/* 1282 */         if (args[2].equalsIgnoreCase("true"))
/* 1283 */           group.killPerm = true;
/* 1284 */         else if (args[2].equalsIgnoreCase("false"))
/* 1285 */           group.killPerm = false;
/*      */         else
/* 1287 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1289 */       else if (args[1].equalsIgnoreCase("button")) {
/* 1290 */         if (args[2].equalsIgnoreCase("true"))
/* 1291 */           group.buttonPerm = true;
/* 1292 */         else if (args[2].equalsIgnoreCase("false"))
/* 1293 */           group.buttonPerm = false;
/*      */         else {
/* 1295 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/* 1300 */     else if (args[1].equalsIgnoreCase("all")) {
/* 1301 */       if (args[2].equalsIgnoreCase("true")) {
/* 1302 */         gate.gd.openPerm = true;
/* 1303 */         gate.gd.closePerm = true;
/* 1304 */         gate.gd.killPerm = true;
/* 1305 */         gate.gd.buttonPerm = true;
/* 1306 */       } else if (args[2].equalsIgnoreCase("false")) {
/* 1307 */         gate.gd.openPerm = false;
/* 1308 */         gate.gd.closePerm = false;
/* 1309 */         gate.gd.killPerm = false;
/* 1310 */         gate.gd.buttonPerm = false;
/*      */       } else {
/* 1312 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1314 */     } else if (args[1].equalsIgnoreCase("open")) {
/* 1315 */       if (args[2].equalsIgnoreCase("true"))
/* 1316 */         gate.gd.openPerm = true;
/* 1317 */       else if (args[2].equalsIgnoreCase("false"))
/* 1318 */         gate.gd.openPerm = false;
/*      */       else
/* 1320 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/* 1322 */     else if (args[1].equalsIgnoreCase("close")) {
/* 1323 */       if (args[2].equalsIgnoreCase("true"))
/* 1324 */         gate.gd.closePerm = true;
/* 1325 */       else if (args[2].equalsIgnoreCase("false"))
/* 1326 */         gate.gd.closePerm = false;
/*      */       else
/* 1328 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/* 1330 */     else if (args[1].equalsIgnoreCase("kill")) {
/* 1331 */       if (args[2].equalsIgnoreCase("true"))
/* 1332 */         gate.gd.killPerm = true;
/* 1333 */       else if (args[2].equalsIgnoreCase("false"))
/* 1334 */         gate.gd.killPerm = false;
/*      */       else
/* 1336 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/* 1338 */     else if (args[1].equalsIgnoreCase("button")) {
/* 1339 */       if (args[2].equalsIgnoreCase("true"))
/* 1340 */         gate.gd.buttonPerm = true;
/* 1341 */       else if (args[2].equalsIgnoreCase("false"))
/* 1342 */         gate.gd.buttonPerm = false;
/*      */       else
/* 1344 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private ArrayList<MaterialId> getMaterials(int[] p1, int[] p2, World w)
/*      */   {
/* 1350 */     int[] x = convertPoints(p1[0], p2[0]);
/* 1351 */     int[] y = convertPoints(p1[1], p2[1]);
/* 1352 */     int[] z = convertPoints(p1[2], p2[2]);
/* 1353 */     ArrayList materials = new ArrayList();
/* 1354 */     for (int a = x[0]; a <= x[1]; a++) {
/* 1355 */       for (int b = y[0]; b <= y[1]; b++) {
/* 1356 */         for (int c = z[0]; c <= z[1]; c++) {
/* 1357 */           Block block = w.getBlockAt(a, b, c);
/* 1358 */           materials.add(new MaterialId(block.getTypeId(), block.getData()));
/*      */         }
/*      */       }
/*      */     }
/* 1362 */     return materials;
/*      */   }
/*      */ 
/*      */   private void gsetClose(Player player, String[] args) {
/* 1366 */     Gate gate = testGate(args[0]);
/* 1367 */     if (gate == null) {
/* 1368 */       GateGroup group = testGateGroup(args[0]);
/* 1369 */       if (group == null)
/* 1370 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       else
/* 1372 */         for (int a = 0; a < group.g.size(); a++)
/*      */           try {
/* 1374 */             GateGroup group2 = (GateGroup)group.g.get(a);
/* 1375 */             gsetClose(player, new String[] { group.name });
/*      */           } catch (Exception e) {
/*      */             try {
/* 1378 */               Gate gate2 = (Gate)group.g.get(a);
/* 1379 */               gsetClose(player, new String[] { gate2.gd.name });
/*      */             } catch (Exception localException1) {
/*      */             }
/*      */           }
/*      */     }
/*      */     else {
/* 1385 */       ArrayList material = getMaterials(gate.gd.p1, gate.gd.p2, this.plugin.getServer().getWorld(gate.gd.World));
/* 1386 */       gate.gd.materials2 = material;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gsetOpen(Player player, String[] args) {
/* 1391 */     Gate gate = testGate(args[0]);
/* 1392 */     if (gate == null) {
/* 1393 */       GateGroup group = testGateGroup(args[0]);
/* 1394 */       if (group == null)
/* 1395 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       else
/* 1397 */         for (int a = 0; a < group.g.size(); a++)
/*      */           try {
/* 1399 */             GateGroup group2 = (GateGroup)group.g.get(a);
/* 1400 */             gsetOpen(player, new String[] { group2.name });
/*      */           } catch (Exception e) {
/*      */             try {
/* 1403 */               Gate gate2 = (Gate)group.g.get(a);
/* 1404 */               gsetOpen(player, new String[] { gate.gd.name });
/*      */             } catch (Exception localException1) {
/*      */             }
/*      */           }
/*      */     }
/*      */     else {
/* 1410 */       ArrayList material = getMaterials(gate.gd.p1, gate.gd.p2, this.plugin.getServer().getWorld(gate.gd.World));
/* 1411 */       gate.gd.materials1 = material;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gAnimation(Player player, String[] args) {
/* 1416 */     Gate gate = testGate(args[0]);
/* 1417 */     if (gate == null) {
/* 1418 */       player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */     }
/* 1420 */     else if (!createAnimation(gate, Directions.getDirection(args[1])))
/* 1421 */       player.sendMessage("[CityGates] Unknow Direction: " + args[1]);
/*      */     else
/* 1423 */       player.sendMessage("[CityGates] Adding Animation to " + args[0]);
/*      */   }
/*      */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.Commands
 * JD-Core Version:    0.6.2
 */