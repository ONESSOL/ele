package com.ele.request.board.notice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoticeCreateRequest {

    private String title;
    private String contents;
    List<MultipartFile> boardFiles = new ArrayList<>();

}
