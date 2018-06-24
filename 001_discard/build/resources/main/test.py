#!/usr/bin/env python
# encoding: utf-8

#coding=utf-8
__author__ = '药师Aric'
'''
client端
长连接，短连接，心跳
'''
import socket
import time
host = '127.0.0.1'
port = 8080
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1) #在客户端开启心跳维护
client.connect((host, port))
send_count = 0
try:
    while True:
        timeStr= time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
        client.send((u"你好，这是Python客户端,已经您发送消息 %s" % timeStr).encode())
        send_count = send_count + 1
        print("发送数据完成,已发送次数：%d" % send_count)
        time.sleep(2)
except:
    print("当前系统已经和服务器断开连接....")
