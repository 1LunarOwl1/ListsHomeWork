import java.util.Comparator;

/**
 * Класс для работы с динамическими массивами.
 * Реализует интерфейс MyList.
 *
 * @param <T> тип элементов списка.
 */
public class MyArrayList<T> implements MyList<T> {
    private static final int DEFAULT_SIZE = 10;    // Стандартный размер массива.
    private int size;                       // Текущий размер массива.
    private int elNumber;                   // Количество элементов в массиве.
    private T[] array;                      // Массив, с которым проводятся манипуляции.

    public MyArrayList() {
        size = DEFAULT_SIZE;
        elNumber = 0;
        array = null;
    }

    /**
     * Создает массив с указанным размером.
     *
     * @param size размер массива.
     */
    public MyArrayList(int size) {
        if (size < 0) throw new IllegalArgumentException();

        this.size = size;
        elNumber = 0;
        array = null;
    }

    public void add(T elem) {
        if (elNumber == size || array == null) resize();
        array[elNumber++] = elem;
    }

    public void add(int index, T elem) {
        if (index > elNumber || index < 0) throw new IndexOutOfBoundsException();
        else if (elNumber == size || array == null) resize();

        for (int i = size - 1; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = elem;
        elNumber++;
    }

    public T get(int index) {
        if (index >= elNumber || index < 0) throw new IndexOutOfBoundsException();
        return array[index];
    }

    public boolean remove(T elem) {
        int elIndex = -1;

        for (int i = 0; i < size; i++) {
            if (array[i].equals(elem)) {
                elIndex = i;
                break;
            }
        }

        if (elIndex == -1) return false;

        for (int i = elIndex; i < elNumber - 1; i++) {
            array[i] = array[i + 1];
        }
        array[elNumber - 1] = null;

        elNumber--;
        size--;
        return true;
    }

    public void clear() {
        for (int i = 0; i < size; i++) array[i] = null;
        elNumber = 0;
    }

    public void sort(Comparator<? super T> c) {
        if (elNumber < 2) {
            return;
        }
        for (int i = 1; i < elNumber; i++) {
            for (int j = 0; j < elNumber - i; j++) {
                T a = array[j];
                T b = array[j + 1];

                if (a == null && b == null) continue;

                if (a == null) {
                    array[j] = b;
                    array[j + 1] = a;
                    continue;
                }

                if (b == null) continue;

                if (c.compare(array[j], array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Задает как будет выводиться информация о массиве.
     *
     * @return строка с информацией о данных, содержащихся в массиве.
     */
    public String toString() {
        if (elNumber == 0) return "[]";

        StringBuilder builder = new StringBuilder();

        builder.append("[");
        for (int i = 0; i < elNumber - 1; i++) {
            builder.append(array[i]).append(", ");
        }
        builder.append(array[elNumber - 1]).append("]");

        return builder.toString();
    }

    /**
     * Заменяет текущий массив новым, с теми же данными и более высоким размером.
     * Если массив не создан, создает его со стандартным размером.
     */
    private void resize() {
        if (array == null) {
            array = (T[]) new Object[size];
            return;
        }

        int newSize = (int) (size * 1.5) + 1;
        T[] newArray = (T[]) new Object[newSize];

        if (size > 0) System.arraycopy(array, 0, newArray, 0, size);

        array = newArray;
        size = newSize;
    }
}
