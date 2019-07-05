package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.widget.ButtonCoral;
import com.fieryslug.reinforcedcoral.widget.LabelCoral;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;

public class PanelProblem extends JPanel {

    public JLabel labelTitle;
    public JTextArea areaDescription;
    public JLabel labelImage;

    private int height;
    private int width;
    private Page page;
    private TableLayout layout;
    private double[][] layoutSize;

    public PanelProblem(FrameCoral parent) {

        this.layoutSize = new double[2][20];
        for(int i=0; i<20; ++i) this.layoutSize[0][i] = this.layoutSize[1][i] = 0.05;

        /*for(int i=0; i<20; ++i) {
            layoutSize[i][0] = layoutSize[i][1] = 0.05;
        }
        */

        this.layout = new TableLayout(layoutSize);
        setLayout(this.layout);

        this.areaDescription = new JTextArea();
        this.areaDescription.setFont(Reference.JHENGHEI30);
        this.areaDescription.setBackground(Reference.BLACK);
        this.areaDescription.setForeground(Reference.WHITE);
        this.areaDescription.setLineWrap(true);
        this.areaDescription.setEditable(false);
        this.setBackground(Reference.BLACK);

        this.labelTitle = new JLabel();
        this.labelTitle.setFont(Reference.JHENGHEI40BOLD);
        this.labelTitle.setBackground(Reference.BLACK);
        this.labelTitle.setForeground(Reference.WHITE);

        this.labelImage = new JLabel();
        this.labelImage.setBackground(Reference.BLACK);

        //setBorder(Reference.BEVELGREEN);



    }
    /*
    public void inflate(Page page) {

        this.areaDescription.setText("");
        HTMLDocument document = (HTMLDocument)(this.areaDescription.getDocument());
        HTMLEditorKit editorKit = (HTMLEditorKit)(this.areaDescription.getEditorKit());

        try {
            editorKit.insertHTML(document, document.getLength(), page.htmlText, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("inflated:\n" + page.htmlText);

    }
    */

    public void inflate2(Page page) {

        removeAll();
        clearThings();
        setLayout(new TableLayout(this.layoutSize));

        this.height = (int)this.getPreferredSize().getHeight();
        this.width = (int)this.getPreferredSize().getWidth();
        this.page = page;

        //answer
        if(page.type == -1) {

            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));
            add(this.labelTitle, "0, 0, 19, 4");
            add(this.areaDescription, "1, 5, 18, 19");

        }
        //title+description
        if(page.type == 0) {
            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));


            add(this.labelTitle, "0, 0, 19, 4");
            add(this.areaDescription, "1, 5, 18, 19");

        }
        //title
        if(page.type == 1) {
            this.labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
            this.labelTitle.setText(page.res.get(0));
            //this.areaDescription.setAlignmentX(CENTER_ALIGNMENT);
            //this.areaDescription.setAlignmentY(CENTER_ALIGNMENT);
            //this.areaDescription.setText(page.res.get(0));

            add(this.labelTitle, "0, 0, 19, 19");
        }
        //title+description+image
        if(page.type == 2) {

            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));
            Image image = MediaRef.getImage(page.res.get(2));
            image = FuncBox.resizeImagePreservingRatio(image, this.width * 8/20, this.height * 15/20);
            this.labelImage.setIcon(new ImageIcon(image));

            add(this.labelTitle, "0, 0, 19, 4");
            add(FuncBox.blankLabel(0, 0), "0, 5, 12, 7");
            add(this.areaDescription, "1, 7, 11, 19");
            add(this.labelImage, "12, 5, 19, 19");

        }
        //image
        if(page.type == 3) {
            Image image = MediaRef.getImage(page.res.get(0));
            image = FuncBox.resizeImagePreservingRatio(image, this.width, this.height);
            this.labelImage.setIcon(new ImageIcon(image));

        }
        //title + top text image bottom
        if (page.type == 4) {
            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));
            Image image = MediaRef.getImage(page.res.get(2));
            this.labelImage.setHorizontalAlignment(SwingConstants.CENTER);
            image = FuncBox.resizeImagePreservingRatio(image, this.width, this.height * 5 / 20);
            this.labelImage.setIcon(new ImageIcon(image));

            add(this.labelTitle, "0, 0, 19, 4");
            add(this.areaDescription, "1, 5, 18, 9");
            add(this.labelImage, "0, 10, 19, 19");
        }

        if(page.type == Reference.MAGIC_PRIME) {


        }


    }

    private void clearThings() {

        this.labelTitle.setText("");
        this.labelTitle.setIcon(null);

        this.areaDescription.setText("");

        this.labelImage.setIcon(null);


    }

    public void enter() {


    }

    public void changeFonts(boolean isFullScreen) {

        if (isFullScreen) {
            if(this.page.type == 0) {
                this.labelTitle.setFont(Reference.JHENGHEI60BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI45);
            }
            if(this.page.type == 1) {
                this.labelTitle.setFont(Reference.JHENGHEI120BOLD);
            }
            if(this.page.type == 2) {
                this.labelTitle.setFont(Reference.JHENGHEI60BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI45);
            }
            if(this.page.type == -1) {
                this.labelTitle.setFont(Reference.JHENGHEI60BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI45);
            }
            if(this.page.type == 4) {
                this.labelTitle.setFont(Reference.JHENGHEI60BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI45);
            }
        }
        else {
            if(this.page.type == 0) {
                this.labelTitle.setFont(Reference.JHENGHEI40BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI30);
            }
            if(this.page.type == 1) {
                this.labelTitle.setFont(Reference.JHENGHEI80BOLD);
            }
            if(this.page.type == 2) {
                this.labelTitle.setFont(Reference.JHENGHEI40BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI30);
            }
            if(this.page.type == -1) {
                System.out.println("eeeeee");
                this.labelTitle.setFont(Reference.JHENGHEI40BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI30);
            }
            if (this.page.type == 4) {
                this.labelTitle.setFont(Reference.JHENGHEI40BOLD);
                this.areaDescription.setFont(Reference.JHENGHEI30);
            }

        }


    }
}
