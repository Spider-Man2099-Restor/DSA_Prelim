import java.util.Scanner;

public class Main {

    // Array-based List ADT
    private static final int MAX_PATIENTS = 100;
    private static Patient[] patients = new Patient[MAX_PATIENTS];
    private static int patientCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    registerNewPatient(sc);
                    break;
                case 2:
                    displayAllPatients();
                    break;
                case 3:
                    searchPatient(sc);
                    break;
                case 4:
                    updatePatient(sc);
                    break;
                case 5:
                    removePatient(sc);
                    break;
                case 6:
                    displayTotalPatients();
                    break;
                case 7:
                    System.out.println("\nThank you for using Clinic Patient Management System. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please select a number between 1 and 7.\n");
            }
        } while (choice != 7);

        sc.close();
    }

    // ========== MENU ==========
    private static void displayMenu() {
        System.out.println("\n====================================");
        System.out.println("     CLINIC PATIENT MANAGEMENT");
        System.out.println("====================================");
        System.out.println("[1] Register New Patient");
        System.out.println("[2] Display All Patients");
        System.out.println("[3] Search Patient");
        System.out.println("[4] Update Patient Record");
        System.out.println("[5] Remove Patient Record");
        System.out.println("[6] Display Total Patients");
        System.out.println("[7] Exit");
        System.out.println("====================================");
    }

    // ========== 1. REGISTER NEW PATIENT ==========
    private static void registerNewPatient(Scanner sc) {
        if (patientCount >= MAX_PATIENTS) {
            System.out.println("\nError: Patient list is full. Cannot register more patients.\n");
            return;
        }

        System.out.print("\nEnter Patient ID: ");
        String id = sc.nextLine().trim();

        if (findPatientIndex(id) != -1) {
            System.out.println("Error: Patient ID already exists! Registration cancelled.\n");
            return;
        }

        System.out.print("Enter Full Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Age: ");
        int age;
        try {
            age = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Age must be a valid number. Registration cancelled.\n");
            return;
        }

        if (age <= 0) {
            System.out.println("Error: Age must be a positive number. Registration cancelled.\n");
            return;
        }

        patients[patientCount] = new Patient(id, name, age);
        patientCount++;

        System.out.println("\nPatient registered successfully!\n");
    }

    // ========== 2. DISPLAY ALL PATIENTS ==========
    private static void displayAllPatients() {
        System.out.println("\n========== LIST OF ALL PATIENTS ==========");

        if (patientCount == 0) {
            System.out.println("No registered patients found.");
        } else {
            for (int i = 0; i < patientCount; i++) {
                System.out.println((i + 1) + ". " + patients[i]);
            }
        }
        System.out.println("==========================================\n");
    }

    // ========== 3. SEARCH PATIENT ==========
    private static void searchPatient(Scanner sc) {
        System.out.print("\nEnter Patient ID to search: ");
        String id = sc.nextLine().trim();

        int index = findPatientIndex(id);

        if (index == -1) {
            System.out.println("Patient with ID \"" + id + "\" not found.\n");
        } else {
            System.out.println("\nPatient Found:");
            System.out.println(patients[index]);
            System.out.println();
        }
    }

    // ========== 4. UPDATE PATIENT RECORD (NEW) ==========
    private static void updatePatient(Scanner sc) {
        System.out.print("\nEnter Patient ID to update: ");
        String id = sc.nextLine().trim();

        int index = findPatientIndex(id);

        if (index == -1) {
            System.out.println("Patient with ID \"" + id + "\" not found. Update failed.\n");
            return;
        }

        System.out.println("\nCurrent Patient Information:");
        System.out.println(patients[index]);

        System.out.println("\nWhat do you want to update?");
        System.out.println("[1] Full Name only");
        System.out.println("[2] Age only");
        System.out.println("[3] Both Name and Age");
        System.out.print("Enter your choice: ");

        int updateChoice;
        try {
            updateChoice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Update cancelled.\n");
            return;
        }

        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Full Name: ");
                String newName = sc.nextLine().trim();
                if (newName.isEmpty()) {
                    System.out.println("Name cannot be empty. Update cancelled.\n");
                    return;
                }
                patients[index].setFullName(newName);
                System.out.println("\nPatient name updated successfully!\n");
                break;

            case 2:
                System.out.print("Enter new Age: ");
                int newAge;
                try {
                    newAge = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Age must be a valid number. Update cancelled.\n");
                    return;
                }
                if (newAge <= 0) {
                    System.out.println("Error: Age must be a positive number. Update cancelled.\n");
                    return;
                }
                patients[index].setAge(newAge);
                System.out.println("\nPatient age updated successfully!\n");
                break;

            case 3:
                System.out.print("Enter new Full Name: ");
                String nameBoth = sc.nextLine().trim();
                if (nameBoth.isEmpty()) {
                    System.out.println("Name cannot be empty. Update cancelled.\n");
                    return;
                }
                
                System.out.print("Enter new Age: ");
                int ageBoth;
                try {
                    ageBoth = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Age must be a valid number. Update cancelled.\n");
                    return;
                }
                if (ageBoth <= 0) {
                    System.out.println("Error: Age must be a positive number. Update cancelled.\n");
                    return;
                }

                patients[index].setFullName(nameBoth);
                patients[index].setAge(ageBoth);
                System.out.println("\nPatient name and age updated successfully!\n");
                break;

            default:
                System.out.println("Invalid choice. Update cancelled.\n");
        }
    }

    // ========== 5. REMOVE PATIENT RECORD ==========
    private static void removePatient(Scanner sc) {
        System.out.print("\nEnter Patient ID to remove: ");
        String id = sc.nextLine().trim();

        int index = findPatientIndex(id);

        if (index == -1) {
            System.out.println("Patient with ID \"" + id + "\" not found. Removal failed.\n");
            return;
        }

        // Shift elements to the left
        for (int i = index; i < patientCount - 1; i++) {
            patients[i] = patients[i + 1];
        }
        patients[patientCount - 1] = null;
        patientCount--;

        System.out.println("Patient record with ID \"" + id + "\" has been successfully removed.\n");
    }

    // ========== 6. DISPLAY TOTAL PATIENTS ==========
    private static void displayTotalPatients() {
        System.out.println("\nTotal number of registered patients: " + patientCount + "\n");
    }

    // ========== HELPER: FIND PATIENT INDEX ==========
    private static int findPatientIndex(String id) {
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }
}