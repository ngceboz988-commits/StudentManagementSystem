
package studentmanagementsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Project 3: Student Management System
 * Tech: Java, Object-Oriented Programming (OOP)
 *
 * This console-based application manages student records, demonstrating
 * key OOP principles like Encapsulation, Inheritance, and Polymorphism.
 */

// --------------------------------------------------------
// BASE CLASS: Student (Demonstrates Encapsulation)
// --------------------------------------------------------
class Student {
    // Encapsulated Fields (Private access)
    private String studentId;
    private String name;
    private int age;
    private double gpa;

    // Constructor
    public Student(String studentId, String name, int age, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    // Public Getter Methods
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }

    // Public Setter Methods (for editing/updating records)
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    // Polymorphic method to display details (to be overridden by subclasses)
    public void displayDetails() {
        System.out.println("ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.printf("GPA: %.2f\n", gpa);
    }
}

// --------------------------------------------------------
// SUBCLASS 1: UndergraduateStudent (Demonstrates Inheritance)
// --------------------------------------------------------
class UndergraduateStudent extends Student {
    private String major;

    public UndergraduateStudent(String studentId, String name, int age, double gpa, String major) {
        // Calls the constructor of the base class (Student)
        super(studentId, name, age, gpa);
        this.major = major;
    }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    // Overriding the displayDetails method (Demonstrates Polymorphism)
    @Override
    public void displayDetails() {
        System.out.println("\n--- Undergraduate Student ---");
        super.displayDetails(); // Call base class method
        System.out.println("Major: " + major);
    }
}

// --------------------------------------------------------
// SUBCLASS 2: GraduateStudent (Demonstrates Inheritance)
// --------------------------------------------------------
class GraduateStudent extends Student {
    private String thesisTopic;

    public GraduateStudent(String studentId, String name, int age, double gpa, String thesisTopic) {
        super(studentId, name, age, gpa);
        this.thesisTopic = thesisTopic;
    }

    public String getThesisTopic() { return thesisTopic; }
    public void setThesisTopic(String thesisTopic) { this.thesisTopic = thesisTopic; }

    // Overriding the displayDetails method (Demonstrates Polymorphism)
    @Override
    public void displayDetails() {
        System.out.println("\n--- Graduate Student ---");
        super.displayDetails(); // Call base class method
        System.out.println("Thesis Topic: " + thesisTopic);
    }
}

// --------------------------------------------------------
// MAIN SYSTEM CLASS
// --------------------------------------------------------
public class StudentManagementSystem {
    private final List<Student> students;
    private final Scanner scanner;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Add some initial data
        students.add(new UndergraduateStudent("U001", "Alice Smith", 20, 3.85, "Computer Science"));
        students.add(new GraduateStudent("G002", "Bob Johnson", 25, 3.92, "Machine Learning Algorithms"));
    }

    // Helper method to find a student by ID
    private Student findStudent(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // 1. ADD Student Functionality
    public void addStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter ID (e.g., U100 or G200): ");
        String id = scanner.nextLine().trim();

        if (findStudent(id) != null) {
            System.out.println("Error: Student with ID " + id + " already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Is this an (U)ndergraduate or (G)raduate student? (U/G): ");
        String type = scanner.nextLine().trim().toUpperCase();

        if (type.equals("U")) {
            System.out.print("Enter Major: ");
            String major = scanner.nextLine().trim();
            students.add(new UndergraduateStudent(id, name, age, gpa, major));
            System.out.println("Successfully added Undergraduate Student: " + name);
        } else if (type.equals("G")) {
            System.out.print("Enter Thesis Topic: ");
            String topic = scanner.nextLine().trim();
            students.add(new GraduateStudent(id, name, age, gpa, topic));
            System.out.println("Successfully added Graduate Student: " + name);
        } else {
            System.out.println("Invalid student type. Addition cancelled.");
        }
    }

    // 2. VIEW All Students Functionality
    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\nNo student records available.");
            return;
        }
        System.out.println("\n--- All Student Records (" + students.size() + ") ---");
        // Using streams for clean data presentation
        students.forEach(Student::displayDetails);
    }

    // 3. EDIT Student Functionality
    public void editStudent() {
        System.out.println("\n--- Edit Student Record ---");
        System.out.print("Enter ID of student to edit: ");
        String id = scanner.nextLine().trim();
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Error: Student with ID " + id + " not found.");
            return;
        }

        System.out.println("Editing Student: " + student.getName() + " (ID: " + id + ")");
        System.out.print("Enter new Name (current: " + student.getName() + "): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            student.setName(newName);
        }

        System.out.print("Enter new Age (current: " + student.getAge() + "): ");
        String newAgeStr = scanner.nextLine().trim();
        if (!newAgeStr.isEmpty()) {
            student.setAge(Integer.parseInt(newAgeStr));
        }

        System.out.print("Enter new GPA (current: " + student.getGpa() + "): ");
        String newGpaStr = scanner.nextLine().trim();
        if (!newGpaStr.isEmpty()) {
            student.setGpa(Double.parseDouble(newGpaStr));
        }

        // Edit specific fields based on the actual class type (Polymorphism in action)
        if (student instanceof UndergraduateStudent) {
            UndergraduateStudent uStudent = (UndergraduateStudent) student;
            System.out.print("Enter new Major (current: " + uStudent.getMajor() + "): ");
            String newMajor = scanner.nextLine().trim();
            if (!newMajor.isEmpty()) {
                uStudent.setMajor(newMajor);
            }
        } else if (student instanceof GraduateStudent) {
            GraduateStudent gStudent = (GraduateStudent) student;
            System.out.print("Enter new Thesis Topic (current: " + gStudent.getThesisTopic() + "): ");
            String newTopic = scanner.nextLine().trim();
            if (!newTopic.isEmpty()) {
                gStudent.setThesisTopic(newTopic);
            }
        }

        System.out.println("\nRecord updated successfully.");
        student.displayDetails();
    }

    // 4. DELETE Student Functionality
    public void deleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        System.out.print("Enter ID of student to delete: ");
        String id = scanner.nextLine().trim();

        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Error: Student with ID " + id + " not found.");
            return;
        }

        students.remove(student);
        System.out.println("Successfully deleted student: " + student.getName() + " (ID: " + id + ")");
    }

    // Main menu loop
    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n==================================");
            System.out.println("  STUDENT MANAGEMENT SYSTEM (OOP) ");
            System.out.println("==================================");
            System.out.println("1. Add Student Record");
            System.out.println("2. View All Records");
            System.out.println("3. Edit Student Record");
            System.out.println("4. Delete Student Record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        editStudent();
                        break;
                    case 4:
                        deleteStudent();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }
}
