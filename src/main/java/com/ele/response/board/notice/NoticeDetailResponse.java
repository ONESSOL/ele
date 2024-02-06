package com.ele.response.board.notice;

import com.ele.domain.board.notice.Notice;
import com.ele.domain.board.notice.NoticeFile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoticeDetailResponse {

    private String title;
    private String contents;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String createdBy;
    private String updatedBy;
    private int fileAttached;
    private List<String> originalFileNames;
    private List<String> storedFileNames;

    public static NoticeDetailResponse toSave(Notice notice) {

        NoticeDetailResponse response = new NoticeDetailResponse();
        response.setTitle(notice.getTitle());
        response.setContents(notice.getContents());
        response.setCreatedTime(notice.getCreateTime());
        response.setUpdatedTime(notice.getUpdatedTime());
        response.setCreatedBy(notice.getCreatedBy());
        response.setUpdatedBy(notice.getUpdatedBy());
        if(notice.getFileAttached() == 1) {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            for(NoticeFile noticeFile : notice.getNoticeFiles()) {
                originalFileNameList.add(noticeFile.getOriginalFileName());
                storedFileNameList.add(noticeFile.getStoredFileName());
            }
            response.setOriginalFileNames(originalFileNameList);
            response.setStoredFileNames(storedFileNameList);
        }
        return response;
    }
}
