import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

class WrongStudentName extends Exception { }
class WrongDateOfBirth extends Exception { }

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                int ex = menu();
                switch (ex) {
                    case 1: exercise1(); break;
                    case 2: exercise2(); break;
                    case 3: exercise3(); break;
                    default: return;
                }
            } catch (IOException e) {
                System.out.println("Błąd wejścia-wyjścia!");
            } catch (WrongStudentName e) {
                System.out.println("Błędne imię studenta!");
            } catch (WrongDateOfBirth e) {
                System.out.println("Niepoprawna data urodzenia! Wprowadź poprawny format DD-MM-YYYY.");
            }
        }
    }

    public static int menu() {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");
        return scan.nextInt();
    }

    public static String readName() throws WrongStudentName {
        scan.nextLine();
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();

        return name;
    }

    public static int readAge() {
        int age;
        while (true) {
            System.out.println("Podaj wiek (1-99): ");
            while (!scan.hasNextInt()) {
                System.out.println("Nieprawidłowy wiek! Wprowadź liczbę całkowitą.");
                scan.next();
            }
            age = scan.nextInt();
            if (age >= 1 && age <= 99) {
                return age;
            }
            System.out.println("Niepoprawny wiek! Wprowadź poprawną wartość w przedziale 1-99.");
        }
    }

    public static String readDateOfBirth() throws WrongDateOfBirth {
        while (true) {
            System.out.println("Podaj datę urodzenia (DD-MM-YYYY): ");
            String date = scan.next();

            // Sprawdzanie poprawności formatu DD-MM-YYYY
            if (Pattern.matches("\\d{2}-\\d{2}-\\d{4}", date)) {
                String[] parts = date.split("-");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                if (day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1000 && year <= 9999) {
                    return date;
                }
            }
            System.out.println("Niepoprawna data! Wprowadź poprawną wartość w formacie DD-MM-YYYY.");
        }
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongDateOfBirth {
        var name = readName();
        var age = readAge();
        var date = readDateOfBirth();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for (Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imię: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}
