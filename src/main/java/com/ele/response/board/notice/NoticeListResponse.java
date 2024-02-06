package com.ele.response.board.notice;

import com.ele.domain.board.notice.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NoticeListResponse {

    private Long id;
    private String title;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String createdBy;
    private String updatedBy;

    public static NoticeListResponse toSave(Notice notice) {

        NoticeListResponse response = new NoticeListResponse();
        response.setId(notice.getId());
        response.setTitle(notice.getTitle());
        response.setCreatedTime(notice.getCreateTime());
        response.setUpdatedTime(notice.getUpdatedTime());
        response.setCreatedBy(notice.getCreatedBy());
        response.setUpdatedBy(notice.getUpdatedBy());
        return response;
    }
}
