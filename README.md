# 后端考核2（简单网络key-value数据库数据库）

### 完成功能

​	1.服务端与客户端分离，可自定义服务器端口

​	2.使用多线程，服务器可以同时对多个客户端进行响应

​	3.实现部分数据库实现指令

​	4.简单日志实现，记录服务器操作与异常报告

​	5.部分配置文件读取与保存，hashset数据保存

#### 端口自定义

​	在程序运行目录下Config.properties文件里面，使用port=8080（默认值）键值对保存，运行时自动读取

#### 文件名与作用

​	运行过程会产生4个文件

​	1.Config.properties（配置文件，目前只保存了端口设定值）

​	2.Log.txt（服务端运行Log日志)

​	3.Error.txt（服务端运行错误日志)

​	4.HashMap.properties（字符串类型的hashmap文件数据）

#### 程序启动与调试

​	1.服务器Main程序包com.gduf.Server下UDPMainServerDemo类

​	2.客户端在com.gduf.cline下

​	3.Text包下中的TestErrorLogger类是用于人为制造除于0错误，观察是否能成功记录错误

#### 实现的指令

- 字符串类型（一个key对应一个字符串value）

  - `set [key] [value]` 存储 key-value 类型数据
  - `get [key]`                  获取 key 对应的 value
  - `del [key]`                  删除 key 对应的 value
  - `hdel [key]`                删除key的键值对

- 双向链表类型（一个key对应一个双向链表，即可左右遍历，可以当栈，也可以当队列使用的数据结构）

  - `lpush [key] [value]` 可直接放一个数据在左端
  - `rpush [key] [value]` 可直接放一个数据在右端
  - `range [key] [start] [end]`  将key 对应 start 到 end 位置的数据全部返回
  - `len  [key]`  获取 key 存储数据的个数
  - `lpop [key]`  获取key最左端的数据并删除
  - `rpop [key]`  获取key最右端的数据并删除
  - `ldel [key]`  删除key 所有的数据

- 其它指令

  - `ping`   心跳指令，ping响应pong

  - `help`   获取所有command指令的使用方式

  - `help [command]`  获取单个command指令适用方式

    



