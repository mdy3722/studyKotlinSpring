package com.example.studyKotlin.blog.controller

import com.example.studyKotlin.blog.dto.BlogDto
import com.example.studyKotlin.blog.entity.WordCount
import com.example.studyKotlin.blog.service.BlogService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/blog")
@RestController
class BlogController(
    val blogService: BlogService
) {
    // 컨트롤러 1
    @GetMapping("")
    fun search(@RequestBody @Valid blogDto: BlogDto): String? {
        val result = blogService.searchKakao((blogDto))
        return result
    }

    // 컨트롤러 2
    @GetMapping("/rank")
    fun searchWordRank(): List<WordCount> = blogService.searchWordRank()
}
