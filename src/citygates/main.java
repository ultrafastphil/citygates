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
/*     */   private Listeners l;
/*     */   private ArrayList<GateData> gd;
/*     */   private ArrayList<Gate> g;
/*     */   private ArrayList<GateGroup> gg;
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  26 */     this.l = new Listeners(this) {
/*     */       public void onTimeChanged(int time) {
/*  28 */         for (int a = 0; a < main.this.g.size(); a++) {
/*  29 */           if (((Gate)main.this.g.get(a)).gd.timeGate) {
/*  30 */             ((Gate)main.this.g.get(a)).setTime(time);
/*     */           }
/*     */         }
/*  33 */         for (int a = 0; a < main.this.gg.size(); a++)
/*  34 */           if (((GateGroup)main.this.gg.get(a)).timegate)
/*  35 */             ((GateGroup)main.this.gg.get(a)).setTime(time);
/*     */       }
/*     */     };
/*  40 */     getServer().getPluginManager().registerEvents(this, this);
/*     */ 
/*  43 */     this.gd = new ArrayList();
/*     */ 
/*  45 */     Config.mdir();
/*     */     try {
/*  47 */       this.gd = Config.LoadGates(this);
/*     */     } catch (Exception ex) {
/*  49 */       getLogger().info("No Gates Found!");
/*     */     }
/*     */ 
/*  52 */     if (this.gd.size() == 0) {
/*  53 */       this.g = new ArrayList();
/*     */     } else {
/*  55 */       this.g = new ArrayList();
/*  56 */       for (int a = 0; a < this.gd.size(); a++) {
/*  57 */         this.g.add(new Gate((GateData)this.gd.get(a), this));
/*     */       }
/*     */     }
/*  60 */     this.gg = new ArrayList();
/*     */     try {
/*  62 */       this.gg = Config.LoadGroups(this.g, this);
/*     */     } catch (Exception e) {
/*  64 */       getLogger().info("No Groups Found!");
/*     */     }
/*  66 */     this.exe = new Commands(this, this.g, this.gg);
/*  67 */     new Downloader(this);
/*     */   }
/*     */ 
/*     */   public void onDisable()
/*     */   {
/*  72 */     this.gd = new ArrayList();
/*  73 */     for (int a = 0; a < this.g.size(); a++) {
/*  74 */       GateData gatedata = ((Gate)this.g.get(a)).gd;
/*  75 */       this.gd.add(gatedata);
/*     */     }
/*  77 */     getLogger().info(String.valueOf(this.gd.size()));
/*     */     try {
/*  79 */       Config.SaveGates(this.gd);
/*     */     } catch (Exception ex) {
/*  81 */       getLogger().log(Level.SEVERE, null, ex);
/*     */     }
/*     */     try {
/*  84 */       Config.SaveGroups(this.gg);
/*     */     } catch (Exception e) {
/*  86 */       getLogger().log(Level.SEVERE, null, e);
/*     */     }
/*  88 */     for (int a = 0; a < this.g.size(); a++) {
/*  89 */       ((Gate)this.g.get(a)).Close();
/*     */     }
/*  91 */     getServer().getScheduler().cancelAllTasks();
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
/*     */   {
/*  96 */     boolean command = this.exe.onCommand(cs, cmnd, string, strings);
/*  97 */     this.gd = new ArrayList();
/*  98 */     for (int a = 0; a < this.g.size(); a++) {
/*  99 */       GateData gatedata = ((Gate)this.g.get(a)).gd;
/* 100 */       this.gd.add(gatedata);
/*     */     }
/*     */     try {
/* 103 */       Config.SaveGates(this.gd);
/*     */     } catch (Exception ex) {
/* 105 */       getLogger().log(Level.SEVERE, null, ex);
/*     */     }
/*     */     try {
/* 108 */       Config.SaveGroups(this.gg);
/*     */     } catch (Exception e) {
/* 110 */       getLogger().log(Level.SEVERE, null, e);
/*     */     }
/* 112 */     return command;
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onRedstoneListener(BlockRedstoneEvent event)
/*     */   {
/* 118 */     for (int a = 0; a < this.g.size(); a++) {
/* 119 */       if (((Gate)this.g.get(a)).gd.redstoneListener) {
/* 120 */         Location l = event.getBlock().getLocation();
/* 121 */         if ((l.getBlockX() == ((Gate)this.g.get(a)).gd.pr[0]) && (l.getBlockY() == ((Gate)this.g.get(a)).gd.pr[1]) && (l.getBlockZ() == ((Gate)this.g.get(a)).gd.pr[2]) && (l.getWorld().getName().equals(((Gate)this.g.get(a)).gd.World)))
/*     */         {
/* 125 */           if (!((Gate)this.g.get(a)).gd.open)
/* 126 */             ((Gate)this.g.get(a)).Open();
/*     */           else {
/* 128 */             ((Gate)this.g.get(a)).Close();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 134 */     for (int a = 0; a < this.gg.size(); a++)
/* 135 */       if (((GateGroup)this.gg.get(a)).redstoneListener) {
/* 136 */         Location l = event.getBlock().getLocation();
/* 137 */         if ((l.getBlockX() == ((GateGroup)this.gg.get(a)).pr[0]) && (l.getBlockY() == ((GateGroup)this.gg.get(a)).pr[1]) && (l.getBlockZ() == ((GateGroup)this.gg.get(a)).pr[2]) && (l.getWorld().getName().equals(((GateGroup)this.gg.get(a)).world)))
/*     */         {
/* 141 */           if (!((GateGroup)this.gg.get(a)).open)
/* 142 */             ((GateGroup)this.gg.get(a)).Open();
/*     */           else
/* 144 */             ((GateGroup)this.gg.get(a)).Close();
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onKillListener(EntityDeathEvent event)
/*     */   {
/* 153 */     for (int a = 0; a < this.g.size(); a++) {
/*     */       try {
/* 155 */         if ((((Gate)this.g.get(a)).gd.mobKill) && 
/* 156 */           (((Gate)this.g.get(a)).gd.mob.equals(event.getEntityType().getName())) && (((Gate)this.g.get(a)).gd.World.equals(event.getEntity().getWorld().getName()))) {
/* 157 */           ((Gate)this.g.get(a)).Open();
/* 158 */           if (((Gate)this.g.get(a)).gd.killMsg != null)
/* 159 */             event.getEntity().getKiller().sendMessage(((Gate)this.g.get(a)).gd.killMsg);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/* 166 */     for (int a = 0; a < this.gg.size(); a++)
/*     */       try {
/* 168 */         if ((((GateGroup)this.gg.get(a)).mobKill) && 
/* 169 */           (((GateGroup)this.gg.get(a)).mob.equals(event.getEntityType().getName())) && (((GateGroup)this.gg.get(a)).world.equals(event.getEntity().getWorld().getName()))) {
/* 170 */           ((GateGroup)this.gg.get(a)).Open();
/* 171 */           if (((GateGroup)this.gg.get(a)).killMsg != null)
/* 172 */             event.getEntity().getKiller().sendMessage(((GateGroup)this.gg.get(a)).killMsg);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onButtonClicked(PlayerInteractEvent event) {
/* 182 */     if ((event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (event.getClickedBlock().getType().equals(Material.STONE_BUTTON))) {
/* 183 */       for (int a = 0; a < this.g.size(); a++) {
/* 184 */         if (((Gate)this.g.get(a)).gd.buttonListener) {
/* 185 */           int[] button = ((Gate)this.g.get(a)).gd.button;
/* 186 */           if ((button[0] == event.getClickedBlock().getX()) && (button[1] == event.getClickedBlock().getY()) && (button[2] == event.getClickedBlock().getZ()))
/*     */           {
/* 189 */             ((Gate)this.g.get(a)).Open();
/* 190 */             final int b = a;
/* 191 */             new Thread(new Runnable() {
/*     */               public void run() {
/*     */                 try {
/* 194 */                   Thread.sleep(((Gate)main.this.g.get(b)).gd.ButtonInterval); } catch (InterruptedException ex) {
/*     */                 }
/* 196 */                 ((Gate)main.this.g.get(b)).Close();
/*     */               }
/*     */             }).start();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 203 */       for (int a = 0; a < this.gg.size(); a++)
/* 204 */         if (((GateGroup)this.gg.get(a)).buttonListener) {
/* 205 */           int[] button = ((GateGroup)this.gg.get(a)).button;
/* 206 */           if ((button[0] == event.getClickedBlock().getX()) && (button[1] == event.getClickedBlock().getY()) && (button[2] == event.getClickedBlock().getZ()))
/*     */           {
/* 209 */             ((GateGroup)this.gg.get(a)).Open();
/* 210 */             final int b = a;
/* 211 */             new Thread(new Runnable() {
/*     */               public void run() {
/*     */                 try {
/* 214 */                   Thread.sleep(((GateGroup)main.this.gg.get(b)).ButtonInterval); } catch (InterruptedException ex) {
/*     */                 }
/* 216 */                 ((GateGroup)main.this.gg.get(b)).Close();
/*     */               }
/*     */             }).start();
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates.jar
 * Qualified Name:     citygates.main
 * JD-Core Version:    0.6.2
 */