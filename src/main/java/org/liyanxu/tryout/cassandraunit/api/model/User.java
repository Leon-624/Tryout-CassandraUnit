package org.liyanxu.tryout.cassandraunit.api.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UserIdentifier identifier;

    private String favoriteFood;

    private int favoriteNumber;
}
