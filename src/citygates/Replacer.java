/*    */ package citygates;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.Clipboard;
/*    */ import java.awt.datatransfer.ClipboardOwner;
/*    */ import java.awt.datatransfer.DataFlavor;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import java.awt.datatransfer.Transferable;
/*    */ import java.awt.datatransfer.UnsupportedFlavorException;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class Replacer
/*    */   implements ClipboardOwner
/*    */ {
/*    */   public static void main(String[] ars)
/*    */   {
/* 23 */     new Replacer().Start();
/*    */   }
/*    */ 
/*    */   public void Start() {
/* 27 */     JFrame f = new JFrame("REPLACER");
/* 28 */     final JTextField tf1 = new JTextField();
/* 29 */     final JTextField tf2 = new JTextField();
/* 30 */     JButton b1 = new JButton("Replace");
/* 31 */     JButton b2 = new JButton("Clear");
/* 32 */     f.setLayout(null);
/* 33 */     f.setBounds(500, 500, 300, 115);
/* 34 */     f.setResizable(false);
/* 35 */     f.add(tf1);
/* 36 */     f.add(tf2);
/* 37 */     f.add(b1);
/* 38 */     f.add(b2);
/* 39 */     tf1.setBounds(0, 0, 300, 25);
/* 40 */     tf2.setBounds(0, 30, 300, 25);
/* 41 */     b1.setBounds(0, 60, 150, 30);
/* 42 */     b2.setBounds(150, 60, 150, 30);
/* 43 */     f.setVisible(true);
/* 44 */     f.setDefaultCloseOperation(3);
/*    */ 
/* 46 */     b1.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 48 */         String copy = Replacer.this.getClipboardContents();
/* 49 */         String str1 = tf1.getText();
/* 50 */         String str2 = tf2.getText();
/* 51 */         String p1 = copy.replace(str1, str2);
/* 52 */         String str3 = tf1.getText().toLowerCase();
/* 53 */         String str4 = tf2.getText().toLowerCase();
/* 54 */         String p2 = p1.replace(str3, str4);
/* 55 */         String str5 = tf1.getText().toUpperCase();
/* 56 */         String str6 = tf2.getText().toUpperCase();
/* 57 */         String paste = p2.replace(str5, str6);
/* 58 */         Replacer.this.setClipboardContents(paste);
/*    */       }
/*    */     });
/* 61 */     b2.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 63 */         tf1.setText("");
/* 64 */         tf2.setText("");
/*    */       } } );
/*    */   }
/*    */ 
/*    */   public String getClipboardContents() {
/* 69 */     String result = "";
/* 70 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/*    */ 
/* 72 */     Transferable contents = clipboard.getContents(null);
/* 73 */     boolean hasTransferableText = (contents != null) && (contents.isDataFlavorSupported(DataFlavor.stringFlavor));
/*    */ 
/* 75 */     if (hasTransferableText) {
/*    */       try {
/* 77 */         result = (String)contents.getTransferData(DataFlavor.stringFlavor);
/*    */       }
/*    */       catch (UnsupportedFlavorException ex) {
/* 80 */         System.out.println(ex);
/* 81 */         ex.printStackTrace();
/*    */       } catch (IOException ex) {
/* 83 */         System.out.println(ex);
/* 84 */         ex.printStackTrace();
/*    */       }
/*    */     }
/* 87 */     return result;
/*    */   }
/*    */ 
/*    */   public void setClipboardContents(String aString) {
/* 91 */     StringSelection stringSelection = new StringSelection(aString);
/* 92 */     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 93 */     clipboard.setContents(stringSelection, this);
/*    */   }
/*    */ 
/*    */   public void lostOwnership(Clipboard clipboard, Transferable contents)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Logan\Documents\City Gates Decompiles\CityGates (2).jar
 * Qualified Name:     citygates.Replacer
 * JD-Core Version:    0.6.2
 */