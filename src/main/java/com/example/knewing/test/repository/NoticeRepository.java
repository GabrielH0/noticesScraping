package com.example.knewing.test.repository;

import com.example.knewing.test.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findById(Long id);

    List<Notice> findByUrl(String url);

    Page<Notice> findByContentLike(String keyword, Pageable pageable);
}
