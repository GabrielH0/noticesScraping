package com.example.knewing.test.service;

import com.example.knewing.test.repository.NoticeRepository;
import com.example.knewing.test.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public List<Notice> findByUrl(String url) {
        return  noticeRepository.findByUrl(url);
    }

    public List<Notice> findByContentContainsKeyword (String keyword) {
        return noticeRepository.findByContentLike("%" + keyword + "%");
    }

    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }
}
