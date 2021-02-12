package com.devchallenges.model;

public class Paging {

    private String currentUserId;
    private Integer startingRow;
    private Integer numberOfRows;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Integer getStartingRow() {
        return startingRow;
    }

    public void setStartingRow(Integer startingRow) {
        this.startingRow = startingRow;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
