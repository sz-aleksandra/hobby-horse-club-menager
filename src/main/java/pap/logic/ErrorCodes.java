package pap.logic;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodes {
    private static final Map<Integer, String> errorDescriptions = new HashMap<>();

    static {
        errorDescriptions.put(1, "Database error");

        // User errors
        errorDescriptions.put(101, "Username is too short");
        errorDescriptions.put(102, "Username is too long");
        errorDescriptions.put(103, "Username contains illegal characters");
        errorDescriptions.put(104, "Username contains illegal keywords");
        errorDescriptions.put(105, "Username is not unique");
        errorDescriptions.put(106, "Password is too short");
        errorDescriptions.put(107, "Password is too long");
        errorDescriptions.put(108, "Password contains username");
        errorDescriptions.put(109, "Password doesn't contain capital letter");
        errorDescriptions.put(110, "Password doesn't contain lowercase letter");
        errorDescriptions.put(111, "Password doesn't contain number");
        errorDescriptions.put(112, "Password doesn't contain special character");
        errorDescriptions.put(113, "Email is too short");
        errorDescriptions.put(114, "Email is too long");
        errorDescriptions.put(115, "Email format is incorrect");
        errorDescriptions.put(116, "Phone number is too short");
        errorDescriptions.put(117, "Phone number is too long");
        errorDescriptions.put(118, "Phone number doesn't contain only numbers");

        // Client errors
        errorDescriptions.put(201, "Client name is too short");
        errorDescriptions.put(202, "Client name is too long");
        errorDescriptions.put(203, "Client name doesn't contain only letters");
        errorDescriptions.put(204, "Client surname is too short");
        errorDescriptions.put(205, "Client surname is too long");
        errorDescriptions.put(206, "Client surname doesn't contain only letters");
        errorDescriptions.put(207, "Birthdate is invalid");
        errorDescriptions.put(208, "Nationality is too short");
        errorDescriptions.put(209, "Nationality is too long");
        errorDescriptions.put(210, "Nationality doesn't contain only letters");
        errorDescriptions.put(211, "Gender is invalid");

        // Owner errors
        errorDescriptions.put(301, "Owner company name is too short");
        errorDescriptions.put(302, "Owner company name is too long");
        errorDescriptions.put(303, "Owner NIP is wrong length");
        errorDescriptions.put(304, "Owner NIP contains not only digits");

        // Login errors
        errorDescriptions.put(401, "No such user in the database (not a successful login attempt)");
        errorDescriptions.put(402, "Wrong password (not a successful login attempt)");
        errorDescriptions.put(403, "User is not active (not a successful login attempt)");
        errorDescriptions.put(404, "No such owner in the database (not a successful login attempt)");
        errorDescriptions.put(405, "Wrong password for owner (not a successful login attempt)");
        errorDescriptions.put(406, "Owner is not active (not a successful login attempt)");
        errorDescriptions.put(407, "No such admin in the database (not a successful login attempt)");
        errorDescriptions.put(408, "Wrong password for admin (not a successful login attempt)");
        errorDescriptions.put(409, "Admin is not active (not a successful login attempt)");


        // Addresses
        errorDescriptions.put(501, "Country is too short");
        errorDescriptions.put(502, "Country is too long");
        errorDescriptions.put(503, "City is too short");
        errorDescriptions.put(504, "City is too long");
        errorDescriptions.put(505, "Street is too short");
        errorDescriptions.put(506, "Street is too long");
        errorDescriptions.put(507, "Postal code is too short");
        errorDescriptions.put(508, "Postal code is too long");
        errorDescriptions.put(509, "Street number is too short");
        errorDescriptions.put(510, "Street number is too long");
        errorDescriptions.put(511, "Wrong street number format");

        // Offer errors
        errorDescriptions.put(601, "Room type is too short");
        errorDescriptions.put(602, "Room type is too long");
        errorDescriptions.put(603, "Name is too short");
        errorDescriptions.put(604, "Name is too long");
        errorDescriptions.put(605, "Add date is illegal");
        errorDescriptions.put(606, "Description is too short");
        errorDescriptions.put(607, "Description is too long");
        errorDescriptions.put(608, "BathroomNo can't be negative");
        errorDescriptions.put(609, "RoomNo can't be negative");
        errorDescriptions.put(610, "BedNo can't be negative");
        errorDescriptions.put(611, "Price can't be negative");

        // Hotel errors
        errorDescriptions.put(701, "Name is too short");
        errorDescriptions.put(702, "Name is too long");
        errorDescriptions.put(703, "Add date is illegal");
        errorDescriptions.put(704, "Description is too short");
        errorDescriptions.put(705, "Description is too long");
        errorDescriptions.put(706, "Email is too short");
        errorDescriptions.put(707, "Email is too long");
        errorDescriptions.put(708, "Email format is incorrect");
        errorDescriptions.put(709, "Website is too short");
        errorDescriptions.put(710, "Website is too long");
        errorDescriptions.put(711, "Website format is incorrect");
        errorDescriptions.put(712, "Phone number is too short");
        errorDescriptions.put(713, "Phone number is too long");
        errorDescriptions.put(714, "Phone number doesn't contain only numbers");
        errorDescriptions.put(715, "Bank account is too short");
        errorDescriptions.put(716, "Bank account is too long");
        errorDescriptions.put(717, "You cannot have two hotels with the same name");

        // Rating errors
        errorDescriptions.put(801, "Rating has to be in range (1, 5)");
        errorDescriptions.put(802, "Comment is too short");
        errorDescriptions.put(803, "Comment is too long");
        errorDescriptions.put(804, "Date is invalid");

        // DiscountCode errors
        errorDescriptions.put(901, "Discount code has wrong length");
        errorDescriptions.put(902, "Discount code can contain only letters or numbers");
        errorDescriptions.put(903, "Discount value type is incorrect (has to be 0 or 1)");
        errorDescriptions.put(904, "Discount type is incorrect (has to be 0 or 1)");
        errorDescriptions.put(905, "Discount description is too short");
        errorDescriptions.put(906, "Discount description is too long");
        errorDescriptions.put(907, "Discount value percentage has to be in <0, 100>");
        errorDescriptions.put(908, "Discount value can't be negative");
        errorDescriptions.put(909, "Discount code is not unique, enter different one");

        // Deactivate Errors
        errorDescriptions.put(1001, "User not found");
        errorDescriptions.put(1002, "User has active reservations (not successful deactivate account)");
        errorDescriptions.put(1003, "Owner not found");
        errorDescriptions.put(1004, "Owner has active hotels (not successful deactivate account)");
    }

    public static String getErrorDescription(int errorCode) {
        return errorDescriptions.getOrDefault(errorCode, "Unknown error");
    }
}