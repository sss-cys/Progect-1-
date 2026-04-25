import java.util.ArrayList;
import java.util.Scanner;

abstract class Student {
    private String name;
    private String id;
    protected double marks;

    public Student(String name, String id, double marks) {
        this.name = name;
        this.id = id;
        this.marks = marks;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public abstract String getStatus();
}

class UndergradStudent extends Student {
    public UndergradStudent(String name, String id, double marks) {
        super(name, id, marks);
    }

    @Override
    public String getStatus() {
        return (marks >= 60) ? "Pass" : "Fail";
    }
}

class GradStudent extends Student {
    public GradStudent(String name, String id, double marks) {
        super(name, id, marks);
    }

    @Override
    public String getStatus() {
        return (marks >= 80) ? "Pass" : "Fail";
    }
}

class RegistrationSystem {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void modifyMarks(String id, double newMarks) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                s.setMarks(newMarks);
                System.out.println("Marks updated successfully.");
                return;
            }
        }
        System.out.println("Student ID not found.");
    }

    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student s : students) {
            System.out.println("ID: " + s.getId() + " | Name: " + s.getName() + 
                               " | Marks: " + s.getMarks() + " | Status: " + s.getStatus());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RegistrationSystem system = new RegistrationSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. Modify Marks");
                System.out.println("3. Display All Students");
                System.out.println("4. Exit");
                System.out.print("Select an option: ");
                
                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 4) break;

                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = Double.parseDouble(sc.nextLine());
                        if (marks < 0 || marks > 100) throw new Exception("Marks must be between 0 and 100.");

                        System.out.print("Student Type (1: Undergrad, 2: Grad): ");
                        int type = Integer.parseInt(sc.nextLine());

                        if (type == 1) {
                            system.addStudent(new UndergradStudent(name, id, marks));
                        } else if (type == 2) {
                            system.addStudent(new GradStudent(name, id, marks));
                        } else {
                            System.out.println("Invalid type selected.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Student ID to modify: ");
                        String mid = sc.nextLine();
                        System.out.print("Enter New Marks: ");
                        double nm = Double.parseDouble(sc.nextLine());
                        if (nm < 0 || nm > 100) throw new Exception("Marks must be between 0 and 100.");
                        system.modifyMarks(mid, nm);
                        break;

                    case 3:
                        system.displayAll();
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
