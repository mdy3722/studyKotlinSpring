package com.example.studyKotlin.blog.service

import com.example.studyKotlin.blog.dto.BlogDto
import com.example.studyKotlin.blog.entity.WordCount
import com.example.studyKotlin.blog.repository.WordRepository
import com.example.studyKotlin.core.exception.InvalidInputException
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class BlogService(
    val wordRepository: WordRepository
) {
    @Value("\${REST_API_KEY}")
    lateinit var restApiKey: String

    fun searchKakao(blogDto: BlogDto): String? {
// 별도 validator로 DTO에서 유효성 검증하므로 아래 부분 주석처리
//        val msgList = mutableListOf<ExceptionMsg>()   // mutableListOf<타입>() - 변경 가능한 리스트. add(), remove(), clear() 등으로 내용 변경 가능. val은 변경불가 = 참조값을 변경 못한다는 뜻
//
//        if (blogDto.query.trim().isEmpty()) {
//            msgList.add(ExceptionMsg.EMPTY_QUERY)
//        }
//
//        if (blogDto.sort.trim() !in arrayOf("ACCURACY", "RECENCY")) {
//            msgList.add(ExceptionMsg.NOT_IN_SORT)
//        }
//
//        when {
//            blogDto.page < 1 -> msgList.add(ExceptionMsg.LESS_THAN_MIN)
//            blogDto.page > 50 -> msgList.add(ExceptionMsg.MORE_THAN_MAX)
//        }
//
//        if (msgList.isNotEmpty()) {
//            val message = msgList.joinToString { it.msg }
//            throw InvalidInputException(message)
//        }

        val webClient = WebClient
            .builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webClient
            .get()
            .uri { it.path("/v2/search/blog")
                .queryParam("query", blogDto.query)
                .queryParam("sort", blogDto.sort)
                .queryParam("page", blogDto.page)
                .queryParam("size", blogDto.size)
                .build()
            }
            .header("Authorization", "KakaoAK ${restApiKey}")
            .retrieve()
            .bodyToMono(String::class.java)
        val result = response.block()

        val lowQuery: String = blogDto.query.lowercase()   // 소문자처리
        val word: WordCount = wordRepository.findById(lowQuery).orElse(WordCount(lowQuery))   // DB에서 못찾으면 새 객체 반환

        word.cnt++   // 검색 카운트 증가
        wordRepository.save(word)
        return result
    }


    fun searchWordRank(): List<WordCount> = wordRepository.findTop10ByOrderByCntDesc()   // 리턴문 한 문장만 있으니 이렇게 작성 가능

}

//private enum class ExceptionMsg(val msg: String) {
//    EMPTY_QUERY("query parameter required"),
//    NOT_IN_SORT("sort parameter one of accuracy and recency"),
//    LESS_THAN_MIN("page is less than min"),
//    MORE_THAN_MAX("page is more than max")
//}