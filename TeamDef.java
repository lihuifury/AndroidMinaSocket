package com.handsmap.util.team;

/**
 * Created by yandaoqiu on 15-1-29,modified by DaHui on 15-03-30
 */
public class TeamDef
{
    /*消息常量*/
    //团队服务
    public static final String ACTION_TEAM_SERVICE = "com.handsmap.service.TeamService";
    //团队消息
    public static final String ACTION_TEAM_MESSAGE = "com.handsmap.action.team.msg";
    //连接团队服务器异常
    public static final String ACTION_TEAM_EXCEPTION = "com.handsmap.action.team.exception";
    //消息分隔符
    public static final String MSG_SPLIT = "%%";
    //消息头
    public static final String MSG_HEADER = "0x5E";
    //消息尾
    public static final String MSG_TAIL = "%%%%\n";
    //服务器登录密码
    public static final String MSG_SERVER_PASS = "123456";
    //团队处理超时s
    public static final int TIME_OUT_S = 20;

    //用户登录
    public static final String MSG_USER_LOGIN = "801";
    //退出登录
    public static final String MSG_USER_LOGOUT = "011";
    //位置上传
    public static final String MSG_LOCATION_UPLOAD = "001";
    //设备上线通知
    public static final String MSG_NOTIFY_ONLINE = "002";
    //游客报警求助
    public static final String MSG_ASK_HELP = "003";
    //新建团队
    public static final String MSG_CREATE_TEAM = "015";
    //加入团队
    public static final String MSG_JOIN_TEAM = "016";
    //修改设备注册信息
    public static final String MSG_MODIFY_INFO = "017";
    //发送消息给单个队友
    public static final String MSG_P_TO_P = "018";
    //退团
    public static final String MSG_EXIT_TEAM = "019";
    //发送消息给所有人
    public static final String MSG_P_TO_TEAM = "022";
    //查找团队内在线队友的实时位置
    public static final String MSG_REQUEST_LOCATION_SINGLE = "023";
    //查找团队内在线队友的属性信息
    public static final String MSG_REQUEST_INFO_SINGLE = "024";
    //获取所有团队
    public static final String MSG_REQUEST_TEAM_INFO = "025";
    //模糊搜索团队
    public static final String MSG_SEARCH_TEAM = "027";

    /**
     * 被动接收接口
     */
    public static final String TYPE = "type";
    public static final String MESSAGE = "msg";
    //收到单条消息
    public static final int CC_RECEIVED_MESSAGE_SINGLE = 0x0200;
    //收到团队消息
    public static final int CC_RECEIVED_MESSAGE_TEAM = 0x0201;

    /**
     * 消息状态
     * TeamMessageState
     */
    //发送成功
    public static final int TEAM_MESSAGE_STATE_SUCCESS = 0x0101;
    //消息正在发送
    public static final int TEAM_MESSAGE_STATE_SENDING = 0x0102;
    //消息发送失败
    public static final int TEAM_MESSAGE_STATE_FAILD = 0x0103;
    //后期扩展
    //正在输入
    public static final int TEAM_MESSAGE_STATE_EDITING = 0x1001;

    //正在下载
    public static final int TEAM_MESSAGE_STATE_DOWNLOADING = 0x1002;
    //下载失败
    public static final int TEAM_MESSAGE_STATE_DOWNLOAD_FAILD = 0x1003;
}
