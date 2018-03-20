package org.liyanxu.tryout.cassandraunit.dao;

import org.liyanxu.tryout.cassandraunit.api.model.User;
import org.liyanxu.tryout.cassandraunit.api.model.UserIdentifier;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUser(UserIdentifier identifier);

    List<User> getUsers(String city);

    void updateUser(User user);

}
