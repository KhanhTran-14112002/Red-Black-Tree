package Tree.redblacktree;

import java.util.*;

// Class Definitions
public class RedBlackTree<T extends Comparable<T>, V> implements RedBlackTreeInterface<T, V> {

    // Root initialized to nil.
    private RedBlackNode<T, V> nil = new RedBlackNode<T, V>();
    private RedBlackNode<T, V> root = nil;

    public RedBlackTree() {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }

    public RedBlackNode<T, V> getRoot() {
        return root;
    }

    // @param: x, The node which the lefRotate is to be performed on.
    // Performs a leftRotate around x.
    private void leftRotate(RedBlackNode<T, V> x) {

        // Call leftRotateFixup() which updates the numLeft
        // and numRight values.
        leftRotateFixup(x);

        // Perform the left rotate as described in the algorithm
        // in the course text.
        RedBlackNode<T, V> y;
        y = x.right;
        x.right = y.left;

        // Check for existence of y.left and make pointer changes
        if (!isNil(y.left))
            y.left.parent = x;
        y.parent = x.parent;

        // x's parent is nul
        if (isNil(x.parent))
            root = y;

            // x is the left child of it's parent
        else if (x.parent.left == x)
            x.parent.left = y;

            // x is the right child of it s parent.
        else
            x.parent.right = y;

        // Finish of the leftRotate
        y.left = x;
        x.parent = y;
    }// end leftRotate(RedBlackNode x)


    // @param: x, The node which the leftRotate is to be performed on.
    // Updates the numLeft & numRight values affected by leftRotate.
    private void leftRotateFixup(RedBlackNode x) {

        // Case 1: Only x, x.right and x.right.right always are not nil.
        if (isNil(x.left) && isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 0;
            x.right.numLeft = 1;
        }

        // Case 2: x.right.left also exists in addition to Case 1
        else if (isNil(x.left) && !isNil(x.right.left)) {
            x.numLeft = 0;
            x.numRight = 1 + x.right.left.numLeft +
                    x.right.left.numRight;
            x.right.numLeft = 2 + x.right.left.numLeft +
                    x.right.left.numRight;
        }

        // Case 3: x.left also exists in addition to Case 1
        else if (!isNil(x.left) && isNil(x.right.left)) {
            x.numRight = 0;
            x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;

        }
        // Case 4: x.left and x.right.left both exist in addtion to Case 1
        else {
            x.numRight = 1 + x.right.left.numLeft +
                    x.right.left.numRight;
            x.right.numLeft = 3 + x.left.numLeft + x.left.numRight +
                    x.right.left.numLeft + x.right.left.numRight;
        }

    }// end leftRotateFixup(RedBlackNode x)


    // @param: x, The node which the rightRotate is to be performed on.
    // Updates the numLeft and numRight values affected by the Rotate.
    private void rightRotate(RedBlackNode<T, V> y) {

        // Call rightRotateFixup to adjust numRight and numLeft values
        rightRotateFixup(y);

        // Perform the rotate as described in the course text.
        RedBlackNode<T, V> x = y.left;
        y.left = x.right;

        // Check for existence of x.right
        if (!isNil(x.right))
            x.right.parent = y;
        x.parent = y.parent;

        // y.parent is nil
        if (isNil(y.parent))
            root = x;

            // y is a right child of it s parent.
        else if (y.parent.right == y)
            y.parent.right = x;

            // y is a left child of it s parent.
        else
            y.parent.left = x;
        x.right = y;

        y.parent = x;

    }// end rightRotate(RedBlackNode y)


