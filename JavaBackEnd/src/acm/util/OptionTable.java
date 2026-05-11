package acm.util;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class OptionTable {
   private HashMap<String, String> optionTable;
   static final long serialVersionUID = 1L;

   public OptionTable(String var1) {
      this(var1, (String[])null);
   }

   public OptionTable(String var1, String[] var2) {
      this.optionTable = new HashMap();

      try {
         StreamTokenizer var3 = this.createTokenizer(var1);
         int var4 = var3.nextToken();

         while(var4 != -1) {
            if (var4 != -3) {
               throw new ErrorException("Illegal option string: " + var1);
            }

            String var5 = var3.sval;
            if (var2 != null && !this.keyExists(var5, var2)) {
               throw new ErrorException("Unrecognized option: " + var5);
            }

            var4 = var3.nextToken();
            if (var4 == 61) {
               var4 = var3.nextToken();
               if (var4 != -3 && var4 != 34 && var4 != 39) {
                  throw new ErrorException("Illegal option string: " + var1);
               }

               this.optionTable.put(var5, var3.sval);
               var4 = var3.nextToken();
            } else {
               this.optionTable.put(var5, "");
            }
         }

      } catch (IOException var6) {
         throw new ErrorException("Illegal option string: " + var1);
      }
   }

   public OptionTable(Map<String, String> var1) {
      this.optionTable = new HashMap();

      for(String var3 : var1.keySet()) {
         String var4 = (String)var1.get(var3);
         this.optionTable.put(var3, var4);
      }

   }

   public boolean isSpecified(String var1) {
      return this.optionTable.containsKey(var1);
   }

   public String getOption(String var1) {
      return this.getOption(var1, (String)null);
   }

   public String getOption(String var1, String var2) {
      String var3 = (String)this.optionTable.get(var1);
      return var3 != null && !var3.equals("") ? var3 : var2;
   }

   public int getIntOption(String var1) {
      return this.getIntOption(var1, 0);
   }

   public int getIntOption(String var1, int var2) {
      String var3 = this.getOption(var1, (String)null);
      return var3 != null && !var3.equals("") ? Integer.decode(var3) : var2;
   }

   public double getDoubleOption(String var1) {
      return this.getDoubleOption(var1, (double)0.0F);
   }

   public double getDoubleOption(String var1, double var2) {
      String var4 = this.getOption(var1, (String)null);
      return var4 != null && !var4.equals("") ? Double.valueOf(var4) : var2;
   }

   public HashMap<String, String> getMap() {
      return this.optionTable;
   }

   private StreamTokenizer createTokenizer(String var1) {
      StreamTokenizer var2 = new StreamTokenizer(new StringReader(var1));
      var2.resetSyntax();
      var2.wordChars(33, 60);
      var2.wordChars(62, 126);
      var2.quoteChar(34);
      var2.quoteChar(39);
      var2.whitespaceChars(32, 32);
      var2.whitespaceChars(9, 9);
      return var2;
   }

   private boolean keyExists(String var1, String[] var2) {
      for(int var3 = 0; var3 < var2.length; ++var3) {
         if (var1.equals(var2[var3])) {
            return true;
         }
      }

      return false;
   }
}
