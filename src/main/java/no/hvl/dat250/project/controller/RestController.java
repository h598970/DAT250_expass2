package no.hvl.dat250.project.controller;


import no.hvl.dat250.project.model.Poll;
import no.hvl.dat250.project.model.User;
import no.hvl.dat250.project.model.Vote;
import no.hvl.dat250.project.service.PollManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final PollManager pollManager;

    public RestController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @PostMapping("/user")
    User newUser(@RequestBody User user) {
        return pollManager.addUser(user);
    }

    @GetMapping("/users")
    List<User> getUsers() {
        return pollManager.getUsers().values().stream().toList();
    }

    @PostMapping("/poll")
    Poll newPoll(@RequestBody Poll poll) {
        pollManager.getUser(poll.getCreator().getUsername()).getPolls().add(poll);
        return pollManager.addPoll(poll);
    }

    @GetMapping("/polls")
    List<Poll> getPolls() {
        return pollManager.getPolls().values().stream().toList();
    }

    @PostMapping("/vote")
    Vote newVote(@RequestBody Vote vote) {
        if(pollManager.getUser(vote.getVoter().getUsername()).getVotes().stream().anyMatch( x -> x.getPoll() == vote.getPoll())){
            pollManager.getUser(vote.getVoter().getUsername()).getVotes().remove(vote);
        }
        pollManager.getUser(vote.getVoter().getUsername()).getVotes().add(vote);
        return vote;
    }


    @PostMapping("/votes")
    List<Vote> getVotes(@RequestBody User user) {
        return pollManager.getUser(user.getUsername()).getVotes();
    }

    @PostMapping("/DelPoll")
    void delPoll(@RequestBody Poll poll) {
        pollManager.getPolls().remove(poll.getQuestions());
        pollManager.getUser(poll.getCreator().getUsername()).getPolls().remove(poll);
    }
}
