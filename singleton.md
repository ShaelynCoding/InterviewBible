### 饱汉模式:
1. 非线程安全
优点：懒加载启动快，资源占用小，使用时才实例化
缺点：无锁，线程不安全
```java
public class Singleton {
    private static Singleton singleton = null;
    private Singleton() {
    }
    public static Singleton getInstance()
    {
        if(singleton == null)
        {
            singleton = new Singleton();
        }
        return singleton;
    }
}
```
2. 线程安全
优点：在非线程安全版本上加锁线程安全
缺点：排他锁，并发性能差，即使创建成功获取实例仍串行
```java
public class Singleton {
    private static Singleton singleton = null;
    private Singleton() {      
    }
    public static synchronized Singleton getInstance()
    {
        if(singleton == null)
        {
            singleton = new Singleton();
        }
        return singleton;
    }
}
```
3. Double check lock
优点：懒加载，线程安全
缺点：实例必须有volatile修饰以保证初始化完全（？）
```java
public class Singleton {
    private volatile static Singleton singleton = null;
    private Singleton() {      
    }
    public static Singleton getInstance()
    {
        if(singleton == null)
        {
            synchronized(Singleton.class)
            {
                if(singleton == null) {
                    singleton = new Singleton();
                }
            }
           
        }
        return singleton;
    }
}
```
### 饿汉模式
优点：线程安全
缺点：启动即创建，启动慢，有可能造成资源浪费
```java
public class Singleton() {
    private static Singleton singleton = new Singleton();
    private Singleton() {
    }
    public static Singleton getInstance()
    {
        return singleton;
    } 
}
```
### 静态内部类模式（Holder）
优点：懒汉+线程安全，无锁
```java
public class Singleton() {
    private static class SingletonHolder {
        private static final Singleton instance = new Singleton();
    }
    private Singleton() {
    }
    public static Singleton getInstance()
    {
        return SingletonHolder.instance;
    } 
}
```
### 枚举
```java
public enum Singleton {  
    INSTANCE;  
    public void singletonOperation() {  
    }  
}
```
### 场景选择
- 单例对象占用资源少，不需要lazy loading， 枚举优于饿汉
- 单例对象占用资源多，需要lazy loading， Holder优于懒汉






