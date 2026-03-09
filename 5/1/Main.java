import java.lang.Thread;
import java.util.Random;

class SharedBuffer {

    private int[] buffer; // массив данных

    // конструктор
    public SharedBuffer(int size) {
        buffer = new int[size];

        // изначально массив заполнен нулями
        for (int i = 0; i < size; i++) {
            buffer[i] = 0;
        }
    }

    // метод записи значения в массив
    public synchronized void write(int position, int value) {

        buffer[position] = value;

        System.out.println("Write: " + value + " to position " + position);
    }

    // метод чтения значения
    public synchronized int read(int position) {

        int value = buffer[position];

        System.out.println("Read: " + value + " from position " + position);

        return value;
    }

    // метод получения размера массива
    public int getSize() {
        return buffer.length;
    }
}

class WriterThread extends Thread {

    private SharedBuffer buffer;

    public WriterThread(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        Random random = new Random();

        for (int i = 0; i < buffer.getSize(); i++) {

            // генерируем случайное число (не 0)
            int value = random.nextInt(100) + 1;

            buffer.write(i, value);

            try {
                Thread.sleep(100); // небольшая пауза
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ReaderThread extends Thread {

    private SharedBuffer buffer;

    public ReaderThread(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 0; i < buffer.getSize(); i++) {

            buffer.read(i);

            try {
                Thread.sleep(150); // небольшая пауза
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {

        // создаем общий объект
        SharedBuffer buffer = new SharedBuffer(10);

        // создаем нити
        WriterThread writer = new WriterThread(buffer);
        ReaderThread reader = new ReaderThread(buffer);

        // устанавливаем приоритеты
        writer.setPriority(Thread.MAX_PRIORITY);
        reader.setPriority(Thread.MIN_PRIORITY);

        // запускаем нити
        writer.start();
        reader.start();
    }
}

/*
writer.setPriority(Thread.NORM_PRIORITY);
reader.setPriority(Thread.NORM_PRIORITY);
writer.setPriority(Thread.MAX_PRIORITY);
reader.setPriority(Thread.MIN_PRIORITY);
*/