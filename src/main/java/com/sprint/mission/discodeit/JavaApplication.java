package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class JavaApplication {
    static JCFUserService userService = new JCFUserService();
    static JCFChannelService channelService = new JCFChannelService();
    static JCFMessageService messageService = new JCFMessageService();

    public static void main(String[] args) {
       userTest();
       channelTest();
       messageTest();
    }

    public static void userTest() {
        System.out.println("========== 유저 테스트 ==========");
        userService.createUser("test0");
        System.out.println("유저 전체 조회 : " + userService.getUsers().toString());
        System.out.println("유저 단건 조회(test0) : " + userService.getUserByNickname("test0"));
        boolean updateUser = userService.updateUser("test0", "testUpdate");
        System.out.println(updateUser ? "업데이트 된 유저 : " + userService.getUserByNickname("testUpdate").toString() : "유저 업데이트 실패");
        boolean deleteUser = userService.deleteUser(userService.getUserByNickname("testUpdate"));
        System.out.println(deleteUser ? "유저 삭제 후 전체 조회 : " + userService.getUsers().toString() : "유저 삭제 실패");
    }

    public static void channelTest() {
        System.out.println("========== 채널 테스트 ==========");
        channelService.createChannel("테스트채널0", "test0");
        System.out.println("채널 전체 조회 : " + channelService.getChannel().toString());
        System.out.println("채널 단건 조회(테스트채널0) : " + channelService.getChannel("테스트채널0"));
        boolean updateCh = channelService.updateChannel("테스트채널0", "업데이트채널");
        System.out.println(updateCh ? "업데이트 된 채널 : " + channelService.getChannel("업데이트채널").toString() : "채널 업데이트 실패");
        boolean deleteCh = channelService.deleteChannel(channelService.getChannelByName("업데이트채널"));
        System.out.println(deleteCh ? "채널 삭제 후 전체 조회 : " + channelService.getChannel() : "채널 삭제 실패");
    }

    public static void messageTest() {
        System.out.println("========== 메세지 테스트 ==========");
        Message sentMsg = messageService.sendMessage("테스트메세지0", "test0", "테스트채널0");
        System.out.println("메세지 전체 조회 : " + messageService.getMessages().toString());
        System.out.println("메세지 단건 조회(테스트메세지0) : " + messageService.getMessageById(sentMsg.getId()));
        boolean updateMessage = messageService.updateMessage(sentMsg.getId(), "updateMessage");
        System.out.println(updateMessage ? "업데이트된 메세지 : " + messageService.getMessageById(sentMsg.getId()) : "메시지 업데이트 실패");
        boolean deleteMessage = messageService.deleteMessage(sentMsg.getId());
        System.out.println(deleteMessage ? "메시지 삭제 후 전체 조회 : " + messageService.getMessages().toString() : "메시지 삭제 실패");
    }
}
