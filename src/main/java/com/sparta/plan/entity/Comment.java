package com.sparta.plan.entity;

import com.sparta.plan.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto requestDto, Plan plan, User user) {
        this.commentContent = requestDto.getCommentContent();
        this.plan = plan;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.commentContent = requestDto.getCommentContent();
    }
}
