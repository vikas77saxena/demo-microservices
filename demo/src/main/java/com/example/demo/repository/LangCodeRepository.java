package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LangCode;

@Repository
public interface LangCodeRepository extends JpaRepository<LangCode, Integer> {

  Optional<LangCode> findByLangCode(String langCode);

}
    


