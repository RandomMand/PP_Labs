import java.util.Arrays;
import java.util.Scanner;

interface CourseInterface {
    int[] getModules();
    String getName();
    int getExtraHours();

    void setModules(int[] modules) throws InvalidDataException;
    void setName(String name);
    void setExtraHours(int hours);

    int calculateTotalHours();
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

class OnlineCourse implements CourseInterface {

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
}

class OfflineCourse implements CourseInterface {

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

        } catch (InvalidDataException e) {
            System.out.println("Ошибка данных: " + e.getMessage());
        }
    }
}