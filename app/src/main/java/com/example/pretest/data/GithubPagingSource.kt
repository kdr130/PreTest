package com.example.pretest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pretest.api.GithubService
import com.example.pretest.data.GithubRepository.Companion.DEFAULT_PAGE_SIZE
import com.example.pretest.model.User
import com.example.pretest.utils.UrlUtil
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val DEFAULT_PAGE_INDEX = 1
private const val KET_LINK_HEADER = "link"
private const val PARAMETER_TYPE_PAGE = "page"
private const val REL_TYPE_NEXT = "\"next\""

class GithubPagingSource (
    private val service: GithubService,
    private val query: String
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: DEFAULT_PAGE_INDEX
        val apiQuery = query
        return try {
            val response = service.searchUser(apiQuery, position, DEFAULT_PAGE_SIZE)

            if (response.isSuccessful) {
                val repos = response.body()?.items ?: emptyList()
                val nextKey = if (repos.isEmpty()) {
                    null
                } else {
                    getNextKey(response.headers()[KET_LINK_HEADER])
                }
                LoadResult.Page(
                    data = repos,
                    prevKey = null,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Error("Https code: ${response.code()}"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun getNextKey(relLink: String?): Int? {
        val nextUrl = getNextRelUrl(relLink)
        return UrlUtil.getQueryParameterValue(nextUrl, PARAMETER_TYPE_PAGE)?.toInt()
    }

    private fun getNextRelUrl(link: String?): String {
        link ?: return ""

        val rels = link.split(",")
        for (rel in rels) {
            val relData = rel.split(";")

            var type = ""
            var url = ""
            if (relData[1].split("=").size > 1) {
                type = relData[1].split("=")[1]
                url = relData[0].trim()
            }

            if (type == REL_TYPE_NEXT) {
                return url.substring(1, url.length - 1)
            }
        }

        return ""
    }
}