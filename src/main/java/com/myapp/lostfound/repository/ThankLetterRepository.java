package com.myapp.lostfound.repository;

import com.myapp.lostfound.domain.ThankLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThankLetterRepository extends JpaRepository<ThankLetter,Integer> {
    List<ThankLetter> findAll();

    ThankLetter save(ThankLetter ThankLetter);

    ThankLetter findByUserId(int userId);

    void delete(ThankLetter ThankLetter);
}
