package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.page.Widget;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.SpinnerLayout;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.util.filter.ColorFilter;
import com.fieryslug.reinforcedcoral.util.filter.IntFilter;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.PlainDocument;

import info.clearthought.layout.TableLayout;

public class PanelEditProblem extends PanelInterior {


    private Problem problem;
    private int currPageNum;

    private static final GamePhase[] PHASES = new GamePhase[]{GamePhase.IN_PROBLEM, GamePhase.ANSWERING, GamePhase.SOLUTION, GamePhase.POST_SOLUTION};
    private GamePhase phase;


    private PanelEditPage panelInteriorPage;

    private ButtonCoral buttonPrev;
    private ButtonCoral buttonNext;




    private PanelEdit panelEdit;
    //panel 0
    private JLabel labelTitle;
    private JLabel labelProbName;
    private JLabel labelBack;
    private ButtonCoral buttonBack;
    //panel 1
    JToggleButton[] toggleButtons;
    JLabel[] labelsAttr;
    JTextField[] fieldsAttr;

    JLabel labelImage;
    ButtonCoral buttonImage;
    JLabel labelChooseFile;

    //panel2
    JLabel labelLayout;
    JLabel[] labels;
    JTextField[] fields;
    ButtonCoral buttonApplyLayout;
    JLabel labelApplyLayout;

    //banner
    JLabel labelAbstract;
    JSpinner spinnerAbstract;
    JPanel panelMini;
    JLabel labelAbstractName;


    private MouseAdapter mouseAdapter;
    private JFileChooser fileChooser;

    private static double[][] layout1 = new double[][]{{0.05, 0.14, 0.05, 0.14, 0.05, 0.14, 0.05, 0.14, 0.05, 0.14, 0.05}, {0.07, 0.24, 0.07, 0.24, 0.07, 0.24, 0.07}};
    private static double[][] layout2 = new double[][]{FuncBox.createDivisionArray(4), {0.4, 0.3, 0.3}};


    PanelEditProblem(PanelEdit panelEdit) {

        TextureHolder holder = TextureHolder.getInstance();
        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};

        double[][] size = new double[][]{{1.0d / 6, 1.0d / 6, 2.0d / 6, 1.0d / 6, 1.0d / 6}, {5.0d / 6, 1.0d / 6}};
        setLayout(new TableLayout(size));

        this.panelEdit = panelEdit;

        this.panelInteriorPage = new PanelEditPage(panelEdit.parent, this);



        this.buttonPrev = new ButtonCoral(images[0], images[1], images[2]);
        this.buttonNext = new ButtonCoral(images[0], images[1], images[2]);

        //panel 0
        this.labelTitle = new JLabel("    editting:");
        this.labelProbName = new JLabel();

        this.labelBack = new JLabel("back", SwingConstants.LEFT);

        this.buttonBack = new ButtonCoral(images[0], images[1], images[2]);

        //panel 1
        toggleButtons = new JToggleButton[4];
        for (int i = 0; i < 4; ++i) {
            toggleButtons[i] = new JToggleButton();
            toggleButtons[i].setFocusPainted(false);
            toggleButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            toggleButtons[i].setVerticalAlignment(SwingConstants.CENTER);
            toggleButtons[i].setVisible(false);
        }

        toggleButtons[0].setText("center");
        toggleButtons[1].setText("bold");

        labelsAttr = new JLabel[4];
        for (int i = 0; i < 4; ++i) {
            labelsAttr[i] = new JLabel("", SwingConstants.CENTER);
            labelsAttr[i].setVisible(false);
        }

        labelsAttr[0].setText("text size");
        labelsAttr[1].setText("color");

