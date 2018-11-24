package com.texastoc.service;

import com.texastoc.model.season.QuarterlySeason;
import com.texastoc.model.season.Season;
import com.texastoc.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public Season createSeason(Season season) {

        // TODO make sure not overlapping with another season
        LocalDate start = season.getStart();

        // TODO make sure not overlapping with another season
        // The end will be the day before the start date next year
        LocalDate end = season.getStart().plusYears(1).minusDays(1);

        // Count the number of Thursdays between the start and end
        int numThursdays = 0;
        LocalDate currentWeek = start;
        while (currentWeek.isBefore(end)) {
            ++numThursdays;
            currentWeek = currentWeek.plusDays(7);
        }

        List<QuarterlySeason> qSeasons = new ArrayList<>();
        for (int i = 1; i <=4; ++i) {
            LocalDate qStart = season.getStart().plusWeeks(13 * (i-1));

            LocalDate qEnd = season.getStart().plusWeeks(13 * i).minusDays(1);

            // Count the number of Thursdays between the start and end
            int qNumThursdays = 0;
            currentWeek = qStart;
            while (currentWeek.isBefore(qEnd)) {
                ++qNumThursdays;
                currentWeek = currentWeek.plusDays(7);
            }

            QuarterlySeason qSeason = QuarterlySeason.builder()
                .quarter(i)
                .start(qStart)
                .end(qEnd)
                .numGames(qNumThursdays)
                .tocPerGame(season.getQuarterlyTocPerGame())
                .numPayouts(season.getQuarterlyNumPayouts())
                .build();
            qSeasons.add(qSeason);

        }

        Season newSeason = Season.builder()
            .start(start)
            .end(end)
            .numGames(0)
            .numGamesPlayed(0)
            .buyInCollected(0)
            .rebuyAddOnCollected(0)
            .tocCollected(0)
            .tocPerGame(season.getTocPerGame())
            .kittyPerGame(season.getKittyPerGame())
            .quarterlyTocPerGame(season.getQuarterlyTocPerGame())
            .quarterlyNumPayouts(season.getQuarterlyNumPayouts())
            .numGames(numThursdays)
            .quarterlySeasons(qSeasons)
            .build();

        int seasonId = seasonRepository.save(newSeason);
        newSeason.setId(seasonId);
        return newSeason;
    }
}
