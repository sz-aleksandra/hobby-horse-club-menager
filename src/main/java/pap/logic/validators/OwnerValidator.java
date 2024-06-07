package pap.logic.validators;

import java.util.List;

public class OwnerValidator extends UserValidator {
    private static final int MIN_COMPANY_NAME_LENGTH = 2;
    private static final int MAX_COMPANY_NAME_LENGTH = 64;
    private static final int NIP_LENGTH = 10;
    private final String companyName;
    private final String nip;

    public OwnerValidator(String username, String password, String companyName, String email, String phoneNumber,
                          String country, String city, String street, String postalCode, String streetNo, String nip) {
        super(username, password, email, phoneNumber, country, city, street, postalCode, streetNo);
        this.companyName = companyName;
        this.nip = nip;
    }


    public List <Integer> validateOwnerCredentials() {
        List <Integer> codes = super.validateCredentials();
        validateCompanyName(companyName, codes);
        validateNip(nip, codes);
        return codes;
    }

    public static void validateCompanyName(String companyName, List<Integer> codes) {
        checkTooShortCompanyName(companyName, codes);
        checkTooLongCompanyName(companyName, codes);
    }

    public static void validateNip(String nip, List <Integer> codes) {
        checkNipLength(nip, codes);
        checkNipNumbers(nip, codes);
    }

    private static void checkTooShortCompanyName(String companyName, List <Integer> codes) {
        if (companyName.length() < MIN_COMPANY_NAME_LENGTH) codes.add(301);
    }
    private static void checkTooLongCompanyName(String companyName, List <Integer> codes) {
        if (companyName.length() > MAX_COMPANY_NAME_LENGTH) codes.add(302);
    }

    private static void checkNipLength(String nip, List <Integer> codes) {
        if (nip.length() != NIP_LENGTH) codes.add(303);
    }
    private static void checkNipNumbers(String nip, List <Integer> codes) {
        if (!nip.matches("\\d+")) codes.add(304);
    }
}
