package acm.gui;

import acm.util.ErrorException;
import acm.util.OptionTable;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Map;

public class TableConstraints extends GridBagConstraints {
   public int width;
   public int height;
   private static final int MY_PAGE_START = 19;
   private static final int MY_PAGE_END = 20;
   private static final int MY_LINE_START = 21;
   private static final int MY_LINE_END = 22;
   private static final int MY_FIRST_LINE_START = 23;
   private static final int MY_FIRST_LINE_END = 24;
   private static final int MY_LAST_LINE_START = 25;
   private static final int MY_LAST_LINE_END = 26;
   protected static final String[] LEGAL_KEYS = new String[]{"anchor", "bottom", "colspan", "fill", "gridwidth", "gridheight", "gridx", "gridy", "height", "ipadx", "ipady", "left", "right", "rowspan", "top", "weightx", "weighty", "width"};
   static final long serialVersionUID = 1L;

   public TableConstraints() {
      this("");
   }

   public TableConstraints(String var1) {
      this((Map)(new OptionTable(var1.toLowerCase(), LEGAL_KEYS)).getMap());
   }

   public TableConstraints(Map<String, String> var1) {
      OptionTable var2 = new OptionTable(var1);
      this.gridx = this.parseXYConstraint(var2.getOption("gridx"));
      this.gridy = this.parseXYConstraint(var2.getOption("gridy"));
      String var3 = var2.getOption("gridwidth");
      if (var3 == null) {
         var3 = var2.getOption("rowspan");
      }

      String var4 = var2.getOption("gridheight");
      if (var4 == null) {
         var4 = var2.getOption("colspan");
      }

      this.gridwidth = this.parseSpanConstraint(var3);
      this.gridheight = this.parseSpanConstraint(var4);
      this.fill = this.parseFillConstraint(var2.getOption("fill"));
      this.anchor = this.parseAnchorConstraint(var2.getOption("anchor"));
      this.ipadx = var2.getIntOption("ipadx", 0);
      this.ipady = var2.getIntOption("ipady", 0);
      this.weightx = var2.getDoubleOption("weightx", (double)0.0F);
      this.weighty = var2.getDoubleOption("weighty", (double)0.0F);
      this.insets.left = var2.getIntOption("left", 0);
      this.insets.right = var2.getIntOption("right", 0);
      this.insets.top = var2.getIntOption("top", 0);
      this.insets.bottom = var2.getIntOption("bottom", 0);
      this.width = var2.getIntOption("width", -1);
      if (this.width == -1) {
         this.width = 0;
      } else {
         this.width += this.insets.left + this.insets.right;
      }

      this.height = var2.getIntOption("height", -1);
      if (this.height == -1) {
         this.height = 0;
      } else {
         this.height += this.insets.top + this.insets.bottom;
      }

      if (this.gridwidth != 1 && this.width != 0) {
         throw new ErrorException("TableConstraints: Cannot specify both width and gridwidth");
      } else if (this.gridheight != 1 && this.height != 0) {
         throw new ErrorException("TableConstraints: Cannot specify both height and gridheight");
      }
   }

   public TableConstraints(GridBagConstraints var1) {
      this.gridx = var1.gridx;
      this.gridy = var1.gridy;
      this.gridwidth = var1.gridwidth;
      this.gridheight = var1.gridheight;
      this.fill = var1.fill;
      this.anchor = var1.anchor;
      this.ipadx = var1.ipadx;
      this.ipady = var1.ipady;
      this.weightx = var1.weightx;
      this.weighty = var1.weighty;
      this.insets.left = var1.insets.left;
      this.insets.right = var1.insets.right;
      this.insets.top = var1.insets.top;
      this.insets.bottom = var1.insets.bottom;
      if (var1 instanceof TableConstraints) {
         TableConstraints var2 = (TableConstraints)var1;
         this.width = var2.width;
         this.height = var2.height;
      }

   }

   public int getAnchor() {
      return this.anchor;
   }

   public int getFill() {
      return this.fill;
   }

   public int getGridX() {
      return this.gridx;
   }

   public int getGridY() {
      return this.gridy;
   }

   public int getGridWidth() {
      return this.gridwidth;
   }

   public int getGridHeight() {
      return this.gridheight;
   }

   public int getIPadX() {
      return this.ipadx;
   }

   public int getIPadY() {
      return this.ipady;
   }

   public Insets getInsets() {
      return this.insets;
   }

   public double getWeightX() {
      return this.weightx;
   }

