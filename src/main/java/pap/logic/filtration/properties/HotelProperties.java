package pap.logic.filtration.properties;

import lombok.Getter;


@Getter
public class HotelProperties extends Properties {
    private final String name;

    public HotelProperties(String name) {
        this.name = trimIfNotNull(name);
    }
}
