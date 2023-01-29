public class Main {
    public static void main(String[] args) {
        //Binary tree
        //A node has no more than 2 children nodes
        //In a binary search tree a parent node should be greater than the left child
        //and lower than the right child
        //Runtime in its best case is O(log n) worst case O(n)

        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(new Node(5));
        tree.insert(new Node(1));
        tree.insert(new Node(9));
        tree.insert(new Node(2));
        tree.insert(new Node(7));
        tree.insert(new Node(3));
        tree.insert(new Node(6));
        tree.insert(new Node(4));

        tree.remove(1);

        tree.display();
        System.out.println(tree.search(5));




    }
}