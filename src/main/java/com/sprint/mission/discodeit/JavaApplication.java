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
        System.out.println("===== 생성 및 전체 조회 ======");
        userService.createUser("test0");
        channelService.createChannel("테스트채널0", "test0");
        Message sentMsg = messageService.sendMessage("테스트메세지0", "test0", "테스트채널0");
        System.out.println("유저 전체 조회 : " + userService.getUsers().toString());
        System.out.println("채널 전체 조회 : " + channelService.getChannel().toString());
        System.out.println("메세지 전체 조회 : " + messageService.getMessages().toString());
        System.out.println("===== 단건 조회 =====");
        System.out.println("유저 단건 조회(test0) : " + userService.getUserByNickname("test0"));
        System.out.println("채널 단건 조회(테스트채널0) : " + channelService.getChannel("테스트채널0"));
        System.out.println("메세지 단건 조회(테스트메세지0) : " + messageService.getMessageById(sentMsg.getId()));
        System.out.println("===== 수정 =====");
        boolean updateUser = userService.updateUser("test0", "testUpdate");
        System.out.println(updateUser ? "업데이트 된 유저 : " + userService.getUserByNickname("testUpdate").toString() : "유저 업데이트 실패");
        boolean updateCh = channelService.updateChannel("테스트채널0", "업데이트채널");
        System.out.println(updateCh ? "업데이트 된 채널 : " + channelService.getChannel("업데이트채널").toString() : "채널 업데이트 실패");
        boolean updateMessage = messageService.updateMessage(sentMsg.getId(), "updateMessage");
        System.out.println(updateMessage ? "업데이트된 메세지 : " + messageService.getMessageById(sentMsg.getId()) : "메시지 업데이트 실패");
        boolean deleteUser = userService.deleteUser(userService.getUserByNickname("testUpdate"));
        System.out.println("===== 삭제 =====");
        System.out.println(deleteUser ? "유저 삭제 후 전체 조회 : " + userService.getUsers().toString() : "유저 삭제 실패");
        boolean deleteCh = channelService.deleteChannel(channelService.getChannelByName("업데이트채널"));
        System.out.println(deleteCh ? "채널 삭제 후 전체 조회 : " + channelService.getChannel() : "채널 삭제 실패");
        boolean deleteMessage = messageService.deleteMessage(sentMsg.getId());
        System.out.println(deleteMessage ? "메시지 삭제 후 전체 조회 : " + messageService.getMessages().toString() : "메시지 삭제 실패");
    }

