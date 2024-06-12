package com.elice.homealone.module.tag.Service;

import com.elice.homealone.module.tag.Repository.TagRepository;
import com.elice.homealone.module.tag.entity.PostTag;
import com.elice.homealone.module.tag.entity.Tag;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    // 입력된 게시물의 태그와 db의 태그를 비교하여 비슷한 태그가 없다면 태그 엔티티를 생성하고 연결한다.
    @Transactional
    public Tag addTag(PostTag postTag) {
        String tagName = postTag.getName();
        Tag tag = similarTagExists(tagName);
        tag.addTag(postTag);
        return tag;
    }

    // 게시물의 태그와 DB의 태그를 비교하는 함수
    // Tag에서 영어와 한글, 숫자만 남긴 뒤, 영어 소문자는 대문자로 치환해서 비교
    @Transactional
    public Tag similarTagExists(String tagName) {
        StringBuilder sb = new StringBuilder();

        for(char c : tagName.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '가' && c <= '힣') || Character.isDigit(c)) {
                sb.append(Character.toUpperCase(c));
            }
        }
        String newName = sb.toString();
        Optional<Tag> tag = tagRepository.findByTagName(newName);
        if(tag.isPresent()){
            return tag.get();
        }

        Tag newTag = Tag.builder()
            .tagName(newName)
            .build();
        tagRepository.save(newTag);
        return newTag;
    }
}