        fieldsAttr = new JTextField[4];
        for (int i = 0; i < 4; ++i) {
            fieldsAttr[i] = new JTextField();
            fieldsAttr[i].setHorizontalAlignment(SwingConstants.CENTER);
            fieldsAttr[i].setVisible(false);
        }
        ((PlainDocument) fieldsAttr[0].getDocument()).setDocumentFilter(new IntFilter(0, 129, 3));
        ((PlainDocument) fieldsAttr[1].getDocument()).setDocumentFilter(new ColorFilter());


        labelImage = new JLabel("choose file", SwingConstants.RIGHT);
        labelImage.setVisible(false);
        buttonImage = new ButtonCoral(images[0], images[1], images[2]);
        buttonImage.setVisible(false);
        labelChooseFile = new JLabel("choose file", SwingConstants.LEFT);
        labelChooseFile.setVisible(false);



        //panel 2
        this.labelLayout = new JLabel("    layout  (x,y<20)");
        this.labelLayout.setVisible(false);

        labels = new JLabel[4];
        String[] text = new String[]{"x1", "y1", "x2", "y2"};
        for (int i = 0; i < 4; ++i) {
            labels[i] = new JLabel("", SwingConstants.CENTER);
            labels[i].setText(text[i]);
            labels[i].setVisible(false);
        }

