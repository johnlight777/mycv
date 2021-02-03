/*
   1. Only write your code in the methods that have the comment 'write your code here' or in the section 'write optional helper methods here'
   2. Do not modify anything else
   3. If your code cannot compile or fails the test cases in 'main()', you will not receive a response from us
*/

import java.util.*;
import java.util.regex.*;

public class TreeTestQ {
    public static void main(String... args) {

        /*
           Consider the following tree:

                 1
              /  |  \
             2   4   6
           / |  / \  |  \
          3  9 5  7  11 12
              / \      / | \
             13 16    14 23 17
               / \
              24 32

          Assuming the numbers are the ids of each node, the tree can be written down as follows:

          1(2,4,6) 2(3,9) 4(5,7) 6(11,12) 5(13,16) 12(14,23,17) 16(24,32)

          In the example above, for the group 1(2,4,6), node 2, 4 and 6 are the child nodes of
          node 1. Note that extra whitespaces should be accepted. Assume ids are positive integers only.
        */

        Tree first = new Tree("1(2,4,6) 2(3,9) 4(5,7) 6(11,12) 5(13,16) 12(14,23,17) 16(24,32)");
        assertTrue(first.getLevelOfNodeWithId(1) == 5);
        assertTrue(first.getLevelOfNodeWithId(4) == 4);
        assertTrue(first.getLevelOfNodeWithId(5) == 3);
        assertTrue(first.getLevelOfNodeWithId(12) == 3);
        assertTrue(first.getLevelOfNodeWithId(16) == 2);
        assertTrue(first.getLevelOfNodeWithId(23) == 2);
        assertTrue(first.getLevelOfNodeWithId(32) == 1);

        /*
              2
           / | | \
          5  4 3  1
          |     \
          7      9
         / \   /  \
        12 10 11  14
            |
            13
           / | \
         16  8  15
        */

        Tree second = new Tree("2(5,4,3,1) 5(7) 3(9) 7(12,10) 9(11,14) 10(13) 13(16,8,15)");
        assertTrue(second.getLevelOfNodeWithId(2) == 6);
        assertTrue(second.getLevelOfNodeWithId(5) == 5);
        assertTrue(second.getLevelOfNodeWithId(3) == 5);
        assertTrue(second.getLevelOfNodeWithId(12) == 3);
        assertTrue(second.getLevelOfNodeWithId(11) == 3);
        assertTrue(second.getLevelOfNodeWithId(13) == 2);
        assertTrue(second.getLevelOfNodeWithId(8) == 1);
    }

    private static void assertTrue(boolean v) {
        if(!v) {
            Thread.dumpStack();
            System.exit(0);
        }
    }
}

class Tree {
    private Node root;
    private Map<Integer, Integer> nodeIdsMap = new HashMap<>();

    // do not add new properties

    public Tree(String treeData) {
        StringTokenizer st = new StringTokenizer(treeData," ");
        boolean initRoot = false;

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(token,"(");

            if (st2.hasMoreTokens()) {
                String parent = st2.nextToken();
                String subToken = token.substring(parent.length()).replace("(", "").replace(")", "");

                if (!initRoot) {
                    initRoot = true;
                    root = new Node(Integer.parseInt(parent));
                    StringTokenizer st3 = new StringTokenizer(subToken,",");

                    while (st3.hasMoreTokens()) {
                        String child = st3.nextToken();
                        Node subNode = new Node(Integer.parseInt(child), root);
                        root.getChildren().add(subNode);
                    }
                } else {
                    Node parentNode = findNodeWithId(root, Integer.parseInt(parent));
                    if (parentNode != null) {
                        StringTokenizer st3 = new StringTokenizer(subToken,",");

                        while (st3.hasMoreTokens()) {
                            String child = st3.nextToken();
                            Node subNode = new Node(Integer.parseInt(child), parentNode);
                            parentNode.getChildren().add(subNode);
                        }
                    }
                }
            }
        }

        int treeDepth = initTreeDepth();
        contructNodeLevelMap(treeDepth);
    }

    private Node findNodeWithId(Node node, int id) {
        boolean found = false;
        Node foundNode = null;

        for (Node childNote : node.getChildren()) {
            if (childNote.getId() == id) {
                foundNode = childNote;
                found = true;
                break;
            }
        }

        if (!found) {
            for (Node childNote : node.getChildren()) {
                foundNode = findNodeWithId(childNote, id);
                if (foundNode != null) break;
            }
        }

        return foundNode;
    }

    /**
     * Finds a node with a given id and return it's level.
     * The nodes at the bottom of the tree have a level of 1.
     *
     * @param id The id of node
     * @return The level of the the node of that id, or -1 if a node is not found
     */
    public int getLevelOfNodeWithId(int id) {
        return nodeIdsMap.get(id);
    }

    private void contructNodeLevelMap(int treeDepth) {
        nodeIdsMap.put(root.getId(), treeDepth);
        treeDepth--;

        for (Node node : root.getChildren()) {
            nodeIdsMap.put(node.getId(), treeDepth);
            contructChildsNodeLevelMap(node, treeDepth);
        }
    }

    private void contructChildsNodeLevelMap(Node node, int depth) {
        depth--;

        for (Node childNode : node.getChildren()) {
            nodeIdsMap.put(childNode.getId(), depth);
            contructChildsNodeLevelMap(childNode, depth);
        }
    }

    private int initTreeDepth() {
        if (root == null) return 0;
        Integer[] childsSize = new Integer[root.getChildren().size()];

        for (int i = 0; i < childsSize.length; i++) {
            childsSize[i] = 2;
        }

        int treeDepth = getTreeDepth(root, 1, childsSize);
        return treeDepth;
    }

    private int getTreeDepth(Node node, int level, Integer[] nodesDepth) {
        List<Node> childs = node.getChildren();

        for (int i = 0; i < childs.size(); i++) {
            Node childNode = childs.get(i);
            int depth = nodesDepth[i];

            if (childNode.getChildren() != null && childNode.getChildren().size() > 0) {
                depth++;
                nodesDepth[i] = depth;
                getTreeDepth(childNode, level, nodesDepth);
            }
        }

        level = nodesDepth[0];

        for (int j = 1; j < nodesDepth.length; j++) {
            int depthNode = nodesDepth[j];
            if (depthNode > level) level = depthNode;
        }

        return level;
    }

    // write optional helper methods here
}

class Node {
    private Node parent;
    private List<Node> children;
    private int id;

    public Node(int id) {
        this.id = id;
        this.children = new ArrayList<Node>();
    }

    public Node(int id, Node parent) {
        this(id);
        this.parent = parent;
    }

    public void appendChild(Node child) {
        children.add(child);
    }

    public int getId() {
        return id;
    }

    public List<Node> getChildren() {
        return children;
    }
}