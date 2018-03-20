package org.liyanxu.tryout.cassandraunit.dao;

import com.datastax.driver.core.Session;
import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.*;
import org.liyanxu.tryout.cassandraunit.api.model.User;
import org.liyanxu.tryout.cassandraunit.api.model.UserIdentifier;
import java.util.List;
import java.util.Optional;

public class CassandraUserDaoImplTest {

    public static User user1;
    public static User user2;

    @BeforeClass
    public static void setupUser() {
        user1 = User.builder()
                .identifier(UserIdentifier.builder()
                        .city("nyc")
                        .name("user1")
                        .build())
                .favoriteFood("kfc")
                .favoriteNumber(3)
                .build();

        user2 = User.builder()
                .identifier(UserIdentifier.builder()
                        .city("nyc")
                        .name("user2")
                        .build())
                .favoriteFood("popeyes")
                .favoriteNumber(7)
                .build();
    }

    @Rule
    public CassandraCQLUnit cassandraCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("LoadDataCassandra.cql", false, false));

    /**
     * Clean up keyspace and table after each test, because cql will be loaded before each test.
     */
    @After
    public void cleanUp() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

    @Test
    public void testGetUser() {
        Session session = cassandraCQLUnit.getSession();
        UserDao dao = new CassandraUserDaoImpl(session);


        Optional<User> storedUser1 = dao.getUser(UserIdentifier.builder()
                .city("nyc")
                .name("user1")
                .build());

        Assert.assertTrue(storedUser1.isPresent());
        Assert.assertEquals(user1, storedUser1.get());


        Optional<User> storedUser2 = dao.getUser(UserIdentifier.builder()
                .city("nyc")
                .name("user2")
                .build());

        Assert.assertTrue(storedUser2.isPresent());
        Assert.assertEquals(user2, storedUser2.get());
    }

    @Test
    public void testGetUsers() {
        Session session = cassandraCQLUnit.getSession();
        UserDao dao = new CassandraUserDaoImpl(session);

        List<User> users = dao.getUsers("nyc");

        Assert.assertEquals(2, users.size());
        Assert.assertEquals(user1, users.get(0));
        Assert.assertEquals(user2, users.get(1));
    }

    @Test
    public void testUpdateRoundTrip() {
        Session session = cassandraCQLUnit.getSession();
        UserDao dao = new CassandraUserDaoImpl(session);

        User user3 = User.builder()
                .identifier(UserIdentifier.builder()
                        .city("sfo")
                        .name("user3")
                        .build())
                .favoriteFood("subway")
                .favoriteNumber(12)
                .build();

        dao.updateUser(user3);

        Optional<User> storedUser = dao.getUser(UserIdentifier.builder()
                .city("sfo")
                .name("user3")
                .build()
        );

        Assert.assertTrue(storedUser.isPresent());
        Assert.assertEquals(user3, storedUser.get());
    }

}
