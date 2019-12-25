package xyz.mdou.quickstart;

import lombok.Data;

public class Linked<T> {

    private Node<T> head;

    public Linked() {
        head = null;
    }

    public void addNode(Node<T> node) {
        if (head == null) {
            head = node;
            return;
        }
        Node<T> n = head;
        while (n.next != null) {
            n = n.next;
        }
        n.next = node;
    }

    public void reversalNodes() {
        if (head == null || head.next == null) {
            return;
        }
        Node<T> pre = head;
        Node<T> curr = head.next;
        while (curr != null) {
            Node<T> next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        head.next = null;
        head = pre;
    }

    public void reversalNodes2() {
        head = reversalNodes(head);
    }

    public Node<T> reversalNodes(Node<T> node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node<T> newHead = reversalNodes(node.next);
        node.next.next = node;
        node.next = null;
        return newHead;
    }

    public void printNodes() {
        Node<T> n = head;
        while (n != null) {
            System.out.println(n.t);
            n = n.next;
        }
    }

    public static void main(String[] args) {
        Linked<Integer> linked = new Linked<>();
        linked.addNode(new Node<Integer>(1));
        linked.addNode(new Node<Integer>(2));
        linked.addNode(new Node<Integer>(3));
        linked.addNode(new Node<Integer>(4));
        linked.printNodes();
        linked.reversalNodes();
        linked.printNodes();
        linked.reversalNodes2();
        linked.printNodes();
    }

    @Data
    private static class Node<T> {

        private T t;

        private Node<T> next;

        public Node(T t) {
            this(t, null);
        }

        public Node(T t, Node<T> next) {
            this.t = t;
            this.next = next;
        }
    }
}
