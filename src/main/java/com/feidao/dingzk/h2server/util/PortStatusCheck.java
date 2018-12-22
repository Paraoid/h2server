package com.feidao.dingzk.h2server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.UnknownHostException;


public abstract class PortStatusCheck {
    private static final Logger logger = LoggerFactory.getLogger(PortStatusCheck.class);

    /**
     * 通过尝试创建socket连接，判断端口是否已经打开。<br />
     * 如果socket连接成功，说明端口已经被占用。
     *
     * @param port port
     * @return is the port available
     */
    public static boolean checkPort(String port) {
        boolean portstatus = true;

//        try {
//            SocketAddress address = new InetSocketAddress(Integer.parseInt(port));
//            ServerSocket socket = new ServerSocket();
//            socket.bind(address);
//            logger.debug("端口未占用", port);
//            socket.close();
//        } catch (UnknownHostException | BindException e) {
//            logger.error("本机已经使用了端口: " + port + ", 不能重复使用!");
//            portstatus = false;
//        } catch (IOException e) {
//            logger.error(e.toString());
//        }
        try {
            logger.debug("尝试bind端口: {}", port);
            ServerSocket socket = new ServerSocket(Integer.parseInt(port));
            logger.debug("端口未占用", port);
            socket.close();
        } catch (UnknownHostException | BindException e) {
            logger.error("本机已经使用了端口: " + port + ", 不能重复使用!");
            portstatus = false;
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return portstatus;
    }
}
