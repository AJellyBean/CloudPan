#!/usr/bin/python
#coding:utf8
 
import os
import sys
import time
import fcntl #导入模块
 
class FLOCK(object):
    def __init__(self,name):
        """
        :param name: 文件名
        """
 
        self.fobj = open(name,'w')
        self.fd = self.fobj.fileno()
 
    def lock(self):
        try:
            fcntl.lockf(sefl.fd,fcntl.LOCK_EX|fcntl.LOCK_NB) #给文件加锁，使用了fcntl.LOCK_NB
            print '给文件加锁，稍等 ... ...'
            time.sleep(20)
            return True
        except:
            print '文件加锁，无法执行，请稍后运行。'
            return False
 
    def unlock(self):
        self.fobj.close()
        print '已解锁'
 
if __name__ == "__main__":
    print sys.argv[1]
    locker = FLOCK(sys.argv[1])
    a = locker.lock()
    if a:
        print '文件已加锁'
    else:
        print '无法执行，程序已锁定，请稍等'