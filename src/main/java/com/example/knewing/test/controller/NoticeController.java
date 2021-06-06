package com.example.knewing.test.controller;

import com.example.knewing.test.service.NoticeService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;

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
    public String postNotice(@RequestParam("url") String url) {
        try {
            noticeService.convertUrlToNotice(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        }
        return "redirect:/notices";
    }

    @PostMapping("/notices/filter")
    public ModelAndView filterNotice(@RequestParam("keyword") String keyword) {
        ModelAndView mav = new ModelAndView("notices");
        mav.addObject("keyword", keyword);
        mav.addObject("notices", noticeService.findByContentContainsKeyword(keyword));
        return mav;
    }

}
