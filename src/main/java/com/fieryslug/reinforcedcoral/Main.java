package com.fieryslug.reinforcedcoral;

import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.fieryslug.reinforcedcoral.core.WorkTable;

import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;


import com.fieryslug.reinforcedcoral.util.AePlayWave;
import com.fieryslug.reinforcedcoral.util.DataLoader;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.web.ServerThread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.InputStream;


import javax.swing.*;

//import layout.TableLayout;
import info.clearthought.layout.TableLayout;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) {

        start();
        /*
        ProblemSet set = new ProblemSet("oblivion1");
        set.acquireProblemSet();
        set.loadResources();

        set.dumpProblemSet(".tmp/oblivion1_tmp", true);

        ProblemSet set1 = new ProblemSet("test");
        set1.loadProblemSet(".tmp/oblivion1_tmp");
        set1.loadResources();


        set1.saveProblemSet("oblivion1_clone", true);

         */

    }

    public static void start() {
        System.out.println("fieryslug is back\n");

        DataLoader loader = DataLoader.getInstance();



        loader.checkFiles();
        loader.deleteTempFiles();

        WorkTable.getGame0().getProblemSet().saveProblemSet("oblivion1", false);
        WorkTable.getGame1().getProblemSet().saveProblemSet("oblivion2", false);

        loader.updateProblemSetIndex();

        loader.loadAllProblemSets();
        System.out.println(loader.getProblemSets());


        Game game;

        game = new Game(loader.getProblemSets().get(0), FuncBox.generateTeams(Preference.teams));



        FrameCoral frame = new FrameCoral(game);


        /*
        Thread serverthread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
                    server.createContext("/test", new RequestHandler(frame));
                    server.start();
                    System.out.println("Server started");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        */
        //serverthread.start();
        ServerThread serverThread = new ServerThread(frame);
        serverThread.start();

    }

    public static void test1() {
        JFrame frame = new JFrame("test");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        FuncBox.addKeyBinding(frame.getRootPane(), "F11", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                        if (frame.isUndecorated()) {
                            device.setFullScreenWindow(null);
                            frame.setUndecorated(false);

                        } else {
                            frame.setUndecorated(true);
                            device.setFullScreenWindow(frame);

                        }

                        frame.setVisible(true);

                        //frame.refresh();

                        frame.repaint();
                    }
                }
        );

        JPanel panel = new JPanel();
        double[][] size = {{0.5, 0.5}, {0.5, 0.5}};
        panel.setLayout(new TableLayout(size));

        JButton button = new JButton();
        button.setBackground(Reference.DARKRED);

        panel.add(button, "0, 0");
        frame.add(panel);
    }

}
