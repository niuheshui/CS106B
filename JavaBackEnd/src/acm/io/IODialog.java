// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IODialog.java

package acm.io;

import acm.util.*;
import java.awt.Component;
import java.lang.reflect.Constructor;
import javax.swing.JPanel;

// Referenced classes of package acm.io:
//            DialogModel, AWTDialogModel, IOModel, IOConsole

public class IODialog implements IOModel {
    private boolean exceptionOnError;
    private boolean allowCancel;
    private DialogModel model;
    private Component myComponent;
    private IOConsole myConsole;
    private String outputLine;

    public IODialog() {
        this((Component)null);
    }

    public IODialog(Component var1) {
        this.myComponent = var1;
        this.model = this.createModel();
        this.outputLine = "";
        this.exceptionOnError = false;
        this.allowCancel = false;
    }

    public void print(String var1) {
        this.outputLine = this.outputLine + var1;
    }

    public final void print(boolean var1) {
        this.print("" + var1);
    }

    public final void print(char var1) {
        this.print("" + var1);
    }

    public final void print(double var1) {
        this.print("" + var1);
    }

    public final void print(float var1) {
        this.print("" + var1);
    }

    public final void print(int var1) {
        this.print("" + var1);
    }

    public final void print(long var1) {
        this.print("" + var1);
    }

    public final void print(Object var1) {
        this.print("" + var1);
    }

    public void println() {
        this.model.popupMessage(this.outputLine);
        this.outputLine = "";
    }

    public void println(String var1) {
        this.print(var1);
        this.println();
    }

    public final void println(boolean var1) {
        this.println("" + var1);
    }

    public final void println(char var1) {
        this.println("" + var1);
    }

    public final void println(double var1) {
        this.println("" + var1);
    }

    public final void println(float var1) {
        this.println("" + var1);
    }

    public final void println(int var1) {
        this.println("" + var1);
    }

    public final void println(long var1) {
        this.println("" + var1);
    }

    public final void println(Object var1) {
        this.println("" + var1);
    }

    public void showErrorMessage(String var1) {
        this.model.popupErrorMessage(var1);
    }

    public final String readLine() {
        return this.readLine((String)null);
    }

    public String readLine(String var1) {
        if (this.myConsole != null && this.myConsole.getInputScript() != null) {
            return this.myConsole.readLine(var1);
        } else {
            var1 = var1 == null ? this.outputLine : this.outputLine + var1;
            this.outputLine = "";

            String var2;
            while((var2 = this.model.popupLineInputDialog(var1, this.allowCancel)) == null) {
                if (this.allowCancel) {
                    throw new CancelledException();
                }
            }

            return var2;
        }
    }

    public final int readInt() {
        return this.readInt((String)null, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public final int readInt(int var1, int var2) {
        return this.readInt((String)null, var1, var2);
    }

    public final int readInt(String var1) {
        return this.readInt(var1, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int readInt(String var1, int var2, int var3) {
        while(true) {
            String var4 = this.readLine(var1);

            try {
                int var5 = Integer.parseInt(var4);
                if (var5 < var2 || var5 > var3) {
                    this.signalError("Value is outside the range [" + var2 + ":" + var3 + "]");
                }

                return var5;
            } catch (NumberFormatException var6) {
                this.signalError("Illegal integer format");
            }
        }
    }

    public final double readDouble() {
        return this.readDouble((String)null, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public final double readDouble(double var1, double var3) {
        return this.readDouble((String)null, var1, var3);
    }

    public final double readDouble(String var1) {
        return this.readDouble(var1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public double readDouble(String var1, double var2, double var4) {
        while(true) {
            String var6 = this.readLine(var1);

            try {
                double var7 = Double.valueOf(var6);
                if (var7 < var2 || var7 > var4) {
                    this.signalError("Value is outside the range [" + var2 + ":" + var4 + "]");
                }

                return var7;
            } catch (NumberFormatException var9) {
                this.signalError("Illegal numeric format");
            }
        }
    }

    public final boolean readBoolean() {
        return this.readBoolean((String)null);
    }

    public final boolean readBoolean(String var1) {
        return this.readBoolean(var1, "true", "false");
    }

    public boolean readBoolean(String var1, String var2, String var3) {
        if (this.myConsole != null && this.myConsole.getInputScript() != null) {
            return this.myConsole.readBoolean(var1, var2, var3);
        } else {
            var1 = var1 == null ? this.outputLine : this.outputLine + var1;
            this.outputLine = "";

            Boolean var4;
            while((var4 = this.model.popupBooleanInputDialog(var1, var2, var3, this.allowCancel)) == null) {
                if (this.allowCancel) {
                    throw new CancelledException();
                }
            }

            return var4;
        }
    }

    public void setExceptionOnError(boolean var1) {
        this.exceptionOnError = var1;
    }

    public boolean getExceptionOnError() {
        return this.exceptionOnError;
    }

    public void setAllowCancel(boolean var1) {
        this.allowCancel = var1;
    }

    public boolean getAllowCancel() {
        return this.allowCancel;
    }

    public void setAssociatedConsole(IOConsole var1) {
        this.myConsole = var1;
    }

    public IOConsole getAssociatedConsole() {
        return this.myConsole;
    }

    protected DialogModel createModel() {
        String var1 = (new JPanel()).getClass().getName();
        if (var1.startsWith("javax.swing.") && Platform.isSwingAvailable()) {
            try {
                Class var2 = Class.forName("acm.io.SwingDialogModel");
                Class[] var3 = new Class[]{Class.forName("java.awt.Component")};
                Object[] var4 = new Object[]{this.myComponent};
                Constructor var5 = var2.getConstructor(var3);
                return (DialogModel)var5.newInstance(var4);
            } catch (Exception var6) {
                return new AWTDialogModel(this.myComponent);
            }
        } else {
            return new AWTDialogModel(this.myComponent);
        }
    }

    private void signalError(String var1) {
        if (this.exceptionOnError) {
            throw new ErrorException(var1);
        } else {
            this.model.popupErrorMessage(var1);
        }
    }
}
