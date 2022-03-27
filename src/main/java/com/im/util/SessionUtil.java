package com.im.util;

import com.im.message.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.*;

public class SessionUtil {
    //userid -> channel 映射
    private static final Map<String, String> userIdUsernameMap = new HashMap<>();
    private static final Map<String, Channel> userIdChannelMap = new HashMap<>();
    //group相关
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new HashMap<>();
    private static final Map<String, List<Session>> groupIdSessionMap = new HashMap<>();


    public static void removeSessionByGroupId(String groupId,Session session){
        groupIdSessionMap.get(groupId).remove(session);
    }

    public static List<String> getAllUsernameByGroupId(String groupId){
        List<String> result = new ArrayList<>();
        List<Session> sessionList = groupIdSessionMap.get(groupId);
        for (Session session : sessionList) {
            result.add(session.getUserName());
        }
        return result;
    }

    public static void addSessionToGroup(String groupId,Session session){
        if(groupIdSessionMap.containsKey(groupId)){
            groupIdSessionMap.get(groupId).add(session);
        }else {
            System.out.println("没有这个groupId");
        }
    }

    public static void addSessionToGroup(String groupId,List<Session> sessions){
        groupIdSessionMap.put(groupId,sessions);
    }

    /**
     * 登录的时候调用
     * @param userId
     * @param userName
     */
    public static void addLoginUser(String userId,String userName){
        userIdUsernameMap.put(userId,userName);
    }

    public static String getUsernameByUserId(String userId){
        return userIdUsernameMap.get(userId);
    }

    public static ChannelGroup getChannelGroup(String groupId){
        return groupIdChannelGroupMap.get(groupId);
    }

    public static void createGroup(String groupId,ChannelGroup channelGroup){
        groupIdChannelGroupMap.put(groupId,channelGroup);
    }

    public static void addChannelToChannelGroup(String groupId, Channel channel, ChannelHandlerContext ctx){
        if(groupIdChannelGroupMap.containsKey(groupId)){
            ChannelGroup channelGroup = groupIdChannelGroupMap.get(groupId);
            channelGroup.add(channel);
        }else {
            DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
            channelGroup.add(channel);
            groupIdChannelGroupMap.put(groupId,channelGroup);
        }
    }

    public static void bindSession(Session session,Channel channel){
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel){
        userIdChannelMap.remove(getSession(channel).getUserId());
        channel.attr(Attributes.SESSION).set(null);
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }
}
