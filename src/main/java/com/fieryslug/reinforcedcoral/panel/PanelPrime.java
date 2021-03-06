package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.TextureHolder;

import javax.swing.*;
import java.awt.*;

public abstract class PanelPrime extends JPanel {

    public FrameCoral parent;

    public PanelPrime(FrameCoral parent) {

        this.parent = parent;

        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER));

    }

    protected void initialize() {

    }

    public void enter() {

    }

    public void exit() {

    }


    public void refresh() {

    }

    public void react(Team team, ControlKey key) {

    }

    public void applyTexture(TextureHolder holder) {

    }
}
