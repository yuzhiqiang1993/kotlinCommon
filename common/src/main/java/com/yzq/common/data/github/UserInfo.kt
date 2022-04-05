package com.yzq.common.data.github


import com.squareup.moshi.Json

data class UserInfo(
    @Json(name = "avatar_url")
    val avatarUrl: String, // https://avatars.githubusercontent.com/u/18605757?v=4
    val bio: String, // Android
    val blog: String,
    val company: String?, // null
    @Json(name = "created_at")
    val createdAt: String, // 2016-04-22T01:21:47Z
    val email: String?, // null
    @Json(name = "events_url")
    val eventsUrl: String, // https://api.github.com/users/yuzhiqiang1993/events{/privacy}
    val followers: Int, // 44
    @Json(name = "followers_url")
    val followersUrl: String, // https://api.github.com/users/yuzhiqiang1993/followers
    val following: Int, // 6
    @Json(name = "following_url")
    val followingUrl: String, // https://api.github.com/users/yuzhiqiang1993/following{/other_user}
    @Json(name = "gists_url")
    val gistsUrl: String, // https://api.github.com/users/yuzhiqiang1993/gists{/gist_id}
    @Json(name = "gravatar_id")
    val gravatarId: String,
    val hireable: Any?, // null
    @Json(name = "html_url")
    val htmlUrl: String, // https://github.com/yuzhiqiang1993
    val id: Int, // 18605757
    val location: String, // shanghai
    val login: String, // yuzhiqiang1993
    val name: String, // 喻志强
    @Json(name = "node_id")
    val nodeId: String, // MDQ6VXNlcjE4NjA1NzU3
    @Json(name = "organizations_url")
    val organizationsUrl: String, // https://api.github.com/users/yuzhiqiang1993/orgs
    @Json(name = "public_gists")
    val publicGists: Int, // 0
    @Json(name = "public_repos")
    val publicRepos: Int, // 21
    @Json(name = "received_events_url")
    val receivedEventsUrl: String, // https://api.github.com/users/yuzhiqiang1993/received_events
    @Json(name = "repos_url")
    val reposUrl: String, // https://api.github.com/users/yuzhiqiang1993/repos
    @Json(name = "site_admin")
    val siteAdmin: Boolean, // false
    @Json(name = "starred_url")
    val starredUrl: String, // https://api.github.com/users/yuzhiqiang1993/starred{/owner}{/repo}
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String, // https://api.github.com/users/yuzhiqiang1993/subscriptions
    @Json(name = "twitter_username")
    val twitterUsername: Any?, // null
    val type: String, // User
    @Json(name = "updated_at")
    val updatedAt: String, // 2022-02-22T06:58:11Z
    val url: String // https://api.github.com/users/yuzhiqiang1993


)