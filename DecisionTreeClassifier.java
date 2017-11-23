/**-------------------------------------Info---------------------------------*/
/**
 *	Name:				Date:			Current Version:
 *	Martin Barcelon		11-01-2016		1.0
 *
 *	Email:
 *	martin.barcelon@stonybrook.edu
 *
 *	Student ID:		
 *	110250249		
 */
/**-------------------------------Short Description--------------------------*//
/**
 *   This class serves as the the driver for the decision tree classifier. This
 * program classifies text based terms by building a binary tree. As an option,
 * a user can import a text (.txt) file and edit them in the program following 
 * the format: term path;term text; leaf/non-leaf, where the term path is marked
 * by 0's for "no" and 1's for "yes" separated by "-". Furthermore, the program
 * can read input text and come to a final term's conclusion and show how it
 * reached that conclusion. The tree can also be made from scratch and/or edited
 * through the Edit menu.
 *      Main Menu:
 *       I)Import a tree from a file
 *       E)Edit current tree
 *       C)Classify a Description
 *       P)Show decision path for a Description
 *       Q) Quit
 *      Edit Menu:
 *       E)Edit Keywords
 *       C)Add Children 
 *       D)Delete Children, and Make Leaf
 *       N)Cursor to No Child
 *       Y)Cursor to Yes Child
 *       R)Cursor to Root
 *       T)Print tree inorder
 *       M)Main Menu
 */
/**--------------------------------------------------------------------------*/

import java.util.Scanner;
import java.io.*;

public class DecisionTreeClassifier {

    public static void main(String args[]) {

        System.out.println("Welcome to the DecisionTree Classifier");

        //Initializes the objects
        Scanner scan = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        TreeNavigator dTree = new TreeNavigator();

        //Sets the condition for the do-while loop
        boolean exit = false;

        /**
         * This is the start of the do-while loop which will continuously run
         * until a readable, file is found.
         */
        do {
            System.out.println("Menu:");
            System.out.println("\tI)Import a tree from a file");
            System.out.println("\tE)Edit current tree");
            System.out.println("\tC)Classify a Description");
            System.out.println("\tP)Show decision path for a Description");
            System.out.println("\tQ)Quit");

            System.out.print("Please select an option: ");
            String menuInput = scan.nextLine();

            switch (menuInput.toUpperCase()) {
                //Import a tree from a file
                case "I":
                    boolean fileCheck = false;
                    do {
                        System.out.print("Please enter a filename: ");
                        String fileName = scan.nextLine();

                        /**
                         * Use as an easy way to test files without typing the
                         * name repeatedly. TEST fileName = "testText.txt";
                         */
                        if (fileName == "") {
                            fileCheck = true;
                        } else if (fileName == null) {
                            fileCheck = true;
                        }

                        BufferedReader bufferedRead = null;
                        try {
                            /**
                             * Here is the directory of the file read path. If
                             * the path needs to be changed it needs to be done
                             * here.
                             */
                            bufferedRead = new BufferedReader
                                                    (new FileReader(fileName));
                            String line;
                            while ((line = bufferedRead.readLine()) != null) {
                                if (sb.length() > 0) {
                                    sb.append("\n");
                                }
                                sb.append(line);
                            }
                            fileCheck = false;
                        } catch (IOException e) {
                            System.out.println
                                            ("File not found please try again");
                            fileCheck = true;
                        }
                    } while (fileCheck);
                    System.out.println("Tree Loaded.");
                    dTree.resetCursor();
                    dTree = dTree.buildTree(sb.toString());
                    break;
                //Edit current tree
                case "E":
                    boolean quitEditing = false;
                    do {
                        System.out.println("Current node keywords: "
                                + dTree.getCursor().returnKeywords());
                        
                        System.out.println("\tE)Edit Keywords");
                        System.out.println("\tC)Add Children");
                        System.out.println
                                        ("\tD)Delete Children, and Make Leaf");
                        System.out.println("\tN)Cursor to No Child");
                        System.out.println("\tY)Cursor to Yes Child");
                        System.out.println("\tR)Cursor to Root");
                        System.out.println("\tT)Print Tree Inorder");
                        System.out.println("\tM)Main Menu");

                        System.out.print("Please select an Edit option: ");
                        String editInput = scan.nextLine();

                        switch (editInput.toUpperCase()) {
                            //Edit keywords
                            case "E":
                                System.out.print("Please enter keywords for "
                                        + "this node, seperated by a comma:");
                                String userInputE = scan.nextLine();
                                dTree.editCursor(userInputE);
                                break;
                            //Add children
                            case "C":
                                System.out.print("Please enter terminal text "
                                        + "for the no leaf: ");
                                String leftUserInput = scan.nextLine();
                                System.out.print("Please enter terminal text "
                                        + "for the yes leaf: ");
                                String rightUserInput = scan.nextLine();
                                dTree.insert(leftUserInput, 0);
                                dTree.insert(rightUserInput, 1);
                                System.out.println("Children are: yes - '"
                                        + rightUserInput + "' and no - '"
                                        + leftUserInput + "'.");
                                break;
                            //Add children
                            case "D":
                                System.out.print("Please enter a terminal "
                                        + "text for this node: ");
                                String userInputD = scan.nextLine();
                                dTree.toRemove(userInputD);
                                break;
                            //Moves cursor to no child    
                            case "N":
                                dTree.cursorLeft();
                                System.out.println("Cursor Moved.Cursor is at "
                                        + "leaf, message is "
                                        + dTree.getCursor().returnKeywords());
                                break;
                            //Moves cursor to yes child    
                            case "Y":
                                dTree.cursorRight();
                                System.out.println("Cursor Moved.Cursor is at "
                                        + "leaf, message is "
                                        + dTree.getCursor().returnKeywords());
                                break;
                            //Moves cursor to root    
                            case "R":
                                dTree.resetCursor();
                                System.out.println("Cursor moved. Cursor is at"
                                        + "root.");
                                break;
                            //Prints tree inorder starting at root    
                            case "T":
                                dTree.resetCursor();
                                dTree.printInorder(dTree.getCursor());
                                break;
                            //Moves back to main menu
                            case "M":
                                quitEditing = true;
                                break;
                            //If none are entered, promt to try again    
                            default:
                                System.out.println("Input not recognized, try "
                                        + "again");
                                break;
                        }
                    } while (quitEditing == false);
                    break;
                //Classify a Description
                case "C":
                    System.out.print("Please enter your text:");
                    String userInputC = scan.nextLine();
                    System.out.println(dTree.classify(userInputC));
                    break;
                //Show decision path for a Description
                case "P":
                    System.out.print("Please enter your text:");
                    String userInputP = scan.nextLine();
                    System.out.println(dTree.getPath(userInputP));
                    break;
                //Quit
                case "Q":
                    exit = true;
                    break;
                default:
                    System.out.println("Input not recognized, try again");
                    break;
            }
        } while (exit == false);
        System.out.println("Have a good day!");
    }
}
