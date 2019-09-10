### ArrayList 
- 底层实现：数组
- 插入 -> 扩容：1.5x, arraycopy 旧数组
- 删除：让下标到数组末尾的元素向前移动一个单位，并把最后一位的值置空，方便GC（缩小容量：trimToSize()）
- 非线程安全： 
    - Fail-Fast机制：面对并发的修改时，迭代器很快就会完全失败，而不是冒着在将来某个不确定时间发生任意不确定行为的风险
### LinkedList
- 底层实现：双向链表
- 插入：改prev/next
- 删除：二分查找，删除对应元素
- 非线程安全

### Vector
- Vector是基于synchronized实现的线程安全的ArrayList。
- 扩容： 2x
单线程应尽量使用ArrayList，Vector因为同步会有性能损耗；即使在多线程环境下，我们可以利用Collections这个类中为我们提供的synchronizedList(List list)方法返回一个线程安全的同步列表对象。

### HashTable
- 底层实现：数组+单链表
- key/value不允许为null
- synchronized  同步：针对整张hash表
- Hashtable在底层将key-value当成一个整体进行处理，这个整体就是一个Entry对象。Hashtable底层采用一个Entry[]数组来保存所有的key-value对，当需要存储一个Entry对象时，会根据key的hash算法来决定其在数组中的存储位置，在根据equals方法决定其在该数组位置上的链表中的存储位置；当需要取出一个Entry时，也会根据key的hash算法找到其在数组中的存储位置，再根据equals方法从该位置上的链表中取出该Entry。

### HashMap
- 底层实现：数组+单链表
- key/value允许为null
- 非同步，线程不安全

### ConcurrentHashMap
- 锁分离：多个锁控制hash表的不同段的修改（每段相当于一个小的hashtable），

#### HashMap vs HashTable vs ConcurrentHashMap
1. 三者在数据存储层面的机制原理基本一致
2. HashMap不是线程安全的，多线程环境下除了不能保证数据一致性之外，还有可能在rehash阶段引发Entry链表成环，导致死循环
3. Hashtable是线程安全的，能保证绝对的数据一致性，但性能是问题，并发线程越多，性能越差
4. ConcurrentHashMap 也是线程安全的，使用分离锁和volatile等方法极大地提升了读写性能，同时也能保证在绝大部分情况下的数据一致性。但其不能保证绝对的数据一致性， 在一个线程向Map中加入Entry的操作没有完全完成之前，其他线程有可能读不到新加入的Entry

### HashSet
- HashSet由哈希表(实际上是一个HashMap实例)支持，不保证set的迭代顺序，并允许使用null元素。
- 基于HashMap实现，API也是对HashMap的行为进行了封装，可参考HashMap

### LinkedHashMap 
- LinkedHashMap继承于HashMap，底层使用哈希表和双向链表来保存所有元素，并且它是非同步，允许使用null值和null键。
- 基本操作与父类HashMap相似，通过重写HashMap相关方法，重新定义了数组中保存的元素Entry，来实现自己的链接列表特性。该Entry除了保存当前对象的引用外，还保存了其上一个元素before和下一个元素after的引用，从而构成了双向链接列表。
### LinkedHashSet 
- 对于LinkedHashSet而言，它继承与HashSet、又基于LinkedHashMap来实现的。LinkedHashSet底层使用LinkedHashMap来保存所有元素，它继承与HashSet，其所有的方法操作上又与HashSet相同。

### TreeMap
- 基于红黑树的Map结构，Entry类拥有左右字节点/父节点的引用，以及自己的颜色/key/value
- entry有序，快速排序/查找

### ConcurrentSkipListMap
- 基于跳表，entry有序
- 线程安全，但不能保证数据的绝对一致性

### vector
- 线程安全，方法基本同步，但性能低
- 可定义数据长度扩容的因子

### CopyOnWriteArrayList
- 线程安全，在write operation时会复制一个副本，在新副本上执行写操作，然后再修改引用，这样CopyOnWriteArrayList可以对读操作不加锁，这样CopyOnWriteArrayList的读效率远高于vector，适合多读少些的多线程场景。但只能保证数据的最终一致性，不能保证实时一致性。
- set的实现基于同类map，CopyOnWriteArraySet内置的是CopyOnWriteArrayList

PS：Fail-Fast:
- 因此如果在使用迭代器的过程中有其他线程修改了map，那么将抛出ConcurrentModificationException
- 实现是通过modCount域，modCount是修改次数，对HashMap内容的修改都将增加这个值，那么在迭代器初始化过程中会将这个值赋给迭代器的expectedModCount
-  在迭代过程中，判断modCount跟expectedModCount是否相等，如果不相等就表示已经有其他线程修改了Map：modCount声明为volatile，保证线程之间修改的可见性。