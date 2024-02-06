package com.ele.service.board.notice;

import com.ele.domain.board.notice.Notice;
import com.ele.domain.board.notice.NoticeFile;
import com.ele.exception.board.BoardNotFoundException;
import com.ele.exception.board.FileUploadFailureException;
import com.ele.repository.board.notice.NoticeFileRepository;
import com.ele.repository.board.notice.NoticeRepository;
import com.ele.request.board.notice.NoticeCreateRequest;
import com.ele.request.board.notice.NoticeUpdateRequest;
import com.ele.response.board.notice.NoticeCreateResponse;
import com.ele.response.board.notice.NoticeDetailResponse;
import com.ele.response.board.notice.NoticeListResponse;
import com.ele.response.board.notice.NoticeUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class NoticeService {

    @Value("${upload.notice_image.location}")
    private String location;
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

    @Transactional
    public NoticeCreateResponse saveNotice(NoticeCreateRequest request) {

        if (request.getBoardFiles().get(0).isEmpty()) {
            Notice notice = noticeRepository.save(Notice.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .fileAttached(0)
                    .build());
            return NoticeCreateResponse.toSave(notice);
        } else {
            Notice notice = noticeRepository.save(Notice.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .fileAttached(1)
                    .build());
            saveFile(request.getBoardFiles(), notice);
            return NoticeCreateResponse.toSave(notice);
        }
    }

    @Transactional(readOnly = true)
    public NoticeDetailResponse findById(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(BoardNotFoundException::new);
        return NoticeDetailResponse.toSave(notice);
    }

    @Transactional(readOnly = true)
    public Page<NoticeListResponse> findAll(Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Notice> noticeList = noticeRepository.findAll(pageRequest);
        return noticeList.map(NoticeListResponse::toSave);
    }

    @Transactional(readOnly = true)
    public Page<NoticeListResponse> findByTitleAndContents(String search, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Notice> noticeList = noticeRepository.findByTitleAndContents(search, pageRequest);
        return noticeList.map(NoticeListResponse::toSave);
    }

    @Transactional
    public NoticeUpdateResponse update(Long noticeId, NoticeUpdateRequest request) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(BoardNotFoundException::new);
        if(notice.getFileAttached() == 0) {
            if(request.getBoardFiles().get(0).isEmpty()) {
                notice.update(request.getTitle(), request.getContents(), 0);
            } else {
                saveFile(request.getBoardFiles(), notice);
                notice.update(request.getTitle(), request.getContents(), 1);
            }
        } else {
            if(request.getBoardFiles().get(0).isEmpty()) {
                notice.getNoticeFiles().removeAll(notice.getNoticeFiles());
                notice.update(request.getTitle(), request.getContents(), 0);
            } else {
                notice.getNoticeFiles().removeAll(notice.getNoticeFiles());
                saveFile(request.getBoardFiles(), notice);
                notice.update(request.getTitle(), request.getContents(), 1);
            }
        }
        return NoticeUpdateResponse.toSave(notice);
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    private void saveFile(List<MultipartFile> boardFiles, Notice notice) {
        for(MultipartFile boardFile : boardFiles) {
            String originalFileName = boardFile.getOriginalFilename();
            String storedFileName = UUID.randomUUID() + "_" + originalFileName;
            String savePath = location + storedFileName;
            File file = new File(savePath);
            if(!file.exists()) {
                file.mkdirs();
            }
            try {
                boardFile.transferTo(file);
            } catch (IOException e) {
                throw new FileUploadFailureException();
            }
            noticeFileRepository.save(NoticeFile.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .notice(notice)
                    .build());
        }
    }
}























