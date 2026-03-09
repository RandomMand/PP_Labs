import java.util.Arrays;
import java.util.Scanner;
//_______________________________________________________
import java.io.*;
//_______________________________________________________

interface CourseInterface {
    int[] getModules();
    String getName();
    int getExtraHours();

    void setModules(int[] modules) throws InvalidDataException;
    void setName(String name);
    void setExtraHours(int hours);

    int calculateTotalHours();
	//_______________________________________________________
	 // запись в байтовый поток
    void output(OutputStream out) throws IOException;

    // запись в символьный поток
    void write(Writer out) throws IOException;
	//_______________________________________________________
	
}

class SynchronizedCourse implements CourseInterface {

    private final CourseInterface course;

    public SynchronizedCourse(CourseInterface course) {
        this.course = course;
    }

    @Override
    public synchronized int[] getModules() {
        return course.getModules();
    }

    @Override
    public synchronized String getName() {
        return course.getName();
    }

    @Override
    public synchronized int getExtraHours() {
        return course.getExtraHours();
    }

    @Override
    public synchronized void setModules(int[] modules) throws InvalidDataException {
        course.setModules(modules);
    }

    @Override
    public synchronized void setName(String name) {
        course.setName(name);
    }

    @Override
    public synchronized void setExtraHours(int hours) {
        course.setExtraHours(hours);
    }

    @Override
    public synchronized int calculateTotalHours() {
        return course.calculateTotalHours();
    }

    @Override
    public synchronized void output(OutputStream out) throws IOException {
        course.output(out);
    }

    @Override
    public synchronized void write(Writer out) throws IOException {
        course.write(out);
    }
}

class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

class NegativeHoursException extends RuntimeException {
    public NegativeHoursException(String message) {
        super(message);
    }
}

class OnlineCourse implements CourseInterface, Serializable {

    private int[] modules;
    private String name;
    private int extraHours;

    // Конструктор по умолчанию
    public OnlineCourse() {
        this.modules = new int[]{10, 10};
        this.name = "Default Online";
        this.extraHours = 2;
    }

    // Конструктор с параметрами
    public OnlineCourse(int[] modules, String name, int extraHours) throws InvalidDataException {
        setModules(modules);
        this.name = name;
        this.extraHours = extraHours;
    }

    public int[] getModules() {
        return modules;
    }

    public String getName() {
        return name;
    }

    public int getExtraHours() {
        return extraHours;
    }

    public void setModules(int[] modules) throws InvalidDataException {
        if (modules == null || modules.length == 0)
            throw new InvalidDataException("Массив модулей пустой!");
        this.modules = modules;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtraHours(int hours) {
        if (hours < 0)
            throw new NegativeHoursException("Часы не могут быть отрицательными!");
        this.extraHours = hours;
    }

    // Функциональный метод
    public int calculateTotalHours() {
        int sum = 0;
        for (int m : modules) {
            sum += m;
        }
        return sum + extraHours;
    }

    public String toString() {
        return "OnlineCourse: " + name +
                ", modules=" + Arrays.toString(modules) +
                ", extra=" + extraHours +
                ", total=" + calculateTotalHours();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof OnlineCourse)) return false;
        OnlineCourse other = (OnlineCourse) obj;
        return this.name.equals(other.name);
    }

    public int hashCode() {
        return name.hashCode();
    }
	
	
    //_______________________________________________________

	    @Override
    public void output(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeUTF("ONLINE");          // тип объекта
        dos.writeUTF(name);
        dos.writeInt(extraHours);

        dos.writeInt(modules.length);
        for (int m : modules) {
            dos.writeInt(m);
        }
    }
	
    @Override
    public void write(Writer out) throws IOException {
        out.write("ONLINE ");
        out.write(name + " ");
        out.write(extraHours + " ");
        out.write(modules.length + " ");

        for (int m : modules) {
            out.write(m + " ");
        }

        out.write("\n");
    }
	//_______________________________________________________
}

class OfflineCourse implements CourseInterface, Serializable {

    private int[] modules;
    private String name;
    private int extraHours;

    public OfflineCourse() {
        this.modules = new int[]{15, 15};
        this.name = "Default Offline";
        this.extraHours = 5;
    }

    public OfflineCourse(int[] modules, String name, int extraHours) throws InvalidDataException {
        setModules(modules);
        this.name = name;
        this.extraHours = extraHours;
    }

    public int[] getModules() {
        return modules;
    }

    public String getName() {
        return name;
    }

    public int getExtraHours() {
        return extraHours;
    }

