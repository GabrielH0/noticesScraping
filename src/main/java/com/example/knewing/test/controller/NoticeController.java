package com.example.knewing.test.controller;

import com.example.knewing.test.model.DataPage;
import com.example.knewing.test.service.NoticeService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
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
        mav.addObject("notices", noticeService.findAll(getPageRequest(dataPage)));
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
        noticesMav.addObject("notices", noticeService.findAll(getPageRequest(dataPage)));
        return noticesMav;
    }

    @PostMapping("/notices/filter")
    public ModelAndView filterNotice(@RequestParam("keyword") String keyword) {
        dataPage.initPageAndSize();

        ModelAndView mav = new ModelAndView("notices");
        mav.addObject("keyword", keyword);
        mav.addObject("notices", noticeService.findByContentContainsKeyword(keyword, getPageRequest(dataPage)));
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
