package com.devchallenges.model;

public class PostStats {

    private Integer shares;
    private ReactionCounts reactionCounts;
    private Integer commentCount;

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public ReactionCounts getReactionCounts() {
        return reactionCounts;
    }

    public void setReactionCounts(ReactionCounts reactionCounts) {
        this.reactionCounts = reactionCounts;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

}
