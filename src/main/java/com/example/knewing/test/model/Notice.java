package com.example.knewing.test.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import javax.persistence.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Table
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String subTitle;

    private String author;

    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(unique = true)
    private String url;

    public Notice() {
    }

    public void fromUrl(String url) throws IOException, ParseException {
        Document document = Jsoup.connect(url).get();
        cleanAndFormat(document);
        Elements subTitle = document.getElementsByClass("article-lead");
        Elements author = document.getElementsByClass("author-name");
        Elements date = document.getElementsByClass("entry-date");
        Elements content = document.getElementsByClass("article-content");
        LocalDateTime dateNotice = formatNoticeData(date.text());

        this.setTitle(document.title());
        this.setAuthor(author.first().text());
        this.setSubTitle(subTitle.first().text());
        this.setDateTime(dateNotice);
        this.setContent(content.html());
        this.setUrl(document.baseUri());
    }

    public void cleanAndFormat(Document document) {
        document.getElementsByClass("container-ads-content").remove();
        document.getElementsByClass("jp-relatedposts-headline").remove();
        document.getElementsByClass("article-content").first().getElementsByTag("p").last().remove();
        document.getElementsByTag("figure").remove();
    }

    public LocalDateTime formatNoticeData(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy HH'h'mm");
        Date startDate = df.parse(date);
        return LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm");
        return formatter.format(dateTime);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getContent() {
        return Jsoup.parse(content).html();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
