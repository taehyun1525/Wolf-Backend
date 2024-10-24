package com.kdt.wolf.domain.challenge.entity;

import com.kdt.wolf.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_payment_id")
    @SequenceGenerator(name = "seq_payment_id", sequenceName = "payment_sequence", allocationSize = 1)
    private Long paymentId;

    // ChallengeRegistrationEntity와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChallengeRegistrationEntity registration;

    // UserEntity와 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Long amount;
    private LocalDate paymentDate;

    @Builder
    public PaymentEntity(ChallengeRegistrationEntity registration, UserEntity user, Long amount) {
        this.registration = registration;
        this.user = user;
        this.amount = amount;
        this.paymentDate = LocalDate.now();
    }

}