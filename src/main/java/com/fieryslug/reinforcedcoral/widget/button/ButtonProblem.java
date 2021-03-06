package com.fieryslug.reinforcedcoral.widget.button;

import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.Preference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class ButtonProblem extends ButtonCoral {

    private boolean enabled;
    public int state; //0:normal, 1:deactivated, -1:to be activated
    protected boolean selected;
    public Image imageDisabled;
    public Image imageSelected;
    public Image imageDisabledSelected;
    public Image imagePreenabled;
    public Image imagePreenabledSelected;
    private ImageIcon iconDisabled;
    private ImageIcon iconSelected;
    private ImageIcon iconDisabledSelected;
    private ImageIcon iconPreeanbled;
    private ImageIcon iconPreenabledSelected;

    private Timer timerAnimation;
    private TimerTask taskAnimation;
    private int animationNumber;

    public JLabel label;

    public ButtonProblem(Image imageDefault, Image imageHover, Image imagePress, boolean irregularShape) {

        super(imageDefault, imageHover, imagePress, irregularShape);
        this.enabled = true;
        this.state = 0;
        this.taskAnimation = new TimerTask() {
            @Override
            public void run() {
                if (animationNumber < 9) {
                    if (animationNumber % 2 == 0) {
                        //setIcon(iconDefault);
                        toDefault();
                    } else {
                        toPreenabled();
                    }
                    animationNumber += 1;
                }
                else
                    timerAnimation.purge();
            }
        };

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                boolean isUp = mouseWheelEvent.getPreciseWheelRotation() < 0;
                if (mouseWheelEvent.isControlDown()) {
                    if (!isUp) {
                        int state1 = Math.max(-1, state - 1);
                        setState(state1);
                    }
                    else {
                        int state1 = Math.min(1, state + 1);
                        setState(state1);
                    }
                } else if (mouseWheelEvent.isShiftDown()) {
                    Font font = label.getFont();
                    int size = (int)(font.getSize() / Preference.fontSizeMultiplier);
                    if (isUp) {
                        size += 5;
                        label.setFont(FontRef.getFont(font.getFontName(), font.getStyle(), size));
                    } else {
                        size = Math.max(5, size - 5);
                        label.setFont(FontRef.getFont(font.getFontName(), font.getStyle(), size));

                    }
                }


            }
        });


    }


    public void setImageDisabled(Image image) {

        this.imageDisabled = image;
        this.iconDisabled = new ImageIcon(this.imageDisabled);

    }

    public void setImageSelected(Image image) {

        this.imageSelected = image;
        this.iconSelected = new ImageIcon(this.imageSelected);

    }

    public void setImageDisabledSelected(Image image) {
        this.imageDisabledSelected = image;
        this.iconDisabledSelected = new ImageIcon(this.imageDisabledSelected);
    }

    public void setImagePreenabled(Image image) {
        this.imagePreenabled = image;
        this.iconPreeanbled = new ImageIcon(this.imagePreenabled);
    }

    public void setImagePreenabledSelected(Image image) {
        this.imagePreenabledSelected = image;
        this.iconPreenabledSelected = new ImageIcon(this.imagePreenabledSelected);
    }

    public void toPreenabled() {
        setIcon(this.iconPreeanbled);
    }

    public void setState(int state) {

        this.state = state;
        refreshState();

    }
    public void setButtonSelected(boolean b) {

        this.selected = b;
        refreshState();
    }

    protected void refreshState() {

        if(this.state == 0) {
            setEnabled(true);
            if(!Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                addMouseListener(this.mouseListener);
            if(this.selected) {
                setIcon(this.iconSelected);
            }
            else {
                setIcon(this.iconDefault);
            }

        }
        if(this.state == 1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {
                setDisabledIcon(this.iconDisabledSelected);
            }
            else {
                setDisabledIcon(this.iconDisabled);
            }
        }
        if(this.state == -1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {
                setDisabledIcon(this.iconPreenabledSelected);
            }
            else {
                setDisabledIcon(this.iconPreeanbled);
            }

        }
    }

    @Override
    public void resizeImageForIcons(int x, int y) {

        super.resizeImageForIcons(x, y);
        this.iconDisabled = ButtonCoral.resizeImage(this.imageDisabled, x, y);
        this.iconSelected = ButtonCoral.resizeImage(this.imageSelected, x, y);
        this.iconDisabledSelected = ButtonCoral.resizeImage(this.imageDisabledSelected, x, y);
        this.iconPreeanbled = ButtonCoral.resizeImage(this.imagePreenabled, x, y);
        this.iconPreenabledSelected = ButtonCoral.resizeImage(this.imagePreenabledSelected, x, y);

        refreshState();
    }

    public void playAnimation() {
        this.animationNumber = 0;
        this.timerAnimation = new Timer();
        try {
            this.timerAnimation.scheduleAtFixedRate(this.taskAnimation, 500, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
