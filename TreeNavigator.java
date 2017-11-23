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
/**-------------------------------Short Description--------------------------*/
/**
 *      This class contains the methods that are used in the class
 * DecisionTreeClassifier. Furthermore, this class holds the decision tree 
 * itself, where the TreeNode root is the root and has a cursor. The class, when
 * initialized has a root containing the string "Tree is empty".
 */
/**--------------------------------------------------------------------------*/

import java.util.Arrays;
import java.util.List;

public class TreeNavigator {

    /**
     * Initialized variables
     */
    private TreeNode root;
    private TreeNode cursor;
    //Used for testing purposes, to pass strings through program
    String searchHistory;

    /**
     * Constructs the class
     */
    public TreeNavigator() {
        String input = "Tree is empty";
        root = new TreeNode(input);
        cursor = root;
        searchHistory = "";
    }

    /**
     * Takes a string input and adds it to either the left child or right child 
     * depending on the yesNo int number.
     * 
     * @param input the string added to the new TreeNode
     * @param yesNo 0 sets the new node to the left child,1 to the right child.
     */
    public void insert(String input, int yesNo) {
        TreeNode newNode = new TreeNode(input);
        if (yesNo == 0) {
            cursor.setLeft(newNode);
        } else if (yesNo == 1) {
            cursor.setRight(newNode);
        } else {
            System.out.println("ERROR:Leaf side not givien, try again.");
        }
    }

    /**
     * Takes a string input and adds it based on the path given by the inPath
     * array. This method is primarily made for the buildTree method's input.
     * 
     * @param input the string to be added to the new node leaf
     * @param inPath the sequence of numbers determining the location of the new
     * leaf. 
     */
    public void insert(String input, String[] inPath) {
        TreeNode newNode = new TreeNode(input);
        boolean done = false;
        int pathRow = 1;
        if (root == null) {
            root = newNode;
        } else {
            cursor = root;
            while (!done) {
                if (Integer.parseInt(inPath[pathRow]) == 0) {
                    if (cursor.getLeft() == null) {
                        cursor.setLeft(newNode);
                        done = true;
                    } else {
                        cursor = cursor.getLeft();
                        pathRow++;
                    }
                } else if (Integer.parseInt(inPath[pathRow]) == 1) {
                    if (cursor.getRight() == null) {
                        cursor.setRight(newNode);
                        done = true;
                    } else {
                        cursor = cursor.getRight();
                        pathRow++;
                    }
                } else {
                    done = true;
                }
            }
        }
    }

    /**
     * Sets the left and right child (of cursor) to null, thereby detaching it 
     * from any grandchildren, and replaces the cursor's keywords.
     * 
     * @param input the string to replace the original keywords
     */
    public void toRemove(String input) {
        cursor.setLeft(null);
        cursor.setRight(null);
        editCursor(input);
    }

    /**
     * Takes a formatted string input and creates a new tree, overlapping any
     * pre-existing tree. 
     * 
     * @param treeFile the string input to be formated
     * @return returns the new TreeNavigator tree
     */
    public static TreeNavigator buildTree(String treeFile) {
        TreeNavigator tempTree = new TreeNavigator();
        String[] keyNodes;
        List<String> list = Arrays.asList(treeFile.split("[;\n]"));
        keyNodes = new String[list.size()];
        String[] keyPath = new String[keyNodes.length / 3 + 1];
        String[] keyData = new String[keyNodes.length / 3 + 1];
        String[] keyStatus = new String[keyNodes.length / 3 + 1];
        int j = 0, k = 0, l = 0, keyPathLength = 0;
        String[] keyNumPath = new String[keyPathLength];
        for (int i = 0; i < list.size(); i++) {
            keyNodes[i] = list.get(i);
            //For position
            if (i % 3 == 0) {
                keyPath[j] = keyNodes[i];
                keyNumPath = new String[keyPath[j].length() + 1];
                keyPathLength = keyPath[j].length();
                keyNumPath = keyPath[j].split("[-]");
                j++;
            //For data
            } else if (i % 3 == 1) {
                keyData[k] = keyNodes[i];
                if (keyPathLength == 1) {
                    TreeNode tempTreeNode = new TreeNode(keyData[k]);
                    tempTree.setRoot(tempTreeNode);
                } else {
                    tempTree.insert(keyData[k], keyNumPath);
                }
                k++;
            //Leaf or non-leaf
            } else if (i % 3 == 2) {
                keyStatus[l] = keyNodes[i];
                l++;
            } else {
                System.out.println("ERROR:odd number of inputs. ");
            }

        }
        tempTree.resetCursor();
        return tempTree;
    }

