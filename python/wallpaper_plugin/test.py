# coding=utf8
# python 2.7
import time, platform
import os
import sys
import re
import fcntl


# import pidfile

def funzioneDemo():
    # 这是具体业务函数示例
    fout = open('/tmp/demone.log', 'w')
    while True:
        fout.write(time.ctime() + '\n')
        fout.flush()
        time.sleep(2)
    fout.close()


def createDaemon():
    # fork进程        
    try:
        if os.fork() > 0: os._exit(0)
    except OSError, error:
        print 'fork #1 failed: %d (%s)' % (error.errno, error.strerror)
        os._exit(1)
    os.chdir('/')
    os.setsid()
    os.umask(0)
    try:
        pid = os.fork()
        if pid > 0:
            print 'Daemon PID %d' % pid
            os._exit(0)
    except OSError, error:
        print 'fork #2 failed: %d (%s)' % (error.errno, error.strerror)
        os._exit(1)
    # 重定向标准IO
    sys.stdout.flush()
    sys.stderr.flush()
    si = file("/dev/null", 'r')
    so = file("/dev/null", 'a+')
    se = file("/dev/null", 'a+', 0)
    os.dup2(si.fileno(), sys.stdin.fileno())
    os.dup2(so.fileno(), sys.stdout.fileno())
    os.dup2(se.fileno(), sys.stderr.fileno())

    # 在子进程中执行代码
    funzioneDemo()  # function demo

    # check function


if __name__ == '__main__':
    if platform.system() == "Linux":
        createDaemon()
    else:
        os._exit(0)
