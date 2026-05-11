// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JTFTools.java

package acm.util;

import acm.program.Program;
import jdk.jfr.internal.JVM;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.*;
import java.util.zip.*;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;

// Referenced classes of package acm.util:
//            EmptyContainer, ErrorException, FileChooserFilter, MailStream, 
//            HexByteOutputStream, Base64OutputStream, ThreadedMenuAction, ExportAppletDialog, 
//            NullOutputStream, SubmitOptions, Platform, DOSCommandLine, 
//            ProgressBarDialog

public class JTFTools {
    private static final String[] SKIP_FILES = new String[]{".DS_Store", "FINDER.DAT", "RESOURCE.FRK"};
    private static final String[] RESOURCE_EXTENSIONS = new String[]{".txt", ".dat", ".gif", ".jpg", ".jpeg", ".png", ".au", ".wav", ".class"};
    private static final String[] SUBMIT_EXTENSIONS = new String[]{".java", ".html", ".txt", ".dat", ".gif", ".jpg", ".jpeg", ".png", ".au", ".wav"};
    private static HashMap<Component, SubmitOptions> optionsTable = new HashMap();
    private static final int BUFFER_SIZE = 4096;
    private static final String[] SERIF_SUBSTITUTIONS = new String[]{"Serif", "Times", "TimesRoman", "Times-Roman"};
    private static final String[] SANSSERIF_SUBSTITUTIONS = new String[]{"SansSerif", "Helvetica", "Arial"};
    private static final String[] MONOSPACED_SUBSTITUTIONS = new String[]{"Monospaced", "Courier", "Monaco"};
    private static final String[] SKIP_JARS = new String[]{"acm.jar", "acm11.jar", "swingall.jar", "patchJTF.jar"};
    private static boolean fontFamilyTableInitialized = false;
    private static String[] fontFamilyArray = null;
    private static HashMap<String, String> fontFamilyTable = null;
    private static HashMap<Thread, Applet> appletTable = new HashMap();
    private static Applet mostRecentApplet = null;
    private static SecurityManager managerThatFails = null;
    private static String debugOptions = null;

    private JTFTools() {
    }

    public static void pause(double var0) {
        Applet var2 = (Applet)appletTable.get(Thread.currentThread());
        if (var2 == null) {
            var2 = mostRecentApplet;
            appletTable.put(Thread.currentThread(), var2);
        }

        try {
            int var3 = (int)var0;
            int var4 = (int)Math.round((var0 - (double)var3) * (double)1000000.0F);
            Thread.sleep((long)var3, var4);
        } catch (InterruptedException var5) {
        }

    }

    public static String toHexString(int var0) {
        return Integer.toHexString(var0).toUpperCase();
    }

    public static String toHexString(int var0, int var1) {
        String var2;
        for(var2 = toHexString(var0); var2.length() < var1; var2 = "0" + var2) {
        }

        return var2;
    }

    public static Container createEmptyContainer() {
        return new EmptyContainer();
    }

    public static Frame getEnclosingFrame(Component var0) {
        if (var0 instanceof Program) {
            var0 = ((Program)var0).getContentPane();
        }

        while(var0 != null && !(var0 instanceof Frame)) {
            var0 = ((Component)var0).getParent();
        }

        return (Frame)var0;
    }

    public static Font getStandardFont(Font var0) {
        if (!fontFamilyTableInitialized) {
            initFontFamilyTable();
        }

        if (var0 != null && fontFamilyTable != null) {
            String var1 = var0.getFamily();
            if (fontFamilyTable.get(trimFamilyName(var1)) != null) {
                return var0;
            } else {
                if (!var1.equals("Serif") && !var1.equals("Times")) {
                    if (var1.equals("SansSerif")) {
                        var1 = getFirstAvailableFontSubstitution(SANSSERIF_SUBSTITUTIONS);
                    } else {
                        if (!var1.equals("Monospaced")) {
                            return var0;
                        }

                        var1 = getFirstAvailableFontSubstitution(MONOSPACED_SUBSTITUTIONS);
                    }
                } else {
                    var1 = getFirstAvailableFontSubstitution(SERIF_SUBSTITUTIONS);
                }

                return var1 == null ? var0 : new Font(var1, var0.getStyle(), var0.getSize());
            }
        } else {
            return var0;
        }
    }

    public static String[] getFontList() {
        if (!fontFamilyTableInitialized) {
            initFontFamilyTable();
        }

        return fontFamilyArray;
    }

    public static String findFontFamily(String var0) {
        if (!fontFamilyTableInitialized) {
            initFontFamilyTable();
        }

        StringTokenizer var1 = new StringTokenizer(var0, ";", false);

        while(var1.hasMoreTokens()) {
            String var2 = (String)fontFamilyTable.get(trimFamilyName(var1.nextToken()));
            if (var2 != null) {
                return var2;
            }
        }

        return null;
    }

    public static Font decodeFont(String var0) {
        return decodeFont(var0, (Font)null);
    }

