package org.maxvas.exercise5.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Genre {
    private UUID id;
    private String name;
}
