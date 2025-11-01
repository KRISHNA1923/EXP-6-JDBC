package view;

import controller.StudentDAO;
import model.Student;
import java.util.*;

public class StudentApp {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            StudentDAO dao = new StudentDAO();
            while (true) {
                System.out.println("\n------ Student Management ------");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Marks");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();

                switch (ch) {
                    case 1 -> {
                        System.out.print("Enter ID, Name, Dept, Marks: ");
                        dao.addStudent(new Student(sc.nextInt(), sc.next(), sc.next(), sc.nextDouble()));
                    }
                    case 2 -> {
                        System.out.println("ID\tName\tDepartment\tMarks");
                        System.out.println("-----------------------------------");
                        dao.getAllStudents().forEach(System.out::println);
                    }
                    case 3 -> {
                        System.out.print("Enter ID and new Marks: ");
                        dao.updateMarks(sc.nextInt(), sc.nextDouble());
                    }
                    case 4 -> {
                        System.out.print("Enter ID to delete: ");
                        dao.deleteStudent(sc.nextInt());
                    }
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
