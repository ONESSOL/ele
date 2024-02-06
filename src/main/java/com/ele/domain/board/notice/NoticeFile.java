package com.ele.domain.board.notice;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class NoticeFile {

    @Id @GeneratedValue
    @Column(name = "notice_file_id")
    private Long id;
    private String originalFileName;
    private String storedFileName;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Builder
    public NoticeFile(String originalFileName, String storedFileName, Notice notice) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        createNotice(notice);
    }

    protected NoticeFile() {
    }

    public void createNotice(Notice notice) {
        this.notice = notice;
        notice.addNoticeFile(this);
    }
}
