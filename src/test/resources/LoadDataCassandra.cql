CREATE KEYSPACE tryout_cassandraunit with replication = {'class':'SimpleStrategy','replication_factor':1};

CREATE TABLE tryout_cassandraunit.user (
city text,
name text,
favoritefood text,
favoritenumber int,
PRIMARY KEY ((city), name)
);

INSERT INTO tryout_cassandraunit.user (city,name,favoritefood,favoritenumber) VALUES ('nyc','user1','kfc',3);
INSERT INTO tryout_cassandraunit.user (city,name,favoritefood,favoritenumber) VALUES ('nyc','user2','popeyes',7);