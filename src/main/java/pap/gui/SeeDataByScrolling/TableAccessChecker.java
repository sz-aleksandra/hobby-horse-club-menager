package pap.gui.SeeDataByScrolling;

import java.util.HashMap;

public class TableAccessChecker {

    private static HashMap<String, HashMap<String, String>> userTypeAccessTypeToTable;

    static {
        userTypeAccessTypeToTable = new HashMap<>();
        HashMap<String, String> employeesAccess = new HashMap<>();
        employeesAccess.put("Cleaner", "No"); employeesAccess.put("Trainer", "No"); employeesAccess.put("Accountant", "Write"); employeesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Employees",employeesAccess);

        HashMap<String, String> positionsAccess = new HashMap<>();
        positionsAccess.put("Cleaner", "No"); positionsAccess.put("Trainer", "No"); positionsAccess.put("Accountant", "Write"); positionsAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Positions",positionsAccess);

        HashMap<String, String> stablesAccess = new HashMap<>();
        stablesAccess.put("Cleaner", "Read"); stablesAccess.put("Trainer", "Read"); stablesAccess.put("Accountant", "No"); stablesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Stables",stablesAccess);

        HashMap<String, String> horsesAccess = new HashMap<>();
        horsesAccess.put("Cleaner", "Read"); horsesAccess.put("Trainer", "Read"); horsesAccess.put("Accountant", "No"); horsesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Horses",horsesAccess);

        HashMap<String, String> accessoriesAccess = new HashMap<>();
        accessoriesAccess.put("Cleaner", "Read"); accessoriesAccess.put("Trainer", "Read"); accessoriesAccess.put("Accountant", "No"); accessoriesAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Accessories",accessoriesAccess);

        HashMap<String, String> ridersAccess = new HashMap<>();
        ridersAccess.put("Cleaner", "No"); ridersAccess.put("Trainer", "No"); ridersAccess.put("Accountant", "No"); ridersAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Riders",ridersAccess);

        HashMap<String, String> groupsAccess = new HashMap<>();
        groupsAccess.put("Cleaner", "No"); groupsAccess.put("Trainer", "Write"); groupsAccess.put("Accountant", "No"); groupsAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Groups",groupsAccess);

        HashMap<String, String> trainingsAccess = new HashMap<>();
        trainingsAccess.put("Cleaner", "No"); trainingsAccess.put("Trainer", "Write"); trainingsAccess.put("Accountant", "No"); trainingsAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Trainings",trainingsAccess);

        HashMap<String, String> tournamentsAccess = new HashMap<>();
        tournamentsAccess.put("Cleaner", "No"); tournamentsAccess.put("Trainer", "Write"); tournamentsAccess.put("Accountant", "No"); tournamentsAccess.put("Owner", "Write");
        userTypeAccessTypeToTable.put("Tournaments",tournamentsAccess);
    }

    public String getAccessType(String tableName, String employeeType){
        return userTypeAccessTypeToTable.get(tableName).get(employeeType);
    }

}
