package com.handsmap.util.team;

import com.handsmap.nsstour.model.team.TeamUser;

/**
 * Created by DaHui on 2015/3/11.
 * <p/>
 * 消息处理接口
 */
public interface TeamMessageListener
{
    /**
     * 连接socket服务器
     *
     * @param result
     * @param msg
     */
    public void connectResult(boolean result, String msg);

    /**
     * 加入团队
     *
     * @param result true登录成功,false登录失败
     * @param msg    详细信息
     */
    public void logonResult(boolean result, String msg);

    /**
     * 收取队友消息
     *
     * @param user 队友对象
     * @param msg  消息
     */
    public void receiveMessage(TeamUser user, String msg);

    /**
     * 收取团队消息
     *
     * @param msg 消息详情
     */
    public void receiveTeamMessage(String msg);

    /**
     * 消息发送成功，已经发送出去，不管接受者有没有接收到
     */
    public void messageSent();
}
