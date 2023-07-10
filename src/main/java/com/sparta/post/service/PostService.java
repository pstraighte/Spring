package com.sparta.post.service;

import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.PostResponseDto;
import com.sparta.post.entity.Post;
import com.sparta.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service    //나는 서비스다, 선언
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<PostResponseDto> getAllPost() { //전체조회
        List<Post> postlist = postRepository.findAllByOrderByCreatedAtDesc();//다찾고, 정렬하고, 생성순, 내림차순으로
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postlist) {    //Entity이름 적기
            {
                postResponseDtoList.add(new PostResponseDto(post));
            }
        }
        return postResponseDtoList;
    }

    public PostResponseDto getPost(Long id) {   //단건조회
        Post post = findPost(id);   //find메소드는 아래
        return new PostResponseDto(post);
    }

    public PostResponseDto creatPost(PostRequestDto requestDto) { //게시물 생성
        Post post = postRepository.save(new Post(requestDto));//받은 request로 Post 생성후 저장
        return new PostResponseDto(post);//response로 반환
    }

    @Transactional//자동 DB반영 에너테이션
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = findPost(id);   //게시글 검색

        post.checkPassword(postRequestDto.getPassword());//비번 체크

        post.setTitle(postRequestDto.getTitle());//게시글 변경
        post.setUsername(postRequestDto.getUsername());
        post.setContents(postRequestDto.getContents());

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, String password) {
        Post post = findPost(id);   //게시글 검색
        post.checkPassword(password);//비번 체크
        postRepository.delete(post);    //삭제
    }


    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->    //findById->기본지원메소드.
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }
} //JPA지원메소드는(Optional) 사용. 옵셔널은 결과값이 없어도 뭔가 처리를 해야한다
//그래서 .orElseThrow로 결과없음 메세지 출력