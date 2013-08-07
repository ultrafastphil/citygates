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
/*  281 */   public static final String[] help = { "------ Help page 1 ------", "/ghelp <page number> - help funcie", "/ggroup             - help funcite", "------Gate Managing------", "/gp1                - select point 1", "/gp2                - select point 2", "/gcreate [GateName] - create a gate", "/gremove [GateName] - remove a gate", "/ginfo [Gate/Group] - info about a gate", "/glist <Group/Gate> - list all the Gates and/or Groups", "------Group Managing------", "/ggroup create [Group]              - create a group", "/ggroup del [Group]                 - delete a group", "/ggroup add [Group] [Gate/Group]    - Add to group", "/ggroup remove [Group] [Gate/Group] - Remove from group", "/ggroup clear [Group]               - Clear a group", "/ggroup setDelay [Group] [time delay (in milli sec)c] - Set delay" };
/*      */ 
/*  300 */   public static final String[] help2 = { "------ Help page 2 ------", "/ghelp <page number> - help funcie", "/ggroup             - help funcite", "------Gate Actions------", "/gOpen [Gate/Group] - Open a Gate/Group", "/gClose[Gate/Group] - Close a Gate/Group", "/gGroup timeGate [Gate/Group] [true/false] - Gate open and close by time!", "/gGroup redstone [Gate/Group] [true/false] look at the redstone - Add a redstone listener", "/gGroup mobKill [Gate/Group] [mob/del] <Kill Msg> - Gate open and close when the mob is slayen!", "/gGroup button [Gate/Group] [true/false] [time delay (in milli sec)] look at the button - Add a non-toggle button listener" };
/*      */ 
/*      */   public Commands(Plugin p, ArrayList<Gate> g, ArrayList<GateGroup> gg)
/*      */   {
/*   17 */     this.plugin = p;
/*   18 */     this.g = g;
/*   19 */     this.gg = gg;
/*      */   }
/*      */ 
/*      */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
/*   23 */     Player p = null;
/*   24 */     if ((cs instanceof Player)) {
/*   25 */       p = (Player)cs;
/*      */ 
/*   27 */       String cmd = cmnd.getName().toLowerCase();
/*      */ 
/*   29 */       if (cmd.equalsIgnoreCase("gp1")) {
/*   30 */         if (cs.hasPermission("citygates.admin.p1"))
/*   31 */           gp1(p);
/*      */         else
/*   33 */           cs.sendMessage("You don't have Permission to use this Command!");
/*   34 */         return true;
/*   35 */       }if (cmd.equalsIgnoreCase("gp2")) {
/*   36 */         if (cs.hasPermission("citygates.admin.p2"))
/*   37 */           gp2(p);
/*      */         else
/*   39 */           cs.sendMessage("You don't have Permission to use this Command!");
/*   40 */         return true;
/*   41 */       }if (cmd.equalsIgnoreCase("gcreate")) {
/*   42 */         if (cs.hasPermission("citygates.admin.create")) {
/*   43 */           if ((strings.length == 1) || (strings.length == 2)) {
/*   44 */             gCreate(p, strings);
/*   45 */             return true;
/*      */           }
/*   47 */           return false;
/*      */         }
/*      */ 
/*   50 */         cs.sendMessage("You don't have Permission to use this Command!");
/*   51 */         return true;
/*      */       }
/*   53 */       if (cmd.equalsIgnoreCase("gredstone")) {
/*   54 */         if (cs.hasPermission("citygates.admin.redstone")) {
/*   55 */           if (strings.length == 2) {
/*   56 */             gRedstone(p, strings);
/*   57 */             return true;
/*      */           }
/*   59 */           return false;
/*      */         }
/*      */ 
/*   62 */         cs.sendMessage("You don't have Permission to use this Command!");
/*   63 */         return true;
/*      */       }
/*   65 */       if (cmd.equalsIgnoreCase("gtimegate")) {
/*   66 */         if (cs.hasPermission("citygates.admin.timegate")) {
/*   67 */           if (strings.length == 2) {
/*   68 */             gTimeGate(p, strings);
/*   69 */             return true;
/*      */           }
/*   71 */           return false;
/*      */         }
/*      */ 
/*   74 */         cs.sendMessage("You don't have Permission to use this Command!");
/*   75 */         return true;
/*      */       }
/*   77 */       if (cmd.equalsIgnoreCase("gkill")) {
/*   78 */         if (cs.hasPermission("citygates.admin.kill")) {
/*   79 */           if (strings.length >= 2) {
/*   80 */             gKill(p, strings);
/*   81 */             return true;
/*      */           }
/*   83 */           return false;
/*      */         }
/*      */ 
/*   86 */         cs.sendMessage("You don't have Permission to use this Command!");
/*   87 */         return true;
/*      */       }
/*   89 */       if (cmd.equalsIgnoreCase("glist")) {
/*   90 */         if (cs.hasPermission("citygates.user.list")) {
/*   91 */           if (strings.length < 3) {
/*   92 */             gList(p, strings);
/*   93 */             return true;
/*      */           }
/*      */         } else {
/*   96 */           cs.sendMessage("You don't have Permission to use this Command!");
/*   97 */           return true;
/*      */         }
/*   99 */       } else if (cmd.equalsIgnoreCase("gremove")) {
/*  100 */         if (cs.hasPermission("citygates.admin.remove")) {
/*  101 */           if (strings.length == 1) {
/*  102 */             gRemove(p, strings);
/*  103 */             return true;
/*  104 */           }if (strings.length == 2) {
/*  105 */             gRemoveAll(p, strings);
/*  106 */             p.sendMessage("[CityGates] Removing: " + strings[0]);
/*  107 */             return true;
/*      */           }
/*      */         } else {
/*  110 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  111 */           return true;
/*      */         }
/*  113 */       } else if (cmd.equalsIgnoreCase("gopen")) {
/*  114 */         if (cs.hasPermission("citygates.user.open")) {
/*  115 */           if (strings.length == 1) {
/*  116 */             gOpen(p, strings);
/*  117 */             return true;
/*      */           }
/*      */         } else {
/*  120 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  121 */           return true;
/*      */         }
/*  123 */       } else if (cmd.equalsIgnoreCase("gclose")) {
/*  124 */         if (cs.hasPermission("citygates.user.close")) {
/*  125 */           if (strings.length == 1) {
/*  126 */             gClose(p, strings);
/*  127 */             return true;
/*      */           }
/*      */         } else {
/*  130 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  131 */           return true;
/*      */         }
/*  133 */       } else if (cmd.equalsIgnoreCase("ginfo")) {
/*  134 */         if (cs.hasPermission("citygates.user.info")) {
/*  135 */           if (strings.length == 1) {
/*  136 */             gInfo(p, strings);
/*  137 */             return true;
/*      */           }
/*      */         } else {
/*  140 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  141 */           return true;
/*      */         }
/*  143 */       } else if (cmd.equalsIgnoreCase("clear")) {
/*  144 */         if (cs.hasPermission("citygates.admin.clear")) {
/*  145 */           Clear(p, strings);
/*      */         } else {
/*  147 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  148 */           return true;
/*      */         }
/*      */       } else { if (cmd.equalsIgnoreCase("gkillarea"))
/*      */         {
/*  157 */           cs.sendMessage("You don't have Permission to use this Command!");
/*  158 */           return true;
/*      */         }
/*  160 */         if (cmd.equalsIgnoreCase("gbutton")) {
/*  161 */           if (cs.hasPermission("citygates.admin.button")) {
/*  162 */             if (strings.length == 2) {
/*  163 */               if (strings[1].equalsIgnoreCase("false")) {
/*  164 */                 gRemoveButton(p, strings[0]);
/*  165 */                 return true;
/*      */               }
/*  167 */             } else if (strings.length == 3) {
/*  168 */               gButton(p, strings);
/*  169 */               return true;
/*      */             }
/*      */           } else {
/*  172 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  173 */             return true;
/*      */           }
/*      */         } else { if (cmd.equalsIgnoreCase("ggroup")) {
/*  176 */             gGroup(p, strings);
/*  177 */             return true;
/*  178 */           }if (cmd.equalsIgnoreCase("ghelp")) {
/*  179 */             if (cs.hasPermission("citygates.user.help")) {
/*  180 */               if (strings.length == 0)
/*  181 */                 p.sendMessage(help);
/*  182 */               else if (strings[0].equalsIgnoreCase("1"))
/*  183 */                 p.sendMessage(help);
/*  184 */               else if (strings[0].equalsIgnoreCase("2"))
/*  185 */                 p.sendMessage(help2);
/*      */               else {
/*  187 */                 p.sendMessage("Max pages = 2");
/*      */               }
/*  189 */               return true;
/*      */             }
/*  191 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  192 */             return true;
/*      */           }
/*  194 */           if (cmd.equalsIgnoreCase("greload")) {
/*  195 */             if (cs.hasPermission("citygates.admin.reload")) {
/*  196 */               gReloadConfig();
/*  197 */               p.sendMessage("[CityGates] Reload Complete!");
/*  198 */               return true;
/*      */             }
/*  200 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  201 */             return true;
/*      */           }
/*  203 */           if (cmd.equalsIgnoreCase("gshow")) {
/*  204 */             if (cs.hasPermission("citygates.admin.show")) {
/*  205 */               showSelection();
/*  206 */               return true;
/*      */             }
/*  208 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  209 */             return true;
/*      */           }
/*  211 */           if (cmd.equalsIgnoreCase("gsetperm")) {
/*  212 */             if (cs.hasPermission("citygates.admin.setperm")) {
/*  213 */               if (strings.length == 3) {
/*  214 */                 setPerm(p, strings);
/*  215 */                 return true;
/*      */               }
/*  217 */               return false;
/*      */             }
/*      */ 
/*  220 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  221 */             return true;
/*      */           }
/*  223 */           if (cmd.equalsIgnoreCase("gsetclose")) {
/*  224 */             if (cs.hasPermission("citygates.admin.setopen")) {
/*  225 */               if (strings.length == 1) {
/*  226 */                 gsetOpen(p, strings);
/*  227 */                 p.sendMessage("[CityGates] Gate updated!");
/*  228 */                 return true;
/*      */               }
/*  230 */               return false;
/*      */             }
/*      */ 
/*  233 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  234 */             return true;
/*      */           }
/*  236 */           if (cmd.equalsIgnoreCase("gsetopen")) {
/*  237 */             if (cs.hasPermission("citygates.admin.setopen")) {
/*  238 */               if (strings.length == 1) {
/*  239 */                 gsetClose(p, strings);
/*  240 */                 p.sendMessage("[CityGates] Gate updated!");
/*  241 */                 return true;
/*      */               }
/*  243 */               return false;
/*      */             }
/*      */ 
/*  246 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  247 */             return true;
/*      */           }
/*  249 */           if (cmd.equalsIgnoreCase("gsetanimation")) {
/*  250 */             if (cs.hasPermission("citygates.admin.addanimation")) {
/*  251 */               if (strings.length == 2) {
/*  252 */                 gAnimation(p, strings);
/*  253 */                 return true;
/*      */               }
/*  255 */               return false;
/*      */             }
/*      */ 
/*  258 */             cs.sendMessage("You don't have Permission to use this Command!");
/*  259 */             return true;
/*      */           }
/*      */ 
/*  262 */           if (!(cs instanceof Player)) {
/*  263 */             cs.sendMessage("This command can only be execute by console!");
/*  264 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  270 */     return false;
/*      */   }
/*      */ 
/*      */   private void gp1(Player p)
/*      */   {
/*  314 */     if (p != null) {
/*  315 */       Block b = p.getTargetBlock(null, 50);
/*  316 */       this.p1 = b.getLocation();
/*  317 */       Material m = b.getType();
/*  318 */       p.sendMessage("[CityGates] Select Point 1 - X: " + this.p1.getBlockX() + " Y: " + this.p1.getBlockY() + " Z: " + this.p1.getBlockZ());
/*      */     } else {
/*  320 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gp2(Player p) {
/*  325 */     if (p == null) {
/*  326 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     } else {
/*  328 */       this.p2 = p.getTargetBlock(null, 50).getLocation();
/*  329 */       p.sendMessage("[CityGates] Select Point 2 - X: " + this.p2.getBlockX() + " Y: " + this.p2.getBlockY() + " Z: " + this.p2.getBlockZ());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gCreate(Player player, String[] args) {
/*  334 */     if (player == null) {
/*  335 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     } else {
/*  337 */       boolean exists = false;
/*  338 */       for (int a = 0; a < this.g.size(); a++) {
/*  339 */         if (args[0].equals(((Gate)this.g.get(a)).gd.name)) {
/*  340 */           exists = true;
/*  341 */           player.sendMessage("[CityGates] This Gate already exists!");
/*      */         }
/*      */       }
/*  344 */       for (int a = 0; a < this.gg.size(); a++) {
/*  345 */         if (args[0].equals(((GateGroup)this.gg.get(a)).name)) {
/*  346 */           exists = true;
/*  347 */           player.sendMessage("[CityGates] A Group with this name already exists!");
/*      */         }
/*      */       }
/*  350 */       if (this.p1 == null) {
/*  351 */         player.sendMessage("[CityGates] You have to select point 1 first");
/*  352 */         player.sendMessage("[CityGates] Command: /gp1");
/*  353 */       } else if (this.p2 == null) {
/*  354 */         player.sendMessage("[CityGates] You have to select point 2 first");
/*  355 */         player.sendMessage("[CityGates] Command: /gp2");
/*  356 */       } else if (!this.p1.getWorld().equals(this.p2.getWorld())) {
/*  357 */         if (!this.p1.getWorld().equals(player.getWorld())) {
/*  358 */           player.sendMessage("[CityGates] You have to select point 1 first");
/*  359 */           player.sendMessage("[CityGates] Command: /gp1");
/*      */         } else {
/*  361 */           player.sendMessage("[CityGates] You have to select point 2 first");
/*  362 */           player.sendMessage("[CityGates] Command: /gp2");
/*      */         }
/*  364 */       } else if (exists) {
/*  365 */         player.sendMessage("[CityGates] " + args[0] + " already exists!");
/*      */       } else {
/*  367 */         int[] l1 = { this.p1.getBlockX(), this.p1.getBlockY(), this.p1.getBlockZ() };
/*      */ 
/*  372 */         int[] l2 = { this.p2.getBlockX(), this.p2.getBlockY(), this.p2.getBlockZ() };
/*      */ 
/*  377 */         ArrayList material = new ArrayList();
/*  378 */         byte[] bytes = { 0 };
/*  379 */         material.add(new MaterialId(0, bytes[0]));
/*  380 */         Gate newGate = new Gate(new GateData(args[0], l1, l2, player.getWorld().getName(), getMaterials(l1, l2, this.p1.getWorld()), material), this.plugin);
/*  381 */         if (args.length == 1) {
/*  382 */           this.g.add(newGate);
/*  383 */           player.sendMessage("[CityGates] " + args[0] + " Succesfull created!");
/*  384 */           showSelection();
/*  385 */         } else if (args.length == 2) {
/*  386 */           int dir = Directions.getDirection(args[1]);
/*  387 */           if (createAnimation(newGate, dir)) {
/*  388 */             player.sendMessage("[CityGates] " + args[0] + " Succesfull created!");
/*  389 */             showSelection();
/*      */           } else {
/*  391 */             player.sendMessage("[CityGates] Unknow Direction: " + args[1]);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRedstone(Player player, String[] args) {
/*  399 */     if (player == null) {
/*  400 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     }
/*      */     else
/*      */     {
/*      */       Gate gate;
/*  403 */       if ((gate = testGate(args[0])) == null) {
/*  404 */         GateGroup group = testGateGroup(args[0]);
/*  405 */         if (group == null) {
/*  406 */           player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */         }
/*  408 */         else if (args[1].equals("true")) {
/*  409 */           Location l = player.getTargetBlock(null, 50).getLocation();
/*  410 */           group.pr[0] = l.getBlockX();
/*  411 */           group.pr[1] = l.getBlockY();
/*  412 */           group.pr[2] = l.getBlockZ();
/*  413 */           group.redstoneListener = true;
/*  414 */           player.sendMessage("[CityGates] RedstoneListener add to " + args[0] + "on Block -" + " X: " + group.pr[0] + " Y: " + group.pr[1] + " Z: " + group.pr[2]);
/*      */         }
/*  416 */         else if (args[1].equals("false")) {
/*  417 */           group.redstoneListener = false;
/*  418 */           player.sendMessage("[CityGates] RedstoneListener Removed!");
/*      */         } else {
/*  420 */           player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */         }
/*      */ 
/*      */       }
/*  424 */       else if (args[1].equals("true")) {
/*  425 */         gate.setRedstoenListener(player.getTargetBlock(null, 50).getLocation());
/*  426 */         player.sendMessage("[CityGates] RedstoneListener add to " + args[0] + "on Block -" + " X: " + gate.gd.pr[0] + " Y: " + gate.gd.pr[1] + " Z: " + gate.gd.pr[2]);
/*      */       }
/*  428 */       else if (args[1].equals("false")) {
/*  429 */         gate.removeRedstoneListener();
/*  430 */         player.sendMessage("[CityGates] RedstoneListener Removed!");
/*      */       } else {
/*  432 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gKill(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  440 */     if ((gate = testGate(args[0])) == null) {
/*  441 */       GateGroup group = testGateGroup(args[0]);
/*  442 */       if (group == null)
/*  443 */         player.sendMessage("[CityGates] " + args[0] + "not found!");
/*      */       else
/*      */         try {
/*  446 */           if (args[1].equalsIgnoreCase("del")) {
/*  447 */             group.mobKill = false;
/*      */           } else {
/*  449 */             EntityType mob = EntityType.fromName(args[1]);
/*  450 */             group.mob = mob.getName();
/*  451 */             group.mobKill = true;
/*  452 */             player.sendMessage("[CityGates] " + args[0] + " Open when a " + mob.getName() + " is slayen in this World!");
/*  453 */             if (args.length > 2) {
/*  454 */               String mes = "";
/*  455 */               for (int a = 2; a < args.length; a++) {
/*  456 */                 mes = mes + " " + args[a];
/*  457 */                 group.killMsg = mes;
/*      */               }
/*      */             }
/*      */           }
/*      */         } catch (Exception e) {
/*  462 */           player.sendMessage("[CityGates] " + args[1] + " Invallid Entity!");
/*      */         }
/*      */     }
/*      */     else {
/*      */       try {
/*  467 */         if (args[1].equalsIgnoreCase("del")) {
/*  468 */           gate.removeMobKill();
/*      */         } else {
/*  470 */           EntityType mob = EntityType.fromName(args[1]);
/*  471 */           gate.setMobKill(mob);
/*  472 */           player.sendMessage("[CityGates] " + args[0] + " Open when a " + mob.getName() + " is slayen in this World!");
/*  473 */           if (args.length > 2) {
/*  474 */             String mes = "";
/*  475 */             for (int a = 2; a < args.length; a++) {
/*  476 */               mes = mes + " " + args[a];
/*  477 */               gate.gd.killMsg = mes;
/*      */             }
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/*  482 */         player.sendMessage("[CityGates] " + args[1] + " Invallid Entity!");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gTimeGate(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  490 */     if ((gate = testGate(args[0])) == null) {
/*  491 */       GateGroup group = testGateGroup(args[0]);
/*  492 */       if (group == null) {
/*  493 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  495 */       else if (args[1].equalsIgnoreCase("true")) {
/*  496 */         group.timegate = true;
/*  497 */         player.sendMessage("[CityGates] " + args[0] + " Open and Close by time!");
/*  498 */       } else if (args[1].equalsIgnoreCase("false")) {
/*  499 */         group.timegate = false;
/*  500 */         player.sendMessage("[CityGates] Succesfully removed timeGates!");
/*      */       } else {
/*  502 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */       }
/*      */ 
/*      */     }
/*  506 */     else if (args[1].equalsIgnoreCase("true")) {
/*  507 */       gate.setTimeGate();
/*  508 */       player.sendMessage("[CityGates] " + args[0] + " Open and Close by time!");
/*  509 */     } else if (args[1].equalsIgnoreCase("false")) {
/*  510 */       gate.removeTimeGate();
/*  511 */       player.sendMessage("[CityGates] Succesfully removed timeGates!");
/*      */     } else {
/*  513 */       player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gList(Player player, String[] args)
/*      */   {
/*  519 */     if (args.length == 0) {
/*  520 */       if (this.g.size() == 0)
/*  521 */         player.sendMessage("No Gates Found!");
/*      */       else {
/*  523 */         for (int a = 0; a < this.g.size(); a++) {
/*  524 */           player.sendMessage("[Gate]" + String.valueOf(a + 1) + ": " + ((Gate)this.g.get(a)).gd.name);
/*      */         }
/*      */       }
/*  527 */       if (this.gg.size() == 0)
/*  528 */         player.sendMessage("No Groups Found!");
/*      */       else {
/*  530 */         for (int a = 0; a < this.gg.size(); a++)
/*  531 */           player.sendMessage("[Group] " + String.valueOf(a + 1) + ": " + ((GateGroup)this.gg.get(a)).name);
/*      */       }
/*      */     }
/*  534 */     else if (args.length == 1) {
/*  535 */       if (args[0].equalsIgnoreCase("group")) {
/*  536 */         if (this.gg.size() == 0)
/*  537 */           player.sendMessage("No Groups Found!");
/*      */         else {
/*  539 */           for (int a = 0; a < this.gg.size(); a++)
/*  540 */             player.sendMessage("[Group] " + String.valueOf(a + 1) + ": " + ((GateGroup)this.gg.get(a)).name);
/*      */         }
/*      */       }
/*  543 */       else if (args[0].equalsIgnoreCase("gate")) {
/*  544 */         if (this.g.size() == 0)
/*  545 */           player.sendMessage("No Gates Found!");
/*      */         else {
/*  547 */           for (int a = 0; a < this.g.size(); a++)
/*  548 */             player.sendMessage("[Gate]" + String.valueOf(a + 1) + ": " + ((Gate)this.g.get(a)).gd.name);
/*      */         }
/*      */       }
/*      */       else
/*  552 */         player.sendMessage("[CityGate] /list [group/gate]");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRemove(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  559 */     if ((gate = testGate(args[0])) == null) {
/*  560 */       GateGroup group = testGateGroup(args[0]);
/*  561 */       if (group == null)
/*  562 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       else
/*  564 */         for (int a = 0; a < this.gg.size(); a++)
/*  565 */           if (((GateGroup)this.gg.get(a)).name.equalsIgnoreCase(args[0])) {
/*  566 */             this.gg.remove(a);
/*  567 */             player.sendMessage("[CityGates] Removing: " + args[0]);
/*      */           }
/*      */     }
/*      */     else
/*      */     {
/*  572 */       for (int a = 0; a < this.g.size(); a++)
/*  573 */         if (((Gate)this.g.get(a)).gd.name.equalsIgnoreCase(args[0])) {
/*  574 */           this.g.remove(a);
/*  575 */           player.sendMessage("[CityGates] Removing: " + args[0]);
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRemoveAll(Player player, String[] args)
/*      */   {
/*  582 */     Gate gate = testGate(args[0]);
/*  583 */     if (gate == null) {
/*  584 */       GateGroup group = testGateGroup(args[0]);
/*  585 */       if (group == null) {
/*  586 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       } else {
/*  588 */         for (int a = 0; a < group.g.size(); a++)
/*      */           try {
/*  590 */             Gate gate2 = (Gate)group.g.get(a);
/*  591 */             gRemoveAll(player, new String[] { gate2.gd.name });
/*      */           } catch (Exception e) {
/*      */             try {
/*  594 */               GateGroup group2 = (GateGroup)group.g.get(a);
/*  595 */               gRemoveAll(player, new String[] { group2.name });
/*      */             } catch (Exception ee) {
/*      */             }
/*      */           }
/*  599 */         for (int a = 0; a < this.gg.size(); a++)
/*  600 */           if (((GateGroup)this.gg.get(a)).name.equalsIgnoreCase(args[0]))
/*  601 */             this.gg.remove(a);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  606 */       for (int a = 0; a < this.g.size(); a++)
/*  607 */         if (((Gate)this.g.get(a)).gd.name.equalsIgnoreCase(args[0]))
/*  608 */           this.g.remove(a);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gOpen(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  616 */     if ((gate = testGate(args[0])) == null) {
/*  617 */       GateGroup group = testGateGroup(args[0]);
/*  618 */       if (group == null) {
/*  619 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  621 */       else if (((group.openPerm) && (player.hasPermission("citygates.user.gate." + group.name))) || ((group.openPerm) && (player.hasPermission("citygates.user.gate.*"))))
/*  622 */         group.Open();
/*  623 */       else if (!group.openPerm)
/*  624 */         group.Open();
/*      */       else {
/*  626 */         player.sendMessage("You don't have Permssion to use this Gate!");
/*      */       }
/*      */ 
/*      */     }
/*  630 */     else if (((gate.gd.openPerm) && (player.hasPermission("citygates.user.gate." + gate.gd.name))) || ((gate.gd.openPerm) && (player.hasPermission("citygates.user.gate.*")))) {
/*  631 */       gate.Open();
/*  632 */     } else if (!gate.gd.openPerm) {
/*  633 */       gate.Open();
/*      */     } else {
/*  635 */       player.sendMessage("You don't have Permssion to use this Gate!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gClose(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  642 */     if ((gate = testGate(args[0])) == null) {
/*  643 */       GateGroup group = testGateGroup(args[0]);
/*  644 */       if (group == null) {
/*  645 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  647 */       else if (((group.closePerm) && (player.hasPermission("citygates.user.gate." + group.name))) || ((group.closePerm) && (player.hasPermission("citygates.user.gate.*"))))
/*  648 */         group.Close();
/*  649 */       else if (!group.closePerm)
/*  650 */         group.Close();
/*      */       else {
/*  652 */         player.sendMessage("You don't have Permssion to use this Gate!");
/*      */       }
/*      */ 
/*      */     }
/*  656 */     else if (((gate.gd.closePerm) && (player.hasPermission("citygates.user.gate." + gate.gd.name))) || ((gate.gd.closePerm) && (player.hasPermission("citygates.user.gate.*")))) {
/*  657 */       gate.Close();
/*  658 */     } else if (!gate.gd.closePerm) {
/*  659 */       gate.Close();
/*      */     } else {
/*  661 */       player.sendMessage("You don't have Permssion to use this Gate!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gInfo(Player player, String[] args)
/*      */   {
/*      */     Gate gate;
/*  668 */     if ((gate = testGate(args[0])) == null) {
/*  669 */       GateGroup group = testGateGroup(args[0]);
/*  670 */       if (group == null) {
/*  671 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       } else {
/*  673 */         player.sendMessage("[CityGates] Name: " + group.name);
/*  674 */         player.sendMessage("[CityGates] World: " + group.world);
/*  675 */         player.sendMessage("[CityGates] Delay: " + group.delay);
/*  676 */         player.sendMessage("[CityGates] Open: " + group.open);
/*  677 */         player.sendMessage("[CityGates] TimeGate: " + group.timegate);
/*  678 */         player.sendMessage("[CityGates] redstoneListener: " + group.redstoneListener);
/*  679 */         player.sendMessage("[CityGates] Redstone Point: " + group.pr[0] + ", " + group.pr[1] + ", " + group.pr[2]);
/*  680 */         player.sendMessage("[CityGates] Kill Listener: " + group.mobKill);
/*  681 */         player.sendMessage("[CityGates] Kill Message: " + group.killMsg);
/*  682 */         player.sendMessage("[CityGates] Button Listener: " + group.buttonListener);
/*  683 */         player.sendMessage("[CityGates] Button Interval: " + group.delay);
/*  684 */         String Children = "";
/*  685 */         if (group.g.size() == 0)
/*  686 */           Children = "No Gates!";
/*      */         else
/*  688 */           for (int a = 0; a < group.g.size(); a++)
/*      */             try {
/*  690 */               Gate gate2 = (Gate)group.g.get(a);
/*  691 */               if (a == 0)
/*  692 */                 Children = gate2.gd.name;
/*      */               else
/*  694 */                 Children = Children + ", " + gate2.gd.name;
/*      */             }
/*      */             catch (Exception e) {
/*      */               try {
/*  698 */                 GateGroup gategroup = (GateGroup)group.g.get(a);
/*  699 */                 if (a == 0)
/*  700 */                   Children = gategroup.name;
/*      */                 else
/*  702 */                   Children = Children + ", " + gategroup.name;
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/*      */               }
/*      */             }
/*  708 */         player.sendMessage("[CityGates] Gates: " + Children);
/*      */       }
/*      */     } else {
/*  711 */       GateData gd = gate.gd;
/*  712 */       player.sendMessage("[CityGates] Name: " + gd.name);
/*  713 */       player.sendMessage("[CityGates] Point 1: " + gd.p1[0] + ", " + gd.p1[1] + ", " + gd.p1[2]);
/*  714 */       player.sendMessage("[CityGates] Point 2: " + gd.p2[0] + ", " + gd.p2[1] + ", " + gd.p2[2]);
/*  715 */       player.sendMessage("[CityGates] World: " + gd.World);
/*  716 */       player.sendMessage("[CityGates] Redstone Listner: " + gd.redstoneListener);
/*  717 */       player.sendMessage("[CityGates] Redstone Point: " + gd.pr[0] + ", " + gd.pr[1] + ", " + gd.pr[2]);
/*  718 */       player.sendMessage("[CityGates] TimeGate: " + gd.timeGate);
/*  719 */       player.sendMessage("[CityGates] Kill Listener: " + gd.mobKill + " Mob: " + gd.mob);
/*  720 */       player.sendMessage("[CityGates] Kill Message: " + gd.killMsg);
/*  721 */       player.sendMessage("[CityGates] Button Listener: " + gd.buttonListener);
/*  722 */       player.sendMessage("[CityGates] Button Interval: " + gd.ButtonInterval);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Gate testGate(String GateName)
/*      */   {
/*  728 */     for (int a = 0; a < this.g.size(); a++) {
/*  729 */       if (((Gate)this.g.get(a)).gd.name.equals(GateName)) {
/*  730 */         return (Gate)this.g.get(a);
/*      */       }
/*      */     }
/*  733 */     return null;
/*      */   }
/*      */ 
/*      */   private GateGroup testGateGroup(String gateGroupname) {
/*  737 */     for (int a = 0; a < this.gg.size(); a++) {
/*  738 */       if (((GateGroup)this.gg.get(a)).name.equals(gateGroupname)) {
/*  739 */         return (GateGroup)this.gg.get(a);
/*      */       }
/*      */     }
/*  742 */     return null;
/*      */   }
/*      */ 
/*      */   private void Clear(Player player, String[] args) {
/*  746 */     if (args.length == 0)
/*  747 */       player.getInventory().clear();
/*  748 */     else if (args.length == 1)
/*      */       try {
/*  750 */         this.plugin.getServer().getPlayer(args[0]).getInventory().clear();
/*      */       } catch (Exception e) {
/*  752 */         player.sendMessage("[CityGates] No Player found with the name: " + args[1]);
/*      */       }
/*      */     else
/*  755 */       player.sendMessage("[CityGates] to many arguments!");
/*      */   }
/*      */ 
/*      */   private void gKillArea(Player player, String[] args)
/*      */   {
/*  760 */     if (player == null) {
/*  761 */       this.plugin.getLogger().info("This command can only be used by players!");
/*      */     }
/*      */     else
/*      */     {
/*      */       Gate gate;
/*  764 */       if ((gate = testGate(args[0])) == null) {
/*  765 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  767 */       else if (this.p1 == null) {
/*  768 */         player.sendMessage("[CityGates] You have to select point 1 first");
/*  769 */         player.sendMessage("[CityGates] Command: /gp1");
/*  770 */       } else if (this.p2 == null) {
/*  771 */         player.sendMessage("[CityGates] You have to select point 2 first");
/*  772 */         player.sendMessage("[CityGates] Command: /gp2");
/*  773 */       } else if (!this.p1.getWorld().equals(this.p2.getWorld())) {
/*  774 */         if (!this.p1.getWorld().equals(player.getWorld())) {
/*  775 */           player.sendMessage("[CityGates] You have to select point 1 first");
/*  776 */           player.sendMessage("[CityGates] Command: /gp1");
/*      */         } else {
/*  778 */           player.sendMessage("[CityGates] You have to select point 2 first");
/*  779 */           player.sendMessage("[CityGates] Command: /gp2");
/*      */         }
/*      */       }
/*  782 */       else if (args[1].equalsIgnoreCase("true")) {
/*  783 */         gate.setKillArea(this.p1, this.p2);
/*  784 */         player.sendMessage("[CityGates] Kill area set!");
/*  785 */       } else if (args[1].equalsIgnoreCase("false")) {
/*  786 */         gate.removeKillArea();
/*  787 */         player.sendMessage("[CityGates] Removed Kill area!");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gRemoveButton(Player player, String gate)
/*      */   {
/*  795 */     Gate g = testGate(gate);
/*  796 */     if (g == null) {
/*  797 */       GateGroup group = testGateGroup(gate);
/*  798 */       if (group == null) {
/*  799 */         player.sendMessage("[CityGates] " + gate + " not found!");
/*      */       } else {
/*  801 */         group.buttonListener = false;
/*  802 */         player.sendMessage("[CityGates] " + gate + " removed!");
/*      */       }
/*      */     } else {
/*  805 */       g.gd.buttonListener = false;
/*  806 */       player.sendMessage("[CityGates] " + gate + " removed!");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gButton(Player player, String[] args) {
/*  811 */     Gate g = testGate(args[0]);
/*  812 */     if (g == null) {
/*  813 */       GateGroup group = testGateGroup(args[0]);
/*  814 */       if (group == null) {
/*  815 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/*  817 */       else if (args[1].equalsIgnoreCase("true"))
/*      */         try {
/*  819 */           group.ButtonInterval = Integer.parseInt(args[2]);
/*  820 */           Location l = player.getTargetBlock(null, 50).getLocation();
/*  821 */           group.button[0] = l.getBlockX();
/*  822 */           group.button[1] = l.getBlockY();
/*  823 */           group.button[2] = l.getBlockZ();
/*  824 */           group.buttonListener = true;
/*  825 */           player.sendMessage("[CityGates] add Button Listener");
/*      */         } catch (Exception e) {
/*  827 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */         }
/*  829 */       else if (args[1].equalsIgnoreCase("false"))
/*  830 */         gRemoveButton(player, args[0]);
/*      */       else {
/*  832 */         player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */       }
/*      */ 
/*      */     }
/*  836 */     else if (args[1].equalsIgnoreCase("true")) {
/*      */       try {
/*  838 */         g.gd.ButtonInterval = Integer.parseInt(args[2]);
/*  839 */         Location l = player.getTargetBlock(null, 50).getLocation();
/*  840 */         g.gd.button[0] = l.getBlockX();
/*  841 */         g.gd.button[1] = l.getBlockY();
/*  842 */         g.gd.button[2] = l.getBlockZ();
/*  843 */         g.gd.buttonListener = true;
/*  844 */         player.sendMessage("[CityGates] add Button Listener");
/*      */       } catch (Exception e) {
/*  846 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/*  848 */     } else if (args[1].equalsIgnoreCase("false")) {
/*  849 */       gRemoveButton(player, args[0]);
/*      */     } else {
/*  851 */       player.sendMessage("[CityGates] Error parsing: " + args[1]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gGroup(Player player, String[] args)
/*      */   {
/*  857 */     if (args.length == 0) {
/*  858 */       player.sendMessage(new String[] { "/gGroup create [Group Name] - create a new Group", "/gGroup del [Group Name] - delete a Group", "/gGroup add [Group] [Gate] - add a Gate to a Group", "/gGroup remove [Group] [Gate] - remove a Gate from a Group", "/gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate", "/gGroup clear - removes all the Gates in a Group" });
/*      */     }
/*  866 */     else if (args.length > 0)
/*  867 */       if (args[0].equalsIgnoreCase("create")) {
/*  868 */         if (player.hasPermission("citygates.admin.group.create")) {
/*  869 */           if (args.length == 2) {
/*  870 */             GateGroup group = testGateGroup(args[1]);
/*  871 */             if (group == null) {
/*  872 */               Gate gate = testGate(args[1]);
/*  873 */               if (gate == null) {
/*  874 */                 group = new GateGroup(args[1], this.plugin);
/*  875 */                 group.world = player.getWorld().getName();
/*  876 */                 this.gg.add(group);
/*  877 */                 player.sendMessage("[CityGates] Create: " + args[1]);
/*      */               } else {
/*  879 */                 player.sendMessage("[CityGates] A Gate with this name already exists!");
/*      */               }
/*      */             } else {
/*  882 */               player.sendMessage("[CityGates] This Group already exists!");
/*      */             }
/*      */           } else {
/*  885 */             player.sendMessage("[CityGates] /gGroup create [Group Name] - create a new Group");
/*      */           }
/*      */         }
/*  888 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  890 */       else if (args[0].equalsIgnoreCase("del")) {
/*  891 */         if (player.hasPermission("citygates.admin.group.del")) {
/*  892 */           if (args.length == 2) {
/*  893 */             GateGroup group = testGateGroup(args[1]);
/*  894 */             if (group == null)
/*  895 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             else
/*  897 */               for (int a = 0; a < this.gg.size(); a++)
/*  898 */                 if (((GateGroup)this.gg.get(a)).name.equals(args[1])) {
/*  899 */                   this.gg.remove(a);
/*  900 */                   player.sendMessage("[CityGates] Deleting: " + args[1]);
/*      */                 }
/*      */           }
/*      */           else
/*      */           {
/*  905 */             player.sendMessage("[CityGates] /gGroup del [Group Name] - delete a Group");
/*      */           }
/*      */         }
/*  908 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  910 */       else if (args[0].equalsIgnoreCase("add")) {
/*  911 */         if (player.hasPermission("citygates.admin.group.add")) {
/*  912 */           if (args.length == 3) {
/*  913 */             GateGroup group = testGateGroup(args[1]);
/*  914 */             if (group == null) {
/*  915 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             } else {
/*  917 */               Gate gate = testGate(args[2]);
/*  918 */               if (gate == null) {
/*  919 */                 GateGroup gg = testGateGroup(args[2]);
/*  920 */                 if (gg == null) {
/*  921 */                   player.sendMessage("[CityGates] " + args[2] + " not found!");
/*  922 */                 } else if (gg.name.equals(group.name)) {
/*  923 */                   player.sendMessage("[CityGates] Parent is the same as the child!");
/*      */                 } else {
/*  925 */                   group.add(gg);
/*  926 */                   player.sendMessage("[CityGates]" + gg.name + " Added to: " + group.name);
/*      */                 }
/*      */               }
/*      */               else {
/*  930 */                 group.add(gate);
/*  931 */                 player.sendMessage("[CityGates] " + gate.gd.name + " Added to: " + group.name);
/*      */               }
/*      */             }
/*      */           } else {
/*  935 */             player.sendMessage("[CityGates] /gGroup add [Group] [Gate] - add a Gate to a Group");
/*      */           }
/*      */         }
/*  938 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  940 */       else if (args[0].equalsIgnoreCase("remove")) {
/*  941 */         if (player.hasPermission("citygates.admin.group.remove")) {
/*  942 */           if (args.length == 3) {
/*  943 */             GateGroup group = testGateGroup(args[1]);
/*  944 */             if (group == null) {
/*  945 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             } else {
/*  947 */               Gate gate = testGate(args[2]);
/*  948 */               if (gate == null) {
/*  949 */                 GateGroup gg = testGateGroup(args[0]);
/*  950 */                 if (gg == null) {
/*  951 */                   player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */                 } else {
/*  953 */                   group.remove(gg);
/*  954 */                   player.sendMessage("[CityGates] " + gg.name + " Removed from: " + group.name);
/*      */                 }
/*      */               } else {
/*  957 */                 group.remove(gate);
/*  958 */                 player.sendMessage("[CityGates] " + gate.gd.name + " Removed from: " + group.name);
/*      */               }
/*      */             }
/*      */           } else {
/*  962 */             player.sendMessage("[CityGates] /gGroup remove [Group] [Gate] - remove a Gate from a Group");
/*      */           }
/*      */         }
/*  965 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  967 */       else if (args[0].equalsIgnoreCase("setDelay")) {
/*  968 */         if (player.hasPermission("citygates.admin.group.delay")) {
/*  969 */           if (args.length == 3) {
/*  970 */             GateGroup group = testGateGroup(args[1]);
/*  971 */             if (group == null)
/*  972 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             else
/*      */               try {
/*  975 */                 group.delay = Integer.parseInt(args[2]);
/*  976 */                 player.sendMessage("[CityGates] " + args[1] + " setDelay: " + args[2]);
/*      */               } catch (Exception e) {
/*  978 */                 player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */               }
/*      */           }
/*      */           else {
/*  982 */             player.sendMessage("[CityGates] /gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate");
/*      */           }
/*      */         }
/*  985 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*  987 */       else if (args[0].equalsIgnoreCase("clear")) {
/*  988 */         if (player.hasPermission("citygates.admin.group.clear")) {
/*  989 */           if (args.length == 2) {
/*  990 */             GateGroup group = testGateGroup(args[1]);
/*  991 */             if (group == null) {
/*  992 */               player.sendMessage("[CityGates] Can't find Group: " + args[1]);
/*      */             } else {
/*  994 */               group.Clear();
/*  995 */               player.sendMessage("[CityGates] " + args[1] + " is Cleared!");
/*      */             }
/*      */           } else {
/*  998 */             player.sendMessage("[CityGates] /gGroup clear - removes all the Gates in a Group");
/*      */           }
/*      */         }
/* 1001 */         else player.sendMessage("You don't have Permission to use this Command!");
/*      */       }
/*      */       else
/* 1004 */         player.sendMessage(new String[] { "/gGroup create [Group Name] - create a new Group", "/gGroup del [Group Name] - delete a Group", "/gGroup add [Group] [Gate] - add a Gate to a Group", "/gGroup remove [Group] [Gate] - remove a Gate from a Group", "/gGroup setDelay [Group] [Time in milli sec] - add a Time delay between each gate", "/gGroup clear - removes all the Gates in a Group" });
/*      */   }
/*      */ 
/*      */   private boolean createAnimation(Gate g, int dir)
/*      */   {
/* 1017 */     g.Close();
/* 1018 */     ArrayList material = new ArrayList();
/* 1019 */     byte[] bytes = { 0 };
/* 1020 */     material.add(new MaterialId(0, bytes[0]));
/* 1021 */     if (dir == 0)
/* 1022 */       return false;
/* 1023 */     if (dir == 1) {
/* 1024 */       for (int a = 0; a < this.g.size(); a++) {
/* 1025 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1026 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1029 */       int[] x = convertPoints(g.gd.p1[0], g.gd.p2[0]);
/* 1030 */       int x1 = x[0];
/* 1031 */       int x2 = x[1];
/* 1032 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1033 */       gategroup.setDelay(1000L);
/* 1034 */       int count = 0;
/* 1035 */       for (int a = x2; a >= x1; a--) {
/* 1036 */         count++;
/* 1037 */         int[] l1 = { a, g.gd.p1[1], g.gd.p1[2] };
/*      */ 
/* 1040 */         int[] l2 = { a, g.gd.p2[1], g.gd.p2[2] };
/*      */ 
/* 1043 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1044 */         this.g.add(gate);
/* 1045 */         gategroup.add(gate);
/*      */       }
/* 1047 */       this.gg.add(gategroup);
/* 1048 */       return true;
/* 1049 */     }if (dir == 3) {
/* 1050 */       for (int a = 0; a < this.g.size(); a++) {
/* 1051 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1052 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1055 */       int[] x = convertPoints(g.gd.p1[0], g.gd.p2[0]);
/* 1056 */       int x1 = x[0];
/* 1057 */       int x2 = x[1];
/* 1058 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1059 */       gategroup.setDelay(1000L);
/* 1060 */       int count = 0;
/* 1061 */       for (int a = x1; a <= x2; a++) {
/* 1062 */         count++;
/* 1063 */         int[] l1 = { a, g.gd.p1[1], g.gd.p1[2] };
/*      */ 
/* 1066 */         int[] l2 = { a, g.gd.p2[1], g.gd.p2[2] };
/*      */ 
/* 1069 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1070 */         this.g.add(gate);
/* 1071 */         gategroup.add(gate);
/*      */       }
/* 1073 */       this.gg.add(gategroup);
/* 1074 */       return true;
/* 1075 */     }if (dir == 2) {
/* 1076 */       for (int a = 0; a < this.g.size(); a++) {
/* 1077 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1078 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1081 */       int[] z = convertPoints(g.gd.p1[2], g.gd.p2[2]);
/* 1082 */       int z1 = z[0];
/* 1083 */       int z2 = z[1];
/* 1084 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1085 */       gategroup.setDelay(1000L);
/* 1086 */       int count = 0;
/* 1087 */       for (int a = z2; a >= z1; a--) {
/* 1088 */         count++;
/* 1089 */         int[] l1 = { g.gd.p1[0], g.gd.p1[1], a };
/*      */ 
/* 1092 */         int[] l2 = { g.gd.p2[0], g.gd.p2[1], a };
/*      */ 
/* 1095 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1096 */         this.g.add(gate);
/* 1097 */         gategroup.add(gate);
/*      */       }
/* 1099 */       this.gg.add(gategroup);
/* 1100 */       return true;
/* 1101 */     }if (dir == 4) {
/* 1102 */       for (int a = 0; a < this.g.size(); a++) {
/* 1103 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1104 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1107 */       int[] z = convertPoints(g.gd.p1[2], g.gd.p2[2]);
/* 1108 */       int z1 = z[0];
/* 1109 */       int z2 = z[1];
/* 1110 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1111 */       gategroup.setDelay(1000L);
/* 1112 */       int count = 0;
/* 1113 */       for (int a = z1; a <= z2; a++) {
/* 1114 */         count++;
/* 1115 */         int[] l1 = { g.gd.p1[0], g.gd.p1[1], a };
/*      */ 
/* 1118 */         int[] l2 = { g.gd.p2[0], g.gd.p2[1], a };
/*      */ 
/* 1121 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1122 */         this.g.add(gate);
/* 1123 */         gategroup.add(gate);
/*      */       }
/* 1125 */       this.gg.add(gategroup);
/* 1126 */       return true;
/* 1127 */     }if (dir == 5) {
/* 1128 */       for (int a = 0; a < this.g.size(); a++) {
/* 1129 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1130 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1133 */       int[] y = convertPoints(g.gd.p1[1], g.gd.p2[1]);
/* 1134 */       int y1 = y[0];
/* 1135 */       int y2 = y[1];
/* 1136 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1137 */       gategroup.setDelay(1000L);
/* 1138 */       int count = 0;
/* 1139 */       for (int a = y1; a <= y2; a++) {
/* 1140 */         count++;
/* 1141 */         int[] l1 = { g.gd.p1[0], a, g.gd.p1[2] };
/*      */ 
/* 1144 */         int[] l2 = { g.gd.p2[0], a, g.gd.p2[2] };
/*      */ 
/* 1147 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1148 */         this.g.add(gate);
/* 1149 */         gategroup.add(gate);
/*      */       }
/* 1151 */       this.gg.add(gategroup);
/* 1152 */       return true;
/* 1153 */     }if (dir == 6) {
/* 1154 */       for (int a = 0; a < this.g.size(); a++) {
/* 1155 */         if (((Gate)this.g.get(a)).gd.name.equals(g.gd.name)) {
/* 1156 */           this.g.remove(a);
/*      */         }
/*      */       }
/* 1159 */       int[] y = convertPoints(g.gd.p1[1], g.gd.p2[1]);
/* 1160 */       int y1 = y[0];
/* 1161 */       int y2 = y[1];
/* 1162 */       GateGroup gategroup = new GateGroup(g.gd.name, this.plugin);
/* 1163 */       gategroup.setDelay(1000L);
/* 1164 */       int count = 0;
/* 1165 */       for (int a = y2; a >= y1; a--) {
/* 1166 */         count++;
/* 1167 */         int[] l1 = { g.gd.p1[0], a, g.gd.p1[2] };
/*      */ 
/* 1170 */         int[] l2 = { g.gd.p2[0], a, g.gd.p2[2] };
/*      */ 
/* 1173 */         Gate gate = new Gate(new GateData(g.gd.name + String.valueOf(count), l1, l2, g.gd.World, getMaterials(l1, l2, this.plugin.getServer().getWorld(g.gd.World)), material), this.plugin);
/* 1174 */         this.g.add(gate);
/* 1175 */         gategroup.add(gate);
/*      */       }
/* 1177 */       this.gg.add(gategroup);
/* 1178 */       return true;
/*      */     }
/* 1180 */     return false;
/*      */   }
/*      */ 
/*      */   private int[] convertPoints(int i1, int i2) {
/* 1184 */     int x1 = 0;
/* 1185 */     int x2 = 0;
/*      */ 
/* 1187 */     if (i1 < i2) {
/* 1188 */       x1 = i1;
/* 1189 */       x2 = i2;
/*      */     } else {
/* 1191 */       x2 = i1;
/* 1192 */       x1 = i2;
/*      */     }
/* 1194 */     return new int[] { x1, x2 };
/*      */   }
/*      */ 
/*      */   private void showSelection() {
/* 1198 */     int[] x = convertPoints(this.p1.getBlockX(), this.p2.getBlockX());
/* 1199 */     int[] y = convertPoints(this.p1.getBlockY(), this.p2.getBlockY());
/* 1200 */     int[] z = convertPoints(this.p1.getBlockZ(), this.p2.getBlockZ());
/* 1201 */     World w = this.p1.getWorld();
/*      */ 
/* 1203 */     int x1 = x[0]; int x2 = x[1];
/* 1204 */     int y1 = y[0]; int y2 = y[1];
/* 1205 */     int z1 = z[0]; int z2 = z[1];
/*      */ 
/* 1207 */     final ArrayList blocks = new ArrayList();
/* 1208 */     final ArrayList backup = new ArrayList();
/*      */ 
/* 1210 */     for (int a = x1; a <= x2; a++) {
/* 1211 */       for (int b = y1; b <= y2; b++) {
/* 1212 */         for (int c = z1; c <= z2; c++) {
/* 1213 */           blocks.add(w.getBlockAt(a, b, c));
/* 1214 */           backup.add(new MaterialId(w.getBlockAt(a, b, c).getTypeId(), w.getBlockAt(a, b, c).getData()));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1219 */     for (int a = 0; a < blocks.size(); a++) {
/* 1220 */       ((Block)blocks.get(a)).setTypeId(20);
/*      */     }
/*      */ 
/* 1223 */     this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
/*      */     {
/*      */       public void run() {
/* 1226 */         for (int a = 0; a < blocks.size(); a++) {
/* 1227 */           ((Block)blocks.get(a)).setTypeId(((MaterialId)backup.get(a)).getID());
/* 1228 */           ((Block)blocks.get(a)).setData(((MaterialId)backup.get(a)).getData());
/*      */         }
/*      */       }
/*      */     }
/*      */     , 20L);
/*      */   }
/*      */ 
/*      */   private void gReloadConfig()
/*      */   {
/* 1237 */     ArrayList gd = new ArrayList();
/*      */ 
/* 1239 */     Config.mdir();
/*      */     try {
/* 1241 */       gd = Config.LoadGates(this.plugin);
/*      */     } catch (Exception ex) {
/* 1243 */       this.plugin.getLogger().info("No Gates Found!");
/*      */     }
/*      */ 
/* 1246 */     if (gd.isEmpty()) {
/* 1247 */       this.g = new ArrayList();
/*      */     } else {
/* 1249 */       this.g = new ArrayList();
/* 1250 */       for (int a = 0; a < gd.size(); a++) {
/* 1251 */         this.g.add(new Gate((GateData)gd.get(a), this.plugin));
/*      */       }
/*      */     }
/* 1254 */     this.gg = new ArrayList();
/*      */     try {
/* 1256 */       this.gg = Config.LoadGroups(this.g, this.plugin);
/*      */     } catch (Exception e) {
/* 1258 */       this.plugin.getLogger().info("No Groups Found!");
/*      */     }
/*      */ 
/* 1261 */     setGateGroup(this.gg);
/* 1262 */     setGate(this.g);
/*      */   }
/*      */   public abstract void setGateGroup(ArrayList<GateGroup> paramArrayList);
/*      */ 
/*      */   public abstract void setGate(ArrayList<Gate> paramArrayList);
/*      */ 
/*      */   private void setPerm(Player player, String[] args) {
/* 1269 */     Gate gate = testGate(args[0]);
/* 1270 */     if (gate == null) {
/* 1271 */       GateGroup group = testGateGroup(args[0]);
/* 1272 */       if (group == null) {
/* 1273 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       }
/* 1275 */       else if (args[1].equalsIgnoreCase("all")) {
/* 1276 */         if (args[2].equalsIgnoreCase("true")) {
/* 1277 */           group.openPerm = true;
/* 1278 */           group.closePerm = true;
/* 1279 */           group.killPerm = true;
/* 1280 */           group.buttonPerm = true;
/* 1281 */         } else if (args[2].equalsIgnoreCase("false")) {
/* 1282 */           group.openPerm = false;
/* 1283 */           group.closePerm = false;
/* 1284 */           group.killPerm = false;
/* 1285 */           group.buttonPerm = false;
/*      */         } else {
/* 1287 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */         }
/* 1289 */       } else if (args[1].equalsIgnoreCase("open")) {
/* 1290 */         if (args[2].equalsIgnoreCase("true"))
/* 1291 */           group.openPerm = true;
/* 1292 */         else if (args[2].equalsIgnoreCase("false"))
/* 1293 */           group.openPerm = false;
/*      */         else
/* 1295 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1297 */       else if (args[1].equalsIgnoreCase("close")) {
/* 1298 */         if (args[2].equalsIgnoreCase("true"))
/* 1299 */           group.closePerm = true;
/* 1300 */         else if (args[2].equalsIgnoreCase("false"))
/* 1301 */           group.closePerm = false;
/*      */         else
/* 1303 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1305 */       else if (args[1].equalsIgnoreCase("kill")) {
/* 1306 */         if (args[2].equalsIgnoreCase("true"))
/* 1307 */           group.killPerm = true;
/* 1308 */         else if (args[2].equalsIgnoreCase("false"))
/* 1309 */           group.killPerm = false;
/*      */         else
/* 1311 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1313 */       else if (args[1].equalsIgnoreCase("button")) {
/* 1314 */         if (args[2].equalsIgnoreCase("true"))
/* 1315 */           group.buttonPerm = true;
/* 1316 */         else if (args[2].equalsIgnoreCase("false"))
/* 1317 */           group.buttonPerm = false;
/*      */         else {
/* 1319 */           player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/* 1324 */     else if (args[1].equalsIgnoreCase("all")) {
/* 1325 */       if (args[2].equalsIgnoreCase("true")) {
/* 1326 */         gate.gd.openPerm = true;
/* 1327 */         gate.gd.closePerm = true;
/* 1328 */         gate.gd.killPerm = true;
/* 1329 */         gate.gd.buttonPerm = true;
/* 1330 */       } else if (args[2].equalsIgnoreCase("false")) {
/* 1331 */         gate.gd.openPerm = false;
/* 1332 */         gate.gd.closePerm = false;
/* 1333 */         gate.gd.killPerm = false;
/* 1334 */         gate.gd.buttonPerm = false;
/*      */       } else {
/* 1336 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */       }
/* 1338 */     } else if (args[1].equalsIgnoreCase("open")) {
/* 1339 */       if (args[2].equalsIgnoreCase("true"))
/* 1340 */         gate.gd.openPerm = true;
/* 1341 */       else if (args[2].equalsIgnoreCase("false"))
/* 1342 */         gate.gd.openPerm = false;
/*      */       else
/* 1344 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/* 1346 */     else if (args[1].equalsIgnoreCase("close")) {
/* 1347 */       if (args[2].equalsIgnoreCase("true"))
/* 1348 */         gate.gd.closePerm = true;
/* 1349 */       else if (args[2].equalsIgnoreCase("false"))
/* 1350 */         gate.gd.closePerm = false;
/*      */       else
/* 1352 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/* 1354 */     else if (args[1].equalsIgnoreCase("kill")) {
/* 1355 */       if (args[2].equalsIgnoreCase("true"))
/* 1356 */         gate.gd.killPerm = true;
/* 1357 */       else if (args[2].equalsIgnoreCase("false"))
/* 1358 */         gate.gd.killPerm = false;
/*      */       else
/* 1360 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/* 1362 */     else if (args[1].equalsIgnoreCase("button")) {
/* 1363 */       if (args[2].equalsIgnoreCase("true"))
/* 1364 */         gate.gd.buttonPerm = true;
/* 1365 */       else if (args[2].equalsIgnoreCase("false"))
/* 1366 */         gate.gd.buttonPerm = false;
/*      */       else
/* 1368 */         player.sendMessage("[CityGates] Error parsing: " + args[2]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private ArrayList<MaterialId> getMaterials(int[] p1, int[] p2, World w)
/*      */   {
/* 1375 */     int[] x = convertPoints(p1[0], p2[0]);
/* 1376 */     int[] y = convertPoints(p1[1], p2[1]);
/* 1377 */     int[] z = convertPoints(p1[2], p2[2]);
/* 1378 */     ArrayList materials = new ArrayList();
/* 1379 */     for (int a = x[0]; a <= x[1]; a++) {
/* 1380 */       for (int b = y[0]; b <= y[1]; b++) {
/* 1381 */         for (int c = z[0]; c <= z[1]; c++) {
/* 1382 */           Block block = w.getBlockAt(a, b, c);
/* 1383 */           materials.add(new MaterialId(block.getTypeId(), block.getData()));
/*      */         }
/*      */       }
/*      */     }
/* 1387 */     return materials;
/*      */   }
/*      */ 
/*      */   private void gsetClose(Player player, String[] args) {
/* 1391 */     Gate gate = testGate(args[0]);
/* 1392 */     if (gate == null) {
/* 1393 */       GateGroup group = testGateGroup(args[0]);
/* 1394 */       if (group == null)
/* 1395 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       else
/* 1397 */         for (int a = 0; a < group.g.size(); a++)
/*      */           try {
/* 1399 */             GateGroup group2 = (GateGroup)group.g.get(a);
/* 1400 */             gsetClose(player, new String[] { group.name });
/*      */           } catch (Exception e) {
/*      */             try {
/* 1403 */               Gate gate2 = (Gate)group.g.get(a);
/* 1404 */               gsetClose(player, new String[] { gate2.gd.name });
/*      */             } catch (Exception ee) {
/*      */             }
/*      */           }
/*      */     }
/*      */     else {
/* 1410 */       ArrayList material = getMaterials(gate.gd.p1, gate.gd.p2, this.plugin.getServer().getWorld(gate.gd.World));
/* 1411 */       gate.gd.materials2 = material;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gsetOpen(Player player, String[] args) {
/* 1416 */     Gate gate = testGate(args[0]);
/* 1417 */     if (gate == null) {
/* 1418 */       GateGroup group = testGateGroup(args[0]);
/* 1419 */       if (group == null)
/* 1420 */         player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */       else
/* 1422 */         for (int a = 0; a < group.g.size(); a++)
/*      */           try {
/* 1424 */             GateGroup group2 = (GateGroup)group.g.get(a);
/* 1425 */             gsetOpen(player, new String[] { group2.name });
/*      */           } catch (Exception e) {
/*      */             try {
/* 1428 */               Gate gate2 = (Gate)group.g.get(a);
/* 1429 */               gsetOpen(player, new String[] { gate.gd.name });
/*      */             } catch (Exception ee) {
/*      */             }
/*      */           }
/*      */     }
/*      */     else {
/* 1435 */       ArrayList material = getMaterials(gate.gd.p1, gate.gd.p2, this.plugin.getServer().getWorld(gate.gd.World));
/* 1436 */       gate.gd.materials1 = material;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void gAnimation(Player player, String[] args) {
/* 1441 */     Gate gate = testGate(args[0]);
/* 1442 */     if (gate == null) {
/* 1443 */       player.sendMessage("[CityGates] " + args[0] + " not found!");
/*      */     }
/* 1445 */     else if (!createAnimation(gate, Directions.getDirection(args[1])))
/* 1446 */       player.sendMessage("[CityGates] Unknow Direction: " + args[1]);
/*      */     else
/* 1448 */       player.sendMessage("[CityGates] Adding Animation to " + args[0]);
/*      */   }
/*      */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.Commands
 * JD-Core Version:    0.6.2
 */