    // @param: y, the node around which the righRotate is to be performed.
    // Updates the numLeft and numRight values affected by therotate
    private void rightRotateFixup(RedBlackNode y) {

        // Case 1: Only y, y.left and y.left.left exists.
        if (isNil(y.right) && isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 0;
            y.left.numRight = 1;
        }

        // Case 2: y.left.right also exists in addition to Case 1
        else if (isNil(y.right) && !isNil(y.left.right)) {
            y.numRight = 0;
            y.numLeft = 1 + y.left.right.numRight +
                    y.left.right.numLeft;
            y.left.numRight = 2 + y.left.right.numRight +
                    y.left.right.numLeft;
        }

        // Case 3: y.right also exists in addition to Case 1
        else if (!isNil(y.right) && isNil(y.left.right)) {
            y.numLeft = 0;
            y.left.numRight = 2 + y.right.numRight + y.right.numLeft;

        }

        // Case 4: y.right & y.left.right exist in addition to Case 1
        else {
            y.numLeft = 1 + y.left.right.numRight +
                    y.left.right.numLeft;
            y.left.numRight = 3 + y.right.numRight +
                    y.right.numLeft +
                    y.left.right.numRight + y.left.right.numLeft;
        }

    }// end rightRotateFixup(RedBlackNode y)

    @Override
    public void insert(T key, V data) {
        insert(new RedBlackNode<T, V>(key, data));
    }

