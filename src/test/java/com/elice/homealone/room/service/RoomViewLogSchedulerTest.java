//package com.elice.homealone.room.service;
//
//import com.elice.homealone.HomealoneApplication;
//import com.elice.homealone.member.entity.Member;
//import com.elice.homealone.member.entity.Role;
//import com.elice.homealone.member.repository.MemberRepository;
//import com.elice.homealone.room.entity.Room;
//import com.elice.homealone.room.entity.RoomViewLog;
//import com.elice.homealone.room.repository.RoomRepository;
//import com.elice.homealone.room.repository.RoomViewLogRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//@SpringBootTest(classes = HomealoneApplication.class)
//class RoomViewLogSchedulerTest {
//
//    @Autowired
//    private RoomViewLogRepository roomViewLogRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @Autowired
//    private RoomViewLogScheduler scheduler;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Transactional
//    @Rollback(false) // 테스트 후 데이터를 롤백하지 않도록 설정 (테스트 데이터 확인을 위해)
//    public void insertTestData(){
//        Member member = Member.builder()
//                .name("nana00")
//                .birth(LocalDate.of(1945, 4, 28)) // 회원 생년월일
//                .email("nana00@homealone.co.kr")
//                .firstAddress("Deageon")
//                .secondAddress("유성구")
//                .password(passwordEncoder.encode("1234")) // 회원 비밀번호
//                .role(Role.ROLE_USER)
//                .build();
//        memberRepository.save(member);
//        Room room = new Room(member,"title","content","thumbnailUrl");
//        roomRepository.save(room);
//
//        // 현재 시간의 로그
//        RoomViewLog currentLog = new RoomViewLog(null, room, LocalDateTime.now());
//        roomViewLogRepository.save(currentLog);
//
//        // 1주일 전의 로그
//        RoomViewLog oldLog = new RoomViewLog(null, room, LocalDateTime.now().minusWeeks(1).minusDays(1));
//        roomViewLogRepository.save(oldLog);
//    }
//
//    @Test
//    @Transactional
//    public void testRoomViewLogCleanup() {
//        // Given: 현재 시간 기준으로 1주일 전의 로그를 추가
//        insertTestData();
//
//        // When: 스케줄러를 수동으로 호출
//        scheduler.cleanUpOldLogs();
//
//        // Then: 1주일 전의 로그는 삭제되어야 함
//        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
//        List<RoomViewLog> logs = roomViewLogRepository.findAll();
//        logs.forEach(log -> {
//            assertTrue(log.getTimeStamp().isAfter(oneWeekAgo));
//        });
//    }
//}
