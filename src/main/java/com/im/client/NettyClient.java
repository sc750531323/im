package com.im.client;

import com.im.message.*;
import com.im.message.command.ConsoleCommandManager;
import com.im.message.command.LoginConsoleCommand;
import com.im.message.packet.LoginResponsePacket;
import com.im.message.packet.MessageResponsePacket;
import com.im.message.packet.Packet;
import com.im.server.handlers.*;
import com.im.util.LoginUtil;
import com.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 3;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //服务端的引导类是ServerBootstrap
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)//指定线程模型，驱动连接数据的读写
                .channel(NioSocketChannel.class)//设置IO模型
                .handler(new ChannelInitializer<SocketChannel>() {//handler定义连接业务处理逻辑,客户端建立连接之后向服务端
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4)) //处理消息解包
                                .addLast(new PacketDecoder()) //处理消息解码
                                .addLast(new HeartBeatTimeHandler()) //心跳发送器
                                .addLast(new HeartbeatResponseHandler())
                                .addLast(LoginResponseHandler.INSTANCE)  //处理登录返回结果消息
                                .addLast(MessageResponseHandler.INSTANCE) //处理返回聊天消息
                                .addLast(CreateGroupResponseHandler.INSTANCE)  //处理创建群组消息
                                .addLast(JoinGroupResponseHandler.INSTANCE) //加入群组
                                .addLast(QuitGroupResponseHandler.INSTANCE)//退出群聊
                                .addLast(SendToGroupResponseHandler.INSTANCE) //发送群消息
                                .addLast(new PacketEncoder());   //处理消息打包消息
                    }
                });
        reconnect(bootstrap, "localhost", 8080, MAX_RETRY);
    }

    private static void reconnect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true) //心跳机制
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) //超过这个时间表示失败
                .option(ChannelOption.TCP_NODELAY, true); //是否开启Nagle算法
        //connect返回的是一个future，可以给这个future设置监听器
        bootstrap.connect(host, port).addListener(f -> {
            if (f.isSuccess()) {
                Channel channel = ((ChannelFuture) f).channel();
                System.out.println("client success to connect to server");
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.out.println("retry has been exhausted,failed...");
                System.exit(-1);
            } else {
                System.out.println("client failed to connect to server");
                //第几次重试
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.out.println(new Date() + ":连接失败，第 " + order + " 次重连,delay = " + delay + ",retry = " + retry);
                //bootstrap.config()返回的是BootstrapConfig，是对Bootstrap配置参数的抽象，group()方法拿到的就是前面配置的workerGroup
                //调用workerGroup的schedule方法即可实现定时任务逻辑
                bootstrap.config().group().schedule(() -> reconnect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        Scanner sc = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    //没有登录，则执行登录逻辑
                    System.out.println("用户尚未登录，先执行登录逻辑");
                    loginConsoleCommand.exec(sc, channel);
                    waitForRegisterFinish();
                } else {
                    //如果已经登录了
                    consoleCommandManager.exec(sc, channel);
//                    MessageRequestPacket packet = new MessageRequestPacket();
//                    System.out.println("请输入需要接受的用户id：");
//                    String toUserId = sc.nextLine();
//                    packet.setToUserId(toUserId);
//                    System.out.println("请输入想要输入的信息：");
//                    String message = sc.nextLine();
//                    packet.setMessge(message);
//                    channel.writeAndFlush(packet);
                }
            }
        }).start();
    }

    private static void waitForRegisterFinish() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class FirstClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf bb = (ByteBuf) msg;
            Packet packet = PacketCodeC.INSTANCE.decode(bb);

            if (packet instanceof LoginResponsePacket) {
                LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
                if (loginResponsePacket.isSuccess()) {
                    System.out.println(new Date() + ": 登录成功！");
                    LoginUtil.markAsLogin(ctx.channel());
                } else {
                    System.out.println(new Date() + ": 登录失败，原因是:" + loginResponsePacket.getReason());
                }
            } else if (packet instanceof MessageResponsePacket) {
                MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
                System.out.println(new Date() + ":收到服务端的消息：" + messageResponsePacket.getMessage());
            }
        }

        //channelActive方法会在客户端建立连接成功后调用
//        @Override
//        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            System.out.println("发送注册数据到server...");
//            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//            loginRequestPacket.setUserId(UUID.randomUUID().toString());
//            loginRequestPacket.setName("flash");
//            loginRequestPacket.setPassword("pwd");
//            ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
//            ctx.channel().writeAndFlush(buffer);
//        }

        private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
            //获取二进制的bytebuf
            ByteBuf buffer = ctx.alloc().buffer();
            //准备数据，指定字符集为utf-8
            byte[] bytes = "你好，闪电侠！".getBytes(Charset.forName("utf-8"));
            //填充到bytebuf
            buffer.writeBytes(bytes);
            return buffer;
        }
    }
}
