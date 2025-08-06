package com.example.studyKotlin.blog.repository

import com.example.studyKotlin.blog.entity.WordCount
import org.springframework.data.repository.CrudRepository

interface WordRepository : CrudRepository<WordCount, String> {
    fun findTop10ByOrderByCntDesc(): List<WordCount>   // 상위 10개 wordcount 조회
}