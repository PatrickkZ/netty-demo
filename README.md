# netty-demo

# usage

1. cd transport-file

2. execute
```
mvn package assembly:single
```
(generate a transport-file-1.0-SNAPSHOT-jar-with-dependencies.jar)

3. execute

```
java -jar transport-file-1.0-SNAPSHOT-jar-with-dependencies.jar /file/path/filename
```

4.if correct, you should see a filename_copy in the current dir
