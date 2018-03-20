package org.liyanxu.tryout.cassandraunit.api.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentifier {

    private String city;

    private String name;

}
