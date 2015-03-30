package com.handsmap.util.team;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;

import com.handsmap.nsstour.ITeamService;
import com.handsmap.nsstour.manager.message.teammessage.TeamManager;
import com.handsmap.nsstour.model.team.TeamUser;

/**
 * Created by DaHui on 2015/3/9.
 * <p/>
 * 团队消息服务
 */
public class TeamService extends Service
{
    //团队控制，所有的消息处理都在里面执行
    private TeamControl teamControl;
    private TeamManager.TeamMessageReceiver messageReceiver;

    @Override
    public IBinder onBind(Intent intent)
    {
        return new ServiceStub();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        teamControl = new TeamControl(getApplicationContext());
        TeamManager manager = TeamManager.getInstance(getApplicationContext());
        messageReceiver = manager.new TeamMessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TeamDef.ACTION_TEAM_MESSAGE);
        filter.addAction(TeamDef.ACTION_TEAM_EXCEPTION);
        registerReceiver(messageReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //注销广播，记得stopservice
        unregisterReceiver(messageReceiver);
    }

    private class ServiceStub extends ITeamService.Stub
    {

        @Override
        public void connectServer() throws RemoteException
        {
            // 链接服务器
            teamControl.connectServer();
        }

        @Override
        public void logon() throws RemoteException
        {
            // 登陆
        }

        @Override
        public void logout() throws RemoteException
        {
            // 注销
            teamControl.logout();
        }

        @Override
        public void sendHelpMsg(double lat, double lng, int helpType) throws RemoteException
        {
            // 发送救援信息
            teamControl.sendHelpMsg(lat, lng, helpType);
        }

        @Override
        public void createTeam(String teamName, String pass) throws RemoteException
        {
            // 创建团队
            teamControl.createTeam(teamName, pass);
        }

        @Override
        public void exitTeam() throws RemoteException
        {
            // 退出团队
            teamControl.exitTeam();
        }

        @Override
        public void modifySelfInfo(String alis, int sex, String whereCome) throws RemoteException
        {
            // 修改个人信息
            teamControl.modifySelfInfo(alis, sex, whereCome);
        }

        @Override
        public void sendSingleMsg(TeamUser user, String msg, int msgId) throws RemoteException
        {
            // 给单个队友发送消息
            teamControl.sendSingleMsg(user, msg, msgId);
        }

        @Override
        public void sendTeamMsg(String msg, int msgId) throws RemoteException
        {
            // 发送团队消息
            teamControl.sendTeamMsg(msg, msgId);
        }

        @Override
        public void addTeam(String teamName, String pass) throws RemoteException
        {
            // 加入团队
            teamControl.addTeam(teamName, pass);
        }
    }

}
