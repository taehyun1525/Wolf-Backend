package com.kdt.wolf.domain.faq.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kdt.wolf.domain.admin.entity.AdminEntity;
import com.kdt.wolf.domain.admin.repository.AdminRepository;
import com.kdt.wolf.domain.faq.dto.FaqDto.FaqCreateRequest;
import com.kdt.wolf.domain.faq.dto.FaqDto.FaqDetail;
import com.kdt.wolf.domain.faq.dto.FaqDto.FaqUpdateRequest;
import com.kdt.wolf.domain.faq.dto.response.FaqPageResponse;
import com.kdt.wolf.domain.faq.entity.FaqCategory;
import com.kdt.wolf.domain.faq.entity.FaqEntity;
import com.kdt.wolf.domain.faq.repository.FaqRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FaqServiceTest {
    @Autowired
    private FaqService faqService;

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private AdminRepository adminRepository;

    private AdminEntity adminEntity;
    @BeforeEach
    void setUp() {
        adminEntity = adminRepository.save(
                AdminEntity.builder()
                    .email("adminEmail")
                    .password("adminPassword")
                    .name("adminName")
                    .nickname("adminNickname")
                    .build()
        );
        faqRepository.save(
                FaqEntity.builder()
                        .category(FaqCategory.STUDY)
                        .question("question")
                        .answer("answer")
                        .admin(adminEntity)
                        .build()
        );
    }

    @Test
    @DisplayName("카테고리별 FAQ를 조회한다.")
    void getAllFaq() {
        //given
        faqRepository.save(
                FaqEntity.builder()
                        .category(FaqCategory.STUDY)
                        .question("question2")
                        .answer("answer2")
                        .admin(adminEntity)
                        .build()
        );

        faqRepository.save(FaqEntity.builder()
                .category(FaqCategory.STUDY)
                .question("question3")
                .answer("answer3")
                .admin(adminEntity)
                .build());
        //when
        Pageable pageable = Pageable.ofSize(20);
        FaqPageResponse response = faqService.getFaqsByCategory(FaqCategory.STUDY, pageable);
        //then
        assertEquals(3, response.faqItems().size());
    }

    @Test
    @DisplayName("FAQ 상세 조회")
    void getFaq() {
        //given
        Long faqId = faqRepository.findAll().get(0).getId();
        //when
        FaqDetail result = faqService.getFaqDetail(faqId);
        //then
        assertEquals("question", result.question());
        assertEquals("answer", result.answer());
    }

    @Test
    @DisplayName("FAQ 게시글 작성")
    void createFaq() {
        //given
        Long adminId = adminRepository.findAll().get(0).getAdminId();
        FaqCreateRequest request = new FaqCreateRequest(
                "스터디", "question", "answer");
        //when
        Long resultId = faqService.createFaq(adminId, request);
        //then
        assertThat(resultId).isNotNull();
    }

    @Test
    @DisplayName("FAQ 게시글 수정")
    void updateFaq() {
        //given
        Long faqId = faqRepository.findAll().get(0).getId();
        FaqCreateRequest request = new FaqCreateRequest(
                "스터디", "question", "answer");
        Long resultId = faqService.createFaq(adminRepository.findAll().get(0).getAdminId(), request);
        //when
        FaqUpdateRequest updateRequest = new FaqUpdateRequest("스터디", "updateQuestion", "updateAnswer");
        faqService.updateFaq(resultId, updateRequest);
        //then
        FaqEntity expected = faqRepository.findById(resultId).get();
        assertThat(expected).isNotNull();
        assertEquals("updateQuestion", expected.getQuestion());
        assertEquals("updateAnswer", expected.getAnswer());
    }

    @Test
    @DisplayName("FAQ 게시글 삭제")
    void deleteFaq() {
        //given
        Long faqId = faqRepository.findAll().get(0).getId();
        //when
        Long resultId = faqService.deleteFaq(faqId);
        //then
        assertThat(resultId).isNotNull();
        assertThat(faqRepository.findById(resultId)).isEmpty();
    }
}