   public double getWeightY() {
      return this.weighty;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public String toString() {
      String var1 = this.getClass().getName();
      var1 = var1 + "[gridx=" + this.gridx + ",gridy=" + this.gridy;
      switch (this.fill) {
         case 1:
            var1 = var1 + ",fill=BOTH";
            break;
         case 2:
            var1 = var1 + ",fill=HORIZONTAL";
            break;
         case 3:
            var1 = var1 + ",fill=VERTICAL";
      }

      switch (this.anchor) {
         case 11:
            var1 = var1 + ",anchor=NORTH";
            break;
         case 12:
            var1 = var1 + ",anchor=NORTHEAST";
            break;
         case 13:
            var1 = var1 + ",anchor=EAST";
            break;
         case 14:
            var1 = var1 + ",anchor=SOUTHEAST";
            break;
         case 15:
            var1 = var1 + ",anchor=SOUTH";
            break;
         case 16:
            var1 = var1 + ",anchor=SOUTHWEST";
            break;
         case 17:
            var1 = var1 + ",anchor=WEST";
            break;
         case 18:
            var1 = var1 + ",anchor=NORTHWEST";
            break;
         case 19:
            var1 = var1 + ",anchor=PAGE_START";
            break;
         case 20:
            var1 = var1 + ",anchor=PAGE_END";
            break;
         case 21:
            var1 = var1 + ",anchor=LINE_START";
            break;
         case 22:
            var1 = var1 + ",anchor=LINE_END";
            break;
         case 23:
            var1 = var1 + ",anchor=FIRST_LINE_START";
            break;
         case 24:
            var1 = var1 + ",anchor=FIRST_LINE_END";
            break;
         case 25:
            var1 = var1 + ",anchor=LAST_LINE_START";
            break;
         case 26:
            var1 = var1 + ",anchor=LAST_LINE_END";
      }

      if (this.gridwidth != 1) {
         var1 = var1 + ",gridwidth=" + this.gridwidth;
      }

      if (this.gridheight != 1) {
         var1 = var1 + ",gridheight=" + this.gridheight;
      }

      if (this.ipadx != 0) {
         var1 = var1 + ",ipadx=" + this.ipadx;
      }

      if (this.ipady != 0) {
         var1 = var1 + ",ipady=" + this.ipady;
      }

      if (this.insets.left != 0) {
         var1 = var1 + ",left=" + this.insets.left;
      }

      if (this.insets.right != 0) {
         var1 = var1 + ",right=" + this.insets.right;
      }

      if (this.insets.top != 0) {
         var1 = var1 + ",top=" + this.insets.top;
      }

      if (this.insets.bottom != 0) {
         var1 = var1 + ",bottom=" + this.insets.bottom;
      }

      if (this.width != 0) {
         var1 = var1 + ",width=" + this.width;
      }

      if (this.height != 0) {
         var1 = var1 + ",height=" + this.height;
      }

      var1 = var1 + "]";
      return var1;
   }

   private int parseXYConstraint(String var1) {
      if (var1 == null) {
         return -1;
      } else if (var1.equals("relative")) {
         return -1;
      } else {
         try {
            return Integer.decode(var1);
         } catch (NumberFormatException var3) {
            throw new ErrorException("TableConstraints: Illegal grid coordinate");
         }
      }
   }

   private int parseSpanConstraint(String var1) {
      if (var1 == null) {
         return 1;
      } else if (var1.equals("relative")) {
         return -1;
      } else if (var1.equals("remainder")) {
         return 0;
      } else {
         try {
            return Integer.decode(var1);
         } catch (NumberFormatException var3) {
            throw new ErrorException("TableConstraints: Illegal span constraint");
         }
      }
   }

   private int parseAnchorConstraint(String var1) {
      if (var1 == null) {
         return 10;
      } else if (var1.equals("center")) {
         return 10;
      } else if (var1.equals("north")) {
         return 11;
      } else if (var1.equals("south")) {
         return 15;
      } else if (var1.equals("east")) {
         return 13;
      } else if (var1.equals("west")) {
         return 17;
      } else if (!var1.equals("northeast") && !var1.equals("ne")) {
         if (!var1.equals("northwest") && !var1.equals("nw")) {
            if (!var1.equals("southeast") && !var1.equals("se")) {
               if (!var1.equals("southwest") && !var1.equals("sw")) {
                  if (var1.equals("page_start")) {
                     return 19;
                  } else if (var1.equals("page_end")) {
                     return 20;
                  } else if (var1.equals("line_start")) {
                     return 21;
                  } else if (var1.equals("line_end")) {
                     return 22;
                  } else if (var1.equals("first_line_start")) {
                     return 23;
                  } else if (var1.equals("first_line_end")) {
                     return 24;
                  } else if (var1.equals("last_line_start")) {
                     return 25;
                  } else if (var1.equals("last_line_end")) {
                     return 26;
                  } else {
                     throw new ErrorException("TableConstraints: Illegal anchor specification");
                  }
               } else {
                  return 16;
               }
            } else {
               return 14;
            }
         } else {
            return 18;
         }
      } else {
         return 12;
      }
   }

   private int parseFillConstraint(String var1) {
      if (var1 != null && !var1.equals("none")) {
         if (var1.equals("horizontal")) {
            return 2;
         } else if (var1.equals("vertical")) {
            return 3;
         } else if (var1.equals("both")) {
            return 1;
         } else {
            throw new ErrorException("TableConstraints: Illegal fill specification");
         }
      } else {
         return 0;
      }
   }
}
