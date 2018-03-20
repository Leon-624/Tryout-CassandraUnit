package org.liyanxu.tryout.cassandraunit.dao;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import lombok.NonNull;
import org.liyanxu.tryout.cassandraunit.api.model.User;
import org.liyanxu.tryout.cassandraunit.api.model.UserIdentifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CassandraUserDaoImpl implements UserDao {

    private final Session session;

    private final PreparedStatement st_getUser;
    private final PreparedStatement st_getUsers;
    private final PreparedStatement st_updateUser;

    public CassandraUserDaoImpl(@NonNull Session session) {
        this.session = session;

        st_getUser = session.prepare("SELECT city, name, favoritefood, favoritenumber " +
                "FROM tryout_cassandraunit.user WHERE city = ? AND name = ?;"
        );

        st_getUsers = session.prepare("SELECT city, name, favoritefood, favoritenumber " +
                "FROM tryout_cassandraunit.user WHERE city = ?;"
        );

        st_updateUser = session.prepare("UPDATE tryout_cassandraunit.user " +
                "SET favoritefood = ?, favoritenumber = ? WHERE city = ? AND name = ?;"
        );
    }

    @Override
    public Optional<User> getUser(@NonNull UserIdentifier identifier) {
        ResultSet rs = session.execute(st_getUser.bind(
                identifier.getCity(),
                identifier.getName()
        ));

        return Optional.ofNullable(mapToUser(rs.one()));
    }

    @Override
    public List<User> getUsers(@NonNull String city) {
        ResultSet rs = session.execute(st_getUsers.bind(city));

        return rs.all().stream().map(r -> mapToUser(r)).collect(Collectors.toList());
    }

    @Override
    public void updateUser(@NonNull User user) {
        session.execute(st_updateUser.bind(
                user.getFavoriteFood(),
                user.getFavoriteNumber(),
                user.getIdentifier().getCity(),
                user.getIdentifier().getName()
        ));
    }

    private User mapToUser(Row r) {
        if(r == null) return null;

        return User.builder()
                .identifier(UserIdentifier.builder()
                        .city(r.getString("city"))
                        .name(r.getString("name"))
                        .build())
                .favoriteFood(r.getString("favoritefood"))
                .favoriteNumber(r.getInt("favoritenumber"))
                .build();
    }
}
