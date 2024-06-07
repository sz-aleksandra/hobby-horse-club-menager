package pap.logic.filtration.properties;

public class Properties {
    public static String trimIfNotNull(String text) {
        if (text != null) {
            return text.trim();
        }
        return null;
    }
}
