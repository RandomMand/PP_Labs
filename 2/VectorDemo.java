class Vector {
    private double[] data;
    private int length;

    // Конструктор
    public Vector(int size) {
        if (size <= 0) {
            System.out.println("Ошибка: размер должен быть > 0");
            size = 1;
        }
        length = size;
        data = new double[length];
    }

    // Получить длину вектора (количество элементов)
    public int getLength() {
        return length;
    }

    // Получить элемент
    public double getElement(int index) {
        if (index < 0 || index >= length) {
            System.out.println("Ошибка индекса");
            return 0;
        }
        return data[index];
    }

    // Установить элемент
    public void setElement(int index, double value) {
        if (index < 0 || index >= length) {
            System.out.println("Ошибка индекса");
            return;
        }
        data[index] = value;
    }

    // Найти минимум
    public double getMin() {
        double min = data[0];
        for (int i = 1; i < length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }

    // Найти максимум
    public double getMax() {
        double max = data[0];
        for (int i = 1; i < length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    // Сортировка по возрастанию (пузырьком)
    public void sortAscending() {
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    double temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    // Евклидова норма
    public double getNorm() {
        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum += data[i] * data[i];
        }
        return Math.sqrt(sum);
    }

    // Умножение на число
    public Vector multiply(double number) {
        Vector result = new Vector(length);
        for (int i = 0; i < length; i++) {
            result.data[i] = data[i] * number;
        }
        return result;
    }

    // Сложение двух векторов
    public Vector add(Vector other) {
        if (this.length != other.length) {
            System.out.println("Ошибка: разные размеры векторов");
            return null;
        }

        Vector result = new Vector(length);
        for (int i = 0; i < length; i++) {
            result.data[i] = this.data[i] + other.data[i];
        }
        return result;
    }

    // Скалярное произведение
    public double dotProduct(Vector other) {
        if (this.length != other.length) {
            System.out.println("Ошибка: разные размеры векторов");
            return 0;
        }

        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum += this.data[i] * other.data[i];
        }
        return sum;
    }

    // Вывод вектора
    public void print() {
        System.out.print("[ ");
        for (int i = 0; i < length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println("]");
    }
}

public class VectorDemo {

    public static void main(String[] args) {

        // Создаем два вектора длины 3
        Vector v1 = new Vector(3);
        Vector v2 = new Vector(3);

        // Заполняем v1
        v1.setElement(0, 3);
        v1.setElement(1, 1);
        v1.setElement(2, 2);

        // Заполняем v2
        v2.setElement(0, 4);
        v2.setElement(1, 5);
        v2.setElement(2, 6);

        System.out.println("Вектор v1:");
        v1.print();

        System.out.println("Вектор v2:");
        v2.print();

        // Минимум и максимум
        System.out.println("Min v1 = " + v1.getMin());
        System.out.println("Max v1 = " + v1.getMax());

        // Сортировка
        v1.sortAscending();
        System.out.println("v1 после сортировки:");
        v1.print();

        // Норма
        System.out.println("Норма v1 = " + v1.getNorm());

        // Умножение на число
        Vector v3 = v1.multiply(2);
        System.out.println("v1 * 2:");
        v3.print();

        // Сложение
        Vector v4 = v1.add(v2);
        System.out.println("v1 + v2:");
        v4.print();

        // Скалярное произведение
        double dot = v1.dotProduct(v2);
        System.out.println("Скалярное произведение v1 и v2 = " + dot);

        // Длина вектора
        System.out.println("Длина v1 = " + v1.getLength());

        // Получение элемента
        System.out.println("Элемент v1[0] = " + v1.getElement(0));
    }
}