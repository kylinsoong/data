## Issue 

* After installing it via [quickstart 1.2.2](http://hbase.apache.org/book/quickstart.html), sometimes scann mytable in HBase shell throws the following error:

~~~
1.8.7-p357 :005 > scan 'Customer'
ROW                                                          COLUMN+CELL                                                                                                                                                                     
2014-12-01 15:51:25,158 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 0 time(s).
2014-12-01 15:51:25,259 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 1 time(s).
2014-12-01 15:51:25,361 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 2 time(s).
2014-12-01 15:51:27,922 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 0 time(s).
2014-12-01 15:51:28,023 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 1 time(s).
2014-12-01 15:51:28,125 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 2 time(s).
2014-12-01 15:51:30,249 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 0 time(s).
2014-12-01 15:51:30,350 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 1 time(s).
2014-12-01 15:51:30,452 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 2 time(s).
2014-12-01 15:51:33,202 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 0 time(s).
2014-12-01 15:51:33,303 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 1 time(s).
2014-12-01 15:51:33,404 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 2 time(s).
2014-12-01 15:51:35,521 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 0 time(s).
2014-12-01 15:51:35,623 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 1 time(s).
2014-12-01 15:51:35,724 INFO  [main] ipc.RpcClient: Retrying connect to server: localhost/127.0.0.1:40228 after sleeping 100ms. Already tried 2 time(s).
~~~

* Java Code as below invoke HBase client API throw the exception

~~~
Configuration conf = HBaseConfiguration.create();
HTable table = new HTable(conf, "Customer");
Get get = new Get("101".getBytes());
get.addFamily("customer".getBytes());
Result result = table.get(get);
~~~

[Error Log DEBUG](error.log.debug)

[Error Log INFO](error.log.info)


## Diagnostic Steps


## Resolution
