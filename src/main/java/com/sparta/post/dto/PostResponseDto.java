package com.sparta.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  //NULL 아닌것만 Json 변환
public class PostResponseDto {
    private Boolean success;
    private Long id;
    private String title;
    private String username;
    private String contents;
    private String password; //중요정보이기에 반환X
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post){  //생성자1 게시글 생성,수정,삭제용
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
    public PostResponseDto(Boolean success){    //생성자2 삭제시 success 값 받아오는 용
        this.success = success;
    }
}
