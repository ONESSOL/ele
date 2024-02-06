package com.ele.domain.board.notice;

import com.ele.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
public class Notice extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;
    private String title;
    private String contents;
    private int fileAttached;
    @OneToMany(mappedBy = "notice", cascade = ALL, orphanRemoval = true)
    private List<NoticeFile> noticeFiles = new ArrayList<>();

    @Builder
    public Notice(String title, String contents, int fileAttached) {
        this.title = title;
        this.contents = contents;
        this.fileAttached = fileAttached;
    }
    protected Notice() {
    }

    public void addNoticeFile(NoticeFile noticeFile) {
        this.noticeFiles.add(noticeFile);
    }

    public void update(String title, String contents, int fileAttached) {
        this.title = title;
        this.contents = contents;
        this.fileAttached = fileAttached;
    }
}


























