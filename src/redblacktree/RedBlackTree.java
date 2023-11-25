package redblacktree;/*
Design Decisions:
-----------------

I chose to use the sentinel instead of regular null pointers because it makes
removeFixup() easier and more efficient.  Every RedBlackNode instantiated has
all of its pointers pointed to nil.  The root at all times will have it s
parent pointer to nil. The remove and delete algorithm s are based on the
course textbook and so are the leftRotate(RedBlackNode x) and
rightRotate(RedBlackNode y) functions.

After an insertion of an element using insert(), we always call insertFixup()
to ensure that red-black properties are maintained.  While when deleteing, we
only call deleteFixup when a certain condition( x == BLACK) is true.

Since we are only concerned with deleting the key from the tree, we will begin
our delete(RedBlackNode v) function with a call to search(v.key) which will
ensure us that we are deleting the correct node.

I have implemented the numSmaller(int) and numGreater(int) functions by keeping
track of how many elements are to the left (numLeft) and to the right (numRight)
of each node.  They both contain the number of elements to the left or right of
a given node, not including that node itself.

This value is updated when a node is inserted and maintained by the functions
leftRotateFixup(RedBlackNode) and rightRotateFixup(RedBlackNode) which update
these variables when a rotation occurs. This value is also updated during the
deletion of a node by the function called fixNodeData(RedBlackNode, int).

My size() function checks the size of the roots numLeft and numRight variables,
adds them and adds one to return the answer.  This operation is performed in
O(1) time.

In the program, I am checking for the case where a particular RedBlackNode has
a pointer pointing to nil, since this operation is very common, I have a
function called isNil(RedBlackNode), which returns a boolean value of whether
the argument is nil or not.  I have chosen my search(int key) function to be
iterative when it easily could have been recursive because the textbook
mentions that an iterative search is always faster than a recursive one.

Duplicate RedBlackNodes are thought of as being slightly greater than its
counterpart with the same key.  The insert() function takes care of this
by having to cases in it's while loop, one for < and one for =>.  The
function fixNodeData() takes care of this during deletion as also having two
cases.

I have chosen to represent, RED as the integer value 1 and BLACK as the integer
value 0. Both these are declared as final in this class' instance variables.
These values are assigned to the 'color' variable.

*/

