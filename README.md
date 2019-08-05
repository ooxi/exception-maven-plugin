# exception-maven-plugin [![Build Status](https://travis-ci.org/ooxi/exception-maven-plugin.svg?branch=master)](https://travis-ci.org/ooxi/exception-maven-plugin)

Declarative generation of Java exceptions.


## Usage

Since `exception-maven-plugin` is published on Maven Central, using this plugin
is as simple as adding the following line to your project's POM file

```xml
<plugin>
    <groupId>com.github.ooxi</groupId>
    <artifactId>exception-maven-plugin</artifactId>
    <version>1.1.1</version>

    <configuration>

        <!--
         | This directory contains XML exception
         | specifications.
         |
         | In this case: src/exception
        -->
        <exceptionDirectory>${basedir}/src/main/exception</exceptionDirectory>

        <!--
         | This directory will be used to emit
         | the generated Java source files.
         |
         | In this case: target/generated-sources/exception
        -->
        <sourceDirectory>${project.build.directory}/generated-sources/exception</sourceDirectory>

        <!--
         | Include current date in generated
         | source file annotations.
        -->
        <outputGeneratedDate>true</outputGeneratedDate>
    </configuration>

    <executions>
        <execution>
            <goals>
                <goal>generate-sources</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```


## Quickstart

```
mc _ mvn --file aggregator clean install
```

