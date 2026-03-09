import java.lang.Thread;
import java.util.Random;

class SharedBuffer {

    private double[] buffer;

    public SharedBuffer(int size) {
        buffer = new double[size];
    }

    public void setElement(int index, double value) {
        buffer[index] = value;
    }

    public double getElement(int index) {
        return buffer[index];
    }

    public int getSize() {
        return buffer.length;
    }
}

public class Synchronizer {

    private SharedBuffer buffer; // массив

    private volatile int current = 0; // текущая позиция

    private Object lock = new Object(); // объект блокировки

    private boolean set = false; // записано ли значение

    public Synchronizer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    // метод чтения
    public double read() throws InterruptedException {

        double value;

        synchronized (lock) {

            if (!canRead())
                throw new InterruptedException();

            while (!set) {
                lock.wait(); // ждём пока появится запись
            }

            value = buffer.getElement(current);

            System.out.println("Read: " + value + " from position " + current);

            current++;

            set = false;

            lock.notifyAll();
        }

        return value;
    }

    // метод записи
    public void write(double value) throws InterruptedException {

        synchronized (lock) {

            if (!canWrite())
                throw new InterruptedException();

            while (set) {
                lock.wait(); // ждём пока прочитают
            }

            buffer.setElement(current, value);

            System.out.println("Write: " + value + " to position " + current);

            set = true;

            lock.notifyAll();
        }
    }

    public boolean canRead() {
        return current < buffer.getSize();
    }

    public boolean canWrite() {
        return current < buffer.getSize();
    }
}

class WriterRunnable implements Runnable {

    private Synchronizer sync;

    public WriterRunnable(Synchronizer sync) {
        this.sync = sync;
    }

    public void run() {

        Random random = new Random();

        try {

            while (true) {

                double value = random.nextInt(100) + 1;

                sync.write(value);

                Thread.sleep(100);
            }

        } catch (InterruptedException e) {
            // поток завершился
        }
    }
}

class ReaderRunnable implements Runnable {

    private Synchronizer sync;

    public ReaderRunnable(Synchronizer sync) {
        this.sync = sync;
    }

    public void run() {

        try {

            while (true) {

                sync.read();

                Thread.sleep(100);
            }

        } catch (InterruptedException e) {
            // поток завершился
        }
    }
}

public class Main {

    public static void main(String[] args) {

        SharedBuffer buffer = new SharedBuffer(10);

        Synchronizer sync = new Synchronizer(buffer);

        WriterRunnable writer = new WriterRunnable(sync);
        ReaderRunnable reader = new ReaderRunnable(sync);

        Thread writerThread = new Thread(writer);
        Thread readerThread = new Thread(reader);

        writerThread.setPriority(Thread.MAX_PRIORITY);
        readerThread.setPriority(Thread.MIN_PRIORITY);

        writerThread.start();
        readerThread.start();
    }
}