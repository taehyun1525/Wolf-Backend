package com.kdt.wolf.domain.group.entity;

import com.kdt.wolf.domain.group.entity.common.TaskStatus;
import com.kdt.wolf.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_task_id")
    @SequenceGenerator(name = "seq_task_id", sequenceName = "seq_task", allocationSize = 1)
    private Long taskId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "group_post_id", nullable = false)
    private GroupPostEntity groupPostId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", nullable = false)
    private UserEntity userId;

    @Column
    private String details;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Builder
    public TaskEntity(GroupPostEntity groupPostId, UserEntity userId, String details, TaskStatus status) {
        this.groupPostId = groupPostId;
        this.userId = userId;
        this.details = details;
        this.status = status;
    }
}
