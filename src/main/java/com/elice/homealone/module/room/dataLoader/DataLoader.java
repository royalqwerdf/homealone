package com.elice.homealone.room.dataLoader;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.entity.Role;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.room.dto.RoomRequestDTO;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.repository.RoomRepository;
import com.elice.homealone.tag.Service.PostTagService;
import com.elice.homealone.tag.entity.PostTag;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Configuration
public class DataLoader {

    private final RoomRepository roomRepository;
    private final MemberService memberService;
    private final PostTagService postTagService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner loadData() {
        return args -> loadRoomData();
    }

    @Transactional
    public void loadRoomData() {
        try {
            String email = "nana00@homealone.co.kr";
            if (!memberRepository.existsByEmail(email)) {
                Member member = Member.builder()
                        .name("nana00")
                        .birth(LocalDate.of(1945, 4, 28)) // 회원 생년월일
                        .email(email)
                        .firstAddress("Deageon")
                        .secondAddress("유성구")
                        .password(passwordEncoder.encode("1234")) // 회원 비밀번호
                        .role(Role.ROLE_USER)
                        .build();
                memberRepository.save(member);
            }
            ObjectMapper mapper = new ObjectMapper();
            Member byEmail = memberService.findByEmail(email);
            List<RoomRequestDTO> roomRequestDTOS = mapper.readValue(new File("src/main/resources/room.json"), new com.fasterxml.jackson.core.type.TypeReference<List<RoomRequestDTO>>() {});

            List<Room> roomList = roomRequestDTOS.stream().map(roomRequestDTO -> {
                Room room = new Room(roomRequestDTO, byEmail);
                room.setPlainContent(Jsoup.clean(room.getContent(), Safelist.none()).replace("&nbsp;", " ").replaceAll("\\s", " ").trim());
                Room save = roomRepository.save(room);

                // 태그 생성 및 추가
                List<PostTag> postTags = roomRequestDTO.getTags().stream()
                        .map(tag -> postTagService.createPostTag(tag))
                        .collect(Collectors.toList());
                for (PostTag postTag : postTags) {
                    postTag.setPost(save);
                    save.addTag(postTag);
                }

                return save;
            }).collect(Collectors.toList());
            roomRepository.saveAll(roomList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
