package com.devchallenges.model;

public class ReactionCounts {

    private Integer angry = 0;
    private Integer awesome = 0;
    private Integer boring = 0;
    private Integer care = 0;
    private Integer crazy = 0;
    private Integer fakeNews = 0;
    private Integer haha = 0;
    private Integer lame = 0;
    private Integer legal = 0;
    private Integer like = 0;
    private Integer love = 0;
    private Integer meal = 0;
    private Integer sad = 0;
    private Integer scary = 0;
    private Integer wow = 0;

    public Integer getAngry() {
        return angry;
    }

    public void setAngry(Integer angry) {
        this.angry = angry;
    }

    public Integer getAwesome() {
        return awesome;
    }

    public void setAwesome(Integer awesome) {
        this.awesome = awesome;
    }

    public Integer getBoring() {
        return boring;
    }

    public void setBoring(Integer boring) {
        this.boring = boring;
    }

    public Integer getCare() {
        return care;
    }

    public void setCare(Integer care) {
        this.care = care;
    }

    public Integer getCrazy() {
        return crazy;
    }

    public void setCrazy(Integer crazy) {
        this.crazy = crazy;
    }

    public Integer getFakeNews() {
        return fakeNews;
    }

    public void setFakeNews(Integer fakeNews) {
        this.fakeNews = fakeNews;
    }

    public Integer getHaha() {
        return haha;
    }

    public void setHaha(Integer haha) {
        this.haha = haha;
    }

    public Integer getLame() {
        return lame;
    }

    public void setLame(Integer lame) {
        this.lame = lame;
    }

    public Integer getLegal() {
        return legal;
    }

    public void setLegal(Integer legal) {
        this.legal = legal;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getLove() {
        return love;
    }

    public void setLove(Integer love) {
        this.love = love;
    }

    public Integer getMeal() {
        return meal;
    }

    public void setMeal(Integer meal) {
        this.meal = meal;
    }

    public Integer getSad() {
        return sad;
    }

    public void setSad(Integer sad) {
        this.sad = sad;
    }

    public Integer getScary() {
        return scary;
    }

    public void setScary(Integer scary) {
        this.scary = scary;
    }

    public Integer getWow() {
        return wow;
    }

    public void setWow(Integer wow) {
        this.wow = wow;
    }

    public Integer countReaction(String reactionType){

        Integer count = 0;

        switch (reactionType) {
            case "ANGRY":
                count = angry;
                break;
            case "AWESOME":
                count = awesome;
                break;
            case "BORING":
                count = boring;
                break;
            case "CARE":
                count = care;
                break;
            case "CRAZY":
                count = crazy;
                break;
            case "FAKENEWS":
                count = fakeNews;
                break;
            case "HAHA":
                count =  haha;
                break;
            case "LAME":
                count =  lame;
                break;
            case "LEGAL":
                count =  legal;
                break;
            case "LIKE":
                count =  like;
                break;
            case "LOVE":
                count =  love;
                break;
            case "MEAL":
                count =  meal;
                break;
            case "SAD":
                count =  sad;
                break;
            case "SCARY":
                count =  scary;
                break;
            case "WOW":
                count =  wow;
                break;
        }

        if(count == null){
            count = 0;
        }

        return count;

    }

}
