## How to run WordCount Example

### 1. Install and Start Hadoop

Refer to [http://ksoong.org/hadoop-intro/](http://ksoong.org/hadoop-intro/) Installation section.

### 2. Create input folder in HDFS

Below is a example to add JBoss log file to input_wordcount:

~~~
$ ./bin/hadoop fs -mkdir input_wordcount
$ ./bin/hadoop fs -ls
$ ./bin/hadoop fs -put ~/server/jboss-eap-6.3/standalone/log/* input_wordcount
$ ./bin/hadoop fs -cat input_wordcount/server.log
~~~

### 3. Build generate jar

~~~
mvn clean install
~~~

Note that, `hadoop-example-wordcount.jar` will generate while build finished.

### 4. Start WordCount

~~~
$ ./bin/hadoop jar target/hadoop-example-wordcount.jar org.apache.hadoop.examples.WordCount input_wordcount output_wordcount
~~~

Note that the following log output hints it ran success:

~~~
15/07/30 15:49:44 INFO mapred.JobClient:  map 0% reduce 0%
15/07/30 15:49:49 INFO mapred.JobClient:  map 25% reduce 0%
15/07/30 15:49:50 INFO mapred.JobClient:  map 50% reduce 0%
15/07/30 15:49:53 INFO mapred.JobClient:  map 100% reduce 0%
15/07/30 15:49:58 INFO mapred.JobClient:  map 100% reduce 33%
15/07/30 15:50:00 INFO mapred.JobClient:  map 100% reduce 100%
~~~

### 5. Verify the result

~~~
$ ./bin/hadoop fs -ls output_wordcount
$ ./bin/hadoop fs -cat output_wordcount/part-r-00000
~~~

