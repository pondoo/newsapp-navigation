package com.pondoo.application.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class News(
    @Json(name = "articles")
    var articles: List<Article>?,
    @Json(name = "status")
    var status: String?,
    @Json(name = "totalResults")
    var totalResults: Int?
)

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "author")
    var author: String?,
    @Json(name = "content")
    var content: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "publishedAt")
    var publishedAt: String?,
    @Json(name = "source")
    var source: Source?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "url")
    var url: String?,
    @Json(name = "urlToImage")
    var urlToImage: String?
)

@JsonClass(generateAdapter = true)
data class Source(
    @Json(name = "id")
    var id: Any?,
    @Json(name = "name")
    var name: String?
)