    public static Font decodeFont(String var0, Font var1) {
        String var2 = var0;
        int var3 = var1 == null ? 0 : var1.getStyle();
        int var4 = var1 == null ? 12 : var1.getSize();
        int var5 = var0.indexOf(45);
        if (var5 >= 0) {
            var2 = var0.substring(0, var5);
            var0 = var0.substring(var5 + 1).toLowerCase();
            String var6 = var0;
            var5 = var0.indexOf(45);
            if (var5 >= 0) {
                var6 = var0.substring(0, var5);
                var0 = var0.substring(var5 + 1);
            } else {
                var0 = "*";
            }

            if (Character.isDigit(var6.charAt(0))) {
                String var7 = var6;
                var6 = var0;
                var0 = var7;
            }

            if (var6.equals("plain")) {
                var3 = 0;
            } else if (var6.equals("bold")) {
                var3 = 1;
            } else if (var6.equals("italic")) {
                var3 = 2;
            } else if (var6.equals("bolditalic")) {
                var3 = 3;
            } else if (!var6.equals("*")) {
                throw new ErrorException("Illegal font style");
            }

            if (!var0.equals("*")) {
                try {
                    var4 = Integer.valueOf(var0);
                } catch (NumberFormatException var8) {
                    throw new ErrorException("Illegal font size");
                }
            }
        }

        if (var2.equals("*")) {
            var2 = var1 == null ? "Default" : var1.getName();
        } else {
            if (!fontFamilyTableInitialized) {
                initFontFamilyTable();
            }

            if (fontFamilyTable != null) {
                var2 = (String)fontFamilyTable.get(trimFamilyName(var2));
                if (var2 == null) {
                    var2 = "Default";
                }
            }
        }

        return getStandardFont(new Font(var2, var3, var4));
    }

    public static Color decodeColor(String var0) {
        if (var0.equalsIgnoreCase("desktop")) {
            return SystemColor.desktop;
        } else if (var0.equalsIgnoreCase("activeCaption")) {
            return SystemColor.activeCaption;
        } else if (var0.equalsIgnoreCase("activeCaptionText")) {
            return SystemColor.activeCaptionText;
        } else if (var0.equalsIgnoreCase("activeCaptionBorder")) {
            return SystemColor.activeCaptionBorder;
        } else if (var0.equalsIgnoreCase("inactiveCaption")) {
            return SystemColor.inactiveCaption;
        } else if (var0.equalsIgnoreCase("inactiveCaptionText")) {
            return SystemColor.inactiveCaptionText;
        } else if (var0.equalsIgnoreCase("inactiveCaptionBorder")) {
            return SystemColor.inactiveCaptionBorder;
        } else if (var0.equalsIgnoreCase("window")) {
            return SystemColor.window;
        } else if (var0.equalsIgnoreCase("windowBorder")) {
            return SystemColor.windowBorder;
        } else if (var0.equalsIgnoreCase("windowText")) {
            return SystemColor.windowText;
        } else if (var0.equalsIgnoreCase("menu")) {
            return SystemColor.menu;
        } else if (var0.equalsIgnoreCase("menuText")) {
            return SystemColor.menuText;
        } else if (var0.equalsIgnoreCase("text")) {
            return SystemColor.text;
        } else if (var0.equalsIgnoreCase("textText")) {
            return SystemColor.textText;
        } else if (var0.equalsIgnoreCase("textHighlight")) {
            return SystemColor.textHighlight;
        } else if (var0.equalsIgnoreCase("textHighlightText")) {
            return SystemColor.textHighlightText;
        } else if (var0.equalsIgnoreCase("textInactiveText")) {
            return SystemColor.textInactiveText;
        } else if (var0.equalsIgnoreCase("control")) {
            return SystemColor.control;
        } else if (var0.equalsIgnoreCase("controlText")) {
            return SystemColor.controlText;
        } else if (var0.equalsIgnoreCase("controlHighlight")) {
            return SystemColor.controlHighlight;
        } else if (var0.equalsIgnoreCase("controlLtHighlight")) {
            return SystemColor.controlLtHighlight;
        } else if (var0.equalsIgnoreCase("controlShadow")) {
            return SystemColor.controlShadow;
        } else if (var0.equalsIgnoreCase("controlDkShadow")) {
            return SystemColor.controlDkShadow;
        } else if (var0.equalsIgnoreCase("scrollbar")) {
            return SystemColor.scrollbar;
        } else if (var0.equalsIgnoreCase("info")) {
            return SystemColor.info;
        } else if (var0.equalsIgnoreCase("infoText")) {
            return SystemColor.infoText;
        } else if (var0.equalsIgnoreCase("black")) {
            return Color.BLACK;
        } else if (var0.equalsIgnoreCase("blue")) {
            return Color.BLUE;
        } else if (var0.equalsIgnoreCase("cyan")) {
            return Color.CYAN;
        } else if (var0.equalsIgnoreCase("darkGray")) {
            return Color.DARK_GRAY;
        } else if (var0.equalsIgnoreCase("DARK_GRAY")) {
            return Color.DARK_GRAY;
        } else if (var0.equalsIgnoreCase("gray")) {
            return Color.GRAY;
        } else if (var0.equalsIgnoreCase("green")) {
            return Color.GREEN;
        } else if (var0.equalsIgnoreCase("lightGray")) {
            return Color.LIGHT_GRAY;
        } else if (var0.equalsIgnoreCase("LIGHT_GRAY")) {
            return Color.LIGHT_GRAY;
        } else if (var0.equalsIgnoreCase("magenta")) {
            return Color.MAGENTA;
        } else if (var0.equalsIgnoreCase("orange")) {
            return Color.ORANGE;
        } else if (var0.equalsIgnoreCase("pink")) {
            return Color.PINK;
        } else if (var0.equalsIgnoreCase("red")) {
            return Color.RED;
        } else if (var0.equalsIgnoreCase("white")) {
            return Color.WHITE;
        } else if (var0.equalsIgnoreCase("yellow")) {
            return Color.YELLOW;
        } else {
            try {
                if (var0.startsWith("0x")) {
                    var0 = var0.substring(2);
                } else if (var0.startsWith("#")) {
                    var0 = var0.substring(1);
                }

                int var1 = var0.length();
                int var2 = 255;
                if (var1 == 8) {
                    var2 = Integer.parseInt(var0.substring(0, 2), 16);
                    var0 = var0.substring(2);
                } else if (var1 != 6) {
                    throw new ErrorException("decodeColor: Colors must have 6 or 8 hexadecimal digits");
                }

                return new Color(Integer.parseInt(var0, 16) | var2 << 24, true);
            } catch (NumberFormatException var3) {
                throw new ErrorException("decodeColor: Illegal color value");
            }
        }
    }

