package org.maxvas.exercise6.domain;

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
