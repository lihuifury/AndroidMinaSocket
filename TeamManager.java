package com.handsmap.nsstour.manager.message.teammessage;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.handsmap.nsstour.ITeamService;
import com.handsmap.nsstour.manager.BaseManager;
import com.handsmap.nsstour.model.team.TeamUser;
import com.handsmap.util.Logger;
import com.handsmap.util.extend.app.ToastUtil;
import com.handsmap.util.team.TeamDef;
import com.handsmap.util.team.TeamMessageListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yandaoqiu on 15-1-28. modified by DaHui on 15-03-10
 * <p/>
 * 团队管理类
 */
public class TeamManager extends BaseManager
{
    private Context mContext;
    private static TeamManager manager = null;
    //团队处理接口
    private ITeamService mService;
    //服务连接
    private ServiceConnection mConn;
    //消息监听
    private ArrayList<TeamMessageListener> listeners;

    public synchronized static TeamManager getInstance(Context context)
    {
        if (manager == null)
        {
            manager = new TeamManager(context);
        }
        return manager;
    }

    private TeamManager(Context context)
    {
        this.mContext = context;
        initServiceConn();
        listeners = new ArrayList<>();
    }

    /**
     * 初始化连接服务
     */
    private void initServiceConn()
    {
        Intent intent = new Intent(TeamDef.ACTION_TEAM_SERVICE);
        //启动服务，接下来绑定服务拿到服务对象，通过AIDL接口调用Sercice的函数
        mContext.startService(intent);

        mConn = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                //服务连接上
                mService = ITeamService.Stub.asInterface(service);
                Logger.d(this, "AIDL连接服务成功");
                try
                {
                    //登录服务器
                    mService.connectServer();
                } catch (RemoteException e)
                {
                    Logger.e(this, "登录服务器出错了，detail：" + e.getMessage());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name)
            {
                //服务断开
                ToastUtil.showToastShort(mContext, "AIDL团队服务已断开");
            }
        };
        //绑定服务
        mContext.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 去掉绑定服务
     */
    public void disConnectService()
    {
        mContext.unbindService(mConn);
    }

    /**
     * 停止团队服务
     */
    private void stopService()
    {
        mContext.stopService(new Intent(TeamDef.ACTION_TEAM_SERVICE));
    }

    /**
     * 注册观察者
     *
     * @param listener
     */
    public void registerObserver(TeamMessageListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }

    /**
     * 注销观察者
     *
     * @param listener
     */
    public void unRegisterObserver(TeamMessageListener listener)
    {
        if (listeners.contains(listener))
        {
            listeners.remove(listener);
        }
    }

    /**
     * 登录服务器
     */
    public void logon()
    {
        try
        {
            mService.logon();
            Logger.d(this, "登录团队服务器成功");
        } catch (RemoteException e)
        {
            Logger.e(this, "登录Socket出错，detail:" + e.getMessage());
        }
    }

    /**
     * 注销
     */
    public void logout()
    {
        //TODO 注销登录
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
     * @throws android.os.RemoteException
     */
    public void addTeam(String teamName, String pass)
    {
        try
        {
            mService.addTeam(teamName, pass);
            Logger.d(this, "加入团队nice");
        } catch (RemoteException e)
        {
            Logger.e(this, "加入团队失败");
        }
    }

    /**
     * 监听超时
     *
     * @param obj
     */
    private void listenerTimeOut(RetainObj obj)
    {

    }

    /**
     * 超时处理类
     */
    private class RetainObj
    {
        //请求码
        public int cmd;
        //当前超时的倒计时
        public Timer timer;

        public RetainObj(Integer cmd)
        {
            this.cmd = cmd;
            this.timer = new Timer();
            //超时任务
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    listenerTimeOut(RetainObj.this);
                }
            }, TeamDef.TIME_OUT_S * 1000);
        }
    }

    /**
     * Created by DaHui on 2015/3/11.
     * <p/>
     * 消息接收器，接收团队服务发送的广播消息，接收到的消息经过TeamMessageUtils处理通过Listener抛给Activity
     */
    public class TeamMessageReceiver extends BroadcastReceiver
    {

        public TeamMessageReceiver()
        {

        }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            Logger.d(this, "接收到消息:" + action);
        }
    }

    @Override
    public void recycleManager()
    {
        disConnectService();
        manager = null;
        super.recycleManager();
    }
}