    public static String showOpenDialog(String var0, String var1) {
        FileChooserFilter var2 = new FileChooserFilter(var1);
        JFileChooser var3 = new JFileChooser(var2.getDirectory());
        var3.setFileFilter(var2);
        var3.setDialogTitle(var0);
        return var3.showOpenDialog((Component)null) == 0 ? var3.getSelectedFile().getAbsolutePath() : "";
    }

    public static String showSaveDialog(String var0, String var1) {
        FileChooserFilter var2 = new FileChooserFilter(var1);
        String var3 = var2.getDirectory();
        JFileChooser var4 = new JFileChooser(var3);
        var4.setSelectedFile(new File(var3, var2.getPattern()));
        var4.setDialogTitle(var0);
        return var4.showSaveDialog((Component)null) == 0 ? var4.getSelectedFile().getAbsolutePath() : "";
    }

    public static boolean matchFilenamePattern(String var0, String var1) {
        return recursiveMatch(var0, 0, var1, 0);
    }

    public static void registerApplet(Applet var0) {
        registerApplet(var0, Thread.currentThread());
        mostRecentApplet = var0;
    }

    public static void registerApplet(Applet var0, Thread var1) {
        appletTable.put(var1, var0);
    }

    public static Applet getApplet() {
        Applet var0 = (Applet)appletTable.get(Thread.currentThread());
        if (var0 == null) {
            var0 = mostRecentApplet;
        }

        return var0;
    }

    public static void setDebugOptions(String var0) {
        debugOptions = var0 == null ? null : "+" + var0.toLowerCase() + "+";
    }

    public static boolean testDebugOption(String var0) {
        if (debugOptions == null) {
            return false;
        } else {
            return debugOptions.indexOf("+" + var0.toLowerCase() + "+") >= 0;
        }
    }

    public static String getCommandLine() {
        switch (Platform.getPlatform()) {
            case 1:
            case 2:
                return getShellCommandLine();
            case 3:
                return DOSCommandLine.getCommandLine();
            default:
                return getShellCommandLine();
        }
    }

    public static String getMainClass() {
        String var0 = null;

        try {
            var0 = System.getProperty("java.main");
        } catch (Exception var2) {
        }

        if (var0 == null) {
            var0 = readMainClassFromClassPath();
        }

        if (var0 == null) {
            String var1 = getCommandLine();
            var0 = readMainClassFromCommandLine(var1);
        }

        return var0;
    }

    public static boolean checkIfLoaded(String var0) {
        if (Platform.compareVersion("1.2") < 0) {
            return false;
        } else {
            boolean var1 = false;

            try {
                if (System.getSecurityManager() != null) {
                    return false;
                }

                if (managerThatFails == null) {
                    try {
                        Class var2 = Class.forName("acm.util.SecurityManagerThatFails");
                        managerThatFails = (SecurityManager)var2.newInstance();
                    } catch (Exception var12) {
                        return false;
                    }
                }

                System.setSecurityManager(managerThatFails);

                try {
                    var1 = Class.forName(var0) != null;
                } catch (ExceptionInInitializerError var9) {
                    var1 = true;
                } catch (NoClassDefFoundError var10) {
                } finally {
                    System.setSecurityManager((SecurityManager)null);
                }
            } catch (Exception var13) {
            }

            return var1;
        }
    }

