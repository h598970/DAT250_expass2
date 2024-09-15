package no.hvl.dat250.backend.model;

import com.fasterxml.jackson.annotation.*;

import java.time.Instant;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Vote {
    private String id;
    private Instant publishedAt;
    private VoteOption voteOption;

    //    @JsonBackReference(value = "poll-vote")
    @JsonIdentityReference(alwaysAsId = true)
    private Poll poll;

//    @JsonBackReference(value = "user-vote")
    @JsonIdentityReference(alwaysAsId = true)
    private User voter;

//    @JsonProperty(value = "voter")
//    public void setVoter(String voterId) {
//        this.voter = new User(voterId);
//    }
//
//
//    @JsonProperty(value = "poll")
//    public void setPoll(String pollId) {
//        this.poll = new Poll(pollId);
//    }



    public Vote(Instant publishedAt, VoteOption voteOption, Poll poll, User voter) {
        this.id = UUID.randomUUID().toString();
        this.publishedAt = publishedAt;
        this.voteOption = voteOption;
        this.poll = poll;
        this.voter = voter;
    }

    public Vote(VoteOption voteOption, Poll poll, User voter) {
        this.id = UUID.randomUUID().toString();
        this.publishedAt = Instant.now();
        this.voteOption = voteOption;
        this.poll = poll;
        this.voter = voter;
    }

    public Vote() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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
