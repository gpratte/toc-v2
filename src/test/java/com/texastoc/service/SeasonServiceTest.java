package com.texastoc.service;

import com.texastoc.model.season.QuarterlySeason;
import com.texastoc.model.season.Season;
import com.texastoc.repository.QuarterlySeasonRepository;
import com.texastoc.repository.SeasonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.notNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeasonServiceTest {

    private SeasonService service;

    @MockBean
    private SeasonRepository seasonRepository;

    @MockBean
    private QuarterlySeasonRepository qSeasonRepository;

    @Before
    public void before() {
        service = new SeasonService(seasonRepository, qSeasonRepository);
    }

    @Test
    public void createSeason() {

        // Arrange
        Season expected = Season.builder()
            .start(LocalDate.now())
            .kittyPerGame(10)
            .tocPerGame(10)
            .quarterlyTocPerGame(10)
            .quarterlyNumPayouts(3)
            .build();

        // End date should the day before the start date next year
        LocalDate expectedEnd = LocalDate.now().plusYears(1).minusDays(1);

        Mockito.when(seasonRepository.save( (Season)notNull() )).thenReturn(1);
        Mockito.when(qSeasonRepository.save( (QuarterlySeason)notNull() )).thenReturn(1);

        // Act
        Season actual = service.createSeason(expected);


        // Assert
        Assert.assertTrue(actual.getId() > 0);
        Assert.assertEquals(expected.getKittyPerGame(), actual.getKittyPerGame());
        Assert.assertEquals(expected.getTocPerGame(), actual.getTocPerGame());
        Assert.assertEquals(expected.getQuarterlyTocPerGame(), actual.getQuarterlyTocPerGame());
        Assert.assertEquals(expected.getQuarterlyNumPayouts(), actual.getQuarterlyNumPayouts());

        Assert.assertTrue(actual.getBuyInCollected() == 0);
        Assert.assertTrue(actual.getRebuyAddOnCollected() == 0);
        Assert.assertTrue(actual.getTocCollected() == 0);
        Assert.assertTrue(actual.getNumGamesPlayed() == 0);

        Assert.assertEquals(expected.getStart(), actual.getStart());
        Assert.assertEquals(expectedEnd, actual.getEnd());

        Assert.assertTrue(actual.getNumGames() == 52 || actual.getNumGames() == 53);

        Assert.assertTrue(actual.getPlayers() == null || actual.getPlayers().size() == 0);
        Assert.assertTrue(actual.getPayouts() == null || actual.getPayouts().size() == 0);

        Assert.assertEquals(4, actual.getQuarterlySeasons().size());

        for (int i = 0; i < 4; ++i) {
            QuarterlySeason qSeason = actual.getQuarterlySeasons().get(i);
            Assert.assertTrue(qSeason.getId() > 0);
            Assert.assertEquals((int)i + 1, (int)qSeason.getQuarter());

            Assert.assertEquals((int)expected.getQuarterlyTocPerGame(), (int)qSeason.getTocPerGame());
            Assert.assertEquals((int)expected.getQuarterlyNumPayouts(), (int)qSeason.getNumPayouts());

            Assert.assertTrue(qSeason.getTocCollected() == 0);

            LocalDate qSeasonExpectedEnd = LocalDate.now().plusWeeks(13 * (i + 1)).minusDays(1);

            Assert.assertEquals(expected.getStart().plusWeeks(13 * (i)), qSeason.getStart());
            Assert.assertEquals(qSeasonExpectedEnd, qSeason.getEnd());

            Assert.assertTrue(qSeason.getNumGamesPlayed() == 0);
            Assert.assertTrue(qSeason.getNumGames() == 13 || qSeason.getNumGames() == 14);

            Assert.assertTrue(qSeason.getPlayers() == null || qSeason.getPlayers().size() == 0);
            Assert.assertTrue(qSeason.getPayouts() == null || qSeason.getPayouts().size() == 0);
        }
    }


}
