package com.example.knewing.test.service;

import com.example.knewing.test.model.Notice;
import com.example.knewing.test.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public Notice convertUrlToNotice(String url) throws IOException, ParseException {
        Notice notice = new Notice();
        notice.fromUrl(url);
        notice = noticeRepository.save(notice);
        return notice;
    }

    public Page<Notice> findAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    public List<Notice> findByUrl(String url) {
        return  noticeRepository.findByUrl(url);
    }

    public Optional<Notice> findById(Long id) {
        return noticeRepository.findById(id);
    }

    public Page<Notice> findByContentContainsKeyword (String keyword, Pageable pageable) {
        return noticeRepository.findByContentLike("%" + keyword + "%", pageable);
    }

    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }
}
