package pap.logic.filtration.properties;

import lombok.Getter;

@Getter
public class OwnerProperties extends Properties {
    private final String companyName;

    public OwnerProperties(String companyName) {
        this.companyName = trimIfNotNull(companyName);
    }
}
