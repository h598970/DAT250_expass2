package no.hvl.dat250.backend.service;

import no.hvl.dat250.backend.model.Poll;
import no.hvl.dat250.backend.model.User;
import no.hvl.dat250.backend.model.VoteOption;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class PollManager {
    HashMap<String, Poll> polls = new HashMap<>();
    HashMap<String, User> users = new HashMap<>();

    User fredrik = new User("Fredrik","fredrik@fredrik.com", "heihei");
    User andreas = new User("Andras","andreas@andreas.com", "heiheihei" );




    Poll glassespoll = new Poll("Are glasses cool?",Instant.now(), Instant.now().plus(48, ChronoUnit.HOURS), new ArrayList<VoteOption>(){{
        add(new VoteOption("YES",1 ));
        add(new VoteOption("NO",2 ));
    }}, andreas);

    Poll bikesPoll = new Poll("Are bikes cool?",Instant.now(), Instant.now().plus(48, ChronoUnit.HOURS), new ArrayList<VoteOption>(){{
        add(new VoteOption("YES",1 ));
        add(new VoteOption("NO",2 ));
    }}, fredrik);

    public PollManager() {
        polls.put(glassespoll.getId(), glassespoll );
        polls.put(bikesPoll.getId(), bikesPoll );
        users.put(fredrik.getId(),fredrik);
        users.put(andreas.getId(), andreas);
        andreas.addPoll(glassespoll);
        fredrik.addPoll(bikesPoll);

    }

    public HashMap<String, Poll> getPolls() {
        return polls;
    }

    public void setPolls(HashMap<String, Poll> polls) {
        this.polls = polls;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }
    public Poll addPoll(Poll poll) {
        polls.put(poll.getId(), poll);
        return poll;
    }
    public Poll getPoll(String id) {
        return polls.get(id);
    }
    public User getUser(String id) {
        return users.get(id);
    }
}