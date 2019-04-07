# 基于Netty 实现的简单的WebService服务器

## 运行方式

在 /tmp 目录下创建404.html 文件，以及其他文件，启用该某块，尝试访问对应的文件，如在/tmp目录下创建123.html
那么可以尝试访问 `http://localhost:8080/123.html` 当然你可以随意输入一个不存在的文件，比如`http://localhost:8080/xxx.html` 系统会自动转到404.html
