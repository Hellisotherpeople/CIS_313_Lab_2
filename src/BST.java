import java.util.Scanner;

/* CIS 313: Lab 2 
 * Author = Allen Roush 
 * NOTE: I have implemented the min method. I have NOT implemented error handling for input (wasn't required) 
 * Other Note: Print statements seem to not be always going off with the delete method, it IS STILL DELETING THE NODES (check with traverse method).
 **/




public class BST
{
    public Node root;

    public BST()
    {
        //constructor method 
        this.root = null;
    }

    /*
     * Helper methods 
     */



    public Node getSuccessor(Node deletedNode)
    {
        // Get's the leftmost node from the right subtree, used in the delete method. 
        Node successsor = null;
        Node successsorParent = null;
        Node current = deletedNode.right;
        while (current != null)
        {
            successsorParent = successsor;
            successsor = current;
            current = current.left;
        }
        //check if successor has the right child, (it will never have left children) 
        // if it does have the right child, add it to the left of successorParent.
        if (successsor != deletedNode.right)
        {
            successsorParent.left = successsor.right;
            successsor.right = deletedNode.right;
        }
        return successsor;
    }

    /*
     * These are the main methods used to simulate a Binary Search Tree. 
     */

    public boolean find(int toFind)
    {
        Node current = root; // start at the root 

        while (current != null)
        { //traverse the tree 
            if (current.data == toFind)
            {
                System.out.println("Found:" + toFind);
                return true;
            } else if (current.data > toFind)
            {
                current = current.left;
            } else
            {
                current = current.right;
            }
        }
        System.out.println("Number wasn't found!");
        return false;
    }

    public boolean delete(int toDelete)
    {
        Node parent = root;
        Node current = root;
        /*
         * The below code checks to see which delete case needs to be done. 
         */
        boolean isLeftChild = false;
        while (current.data != toDelete)
        {
            parent = current;
            if (current.data > toDelete)
            {
                isLeftChild = true;
                current = current.left;
            } else
            {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null)
            {
                return false;
            }
        }

        //Case 1: if node to be deleted has no children
        if (current.left == null && current.right == null)
        {
            if (current == root)
            {
                root = null;
                System.out.println("The number " + toDelete + " " + "was removed");
            }
            if (isLeftChild) // update parent to have null left + right nodes 
            {
                parent.left = null;
            } else
            {
                parent.right = null;
            }
        }
        //Case 2 : if node to be deleted has exactly 1 child 

        else if (current.right == null) // if it has a left child 
        {
            if (current == root)
            {
                root = current.left;
                System.out.println("The number " + toDelete + " " + "was removed");
            } else if (isLeftChild)
            {
                parent.left = current.left;
            } else
            {
                parent.right = current.left;
            }
        } else if (current.left == null)
        {
            if (current == root)
            {
                root = current.right;
                System.out.println("The number " + toDelete + " " + "was removed");
            } else if (isLeftChild)
            {
                parent.left = current.right;
            } else
            {
                parent.right = current.right;
            }
        }
        //Case 3, when the node has two children 
        else if (current.left != null && current.right != null)
        {

            //We replace the deleted node with the left-most element in the right sub tree
            Node successor = getSuccessor(current);
            if (current == root)
            {
                root = successor;
                System.out.println("The number " + toDelete + " "
                        + "was removed");
            } else if (isLeftChild)
            {
                parent.left = successor;
            } else
            {
                parent.right = successor;
            }
            successor.left = current.left;
        }
        return true;
    }

    public void insert(int toAdd)
    {
        // This method adds an element to the BST
        // Note: this method will add duplicates to the left. 

        Node newNode = new Node(toAdd);
        if (root == null)
        { // if it's the first node, than the root becomes the new node 
            root = newNode;
            System.out.println("The number " + toAdd + " " + "was added");
            return;
        }

        Node current = root;
        Node parent = null;
        while (true)
        {
            parent = current;
            if (toAdd < current.data)
            { // left = lower            
                current = current.left;
                if (current == null)
                {
                    parent.left = newNode;
                    System.out.println("The number " + toAdd + " " + "was added");
                    return;
                }
            } else
            {
                current = current.right;
                if (current == null)
                {
                    parent.right = newNode;
                    System.out.println("The number " + toAdd + " " + "was added");
                    return;
                }
            }
        }
    }

    public void traverse(Node root)
    {
        // This method simulates an inorder traversal
        if (root != null)
        {
            traverse(root.left);
            System.out.print(" " + root.data);
            traverse(root.right);
        }
    }

    public int minValue(Node root)
    {
        // This finds the leftmost value of the BST, which is the minimum value in the tree 
        Node current = root;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
        {
            current = current.left;
        }
        return (current.data);
    }

    public static void main(String arg[])
    {

        BST b = new BST(); //create tree object 
        Scanner scanner = new Scanner(System.in);
        int i = 0; 

        String userInput = scanner.next();
        while (!userInput.equals("exit"))
        {
         if(userInput.equals("insert"))
         {
          i = scanner.nextInt();
        b.insert(i);
         }
         else if(userInput.equals("traverse")){
        b.traverse(b.root);
         }
         
            else if (userInput.equals("search"))
            {
          i = scanner.nextInt();
        b.find(i);
         }
         else if(userInput.equals("delete")){
          i = scanner.nextInt();
        b.delete(i);
         }
         userInput = scanner.next();
        }
        System.out.println("Exiting!");
        scanner.close();
        }

    /* TEST CODE 
    b.insert(3);
    b.insert(4);
    b.insert(8);
    b.insert(1);
    b.insert(4);
    b.insert(6);
    b.insert(2);
    b.insert(10);
    b.insert(9);
    b.insert(20);
    b.insert(25);
    b.insert(15);
    b.insert(16);
    System.out.println("The minimum value of this tree is: "
            + b.minValue(b.root));
    System.out.println("Current Tree : ");
    b.traverse(b.root);
    System.out.println("");
    System.out.println("Checking  whether Node with value 2 exists : "
            + b.find(2));
    System.out.println("Delete Node with no children (2) : " + b.delete(2));
    b.traverse(b.root);
    System.out
            .println("\n Delete Node with one child (4) : " + b.delete(4));
    b.traverse(b.root);
    System.out.println("\n Delete Node with Two children (10) : "
            + b.delete(10));
    b.traverse(b.root);
    */
}

