package org.maxvas.exercise5.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Genre {
    UUID id;
    String name;
}
