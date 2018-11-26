#!/usr/bin/env python2
# --coding:utf-8--

import os
import time
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from os import path
import urlparse

curdir = path.dirname(path.realpath(__file__))
sep = '/'

# MIME-TYPE
mimedic = [
    ('.html', 'text/html'),
    ('.htm', 'text/html'),
    ('.js', 'application/javascript'),
    ('.css', 'text/css'),
    ('.json', 'application/json'),
    ('.png', 'image/png'),
    ('.jpg', 'image/jpeg'),
    ('.gif', 'image/gif'),
    ('.txt', 'text/plain'),
    ('.avi', 'video/x-msvideo'),
]

class SimpleHttpServerHandler(BaseHTTPRequestHandler):

    def log(self, msg):
        timstr = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
        print("[%s] %s" % (timstr, msg))

    # GET
    def do_GET(self):

        querypath = urlparse.urlparse(self.path)
        filepath, query = querypath.path, querypath.query

        filename, fileext = path.splitext(filepath)

        self.log(filename + " --- " + fileext)

        # 支持命令集合
        urlSet = set(("/start", "/stop", "/reload", "/republish"))
        sendReply = filename in urlSet

        if sendReply == True:
            try:

                param = filename[1:]

                self.log("/app/doe/deploy.sh " + param)
		os.system("/app/doe/deploy.sh " + param)

                content = ("{'success': true, 'msg': 'success.'}").encode("utf-8")
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.end_headers()
                self.wfile.write(content)
            except IOError:
                self.log('File Not Found: %s' % self.path)
                self.send_error(404, 'File Not Found: %s' % self.path)
        else:

            try:
                content = ("{'success': false, 'msg': 'no match url.'}").encode("utf-8")
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.end_headers()
                self.wfile.write(content)
            except IOError:
                self.log('File Not Found: %s' % self.path)
                self.send_error(404, 'File Not Found: %s' % self.path)


def run():
    port = 8000
    print('starting server, port', port)

    # Server settings
    server_address = ('', port)
    httpd = HTTPServer(server_address, SimpleHttpServerHandler)
    print('running server...')
    httpd.serve_forever()


if __name__ == '__main__':
    run()