// Inclusions

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
    private void insert(RedBlackNode<T, V> z) {

        // Create a reference to root & initialize a node to nil
        RedBlackNode<T, V> y = nil;
        RedBlackNode<T, V> x = root;

        // While we haven't reached athe end of the tree keep
        // tryint to figure out where z should go
        while (!isNil(x)) {
            y = x;

            // if z.key is < than the current key, go left
            if (z.key.compareTo(x.key) < 0) {

                // Update x.numLeft as z is < than x
                x.numLeft++;
                x = x.left;
            }

            // else z.key >= x.key so go right.
            else {

                // Update x.numGreater as z is => x
                x.numRight++;
                x = x.right;
            }
        }
        // y will hold z's parent
        z.parent = y;

        // Depending on the value of y.key, put z as the left or
        // right child of y
        if (isNil(y))
            root = z;
        else if (z.key.compareTo(y.key) < 0)
            y.left = z;
        else
            y.right = z;

        // Initialize z's children to nil and z's color to red
        z.left = nil;
        z.right = nil;
        z.color = RedBlackNode.RED;

        // Call insertFixup(z)
        insertFixup(z);

    }// end insert(RedBlackNode z)


    // @param: z, the node which was inserted and may have caused a violation
    // of the RedBlackTree properties
    // Fixes up the violation of the RedBlackTree properties that may have
    // been caused during insert(z)

    /**
     * Phục hồi tính chất của cây đỏ-đen sau khi thực hiện phương thức insert.
     *
     * @param z Nút được thêm vào cây.
     *          <p>
     *          Phương thức này được sử dụng để duy trì tính chất của cây đỏ-đen sau khi một nút mới
     *          được thêm vào cây. Trong quá trình này, chúng ta kiểm tra và điều chỉnh màu sắc của các
     *          nút để đảm bảo tính chất của cây đỏ-đen.
     *          <p>
     *          y Nút cousin của z.
     *          <p>
     *          Trong quá trình phục hồi, chúng ta kiểm tra và xử lý các trường hợp có thể xảy ra khi
     *          một nút mới (z) được thêm vào cây đỏ-đen. Các trường hợp cụ thể được mô tả dưới đây:
     *          <p>
     *          Case 1: Nếu nút cousin y của z có màu đỏ.
     *          - Chuyển màu của parent của z và y thành đen.
     *          - Chuyển màu của grandparent của z thành đỏ.
     *          - Di chuyển lên mức grandparent để kiểm tra tính chất của cây đỏ-đen.
     *          <p>
     *          Case 2: Nếu nút cousin y của z có màu đen và z là con phải của parent của nó.
     *          - Quay trái tại nút parent của z.
     *          - Di chuyển z lên mức parent để kiểm tra tính chất của cây đỏ-đen.
     *          <p>
     *          Case 3: Nếu nút cousin y của z có màu đen và z là con trái của parent của nó.
     *          - Chuyển màu của parent của z thành đen.
     *          - Chuyển màu của grandparent của z thành đỏ.
     *          - Quay phải tại grandparent của z.
     *          - Kết thúc vòng lặp.
     *          <p>
     *          Kết thúc vòng lặp, chúng ta đảm bảo rằng tính chất của cây đỏ-đen vẫn được duy trì và
     *          màu của nút gốc (root) được thiết lập thành đen để đảm bảo không xâm phạm tính chất của cây.
     * @see <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">Red–black tree on Wikipedia</a>
     */
    private void insertFixup(RedBlackNode<T, V> z) {

        RedBlackNode<T, V> y = nil;
        // While there is a violation of the RedBlackTree properties..
        while (z.parent.color == RedBlackNode.RED) {

            // If z's parent is the the left child of it's parent.
            if (z.parent == z.parent.parent.left) {

                // Initialize y to z 's cousin
                y = z.parent.parent.right;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED) {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }
                // Case 2: if y is black & z is a right child
                else if (z == z.parent.right) {

                    // leftRotaet around z's parent
                    z = z.parent;
                    leftRotate(z);
                }

                // Case 3: else y is black & z is a left child
                else {
                    // recolor and rotate round z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            }

            // If z's parent is the right child of it s parent.
            else {

                // Initialize y to z's cousin
                y = z.parent.parent.left;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED) {
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }

                // Case 2: if y is black and z is a left child
                else if (z == z.parent.left) {
                    // rightRotate around z's parent
                    z = z.parent;
                    rightRotate(z);
                }
                // Case 3: if y  is black and z is a right child
                else {
                    // recolor and rotate around z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        // Color root black at all times
        root.color = RedBlackNode.BLACK;

    }// end insertFixup(RedBlackNode z)

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

    /**
     * Phục hồi tính chất của cây đỏ-đen sau khi thực hiện phương thức remove.
     *
     * @param x Nút bắt đầu quá trình phục hồi.
     *          <p>
     *          Phương thức này được sử dụng để đảm bảo tính chất của cây đỏ-đen sau khi một nút được xóa
     *          khỏi cây. Trong quá trình này, màu đỏ-đen và cân bằng của cây được duy trì để đảm bảo
     *          các tính chất của cây đỏ-đen.
     *          <p>
     *          w  Nút sibling của x.
     *          <p>
     *          Trong quá trình phục hồi, chúng ta duyệt qua cây để đảm bảo rằng nó vẫn đáp ứng các điều kiện
     *          của cây đỏ-đen. Quy trình này có thể liên quan đến các trường hợp khác nhau, như khi sibling
     *          của nút x có màu đỏ hoặc các con của nó có màu đen. Các trường hợp cụ thể được mô tả dưới đây:
     *          <p>
     *          Case 1: Màu của sibling w là đỏ.
     *          - Chuyển màu của w thành đen.
     *          - Chuyển màu của parent của x thành đỏ.
     *          - Quay trái tại parent của x.
     *          - Cập nhật sibling w sau khi quay trái.
     *          <p>
     *          Case 2: Cả hai con của w đều có màu đen.
     *          - Chuyển màu của w thành đỏ.
     *          - Di chuyển lên mức trên trong cây để kiểm tra tính chất của cây đỏ-đen.
     *          <p>
     *          Case 3 / Case 4: Các trường hợp còn lại khi ít nhất một con của w có màu đỏ.
     *          - Có thể xảy ra một trong các trường hợp sau:
     *          + Case 3: Con phải của w có màu đen, con trái có màu đen.
     *          - Chuyển màu của con trái của w thành đen.
     *          - Chuyển màu của w thành đỏ.
     *          - Quay phải tại w.
     *          - Cập nhật sibling w sau khi quay phải.
     *          + Case 4: Con trái của w có màu đỏ, con phải có màu đen hoặc màu nào cũng được.
     *          - Chuyển màu của w thành màu của parent x.
     *          - Chuyển màu của parent x thành đen.
     *          - Chuyển màu của con phải của w thành đen (nếu có).
     *          - Quay trái tại parent của x.
     *          - Thiết lập x bằng root để kết thúc vòng lặp.
     *          <p>
     *          Kết thúc vòng lặp, chúng ta đảm bảo rằng tính chất của cây đỏ-đen vẫn được duy trì và
     *          màu của nút x được thiết lập thành đen để đảm bảo không xâm phạm tính chất của cây.
     */
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

    /**
     * Tìm kiếm một nút trong cây dựa trên giá trị khóa.
     *
     * @param key Giá trị khóa cần tìm kiếm.
     * @return Nút chứa giá trị khóa nếu tồn tại, hoặc null nếu không tìm thấy.
     */
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

    /**
     * Returns sorted list of keys greater than key.  Size of list
     * will not exceed maxReturned
     *
     * @param key         Key to search for
     * @param maxReturned Maximum number of results to return
     * @return List of keys greater than key.  List may not exceed maxReturned
     */
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
    private boolean isNil(RedBlackNode node) {

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

    /**
     * In cây đỏ-đen ra màn hình dưới dạng cây nhị phân, bắt đầu từ nút được chỉ định.
     *
     * @param node  Nút hiện tại của cây.
     * @param depth Độ sâu của nút hiện tại trong cây.
     *              <p>
     *              Phương thức này in cây đỏ-đen ra màn hình theo cấu trúc cây nhị phân, bắt đầu từ nút
     *              được chỉ định. Trong quá trình in, mỗi nút được hiển thị với khoảng cách tương ứng với
     *              độ sâu của nó trong cây.
     * @see <a href="https://en.wikipedia.org/wiki/Binary_tree#Definition">Binary Tree on Wikipedia</a>
     */
    public void printTree(RedBlackNode<T, V> node, int depth) {
        if (node == null) {
            return;
        }

        // In cây theo thứ tự: cây con phải - nút - cây con trái
        if (node.right != null) {
            printTree(node.right, depth + 1);
        }

        // In khoảng cách tương ứng với độ sâu của nút
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }

        // In giá trị của nút
        System.out.println(node.key);

        // In cây con trái
        if (node.left != null) {
            printTree(node.left, depth + 1);
        }
    }

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


/*
Design Decisions:
-----------------

Tôi đã chọn lớp đối tượng RedBlackNode để có bảy biến thể hiện
tất cả được khai báo công khai theo thông số kỹ thuật chuyển nhượng. Mỗi trường hợp của một
RedBlackNode giữ một "khóa" có thể so sánh được, là khóa của RedBlackNode. Nó
cũng chứa một số nguyên "màu" khác được gán "0" cho ĐEN và "1" cho
MÀU ĐỎ. Biến số nguyên "numSmaller" chứa các phần tử ở bên trái của một
nút đã cho và "numGreater" chứa các phần tử ở bên phải của một nút nhất định, không phải
bao gồm chính nút đó.

Mỗi phiên bản cũng giữ một con trỏ RedBlackNode tới nút "cha", "trái" của nút
đứa trẻ và đứa trẻ "đúng". Các giá trị này được gán cho nil khi một nút được
được khởi tạo.

Hàm tạo nhận đối số có thể so sánh sẽ gán giá trị đó cho khóa
của nút. Hàm tạo trống ở đó để kiểm tra trường hợp thử nghiệm của Giáo sư Pitt và
Ngoài ra, trong trường hợp chúng tôi chỉ muốn tạo RedBlackNode và khởi tạo khóa của nó sau.

Tôi đã chọn sử dụng công cụ canh gác vì nó dễ dàng hơn và hiệu quả hơn.
cách hiệu quả để triển khai Cây Đỏ Đen. Trọng điểm (nil) được tuyên bố
trong lớp RedBlackTree vì nó được tham chiếu nhiều nhất ở đó, trong lớp này
chúng ta khởi tạo các con trỏ trái/phải/cha với tham chiếu tĩnh về 0.

*/

// inclusions

