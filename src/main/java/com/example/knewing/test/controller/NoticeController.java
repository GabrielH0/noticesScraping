package com.example.knewing.test.controller;

import com.example.knewing.test.service.NoticeService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("/notices")
    public ModelAndView getAllNotices() {
        ModelAndView mav = new ModelAndView("notices");
        mav.addObject("notices", noticeService.findAll());
        return mav;
    }

    @PostMapping("/notices")
    public ModelAndView postNotice(@RequestParam("url") String url) {
        ModelAndView noticesMav = new ModelAndView("notices");
        try {
            noticeService.convertUrlToNotice(url);
        } catch (Exception e) {
            e.printStackTrace();
            noticesMav.addObject("error", "Erro inesperado ao processar a URL informada");
        }
        noticesMav.addObject("notices", noticeService.findAll());
        return noticesMav;
    }

    @PostMapping("/notices/filter")
    public ModelAndView filterNotice(@RequestParam("keyword") String keyword) {
        ModelAndView mav = new ModelAndView("notices");
        mav.addObject("keyword", keyword);
        mav.addObject("notices", noticeService.findByContentContainsKeyword(keyword));
        return mav;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ModelAndView getUrlAlreadyRegistred() {
        ModelAndView mav = new ModelAndView("notices");
        mav.addObject("error", "A URL informada já foi cadastrada");
        mav.addObject("notices", noticeService.findAll());
        return mav;
    }
}
