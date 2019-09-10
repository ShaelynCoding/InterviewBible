### TPC三次握手和四次挥手
1. 三次握手：
- client -> server: SYN=1,ACK=0,seq=x;state: SYN_SENT
- server -> client: SYN=1,ACK=1,seq=y,ack=x+1;state:SYN_RECV
- client -> server: SYN=1,ACK=1, seq=x+1, ack=y+1;state:ESTAB_LISHED

- 三次的原因：
    - 当第一次client向server发送建立连接的报文因为网络延迟，server未收到请求未发送确认，client重发连接请求，在server建立连接请求后第一次延迟的报文也到达server并再次建立连接，并一直等待client的数据传输，既浪费资源又有安全隐患。
    - 如果有第三次握手，server发送确认后但收不到client的确认，就不会建立连接。

2. 四次挥手
- client -> server:FIN=1;c_state:fin_wait_1
- server -> client:ACK=1;
s_state:close_wait;c_state:fin_wait_2
- server -> client:FIN=1;
s_state:last_ack
- client -> server:ACK=1;
c_state:time_wait(2MSL后close),s_state:close

- time_wait 2MSL的原因：假设网络是不可靠的，最后一个ACK丢失。TIME_WAIT状态是用来重发可能丢失的ACK报文。

### TPC与UDP区别
|TPC|UDP|
|---|---|
|面向连接|无连接的，即发送数据之前不需要建立连接|
|提供可靠的服务，数据无差错，不丢失，不重复，且按序到达|尽最大努力交付，即不保证可靠交付|
|面向字节流，把数据看成一连串无结构的字节流|面向报文的，没有拥塞控制|
|点到点|支持一对一，一对多，多对一和多对多的交互通信|
|首部开销20字节|首部开销小，只有8个字节|
|全双工的可靠信道|不可靠信道|