package no.hvl.dat250.project.service;

import no.hvl.dat250.project.model.Poll;
import no.hvl.dat250.project.model.User;
import no.hvl.dat250.project.model.VoteOption;
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
        polls.put("briller", glassespoll );
        polls.put("sykkel", bikesPoll );
        users.put("Fredrik",fredrik);
        users.put("Andreas", andreas);
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
        users.put(user.getUsername(), user);
        return user;
    }
    public Poll addPoll(Poll poll) {
        polls.put(poll.getQuestions(), poll);
        return poll;
    }
    public Poll getPoll(String questions) {
        return polls.get(questions);
    }
    public User getUser(String username) {
        return users.get(username);
    }
}
