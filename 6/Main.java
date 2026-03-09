import java.util.Arrays;
import java.util.Scanner;

import java.io.*;
//_______________________________________________________
import java.util.Iterator;
//_______________________________________________________

interface CourseInterface extends Iterable<Integer>{
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

interface CourseFactory {
    // Создает объект курса по умолчанию
    CourseInterface createInstance();
}

class OnlineCourseFactory implements CourseFactory {
    @Override
    public CourseInterface createInstance() {
        return new OnlineCourse();  // вызов конструктора по умолчанию
    }
}

class OfflineCourseFactory implements CourseFactory {
    @Override
    public CourseInterface createInstance() {
        return new OfflineCourse();  // вызов конструктора по умолчанию
    }
}

class UnmodifiableCourse implements CourseInterface {

    private final CourseInterface course;

    public UnmodifiableCourse(CourseInterface course) {
        this.course = course;
    }

    // ===== методы чтения (разрешены) =====

    @Override
    public int[] getModules() {
        return course.getModules();
    }

    @Override
    public String getName() {
        return course.getName();
    }

    @Override
    public int getExtraHours() {
        return course.getExtraHours();
    }

    @Override
    public int calculateTotalHours() {
        return course.calculateTotalHours();
    }

    @Override
    public void output(OutputStream out) throws IOException {
        course.output(out);
    }

    @Override
    public void write(Writer out) throws IOException {
        course.write(out);
    }

    @Override
    public Iterator<Integer> iterator() {
        return course.iterator();
    }

    // ===== методы изменения (запрещены) =====

    @Override
    public void setModules(int[] modules) {
        throw new UnsupportedOperationException("Объект только для чтения!");
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Объект только для чтения!");
    }

    @Override
    public void setExtraHours(int hours) {
        throw new UnsupportedOperationException("Объект только для чтения!");
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

class CourseIterator implements Iterator<Integer> {

    private int[] modules;
    private int index = 0;

    public CourseIterator(int[] modules) {
        this.modules = modules;
    }

    @Override
    public boolean hasNext() {
        return index < modules.length;
    }

    @Override
    public Integer next() {
        return modules[index++];
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
	@Override
	public Iterator<Integer> iterator() {
		return new CourseIterator(modules);
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
	@Override
	public Iterator<Integer> iterator() {
		return new CourseIterator(modules);
	}
	//_______________________________________________________

}

public class CourseIO {
	
	private static CourseFactory factory = new OnlineCourseFactory(); // по умолчанию OnlineCourse
	public static void setCourseFactory(CourseFactory f) {
    if (f == null)
        throw new IllegalArgumentException("Фабрика не может быть null");
    factory = f;
	}
	public static CourseInterface createInstance() {
    return factory.createInstance();
	}
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
	
	public static CourseInterface unmodifiableCourse(CourseInterface o) {
    return new UnmodifiableCourse(o);
	}

    public static CourseInterface deserializeCourse(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (CourseInterface) ois.readObject();
    }
}


public class Main {
    public static void main(String[] args) {

        try {
			CourseFactory factory = new OnlineCourseFactory();
			CourseInterface course1 = factory.createInstance();

			System.out.println(course1);  // выведет default OnlineCourse
			
			// по умолчанию OnlineCourse
			CourseInterface c1 = CourseIO.createInstance(); // через фабрику (будет OnlineCourse)

			// смена фабрики на OfflineCourse
			CourseIO.setCourseFactory(new OfflineCourseFactory());
			CourseInterface c2 = CourseIO.createInstance(); // теперь будет OfflineCourse
			
            CourseInterface[] database = new CourseInterface[4];

            database[0] = new OnlineCourse(new int[]{10, 20}, "Java Online", 5);
			database[0] = CourseIO.unmodifiableCourse(database[0]);
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

			// выбираем фабрику
			if (choice == 1)
				CourseIO.setCourseFactory(new OnlineCourseFactory());
			else
				CourseIO.setCourseFactory(new OfflineCourseFactory());

			// создаем объект через фабрику
			CourseInterface course = CourseIO.createInstance();

			// задаем поля
			course.setName(name);
			course.setExtraHours(extra);
			course.setModules(modules);

			System.out.println("\nОбъект успешно создан:");
			System.out.println(course);
			
			
			System.out.println("\nОбъект успешно создан:");			
			System.out.println(course);
			System.out.println("Результат функционального метода (total hours): "
        + course.calculateTotalHours());
		
		System.out.println("\nПроверка итератора (модули курса):");

		for (int m : course) {
			System.out.println("Модуль часов: " + m);
		}
        } catch (InvalidDataException e) {
            System.out.println("Ошибка данных: " + e.getMessage());
        }
		
		
		
    }
}