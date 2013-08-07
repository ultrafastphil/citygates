/*     */ package citygates;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class main extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*     */   private Commands exe;
/*     */   private UpdateChecker updater;
/*     */   private Listeners l;
/*     */   private ArrayList<GateData> gd;
/*     */   private ArrayList<Gate> g;
/*     */   private ArrayList<GateGroup> gg;
/*     */   private General general;
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  36 */     Config.RemoveOldDownloader(this);
/*     */ 
/*  38 */     getServer().getPluginManager().registerEvents(this, this);
/*     */ 
/*  40 */     this.gd = new ArrayList();
/*     */ 
/*  42 */     Config.mdir();
/*     */     try {
/*  44 */       this.gd = Config.LoadGates(this);
/*     */     } catch (Exception ex) {
/*  46 */       getLogger().info("No Gates Found!");
/*     */     }
/*     */ 
/*  49 */     if (this.gd.size() == 0) {
/*  50 */       this.g = new ArrayList();
/*     */     } else {
/*  52 */       this.g = new ArrayList();
/*  53 */       for (int a = 0; a < this.gd.size(); a++) {
/*  54 */         this.g.add(new Gate((GateData)this.gd.get(a), this));
/*     */       }
/*     */     }
/*  57 */     this.gg = new ArrayList();
/*     */     try {
/*  59 */       this.gg = Config.LoadGroups(this.g, this);
/*     */     } catch (Exception e) {
/*  61 */       getLogger().info("No Groups Found!");
/*     */     }
/*  63 */     this.exe = new Commands(this, this.g, this.gg)
/*     */     {
/*     */       public void setGateGroup(ArrayList<GateGroup> group)
/*     */       {
/*  67 */         main.this.gg = group;
/*     */       }
/*     */ 
/*     */       public void setGate(ArrayList<Gate> gate)
/*     */       {
/*  72 */         main.this.g = gate;
/*     */       }
/*     */ 
/*     */       public void setGeneral(General g)
/*     */       {
/*  77 */         main.this.general = g;
/*     */       } } ;
/*     */     try {
/*  80 */       this.general = Config.LoadGeneral(this);
/*     */     } catch (Exception ex) {
/*  82 */       getLogger().warning("Unable to load General.yml - Using Standard");
/*  83 */       this.general = new General();
/*     */     }
/*     */ 
/*  86 */     this.l = new Listeners(this, this.general) {
/*     */       public void onTimeChanged(int time) {
/*  88 */         for (int a = 0; a < main.this.g.size(); a++) {
/*  89 */           if (((Gate)main.this.g.get(a)).gd.timeGate) {
/*  90 */             ((Gate)main.this.g.get(a)).setTime(time);
/*     */           }
/*     */         }
/*  93 */         for (int a = 0; a < main.this.gg.size(); a++)
/*  94 */           if (((GateGroup)main.this.gg.get(a)).timegate)
/*  95 */             ((GateGroup)main.this.gg.get(a)).setTime(time);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void onDisable()
/*     */   {
/* 104 */     Config.mdir();
/* 105 */     this.gd = new ArrayList();
/* 106 */     for (int a = 0; a < this.g.size(); a++) {
/* 107 */       GateData gatedata = ((Gate)this.g.get(a)).gd;
/* 108 */       this.gd.add(gatedata);
/*     */     }
/*     */     try {
/* 111 */       Config.SaveGates(this.gd);
/*     */     } catch (Exception ex) {
/* 113 */       getLogger().log(Level.SEVERE, null, ex);
/*     */     }
/*     */     try {
/* 116 */       Config.SaveGroups(this.gg);
/*     */     } catch (Exception e) {
/* 118 */       getLogger().log(Level.SEVERE, null, e);
/*     */     }
/*     */     try {
/* 121 */       Config.SaveGereral(this.general);
/*     */     } catch (IOException ex) {
/* 123 */       getLogger().log(Level.SEVERE, null, ex);
/*     */     }
/* 125 */     getServer().getScheduler().cancelAllTasks();
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
/*     */   {
/* 130 */     boolean command = false;
/* 131 */     if ((cs instanceof Player))
/* 132 */       command = this.exe.onCommand(cs, cmnd, string, strings);
/*     */     else
/* 134 */       command = this.updater.onCommand(cs, cmnd, string, strings);
/* 135 */     this.gd = new ArrayList();
/* 136 */     for (int a = 0; a < this.g.size(); a++) {
/* 137 */       GateData gatedata = ((Gate)this.g.get(a)).gd;
/* 138 */       this.gd.add(gatedata);
/*     */     }
/*     */     try {
/* 141 */       Config.SaveGates(this.gd);
/*     */     } catch (Exception ex) {
/* 143 */       getLogger().log(Level.SEVERE, null, ex);
/*     */     }
/*     */     try {
/* 146 */       Config.SaveGroups(this.gg);
/*     */     } catch (Exception e) {
/* 148 */       getLogger().log(Level.SEVERE, null, e);
/*     */     }
/* 150 */     return command;
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onRedstoneListener(BlockRedstoneEvent event) {
/* 155 */     for (int a = 0; a < this.g.size(); a++) {
/* 156 */       if (((Gate)this.g.get(a)).gd.redstoneListener) {
/* 157 */         Location l = event.getBlock().getLocation();
/* 158 */         if ((l.getBlockX() == ((Gate)this.g.get(a)).gd.pr[0]) && (l.getBlockY() == ((Gate)this.g.get(a)).gd.pr[1]) && (l.getBlockZ() == ((Gate)this.g.get(a)).gd.pr[2]) && (l.getWorld().getName().equals(((Gate)this.g.get(a)).gd.World)))
/*     */         {
/* 162 */           if (!((Gate)this.g.get(a)).gd.open)
/* 163 */             ((Gate)this.g.get(a)).Open();
/*     */           else {
/* 165 */             ((Gate)this.g.get(a)).Close();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 171 */     for (int a = 0; a < this.gg.size(); a++)
/* 172 */       if (((GateGroup)this.gg.get(a)).redstoneListener) {
/* 173 */         Location l = event.getBlock().getLocation();
/* 174 */         if ((l.getBlockX() == ((GateGroup)this.gg.get(a)).pr[0]) && (l.getBlockY() == ((GateGroup)this.gg.get(a)).pr[1]) && (l.getBlockZ() == ((GateGroup)this.gg.get(a)).pr[2]) && (l.getWorld().getName().equals(((GateGroup)this.gg.get(a)).world)))
/*     */         {
/* 178 */           if (!((GateGroup)this.gg.get(a)).open)
/* 179 */             ((GateGroup)this.gg.get(a)).Open();
/*     */           else
/* 181 */             ((GateGroup)this.gg.get(a)).Close();
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onKillListener(EntityDeathEvent event) {
/* 188 */     for (int a = 0; a < this.g.size(); a++)
/*     */       try {
/* 190 */         if ((((Gate)this.g.get(a)).gd.mobKill) && (((Gate)this.g.get(a)).gd.mob.equals(event.getEntityType().getName())) && (((Gate)this.g.get(a)).gd.World.equals(event.getEntity().getWorld().getName())))
/*     */         {
/* 192 */           if (((((Gate)this.g.get(a)).gd.killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate." + ((Gate)this.g.get(a)).gd.name))) || ((((Gate)this.g.get(a)).gd.killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate.*"))) || (event.getEntity().getKiller().isOp()))
/*     */           {
/* 195 */             ((Gate)this.g.get(a)).Open();
/* 196 */             if (((Gate)this.g.get(a)).gd.killMsg != null)
/* 197 */               event.getEntity().getKiller().sendMessage(((Gate)this.g.get(a)).gd.killMsg);
/*     */           }
/* 199 */           else if (!((Gate)this.g.get(a)).gd.killPerm) {
/* 200 */             ((Gate)this.g.get(a)).Open();
/* 201 */             if (((Gate)this.g.get(a)).gd.killMsg != null)
/* 202 */               event.getEntity().getKiller().sendMessage(((Gate)this.g.get(a)).gd.killMsg);
/*     */           }
/*     */           else {
/* 205 */             event.getEntity().getKiller().sendMessage("You don't have Permssion to use this Gate!");
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/* 212 */     for (int a = 0; a < this.gg.size(); a++)
/*     */       try {
/* 214 */         if ((((GateGroup)this.gg.get(a)).mobKill) && (((GateGroup)this.gg.get(a)).mob.equals(event.getEntityType().getName())) && (((GateGroup)this.gg.get(a)).world.equals(event.getEntity().getWorld().getName())))
/*     */         {
/* 216 */           if (((((GateGroup)this.gg.get(a)).killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate." + ((GateGroup)this.gg.get(a)).name))) || ((((GateGroup)this.gg.get(a)).killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate.*"))) || (event.getEntity().getKiller().isOp()))
/*     */           {
/* 219 */             ((GateGroup)this.gg.get(a)).Open();
/* 220 */             if (((GateGroup)this.gg.get(a)).killMsg != null)
/* 221 */               event.getEntity().getKiller().sendMessage(((GateGroup)this.gg.get(a)).killMsg);
/*     */           }
/* 223 */           else if (!((GateGroup)this.gg.get(a)).killPerm) {
/* 224 */             ((GateGroup)this.gg.get(a)).Open();
/* 225 */             if (((GateGroup)this.gg.get(a)).killMsg != null)
/* 226 */               event.getEntity().getKiller().sendMessage(((GateGroup)this.gg.get(a)).killMsg);
/*     */           }
/*     */           else {
/* 229 */             event.getEntity().getKiller().sendMessage("You don't have Permssion to use this Gate!");
/*     */           }
/*     */         }
/*     */       } catch (Exception localException1) {
/*     */       }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onButtonClicked(PlayerInteractEvent event) {
/* 238 */     if ((event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (event.getClickedBlock().getType().equals(Material.STONE_BUTTON))) {
/* 239 */       for (int a = 0; a < this.g.size(); a++) {
/* 240 */         if (((Gate)this.g.get(a)).gd.buttonListener) {
/* 241 */           int[] button = ((Gate)this.g.get(a)).gd.button;
/* 242 */           if ((button[0] == event.getClickedBlock().getX()) && (button[1] == event.getClickedBlock().getY()) && (button[2] == event.getClickedBlock().getZ()))
/*     */           {
/* 245 */             if ((((Gate)this.g.get(a)).gd.buttonPerm) && ((event.getPlayer().hasPermission("citygates.user.gate." + ((Gate)this.g.get(a)).gd.name)) || (event.getPlayer().isOp())))
/* 246 */               ButtonAction((Gate)this.g.get(a));
/* 247 */             else if (!((Gate)this.g.get(a)).gd.buttonPerm)
/* 248 */               ButtonAction((Gate)this.g.get(a));
/*     */             else {
/* 250 */               event.getPlayer().sendMessage("You don't have Permssion to use this Gate!");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 256 */       for (int a = 0; a < this.gg.size(); a++)
/* 257 */         if (((GateGroup)this.gg.get(a)).buttonListener) {
/* 258 */           int[] button = ((GateGroup)this.gg.get(a)).button;
/* 259 */           if ((button[0] == event.getClickedBlock().getX()) && (button[1] == event.getClickedBlock().getY()) && (button[2] == event.getClickedBlock().getZ()))
/*     */           {
/* 262 */             if ((((GateGroup)this.gg.get(a)).buttonPerm) && ((event.getPlayer().hasPermission("citygates.user.gate." + ((GateGroup)this.gg.get(a)).name)) || (event.getPlayer().isOp())))
/* 263 */               ButtonAction((GateGroup)this.gg.get(a));
/* 264 */             else if (!((GateGroup)this.gg.get(a)).buttonPerm)
/* 265 */               ButtonAction((GateGroup)this.gg.get(a));
/*     */             else
/* 267 */               event.getPlayer().sendMessage("You don't have Permssion to use this Gate!");
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void ButtonAction(final Gate gate)
/*     */   {
/* 275 */     gate.Open();
/* 276 */     new BukkitRunnable() {
/*     */       public void run() {
/* 278 */         gate.Close();
/*     */       }
/*     */     }
/* 280 */     .runTaskLater(this, ()(gate.gd.ButtonInterval / 1000.0D * 20.0D));
/*     */   }
/*     */ 
/*     */   private void ButtonAction(final GateGroup group)
/*     */   {
/* 285 */     group.Open();
/* 286 */     new BukkitRunnable() {
/*     */       public void run() {
/* 288 */         group.Close();
/*     */       }
/*     */     }
/* 290 */     .runTaskLater(this, ()(group.ButtonInterval / 1000.0D * 20.0D));
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.main
 * JD-Core Version:    0.6.2
 */