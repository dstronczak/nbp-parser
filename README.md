# NBP Parser

Parser for currency prices, using www.nbp.pl services.

## Usage
<b>Run a compiled MainClass:</b>
```
java pl.parser.nbp.MainClass <CURRENCY> <START_DATE> <END_DATE>
```

  <br>
  <i>OR</i>
  <br>
  
<b>Build with maven and execute jar:</b>
```
mvn clean package
java -jar currency-parser-1.0.jar <CURRENCY> <START_DATE> <END_DATE>
```

##TODO (possible improvements)
  - make http request in multiple Threads (currently running sequentially)
  - replace number primitive types to Objects (consistency with Collections that them)
  - possibly create some interfaces and decouple the classes
  - general code cleanup and refactoring
  - more unit tests

## License

https://opensource.org/licenses/MIT