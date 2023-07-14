package com.sparta.post.Controller;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //@controller+@responce
@RequestMapping("/api") //url과 http를 매칭(매핑)해줌
@RequiredArgsConstructor    //대리 생성자 생성
public class PostController {
    private final PostService postService;  //선언(소환)

    @GetMapping("/post")//전체글 조회
    public List<PostResponseDto> getAllPost() {
        return postService.getAllPost();
    }

    @GetMapping("/post/{id}")   //단건 조회
    public PostResponseDto getPost(@PathVariable Long id) { //@PathVariable 경로를 받아옴
        return postService.getPost(id);
    }

    @PostMapping("/post")  //글작성
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto) { //@RequestBody 쓰면 생성자 안만들어도 ok
        return postService.creatPost(postRequestDto);
    }

    @PutMapping("/post/{id}")   //글수정
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(id, postRequestDto);
    }

    @DeleteMapping("/post/{id}")    //글삭제
    public PostResponseDto deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        postService.deletePost(id, postRequestDto.getPassword());
        return new PostResponseDto(true);
    }
}
