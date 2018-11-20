Feature: CRUD seasons
  Create, Retrieve, Update and Delete seasons

  Scenario: create a season
    Given season starts now
    When the season is created
    Then the start date should be now

