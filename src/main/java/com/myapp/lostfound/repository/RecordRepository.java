package com.myapp.lostfound.repository;

import com.myapp.lostfound.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Integer> {


    List<Record> findAll();

    Record findById(int id);

    Record save(Record record);

    void delete(Record record);

    List<Record> findAllByLostUserId(int lostUserId);

    List<Record> findAllByStatus(int status);

    @Query("select r from Record r where r.status = 0 and r.content like concat('%',:content,'%') ")
    List<Record> findAllByContentLike(@Param("content")String content);
}
