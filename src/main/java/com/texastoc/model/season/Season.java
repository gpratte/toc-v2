package com.texastoc.model.season;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Season {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    private LocalDate end;
    private int numGames;
    private int numGamesPlayed;
    private int buyInCollected;
    private int rebuyAddOnCollected;
    private int tocCollected;
    private int tocPerGame;
    private int kittyPerGame;
    private int quarterlyTocPerGame;
    private int quarterlyNumPayouts;
    private List<SeasonPlayer> players;
    private List<SeasonPayout> payouts;
    private List<QuarterlySeason> quarterlySeasons;

    public void addPlayer(SeasonPlayer player) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(player);
    }

    public void addPayout(SeasonPayout payout) {
        if (payouts == null) {
            payouts = new ArrayList<>();
        }
        payouts.add(payout);
    }

    public void addQuarterlySeason(QuarterlySeason quarterlySeason) {
        if (quarterlySeasons == null) {
            quarterlySeasons = new ArrayList<>();
        }
        quarterlySeasons.add(quarterlySeason);
    }

}
