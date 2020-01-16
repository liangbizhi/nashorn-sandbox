# Nashorn Sandbox

Nashorn运行JavaScript的沙箱。

* 可设置最大的CPU运行时间（防止无限循环等）
* 可设置最大的内存使用限制
* 对可使用的Java类进行过滤

## 简单用法

做资源限制，但前提是给沙箱设置`ExecutorService`

```java
StandardNashornSandbox nashornSandbox = new StandardNashornSandbox();
ExecutorService executorService = Executors.newFixedThreadPool(3);
// limit cpu time (10 seconds)
nashornSandbox.setMaxCPUTime(10 * 1000);
// limit memory (10MB)
nashornSandbox.setMaxMemory(10 * 1024 * 1024);
nashornSandbox.setExecutor(executorService);
nashornSandbox.eval("while(true) {  }");
```

### Java

允许脚本中可使用的Java类：

```java
nashornSandbox.allow(Thread.class);
```

### 多线程

沙箱中只有一个`ScriptEngine`实例，多线程下使用`eval`时，应该创建新的`ScriptContext`实例。

下面例子中省略了异常处理，如果`scriptContext`为`null`，程序运行结果则是无法预料的：

```java
for (int i = 0; i < 30; i++) {
    executorService.execute(() -> {
        ScriptContext scriptContext = nashornSandbox.createScriptContext();
        nashornSandbox.eval("var j = 0; for (var i = 0; i < 100; i++) { j++ } console.log('j=' + j)", scriptContext);
    });
}
```

### 编译脚本

```java
CompiledScript compiledScript = nashornSandbox.compile("var j = 0; for (var i = 0; i < 100; i++) { j++ }");
compiledScript.eval();
```

### JavaScript日志

沙箱默认给`Bindings`提供了`console`对象，因此可以JavaScript脚本中使用如下api：

```javascript
console.debug('Hello')
// log同info
console.log('Hello')
console.info('Hello')
console.error('Hello')
```

`console`打印的日志使用Java的`slf4j-api`实现，所以用户需要制定自己的日志框架实现。see [SLF4J bindings](http://www.slf4j.org/manual.html#swapping)

## 脚本编写

由于安全限制，语句体必须包裹在双大括号`{}`中，即使只有一句

```javascript
// not allow
if (a > b)
    return a - b

// good
if (a > b) {
    return a - b
}
```

## download

maven：

```xml
<!-- 即将到来 -->
```