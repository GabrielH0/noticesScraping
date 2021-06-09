package com.example.knewing.test.controller;

import com.example.knewing.test.model.DataPage;
import com.example.knewing.test.model.Notice;
import com.example.knewing.test.service.NoticeService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private DataPage dataPage;

    @GetMapping("/notices")
    public ModelAndView getAllNotices() {
        dataPage.initPageAndSize();
        ModelAndView mav = new ModelAndView("notices");
        Page<Notice> notices = noticeService.findAll(getPageRequest(dataPage));
        mav.addObject("notices", notices);
        mav.addObject("readingNotice", notices.stream().findFirst().orElse(new Notice()));
        return mav;
    }

    @GetMapping("/notice")
    public ModelAndView getNoticeById(@RequestParam("id") Long id) {
        dataPage.initPageAndSize();
        ModelAndView mav = new ModelAndView("notices");
        mav.addObject("notices", noticeService.findAll(getPageRequest(dataPage)));
        mav.addObject("readingNotice", noticeService.findById(id).orElse(new Notice()));
        return mav;
    }

    @PostMapping("/notices")
    public ModelAndView postNotice(@RequestParam("url") String url) {
        ModelAndView noticesMav = new ModelAndView("notices");
        dataPage.initPageAndSize();

        try {
            noticeService.convertUrlToNotice(url);
        } catch (Exception e) {
            e.printStackTrace();
            noticesMav.addObject("error", "Erro inesperado ao processar a URL informada");
        }
        Page<Notice> notices = noticeService.findAll(getPageRequest(dataPage));
        noticesMav.addObject("notices", notices);
        noticesMav.addObject("readingNotice", notices.stream().findFirst().orElse(new Notice()));
        return noticesMav;
    }

    @PostMapping("/notices/filter")
    public ModelAndView filterNotice(@RequestParam("keyword") String keyword) {
        dataPage.initPageAndSize();

        ModelAndView mav = new ModelAndView("notices");
        Page<Notice> noticesByKeyword = noticeService.findByContentContainsKeyword(keyword, getPageRequest(dataPage));
        mav.addObject("keyword", keyword);
        mav.addObject("notices", noticesByKeyword);
        mav.addObject("readingNotice", noticesByKeyword.stream().findFirst().orElse(new Notice()));

        return mav;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ModelAndView getUrlAlreadyRegistred() {
        ModelAndView mav = new ModelAndView("notices");
        dataPage.initPageAndSize();

        mav.addObject("error", "A URL informada já foi cadastrada");
        mav.addObject("notices", noticeService.findAll(getPageRequest(dataPage)));
        return mav;
    }

    public static PageRequest getPageRequest(DataPage dataPage) {
        return PageRequest.of(dataPage.getPAGE(), dataPage.getSIZE());
    }
}
