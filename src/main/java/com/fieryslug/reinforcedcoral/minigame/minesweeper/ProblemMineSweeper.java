package com.fieryslug.reinforcedcoral.minigame.minesweeper;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelProblem;

public class ProblemMineSweeper extends Problem {

    private PanelMineSweeper panelMineSweeper;

    public ProblemMineSweeper(String name) {
        super(name, 0);
        this.panelMineSweeper = new PanelMineSweeper(this, 7, 24, 34);
    }

    @Override
    public boolean onClick(PanelGame panelGame) {

        PanelProblem panelProblem = new PanelProblem (panelGame.parent);
        panelGame.setPhase(GamePhase.SPECIAL);
        this.panelMineSweeper = new PanelMineSweeper(this, 3, 3, 1);
        this.panelMineSweeper.bindPanelGame(panelGame);

        panelGame.parent.switchPanel(panelGame, panelGame);
        panelGame.add(this.panelMineSweeper, "0, 1, 5, 4");
        panelGame.currentMinigamePanel = this.panelMineSweeper;

        this.panelMineSweeper.start();
        return true;
    }
}

