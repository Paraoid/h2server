package com.feidao.dingzk.h2server;

import com.feidao.dingzk.h2server.util.PortStatusCheck;
import com.feidao.dingzk.h2server.util.PropertyUtil;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class H2Server {
    private static final Logger logger = LoggerFactory.getLogger(H2Server.class);

    public static void main(String[] args) {
        try {
            logger.info("*********************************服务器开始启动**********************************");

            startSysDb();

            logger.info("*********************************服务器启动成功**********************************");
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private static void startSysDb() {
        try {
            PropertyUtil props = new PropertyUtil("h2server.properties");
            String baseDir = props.get("server.root");
            String tcpPort = props.get("sys.db.tcp.port");
            String bindAddress = props.get("sys.db.tcp.ip");

            if ("".equals(baseDir) || null == baseDir) {
                logger.warn("请检查配置文件server.root配置");
                baseDir = System.getProperty("java.io.tmpdir");
                logger.warn("使用临时目录: {}", baseDir);
            }

            if ("".equals(tcpPort) || null == tcpPort) {
                logger.warn("请检查配置文件sys.db.tcp.port配置");
                tcpPort = "5678";
                logger.warn("使用端口: {}", tcpPort);
            }

            if (bindAddress == null || bindAddress.length() <= 0) {
                logger.warn("请检查配置文件sys.db.tcp.ip配置");
                System.setProperty("h2.bindAddress", "127.0.0.1");
                logger.warn("使用IP: 127.0.0.1");
            } else {
                System.setProperty("h2.bindAddress", bindAddress);
            }

            baseDir = baseDir.replaceAll("\\\\", "/");
            baseDir = baseDir.replaceAll("//", "/");

            // 判断端口是否可用
            boolean portstatus = PortStatusCheck.checkPort(tcpPort);
            if (portstatus) {
                String[] args = new String[]{"-tcp", "-tcpAllowOthers", "-baseDir", baseDir,
                        "-tcpPort", tcpPort};
                logger.debug("启动参数: baseDir={}, tcpPort={}, h2.bindAddress={}", baseDir, tcpPort, System.getProperty("h2.bindAddress"));
                logger.debug("启动参数: args={}", Arrays.toString(args));
                Server server = Server.createTcpServer(args);
                server.start();
                logger.info("OK, H2数据库服务启动成功, URL={}", server.getURL());
            } else {
                logger.error("H2数据库服务已经启动或端口{}被占用, 请检查!", tcpPort);
                System.exit(0);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
