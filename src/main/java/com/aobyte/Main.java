package com.aobyte;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Parser parser = null;
        Selector selector = null;
        Scanner sc = new Scanner(System.in);

        System.out.println(".---------------------------------------------------------.");
        System.out.println("|                          OPTIONS                        |");
        System.out.println("| [F] - file path. [S] - selector. [D] - drow. [Q] - quit |");
        System.out.println(" --------------------------------------------------------- ");
        System.out.println(" \nPlease enter the command: ");
        while (true) {
            String command = sc.nextLine();
            switch (command) {
                case "F":
                    System.out.println("Please enter file absolute path:");
                    String path = sc.nextLine();
                    System.out.println("path is: " + path);

                    File file = new File(path);
                    if (file.exists() && file.isFile()) {
                        parser = new Parser(file);
                    }
                    break;
                case "S":
                    System.out.println("Please enter selector:");
                    String query = sc.nextLine();
                    System.out.println("tagToSelect is: " + query);

                    selector = new Selector(query);
                    break;
                case "D":

                    Printer printer = new Printer();
                    if (parser != null && selector != null) {
                        List<Tag> htmlTags = parser.getHtmlTags();
                        List<Tag> queriedTags = selector.getSelectorTag();
                        ArrayList<Integer> matchingTagIds = parser.getMatchingTagIds(queriedTags);

                        List<Tag> tagsToPrint = parser.getTagsToPrint(matchingTagIds);
                            //System.out.println("tagsToPrint = " + tagsToPrint);

                        printer.printSelectedTags(tagsToPrint, htmlTags);
                        System.out.println();
                    }else{
                        System.out.println("Ooops... (incorrect filPath / query)");
                    }
                    break;
                case "Q":
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Command not found!");
                    break;
            }
        }
    }


}




