# Nashorn Sandbox

Nashorn运行JavaScript的沙箱。

具有以下特点：

* 可设置最大的CPU运行时间（防止无限循环等）
* 可设置最大的内存使用限制
* 对可使用的Java类进行过滤

## 简单用法

```java
StandardNashornSandbox nashornSandbox = new StandardNashornSandbox();
ExecutorService executorService = Executors.newFixedThreadPool(3);
// seconds
nashornSandbox.setMaxCPUTime(10 * 1000);
// MB
nashornSandbox.setMaxMemory(10 * 1024 * 1024);
nashornSandbox.setExecutor(executorService);
nashornSandbox.eval("while(true) {  }");
```