    // @param: z, the node to be inserted into the Tree rooted at root
    // Inserts z into the appropriate position in the RedBlackTree while
    // updating numLeft and numRight values.
    private void insert(RedBlackNode<T, V> newNode) {

        // Create a reference to the root & initialize a node to nil
        RedBlackNode<T, V> parent = nil;
        RedBlackNode<T, V> currentNode = root;

        // While we haven't reached the end of the tree, keep
        // trying to figure out where the new node should go
        while (!isNil(currentNode)) {
            parent = currentNode;

            // if newNode.key is < than the current key, go left
            if (newNode.key.compareTo(currentNode.key) < 0) {

                // Update currentNode.numLeft as newNode is < than currentNode
                currentNode.numLeft++;
                currentNode = currentNode.left;
            }

            // else newNode.key >= currentNode.key so go right.
            else {

                // Update currentNode.numGreater as newNode is => currentNode
                currentNode.numRight++;
                currentNode = currentNode.right;
            }
        }
        // parent will hold newNode's parent
        newNode.parent = parent;

        // Depending on the value of parent.key, put newNode as the left or
        // right child of parent
        if (isNil(parent))
            root = newNode;
        else if (newNode.key.compareTo(parent.key) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;

        // Initialize newNode's children to nil and newNode's color to red
        newNode.left = nil;
        newNode.right = nil;
        newNode.color = RedBlackNode.RED;

        // Call insertFixup(newNode)
        insertFixup(newNode);
    }

    // @param: newNode, the node which was inserted and may have caused a violation
// of the Red-Black Tree properties
// Fixes up the violation of the Red-Black Tree properties that may have
// been caused during insert(newNode)
    private void insertFixup(RedBlackNode<T, V> newNode) {

        RedBlackNode<T, V> uncle = nil;
        // While there is a violation of the Red-Black Tree properties..
        while (newNode.parent.color == RedBlackNode.RED) {

            // If newNode's parent is the left child of its parent.
            if (newNode.parent == newNode.parent.parent.left) {

                // Initialize uncle to newNode's cousin
                uncle = newNode.parent.parent.right;

                // Case 1: if uncle is red...recolor
                if (uncle.color == RedBlackNode.RED) {
                    newNode.parent.color = RedBlackNode.BLACK;
                    uncle.color = RedBlackNode.BLACK;
                    newNode.parent.parent.color = RedBlackNode.RED;
                    newNode = newNode.parent.parent;
                }
                // Case 2: if uncle is black & newNode is a right child
                else if (newNode == newNode.parent.right) {

                    // leftRotate around newNode's parent
                    newNode = newNode.parent;
                    leftRotate(newNode);
                }

                // Case 3: else uncle is black & newNode is a left child
                else {
                    // recolor and rotate around newNode's grandparent
                    newNode.parent.color = RedBlackNode.BLACK;
                    newNode.parent.parent.color = RedBlackNode.RED;
                    rightRotate(newNode.parent.parent);
                }
            }

            // If newNode's parent is the right child of its parent.
            else {

                // Initialize uncle to newNode's cousin
                uncle = newNode.parent.parent.left;

                // Case 1: if uncle is red...recolor
                if (uncle.color == RedBlackNode.RED) {
                    newNode.parent.color = RedBlackNode.BLACK;
                    uncle.color = RedBlackNode.BLACK;
                    newNode.parent.parent.color = RedBlackNode.RED;
                    newNode = newNode.parent.parent;
                }

                // Case 2: if uncle is black and newNode is a left child
                else if (newNode == newNode.parent.left) {
                    // rightRotate around newNode's parent
                    newNode = newNode.parent;
                    rightRotate(newNode);
                }
                // Case 3: if uncle is black and newNode is a right child
                else {
                    // recolor and rotate around newNode's grandparent
                    newNode.parent.color = RedBlackNode.BLACK;
                    newNode.parent.parent.color = RedBlackNode.RED;
                    leftRotate(newNode.parent.parent);
                }
            }
        }
        // Color root black at all times
        root.color = RedBlackNode.BLACK;
    }


    // @param: node, a RedBlackNode
    // @param: node, the node with the smallest key rooted at node
    public RedBlackNode<T, V> treeMinimum(RedBlackNode<T, V> node) {

        // while there is a smaller key, keep going left
        while (!isNil(node.left))
            node = node.left;
        return node;
    }// end treeMinimum(RedBlackNode node)


    // @param: x, a RedBlackNode whose successor we must find
    // @return: return's the node the with the next largest key
    // from x.key
    public RedBlackNode<T, V> treeSuccessor(RedBlackNode<T, V> x) {

        // if x.left is not nil, call treeMinimum(x.right) and
        // return it's value
        if (!isNil(x.left))
            return treeMinimum(x.right);

        RedBlackNode<T, V> y = x.parent;

        // while x is it's parent's right child...
        while (!isNil(y) && x == y.right) {
            // Keep moving up in the tree
            x = y;
            y = y.parent;
        }
        // Return successor
        return y;
    }// end treeMinimum(RedBlackNode x)


    // @param: z, the RedBlackNode which is to be removed from the the tree
    // Remove's z from the RedBlackTree rooted at root
    @Override
    public void remove(T key) {
        // Tìm nút cần xóa trong cây
        RedBlackNode<T, V> z = search(key);

        // Khai báo các biến
        RedBlackNode<T, V> x = nil;
        RedBlackNode<T, V> y = nil;

        // Nếu một trong hai con của z là nil, ta cần xóa z
        if (isNil(z.left) || isNil(z.right))
            y = z;
            // Ngược lại, ta cần xóa kế thừa của z
        else
            y = treeSuccessor(z);

        // Đặt x là con trái hoặc con phải của y (y chỉ có thể có một con)
        if (!isNil(y.left))
            x = y.left;
        else
            x = y.right;

        // Liên kết cha của x với cha của y
        x.parent = y.parent;

        // Nếu cha của y là nil, thì x là gốc của cây
        if (isNil(y.parent))
            root = x;
            // Nếu y là con trái, đặt x là con trái của y
        else if (!isNil(y.parent.left) && y.parent.left == y)
            y.parent.left = x;
            // Nếu y là con phải, đặt x là con phải của y
        else if (!isNil(y.parent.right) && y.parent.right == y)
            y.parent.right = x;

        // Nếu y khác z, chuyển dữ liệu của y vào z
        if (y != z) {
            z.key = y.key;
        }

        // Cập nhật các giá trị numLeft và numRight có thể cần cập nhật
        // do việc xóa z.key
        fixNodeData(x, y);

        // Nếu màu của y là đen, đây là vi phạm các tính chất của
        // RedBlackTree, nên gọi removeFixup()
        if (y.color == RedBlackNode.BLACK)
            removeFixup(x);
    }


//	public void remove(RedBlackNode<T> v){
//
//		RedBlackNode<T> z = search(v.key);
//
//		// Declare variables
//		RedBlackNode<T> x = nil;
//		RedBlackNode<T> y = nil;
//
//		// if either one of z's children is nil, then we must remove z
//		if (isNil(z.left) || isNil(z.right))
//			y = z;
//
//		// else we must remove the successor of z
//		else y = treeSuccessor(z);
//
//		// Let x be the left or right child of y (y can only have one child)
//		if (!isNil(y.left))
//			x = y.left;
//		else
//			x = y.right;
//
//		// link x's parent to y's parent
//		x.parent = y.parent;
//
//		// If y's parent is nil, then x is the root
//		if (isNil(y.parent))
//			root = x;
//
//		// else if y is a left child, set x to be y's left sibling
//		else if (!isNil(y.parent.left) && y.parent.left == y)
//			y.parent.left = x;
//
//		// else if y is a right child, set x to be y's right sibling
//		else if (!isNil(y.parent.right) && y.parent.right == y)
//			y.parent.right = x;
//
//		// if y != z, trasfer y's satellite data into z.
//		if (y != z){
//			z.key = y.key;
//		}
//
//		// Update the numLeft and numRight numbers which might need
//		// updating due to the deletion of z.key.
//		fixNodeData(x,y);
//
//		// If y's color is black, it is a violation of the
//		// RedBlackTree properties so call removeFixup()
//		if (y.color == RedBlackNode.BLACK)
//			removeFixup(x);
//	}// end remove(RedBlackNode z)


    // @param: y, the RedBlackNode which was actually deleted from the tree
    // @param: key, the value of the key that used to be in y
    private void fixNodeData(RedBlackNode<T, V> x, RedBlackNode<T, V> y) {

        // Initialize two variables which will help us traverse the tree
        RedBlackNode<T, V> current = nil;
        RedBlackNode<T, V> track = nil;


        // if x is nil, then we will start updating at y.parent
        // Set track to y, y.parent's child
        if (isNil(x)) {
            current = y.parent;
            track = y;
        }

        // if x is not nil, then we start updating at x.parent
        // Set track to x, x.parent's child
        else {
            current = x.parent;
            track = x;
        }

        // while we haven't reached the root
        while (!isNil(current)) {
            // if the node we deleted has a different key than
            // the current node
            if (y.key != current.key) {

                // if the node we deleted is greater than
                // current.key then decrement current.numRight
                if (y.key.compareTo(current.key) > 0)
                    current.numRight--;

                // if the node we deleted is less than
                // current.key thendecrement current.numLeft
                if (y.key.compareTo(current.key) < 0)
                    current.numLeft--;
            }

            // if the node we deleted has the same key as the
            // current node we are checking
            else {
                // the cases where the current node has any nil
                // children and update appropriately
                if (isNil(current.left))
                    current.numLeft--;
                else if (isNil(current.right))
                    current.numRight--;

                    // the cases where current has two children and
                    // we must determine whether track is it's left
                    // or right child and update appropriately
                else if (track == current.right)
                    current.numRight--;
                else if (track == current.left)
                    current.numLeft--;
            }

            // update track and current
            track = current;
            current = current.parent;

        }

    }//end fixNodeData()


    // @param: x, the child of the deleted node from remove(RedBlackNode v)
    // Restores the Red Black properties that may have been violated during
    // the removal of a node in remove(RedBlackNode v)

    private void removeFixup(RedBlackNode<T, V> x) {

        RedBlackNode<T, V> w;

        // While we haven't fixed the tree completely...
        while (x != root && x.color == RedBlackNode.BLACK) {

            // if x is it's parent's left child
            if (x == x.parent.left) {

                // set w = x's sibling
                w = x.parent.right;

                // Case 1, w's color is red.
                if (w.color == RedBlackNode.RED) {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                // Case 2, both of w's children are black
                if (w.left.color == RedBlackNode.BLACK &&
                        w.right.color == RedBlackNode.BLACK) {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }
                // Case 3 / Case 4
                else {
                    // Case 3, w's right child is black
                    if (w.right.color == RedBlackNode.BLACK) {
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // Case 4, w = black, w.right = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            // if x is it's parent's right child
            else {

                // set w to x's sibling
                w = x.parent.left;

                // Case 1, w's color is red
                if (w.color == RedBlackNode.RED) {
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                // Case 2, both of w's children are black
                if (w.right.color == RedBlackNode.BLACK &&
                        w.left.color == RedBlackNode.BLACK) {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }

                // Case 3 / Case 4
                else {
                    // Case 3, w's left child is black
                    if (w.left.color == RedBlackNode.BLACK) {
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    // Case 4, w = black, and w.left = red
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.left.color = RedBlackNode.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }// end while

        // set x to black to ensure there is no violation of
        // RedBlack tree Properties
        x.color = RedBlackNode.BLACK;
    }// end removeFixup(RedBlackNode x)


    // @param: key, the key whose node we want to search for
    // @return: returns a node with the key, key, if not found, returns null
    // Searches for a node with key k and returns the first such node, if no
    // such node is found returns null

    @Override
    public RedBlackNode<T, V> search(T key) {
        // Khởi tạo một con trỏ trỏ đến gốc để duyệt cây
        RedBlackNode<T, V> current = root;

        // Duyệt cây cho đến khi đến cuối cây hoặc tìm thấy nút có giá trị khóa là key
        while (!isNil(current)) {
            // Nếu tìm thấy nút có giá trị khóa là key, trả về nút đó
            if (current.key.equals(key))
                return current;

                // Nếu key nhỏ hơn giá trị khóa của nút hiện tại, điều hướng sang nút con bên trái
            else if (key.compareTo(current.key) < 0)
                current = current.left;

                // Nếu key lớn hơn giá trị khóa của nút hiện tại, điều hướng sang nút con bên phải
            else
                current = current.right;
        }

        // Trả về null nếu không tìm thấy nút có giá trị khóa là key trong cây
        return null;
    }

    // @param: key, any Comparable object
    // @return: return's the number of elements greater than key
    @Override
    public int numGreater(T key) {

        // Call findNumGreater(root, key) which will return the number
        // of nodes whose key is greater than key
        return findNumGreater(root, key);

    }// end numGreater(int key)


    // @param: key, any Comparable object
    // @return: return's teh number of elements smaller than key
    @Override
    public int numSmaller(T key) {

        // Call findNumSmaller(root,key) which will return
        // the number of nodes whose key is greater than key
        return findNumSmaller(root, key);

    }// end numSmaller(int key)

    // @param: node, the root of the tree, the key who we must compare other
    // node key's to.
    // @return: the number of nodes smaller than key.
    public int findNumSmaller(RedBlackNode<T, V> node, T key) {

        // Base Case: if node is nil, return 0
        if (isNil(node)) return 0;

            // If key is less than node.key, look to the left as all
            // elements on the right of node are greater than key
        else if (key.compareTo(node.key) <= 0)
            return findNumSmaller(node.left, key);

            // If key is larger than node.key, all elements to the left of
            // node are smaller than key, add this to our total and look
            // to the right.
        else
            return 1 + node.numLeft + findNumSmaller(node.right, key);

    }// end findNumSmaller(RedBlackNode nod, int key)


    // @param: node, the root of the tree, the key who we must
    // compare other node key's to.
    // @return: the number of nodes greater than key.
    public int findNumGreater(RedBlackNode<T, V> node, T key) {

        // Base Case: if node is nil, return 0
        if (isNil(node))
            return 0;
            // If key is less than node.key, all elements right of node are
            // greater than key, add this to our total and look to the left
        else if (key.compareTo(node.key) < 0)
            return 1 + node.numRight + findNumGreater(node.left, key);

            // If key is greater than node.key, then look to the right as
            // all elements to the left of node are smaller than key
        else
            return findNumGreater(node.right, key);

    }// end findNumGreater(RedBlackNode, int key)


    @Override
    public List<T> getGreaterThan(T key, Integer maxReturned) {
        List<T> list = new ArrayList<T>();
        getGreaterThan(root, key, list);
        return list.subList(0, Math.min(maxReturned, list.size()));
    }


    private void getGreaterThan(RedBlackNode<T, V> node, T key, List<T> list) {
        if (isNil(node)) {
            return;
        }

        int comparisonResult = node.key.compareTo(key);

        // Nếu node.key > key, duyệt qua cây con trái và cây con phải
        if (comparisonResult > 0) {
            getGreaterThan(node.left, key, list);
            list.add(node.key);
            getGreaterThan(node.right, key, list);
        }
        // Nếu node.key <= key, duyệt qua cây con phải
        else {
            getGreaterThan(node.right, key, list);
        }
    }


    // @param: node, the RedBlackNode we must check to see whether it's nil
    // @return: return's true of node is nil and false otherwise
    boolean isNil(RedBlackNode node) {

        // return appropriate value
        return node == nil;

    }// end isNil(RedBlackNode node)


    // @return: return's the size of the tree
    // Return's the # of nodes including the root which the RedBlackTree
    // rooted at root has.
    @Override
    public int size() {

        // Return the number of nodes to the root's left + the number of
        // nodes on the root's right + the root itself.
        return root.numLeft + root.numRight + 1;
    }// end size()



    public RedBlackNode<T, V> searchByKey(T key) {
        // Tree is empty
        if (root == null) {
            return null;
        }

        // Tree is not empty
        RedBlackNode<T, V> temp = root;
        while (temp != null) {
            if (temp.getKey() != null) {  // Add null check here
                int compareResult = key.compareTo(temp.getKey());
                if (compareResult == 0) {
                    return temp;
                } else if (compareResult < 0) {
                    temp = temp.getLeft();
                } else {
                    temp = temp.getRight();
                }
            } else {
                return null;  // Handle the case when temp.getKey() is null
            }
        }
        return null;
    }
    public boolean found(T key) {
        RedBlackNode<T,V> n = searchByKey(key);
        if (n == null) {
            return false;
        } else {
            return true;
        }
    }


    public int countNodes(RedBlackNode<T,V> root) {
        Stack<RedBlackNode<T,V>> stack = new Stack<>();
        int count = 0;
        while (!stack.isEmpty()) {
            RedBlackNode<T,V> node = stack.pop();
            if (node != null) {
                count++;
                stack.push(node.left);
                stack.push(node.right);
            }
        }
        return count;
    }
    public void printTree() {
        if (root == nil) {
            System.out.println("Tree is empty.");
            return;
        }

        Stack<RedBlackNode<T, V>> stack = new Stack<>();
        RedBlackNode<T, V> current = root;
        int nodeCount = 0;

        while (current != nil || !stack.isEmpty()) {
            while (current != nil) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            System.out.print(current.key + "(" + (current.color == RedBlackNode.RED ? "R" : "B") + ") ");
            nodeCount++;

            current = current.right;
        }

        System.out.println("\nNumber of nodes: " + nodeCount);
    }



//    public int getHeight() {
//        if (root == null) {
//            return 0;
//        }
//
//        Queue<RedBlackNode<T, V>> queue = new LinkedList<>();
//        queue.offer(root);
//        int height = 0;
//
//        while (!queue.isEmpty()) {
//            int levelSize = queue.size();
//
//            while (levelSize > 0) {
//                RedBlackNode<T, V> current = queue.poll();
//
//                if (current.getLeft() != null) {
//                    queue.offer(current.getLeft());
//                }
//
//                if (current.getRight() != null) {
//                    queue.offer(current.getRight());
//                }
//
//                levelSize--;
//            }
//
//            height++;
//        }
//
//        return height;
//    }


}// end class RedBlackTree