        fields = new JTextField[4];
        for (int i = 0; i < 4; ++i) {
            fields[i] = new JTextField();
            PlainDocument doc = (PlainDocument) fields[i].getDocument();

            doc.setDocumentFilter(new IntFilter(0, 20, 2));
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            fields[i].setVisible(false);
            fields[i].setOpaque(false);
        }
        buttonApplyLayout = new ButtonCoral(images[0], images[1], images[2]);
        buttonApplyLayout.setVisible(false);
        labelApplyLayout = new JLabel("apply", SwingConstants.RIGHT);
        labelApplyLayout.setVisible(false);

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                panelInteriorPage.clearSounds();
                panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                panelEdit.parent.switchPanel(panelEdit, panelEdit);

            }
        });

        //banner
        labelAbstract = new JLabel("extra    ", SwingConstants.RIGHT);
        spinnerAbstract = new JSpinner() {
            @Override
            public void setLayout(LayoutManager layoutManager) {
                super.setLayout(new SpinnerLayout());
            }
        };
        panelMini = new JPanel();
        panelMini.setLayout(new TableLayout(new double[][]{{0.1, 0.8, 0.1}, {0.15, 0.7, 0.15}}));
        panelMini.setOpaque(false);
        labelAbstractName = new JLabel("", SwingConstants.CENTER);

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                if (phase == GamePhase.IN_PROBLEM) {
                    if (currPageNum + 1 >= problem.getPages().size()) {
                        currPageNum = 0;
                        setPhase(GamePhase.ANSWERING);
                    } else {
                        currPageNum += 1;
                        if (problem.getPages().get(currPageNum).isFinal())
                            setPhase(GamePhase.ANSWERING);

                    }
                } else if (phase == GamePhase.ANSWERING) {
                    currPageNum = 0;
                    setPhase(GamePhase.SOLUTION);

                } else if (phase == GamePhase.SOLUTION) {
                    currPageNum = 0;
                    if (!problem.getPagesExplanation().isEmpty()) {
                        setPhase(GamePhase.POST_SOLUTION);
                    } else {

                    }
                } else if (phase == GamePhase.POST_SOLUTION) {
                    if (currPageNum + 1 >= problem.getPagesExplanation().size()) {

                    } else {
                        currPageNum += 1;

                    }
                }
                panelInteriorPage.setCurrWidget(null);
                panelEdit.parent.switchPanel(panelEdit, panelEdit);

            }
        });

        buttonPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (phase == GamePhase.IN_PROBLEM) {
                    if (currPageNum <= 0) {

                    } else {
                        currPageNum -= 1;

                    }
                } else if (phase == GamePhase.ANSWERING) {

                    if (currPageNum == 0) {

                    } else {
                        currPageNum -= 1;
                        setPhase(GamePhase.IN_PROBLEM);
                    }

                } else if (phase == GamePhase.SOLUTION) {

                    currPageNum = problem.getPages().size() - 1;
                    setPhase(GamePhase.ANSWERING);


                } else if (phase == GamePhase.POST_SOLUTION) {

                    if (currPageNum == 0) {
                        setPhase(GamePhase.SOLUTION);
                    }
                    else {
                        currPageNum -= 1;
                    }

                }
                panelInteriorPage.setCurrWidget(null);
                panelEdit.parent.switchPanel(panelEdit, panelEdit);
            }
        });

        toggleButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelInteriorPage.reformatWidget();
            }
        });

        toggleButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelInteriorPage.reformatWidget();
            }
        });

        buttonImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.showOpenDialog(panelEdit.parent);
                File file = fileChooser.getSelectedFile();
                if(file != null)
                    panelInteriorPage.setWidgetImage(file);
            }
        });

        fieldsAttr[0].addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                System.out.println("released");
                panelInteriorPage.reformatWidget();
            }
        });

        fieldsAttr[1].addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (fieldsAttr[1].getText().length() == 6 || fieldsAttr[1].getText().equals("-1")) {
                    panelInteriorPage.reformatWidget();
                }
            }
        });

        buttonApplyLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelInteriorPage.relocateWidget();
            }
        });

        this.mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                panelInteriorPage.setCurrWidget(null);
            }
        };

        labelAbstractName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Widget widget = panelInteriorPage.getCurrAbstractWidget();
                panelInteriorPage.setCurrWidget(widget);
            }
        });
        labelAbstractName.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                panelInteriorPage.scrollAbstractWidget(mouseWheelEvent);
            }
        });

        this.fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getPath().endsWith(".png") || file.getPath().endsWith(".jpg") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

    }


    @Override
    public void enter() {

        panelEdit.panels[0].add(labelTitle, "0, 0, 0, 0");
        panelEdit.panels[0].add(labelProbName, "1, 0, 3, 0");
        panelEdit.panels[0].add(buttonBack, "0, 2");
        panelEdit.panels[0].add(labelBack, "1, 2");


        panelEdit.panels[1].setLayout(new TableLayout(layout1));

        panelEdit.panels[1].add(toggleButtons[0], "1, 1");
        panelEdit.panels[1].add(toggleButtons[1], "3, 1");
        panelEdit.panels[1].add(labelsAttr[0], "1, 3, 2, 3");
        panelEdit.panels[1].add(fieldsAttr[0], "3, 3");
        panelEdit.panels[1].add(labelsAttr[1], "1, 5, 2, 5");
        panelEdit.panels[1].add(fieldsAttr[1], "3, 5");

        panelEdit.panels[1].add(labelImage, "1, 3, 3, 3");
        panelEdit.panels[1].add(buttonImage, "4, 3, 5, 3");
        panelEdit.panels[1].add(labelChooseFile, "6, 3, 8, 3");

        panelEdit.panels[2].add(labelLayout, "0, 0, 1, 0");
        panelEdit.panels[2].add(buttonApplyLayout, "3, 0");
        panelEdit.panels[2].add(labelApplyLayout, "2, 0");


        for (int i = 0; i < 4; ++i) {
            panelEdit.panels[2].add(labels[i], i + ", 1");
            panelEdit.panels[2].add(fields[i], i + ", 2");
        }


        labelProbName.setText(problem.name);

        add(buttonPrev, "0, 1");
        add(buttonNext, "4, 1");
        add(labelAbstract, "2, 1");
        add(panelMini, "3, 1");
        panelMini.add(labelAbstractName, "1, 1");

        if (this.phase == GamePhase.IN_PROBLEM) {

            System.out.println("currpagenum: " + currPageNum);
            System.out.println(problem.getPages().get(currPageNum).widgets.get(0).content);
            panelInteriorPage.inflate2(problem.getPages().get(currPageNum));

            add(panelInteriorPage, "0, 0, 4, 0");

            if (currPageNum > 0)
                buttonPrev.setVisible(true);
            else
                buttonPrev.setVisible(false);
            buttonNext.setVisible(true);
        }
        if (this.phase == GamePhase.ANSWERING) {


            panelInteriorPage.inflate2(problem.getPages().get(currPageNum));

            add(panelInteriorPage, "0, 0, 4, 0");

            if (currPageNum > 0)
                buttonPrev.setVisible(true);
            else
                buttonPrev.setVisible(false);
            buttonNext.setVisible(true);

        }
        if (this.phase == GamePhase.SOLUTION) {

            panelInteriorPage.inflate2(problem.getPageSolution());

            add(panelInteriorPage, "0, 0, 4, 0");

            add(buttonPrev, "0, 1");
            if (!problem.getPagesExplanation().isEmpty())
                buttonNext.setVisible(true);
            else
                buttonNext.setVisible(false);
            buttonPrev.setVisible(true);

        }
        if (this.phase == GamePhase.POST_SOLUTION) {


            panelInteriorPage.inflate2(problem.getPagesExplanation().get(currPageNum));

            add(panelInteriorPage, "0, 0, 4, 0");

            if(currPageNum < problem.getPagesExplanation().size()-1)
                buttonNext.setVisible(true);
            else
                buttonNext.setVisible(false);

        }


        applyTexture(TextureHolder.getInstance());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                refresh(panelEdit.parent.isFullScreen);
            }
        });


        addMouseListener(this.mouseAdapter);
    }

    @Override
    public void exit() {
        removeAll();
        panelInteriorPage.removeAll();
        panelEdit.panels[0].removeAll();
        panelEdit.panels[1].removeAll();
        panelEdit.panels[2].removeAll();
        panelEdit.panels[1].setLayout(new TableLayout(layout2));
        removeMouseListener(this.mouseAdapter);
        setAllUnvisible();

    }

    @Override
    public void applyTexture(TextureHolder holder) {

        setBackground(holder.getColor("interior"));
        panelInteriorPage.setBackground(holder.getColor("interior"));

        //panel 0
        this.labelTitle.setForeground(holder.getColor("teamu_text"));
        this.labelProbName.setForeground(holder.getColor("teamu_text"));

        labelBack.setForeground(holder.getColor("teamu_score"));

        //panel 1
        for (int i = 0; i < 4; ++i) {
            toggleButtons[i].setBackground(holder.getColor("problem_preenabled"));
            toggleButtons[i].setBorder(FuncBox.getLineBorder(holder.getColor("teamu_border"), 3));
            toggleButtons[i].setForeground(holder.getColor("problem_preenabled_text"));
            UIManager.put("ToggleButton.select", holder.getColor("problem"));
            SwingUtilities.updateComponentTreeUI(toggleButtons[i]);
        }

        for (int i = 0; i < 4; ++i) {
            labelsAttr[i].setForeground(holder.getColor("teamu_score"));
            fieldsAttr[i].setBackground(holder.getColor("teamu"));
            fieldsAttr[i].setForeground(holder.getColor("teamu_text"));
            fieldsAttr[i].setCaretColor(holder.getColor("teamu_text"));
        }

        labelImage.setForeground(holder.getColor("teamu_text"));
        labelChooseFile.setForeground(holder.getColor("teamu_score"));


        //panel 2
        labelLayout.setForeground(holder.getColor("teamd_text"));
        labelApplyLayout.setForeground(holder.getColor("teamu_text"));

        for (int i = 0; i < 4; ++i) {
            labels[i].setForeground(holder.getColor("teamd_score"));
            fields[i].setForeground(holder.getColor("teamd_text"));
            fields[i].setBackground(holder.getColor("teamd"));
            fields[i].setCaretColor(holder.getColor("teamd_text"));
            fields[i].setBorder(null);
        }

        //banner
        labelAbstract.setForeground(holder.getColor("text"));
        labelAbstractName.setForeground(holder.getColor("text"));
        labelAbstractName.setBorder(FuncBox.getLineBorder(holder.getColor("text_light_2"), 3));


        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};


        buttonBack.setImages(images[0], images[1], images[2]);
        buttonPrev.setImages(images[0], images[1], images[2]);
        buttonNext.setImages(images[0], images[1], images[2]);

        buttonImage.setImages(images[0], images[1], images[2]);

        buttonApplyLayout.setImages(images[0], images[1], images[2]);
        panelInteriorPage.applyTexture();

    }

    @Override
    public void refresh(boolean isFullScreen) {

        this.labelTitle.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 45 : 30));
        this.labelProbName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));

        this.labelBack.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));


        for (int i = 0; i < 4; ++i) {
            toggleButtons[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 27 : 18));
        }
        for (int i = 0; i < 4; ++i) {
            labelsAttr[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 33 : 22));
            fieldsAttr[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 27 : 18));
        }

        labelImage.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 39 : 26));
        labelChooseFile.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 33 : 22));

        labelLayout.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));
        labelApplyLayout.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 36 : 24));

        for (int i = 0; i < 4; ++i) {
            labels[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 36 : 24));
            fields[i].setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 36 : 24));
        }

        int buttonX = panelEdit.parent.getContentPane().getWidth() / 2 / 8;
        int buttonY = panelEdit.parent.getContentPane().getHeight() / 2 / 8;

        int boxX = panelEdit.panels[0].getWidth();
        int boxY = panelEdit.panels[0].getHeight();

        int buttonXBox = boxX/ 4;
        int buttonYsBox = boxY / 10;

        buttonBack.resizeIconToSquare(buttonXBox, buttonYsBox * 3, 0.85);

        buttonPrev.resizeIconToSquare(buttonX, buttonY, 1);
        buttonNext.resizeIconToSquare(buttonX, buttonY, 1);
        labelAbstract.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 42 : 28));
        labelAbstractName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));


        buttonImage.resizeIconToSquare(boxX * 33 / 100, boxY * 24 / 100, 0.85);

        buttonApplyLayout.resizeIconToSquare(buttonXBox, buttonYsBox * 3, 0.85);

        if (Preference.autoScaleFontSize) {
            FontRef.scaleFont(this.labelTitle);
            FontRef.scaleFont(this.labelProbName);
            FontRef.scaleFont(this.labelBack);
            FontRef.scaleFont(labelImage);
            FontRef.scaleFont(labelChooseFile);
            FontRef.scaleFont(labelLayout);
            FontRef.scaleFont(labelApplyLayout);

            FontRef.scaleFont(labelAbstract);
            FontRef.scaleFont(labelAbstractName);

            for (int i = 0; i < 4; ++i) {
                FontRef.scaleFont(toggleButtons[i]);
                FontRef.scaleFont(labelsAttr[i]);
            }
        }
        panelInteriorPage.refreshRendering(isFullScreen);

    }

    void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    void setCurrPageNum(int pageNum) {
        this.currPageNum = pageNum;
    }

    void setProblem(Problem problem) {
        this.problem = problem;
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelEdit;
    }

    void setAllUnvisible() {
        //panel 2
        labelLayout.setVisible(false);
        buttonApplyLayout.setVisible(false);
        labelApplyLayout.setVisible(false);
        for (int i = 0; i < 4; ++i) {
            fields[i].setVisible(false);
            labels[i].setVisible(false);
        }
        //panel 1
        for (int i = 0; i < 4; ++i) {
            toggleButtons[i].setVisible(false);
            labelsAttr[i].setVisible(false);
            fieldsAttr[i].setVisible(false);
        }
        labelImage.setVisible(false);
        buttonImage.setVisible(false);
        labelChooseFile.setVisible(false);
    }
}
