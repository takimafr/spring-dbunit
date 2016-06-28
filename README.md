![https://travis-ci.org/excilys/spring-dbunit](https://travis-ci.org/excilys/spring-dbunit.svg)

## Goal

Spring DBunit provides [DBUnit](http://dbunit.sourceforge.net/) add-ons for Spring Framework.

It allows you to easily insert and cleanup test data into the database of your choice.

Use cases examples :

* "pseudo" unit tests on a DAO layer, with an in-memory database like [HSQLDB](http://hsqldb.org) or [H2](http://www.h2database.com) and a schema automatically created with Hibernate hibernate.hbm2ddl.auto=create-drop.
* GUI integration tests with Selenium, with a persistent database and an existing schema.
* application prototype development, with data reloaded on application startup

## Getting Spring DBUnit

Starting from 1.4.0, releases are in Maven Central, all you need to add to your POM is:

```xml
<dependency>
	<groupId>com.excilys.ebi.spring-dbunit</groupId>
	<!-- or spring-dbunit-servlet -->
	<artifactId>spring-dbunit-test</artifactId>
	<version>1.4.0</version>
</dependency>
```

For older versions of Spring DBUnit, read [the Maven](https://github.com/excilys/spring-dbunit/wiki/maven) Wiki page.

>Note that you need version 1.1.X of DBUnit if you use Spring 3.X

## Documentation

Have a look at these Wiki pages for documentation :

* [Test support](https://github.com/excilys/spring-dbunit/wiki/spring-dbunit-test-module)
* [Servlet support](https://github.com/excilys/spring-dbunit/wiki/spring-dbunit-servlet-module)

## Release Notes
See [Github's Milestones](https://github.com/excilys/spring-dbunit/milestones?state=closed)

## License

Spring DBUnit is released under the Apache Software License v2.0 (See LICENSE file)
