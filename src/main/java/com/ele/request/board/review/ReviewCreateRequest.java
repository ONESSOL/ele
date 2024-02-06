package com.ele.request.board.review;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class ReviewCreateRequest {

    private String title;
    private String contents;
    private List<MultipartFile> boardFiles;

}
