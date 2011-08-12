# spring-dbunit 1.0.0

## What for?

spring-dbunit provides [DBUnit](http://www.dbunit.org) add-ons for Spring.

It allows you to easily insert and cleanup test data into the database of your choice.

Use cases examples :

* "pseudo" unit tests on a DAO layer, with an in-memory database like [HSQLDB](http://hsqldb.org) or [H2](http://www.h2database.com) and a schema automatically created with Hibernate hibernate.hbm2ddl.auto=create-drop.
* GUI integration tests with Selenium, with a persistent database and an existing schema.
* application prototype development, with data reloaded at application startup

## Documentation

Have a look at the Wiki for modules documentation :

* [Test support](/excilys/spring-dbunit/wiki/spring-dbunit-test-module)
* [Servlet support](/excilys/spring-dbunit/wiki/spring-dbunit-servlet-module)

## Where are the binaries?

You can find the binaries in our maven repository :
[http://repository.excilys.com/content/repositories/releases](http://repository.excilys.com/content/repositories/releases)

``` xml
	<dependency>
		<groupId>com.excilys.ebi</groupId>
		<artifactId>spring-dbunit-test</artifactId>
		<version>1.0.0</version>
	</dependency>

	<dependency>
		<groupId>com.excilys.ebi</groupId>
		<artifactId>spring-dbunit-servlet</artifactId>
		<version>1.0.0</version>
	</dependency>
```