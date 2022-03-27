package com.im.server;

import com.im.message.*;
import com.im.server.handlers.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new AuthHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new CreateGroupRequestHandler());
                        nioSocketChannel.pipeline().addLast(new JoinGroupRequestHandler());
                        nioSocketChannel.pipeline().addLast(new QuitGroupRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        bind(serverBootstrap, 8080);
    }

//    static class FirstServerHandler extends ChannelInboundHandlerAdapter {
//        //服务端的channelRead方法在收到客户端信息的时候被回调
//        @Override
//        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//            ByteBuf bb = (ByteBuf) msg;
//            Packet packet = PacketCodeC.INSTANCE.decode(bb);
//
//            if (packet instanceof LoginRequestPacket) {
//                System.out.println("服务端收到注册数据");
//                LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
//                LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
//                loginResponsePacket.setVersion(loginRequestPacket.getVersion());
//                if (valid(loginRequestPacket)) {
//                    loginResponsePacket.setSuccess(true);
//                    //登录成功
//                } else {
//                    loginResponsePacket.setReason("账号密码不正确");
//                    loginResponsePacket.setSuccess(false);
//                }
//                ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
//                ctx.channel().writeAndFlush(byteBuf);
//            } else if (packet instanceof MessageRequestPacket) {
//                MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
//                System.out.println("收到message类型的packet，内容是：" + messageRequestPacket.getMessge());
//
//                MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//                messageResponsePacket.setMessage("服务端回应消息【" + messageRequestPacket.getMessge() + "】");
//                ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
//                ctx.channel().writeAndFlush(buf);
//            }
//            super.channelRead(ctx, msg);
//        }
//
//        private boolean valid(LoginRequestPacket loginRequestPacket) {
//            return true;
//        }
//    }


    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        //childHandler用于指定处理新连接数据的读写处理逻辑，handler用于处理服务端启动时的一些逻辑
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                System.out.println("server startting ...");
            }
        });
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap
                .childOption(ChannelOption.SO_KEEPALIVE, true)//是否开启tcp底层心跳机制
                .childOption(ChannelOption.TCP_NODELAY, true);//是否开启nagle算法，true表示关闭。如果对实时性要求高就设置为关闭
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("bind [" + port + "] success");
                } else {
                    System.out.println("bind [" + port + "] failed");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
