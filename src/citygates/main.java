/*     */ package citygates;
/*     */ 
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
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class main extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*     */   private Commands exe;
/*     */   private Downloader downloader;
/*     */   private Listeners l;
/*     */   private ArrayList<GateData> gd;
/*     */   private ArrayList<Gate> g;
/*     */   private ArrayList<GateGroup> gg;
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  28 */     this.l = new Listeners(this) {
/*     */       public void onTimeChanged(int time) {
/*  30 */         for (int a = 0; a < main.this.g.size(); a++) {
/*  31 */           if (((Gate)main.this.g.get(a)).gd.timeGate) {
/*  32 */             ((Gate)main.this.g.get(a)).setTime(time);
/*     */           }
/*     */         }
/*  35 */         for (int a = 0; a < main.this.gg.size(); a++)
/*  36 */           if (((GateGroup)main.this.gg.get(a)).timegate)
/*  37 */             ((GateGroup)main.this.gg.get(a)).setTime(time);
/*     */       }
/*     */     };
/*  42 */     getServer().getPluginManager().registerEvents(this, this);
/*     */ 
/*  45 */     this.gd = new ArrayList();
/*     */ 
/*  47 */     Config.mdir();
/*     */     try {
/*  49 */       this.gd = Config.LoadGates(this);
/*     */     } catch (Exception ex) {
/*  51 */       getLogger().info("No Gates Found!");
/*     */     }
/*     */ 
/*  54 */     if (this.gd.size() == 0) {
/*  55 */       this.g = new ArrayList();
/*     */     } else {
/*  57 */       this.g = new ArrayList();
/*  58 */       for (int a = 0; a < this.gd.size(); a++) {
/*  59 */         this.g.add(new Gate((GateData)this.gd.get(a), this));
/*     */       }
/*     */     }
/*  62 */     this.gg = new ArrayList();
/*     */     try {
/*  64 */       this.gg = Config.LoadGroups(this.g, this);
/*     */     } catch (Exception e) {
/*  66 */       getLogger().info("No Groups Found!");
/*     */     }
/*  68 */     this.exe = new Commands(this, this.g, this.gg)
/*     */     {
/*     */       public void setGateGroup(ArrayList<GateGroup> group)
/*     */       {
/*  72 */         main.this.gg = group;
/*     */       }
/*     */ 
/*     */       public void setGate(ArrayList<Gate> gate)
/*     */       {
/*  77 */         main.this.g = gate;
/*     */       }
/*     */     };
/*  80 */     this.downloader = new Downloader(this);
/*     */   }
/*     */ 
/*     */   public void onDisable()
/*     */   {
/*  85 */     this.gd = new ArrayList();
/*  86 */     for (int a = 0; a < this.g.size(); a++) {
/*  87 */       GateData gatedata = ((Gate)this.g.get(a)).gd;
/*  88 */       this.gd.add(gatedata);
/*     */     }
/*  90 */     getLogger().info(String.valueOf(this.gd.size()));
/*     */     try {
/*  92 */       Config.SaveGates(this.gd);
/*     */     } catch (Exception ex) {
/*  94 */       getLogger().log(Level.SEVERE, null, ex);
/*     */     }
/*     */     try {
/*  97 */       Config.SaveGroups(this.gg);
/*     */     } catch (Exception e) {
/*  99 */       getLogger().log(Level.SEVERE, null, e);
/*     */     }
/* 101 */     getServer().getScheduler().cancelAllTasks();
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
/*     */   {
/* 106 */     boolean command = false;
/* 107 */     if ((cs instanceof Player))
/* 108 */       command = this.exe.onCommand(cs, cmnd, string, strings);
/*     */     else
/* 110 */       command = this.downloader.onCommand(cs, cmnd, string, strings);
/* 111 */     this.gd = new ArrayList();
/* 112 */     for (int a = 0; a < this.g.size(); a++) {
/* 113 */       GateData gatedata = ((Gate)this.g.get(a)).gd;
/* 114 */       this.gd.add(gatedata);
/*     */     }
/*     */     try {
/* 117 */       Config.SaveGates(this.gd);
/*     */     } catch (Exception ex) {
/* 119 */       getLogger().log(Level.SEVERE, null, ex);
/*     */     }
/*     */     try {
/* 122 */       Config.SaveGroups(this.gg);
/*     */     } catch (Exception e) {
/* 124 */       getLogger().log(Level.SEVERE, null, e);
/*     */     }
/* 126 */     return command;
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onRedstoneListener(BlockRedstoneEvent event)
/*     */   {
/* 132 */     for (int a = 0; a < this.g.size(); a++) {
/* 133 */       if (((Gate)this.g.get(a)).gd.redstoneListener) {
/* 134 */         Location l = event.getBlock().getLocation();
/* 135 */         if ((l.getBlockX() == ((Gate)this.g.get(a)).gd.pr[0]) && (l.getBlockY() == ((Gate)this.g.get(a)).gd.pr[1]) && (l.getBlockZ() == ((Gate)this.g.get(a)).gd.pr[2]) && (l.getWorld().getName().equals(((Gate)this.g.get(a)).gd.World)))
/*     */         {
/* 139 */           if (!((Gate)this.g.get(a)).gd.open)
/* 140 */             ((Gate)this.g.get(a)).Open();
/*     */           else {
/* 142 */             ((Gate)this.g.get(a)).Close();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 148 */     for (int a = 0; a < this.gg.size(); a++)
/* 149 */       if (((GateGroup)this.gg.get(a)).redstoneListener) {
/* 150 */         Location l = event.getBlock().getLocation();
/* 151 */         if ((l.getBlockX() == ((GateGroup)this.gg.get(a)).pr[0]) && (l.getBlockY() == ((GateGroup)this.gg.get(a)).pr[1]) && (l.getBlockZ() == ((GateGroup)this.gg.get(a)).pr[2]) && (l.getWorld().getName().equals(((GateGroup)this.gg.get(a)).world)))
/*     */         {
/* 155 */           if (!((GateGroup)this.gg.get(a)).open)
/* 156 */             ((GateGroup)this.gg.get(a)).Open();
/*     */           else
/* 158 */             ((GateGroup)this.gg.get(a)).Close();
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onKillListener(EntityDeathEvent event)
/*     */   {
/* 167 */     for (int a = 0; a < this.g.size(); a++) {
/*     */       try {
/* 169 */         if ((((Gate)this.g.get(a)).gd.mobKill) && 
/* 170 */           (((Gate)this.g.get(a)).gd.mob.equals(event.getEntityType().getName())) && (((Gate)this.g.get(a)).gd.World.equals(event.getEntity().getWorld().getName())))
/* 171 */           if (((((Gate)this.g.get(a)).gd.killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate." + ((Gate)this.g.get(a)).gd.name))) || ((((Gate)this.g.get(a)).gd.killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate.*"))))
/*     */           {
/* 173 */             ((Gate)this.g.get(a)).Open();
/* 174 */             if (((Gate)this.g.get(a)).gd.killMsg != null)
/* 175 */               event.getEntity().getKiller().sendMessage(((Gate)this.g.get(a)).gd.killMsg);
/*     */           }
/* 177 */           else if (!((Gate)this.g.get(a)).gd.killPerm) {
/* 178 */             ((Gate)this.g.get(a)).Open();
/* 179 */             if (((Gate)this.g.get(a)).gd.killMsg != null)
/* 180 */               event.getEntity().getKiller().sendMessage(((Gate)this.g.get(a)).gd.killMsg);
/*     */           }
/*     */           else {
/* 183 */             event.getEntity().getKiller().sendMessage("You don't have Permssion to use this Gate!");
/*     */           }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/* 190 */     for (int a = 0; a < this.gg.size(); a++)
/*     */       try {
/* 192 */         if ((((GateGroup)this.gg.get(a)).mobKill) && 
/* 193 */           (((GateGroup)this.gg.get(a)).mob.equals(event.getEntityType().getName())) && (((GateGroup)this.gg.get(a)).world.equals(event.getEntity().getWorld().getName())))
/* 194 */           if (((((GateGroup)this.gg.get(a)).killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate." + ((GateGroup)this.gg.get(a)).name))) || ((((GateGroup)this.gg.get(a)).killPerm) && (event.getEntity().getKiller().hasPermission("citygates.user.gate.*"))))
/*     */           {
/* 196 */             ((GateGroup)this.gg.get(a)).Open();
/* 197 */             if (((GateGroup)this.gg.get(a)).killMsg != null)
/* 198 */               event.getEntity().getKiller().sendMessage(((GateGroup)this.gg.get(a)).killMsg);
/*     */           }
/* 200 */           else if (!((GateGroup)this.gg.get(a)).killPerm) {
/* 201 */             ((GateGroup)this.gg.get(a)).Open();
/* 202 */             if (((GateGroup)this.gg.get(a)).killMsg != null)
/* 203 */               event.getEntity().getKiller().sendMessage(((GateGroup)this.gg.get(a)).killMsg);
/*     */           }
/*     */           else {
/* 206 */             event.getEntity().getKiller().sendMessage("You don't have Permssion to use this Gate!");
/*     */           }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onButtonClicked(PlayerInteractEvent event) {
/* 216 */     if ((event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (event.getClickedBlock().getType().equals(Material.STONE_BUTTON))) {
/* 217 */       for (int a = 0; a < this.g.size(); a++) {
/* 218 */         if (((Gate)this.g.get(a)).gd.buttonListener) {
/* 219 */           int[] button = ((Gate)this.g.get(a)).gd.button;
/* 220 */           if ((button[0] == event.getClickedBlock().getX()) && (button[1] == event.getClickedBlock().getY()) && (button[2] == event.getClickedBlock().getZ()))
/*     */           {
/* 223 */             if ((((Gate)this.g.get(a)).gd.buttonPerm) && (event.getPlayer().hasPermission("citygates.user.gate." + ((Gate)this.g.get(a)).gd.name)))
/* 224 */               ButtonAction((Gate)this.g.get(a));
/* 225 */             else if (!((Gate)this.g.get(a)).gd.buttonPerm)
/* 226 */               ButtonAction((Gate)this.g.get(a));
/*     */             else {
/* 228 */               event.getPlayer().sendMessage("You don't have Permssion to use this Gate!");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 234 */       for (int a = 0; a < this.gg.size(); a++)
/* 235 */         if (((GateGroup)this.gg.get(a)).buttonListener) {
/* 236 */           int[] button = ((GateGroup)this.gg.get(a)).button;
/* 237 */           if ((button[0] == event.getClickedBlock().getX()) && (button[1] == event.getClickedBlock().getY()) && (button[2] == event.getClickedBlock().getZ()))
/*     */           {
/* 240 */             if ((((GateGroup)this.gg.get(a)).buttonPerm) && (event.getPlayer().hasPermission("citygates.user.gate." + ((GateGroup)this.gg.get(a)).name)))
/* 241 */               ButtonAction((GateGroup)this.gg.get(a));
/* 242 */             else if (!((GateGroup)this.gg.get(a)).buttonPerm)
/* 243 */               ButtonAction((GateGroup)this.gg.get(a));
/*     */             else
/* 245 */               event.getPlayer().sendMessage("You don't have Permssion to use this Gate!");
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void ButtonAction(final Gate gate)
/*     */   {
/* 254 */     gate.Open();
/* 255 */     new Thread(new Runnable() {
/*     */       public void run() {
/*     */         try {
/* 258 */           Thread.sleep(gate.gd.ButtonInterval); } catch (InterruptedException ex) {
/*     */         }
/* 260 */         gate.Close();
/*     */       }
/*     */     }).start();
/*     */   }
/*     */ 
/*     */   private void ButtonAction(final GateGroup group)
/*     */   {
/* 266 */     group.Open();
/* 267 */     new Thread(new Runnable() {
/*     */       public void run() {
/*     */         try {
/* 270 */           Thread.sleep(group.ButtonInterval); } catch (InterruptedException ex) {
/*     */         }
/* 272 */         group.Close();
/*     */       }
/*     */     }).start();
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (1).jar
 * Qualified Name:     citygates.main
 * JD-Core Version:    0.6.2
 */