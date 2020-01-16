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

## JavaScript日志

沙箱默认给`Bindings`提供了`console`对象，因此可以JavaScript脚本中使用如下api：

```javascript
console.debug('Hello')
// log同info
console.log('Hello')
console.info('Hello')
console.error('Hello')
```

`console`打印的日志使用Java的`slf4j-api`实现，所以用户需要制定自己的日志框架实现。see [SLF4J bindings](http://www.slf4j.org/manual.html#swapping)