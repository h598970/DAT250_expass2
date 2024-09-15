package no.hvl.dat250.backend;

import no.hvl.dat250.backend.model.Poll;
import no.hvl.dat250.backend.model.User;
import no.hvl.dat250.backend.model.Vote;
import no.hvl.dat250.backend.model.VoteOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProjectApplicationTests {

	@LocalServerPort
	private int port;

	private WebClient client;



	@BeforeEach
	public void setUp() {
		client = WebClient.create("http://localhost:" + port);
	}


	@Test
	void createAndListUsers() {
		User user1 = new User();
		user1.setUsername("test1");
		user1.setEmail("test1@gmail.com");
		user1.setPassword_hash("Hei");

		var responseEntity = client.post()
				.uri("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(user1), User.class)
				.retrieve()
				.toBodilessEntity()
				.block();

        assert responseEntity != null;
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();

		List<User> users = client.get()
				.uri("/users")
				.retrieve()
				.bodyToFlux(User.class)
				.collectList()
				.block();

		assertThat(users).isNotEmpty();
		assertThat(users.stream().anyMatch(u -> u.getUsername().equals("test1"))).isTrue();
	}

	@Test
	void createSecondUserAndVerify() {
		User user2 = new User();
		user2.setUsername("test2");
		user2.setEmail("test2@gmail.com");
		user2.setPassword_hash("HeiHei");

		client.post()
				.uri("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(user2), User.class)
				.retrieve()
				.toBodilessEntity()
				.block();

		List<User> users = client.get()
				.uri("/users")
				.retrieve()
				.bodyToFlux(User.class)
				.collectList()
				.block();

		assertThat(users).isNotNull();
		assertThat(users.stream().anyMatch(u -> u.getUsername().equals("test2"))).isTrue();
	}


	@Test
	void userCreatesPollAndListPolls() {
		List<User> users = client.get()
				.uri("/users")
				.retrieve()
				.bodyToFlux(User.class)
				.collectList()
				.block();

		User user1;
        assert users != null;
        if (users.stream().noneMatch(u -> u.getUsername().equals("test1"))) {
			user1 = new User();
			user1.setUsername("test1");
			user1.setEmail("test1@gmail.com");
			user1.setPassword_hash("Hei");

			client.post()
					.uri("/user")
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(user1), User.class)
					.retrieve()
					.toBodilessEntity()
					.block();
		} else user1 = users.stream().filter(u -> u.getUsername().equals("test1")).findFirst().orElse(null);

		Poll poll = new Poll();
		poll.setQuestion("Is HVL greater than UiB?");
		poll.setCreator(user1);
		poll.setPublishedAt(Instant.now()); // Set a publish date
		poll.setValidUntil(Instant.now().plus(2, ChronoUnit.DAYS));
		poll.setOptions(List.of(new VoteOption("Yes", 1), new VoteOption("No", 2)));

		client.post()
				.uri("/poll")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(poll), Poll.class)
				.retrieve()
				.toBodilessEntity()
				.block();

		List<Poll> polls = client.get()
				.uri("/polls")
				.retrieve()
				.bodyToFlux(Poll.class)
				.collectList()
				.block();

		assertThat(polls).isNotEmpty();
		assertThat(polls.stream().anyMatch(p -> p.getQuestion().equals("Is HVL greater than UiB?"))).isTrue();


	}



	@Test
	void user2VotesAndChangesVote() {
		User user2;
		List<User> users = client.get()
				.uri("/users")
				.retrieve()
				.bodyToFlux(User.class)
				.collectList()
				.block();

        assert users != null;
        if (users.stream().noneMatch(u -> u.getUsername().equals("test2"))) {
			user2 = new User();
			user2.setUsername("test2");
			user2.setEmail("test2@gmail.com");
			user2.setPassword_hash("HeiHei");

			client.post()
					.uri("/user")
					.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(user2), User.class)
					.retrieve()
					.toBodilessEntity()
					.block();
		} else user2 = users.stream().filter(x -> x.getUsername().equals("test2")).findFirst().orElse(null);


		VoteOption voteOption0 = new VoteOption();
		voteOption0.setCaption("YES");
		voteOption0.setPresentationOrder(1);

		VoteOption voteOption1 = new VoteOption();
		voteOption1.setCaption("NO");
		voteOption1.setPresentationOrder(2);
		ArrayList<VoteOption> voteOptions = new ArrayList<>();
		voteOptions.add(voteOption0);
		voteOptions.add(voteOption1);


		Poll poll = new Poll();
		poll.setQuestion("Is Java better than Python?");
		poll.setCreator(user2);
		poll.setOptions(voteOptions);

		client.post()
				.uri("/poll")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(poll), Poll.class)
				.retrieve()
				.toBodilessEntity()
				.block();

		Vote vote = new Vote();
		vote.setPoll(poll);
		vote.setVoteOption(voteOption0);
		vote.setVoter(user2);

		client.post()
				.uri("/vote")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(vote), Vote.class)
				.retrieve()
				.toBodilessEntity()
				.block();

		vote.setVoteOption(new VoteOption("NO", 2));

		client.post()
				.uri("/vote")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(vote), Vote.class)
				.retrieve()
				.toBodilessEntity()
				.block();

		List<Vote> votes = client.post()
				.uri("/votes")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(user2), User.class)
				.retrieve()
				.bodyToFlux(Vote.class)
				.collectList()
				.block();

        assert votes != null;
        assertThat(votes.stream().anyMatch(v -> v.getVoteOption().getCaption().equals("NO"))).isTrue();

		client.post()
				.uri("/DelPoll")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(poll), Poll.class)
				.retrieve()
				.toBodilessEntity()
				.block();

		 votes = client.post()
				.uri("/votes")
				 .contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(user2),User.class)
				.retrieve()
				.bodyToFlux(Vote.class)
				.collectList()
				.block();

		assertThat(votes).doesNotContain(vote);
	}
}