//    static Scanner sc = new Scanner(System.in);
//    static JCFUserService userService = new JCFUserService();
//    static JCFChannelService channelService = new JCFChannelService();
//    static JCFMessageService messageService = new JCFMessageService();
//    static User loginUser =  new User();
//    static Channel loginCh = new Channel();
//
//    public static void main(String[] args) {
//        while(true){
//            if(loginCh.getId() != null){
//                System.out.println("현재 채널 : " + loginCh.getChName());
//                chMessages(loginCh.getChName());
//            }
//            System.out.println("===== 메뉴 =====");
//            System.out.println("1. 유저 메뉴");
//            System.out.println("2. 메세지 메뉴");
//            System.out.println("3. 채널 메뉴");
//            System.out.println("9. 종료");
//            System.out.print("입력 : ");
//            int menuNum = Integer.parseInt(sc.nextLine());
//            switch (menuNum){
//                case 1: userMenu(); break;
//                case 2: messageMenu(); break;
//                case 3: channelMenu(); break;
//                case 9: return;
//                default:
//                    System.out.println("잘못된 입력입니다."); break;
//            }
//        }
//    }
//
//    private static void chMessages(String chName) {
//        System.out.println("메세지 목록");
//        Map<UUID, Message> messages;
//        messages = messageService.getMessages(chName);
//        for (Message msg : messages.values()) {
//            System.out.println(msg.getSender() + " : " + msg.getContent());
//        }
//    }
//
//    private static void channelMenu() {
//        System.out.println("1. 채널 등록");
//        System.out.println("2. 채널 검색");
//        System.out.println("3. 채널 목록");
//        System.out.println("4. 채널 변경");
//        System.out.println("5. 채널 삭제");
//        System.out.println("6. 채널 참가");
//        System.out.println("9. 메인으로");
//        System.out.print("입력 : ");
//        int menuNum = Integer.parseInt(sc.nextLine());
//        switch (menuNum){
//            case 1: createChannel(); break;
//            case 2: searchChannel(); break;
//            case 3: chaanelList(); break;
//            case 4: channelUpdate(); break;
//            case 5: channelDelete(); break;
//            case 6: joinChannel(); break;
//            case 9: break;
//        }
//    }
//
//    private static void joinChannel() {
//        if(loginUser.getId()==null){
//            System.out.println("유저 등록이 필요합니다.");
//            return;
//        }
//        System.out.print("참가할 채널 이름 입력 : ");
//        String chName = sc.nextLine();
//        Channel channel = channelService.getChannelByName(chName);
//        if(channel.getId()==null){
//            System.out.println("채널이 존재하지 않습니다.");
//        }
//        else{
//            loginCh = channel;
//            System.out.println("참가 완료.");
//        }
//    }
//
//    private static void channelDelete() {
//        System.out.print("채널 이름 입력 : ");
//        String input = sc.nextLine();
//        Channel findCh = channelService.getChannelByName(input);
//        if(findCh == null) {
//            System.out.println("채널이 존재하지 않습니다.");
//            return;
//        }
//        if(!findCh.getChOwn().equals(loginUser.getNickname())){
//            System.out.println("권한이 없습니다.");
//            return;
//        }
//        if(channelService.deleteChannel(findCh)){
//            System.out.println("삭제 성공.");
//        }
//        else{
//            System.out.println("삭제 실패.");
//        }
//    }
//
//    private static void channelUpdate() {
//        System.out.print("채널 이름 입력 : ");
//        String input = sc.nextLine();
//        Channel findCh = channelService.getChannelByName(input);
//        if(findCh == null) {
//            System.out.println("채널이 존재하지 않습니다.");
//            return;
//        }
//        if(!findCh.getChOwn().equals(loginUser.getNickname())){
//            System.out.println("권한이 없습니다.");
//            return;
//        }
//        System.out.print("변경할 채널명 입력 : ");
//        String newChName = sc.nextLine();
//        findCh.update(newChName);
//        System.out.println("변경 완료.");
//    }
//
//    private static void chaanelList() {
//        Map<UUID, Channel> chs = channelService.getChannel();
//        if(chs.isEmpty()){
//            System.out.println("채널이 존재하지 않습니다.");
//            return;
//        }
//        System.out.println("===== 채널 목록 =====");
//        for(Channel ch : chs.values()){
//            System.out.println("채널명 : " + ch.getChName() + "채널 생성자 : " + ch.getChOwn() + " 생성일 : " + ch.getCreatedAt() + " 수정일 : " + ch.getUpdatedAt());
//        }
//    }
//
//    private static void searchChannel() {
//        System.out.println("검색어 입력 : ");
//        String input = sc.nextLine();
//        Map<UUID, Channel> chs = channelService.getChannel(input);
//        if(chs.isEmpty()){
//            System.out.println("채널이 존재하지 않습니다.");
//        }
//        else{
//            System.out.println("===== 채널 목록 =====");
//            for(Channel ch : chs.values()){
//                System.out.println("채널명 : " + ch.getChName() + "채널 생성자 : " + ch.getChOwn() + " 생성일 : " + ch.getCreatedAt() + " 수정일 : " + ch.getUpdatedAt());
//            }
//        }
//
//    }
//
//    private static void createChannel() {
//        if(loginUser.getId() == null) {
//            System.out.println("유저 등록을 해야합니다.");
//            return;
//        }
//        System.out.print("채널명 입력 : ");
//        String chName = sc.nextLine();
//        if (channelService.createChannel(chName, loginUser.getNickname())) {
//            System.out.println("채널 생성 완료.");
//        } else {
//            System.out.println("채널 생성 실패.");
//        }
//    }
//
//    private static void messageMenu() {
//        System.out.println("1. 메세지 등록");
//        System.out.println("2. 내가 보낸 메세지");
//        System.out.println("9. 메인으로");
//        System.out.print("입력 : ");
//        int menuNum = Integer.parseInt(sc.nextLine());
//        switch (menuNum){
//            case 1: insertMessage(); break;
//            case 2: myMessage(); break;
//            case 9: break;
//        }
//    }
//
//    private static void myMessage() {
//        Map<UUID, Message> message;
//        message = messageService.getMessageByNickname(loginUser.getNickname());
//        System.out.println("===== 내가 보낸 메세지 ======");
//        for(Message m : message.values()){
//            System.out.println("(채널 : " + m.getChName() + ") " + m.getContent());
//        }
//        System.out.println("=========================");
//    }
//
//    private static void insertMessage() {
//        if(loginUser.getId() == null){
//            System.out.println("유저 등록을 해야합니다.");
//            return;
//        }
//        if(loginCh.getId() == null){
//            System.out.println("채널에 참가해야합니다.");
//        }
//        System.out.print("메세지 입력 : ");
//        String input = sc.nextLine();
//        messageService.sendMessage(input, loginUser.getNickname(), loginCh.getChName());
//    }
//
//    private static void userMenu(){
//        System.out.println("1. 유저 등록");
//        System.out.println("2. 유저 검색");
//        System.out.println("3. 유저 목록");
//        System.out.println("4. 닉네임 변경");
//        System.out.println("5. 유저 삭제");
//        System.out.println("9. 메인으로");
//        System.out.print("입력 : ");
//        int menuNum = Integer.parseInt(sc.nextLine());
//        switch (menuNum){
//            case 1: insertUser(); break;
//            case 2: searchUser(); break;
//            case 3: userList(); break;
//            case 4: userUpdate(); break;
//            case 5: userDelete(); break;
//            case 9: break;
//        }
//    }
//
//    private static void userDelete() {
//        System.out.print("닉네임 입력 : ");
//        String inputNick = sc.nextLine();
//        User findUser = userService.getUserByNickname(inputNick);
//        if(userService.deleteUser(findUser)){
//            System.out.println("삭제 완료.");
//        }
//        else{
//            System.out.println("삭제 실패.");
//        }
//    }
//
//    private static void userUpdate() {
//        System.out.print("닉네임 입력 : ");
//        String inputNick = sc.nextLine();
//        User findUser = userService.getUserByNickname(inputNick);
//        if(findUser == null){
//            System.out.println("존재하지 않는 유저");
//        }
//        else {
//            System.out.print("변경할 닉네임 입력 : ");
//            String newNick = sc.nextLine();
//            findUser.setNickname(newNick);
//            userService.updateUser(findUser);
//            loginUser = findUser;
//            System.out.println("변경 완료.");
//        }
//    }
//
//    private static void userList() {
//        Map<UUID, User> users = userService.getUsers();
//        if(users.isEmpty()){
//            System.out.println("유저가 존재하지 않습니다.");
//        }
//        else{
//            System.out.println("===== 유저 목록 =====");
//            for(User user : users.values()){
//                System.out.println("닉네임 : " + user.getNickname() + " 가입일 : " + user.getCreatedAt() + " 수정일 : " + user.getUpdatedAt());
//            }
//        }
//    }
//
//    private static void searchUser() {
//        String inputNickname = sc.nextLine();
//        User findUser = userService.getUserByNickname(inputNickname);
//        if(findUser != null){
//            System.out.println("닉네임 : " + findUser.getNickname() + " 가입일 : " + findUser.getCreatedAt() + " 수정일 : " + findUser.getUpdatedAt());
//        }
//        else{
//            System.out.println("존재하지 않는 닉네임.");
//        }
//    }
//
//    private static void insertUser() {
//        System.out.print("닉네임 입력 : ");
//        String nickName = sc.nextLine();
//        if(userService.createUser(nickName)) {
//            System.out.println("유저 생성 완료.");
//            loginUser = userService.getUserByNickname(nickName);
//        }
//        else {
//            System.out.println("유저 생성 실패.");
//        }
//    }
}
