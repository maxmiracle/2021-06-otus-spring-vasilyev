package org.maxvas.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Food {

    private final String name;

    private final Cooker cooker;

}
