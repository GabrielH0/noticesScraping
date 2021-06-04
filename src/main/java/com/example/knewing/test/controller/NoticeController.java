package com.example.knewing.test.controller;

import com.example.knewing.test.model.Notice;
import com.example.knewing.test.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("/notices")
    public ModelAndView getAllNotices() {
        try {
            noticeService.convertUrlToNotice("https://www.infomoney.com.br/mercados/itausa-lucra-123-mais-no-1o-tri-a-r-24-bi-prejuizo-da-marisa-cai-50-e-mais-balancos-petrobras-petrorio-e-outros-destaques/");
            noticeService.convertUrlToNotice("https://www.infomoney.com.br/mercados/adrs-brasileiros-caem-em-ny-com-correcao-enquanto-investidores-digerem-dado-forte-de-emprego-nos-eua/");
        } catch (Exception e) {

        }
        ModelAndView mav = new ModelAndView("notices");
        mav.addObject("notices", noticeService.findAll());
        return mav;
    }

    @PostMapping("/notices")
    public String postNotice(@ModelAttribute("url") String url) {
        try {
            noticeService.convertUrlToNotice(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/notices";
    }
}