    public void setModules(int[] modules) throws InvalidDataException {
        if (modules == null || modules.length == 0)
            throw new InvalidDataException("Массив модулей пустой!");
        this.modules = modules;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtraHours(int hours) {
        if (hours < 0)
            throw new NegativeHoursException("Часы не могут быть отрицательными!");
        this.extraHours = hours;
    }

    public int calculateTotalHours() {
        int sum = 0;
        for (int m : modules) {
            sum += m;
        }
        return sum + extraHours;
    }

    public String toString() {
        return "OfflineCourse: " + name +
                ", modules=" + Arrays.toString(modules) +
                ", extra=" + extraHours +
                ", total=" + calculateTotalHours();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof OfflineCourse)) return false;
        OfflineCourse other = (OfflineCourse) obj;
        return this.name.equals(other.name);
    }

    public int hashCode() {
        return name.hashCode();
    }
	
	//_______________________________________________________
	@Override
    public void output(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeUTF("OFFLINE");
        dos.writeUTF(name);
        dos.writeInt(extraHours);

        dos.writeInt(modules.length);
        for (int m : modules) {
            dos.writeInt(m);
        }
    }

    @Override
    public void write(Writer out) throws IOException {
        out.write("OFFLINE ");
        out.write(name + " ");
        out.write(extraHours + " ");
        out.write(modules.length + " ");

        for (int m : modules) {
            out.write(m + " ");
        }

        out.write("\n");
    }
	//_______________________________________________________

}

public class CourseIO {

    // ===== Байтовая запись =====
    public static void outputCourse(CourseInterface o, OutputStream out) throws IOException {
        o.output(out);
    }

    // ===== Байтовое чтение =====
    public static CourseInterface inputCourse(InputStream in) throws IOException, InvalidDataException {

        DataInputStream dis = new DataInputStream(in);

        String type = dis.readUTF();
        String name = dis.readUTF();
        int extra = dis.readInt();

        int size = dis.readInt();
        int[] modules = new int[size];
        for (int i = 0; i < size; i++) {
            modules[i] = dis.readInt();
        }

        if (type.equals("ONLINE"))
            return new OnlineCourse(modules, name, extra);
        else
            return new OfflineCourse(modules, name, extra);
    }

    // ===== Символьная запись =====
    public static void writeCourse(CourseInterface o, Writer out) throws IOException {
        o.write(out);
    }

    // ===== Символьное чтение =====
    public static CourseInterface readCourse(Reader in) throws Exception {

        BufferedReader br = new BufferedReader(in);
        String line = br.readLine();

        if (line == null) return null;

        String[] parts = line.split(" ");

        String type = parts[0];
        String name = parts[1];
        int extra = Integer.parseInt(parts[2]);
        int size = Integer.parseInt(parts[3]);

        int[] modules = new int[size];
        for (int i = 0; i < size; i++) {
            modules[i] = Integer.parseInt(parts[4 + i]);
        }

        if (type.equals("ONLINE"))
            return new OnlineCourse(modules, name, extra);
        else
            return new OfflineCourse(modules, name, extra);
    }

    // ===== Сериализация =====
    public static void serializeCourse(CourseInterface o, OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(o);
    }

	 public static CourseInterface synchronizedCourse(CourseInterface i) {
        return new SynchronizedCourse(i);
    }

    public static CourseInterface deserializeCourse(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (CourseInterface) ois.readObject();
    }
	
}


public class Main {
    public static void main(String[] args) {

        try {

            CourseInterface[] database = new CourseInterface[4];
			
            database[0] = new OnlineCourse(new int[]{10, 20}, "Java Online", 5);
            database[1] = new OfflineCourse(new int[]{15, 10}, "Java Offline", 10);
            database[2] = new OnlineCourse(new int[]{20, 15}, "C++ Online", 5);
            database[3] = new OfflineCourse(new int[]{10, 20}, "Python Offline", 5);

            // 1. Вывод всей информации
            System.out.println("=== Все объекты ===");
            for (CourseInterface c : database) {
                System.out.println(c);
            }

            // 2. Поиск одинаковых результатов
            System.out.println("\n=== Одинаковые результаты ===");
            for (int i = 0; i < database.length; i++) {
                for (int j = i + 1; j < database.length; j++) {
                    if (database[i].calculateTotalHours() ==
                            database[j].calculateTotalHours()) {

                        System.out.println(database[i]);
                        System.out.println(database[j]);
                        System.out.println("---");
                    }
                }
            }

            // 3. Разделение по типам
            System.out.println("\n=== Online ===");
            for (CourseInterface c : database) {
                if (c instanceof OnlineCourse)
                    System.out.println(c);
            }

            System.out.println("\n=== Offline ===");
            for (CourseInterface c : database) {
                if (c instanceof OfflineCourse)
                    System.out.println(c);
            }
			
			Scanner scanner = new Scanner(System.in);

			System.out.println("\n1 - Online, 2 - Offline");
			int choice = scanner.nextInt();

			System.out.println("Введите название:");
			String name = scanner.next();

			System.out.println("Введите доп часы:");
			int extra = scanner.nextInt();

			int[] modules = {10, 20};

			CourseInterface course;

			if (choice == 1)
				course = new OnlineCourse(modules, name, extra);
			else
				course = new OfflineCourse(modules, name, extra);
			
			course = CourseIO.synchronizedCourse(course);
			
			System.out.println("\nОбъект успешно создан:");
			System.out.println(course);

			System.out.println("Результат функционального метода (total hours): "
        + course.calculateTotalHours());
        } catch (InvalidDataException e) {
            System.out.println("Ошибка данных: " + e.getMessage());
        }
		
		
		
    }
}