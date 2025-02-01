import java.util.Comparator;

/**
 * Класс, предоставляющий базовый набор инструментов для работы с двусвязным списком.
 *
 * @param <T> тип элементов списка.
 */
public class MyLinkedList<T> implements MyList<T> {
    int size;       // Текущий размер списка.
    Node<T> head;   // Начало списка.
    Node<T> tail;   // Конец списка.

    public void add(T elem) {
        Node<T> node = new Node<>(elem);

        if (head == null || tail == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }

        tail = node;
        size++;
    }

    /**
     * Добавляет элемент в начало списка.
     *
     * @param elem элемент, который будет добавлен.
     */
    public void addFirst(T elem) {
        Node<T> node = new Node<>(elem);

        if (head == null || tail == null) {
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
        }

        head = node;
        size++;
    }

    public void add(int index, T elem) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            addFirst(elem);
            return;
        }
        if (index == size) {
            add(elem);
            return;
        }

        Node<T> node = getNode(index);
        node.prev.next = new Node<>(elem, node.prev, node);
        node.prev = node.prev.next;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return getNode(index).data;
    }

    /**
     * Удаляет первый элемент списка.
     */
    public void removeFirst() {
        if (head == null || tail == null) return;

        Node<T> node = head;
        head = head.next;
        node.next.prev = null;
        node.next = null;
        size--;
    }

    /**
     * Удаляет последний элемент списка.
     */
    public void removeLast() {
        if (head == null || tail == null) return;

        Node<T> node = tail;
        tail = tail.prev;
        node.prev.next = null;
        node.prev = null;
        size--;
    }

    public boolean remove(T elem) {
        Node<T> node = getNode(elem);

        if (node == null) return false;

        if (node == head) removeFirst();
        else if (node == tail) removeLast();
        else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
            size--;
        }

        return true;
    }

    public void clear() {
        if (head == null || tail == null) return;

        Node<T> node = head.next;

        for (int i = 0; i < size - 1; i++) {
            node.prev.prev = null;
            node.prev.next = null;
            node = node.next;
        }
        tail.prev = null;

        head = null;
        tail = null;
        size = 0;
    }

    public void sort(Comparator<? super T> c) {
        if (head == null || tail == null) return;

        Node<T> current = head.next;

        while (current != null) {
            T key = current.data;
            Node<T> prevNode = current.prev;

            while (prevNode != null && c.compare(prevNode.data, key) > 0) {
                prevNode.next.data = prevNode.data;
                prevNode = prevNode.prev;
            }

            if (prevNode == null) {
                head.data = key;
            } else {
                prevNode.next.data = key;
            }

            current = current.next;
        }
    }

    /**
     * Метод для получения узла списка по индексу.
     *
     * @param index индекс узла, который нужно получить.
     * @return необходимый узел.
     */
    private Node<T> getNode(int index) {
        Node<T> node;

        if (index < (size / 2)) {
            node = head;

            for (int i = 0; i < index; i++)
                node = node.next;

        } else {
            node = tail;

            for (int i = size; i > index + 1; i--)
                node = node.prev;

        }

        return node;
    }

    /**
     * Метод для получения узла по данным в нем.
     *
     * @param elem элемент, который нужно получить.
     * @return узел, содержащий необходимый элемент.
     */
    private Node<T> getNode(T elem) {
        Node<T> node = head;

        for (int i = 0; i < size; i++) {
            if (node.data.equals(elem)) return node;
            node = node.next;
        }

        return node;
    }

    /**
     * Определяет как будет выводиться информация о списке.
     *
     * @return строка, содержащая данные об элементах в списке.
     */
    public String toString() {
        if (head == null || tail == null) return "[]";

        StringBuilder builder = new StringBuilder();
        Node<T> node = head;

        builder.append("[");
        for (int i = 0; i < size - 1; i++) {
            builder.append(node.data.toString()).append(", ");
            node = node.next;
        }
        builder.append(tail.data.toString()).append("]");

        return builder.toString();
    }

    /**
     * Класс, представляющий собой узел списка, содержащий в себе элемент и ссылки на предыдущий, и следующий узел.
     *
     * @param <T> тип элементов узла.
     */
    private static class Node<T> {
        Node<T> next;   // Ссылка на следующий узел.
        Node<T> prev;   // Ссылка на предыдущий узел.
        T data;         // Элемент, содержащийся в узле.

        Node(T data) {
            this.data = data;
            prev = null;
            next = null;
        }

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
