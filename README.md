# Security Log Analyzer
Java program that takes simple, SSV logs and provides tools to analyze

## Run it
Needs JDK 17+ and Maven  
Open terminal in folder with pom.xml and sample.log  
paste code:
```
mvn compile
java -cp target/classes com.tylercys.Main
```
Program reads sample.log, uses a CLI class to present menu of options. User can choose from menu, which prompts further text input. CLI calls LoginAnalyzer.java to get the requested data, prints it to the command line.



## Note
In early development, future versions will include expanded suite of tools
Support for JSON files coming as well
