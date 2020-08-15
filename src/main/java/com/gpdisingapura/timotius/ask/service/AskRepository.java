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

//public interface AskRepository extends JpaRepository<Question, Integer>, PagingAndSortingRepository<Question, Integer> {
public interface AskRepository extends JpaRepository<Question, Integer> {

    Page<Question> findAll(Pageable pageable);

      @Query(value = "SELECT count(*) as total FROM question WHERE is_answered = false", nativeQuery = true)
      int noOfRecordFoundForIsAnsweredIsFalse();

    @Query(value = "SELECT * FROM question WHERE posted_by = :name", nativeQuery = true)
    List<Question> findByPostedBy(String name);

    @Query(value = "SELECT * FROM question WHERE is_answered = false order by posted_at desc", nativeQuery = true)
    List<Question> findAllQuestions();

    @Modifying
    @Query(value = "UPDATE question SET is_answered = true WHERE id = :questionId", nativeQuery = true)
    int modifyById(Integer questionId);

}

