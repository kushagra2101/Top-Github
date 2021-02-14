package com.kushagragoel.top_github.network.model

data class TopGithubApiOutputBean(
    val count: Int,
    val items: List<Item>,
    val msg: String
)