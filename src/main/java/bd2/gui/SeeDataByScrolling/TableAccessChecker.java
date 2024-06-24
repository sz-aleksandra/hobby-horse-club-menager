package bd2.gui.SeeDataByScrolling;

import java.util.HashMap;

public class TableAccessChecker {

    private static HashMap<String, HashMap<String, String>> userTypeAccessTypeToTable;

    static {
        userTypeAccessTypeToTable = new HashMap<>();
        HashMap<String, String> employeesAccess = new HashMap<>();
        employeesAccess.put("Trainer", "No"); employeesAccess.put("Senior Trainer", "No"); employeesAccess.put("Instructor", "No"); employeesAccess.put("Senior Instructor", "No"); employeesAccess.put("Director of Training", "No"); employeesAccess.put("Veterinarian", "No"); employeesAccess.put("Caretaker", "No"); employeesAccess.put("Administrator", "No"); employeesAccess.put("Coordinator", "Read"); employeesAccess.put("Technician", "No"); employeesAccess.put("IT Specialist", "Write"); employeesAccess.put("Marketer", "No"); employeesAccess.put("HR", "Read"); employeesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Employees",employeesAccess);

        HashMap<String, String> positionsAccess = new HashMap<>();
        positionsAccess.put("Trainer", "No"); positionsAccess.put("Senior Trainer", "No"); positionsAccess.put("Instructor", "No"); positionsAccess.put("Senior Instructor", "No"); positionsAccess.put("Director of Training", "No"); positionsAccess.put("Veterinarian", "No"); positionsAccess.put("Caretaker", "No"); positionsAccess.put("Administrator", "No"); positionsAccess.put("Coordinator", "No"); positionsAccess.put("Technician", "No"); positionsAccess.put("IT Specialist", "No"); positionsAccess.put("Marketer", "No"); positionsAccess.put("HR", "Read"); positionsAccess.put("Owner", "Read");
        userTypeAccessTypeToTable.put("Positions",positionsAccess);

        HashMap<String, String> stablesAccess = new HashMap<>();
        stablesAccess.put("Trainer", "Read"); stablesAccess.put("Senior Trainer", "Read"); stablesAccess.put("Instructor", "Read"); stablesAccess.put("Senior Instructor", "Read"); stablesAccess.put("Director of Training", "Read"); stablesAccess.put("Veterinarian", "Read"); stablesAccess.put("Caretaker", "Read"); stablesAccess.put("Administrator", "Read"); stablesAccess.put("Coordinator", "Read"); stablesAccess.put("Technician", "No"); stablesAccess.put("IT Specialist", "Write"); stablesAccess.put("Marketer", "Read"); stablesAccess.put("HR", "No"); stablesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Stables",stablesAccess);

        HashMap<String, String> horsesAccess = new HashMap<>();
        horsesAccess.put("Trainer", "Read"); horsesAccess.put("Senior Trainer", "Read"); horsesAccess.put("Instructor", "Read"); horsesAccess.put("Senior Instructor", "Read"); horsesAccess.put("Director of Training", "Read"); horsesAccess.put("Veterinarian", "Read"); horsesAccess.put("Caretaker", "Read"); horsesAccess.put("Administrator", "Read"); horsesAccess.put("Coordinator", "Read"); horsesAccess.put("Technician", "No"); horsesAccess.put("IT Specialist", "Write"); horsesAccess.put("Marketer", "No"); horsesAccess.put("HR", "No"); horsesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Horses",horsesAccess);

        HashMap<String, String> accessoriesAccess = new HashMap<>();
        accessoriesAccess.put("Trainer", "Read"); accessoriesAccess.put("Senior Trainer", "Read"); accessoriesAccess.put("Instructor", "Read"); accessoriesAccess.put("Senior Instructor", "Read"); accessoriesAccess.put("Director of Training", "No"); accessoriesAccess.put("Veterinarian", "No"); accessoriesAccess.put("Caretaker", "Read"); accessoriesAccess.put("Administrator", "No"); accessoriesAccess.put("Coordinator", "No"); accessoriesAccess.put("Technician", "No"); accessoriesAccess.put("IT Specialist", "Write"); accessoriesAccess.put("Marketer", "No"); accessoriesAccess.put("HR", "No"); accessoriesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Accessories",accessoriesAccess);

        HashMap<String, String> ridersAccess = new HashMap<>();
        ridersAccess.put("Trainer", "Read"); ridersAccess.put("Senior Trainer", "Read"); ridersAccess.put("Instructor", "Read"); ridersAccess.put("Senior Instructor", "Read"); ridersAccess.put("Director of Training", "Read"); ridersAccess.put("Veterinarian", "No"); ridersAccess.put("Caretaker", "No"); ridersAccess.put("Administrator", "No"); ridersAccess.put("Coordinator", "No"); ridersAccess.put("Technician", "No"); ridersAccess.put("IT Specialist", "Write"); ridersAccess.put("Marketer", "No"); ridersAccess.put("HR", "No"); ridersAccess.put("Owner", "Write");
		userTypeAccessTypeToTable.put("Riders",ridersAccess);

        HashMap<String, String> groupsAccess = new HashMap<>();
        groupsAccess.put("Trainer", "Read"); groupsAccess.put("Senior Trainer", "Read"); groupsAccess.put("Instructor", "Read"); groupsAccess.put("Senior Instructor", "Read"); groupsAccess.put("Director of Training", "Read"); groupsAccess.put("Veterinarian", "No"); groupsAccess.put("Caretaker", "No"); groupsAccess.put("Administrator", "No"); groupsAccess.put("Coordinator", "Read"); groupsAccess.put("Technician", "No"); groupsAccess.put("IT Specialist", "Write"); groupsAccess.put("Marketer", "No"); groupsAccess.put("HR", "No"); groupsAccess.put("Owner", "Write");
		userTypeAccessTypeToTable.put("Groups",groupsAccess);

        HashMap<String, String> trainingsAccess = new HashMap<>();
        trainingsAccess.put("Trainer", "Write"); trainingsAccess.put("Senior Trainer", "Write"); trainingsAccess.put("Instructor", "Write"); trainingsAccess.put("Senior Instructor", "Write"); trainingsAccess.put("Director of Training", "Read"); trainingsAccess.put("Veterinarian", "No"); trainingsAccess.put("Caretaker", "Read"); trainingsAccess.put("Administrator", "No"); trainingsAccess.put("Coordinator", "Read"); trainingsAccess.put("Technician", "No"); trainingsAccess.put("IT Specialist", "Write"); trainingsAccess.put("Marketer", "No"); trainingsAccess.put("HR", "No"); trainingsAccess.put("Owner", "Write");
		userTypeAccessTypeToTable.put("Trainings",trainingsAccess);

        HashMap<String, String> tournamentsAccess = new HashMap<>();
        tournamentsAccess.put("Trainer", "Read"); tournamentsAccess.put("Senior Trainer", "Write"); tournamentsAccess.put("Instructor", "Read"); tournamentsAccess.put("Senior Instructor", "Write"); tournamentsAccess.put("Director of Training", "Read"); tournamentsAccess.put("Veterinarian", "No"); tournamentsAccess.put("Caretaker", "Read"); tournamentsAccess.put("Administrator", "No"); tournamentsAccess.put("Coordinator", "Read"); tournamentsAccess.put("Technician", "No"); tournamentsAccess.put("IT Specialist", "Write"); tournamentsAccess.put("Marketer", "Read"); tournamentsAccess.put("HR", "No"); tournamentsAccess.put("Owner", "Write");
		userTypeAccessTypeToTable.put("Tournaments",tournamentsAccess);
    }

    public String getAccessType(String tableName, String employeeType){
        return userTypeAccessTypeToTable.get(tableName).get(employeeType);
    }

}
