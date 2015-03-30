package com.handsmap.nsstour;

import com.handsmap.nsstour.model.team.TeamUser;

interface ITeamService {
    void connectServer();  //链接服务器
    void logon(); //登陆
    void logout(); //注销
    void sendHelpMsg(double lat,double lng,int helpType); //发送救援信息
    void createTeam(String teamName, String pass); //创建团队
    void exitTeam(); //退出团队
    void modifySelfInfo(String alis, int sex, String whereCome); //修改个人信息
    void sendSingleMsg(in TeamUser user, String msg, int msgId); //给单个队友发送消息
    void sendTeamMsg(String msg, int msgId); //给团队群发消息
    void addTeam(String teamName, String pass);
}
