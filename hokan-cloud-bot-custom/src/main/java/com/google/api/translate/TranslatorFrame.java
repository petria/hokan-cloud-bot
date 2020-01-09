/**
 * TranslatorFrame.java
 * <p>
 * Copyright (C) 2008,  Richard Midwinter
 * <p>
 * This file is part of google-api-translate-java.
 * <p>
 * google-api-translate-java is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * <p>
 * google-api-translate-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with google-api-translate-java. If not, see <http://www.gnu.org/licenses/>.
 */
package com.google.api.translate;

/*
 * TranslatorFrame.java
 *
 * Created on 03 February 2008, 19:47
 */

import com.google.api.Files;
import com.google.api.GoogleAPI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Richard Midwinter
 */
public class TranslatorFrame extends JFrame {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7916697355146649532L;

    private static final String
            REFERRER_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".gtReferrer",
            API_KEY_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".google-translate-api.key";

    private Translate translate;

    private Language languageFrom = Language.FRENCH;
    private Language languageTo = Language.ENGLISH;

    /** Creates new form TranslatorFrame */
    public TranslatorFrame() throws IOException {
        initComponents();
        setLocationRelativeTo(null);

        translate = Translate.DEFAULT;

        final File key = new File(API_KEY_PATH);
        if (key.exists()) {
            GoogleAPI.setKey(Files.read(key));
        }

        String referrer = null;

        final File ref = new File(REFERRER_PATH);
        if (ref.exists()) {
            referrer = Files.read(ref).trim();
        } else {
            referrer = (String) JOptionPane.showInputDialog(this,
                    "Please enter the address of your website.\n(This is just to help Google identify how their translation tools are used).",
                    "Website address", JOptionPane.OK_OPTION);
            Files.write(ref, referrer);
        }

        if (referrer.length() > 0) {
            GoogleAPI.setHttpReferrer(referrer);
        } else {
            System.exit(1);
        }
    }

    private void translate() {
        try {
            toTextArea.setText(translate.execute(fromTextArea.getText().trim(), languageFrom, languageTo));
        } catch (Exception ex) {
            Logger.getLogger(TranslatorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        buttonGroup1 = new ButtonGroup();
        buttonGroup2 = new ButtonGroup();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        fromTextArea = new JTextArea();
        jPanel3 = new JPanel();
        jScrollPane2 = new JScrollPane();
        toTextArea = new JTextArea();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuTo = new JMenu();
        jMenuFrom = new JMenu();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Translator");
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        fromTextArea.setColumns(20);
        fromTextArea.setLineWrap(true);
        fromTextArea.setRows(5);
        fromTextArea.setWrapStyleWord(true);
        fromTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                fromTextAreaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(fromTextArea);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                .addContainerGap())
        );

        getContentPane().add(jPanel2);

        toTextArea.setColumns(20);
        toTextArea.setEditable(false);
        toTextArea.setLineWrap(true);
        toTextArea.setRows(5);
        toTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(toTextArea);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                .addContainerGap())
        );

        getContentPane().add(jPanel3);

        jMenu1.setText("File");

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenuFrom.setText("From");
        jMenuTo.setText("To");

        for (final Language language : Language.values()) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem();
            menuItem.setText(language.name());
            if (language.equals(languageFrom)) {
                menuItem.setSelected(true);
            }
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent evt) {
                    languageFrom = language;
                }
            });
            buttonGroup1.add(menuItem);
            jMenuFrom.add(menuItem);

            if (language != Language.AUTO_DETECT) {
                menuItem = new JRadioButtonMenuItem();
                menuItem.setText(language.name());
                if (language.equals(languageTo)) {
                    menuItem.setSelected(true);
                }
                menuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent evt) {
                        languageTo = language;
                    }
                });
                buttonGroup2.add(menuItem);
                jMenuTo.add(menuItem);
            }
        }

        jMenuBar1.add(jMenuFrom);
        jMenuBar1.add(jMenuTo);

        setJMenuBar(jMenuBar1);

        pack();
    }

    private void jMenuItem1ActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void fromTextAreaKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            translate();
            evt.consume();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TranslatorFrame().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Variables declaration
    private ButtonGroup buttonGroup1;
    private ButtonGroup buttonGroup2;
    private JTextArea fromTextArea;
    private JMenu jMenu1;
    private JMenu jMenuFrom;
    private JMenu jMenuTo;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea toTextArea;
    // End of variables declaration   
}