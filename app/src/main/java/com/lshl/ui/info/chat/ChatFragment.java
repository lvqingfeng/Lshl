package com.lshl.ui.info.chat;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.PathUtil;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.bean.MemberBasicBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.bean.RobotUser;
import com.lshl.db.bean.HxUserBean;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.info.group.GroupDetailsActivity;
import com.lshl.utils.EmojiconExampleGroupData;
import com.lshl.widget.ChatRowVoiceCall;
import com.yunzhanghu.redpacketsdk.constant.RPConstant;
import com.yunzhanghu.redpacketui.RedPacketConstant;
import com.yunzhanghu.redpacketui.utils.RedPacketUtil;
import com.yunzhanghu.redpacketui.widget.ChatRowRedPacket;
import com.yunzhanghu.redpacketui.widget.ChatRowRedPacketAck;
import com.yunzhanghu.redpacketui.widget.ChatRowTransfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

import static android.app.Activity.RESULT_OK;


/**
 * 聊天的Fragment界面
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {
    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;//发送视频
    private static final int ITEM_FILE = 12;//发送文件
    private static final int ITEM_VOICE_CALL = 13;//语音聊天
    private static final int ITEM_VIDEO_CALL = 14;//视频聊天

    private static final int REQUEST_CODE_SEND_TRANSFER_PACKET = 17;//转账的回调
    private static final int MESSAGE_TYPE_RECV_TRANSFER_PACKET = 9;
    private static final int MESSAGE_TYPE_SEND_TRANSFER_PACKET = 10;
    private static final int TRSNSFER = 17;//转账

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;

    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 16;
    private static final int ITEM_RED_PACKET = 16;
    //end of red packet code

    /**
     * if it is chatBot
     */
    private boolean isRobot;
    private ArrayList<EaseUser> userList;
    private EaseUser user;
    private String userJsonString;


    /**
     * 当获取到标题的时候进行的回调
     */
    public interface TitleNameCallback {

        void onCallback(String titleName);

    }

    private TitleNameCallback mTitleNameCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ChatActivity activity = (ChatActivity) getActivity();
        activity.mRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatType == EaseConstant.CHATTYPE_GROUP)
                    GroupDetailsActivity.actionStart(getActivity(), toChatUsername, ChatActivity.REQUEST_CODE_GROUP_DETAIL);
                else if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//                    ChatSettingActivity.actionStart(getActivity(), toChatUsername, ChatActivity.REQUEST_CODE_CONTACT_SETTING);
                    String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
                    new EaseAlertDialog(getActivity(), null, msg, null, new EaseAlertDialog.AlertDialogUser() {

                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {
                            if (confirmed) {
                                EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
//                                getActivity().setResult(RESULT_OK, new Intent().putExtra("result", ChatSettingActivity.ResultType.DELETE_HISTORY));
                                getChatMessageList().refresh();
                            }
                        }
                    }, true).show();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTitleNameCallback = (TitleNameCallback) context;
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            Map<String, RobotUser> robotMap = new HashMap<>();
            //robotMap = DemoHelper.getsInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
        }
        super.setUpView();
        String titleName = titleBar.getTitleName();
        if (titleName != null && mTitleNameCallback != null) {
            if (!toChatUsername.equals(titleName)) {
                mTitleNameCallback.onCallback(titleName);
            } else {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                        , RetrofitManager.RetrofitType.Object)
                        .updatePersonBaseData(LoginHelper.getInstance().getUserToken()
                                , "", titleName), new ProgressSubscriber<>(getActivity()
                        , new SubscriberOnNextListener<PersonBasedataBean>() {
                    @Override
                    public void onNext(PersonBasedataBean result) {
                        PersonBasedataBean.InfoBean infoBean = result.getInfo();
                        if (infoBean != null) {
                            if (!TextUtils.isEmpty(infoBean.getRealname())) {
                                mTitleNameCallback.onCallback(infoBean.getRealname());
                            }
                        }
                    }
                }));
            }
        }

        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
        //inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
        //inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            inputMenu.registerExtendMenuItem("转账", R.drawable.ease_chat_transfer, TRSNSFER, extendMenuItemClickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.ease_chat_voice_call, ITEM_VOICE_CALL, extendMenuItemClickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.ease_chat_video_call, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        }
        //聊天室暂时不支持红包功能
        //red packet code : 注册红包菜单选项
        if (chatType != Constant.CHATTYPE_CHATROOM) {
            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.ease_chat_red_packet, ITEM_RED_PACKET, extendMenuItemClickListener);
        }
        //end of red packet code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                    startActivity(intent);

                    break;

                default:
                    break;
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //选择发送视频的回调
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //选择发送文件的回调
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER://选择@某人的回调
                    if (data != null) {
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
                //red packet code : 发送红包消息到聊天界面
                case REQUEST_CODE_SEND_RED_PACKET://选择发送红包的回调
                    if (data != null) {
                        sendMessage(RedPacketUtil.createRPMessage(getActivity(), data, toChatUsername));
                    }
                    break;

                case REQUEST_CODE_SEND_TRANSFER_PACKET:
                    //从data中取出转账消息需要的属性
                    if (data != null) {
                        sendMessage(RedPacketUtil.createTRMessage(getContext(), data, toChatUsername));
                        //此处为伪代码，由开发者根据自己使用的IM来实现
                    }
                    break;
                //end of red packet code
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            /*startActivityForResult(
                    new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)
                    , REQUEST_CODE_GROUP_DETAIL);*/
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
          /*  startActivityForResult(
                    new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername)
                    , REQUEST_CODE_GROUP_DETAIL);*/
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //点击个人头像
        HxMemberDetailsActivity.actionStart(getActivity(), username, "", false);
    }

    @Override
    public void onAvatarLongClick(String username) {
//        inputAtUsername(username);
        if (EMClient.getInstance().getCurrentUser().equals(username) ||
                chatType != EaseConstant.CHATTYPE_GROUP) {
            return;
        }
        EaseAtMessageHelper.get().addAtUser(username);
        EaseUser user = EaseUserUtils.getUserInfo(username);
        if (user != null) {
            if (!username.equals(user.getNick())) {
                username = user.getNick();
                inputMenu.insertText("@" + username + " ");
            } else {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.String
                        ).getMemberRealname(LoginHelper.getInstance().getUserToken(), username)
                        , new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String resultStr = responseBody.string();
                                    JSONObject object = JSON.parseObject(resultStr);
                                    String status = object.getString("status");
                                    MemberBasicBean memBerBasicBean = null;
                                    if (status.equals(ApiService.STATUS_SUC)) {
                                        memBerBasicBean = JSON.parseObject(object.toJSONString(), MemberBasicBean.class);
                                        if (memBerBasicBean != null) {
                                            //虽然返回的是一个列表 因为我们查询的至是一个账号的id,所以直接取第0个数据就可以
                                            HxUserBean infoBean = memBerBasicBean.getInfo().get(0);
                                            if (infoBean != null) {
                                                inputMenu.insertText("@" + infoBean.getRealname() + " ");
                                            }
                                        }
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
        }
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面
        if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
            RedPacketUtil.openRedPacket(getActivity(), chatType, message, toChatUsername, messageList);
            return true;
        } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE, false)) {
            RedPacketUtil.openTransferPacket(getActivity(), message);
            return true;
        }
        //end of red packet code
        return false;
    }


    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
            if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                RedPacketUtil.receiveRedPacketAckMessage(message);
                messageList.refresh();
            }
        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message)
                        .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL://音频通话
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL://视频通话
                startVideoCall();
                break;
            //red packet code : 进入发红包页面
            case ITEM_RED_PACKET:
                RedPacketUtil.startRedPacketActivityForResult(this, chatType, toChatUsername, REQUEST_CODE_SEND_RED_PACKET);
                break;
            //end of red packet code
            case TRSNSFER://转账
                RedPacketUtil.startTransferActivityForResult(this, toChatUsername, REQUEST_CODE_SEND_TRANSFER_PACKET);
                break;
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * 开启语音聊天
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            MediaCallActivity.actionStart(getContext(), Constant.CallType.VOICE, false, null, toChatUsername, true);
           /* startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));*/
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * 开启视频聊天
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            MediaCallActivity.actionStart(getContext(), Constant.CallType.VIDEO, false, null, toChatUsername, true);
          /*  startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);*/
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 10;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //red packet code : 红包消息和红包回执消息的chat row type
                else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
                    //发送红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET : MESSAGE_TYPE_SEND_RED_PACKET;
                } else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
                    //领取红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET_ACK : MESSAGE_TYPE_SEND_RED_PACKET_ACK;
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE, false)) {
                    //转账消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TRANSFER_PACKET : MESSAGE_TYPE_SEND_TRANSFER_PACKET;
                }
                //end of red packet code
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
                //red packet code : 红包消息和红包回执消息的chat row
                else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {//发送红包消息
                    return new ChatRowRedPacket(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {//open redpacket message
                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE, false)) {//转账消息
                    return new ChatRowTransfer(getActivity(), message, position, adapter);
                }
                //end of red packet code
            }
            return null;
        }

    }
}
