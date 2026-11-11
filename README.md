# Security Log Analyzer
Java program that takes simple, SSV logs and provides tools to analyze

## Run it
Needs JDK 17+ and Maven
Open terminal in folder with pom.xml and sample.log
paste code:
   mvn compile
   java -cp target/classes com.tylercys.Main
Program reads sample.log, prints the following: total failed logins, failed logins of user admin, total login attempts from an ip, and total login attempts from an account

## Note
In early development, future versions will include expanded suite of tools such as detection of many successive fails, fails within time frame, etc
