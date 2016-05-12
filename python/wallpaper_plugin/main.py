# coding=utf8
# python 2.7
# 操作网络需要
import urllib
# 正则表达式需要
import re
# 调用系统命令需要
import os
# 时间
import time

# 图片处理
from PIL import Image, ImageDraw, ImageFont

import time, platform
import sys


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
    looper()

    # check function


# 事件字典
eventDic = {}

isRuning = False;
isShowTime=True;


def looper():
    initEventDic()
    delaym = 50
    while True:
        currTime = time.strftime("%H:%M", time.localtime())
        print  currTime

        # if (currTime == "8:00" or currTime == "12:00" or currTime == "18:00") and (isRuning == False):
        #     isRuning=True;
        # setWallpaper()

        #显示时间
        if isShowTime:
            time.sleep(1)
            delaym=delaym - 1
            print delaym
            if delaym<=0:
                setWallpaper()
                delaym=50

        # while delaym<=60 :
        #     time.sleep(1)
        #     delaym=delaym-1


        if eventDic.has_key(currTime):
            looper("excute event")
            print currTime
            eventDic[currTime]()
            # print time.strftime("%H:%M:%S", time.localtime())


def getJson(url):
    return urllib.urlopen(url).read()


def getImgUrl(html):
    reg = re.compile(r'(http://s.cn.bing.net/.*?\.jpg)')  # 正则
    url = reg.findall(html)
    print url[0]
    return url[0]


def downloadImg():
    fpath = os.getcwd() + "/img/"
    fname = fpath + time.strftime("%Y-%m-%d", time.localtime()) + ".jpg"
    mkdir(fpath)
    if os.path.exists(fname):
        print 'wallpaper exists!'
        return fname
        # exit()
    html = getJson('http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1')
    url = getImgUrl(html)

    print fname
    urllib.urlretrieve(url, fname)
    return fname;


def test():
    print "test1"


def test2():
    print "test2"


def initEventDic():
    logger("initEvent")

    eventDic["10:37"] = test;
    eventDic["10:36"] = test2;


def setWallpaperLocal(time):
    fpath = os.getcwd() + "/img/"
    # fname = fpath + "img_" + time.replace(":", "_") + ".jpg";
    fname = compositeImg(downloadImg())
    # 设置壁纸
    # 适用于gnome 3,也许适用于gnome 2
    os.system('gsettings set org.gnome.desktop.background picture-uri "file://' + fname + '"')
    # 设置壁纸样式
    ## Possible values: centered, none, scaled, spanned, stretched, wallpaper, zoom
    os.system('gsettings set org.gnome.desktop.background picture-options zoom')


def setWallpaper():
    fname = compositeImg(downloadImg())
    # 设置壁纸
    # 适用于gnome 3,也许适用于gnome 2
    os.system('gsettings set org.gnome.desktop.background picture-uri "file://' + fname + '"')
    # 设置壁纸样式
    ## Possible values: centered, none, scaled, spanned, stretched, wallpaper, zoom
    os.system('gsettings set org.gnome.desktop.background picture-options zoom')
    isRuning=False;

def compositeImg(fname):
    fpath = os.getcwd() + "/tmp/"
    outPath = fpath + "/tmp.jpg"
    mkdir(fpath)

    im = Image.open(fname).convert('RGBA')
    txt = Image.new('RGBA', im.size, (0, 0, 0, 0))
    fnt = ImageFont.truetype("./font/Night Club Seventy.ttf", 60)
    d = ImageDraw.Draw(txt)
    message = getMessage()
    currTime = time.strftime("%H:%M", time.localtime())
    print str(txt.size[0]) + "==" + str(txt.size[1])
    d.text((txt.size[0] / 3, 130), message, font=fnt, fill=(255, 255, 255, 255))

    if isShowTime :
        fnt2 = ImageFont.truetype("./font/Wattauchimma.ttf", 70)
        d.text((txt.size[0] / 2, 230), currTime, font=fnt2, fill=(255, 255, 255, 255))

    out = Image.alpha_composite(im, txt)
    out.show()
    out.save(outPath)
    return outPath;


def getMessage():
    currTime = time.strftime("%H:%M", time.localtime())
    message = "Good afternoon, fushenghua."
    if "12:00" > currTime >= "8:00":
        message = "Good morning, fushenghua."
    elif "18:00" > currTime >= "12:00":
        message = "Good afternoon, fushenghua."
    elif "24:00" > currTime >= "18:00":
        message = "Good evening, fushenghua."
    print  message
    return message


def mkdir(dpath):
    if os.path.isdir(dpath):
        pass
    else:
        os.mkdir(dpath)


def logger(request):
    fout = open('/tmp/demone.log', 'a')
    fout.write(time.ctime() + ":" + request + "\n")
    fout.flush()
    time.sleep(2)
    fout.close()


if __name__ == '__main__':
    if platform.system() == "Linux":
        logger("start")
        # createDaemon()
        looper()
    else:
        os._exit(0)
