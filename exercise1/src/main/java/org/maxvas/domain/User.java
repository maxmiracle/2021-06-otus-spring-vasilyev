package org.maxvas.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String firstName;
    private String lastName;
}
