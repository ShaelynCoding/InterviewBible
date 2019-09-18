### 生产者-消费者问题

多线程同步问题
- 保证consumer不在buffer空时消耗，producer不在buffer满时放入数据
- 保证多线程访问一致性

1. wait/notify
-  原理：
    - pool满：producer线程停止执行，放弃锁，处于等待状态/ consumer线程停止执行，放弃锁，处于等待
    - producer向pool放入数据，向等待的线程发出可执行通知，同时放弃锁，处于等待
    - consumer从pool取出数据，向其他等待线程发出可执行通知，同时放弃锁，处于等待
- 缺点：
    - 使用 wait() notify()/notifyAll()的缺点在于在生产者唤醒消费者或者消费者唤醒生产者时，由于生产者和消费者使用同一个锁，所以生产者也会将生产者唤醒，消费者也会将消费者唤醒。
```java
// Pool class： produce/consume
public class Pool {
    private final int MAX_SIZE = 10;                //仓库容量
    private List<Object> list = new LinkedList<>(); //仓库存储载体
    public void produce() {
        synchronized(list) {
            while(list.size() >= MAX_SIZE)
            {
                try
                {
                    list.wait()
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            list.add(new Object());
            list.notifyAll();
        }
    }

    public void consume()
    {
        synchronized(list) {
            while(list.size() == 0) {
                try
                {
                    list.wait();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                list.remove();
                list.notifyAll();
            }
        }
    }
}
//Producer
public class Producer implements Runnable {
    private Pool pool;
    private String name;
    public Producer(String name, Pool pool)
    {
        this.name = name;
        this.pool = pool;
    }
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                pool.produce();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
//Consumer
public class Consumer implements Runnable {
    private Pool pool;
    private String name;
    public Consumer(String name, Pool pool)
    {
        this.name = name;
        this.pool = pool;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(3000);
                pool.consume();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
```
2. 使用Lock和Condition的await() / signal()方法
- 改进：通过在Lock对象上调用newCondition()方法，将条件变量和一个锁对象进行绑定，进而控制并发程序访问竞争资源的安全。
```java
public class Storage {
    // pool最大存储量
    private final int MAX_SIZE = 10;
    // pool存储的载体
    private LinkedList<Object> list = new LinkedList<Object>();
    // 锁
    private final Lock lock = new ReentrantLock();
    // full condition
    private final Condition full = lock.newCondition();
    // empty condition
    private final Condition empty = lock.newCondition();

    public void produce()
    {
        lock.lock();
        while (list.size() >= MAX_SIZE) {
            try {
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(new Object());
        empty.signalAll();
        lock.unlock();
    }

    public void consume()
    {
        lock.lock();
        while (list.size() == 0) {
            try {
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.remove();
        full.signalAll();
        lock.unlock();
    }
}
```
3. BlockingQueue 阻塞队列
- 内部实现：同步队列
- put：容量最大，自动堵塞/take：容量为0，自动堵塞
```java
public class Storage {
    // 仓库存储的载体
    private LinkedBlockingQueue<Object> list = new LinkedBlockingQueue<>(10);

    public void produce() {
        try {
            list.put(new Object());
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void consume() {
        try {
            list.take();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
```
4. 信号量 Semaphore
```java
public class Storage {

    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<Object>();
	// 仓库的最大容量
    final Semaphore notFull = new Semaphore(10);
    // 将线程挂起，等待其他来触发
    final Semaphore notEmpty = new Semaphore(0);
    // 互斥锁
    final Semaphore mutex = new Semaphore(1);

    public void produce()
    {
        try {
            notFull.acquire();
            mutex.acquire();
            list.add(new Object());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            notEmpty.release();
        }
    }

    public void consume()
    {
        try {
            notEmpty.acquire();
            mutex.acquire();
            list.remove();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            notFull.release();
        }
    }
}
```
5. 管道
- 一种特殊的流，用于不同线程间直接传送数据，一个线程发送数据到输出管道，另一个线程从输入管道中读数据。
- inputStream.connect(outputStream)或outputStream.connect(inputStream)作用是使两个Stream之间产生通信链接，这样才可以将数据进行输出与输入。
- 这种方式只适用于两个线程之间通信，不适合多个线程之间通信