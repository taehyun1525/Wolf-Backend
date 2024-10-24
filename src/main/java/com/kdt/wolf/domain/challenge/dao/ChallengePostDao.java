package com.kdt.wolf.domain.challenge.dao;

import com.kdt.wolf.domain.challenge.dto.ChallengeAdminDto.VerificationDetail;
import com.kdt.wolf.domain.challenge.dto.ChallengeAdminDto.VerificationPreview;
import com.kdt.wolf.domain.challenge.dto.ChallengeStatus;
import com.kdt.wolf.domain.challenge.dto.request.ChallengeCreationRequest.ChallengeCreateRequest;
import com.kdt.wolf.domain.challenge.dto.request.ChallengePaymentRequest;
import com.kdt.wolf.domain.challenge.dto.request.ChallengeRegistrationRequest;
import com.kdt.wolf.domain.challenge.dto.request.ChallengeVerificationRequest.VerificationRequest;
import com.kdt.wolf.domain.challenge.entity.*;
import com.kdt.wolf.domain.challenge.repository.*;
import com.kdt.wolf.domain.group.entity.GroupMemberEntity;
import com.kdt.wolf.domain.group.entity.GroupPostEntity;
import com.kdt.wolf.domain.group.repository.GroupPostRepository;
import com.kdt.wolf.domain.user.entity.UserEntity;
import com.kdt.wolf.domain.user.repository.UserRepository;
import com.kdt.wolf.global.exception.NotFoundException;
import com.kdt.wolf.global.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ChallengePostDao {

    private final ChallengeRegistrationQueryRepository challengeRegistrationQueryRepository;
    private final ChallengePostRepository challengePostRepository;
    private final GroupPostRepository groupPostRepository;
    private final UserRepository userRepository;
    private final GroupChallengeParticipantRepository groupChallengeParticipantRepository;
    private final ChallengePaymentRepository challengePaymentRepository;
    private final VerificationRepository verificationRepository;

    // 챌린지(단일) 불러오기
    public ChallengePostEntity findById(Long challengePostId){
        return challengePostRepository.findById(challengePostId).orElseThrow(NotFoundException::new);
    }

    // 챌린지 목록 불러오기(관리자)
    public Page<ChallengePostEntity> findAll(Pageable pageable){

        return challengePostRepository.findAll(pageable);

    }

    public Page<ChallengeRegistrationEntity> findChallengesByStatus(Long groupId, Long userId, ChallengeStatus status, Pageable pageable) {
        return switch (status) {
            case CERTIFICATION -> challengeRegistrationQueryRepository.findCertifiableChallenges(groupId, userId, pageable);
            case CERTIFICATION_COMPLETE -> challengeRegistrationQueryRepository.findCertifiedChallenges(groupId, userId, pageable);
            case RESULT_CONFIRM -> challengeRegistrationQueryRepository.findCompletedChallenges(groupId, userId, pageable);
            case PAY -> challengeRegistrationQueryRepository.findPayableChallenge(groupId, userId, pageable);
            case PARTICIPATE -> challengeRegistrationQueryRepository.findJoinableChallenges(groupId, userId, pageable);
            default -> throw new IllegalArgumentException("Unexpected status: " + status);
        };
    }

    public Page<ChallengePostEntity> findAvailableChallenges(Long groupId, Pageable pageable) {
        //ChallengeStatus.APPLY
        return challengeRegistrationQueryRepository.findApplicableChallenges(groupId, pageable);
    }


    // 챌린지 신청(그룹장)

    public void createChallengeRegistration(GroupPostEntity group, ChallengePostEntity challengePost, String challengeAmount) {

        ChallengeRegistrationEntity registration = new ChallengeRegistrationEntity(
                challengePost,
                group,
                Long.parseLong(challengeAmount == null ? "0" :challengeAmount)
        );

        challengeRegistrationQueryRepository.save(registration);
    }

    // 챌린지 참여
    public void createChallengeRegistrations(ChallengeRegistrationRequest request, long userId) {
        ChallengeRegistrationEntity registration = challengeRegistrationQueryRepository
                .findChallengeRegistration(request.getGroupPostId(), request.getChallengePostId());
        UserEntity user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        GroupChallengeParticipantEntity entity = new GroupChallengeParticipantEntity(registration, user);
        groupChallengeParticipantRepository.save(entity);
    }


    // 챌린지 인증
    @Transactional
    public Long updateVerification(VerificationRequest request, long id) {
        ChallengeRegistrationEntity registration = challengeRegistrationQueryRepository
                .findChallengeRegistration(request.groupPostId(), request.challengePostId());
        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        VerificationEntity verificationEntity = new VerificationEntity(
                registration,
                registration.getChallengePost(),
                user,
                request.certificationNo(),
                request.institutionName(),
                request.content()
        );

        boolean status = request.status().equals("Y");
        GroupChallengeParticipantEntity entity = groupChallengeParticipantRepository.findGroupChallengeParticipantEntity(registration, user);
        entity.updateParticipationStatus();
        groupChallengeParticipantRepository.save(entity);
        verificationEntity.updateVerification(status);
        return verificationRepository.save(verificationEntity).getVerificationId();
    }

    // 챌린지 생성
    public Long createChallenge(ChallengeCreateRequest request,String imageUrl, Long userId){
        ChallengePostEntity entity = ChallengePostEntity.builder()
                .userId(userId)
                .img(imageUrl)
                .title(request.title())
                .content(request.content())
                .manner(request.manner())
                .awardContent(request.awardContent())
                .deadline(request.deadline())
                .build();
        return challengePostRepository.save(entity).getChallengePostId();
    }

    // 챌린지 수정
    public Long updateChallenge(ChallengeCreateRequest request,String imageUrl,Long challengePostId){
        ChallengePostEntity entity = challengePostRepository.findById(challengePostId).orElseThrow(NotFoundException::new);
        entity.updateChallengePost(request, imageUrl);
        return challengePostRepository.save(entity).getChallengePostId();
    }

    // 챌린지 삭제
    public void deleteChallenge(Long challengePostId){
        challengePostRepository.deleteById(challengePostId);
    }

    // 챌린지 결제
    public void payChallenge(ChallengePaymentRequest request, Long userId){
        ChallengeRegistrationEntity registration = challengeRegistrationQueryRepository
                .findChallengeRegistration(request.getGroupPostId(), request.getChallengePostId());
        UserEntity user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        if(request.getPayStatus().equals("Y")){
            GroupChallengeParticipantEntity participantEntity = groupChallengeParticipantRepository
                    .findGroupChallengeParticipantEntity(registration, user);
            participantEntity.updatePaymentStatus();
            groupChallengeParticipantRepository.save(participantEntity);
        }

        PaymentEntity paymentEntity = new PaymentEntity(registration, user, request.getAmount());
        challengePaymentRepository.save(paymentEntity);
    }

    // 결제 정보 조회
    public PaymentEntity getPayment(Long paymentId){
        return challengePaymentRepository.findById(paymentId).orElseThrow(NotFoundException::new);
    }

    public Page<VerificationEntity> getAllVerifications(Pageable pageable) {
        Page<VerificationEntity> verifications = verificationRepository.findAll(pageable);

        return verifications;

    }

    public VerificationDetail getVerification(Long verificationId) {
        VerificationEntity verification = verificationRepository.findById(verificationId).orElseThrow(NotFoundException::new);
        return new VerificationDetail(
                verification.getVerificationId(),
                verification.getRegistration().getGroupPost().getGroupPostId(),
                verification.getChallengePost().getChallengePostId(),
                verification.getUser().getNickname(),
                verification.getChallengePost().getTitle(),
                verification.getCertificationNo(),
                verification.getInstitutionName(),
                verification.getContent(),
                verification.getCreatedTime().toLocalDate().toString(),
                verification.isVerification() ? "인증 성공" : "인증 실패"
        );
    }

    public Long countByGroupPostId(Long groupId) {
        return challengeRegistrationQueryRepository.countByGroupPostId(groupId);
    }

    public List<ChallengePostEntity> findByGroupPost(GroupPostEntity groupPost) {
        return challengeRegistrationQueryRepository.findByGroupPost(groupPost);
    }


    public List<GroupChallengeParticipantEntity> findParticipants(GroupMemberEntity user, ChallengePostEntity challenge, GroupPostEntity groupPost) {
        return groupChallengeParticipantRepository.findMemberByGroupPost(user, challenge, groupPost);
    }
}
