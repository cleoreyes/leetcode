// brute for solution
// class Node {
//     int key;
//     int value;

//     public Node(int key, int value) {
//         this.key = key;
//         this.value = value;
//     }
// }

// class LRUCache {

//     // fields of the LRU Cache
//     private ArrayList<Node> cache;
//     private int capacity;
//     private Node left;
//     private Node right;
    
//     // constructor
//     public LRUCache(int capacity) {
//         this.cache = new ArrayList<>();
//         this.capacity = capacity;
//         this.left = new Node(0, 0);
//         this.right = new Node(0, 0);
//     }
    
//     public int get(int key) {
//         // iterate through the cache and move it to the end and return the value
//         for (int i = 0; i < cache.size(); i++) {
//             if (cache.get(i).key == key) {
//                 Node temp = cache.remove(i);
//                 cache.add(temp);
//                 return temp.value;
//             }
//         }
//         return -1;
//     }
    
//     public void put(int key, int value) {
//         // remove node if it is already in the cache
//         for (int i = 0; i < cache.size(); i++) {
//             if (cache.get(i).key == key) {
//                 cache.remove(i);
//                 break;
//             }
//         }

//         // if cache is at capacity, remove the least used
//         if (cache.size() == capacity) {
//             cache.remove(0);
//         }

//         // add the new node
//         cache.add(new Node(key, value));
//     }
// }

// efficient
// complexity runtime: 
class Node {

    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }
}

class LRUCache {

    private int capacity;
    private Node left;
    private Node right;
    private HashMap<Integer, Node> cache; // key = key value = pointer to Node

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.left = new Node(0, 0);
        this.right = new Node(0,0);
        this.left.next = this.right;
        this.left.prev = null;
        this.right.prev = this.left;
        this.right.next = null;
    }

    private void insert(Node node) {
        Node prev = this.right.prev;
        prev.next = node;
        node.prev = prev;
        node.next = this.right;
        this.right.prev = node;
    }

    private void remove(Node node) {
        Node prev = node.prev;
        Node nxt = node.next;
        prev.next = nxt;
        nxt.prev = prev;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node temp = cache.get(key);
            remove(temp);
            insert(temp);
            return temp.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node temp = cache.get(key);
            cache.remove(temp.key);
            remove(temp);
        }

        if (capacity == cache.size()) {
            Node removed = left.next;
            remove(removed);
            cache.remove(removed.key);
        }

        Node added = new Node(key, value);
        cache.put(key, added);
        insert(added);
    }
}