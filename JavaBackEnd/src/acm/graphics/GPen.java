package acm.graphics;

import acm.util.Animator;
import acm.util.ErrorException;
import acm.util.MediaTools;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class GPen extends GObject {
   private Animator animator;
   private boolean regionOpen;
   private boolean regionStarted;
   private boolean penVisible;
   private PathList path;
   private Image penImage;
   private Color fillColor;
   static final long serialVersionUID = 21L;

   public GPen() {
      this.penVisible = false;
      this.path = new PathList();
      this.animator = new Animator();
      this.setSpeed((double)1.0F);
      this.erasePath();
   }

   public GPen(double var1, double var3) {
      this();
      this.setLocation(var1, var3);
   }

   public void erasePath() {
      this.path.clear();
      this.regionOpen = false;
      this.regionStarted = false;
      this.repaint();
   }

   public void setLocation(double var1, double var3) {
      if (this.regionStarted) {
         throw new ErrorException("It is illegal to move the pen while you are defining a filled region.");
      } else {
         super.setLocation(var1, var3);
         this.animator.delay();
      }
   }

   public void drawLine(double var1, double var3) {
      double var5 = this.getX();
      double var7 = this.getY();
      if (this.regionStarted) {
         this.path.add(new DrawLineElement(var1, var3));
      } else {
         this.path.add(new SetLocationElement(var5, var7), new DrawLineElement(var1, var3));
         this.regionStarted = this.regionOpen;
      }

      super.setLocation(var5 + var1, var7 + var3);
      this.animator.delay();
   }

   public final void drawPolarLine(double var1, double var3) {
      double var5 = var3 * Math.PI / (double)180.0F;
      this.drawLine(var1 * Math.cos(var5), -var1 * Math.sin(var5));
   }

   public void setColor(Color var1) {
      if (this.regionStarted) {
         throw new ErrorException("It is illegal to change the color while you are defining a filled region.");
      } else {
         this.path.add(new SetColorElement(var1));
         super.setColor(var1);
      }
   }

   public void setFillColor(Color var1) {
      if (this.regionStarted) {
         throw new ErrorException("It is illegal to change the fill color while you are defining a filled region.");
      } else {
         this.fillColor = var1;
      }
   }

   public Color getFillColor() {
      return this.fillColor == null ? this.getColor() : this.fillColor;
   }

   public void startFilledRegion() {
      if (this.regionOpen) {
         throw new ErrorException("You are already filling a region.");
      } else {
         this.regionOpen = true;
         this.regionStarted = false;
         this.path.add(new StartRegionElement(this.fillColor));
      }
   }

   public void endFilledRegion() {
      if (!this.regionOpen) {
         throw new ErrorException("You need to call startFilledRegion before you call endFilledRegion.");
      } else {
         this.regionOpen = false;
         this.regionStarted = false;
         this.path.add(new EndRegionElement());
         this.repaint();
      }
   }

   public void showPen() {
      this.penVisible = true;
      this.repaint();
      this.animator.delay();
   }

   public void hidePen() {
      this.penVisible = false;
      this.repaint();
      this.animator.delay();
   }

   public boolean isPenVisible() {
      return this.penVisible;
   }

   public void setSpeed(double var1) {
      this.animator.setSpeed(var1);
   }

   public double getSpeed() {
      return this.animator.getSpeed();
   }

   protected void paint2d(Graphics2D var1) {
      var1 = (Graphics2D)var1.create();
      Color var2 = this.getObjectColor();
      if (var2 != null) {
         var1.setColor(var2);
      }

      PathState var3 = new PathState();
      this.path.mapPaint(var1, var3);
      if (this.penVisible) {
         this.drawPen(var1);
      }

   }

   public GRectangle getBounds() {
      PathState var1 = new PathState();
      return this.path.getBounds(var1);
   }

   public boolean contains(double var1, double var3) {
      return false;
   }

   public void setPenImage(Image var1) {
      this.penImage = MediaTools.loadImage(var1);
   }

   public Image getPenImage() {
      if (this.penImage == null) {
         this.penImage = PenImage.getImage();
      }

      return this.penImage;
   }

   protected void drawPen(Graphics var1) {
      Component var2 = this.getComponent();
      if (var2 != null) {
         if (this.penImage == null) {
            this.penImage = PenImage.getImage();
         }

         int var3 = this.penImage.getWidth(var2);
         int var4 = this.penImage.getHeight(var2);
         int var5 = (int)Math.round(this.getX());
         int var6 = (int)Math.round(this.getY());
         var1.drawImage(this.penImage, var5 - var3 / 2, var6 - var4 / 2, var2);
      }
   }

   protected Rectangle getPenBounds() {
      Component var1 = this.getComponent();
      if (var1 == null) {
         return new Rectangle();
      } else {
         if (this.penImage == null) {
            this.penImage = PenImage.getImage();
         }

         int var2 = this.penImage.getWidth(var1);
         int var3 = this.penImage.getHeight(var1);
         int var4 = (int)Math.round(this.getX());
         int var5 = (int)Math.round(this.getY());
         return new Rectangle(var4 - var2 / 2, var5 - var3 / 2, var2, var3);
      }
   }
}
