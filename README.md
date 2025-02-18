# cli-args
Annotation based command line argument parser. Requires java 17 or higher version.

# Latest version
Current latest version - `0.2.0`.
<br/>
Maven dependency:
```
<dependency>
    <groupId>com.github.chaosfirebolt.converter</groupId>
    <artifactId>cli-args</artifactId>
    <version>0.2.0</version>
</dependency>
```
[All artefacts in maven central](https://mvnrepository.com/artifact/com.github.chaosfirebolt.converter/cli-args)

[Javadoc](https://javadoc.io/doc/com.github.chaosfirebolt.converter/cli-args/latest/index.html)

# Usage
Class to parse arguments into, configuration on fields:
```java
public class TestArgumentsContainer implements ArgumentsContainer {

  @Argument(name = "--string")
  private String string;
  @Argument(name = "--int", aliases = "-i")
  private int integer;
  @Argument(name = "--realNumber", aliases = {"-rn"})
  private Double realNum;
  @Argument(name = "--bool", aliases = "-b", mandatory = true)
  private Boolean bool;
  @Argument(name = "#cc")
  private CustomClass customClass;
  @Argument(name = "\\sb")
  private byte someByte;
  
  //getters and setters omitted for brevity
}
```
Or class with configuration on setters:
```java
public class SetterArgumentsContainer implements ArgumentsContainer {

  private String string;
  private int integer;
  private CustomClass customClass;

  @Argument(name = "-s")
  public void setString(String string) {
    this.string = string;
  }

  @Argument(name = "-i")
  public void setInteger(int integer) {
    this.integer = integer;
  }

  @Argument(name = "-c")
  public void setCustomClass(CustomClass customClass) {
    this.customClass = customClass;
  }
  
  //getters omitted for brevity
}
```
A custom class:
```java
public record CustomClass(String value) {
}
```
Custom converter:
```java
public class CustomClassConverter extends BaseValueConverter<CustomClass> {

  public CustomClassConverter() {
    super(CustomClass.class);
  }

  @Override
  protected CustomClass doConvert(String value) {
    return new CustomClass(value);
  }
}
```
`BaseValueConverter` is the extension point to create custom converters.

Configure the parser and use it:
```java
public static void main(String[] args) {
  CommandLineArguments cliArgs = CommandLineArguments.builder()
          //if no prefixes are added, defaults to '--' and '-' as prefixes denoting keys
          .addPrefixes("--", "-")
          .addPrefix("#")
          .addPrefixes(List.of("\\"))
          .addConverter(CustomClass.class, new CustomClassConverter())
          //default for cache introspection is false
          .setCacheIntrospection(true)
          .build();
  TestArgumentsContainer container = cliArgs.parse(TestArgumentsContainer.class, args);
}
```
