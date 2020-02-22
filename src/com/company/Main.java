package com.company;

import java.io.*;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static double[][] generatorValues;//the four functions and all values about them
    static int[] drawingValues;//the multiplying of and adding to values
    static List<String> printHelp;
    static int timeToWait;
    static int totalIterations;

    public static void main(String[] args) throws IOException {
        giveDefaultValues();
        printBeginning();
        while (true) {
            getCommand();
        }
    }
    
    public static void giveDefaultValues () {
        generatorValues = new double[4][8];

        //setting default values - most are 0 except the percantage, which is 25%, and the color, which is green
        for (int i = 0; i < generatorValues.length; i++) {
            for (int j = 0; j < 6; j++) {
                generatorValues[i][j] = 0.0;
            }

            generatorValues[i][6] = 0.25;
            generatorValues[i][7] = 32768;
        }

        drawingValues = new int[]{100, 200, 45, 500};//sets values that are generally good for generating a reasonably-sized Barnsley Fern

        printHelp = new ArrayList<String>();

        printHelp.add("help    -    get this message");
        printHelp.add("stop    -    stop program");
        printHelp.add("getFunctionMeanings    -    see what each function generates");
        printHelp.add("getValueMeanings    -    see what each value means");
        printHelp.add("valueCombinations    -    see all saved Barnsley Ferns and what their values are");
        printHelp.add("setValueCombination {savedBarnsleyFern}    -    set the values of the Barnsley Fern to be generated " +
                "to those of a saved Barnsley Fern");
        printHelp.add("create {nameOfNewFern} {y/n}    -    save the current values as a new Barnsley Fern " +
                "(with no spaces in the name) where y/n is if you want to save the colors");
        printHelp.add("remove {nameOfFern}    -    remove a saved Barnsley Fern");
        printHelp.add("getValues {function}    -    see the values of the Barnsley Fern to be generated " +
                "(a,b,c,d,e,f,p,color) - if you input a value from 1 to 4 as function, " +
                "you will only see the values of the function you chose");
        printHelp.add("change {function} {value}    -    change a specific value (a,b,c,d,e,f,p,color,widthOfX," +
                "additionToX,widthOfY,subtractionFromY) of a specific function (1,2,3,4 or 0 if you are " +
                "changing width of or addition to the X or Y coordinate)");
        printHelp.add("start    -    generate the Barnsley Fern " +
                "through a number of iterations (if <= 0, there will be infinite iterations) while waiting certain time in milliseconds >= 0 between iterations " +
                "- both are optional and are added to start in any direction in the following way: " +
                "t:{timeToWaitBetweenIterations} " +
                "i:{numberOfIterations}   " +
                "-   e.g. start i:223333");

        timeToWait = 0;
        totalIterations = 500000;
    }

    public static void printBeginning () {
        System.out.printf("Welcome to Barnsley Fern Generator.%nType help to get help.%n");
    }
    
    public static void getCommand () throws IOException {
        String[] command = scanner.nextLine().split(" ");
        
        switch (command[0]) {
            case "help":
                commandHelp();
                break;
            case "getFunctionMeanings":
                getFunctionMeanings();
                break;
            case "getValueMeanings":
                getValueMeanings();
                break;
            case "getValues":
                getValues(command);
                break;
            case "change":
                change(command);
                break;
            case "start":
                Drawing.start(command);
                break;
            case "valueCombinations":
                valueCombinations();
                break;
            case "setValueCombination":
                setValueCombination(command);
                break;
            case "create":
                createValueCombination(command);
                break;
            case "remove":
                removeValueCombination(command);
                break;
            case "stop":
                stop();
                break;
            default:
                invalidCommand();
                break;
        }
    }
    
    public static void commandHelp () {
        for (int i = 0; i < printHelp.size(); i++) {
            System.out.println(printHelp.get(i));
        }
    }

    public static void invalidCommand () {
        System.out.println("Invalid command");
    }

    public static void getFunctionMeanings () {
        System.out.println("Function 1 generates the stem");
        System.out.println("Function 2 generates the successively smaller leaflets");
        System.out.println("Function 3 generates the largest left-hand leaflet");
        System.out.println("Function 4 generates the largest right-hand leaflet");
    }

    public static void getValueMeanings () {
        System.out.println("x(n+1)=a*x(n)+b*y(n)+e");
        System.out.println("y(n+1)=c*x(n)+d*y(n)+f");
        System.out.println("p - chance of function happening");
        System.out.println("color - hexadecimal value from 1 to ffffff that a specific function (1,2,3,4) " +
                "draws");
        System.out.println("widthOfX - by what number the x coordinate will be multiplied " +
                "when drawing(default is 100)");
        System.out.println("additionToX - what number will be added to the x coordinate " +
                "when drawing(default is 200)");
        System.out.println("widthOfY - by what number the y coordinate will be multiplied " +
                "when drawing(default is 100)");
        System.out.println("subtractionFromY - what number the y coordinate will be subtracted from " +
                "when drawing(default is 600)");
    }

    public static void getValues (String[] command) {
        if (command.length==1) {//in case there is no function specified
            for (int i = 0; i < generatorValues.length; i++) {
                for (int j = 0; j < 7; j++) {
                    System.out.print(generatorValues[i][j]+" ");
                }
                System.out.println((int)generatorValues[i][7]);
            }
        }
        else {//in case a value is specified
            int indexOfFunction = Integer.parseInt(command[1])-1;

            for (int i = 0; i < 7; i++) {
                System.out.print(generatorValues[indexOfFunction][i]+" ");
            }
            System.out.println();
        }
    }

    public static void change (String[] command) {
        int i = Integer.parseInt(command[1])-1;
        int j = -1;

        switch (command[2]) {//converts the value to be changed to the correct index
            case "a":
                j = 0;
                break;
            case "b":
                j = 1;
                break;
            case "c":
                j = 2;
                break;
            case "d":
                j = 3;
                break;
            case "e":
                j = 4;
                break;
            case "f":
                j = 5;
                break;
            case "p":
                j = 6;
                break;
            case "color":
                j = 7;
                break;
            case "widthOfX":
                j = 8;
                break;
            case "additionToX":
                j = 9;
                break;
            case "widthOfY":
                j = 10;
                break;
            case "subtractionFromY":
                j = 11;
                break;
        }

        System.out.println("Write value of "+command[2]);

        if (j<8) {//if the value to be changed is part of the generator values
            generatorValues[i][j] = Double.parseDouble(scanner.nextLine());
        }
        else {//if the value to be changed is part of the drawing values
            drawingValues[j-8] = Integer.parseInt(scanner.nextLine());
        }
    }

    public static void valueCombinations () {
        try {//prints everything from the BarnsleyFerns file (all Ferns)
            FileReader reader = new FileReader("BarnsleyFerns");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();

            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setValueCombination(String[] command) {
        String nameOfValueCombination = command[1];

        try {
            FileReader reader = new FileReader("BarnsleyFerns");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();

            while (line != null) {//reads lines until it reaches the name of the Fern we are looking for
                if (line.equals(nameOfValueCombination)) {//if the Fern is found, we skip its name
                    line = bufferedReader.readLine();
                    break;
                }
                line = bufferedReader.readLine();
            }

            if (line!=null) {
                for (int i = 0; i < 4; i++) {//copies the values of each line
                    double[] valuesToPaste = Arrays.
                            stream(line.
                                    split(" ")).
                            mapToDouble(Double::
                                    parseDouble).
                            toArray();
                    for (int j = 0; j < 7; j++) {//setting the current values to those of the current value line
                        generatorValues[i][j] = valuesToPaste[j];
                    }

                    line = bufferedReader.readLine();
                }

                if (line!=null) {
                    if (line.equals("colorIsSpecified")) {//if color is specified, there is a line that says it right after the values of the functions
                        line = bufferedReader.readLine();

                        int[] colors = Arrays.
                                stream(line.
                                        split(" ")).
                                mapToInt(Integer::
                                        parseInt).
                                toArray();
                        for (int i = 0; i < 4; i++) {//sets the current color to that of the read line
                            generatorValues[i][7] = colors[i];
                        }
                    }
                }
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createValueCombination (String[] command) {
        try {
            FileWriter writer = new FileWriter("BarnsleyFerns", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(command[1]);//writes the chosen name of the Barnsley Fern
            bufferedWriter.newLine();

            for (int i = 0; i < 4; i++) {//writes the values of the functions with eachfunction being its own line
                for (int j = 0; j < generatorValues[i].length-2; j++) {
                    bufferedWriter.write(generatorValues[i][j]+" ");
                }

                bufferedWriter.write(String.valueOf(generatorValues[i][6]));//the last value is written like that so there is no space and therefore no need to remove an empty element when reading the array
                bufferedWriter.newLine();
            }

            if (command.length==3 ) {
                if (command[2].equals("y")) {//if the color should be specified, the colors of the four functions are written on one line
                    bufferedWriter.write("colorIsSpecified");
                    bufferedWriter.newLine();
                    for (int i = 0; i < 3; i++) {
                        bufferedWriter.write(generatorValues[i][7] + " ");
                    }

                    bufferedWriter.write(String.valueOf(generatorValues[3][7]));//the last value is written like that so there is no space and therefore no need to remove an empty element when reading the array
                    bufferedWriter.newLine();
                }
            }

            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeValueCombination (String[] command) {
        try {
            FileWriter writer = new FileWriter("temporary", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            FileReader reader = new FileReader("BarnsleyFerns");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            String nameOfValueCombinationToRemove = command[1];

            while (line!=null) {
                if (line.equals(nameOfValueCombinationToRemove)) {//if we find the name of the Barnsley Fern to be destroyed, we skip copying the next line
                    bufferedReader.readLine();
                    break;
                }

                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = bufferedReader.readLine();
            }

            if (line!=null) {
                for (int i = 0; i < 3; i++) {//skipping the three remaining function values
                    bufferedReader.readLine();
                }

                if (bufferedReader.readLine().equals("colorIsSpecified")) {//the line after the values is skipped and,
                    //if it is colorIsSpecified, the next two (the one with the values of color and the empty line);
                    //if the next line is not colorIsSpecified(then it is an empty line), it's skipped in the if itsef
                    for (int i = 0; i < 2; i++) {
                        bufferedReader.readLine();
                    }
                }
            }

            line = bufferedReader.readLine();
            while (line!=null) {//the remaining lines are copied
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                line = bufferedReader.readLine();
            }

            bufferedWriter.close();

            //the temporary files becomes the new BarnsleyFerns file
            File fileToDelete = new File("BarnsleyFerns");
            File fileToRename = new File("temporary");

            fileToDelete.delete();
            fileToRename.renameTo(new File("BarnsleyFerns"));

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop () {
        System.exit(0);
    }
}
