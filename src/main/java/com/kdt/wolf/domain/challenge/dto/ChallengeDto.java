package com.kdt.wolf.domain.challenge.dto;


import com.kdt.wolf.global.dto.PageResponse;
import java.time.LocalDate;

import com.kdt.wolf.domain.challenge.entity.ChallengePostEntity;
import java.util.List;
import lombok.Getter;

public class ChallengeDto {
    @Getter
    public static class ChallengePreview {
        private final Long challengePostId;
        private final String img;
        private final String title;
        private final String registrationDate;
        private final String deadline;
        private final ChallengeStatus status;

        public ChallengePreview(Long challengePostId, String img, String title, LocalDate registrationDate, LocalDate deadline, ChallengeStatus status) {
            this.challengePostId = challengePostId;
            this.img = img;
            this.title = title;
            this.registrationDate = registrationDate.toString();
            this.deadline = deadline.toString();
            this.status = status;
        }
    }

    @Getter
    public static class ChallengeDetail{
        private final Long challengeId;
        private final String title;
        private final String registrationDate;
        private final String deadline;
        private final String content;
        private final String manner;
        private final String awardContent;

        public ChallengeDetail(Long challengeId, String title, LocalDate registrationDate, LocalDate deadline, String content, String manner, String awardContent) {
            this.challengeId = challengeId;
            this.title = title;
            this.registrationDate = registrationDate.toString();
            this.deadline = deadline.toString();
            this.content = content;
            this.manner = manner;
            this.awardContent = awardContent;
        }
    }

    public record ChallengePageResponse(
            List<ChallengePreview> challenges,
            PageResponse page
    ) { }
}
