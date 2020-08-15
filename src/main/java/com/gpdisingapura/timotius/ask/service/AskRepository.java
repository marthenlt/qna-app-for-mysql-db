package com.gpdisingapura.timotius.ask.service;

import com.gpdisingapura.timotius.ask.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Marthen on 15/08/20.
 */

public interface AskRepository extends JpaRepository<Question, Integer>, PagingAndSortingRepository<Question, Integer> {

    Page<Question> findAllUsersWithPaginationNative(Pageable pageable);

      @Query(value = "SELECT count(*) as total FROM Question q WHERE q.isAnswered = false", nativeQuery = true)
      int noOfRecordFoundForIsAnsweredIsFalse();

    @Query(value = "SELECT q FROM Question q WHERE q.postedBy = :name", nativeQuery = true)
    List<Question> findByPostedBy(String name);

    @Query(value = "SELECT q FROM Question q WHERE q.isAnswered = false order by p.postedAt desc", nativeQuery = true)
    List<Question> findAllQuestions();

    @Modifying
    @Query(value = "UPDATE Question q SET q.isAnswered = true WHERE u.id = :questionId", nativeQuery = true)
    int modifyById(Integer questionId);

}

