package fr.ineat.coroutine

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by mehdisli on 03/05/2018.
 */

data class Repo(val id: Int, val name: String, val url: String)

interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Deferred<List<Repo>>
}

private var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

val githubService = retrofit.create(GitHubService::class.java)
