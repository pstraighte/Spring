package com.sparta.demo.controller;

import java.util.concurrent.RejectedExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.demo.dto.ApiResponseDto;
import com.sparta.demo.dto.PostListResponseDto;
import com.sparta.demo.dto.PostRequestDto;
import com.sparta.demo.dto.PostResponseDto;
import com.sparta.demo.security.UserDetailsImpl;
import com.sparta.demo.service.PostService;
import lombok.RequiredArgsConstructor;

@RestController //@controller+@responce
@RequestMapping("/api") //url과 http를 매칭(매핑)해줌
@RequiredArgsConstructor    //대리 생성자 생성
public class PostController {
    private final PostService postService;

    @GetMapping("/post")//전체글 조회 /토큰제크X
        public ResponseEntity<PostListResponseDto> getPosts() {
            PostListResponseDto result = postService.getPosts();

            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/posts/{id}")//단건조회  /토큰제크X
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postService.getPostById(id);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/post")  //글작성
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        PostResponseDto result = postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/posts/{id}")  //글 수정
    public ResponseEntity<ApiResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        try {
            PostResponseDto result = postService.updatePost(id, requestDto, userDetails.getUser());//토큰 확인
            return ResponseEntity.ok().body(result);    //성공할 경우 게시글 반환
        } catch (RejectedExecutionException e) {    //실패할 경우 아래 메세지 출력
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
    @DeleteMapping("/posts/{id}")   //글 삭제
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.deletePost(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
