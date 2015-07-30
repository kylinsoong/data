## How to run Sort Example

### 1. Install and Start Hadoop

Refer to [http://ksoong.org/hadoop-intro/](http://ksoong.org/hadoop-intro/) Installation section.

### 2. Create input folder in HDFS

Below is a example to add JBoss log file to input_sort:

~~~
$ ./bin/hadoop fs -mkdir input_sort
$ ./bin/hadoop fs -ls
$ ./bin/hadoop fs -put input/* input_sort
$ ./bin/hadoop fs -cat input_sort/file
~~~

### 3. Build generate jar

~~~
mvn clean install
~~~

Note that, `hadoop-example-sort.jar` will generate while build finished.

### 4. Start Sort

~~~
$ ./bin/hadoop jar target/hadoop-example-sort.jar org.apache.hadoop.examples.Sort input_sort output_sort
~~~

Log output:

~~~
15/07/30 17:10:37 INFO mapred.JobClient:  map 0% reduce 0%
15/07/30 17:10:42 INFO mapred.JobClient:  map 18% reduce 0%
15/07/30 17:10:45 INFO mapred.JobClient:  map 36% reduce 0%
15/07/30 17:10:47 INFO mapred.JobClient:  map 45% reduce 0%
15/07/30 17:10:48 INFO mapred.JobClient:  map 54% reduce 0%
15/07/30 17:10:50 INFO mapred.JobClient:  map 72% reduce 18%
15/07/30 17:10:53 INFO mapred.JobClient:  map 90% reduce 18%
15/07/30 17:10:54 INFO mapred.JobClient:  map 100% reduce 18%
15/07/30 17:11:00 INFO mapred.JobClient:  map 100% reduce 30%
15/07/30 17:11:02 INFO mapred.JobClient:  map 100% reduce 100%
~~~

### 5. Verify the result

~~~
$ ./bin/hadoop fs -ls output_sort
$ ./bin/hadoop fs -cat output_sort/part-r-00000
~~~

