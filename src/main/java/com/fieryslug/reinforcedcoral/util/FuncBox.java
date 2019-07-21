package com.fieryslug.reinforcedcoral.util;

import com.fieryslug.reinforcedcoral.widget.ButtonCoral;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FuncBox {

    public static Map<Pair<Image, Dimension>, Image> imageCache = new HashMap<>();

    public static JLabel blankLabel(int width, int height) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    public static void addKeyBinding(JComponent c, String key, final Action action) {

        c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        c.getActionMap().put(key, action);
        c.setFocusable(true);

    }

    public static String readFile(String urlstr) {

        URL url = FuncBox.class.getResource(urlstr);

        String res = "";
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String i;
            while ((i = read.readLine()) != null)
                res = res + i + "\n";
            read.close();
        } catch (Exception e) {

            e.printStackTrace();

        }

        return res;

    }

    public static Object readJson() {

        return null;

    }

    public static Image resizeImage(Image image, int x, int y) {
        BufferedImage bimage = MediaRef.toBufferedImage(image);
        return bimage.getScaledInstance(x, y, Image.SCALE_SMOOTH);

    }

    public static Image resizeImagePreservingRatio(Image image, int x, int y) {

        Pair<Image, Dimension> information = new Pair<>(image, new Dimension(x, y));

        Image imageNew = imageCache.get(information);
        if(imageNew != null) return imageNew;

        BufferedImage bimage = MediaRef.toBufferedImage(image);
        int height = bimage.getHeight();
        int width = bimage.getWidth();

        double scalex = (double) x / width;
        double scaley = (double) y / height;
        double scale = Math.min(scalex, scaley);

        imageNew =  bimage.getScaledInstance((int) (scale * width), (int) (scale * height), Image.SCALE_SMOOTH);
        if(imageNew != null) imageCache.put(new Pair<>(image, new Dimension(x, y)), imageNew);
        return imageNew;

    }

    @Deprecated
    public static File fileFromPath(String path) {

        try {
            return new File(FuncBox.class.getResource(path).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream inputStreamFromPath(String path) {
        return FuncBox.class.getResourceAsStream(path);
    }

    public static void listAllFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        Font[] allFonts = ge.getAllFonts();

        for (Font font : allFonts) {
            System.out.println(font.getFontName(Locale.US));
        }
    }


}