    public static void terminateAppletThreads(Applet var0) {
        try {
            Thread var1 = Thread.currentThread();
            Class var2 = Class.forName("java.lang.Thread");
            Method var3 = var2.getMethod("stop");

            for(Thread var5 : appletTable.keySet()) {
                if (var5 != var1 && var5.isAlive() && isAnonymous(var5) && var0 == appletTable.get(var5)) {
                    var3.invoke(var5);
                }
            }
        } catch (Exception var6) {
        }

    }

    public static boolean isAnonymous(Thread var0) {
        String var1 = var0.getName();
        if (!var1.startsWith("Thread-")) {
            return false;
        } else {
            for(int var2 = 7; var2 < var1.length(); ++var2) {
                if (!Character.isDigit(var1.charAt(var2))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static PrintStream openMailStream(String var0, String var1, String var2) {
        return new MailStream(var0, var1, var2);
    }

    public static void cancelMail(PrintStream var0) {
        ((MailStream)var0).cancel();
    }

    public static void sendStandardHeaders(PrintStream var0, String var1, String var2) {
        ((MailStream)var0).sendStandardHeaders(var1, var2);
    }

    public static OutputStream openHexByteOutputStream(PrintStream var0) {
        return new HexByteOutputStream(var0);
    }

    public static OutputStream openBase64OutputStream(PrintStream var0) {
        return new Base64OutputStream(var0);
    }

    public static void padBase64OutputStream(OutputStream var0) {
        ((Base64OutputStream)var0).pad();
    }

    public static void exportJar(File var0, File var1, String var2, Object var3) {
        try {
            ZipOutputStream var4 = new ZipOutputStream(new FileOutputStream(var0));
            dumpJarAndResources("", var1, var4, (File)null, var2, (JProgressBar)null, false, var3);
            var4.close();
        } catch (IOException var5) {
            throw new ErrorException(var5);
        }
    }

    public static boolean executeExportAction(Program var0, String var1) {
        if (!var1.equals("Export Applet") && !var1.equals("Submit Project")) {
            return false;
        } else {
            (new Thread(new ThreadedMenuAction(var0, var1))).start();
            return true;
        }
    }

    public static String getLocalHostName() {
        try {
            InetAddress var0 = InetAddress.getLocalHost();
            Class var1 = var0.getClass();
            Method var2 = var1.getMethod("getCanonicalHostName");
            return (String)var2.invoke(var0);
        } catch (Exception var3) {
            return null;
        }
    }

    public static void copyFile(File var0, File var1) {
        try {
            BufferedInputStream var2 = new BufferedInputStream(new FileInputStream(var0));
            BufferedOutputStream var3 = new BufferedOutputStream(new FileOutputStream(var1));
            copyBytes(var2, var3, var0.length());
            var2.close();
            var3.close();
            Platform.copyFileTypeAndCreator(var0, var1);
        } catch (IOException var4) {
            throw new ErrorException(var4);
        }
    }

    public static void copyBytes(InputStream var0, OutputStream var1, long var2) throws IOException {
        int var6;
        for(byte[] var4 = new byte[4096]; var2 > 0L; var2 -= (long)var6) {
            var6 = (int)Math.min(4096L, var2);
            var6 = var0.read(var4, 0, var6);
            if (var6 == -1) {
                return;
            }

            var1.write(var4, 0, var6);
        }

    }

    static String getURLSuffix(String var0) {
        return var0.substring(var0.lastIndexOf(47) + 1);
    }

    static void exportApplet(Program var0, JProgressBar var1) {
        try {
            String var2 = var0.getClass().getName();
            String var3 = var2.substring(var2.lastIndexOf(".") + 1);
            File var4 = new File(System.getProperty("user.dir"));
            File var5 = new File(System.getProperty("user.home"));
            ExportAppletDialog var6 = new ExportAppletDialog(var5, var0);
            File var7 = var6.chooseOutputDirectory();
            if (var7 != null) {
                if (var7.exists()) {
                    if (!var7.isDirectory()) {
                        var7 = new File(var7.getParent());
                    }
                } else {
                    var7.mkdir();
                }

                if (var1 != null) {
                    var1.setMaximum(countResources(var4, RESOURCE_EXTENSIONS, "acm.jar") + 1);
                    ProgressBarDialog.popup(var1);
                }

                File var8 = new File(var4, "index.html");
                if (var8.canRead()) {
                    copyFile(var8, new File(var7, "index.html"));
                } else {
                    dumpHTMLIndex(var7, var0, var2, var3);
                }

                if (var1 != null) {
                    if (ProgressBarDialog.hasBeenCancelled(var1)) {
                        return;
                    }

                    var1.setValue(var1.getValue() + 1);
                }

                dumpJarAndResources(var4, var7, var3 + ".jar", "acm.jar", var1, var6.exportFiles(), (Object)null);
                if (var1 != null) {
                    ProgressBarDialog.dismiss(var1);
                }

            }
        } catch (IOException var9) {
            throw new ErrorException(var9);
        }
    }

    private static void initFontFamilyTable() {
        fontFamilyTableInitialized = true;

        for(int var0 = 1; fontFamilyArray == null && var0 <= 2; ++var0) {
            try {
                if (var0 == 1) {
                    Class var1 = Class.forName("java.awt.GraphicsEnvironment");
                    Method var2 = var1.getMethod("getLocalGraphicsEnvironment");
                    Method var3 = var1.getMethod("getAvailableFontFamilyNames");
                    Object var4 = var2.invoke((Object)null);
                    fontFamilyArray = (String[])var3.invoke(var4);
                } else {
                    Class var7 = Class.forName("java.awt.Toolkit");
                    Method var8 = var7.getMethod("getFontList");
                    fontFamilyArray = (String[])var8.invoke(Toolkit.getDefaultToolkit());
                }
            } catch (Exception var5) {
            }
        }

        fontFamilyTable = new HashMap();

        for(int var6 = 0; var6 < fontFamilyArray.length; ++var6) {
            fontFamilyTable.put(trimFamilyName(fontFamilyArray[var6]), fontFamilyArray[var6]);
        }

        fontFamilyTable.put("serif", getFirstAvailableFontSubstitution(SERIF_SUBSTITUTIONS));
        fontFamilyTable.put("sansserif", getFirstAvailableFontSubstitution(SANSSERIF_SUBSTITUTIONS));
        fontFamilyTable.put("monospaced", getFirstAvailableFontSubstitution(MONOSPACED_SUBSTITUTIONS));
    }

    private static String getFirstAvailableFontSubstitution(String[] var0) {
        for(int var1 = 0; var1 < var0.length; ++var1) {
            if (fontFamilyTable.get(trimFamilyName(var0[var1])) != null) {
                return var0[var1];
            }
        }

        return null;
    }

    private static String trimFamilyName(String var0) {
        String var1 = "";

        for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var3 = var0.charAt(var2);
            if (var3 != ' ' && var3 != '-') {
                var1 = var1 + Character.toLowerCase(var3);
            }
        }

        return var1;
    }

    private static boolean recursiveMatch(String var0, int var1, String var2, int var3) {
        int var4 = var0.length();
        int var5 = var2.length();
        if (var3 == var5) {
            return var1 == var4;
        } else {
            char var6 = var2.charAt(var3);
            if (var6 == '*') {
                for(int var10 = var1; var10 <= var4; ++var10) {
                    if (recursiveMatch(var0, var10, var2, var3 + 1)) {
                        return true;
                    }
                }

                return false;
            } else if (var1 == var4) {
                return false;
            } else {
                char var7 = var0.charAt(var1);
                if (var6 == '[') {
                    boolean var8 = false;
                    boolean var9 = false;
                    ++var3;
                    if (var3 == var5) {
                        throw new ErrorException("matchFilenamePattern: missing ]");
                    }

                    if (var2.charAt(var3) == '^') {
                        ++var3;
                        var9 = true;
                    }

                    while(var3 < var5 && var2.charAt(var3) != ']') {
                        if (var3 + 2 < var5 && var2.charAt(var3 + 1) == '-') {
                            var8 |= var7 >= var2.charAt(var3) && var7 <= var2.charAt(var3 + 2);
                            var3 += 3;
                        } else {
                            var8 |= var7 == var2.charAt(var3);
                            ++var3;
                        }
                    }

                    if (var3 == var5) {
                        throw new ErrorException("matchFilenamePattern: missing ]");
                    }

                    if (var8 == var9) {
                        return false;
                    }
                } else if (var6 != '?' && var6 != var7) {
                    return false;
                }

                return recursiveMatch(var0, var1 + 1, var2, var3 + 1);
            }
        }
    }

    private static String getShellCommandLine() {
        try {
            String var0 = Platform.isMac() ? "command" : "args";
            String[] var1 = new String[]{"bash", "-c", "ps -p $PPID -o " + var0};
            Process var2 = Runtime.getRuntime().exec(var1);
            var2.waitFor();
            if (var2.getErrorStream().read() != -1) {
                return null;
            } else {
                BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
                var3.readLine();
                return var3.readLine();
            }
        } catch (Exception var4) {
            return null;
        }
    }

    private static String readMainClassFromManifest(String var0) {
        try {
            if (testDebugOption("main")) {
                System.out.println("Read class from JAR manifest in " + var0);
            }

            ZipFile var1 = new ZipFile(var0);
            ZipEntry var2 = var1.getEntry("META-INF/MANIFEST.MF");
            if (var2 == null) {
                return null;
            } else {
                BufferedReader var3 = new BufferedReader(new InputStreamReader(var1.getInputStream(var2)));

                for(String var4 = var3.readLine(); var4 != null; var4 = var3.readLine()) {
                    if (var4.startsWith("Main-Class:")) {
                        String var5 = var4.substring("Main-Class:".length()).trim();
                        if (testDebugOption("main")) {
                            System.out.println("Main class = " + var5);
                        }

                        return var5;
                    }
                }

                return null;
            }
        } catch (IOException var6) {
            return null;
        }
    }

    private static String readMainClassFromCommandLine(String var0) {
        if (testDebugOption("main")) {
            System.out.println("Read class from command line: " + var0);
        }

        if (var0 == null) {
            return null;
        } else {
            boolean var1 = false;

            try {
                StreamTokenizer var2 = new StreamTokenizer(new StringReader(var0));
                var2.resetSyntax();
                var2.wordChars(33, 255);
                var2.quoteChar(34);
                var2.quoteChar(39);
                var2.whitespaceChars(32, 32);
                var2.whitespaceChars(9, 9);
                boolean var3 = false;

                while(true) {
                    int var4 = var2.nextToken();
                    String var5 = var2.sval;
                    switch (var4) {
                        case -3:
                        case 34:
                        case 39:
                            if (var3) {
                                if (!var5.startsWith("-")) {
                                    if (var1) {
                                        return readMainClassFromManifest(var5);
                                    }

                                    if (testDebugOption("main")) {
                                        System.out.println("Main class = " + var5);
                                    }

                                    return var5;
                                }

                                if (var5.equals("-jar")) {
                                    var1 = true;
                                } else if (var5.equals("-cp") || var5.equals("-classpath")) {
                                    var2.nextToken();
                                }
                            } else {
                                var3 = true;
                            }
                            break;
                        case -1:
                            return null;
                        default:
                            return null;
                    }
                }
            } catch (IOException var6) {
                return null;
            }
        }
    }

    private static String readMainClassFromClassPath() {
        String var0 = null;
        String var1 = System.getProperty("java.class.path");
        if (var1 == null) {
            var1 = System.getProperty("user.dir");
        }

        if (var1 == null) {
            return null;
        } else {
            if (testDebugOption("main")) {
                System.out.println("Read class from class path: " + var1);
            }

            StringTokenizer var2 = new StringTokenizer(var1, ":;");

            while(var2.hasMoreTokens()) {
                String var3 = var2.nextToken();
                File var4 = new File(var3);
                String[] var5 = null;
                if (var4.isDirectory()) {
                    var5 = var4.list();
                } else if (var3.endsWith(".jar") && !nameAppears(var3, SKIP_JARS)) {
                    try {
                        ZipFile var6 = new ZipFile(var4);
                        ArrayList var7 = new ArrayList();
                        Enumeration var8 = var6.entries();

                        while(var8.hasMoreElements()) {
                            var7.add(((ZipEntry)var8.nextElement()).getName());
                        }

                        var5 = new String[var7.size()];

                        for(int var9 = 0; var9 < var5.length; ++var9) {
                            var5[var9] = (String)var7.get(var9);
                        }
                    } catch (IOException var12) {
                        var5 = null;
                    }
                }

                if (var5 != null) {
                    for(int var13 = 0; var13 < var5.length; ++var13) {
                        String var14 = var5[var13];
                        if (var14.endsWith(".class")) {
                            String var15 = var14.substring(0, var14.lastIndexOf(".class"));
                            if (var15.indexOf("/") == -1 && checkIfLoaded(var15)) {
                                try {
                                    Class var16 = Class.forName(var15);
                                    Class[] var10 = new Class[]{var5.getClass()};
                                    if (var16.getMethod("main", var10) != null) {
                                        if (testDebugOption("main")) {
                                            System.out.println("Main class = " + var15);
                                        }

                                        if (var0 != null) {
                                            return null;
                                        }

                                        var0 = var15;
                                    }
                                } catch (Exception var11) {
                                }
                            }
                        }
                    }
                }
            }

            return var0;
        }
    }

    protected static void submitProject(Program var0, JProgressBar var1) {
        SubmitOptions var2 = getOptions(var0);
        if (var2.popup() && var2.isComplete()) {
            String var3 = var0.getClass().getName();
            String var4 = var3.substring(var3.lastIndexOf(".") + 1);
            String var5 = "==" + System.currentTimeMillis() + "==";
            String var6 = var2.getSMTPServer();
            String var7 = var2.getAuthorName();
            String var8 = var2.getAuthorEMail();
            String var9 = var2.getSubmissionEMail();
            String var10 = var8;
            int var11 = var8.indexOf("@");
            if (var11 != -1) {
                var10 = var8.substring(0, var11);
            }

            String var12 = var4 + "_" + var10;
            File var13 = new File(System.getProperty("user.dir"));
            if (var1 != null) {
                var1.setMaximum(countResources(var13, SUBMIT_EXTENSIONS, (String)null));
                ProgressBarDialog.popup(var1);
            }

            PrintStream var14 = openMailStream(var6, var8, var9);
            sendStandardHeaders(var14, var7, var4);
            var14.println("Mime-Version: 1.0");
            var14.println("Content-Type: multipart/mixed; boundary=\"" + var5 + '"');
            var14.println();
            var14.println("--" + var5);
            var14.println("Content-Transfer-Encoding: base64");
            var14.println("Content-Type: application/zip; name=" + var12 + ".zip");
            var14.println("Content-Disposition: attachment; filename=" + var12 + ".zip");
            submitDirectory(var14, var12, var1);
            if (ProgressBarDialog.hasBeenCancelled(var1)) {
                cancelMail(var14);
            }

            var14.println("--" + var5 + "--");
            var14.close();
            if (var1 != null) {
                ProgressBarDialog.dismiss(var1);
            }
        }

    }

    private static void dumpHTMLIndex(File var0, Program var1, String var2, String var3) throws IOException {
        File var4 = new File(var0, "index.html");
        PrintWriter var5 = new PrintWriter(new FileWriter(var4));
        Dimension var6 = var1.getSize();
        var5.println("<html>");
        var5.println("<head>");
        var5.println("<meta name=\"generator\" content=\"ACM Java Libraries V1.1\">");
        var5.println("<title>" + var3 + "</title>");
        var5.println("</head>");
        var5.println("<body>");
        var5.println("<center>");
        var5.println("<table border=2 cellpadding=0 cellspacing=0>");
        var5.println("<tr><td>");
        var5.println("<applet archive=\"" + var3 + ".jar\"");
        var5.println("        code=\"" + var2.replace('.', '/') + ".class\"");
        var5.println("        width=" + var6.width + " height=" + var6.height + ">");
        var5.println("</applet>");
        var5.println("</td></tr>");
        var5.println("</table>");
        var5.println("</center>");
        var5.println("</body>");
        var5.println("</html>");
        var5.close();
    }

    private static void dumpJarAndResources(File var0, File var1, String var2, String var3, JProgressBar var4, boolean var5, Object var6) throws IOException {
        File var7 = new File(var1, var2);
        ZipOutputStream var8 = new ZipOutputStream(new FileOutputStream(var7));
        dumpJarAndResources("", var0, var8, var1, var3, var4, var5, var6);
        var8.close();
    }

    private static void dumpJarAndResources(String var0, File var1, ZipOutputStream var2, File var3, String var4, JProgressBar var5, boolean var6, Object var7) throws IOException {
        if (!ProgressBarDialog.hasBeenCancelled(var5)) {
            dumpTree(var0, var1, var2, var3, var5, 0, var6, var7);
            StringTokenizer var8 = new StringTokenizer(var4, ";");

            while(var8.hasMoreTokens()) {
                String var9 = var8.nextToken().trim();
                File var10 = getLibrary(var9);
                if (var10 != null) {
                    ZipFile var11 = new ZipFile(var10);
                    Enumeration var12 = var11.entries();

                    while(var12.hasMoreElements()) {
                        ZipEntry var13 = (ZipEntry)var12.nextElement();
                        String var14 = var13.getName();
                        if (!nameAppears(var14, SKIP_FILES)) {
                            BufferedInputStream var15 = new BufferedInputStream(var11.getInputStream(var13));
                            if (var7 != null && var14.endsWith(".class")) {
                                var13 = new ZipEntry(var14);
                                var2.putNextEntry(var13);
                                transformClass(var7, var15, var2);
                            } else {
                                var2.putNextEntry(var13);
                                copyBytes(var15, var2, var13.getSize());
                            }

                            while(true) {
                                int var16 = ((InputStream)var15).read();
                                if (var16 == -1) {
                                    var2.closeEntry();
                                    ((InputStream)var15).close();
                                    break;
                                }

                                var2.write(var16);
                            }
                        }

                        if (var5 != null) {
                            var5.setValue(var5.getValue() + 1);
                        }
                    }
                }
            }

        }
    }

    private static void dumpTree(String var0, File var1, ZipOutputStream var2, File var3, JProgressBar var4, int var5, boolean var6, Object var7) throws IOException {
        if (!ProgressBarDialog.hasBeenCancelled(var4)) {
            String var8 = var1.getName();
            if (var1.isDirectory()) {
                String[] var9 = var1.list();
                if (var5 > 0) {
                    var0 = var0 + var8 + "/";
                }

                for(int var10 = 0; var10 < var9.length; ++var10) {
                    dumpTree(var0, new File(var1, var9[var10]), var2, var3, var4, var5 + 1, var6, var7);
                }
            } else if (isResourceComponent(var8)) {
                String var15 = var0 + var8;
                if (var15.startsWith("Java Classes/")) {
                    var15 = var15.substring(var15.indexOf(47) + 1);
                }

                ZipEntry var16 = new ZipEntry(var15);
                BufferedInputStream var11 = new BufferedInputStream(new FileInputStream(var1));
                var2.putNextEntry(var16);
                if (var7 != null && var8.endsWith(".class")) {
                    transformClass(var7, var11, var2);
                } else {
                    copyBytes(var11, var2, var1.length());
                }

                ((InputStream)var11).close();
                if (var6 && var3 != null && !var8.endsWith(".class")) {
                    var11 = new BufferedInputStream(new FileInputStream(var1));
                    var3 = new File(var3, var0);
                    File var12 = new File(var3, var8);
                    var3.mkdirs();
                    BufferedOutputStream var13 = new BufferedOutputStream(new FileOutputStream(var12));
                    copyBytes(var11, var13, var1.length());
                    ((InputStream)var11).close();
                    var13.close();
                }

                if (var4 != null) {
                    var4.setValue(var4.getValue() + 1);
                }
            }

        }
    }

    private static void transformClass(Object obj, InputStream inputstream, OutputStream outputstream)
    {
        Method method = null;
        try
        {
            Class aclass[] = {
                Class.forName("java.io.InputStream"), Class.forName("java.io.OutputStream")
            };
            method = obj.getClass().getMethod("transform", aclass);
        }
        catch(Exception exception)
        {
            throw new ErrorException("exportJar: Illegal class transformer object");
        }
        try
        {
            Object aobj[] = {
                inputstream, outputstream
            };
            method.invoke(obj, aobj);
        }
        catch(Exception exception1)
        {
            throw new ErrorException(exception1);
        }
    }
    private static int countResources(File var0, String[] var1, String var2) {
        String var3 = var0.getName();
        int var4 = 0;
        if (var0.isDirectory()) {
            String[] var5 = var0.list();

            for(int var6 = 0; var6 < var5.length; ++var6) {
                var4 += countResources(new File(var0, var5[var6]), var1, (String)null);
            }
        } else {
            for(int var9 = 0; var9 < var1.length && var4 == 0; ++var9) {
                if (var3.endsWith(var1[var9])) {
                    var4 = 1;
                }
            }
        }

        if (var2 != null) {
            File var10 = getLibrary(var2);
            if (var10 != null) {
                try {
                    ZipFile var11 = new ZipFile(var10);
                    Enumeration var7 = var11.entries();

                    while(var7.hasMoreElements()) {
                        ++var4;
                        var7.nextElement();
                    }
                } catch (IOException var8) {
                }
            }
        }

        return var4;
    }

    private static File getLibrary(String var0) {
        if (var0 == null) {
            return null;
        } else {
            File var1 = new File(var0);
            if (!var0.startsWith(".") && !var1.isAbsolute()) {
                String var2 = System.getProperty("java.class.path");
                if (var2 == null) {
                    var2 = "";
                }

                StringTokenizer var3 = new StringTokenizer(var2, ":");

                while(var3.hasMoreTokens()) {
                    String var4 = var3.nextToken();
                    if (var4.equals(var0) || var4.endsWith("/" + var0)) {
                        return new File(var4);
                    }
                }

                File var6 = new File(System.getProperty("user.dir"));
                var1 = new File(var6, var0);
                return var1.exists() ? var1 : null;
            } else {
                return var1;
            }
        }
    }

    private static boolean isResourceComponent(String var0) {
        for(int var1 = 0; var1 < RESOURCE_EXTENSIONS.length; ++var1) {
            if (var0.endsWith(RESOURCE_EXTENSIONS[var1])) {
                return true;
            }
        }

        return false;
    }

    private static void submitDirectory(PrintStream var0, String var1, JProgressBar var2) {
        try {
            OutputStream var3 = openBase64OutputStream(var0);
            ZipOutputStream var4 = new ZipOutputStream(new BufferedOutputStream(var3));
            ZipOutputStream var5 = new ZipOutputStream(new NullOutputStream());
            File var6 = new File(System.getProperty("user.dir"));
            dumpZip(var1 + "/", var6, var4, var5, true, var2);
            var5.close();
            var4.finish();
            var4.flush();
            padBase64OutputStream(var3);
        } catch (IOException var7) {
            throw new ErrorException(var7);
        }
    }

    private static void dumpZip(String var0, File var1, ZipOutputStream var2, ZipOutputStream var3, boolean var4, JProgressBar var5) throws IOException {
        if (!ProgressBarDialog.hasBeenCancelled(var5)) {
            String var6 = var1.getName();
            if (var1.isDirectory()) {
                String[] var7 = var1.list();
                if (!var4) {
                    var0 = var0 + var6 + "/";
                }

                for(int var8 = 0; var8 < var7.length; ++var8) {
                    dumpZip(var0, new File(var1, var7[var8]), var2, var3, false, var5);
                }
            } else if (isSubmitComponent(var6)) {
                String var10 = var0 + var6;
                ZipEntry var11 = new ZipEntry(var10);
                BufferedInputStream var9 = new BufferedInputStream(new FileInputStream(var1));
                if (var3 != null) {
                    var3.putNextEntry(var11);
                    copyBytes(var9, var3, var1.length());
                    var3.closeEntry();
                    ((InputStream)var9).close();
                    var9 = new BufferedInputStream(new FileInputStream(var1));
                }

                var2.putNextEntry(var11);
                copyBytes(var9, var2, var1.length());
                var2.closeEntry();
                ((InputStream)var9).close();
                if (var5 != null) {
                    var5.setValue(var5.getValue() + 1);
                }
            }

        }
    }

    private static boolean isSubmitComponent(String var0) {
        for(int var1 = 0; var1 < SUBMIT_EXTENSIONS.length; ++var1) {
            if (var0.endsWith(SUBMIT_EXTENSIONS[var1])) {
                return true;
            }
        }

        return false;
    }

    private static SubmitOptions getOptions(Program var0) {
        SubmitOptions var1 = (SubmitOptions)optionsTable.get(var0);
        if (var1 == null) {
            var1 = new SubmitOptions(var0);
            optionsTable.put(var0, var1);
        }

        return var1;
    }

    private static boolean nameAppears(String var0, String[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2].equals(var0)) {
                return true;
            }
        }

        return false;
    }
}
