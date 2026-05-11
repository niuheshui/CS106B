// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JTFTools.java

package acm.util;

import acm.gui.TableLayout;
import acm.program.Program;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.Socket;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

// Referenced classes of package acm.util:
//            JTFTools

public class SubmitOptions implements ActionListener, DocumentListener {
    private static final int FIELD_WIDTH = 300;
    private static final int DIALOG_WIDTH = 500;
    private static final int DIALOG_HEIGHT = 230;
    private static final int SMTP_PORT = 25;
    private Component parent;
    private JDialog dialog;
    private JTextField authorNameField;
    private JTextField authorEMailField;
    private JTextField instructorEMailField;
    private JTextField smtpServerField;
    private JButton cancelButton;
    private JButton submitButton;
    private boolean submitFlag;

    public SubmitOptions(Program var1) {
        this.parent = var1;
        this.authorNameField = new JTextField();
        this.authorEMailField = new JTextField();
        this.instructorEMailField = new JTextField();
        this.smtpServerField = new JTextField();
        this.cancelButton = new JButton("Cancel");
        this.submitButton = new JButton("Submit");
        this.authorEMailField.getDocument().addDocumentListener(this);
        this.instructorEMailField.getDocument().addDocumentListener(this);
        this.smtpServerField.getDocument().addDocumentListener(this);
        this.cancelButton.addActionListener(this);
        this.submitButton.addActionListener(this);
        this.initPreferences(var1);
    }

    public String getAuthorName() {
        return this.authorNameField.getText().trim();
    }

    public String getAuthorEMail() {
        return this.authorEMailField.getText().trim();
    }

    public String getSubmissionEMail() {
        return this.instructorEMailField.getText().trim();
    }

    public String getSMTPServer() {
        return this.smtpServerField.getText().trim();
    }

    public boolean isComplete() {
        if (this.getAuthorEMail().indexOf(64) == -1) {
            return false;
        } else if (this.getSubmissionEMail().indexOf(64) == -1) {
            return false;
        } else {
            return this.getSMTPServer().length() != 0;
        }
    }

    public boolean popup() {
        Frame var1 = JTFTools.getEnclosingFrame(this.parent);
        if (var1 == null) {
            return false;
        } else {
            this.dialog = new JDialog(var1, "Submit Project Options", true);
            Container var2 = this.dialog.getContentPane();
            var2.setLayout(new TableLayout(6, 2, 0, 4));
            var2.add(new JLabel("Instructor email ", 4));
            var2.add(this.instructorEMailField, "width=300");
            var2.add(new JLabel("Author name ", 4));
            var2.add(this.authorNameField, "width=300");
            var2.add(new JLabel("Author email ", 4));
            var2.add(this.authorEMailField, "width=300");
            var2.add(new JLabel("SMTP server ", 4));
            var2.add(this.smtpServerField, "width=300");
            JPanel var3 = new JPanel();
            var3.setLayout(new FlowLayout());
            var3.add(this.cancelButton);
            var3.add(this.submitButton);
            var2.add(new JLabel(""));
            var2.add(var3, "top=10");
            this.dialog.setSize(500, 230);
            this.submitFlag = false;
            this.submitButton.setEnabled(this.isComplete());
            this.dialog.setVisible(true);
            return this.submitFlag;
        }
    }

    public void actionPerformed(ActionEvent var1) {
        this.submitFlag = var1.getSource() == this.submitButton;
        this.dialog.setVisible(false);
    }

    public void changedUpdate(DocumentEvent var1) {
        this.submitButton.setEnabled(this.isComplete());
    }

    public void removeUpdate(DocumentEvent var1) {
        this.submitButton.setEnabled(this.isComplete());
    }

    public void insertUpdate(DocumentEvent var1) {
        this.submitButton.setEnabled(this.isComplete());
    }

    private void initPreferences(Program var1) {
        this.instructorEMailField.setText(this.getDefaultField("INSTRUCTOR_EMAIL", var1));
        this.authorNameField.setText(this.getDefaultField("AUTHOR_NAME", var1));
        String var2 = this.getDefaultField("AUTHOR_EMAIL", var1);
        if (var2.length() == 0) {
            var2 = System.getProperty("user.name");
            if (var2 == null) {
                var2 = "";
            } else {
                String var3 = JTFTools.getLocalHostName();
                if (var3 != null) {
                    var2 = var2 + "@" + var3;
                }
            }
        }

        this.authorEMailField.setText(var2);
        String var7 = this.getDefaultField("SMTP_SERVER", var1);
        if (var7.length() == 0) {
            var7 = System.getProperty("mail.smtp.host");
            if (var7 == null) {
                String var4 = JTFTools.getLocalHostName();
                if (var4 == null) {
                    var7 = "";
                } else {
                    var7 = "smtp." + var4.substring(var4.indexOf(46) + 1);

                    try {
                        (new Socket(var7, 25)).close();
                    } catch (Exception var6) {
                        var7 = "";
                    }
                }
            }
        }

        this.smtpServerField.setText(var7);
    }

    private String getDefaultField(String var1, Program var2) {
        try {
            Field var3 = var2.getClass().getField(var1);
            String var4 = (String)var3.get(var2);
            if (var4 != null && var4.trim().length() != 0) {
                return var4;
            }
        } catch (Exception var5) {
        }

        return "";
    }
}
