package acm.io;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;

class AWTMessageCanvas extends Canvas {
   public static final int MARGIN = 8;
   public static final Font MESSAGE_FONT = new Font("Dialog", 0, 12);
   private String message;

   public AWTMessageCanvas() {
      this.setFont(MESSAGE_FONT);
   }

   public void setMessage(String var1) {
      this.message = var1;
   }

   public void paint(Graphics var1) {
      FontMetrics var2 = var1.getFontMetrics();
      int var3 = 8;
      int var4 = 8 + var2.getAscent();
      int var5 = this.getSize().width - 8;
      StringTokenizer var6 = new StringTokenizer(this.message, " ", true);

      while(var6.hasMoreTokens()) {
         String var7 = var6.nextToken();
         int var8 = var2.stringWidth(var7);
         if (var3 + var8 > var5) {
            var3 = 8;
            var4 += var2.getHeight();
            if (var7.equals(" ")) {
               continue;
            }
         }

         var1.drawString(var7, var3, var4);
         var3 += var8;
      }

   }
}
