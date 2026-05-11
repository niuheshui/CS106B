package acm.io;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

class AWTIconCanvas extends Canvas {
   private Image myIcon;

   public AWTIconCanvas(Image var1) {
      this.myIcon = var1;
   }

   public Dimension getMinimumSize() {
      return new Dimension(48, 48);
   }

   public Dimension getPreferredSize() {
      return this.getMinimumSize();
   }

   public void paint(Graphics var1) {
      var1.drawImage(this.myIcon, 8, 8, this);
   }
}
