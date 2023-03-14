package com.github.donghune.domain.usecase.song

import com.github.donghune.domain.entity.Song
import com.github.donghune.domain.repo.KaraokeRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetLatestSongsUseCase @Inject constructor(
    private val karaokeRepository: KaraokeRepository
) {
    suspend operator fun invoke(): List<Song> {
        return karaokeRepository.getLatestList(
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"))
        )
    }
}