    /**
     * Takes the TreeNode and prints all children and grandchildren of the node.
     * This method is done recursively so this method calls itself. The method
     * is printed in-order fashion. 
     * 
     * @param curr the Node to get its children printed 
     */
    public void printInorder(TreeNode curr) {
        if (curr == null) {
            return;
        } else {
            printInorder(curr.getLeft());
            System.out.println(curr.returnKeywords());
            printInorder(curr.getRight());

        }
    }

    /**
     * Takes a string and traverses through the tree in order to find a matching
     * leaf based on whether the TreeNode contains the respective term.
     * 
     * @param text the text to be compared with the tree
     * @return the String which is determined to fit the inputted string
     */
    public String classify(String text) {
        TreeNode current = root;
        String[] searchText = text.split("(?=[,.])|\\s+");
        String build = "";
        boolean cont = true;
        while (cont == true) {
            boolean textFound = false;
            for (int i = 0; i < searchText.length; i++) {
                for (int j = 0; j < current.getKeywords().length; j++) {
                    if (searchText[i].toLowerCase().contains
                                    (current.getKeywords()[j].toLowerCase())) {
                        current = current.getRight();
                        build = current.toString();
                        textFound = true;
                        break;
                    }
                }
            }
            if (textFound == false) {
                current = current.getLeft();
                build = current.toString();
            }
            if (current.getLeft() == null && current.getRight() == null) {
                cont = false;
            }
        }
        return build;
    }

    /**
     * Takes a string and traverses through the tree in order to find a matching
     * leaf based on whether the TreeNode contains the respective term. And, it
     * returns the path of which decisions were made to reach the conclusion.
     * 
     * @param text the text to be compared with the tree
     * @return the String which is determined to fit the inputted string and
     * the path of the decision making process
     */
    public String getPath(String text) {
        TreeNode current = root;
        String[] searchText = text.split("(?=[,.])|\\s+");
        String build = "";
        boolean cont = true;
        while (cont == true) {
            boolean textFound = false;
            for (int i = 0; i < searchText.length; i++) {
                for (int j = 0; j < current.getKeywords().length; j++) {
                    if (searchText[i].toLowerCase().contains
                                    (current.getKeywords()[j].toLowerCase())) {
                        build += "IS " + current.toString();
                        current = current.getRight();
                        textFound = true;
                        break;
                    }
                }
            }
            if (textFound == false) {
                build += "NOT " + current.toString();
                current = current.getLeft();

            }
            if (current.getLeft() == null && current.getRight() == null) {
                cont = false;
                build += "DECISION:" + current.toString();
            }
        }
        return build;
    }

    /**
     * Resets the cursor to the root.
     */
    public void resetCursor() {
        cursor = root;
    }

    /**
     * Changes the cursor to the root, and makes it null.
     */
    public void deleteCursor() {
        resetCursor();
        cursor = null;
    }

    /**
     * Sets the root of the tree to a new TreeNode, thereby removing all 
     * children.
     * 
     * @param input the new node replacement
     */
    public void setRoot(TreeNode input) {
        root = input;
    }
 
    /**
     * Returns the root of the tree.
     * 
     * @return the root of the tree
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * Sets the cursor to the left child.
     */
    public void cursorLeft() {
        this.cursor = cursor.getLeft();
    }

    /**
     * Sets the cursor to the right child.
     */
    public void cursorRight() {
        this.cursor = cursor.getRight();
    }

    /**
     * returns the TreeNode of the cursor.
     * 
     * @return the cursor
     */
    public TreeNode getCursor() {
        return cursor;
    }

    /**
     * Changes the cursor's text only, without disturbing its children.
     * 
     * @param text the new keywords to be set
     */
    public void editCursor(String text) {
        if (cursor != null) {
            cursor.removeAllKeywords();
            cursor.setKeywords(text);
        } else {
            System.out.println("ERROR: cursor is null.");
        }
    }
}
