package no.hvl.dat250.project.model;

import java.time.Instant;

public class Vote {
    private Instant publishedAt;
    private VoteOption voteOption;
    private Poll poll;
    private User voter;

    public Vote(Instant publishedAt, VoteOption voteOption, Poll poll, User voter) {
        this.publishedAt = publishedAt;
        this.voteOption = voteOption;
        this.poll = poll;
        this.voter = voter;
    }

    public Vote() {
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
