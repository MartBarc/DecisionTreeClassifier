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
 *      This object is a node, linking a left node and right node while 
 * retaining an array of keywords as data. When linked, this node forms a binary
 * tree, for the TreeNavigator class. This class contains the usual set, get
 * methods for the left and right nodes.
 */
/**--------------------------------------------------------------------------*/

import java.util.Arrays;
import java.util.List;

public class TreeNode {

    /**
     * All variables initialized.
     */
    private String[] keywords;
    private TreeNode left;
    private TreeNode right;

    /**
     * Constructs the object.
     * 
     * @param input the string to be added to the new node.
     */
    public TreeNode(String input) {
        List<String> list = Arrays.asList(input.split("[, .]"));
        keywords = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            keywords[i] = list.get(i);
        }
        left = null;
        right = null;
    }

    /**
     * Returns a formatted string of the whole keyword array .
     * 
     * @return the formatted string
     */
    public String returnKeywords() {
        String AllKeywords = "|";
        int i;
        for (i = 0; i < keywords.length; i++) {
            AllKeywords += keywords[i] + "|";
        }
        return AllKeywords;
    }

    //Returns the keywords
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * Sets the keywords. When entered, the string gets chopped into words only,
     * stored in a array. The string is chopped based on " " (space), "," 
     * (comma), and "." (period).
     * 
     * @param input the string to be put in the array
     */
    public void setKeywords(String input) {
        List<String> list = Arrays.asList(input.split("[, .]"));
        keywords = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            keywords[i] = list.get(i);
        }
    }

    /**
     * Removes the last keyword in the node.
     */
    public void removeLastKeyword() {
        keywords[keywords.length] = null;
    }

    /**
     * Removes all the keywords in the node by setting them to null.
     */
    public void removeAllKeywords() {
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = null;
        }
    }

    public void setLeft(TreeNode newNode) {
        this.left = newNode;
    }

    public TreeNode getLeft() {
        return (this.left);
    }

    public void setRight(TreeNode newNode) {
        this.right = newNode;
    }

    public TreeNode getRight() {
        return (this.right);
    }

    /**
     * Checks the node to see if the node itself is a leaf. A leaf, must have
     * children that are null.
     * 
     * @return true if the node is a leaf, false if any children are not null
     */
    public Boolean isLeaf() {
        if (this.left == null && this.right == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Converts the keywords to an string separated by space. 
     * 
     * @return formatted string
     */
    public String toString() {
        String AllKeywords = "";
        int i;
        for (i = 0; i < keywords.length; i++) {
            AllKeywords += keywords[i] + " ";
        }
        return AllKeywords;
    }

}
