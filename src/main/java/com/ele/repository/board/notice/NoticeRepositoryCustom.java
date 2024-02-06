package com.ele.repository.board.notice;

import com.ele.domain.board.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {

    Page<Notice> findByTitleAndContents(String search, Pageable pageable);
}
