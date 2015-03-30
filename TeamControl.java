package com.handsmap.util.team;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import com.handsmap.nsstour.config.Appconfig;
import com.handsmap.nsstour.model.team.TeamUser;
import com.handsmap.util.Logger;
import com.handsmap.util.common.StringUtils;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by DaHui on 2015/3/9.
 * <p/>
 * 团队直接控制类，所有的团队操作在此类中进行，Service直接调用，负责收发消息，处理消息
 */
public class TeamControl extends Thread
{
    private IoSession mSession = null;
    private IoConnector mConnector = null;
    //设备ID
    private String deviceId = "";
    private Context mContext;

    public TeamControl(Context context)
    {
        this.mContext = context;
        deviceId = StringUtils.getAndroidId(context);
    }

    @Override
    public synchronized void start()
    {
        super.start();
    }

    @Override
    public void run()
    {
        super.run();
        //初始化socket
        if (mSession != null && mSession.isConnected())
        {
            Logger.d(this, "socket已连接");
            return;
        }
        mConnector = new NioSocketConnector();
        // 设置链接超时时间
        mConnector.setConnectTimeoutMillis(5000);
        // 添加过滤器
        DefaultIoFilterChainBuilder filterChain = mConnector.getFilterChain();
        mConnector.getSessionConfig().setReadBufferSize(2048);
        mConnector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        Charset charset = Charset.forName("UTF-8");
        // 添加编码过滤器 处理乱码、编码问题
        filterChain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(charset)));
        mConnector.setHandler(new ClientAdapter());
        try
        {
            ConnectFuture future = mConnector.connect(new InetSocketAddress(Appconfig.IP_TEAM, Appconfig.PORT_TEAM));
            future.awaitUninterruptibly();// 等待连接创建完成
            mSession = future.getSession();// 获得session
            logon();
        } catch (Exception e)
        {
            //服务器连接异常
            Logger.e(TeamControl.this, "连接Socket服务器异常，" + e.getMessage());
            //当遇到连接异常发送广播通知
            Intent intent = new Intent();
            intent.setAction(TeamDef.ACTION_TEAM_EXCEPTION);
            mContext.sendBroadcast(intent);
        }
    }

    /**
     * 连接服务器
     */
    public void connectServer()
    {
        this.start();
    }

    /**
     * 登陆服务器
     */
    private void logon()
    {
        String msg = TeamDef.MSG_USER_LOGIN +
                TeamDef.MSG_SPLIT +
                deviceId +
                TeamDef.MSG_SPLIT +
                TeamDef.MSG_SERVER_PASS;
        sendMessage(msg);
    }

    /**
     * 注销
     */
    public void logout()
    {
        //TODO 注销登录，同时注销广播
    }

    /**
     * 发送救援位置信息
     *
     * @param lat
     * @param lng
     * @param helpType
     */
    public void sendHelpMsg(double lat, double lng, int helpType)
    {
        //TODO 发送救援信息
    }

    /**
     * 创建团队
     *
     * @param teamName
     * @param pass
     */
    public void createTeam(String teamName, String pass)
    {
        //TODO 创建团队
    }

    /**
     * 退团
     */
    public void exitTeam()
    {
        //TODO 退出团队
    }

    /**
     * 修改个人信息
     *
     * @param alis
     * @param sex
     * @param whereCome
     */
    public void modifySelfInfo(String alis, int sex, String whereCome)
    {
        //TODO 修改个人信息
    }

    /**
     * 给单个队友发送消息
     *
     * @param user
     * @param msg
     * @param msgId
     */
    public void sendSingleMsg(TeamUser user, String msg, int msgId)
    {
        //TODO 给单个队友发送消息
    }

    /**
     * 发送团队消息
     *
     * @param msg
     * @param msgId
     */
    public void sendTeamMsg(String msg, int msgId)
    {
        //TODO 发送团队消息
    }

    /**
     * 加入团队
     *
     * @param teamName
     * @param pass
     * @throws RemoteException
     */
    public void addTeam(String teamName, String pass)
    {
        String msg = TeamDef.MSG_JOIN_TEAM +
                TeamDef.MSG_SPLIT +
                teamName +
                TeamDef.MSG_SPLIT +
                pass;
        sendMessage(msg);
    }

    /**
     * 组装消息，加上头尾和长度
     *
     * @param msg
     * @return
     */
    private String buildMessage(String msg)
    {
        msg = TeamDef.MSG_HEADER + TeamDef.MSG_SPLIT + msg;
        msg = "[length=" + msg.length() + "]" + msg + TeamDef.MSG_TAIL;
        return msg;
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    private void sendMessage(String msg)
    {
        if (mSession != null)
        {
            mSession.write(buildMessage(msg));
        }
    }

    /**
     * 消息逻辑处理,接收服务器发送来的消息，通过广播发送出去
     */
    private class ClientAdapter extends IoHandlerAdapter
    {
        @Override
        public void messageReceived(IoSession session, Object message) throws Exception
        {
            //收到消息
            super.messageReceived(session, message);
            Logger.d(this, "收到消息，" + message.toString());
            Intent intent = new Intent();
            intent.putExtra(TeamDef.MESSAGE, message.toString());
            //表示发送消息动作
            intent.setAction(TeamDef.ACTION_TEAM_MESSAGE);
            mContext.sendBroadcast(intent);
        }

        @Override
        public void sessionClosed(IoSession session) throws Exception
        {
            //会话关闭
            super.sessionClosed(session);
        }

        @Override
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception
        {
            super.exceptionCaught(session, cause);
            Logger.e(this, "异常，消息发送失败");
        }

        @Override
        public void messageSent(IoSession session, Object message) throws Exception
        {
            super.messageSent(session, message);
        }
    }
}
