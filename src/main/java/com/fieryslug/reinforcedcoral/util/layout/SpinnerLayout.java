package com.fieryslug.reinforcedcoral.util.layout;

import com.fieryslug.reinforcedcoral.util.FuncBox;

import java.awt.BorderLayout;
import java.awt.Component;

public class SpinnerLayout extends BorderLayout {
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {

        if("Editor".equals(constraints)) {
            constraints = "Center";
        } else if("Next".equals(constraints)) {
            constraints = "East";
        } else if("Previous".equals(constraints)) {
            constraints = "West";
        } else if ("Painter".equals(constraints)) {
            constraints = "Center";
        } else {
            constraints = "";
            System.out.println("[SpinnerLayout]problem detected while adding " + comp + " to spinner layout");
        }
        super.addLayoutComponent(comp, constraints);
    }
}