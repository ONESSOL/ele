package com.ele.controller.board.notice;

import com.ele.request.board.notice.NoticeCreateRequest;
import com.ele.request.board.notice.NoticeUpdateRequest;
import com.ele.response.board.notice.NoticeCreateResponse;
import com.ele.response.board.notice.NoticeDetailResponse;
import com.ele.response.board.notice.NoticeListResponse;
import com.ele.response.board.notice.NoticeUpdateResponse;
import com.ele.service.board.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/save")
    public ResponseEntity<NoticeCreateResponse> saveNotice(@ModelAttribute NoticeCreateRequest request) throws IOException {
        return ResponseEntity.ok(noticeService.saveNotice(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.findById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<NoticeListResponse>> findAll(@PageableDefault(page = 1, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(noticeService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoticeListResponse>> findByTitleAndContents(@RequestParam String search,
                                                                           @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(noticeService.findByTitleAndContents(search, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeUpdateResponse> update(@PathVariable Long id, @ModelAttribute NoticeUpdateRequest request) {
        return ResponseEntity.ok(noticeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }
}


























