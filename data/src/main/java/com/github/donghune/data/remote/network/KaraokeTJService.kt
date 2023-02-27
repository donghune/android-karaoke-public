package com.github.donghune.data.remote.network

import com.github.donghune.data.remote.response.PopularitySongResponse
import com.github.donghune.data.remote.response.SongResponse
import com.github.donghune.domain.entity.SearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import javax.inject.Inject

class KaraokeTJService @Inject constructor() : KaraokeService {

    override suspend fun getSongList(
        search: String,
        type: SearchType,
        page: Int,
        count: Int
    ): List<SongResponse> {
        return listOf()
    }

    override suspend fun getPopularSongList(): List<PopularitySongResponse> {
        return withContext(Dispatchers.IO) {
            getItemList("http://m.tjmedia.co.kr/tjsong/song_popular.asp")
        }.map {
            PopularitySongResponse(it[0].toInt(), it[1].toInt(), it[2], it[3])
        }
    }

    override suspend fun getNewSongList(): List<SongResponse> {
        return withContext(Dispatchers.IO) {
            getItemList("http://m.tjmedia.co.kr/tjsong/song_monthNew.asp")
        }.map {
            SongResponse(it[0].toInt(), it[1], it[2])
        }
    }

    private fun getItemList(url: String): List<List<String>> {
        return Jsoup.connect(url).get().getElementsByTag("tr")
            .asSequence()
            .map { tr: Element -> tr.getElementsByTag("td") }
            .map { tds: Elements ->
                tds.map { td ->
                    if (td.getElementsByClass("mr_icon").size != 0) {
                        "[MR] ${td.text()}"
                    }
                    td.text()
                }.toList()
            }
            .filter { it.size >= 3 }
            .toList()
    }
}
