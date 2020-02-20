package com.company;

import java.awt.*;
import java.util.Random;
import javax.swing.JFrame;

public class Drawing extends Canvas {
    public static void start (String[] command) {
        for (int i = 1; i < command.length; i++) {
            if (command[i].charAt(0)=='t') {
                Main.timeToWait = Integer.parseInt(command[i].substring(command[i].indexOf(":")+1));
            }
            else if (command[i].charAt(0)=='i') {
                Main.totalIterations = Integer.parseInt(command[i].substring(command[i].indexOf(":")+1));
            }
        }
        JFrame frame = new JFrame("My Drawing");
        Canvas canvas = new Drawing();
        canvas.setSize(1000,1000);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        double[] coordinatesAndColor = {0.0, 0.0, Main.generatorValues[0][7]};

        Color color = new Color((int)coordinatesAndColor[2]);
        g.setColor(color);

        for (int i = 0; true; i++) {
            int x = (int)(coordinatesAndColor[0]*Main.drawingValues[0])+Main.drawingValues[1],
                    y = Main.drawingValues[3]-(int)(coordinatesAndColor[1]*Main.drawingValues[2]);
            System.out.println(x+"%n"+y);
            g.drawLine(x,y,x,y);
            iterateCoordinates(coordinatesAndColor);
            color = new Color((int)coordinatesAndColor[2]);
            g.setColor(color);
            System.out.println(coordinatesAndColor[0]+","+coordinatesAndColor[1]);//

            if (Main.totalIterations>0&&Main.totalIterations<i) {
                break;
            }

            try {
                Thread.sleep(Main.timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void iterateCoordinates (double[] coordinatesAndColor) {
        Random r = new Random();
        double percentageOfFunction = r.nextDouble();
        int function = 3;

        if (percentageOfFunction<=Main.generatorValues[0][6]) {
            function = 0;
        }
        else if (percentageOfFunction<=Main.generatorValues[1][6]+Main.generatorValues[0][6]) {
            function = 1;
        }
        else if (percentageOfFunction<=Main.generatorValues[2][6]+Main.generatorValues[1][6]+Main.generatorValues[0][6]) {
            function = 2;
        }

        double originalX = coordinatesAndColor[0], originalY = coordinatesAndColor[1];

        coordinatesAndColor[0] = originalX*Main.generatorValues[function][0]+
                originalY*Main.generatorValues[function][1]+
                Main.generatorValues[function][4];
        coordinatesAndColor[1] = originalX*Main.generatorValues[function][2]+
                originalY*Main.generatorValues[function][3]+
                Main.generatorValues[function][5];

        coordinatesAndColor[2] = Main.generatorValues[function][7];
        System.out.println("gggggg"+function);//
    }
}
