package com.kdt.wolf.domain.challenge.entity;

import com.kdt.wolf.domain.user.entity.UserEntity;
import com.kdt.wolf.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "verification")
public class VerificationEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_verification_id")
    @SequenceGenerator(name = "seq_verification_id", sequenceName = "verification_sequence", allocationSize = 1)
    private Long verificationId;

    // ChallengePostEntity와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChallengePostEntity challengePost;

    // ChallengeRegistrationEntity와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChallengeRegistrationEntity registration;

    // UserEntity와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String certificationNo;
    private String institutionName;
    private String content;

    // 기본값 'N' 설정
    @Column(columnDefinition = "NUMBER(1) DEFAULT 0", nullable = false)
    private boolean isVerification = false;


    @Builder
    public VerificationEntity(ChallengeRegistrationEntity registration, ChallengePostEntity challengePost, UserEntity user, String certificationNo, String institutionName, String content) {
        this.registration = registration;
        this.challengePost = challengePost;
        this.user = user;
        this.certificationNo = certificationNo;
        this.institutionName = institutionName;
        this.content = content;
    }

    public void updateVerification(boolean status) {
        this.isVerification = status;
    }
}
