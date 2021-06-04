package com.example.knewing.test.repository;

import com.example.knewing.test.model.Notice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends CrudRepository<Notice, Long> {

    List<Notice> findAll();

    Optional<Notice> findById(Long id);

    List<Notice> findByUrl(String url);
}
