package com.example.knewing.test.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NoticeTest {

    private Notice notice = new Notice();

    private String URL = "https://www.infomoney.com.br/mercados/" +
                        "itausa-lucra-123-mais-no-1o-tri-a-r-24-bi-prejuizo-da-marisa-" +
                        "cai-50-e-mais-balancos-petrobras-petrorio-e-outros-destaques/";

    @Test
    public void testFrom() {
        try {
            this.notice.fromUrl(URL);
            assertThat(notice.getContent()).isNotNull();
            assertThat(notice.getAuthor()).isNotNull();
            assertThat(notice.getDateTime()).isNotNull();
            assertThat(notice.getSubTitle()).isNotNull();
            assertThat(notice.getTitle()).isNotNull();
            assertThat(notice.getUrl()).isNotNull();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
