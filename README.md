Speedment is a Java Stream ORM
==============================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.speedment/runtime/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.speedment/runtime)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.speedment/runtime-deploy/badge.svg)](http://www.javadoc.io/doc/com.speedment/runtime-deploy)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](https://raw.githubusercontent.com/speedment/speedment/master/LICENSE)
[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<img src="https://raw.githubusercontent.com/speedment/speedment-resources/master/src/main/resources/wiki/frontpage/Forest.png" alt="Spire the Hare" title="Spire" align="right" width="480px" />

Speedment accelerates your development speed and makes programming so easy and fun! When you use Speedment for database querying, you do not have to learn a new APIs or use complex ORMs. Everything is **standard Java 8** and works out of the box!

This site covers the **Speedment Open Source** project available under the [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0). If you are interested in the enterprise product with support for commercial databases and in-memory acceleration, check out [www.speedment.com](http://speedment.com/)!

Documentation
-------------
You can read the [the API quick start here](https://github.com/speedment/speedment/wiki/Speedment-API-Quick-Start)!

## Tutorials
* [Tutorial 1 - Set up the IDE](https://github.com/speedment/speedment/wiki/Tutorial:-Set-up-the-IDE)
* [Tutorial 2 - Get started with the UI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-UI)
* [Tutorial 3 - Hello Speedment](https://github.com/speedment/speedment/wiki/Tutorial:-Hello-Speedment)
* [Tutorial 4 - Build a Social Network](https://github.com/speedment/speedment/wiki/Tutorial:-Build-a-Social-Network)
* [Tutorial 5 - Log errors in a database](https://github.com/speedment/speedment/wiki/Tutorial:-Log-errors-in-a-database)
* [Tutorial 6 - Use Speedment with Java EE](https://github.com/speedment/speedment/wiki/Tutorial:-Use-Speedment-with-Java-EE)
* [Tutorial 7 - Writing your own extensions](https://github.com/speedment/speedment/wiki/Tutorial:-Writing-your-own-extensions)
* [Tutorial 8 - Plug-in a Custom TypeMapper](https://github.com/speedment/speedment/wiki/Tutorial:-Plug-in-a-Custom-TypeMapper)
* [Tutorial 9 - Create Event Sourced Systems](https://github.com/speedment/speedment/wiki/Tutorial:-Create-an-Event-Sourced-System)

Quick Start
-----------
Assuming you have Maven installed and a relational database available, you can try out Speedment in a minute by running the following from a command-line.

###### MySQL
```
mvn archetype:generate -DgroupId=com.company -DartifactId=speedment-demo -DarchetypeArtifactId=speedment-archetype-mysql -DarchetypeGroupId=com.speedment.archetypes -DinteractiveMode=false -DarchetypeVersion=3.0.1 && cd speedment-demo && mvn speedment:tool
```

###### PostgreSQL
```
mvn archetype:generate -DgroupId=com.company -DartifactId=speedment-demo -DarchetypeArtifactId=speedment-archetype-postgresql -DarchetypeGroupId=com.speedment.archetypes -DinteractiveMode=false -DarchetypeVersion=3.0.1 && cd speedment-demo && mvn speedment:tool
```

###### MariaDB
```
mvn archetype:generate -DgroupId=com.company -DartifactId=speedment-demo -DarchetypeArtifactId=speedment-archetype-mariadb -DarchetypeGroupId=com.speedment.archetypes -DinteractiveMode=false -DarchetypeVersion=3.0.1 && cd speedment-demo && mvn speedment:tool
```

A graphical dialog will prompt for database connection details.

1. Enter database name and credentials and press **Connect**.
2. Press the **Generate** button and then quit the tool. 

Now you have a demo project set up with generated application code in the directory `speedment-demo`. To learn more about how to leverage the generated Speedment classes and the Speedment runtime in your project, please see the following tutorials and guides.

Examples
--------
Here are a few examples of how you could use Speedment from your code assuming that you have an exemplary MySQL database with the tables "hare", "carrot", "human" and "friends" that looks like this:
```sql
CREATE TABLE `hares`.`hare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `color` varchar(45) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=100;

CREATE TABLE IF NOT EXISTS `hares`.`carrot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `owner` int(11) NOT NULL,
  `rival` int(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=100;

CREATE TABLE IF NOT EXISTS `hares`.`human` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=100;

CREATE TABLE `hares`.`friend` (
  `hare` int(11) NOT NULL,
  `human` int(11) NOT NULL,
  PRIMARY KEY (`hare`, `human`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `hares`.`carrot`
  ADD CONSTRAINT `carrot_owner_to_hare_id` FOREIGN KEY (`owner`) REFERENCES `hare` (`id`);
ALTER TABLE `hares`.`carrot`
  ADD CONSTRAINT `carrot_rival_to_hare_id` FOREIGN KEY (`rival`) REFERENCES `hare` (`id`);

ALTER TABLE `hares`.`friend`
  ADD CONSTRAINT `friend_hare_to_hare_id` FOREIGN KEY (`hare`) REFERENCES `hare` (`id`);
ALTER TABLE `hares`.`friend`
  ADD CONSTRAINT `friend_human_to_human_id` FOREIGN KEY (`human`) REFERENCES `human` (`id`);

```

### Easy initialization
The `HareApplication`, `HareApplicationBuilder` and `HareManager` classes are generated automatically from the database.
```java
final HareApplication app = new HareApplicationBuilder()
    .withPassword("myPwd729")
    .build();
    
final HareManager   hares   = app.getOrThrow(HareManager.class);
final CarrotManager carrots = app.getOrThrow(CarrotManager.class);
final HumanManager  humans  = app.getOrThrow(HumanManager.class);
final FriendManager friends = app.getOrThrow(FriendManager.class);
```


### Query with optimised Stream predicate short-circuit
Search for an old hare (of age greater than 5):
```java
// Searches are optimized in the background!
Optional<Hare> oldHare = hares.stream()
    .filter(Hare.AGE.greaterThan(5))
    .findAny();
``` 

Results in the following SQL query:
```sql
SELECT id, name, color, age FROM hare 
    WHERE (age > 5)
    LIMIT 1;
```

### Easy persistence
Entities can easily be persisted in a database.
```java
Hare newHare = new HareImpl();  // Creates a new empty Hare
newHare.setName("Harry");
newHare.setColor("Gray");
newHare.setAge(3);

// Auto-Increment-fields have been set by the database
Hare persistedHare = hares.persist(newHare); 
```

### Update
```java
hares.stream()
    .filter(Hare.ID.equal(42))  // Filters out all Hares with ID = 42 (just one)
    .map(Hare.AGE.setTo(10))    // Applies a setter that sets the age to 10
    .forEach(hares.updater());  // Applies the updater function
```
or another example
```java
hares.stream()
    .filter(Hare.ID.between(48, 102))   // Filters out all Hares with ID between 48 and 102
    .map(h -> h.setAge(h.getAge() + 1)) // Applies a lambda that increases their age by one
    .forEach(hares.updater());          // Applies the updater function to the selected hares
```

### Remove
```java
hares.stream()
    .filter(Hare.ID.equal(71))  // Filters out all Hares with ID = 71 (just one)
    .forEach(hares.remover());  // Applies the remover function
```

### Join
Construct a Map with all Hares and their corresponding Carrots
```java
Map<Hare, List<Carrot>> join = carrots.stream()
    .collect(
        groupingBy(hares.finderBy(Carrot.OWNER)) // Applies the finderBy(Carrot.OWNER) classifier
    );        
```

### Many-to-many
Construct a Map with all Humans and their corresponding Friend Hares
```java
Map<Human, List<Hare>> humanFriends = friends.stream()
    .collect(
        groupingBy(humans.finderBy(Friend.HUMAN), // Applies the Friend to Human classifier
            mapping(
                hares.finderBy(Friend.HARE),      // Applies the Friend to Hare finder
                toList()                          // Use a List collector for downstream aggregation.
            )
        )
    );        
```

### Entities are linked
No need for complicated joins!
```java
// Find the owner of the orange carrot
Optional<Hare> hare = carrots.stream()
    .filter(Carrot.NAME.equal("Orange"))
    .map(hares.finderBy(Carrot.OWNER))
    .findAny();

// Find one carrot owned by Harry
Optional<Carrot> carrot = hares.stream()
    .filter(Hare.NAME.equal("Harry"))
    .flatMap(carrots.finderBackwardsBy(Carrot.OWNER)) // Carrot is a foreign key table.
    .findAny();
```

### Full Transparency
By appending a logger to the builder, you can follow exactly what happens behind the scenes.
```java
HareApplication app = new HareApplicationBuilder()
    .withPassword("myPwd729")
    .withLogging(ApplicationBuilder.LogType.STREAM)
    .withLogging(ApplicationBuilder.LogType.PERSIST)
    .withLogging(ApplicationBuilder.LogType.UPDATE)
    .withLogging(ApplicationBuilder.LogType.REMOVE)
    .build();
```

### Convert to JSON using a stardard Plugin
Using the JSON Stream Plugin, you can easily convert a stream into JSON:
```java
// List all hares as a complex JSON object where the ID and AGE
// is ommitted and a new field 'carrots' list the id's of all
// carrots associated by a particular hare.
JsonComponent json = app.getOrThrow(JsonComponent.class);
String json = hares.stream()
    .collect(json.toJson(
        json.allOf(hares)
            .remove(Hare.ID)
            .remove(Hare.AGE)
            .putStreamer(
                "carrots",                             // Declare a new attribute
                hares.finderBackwardsBy(Carrot.OWNER), // How it is calculated
                json.noneOf(carrots)                   // How it is formatted
                    .put(Carrot.ID)
            )
    ));
```

Features
--------
Here are some of the many features packed into the Speedment framework!

### Database centric
Speedment is using the database as the source-of-truth, both when it comes to the domain model and the actual data itself. Perfect if you are tired of configuring and debuging complex ORMs. After all, your data is more important than programming tools, is it not?

### Code generation
Speedment inspects your database and can automatically generate code that reflects the latest state of your database. Nice if you have changed the data structure (like columns or tables) in your database. Optionally, you can change the way code is generated [using an intuitive UI](https://github.com/speedment/speedment/wiki/Tutorial:-Get-started-with-the-UI) or programatically using your own code.

### Modular Design
Speedment is built with the ambition to be completely modular! If you don't like the current implementation of a certain function, plug in you own! Do you have a suggestion for an alternative way of solving a complex problem? Share it with the community!

### Type Safety
When the database structure changes during development of a software there is always a risk that bugs sneak into the application. Thats why type-safety is such a big deal! With Speedment, you will notice if something is wrong seconds after you generate your code instead of weeks into the testing phase.

### Null Protection
Ever seen a `NullPointerException` suddenly casted out of nowhere? Null-pointers have been called the billion-dollar-mistake of java, but at the same time they are used in almost every software project out there. To minimize the production risks of using null values, Speedment analyzes if null values are allowed by a column in the database and wraps the values as appropriate in Java 8 Optionals.


Using Maven
-----------
The easiest way to get started with Speedment and Maven is to use one of [the existing archetypes](https://github.com/speedment/speedment-archetypes). An archetype is similar to a template project. When you start a new project, it will add all the dependencies you need to your `pom.xml`-file so that you can begin program immetiatly.

If you do not want to use an archetype, for an example if you already have a project you want to use Speedment with, you can always write your `pom.xml`-file manually. Just add the following lines (between the ... marker lines) to your project's `pom.xml` file, and then assign values to the parameters in the `<properties>`-section of the file.

```xml
<build>
    <plugins>
        
        <plugin>
            <groupId>com.speedment</groupId>
            <artifactId>speedment-maven-plugin</artifactId>
            <version>${speedment.version}</version>
            <dependencies>
                <dependency>
                    <groupId>${db.groupId}</groupId>
                    <artifactId>${db.artifactId}</artifactId>
                    <version>${db.version}</version>
                </dependency>
            </dependencies> 
        </plugin>
        
    </plugins>
</build>
<dependencies>
    
    <dependency>
        <groupId>com.speedment</groupId>
        <artifactId>runtime</artifactId>
        <version>${speedment.version}</version>
        <type>pom</type>
    </dependency>
    <dependency>
        <groupId>${db.groupId}</groupId>
        <artifactId>${db.artifactId}</artifactId>
        <version>${db.version}</version>
    </dependency>
    
</dependencies>
```

To set which database connector you want to use to communicate with your database, please add one of the following to your `<properties>`-section in the `pom.xml`-file:

#### MySQL
```xml
<properties>
    <speedment.version>3.0.1</speedment.version>
    <db.groupId>mysql</db.groupId>
    <db.artifactId>mysql-connector-java</db.artifactId>
    <db.version>5.1.39</db.version>
</properties>
```

#### PostgreSQL
```xml
<properties>
    <speedment.version>3.0.1</speedment.version>
    <db.groupId>org.postgresql</db.groupId>
    <db.artifactId>postgresql</db.artifactId>
    <db.version>9.4-1206-jdbc4</db.version>
</properties>
```

#### MariaDB
```xml
<properties>
    <speedment.version>3.0.1</speedment.version>
    <db.groupId>org.mariadb.jdbc</db.groupId>
    <db.artifactId>mariadb-java-client</db.artifactId>
    <db.version>1.4.0</db.version>
</properties>
```

Make sure that you use the latest `${speedment.version}` available.

### Requirements
Speedment comes with support for the following databases out-of-the-box:
* MySQL
* MariaDB
* PostgreSQL

Support for commercial databases like Oracle DB can be added using enterprise plugins. Visit [www.speedment.com](http://www.speedment.com) for more information on commercial alternatives.  

As of version 2.0, Speedment requires `Java 8` or later. Make sure your IDE configured to use JDK 8 (version 1.8.0_40 or newer).

License
-------

Speedment is available under the [Apache 2 License](http://www.apache.org/licenses/LICENSE-2.0).


#### Copyright

Copyright (c) 2016, Speedment, Inc. All Rights Reserved.
Visit [www.speedment.org](http://www.speedment.org/) for more info.

[![Analytics](https://ga-beacon.appspot.com/UA-64937309-1/speedment/main)](https://github.com/igrigorik/ga-beacon)

[![Beacon](http://stat.speedment.com:8081/Beacon?site=GitHub&path=main)](https://some-site.com)

[Github activity visualized](https://www.youtube.com/watch?v=Rmc_3lLZQpM)

