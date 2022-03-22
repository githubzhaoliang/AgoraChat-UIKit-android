package io.agora.chat.uikit.thread.presenter;

import android.text.TextUtils;

import io.agora.CallBack;
import io.agora.ValueCallBack;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatThread;
import io.agora.chat.Group;

public class EaseThreadChatPresenterImpl extends EaseThreadChatPresenter {

    @Override
    public void getThreadInfo(String threadId) {
        ChatClient.getInstance().threadManager().getThreadFromServer(threadId, new ValueCallBack<ChatThread>() {
            @Override
            public void onSuccess(ChatThread value) {
                if(isActive()) {
                    mView.onGetThreadInfoSuccess(value);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                if(isActive()) {
                    mView.onGetThreadInfoFail(error, errorMsg);
                }
            }
        });
    }

    @Override
    public void joinThread(String threadId) {
        ChatClient.getInstance().threadManager().joinThread(threadId, new CallBack() {
            @Override
            public void onSuccess() {
                if(isDestroy()) {
                    return;
                }
                mView.OnJoinThreadSuccess();
            }

            @Override
            public void onError(int code, String error) {
                if(isDestroy()) {
                    return;
                }
                mView.OnJoinThreadFail(code, error);
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    @Override
    public void getGroupInfo(String groupId) {
        Group group = ChatClient.getInstance().groupManager().getGroup(groupId);
        if(group != null && !TextUtils.isEmpty(group.getGroupName())) {
            if(isDestroy()) {
                return;
            }
            mView.onGetGroupInfoSuccess(group);
            return;
        }
        ChatClient.getInstance().groupManager().asyncGetGroupFromServer(groupId, new ValueCallBack<Group>() {
            @Override
            public void onSuccess(Group value) {
                if(isDestroy()) {
                    return;
                }
                mView.onGetGroupInfoSuccess(value);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if(isDestroy()) {
                    return;
                }
                mView.onGetGroupInfoFail(error, errorMsg);
            }
        });
    }
}