package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part06Request {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

	// ========================================================================================

	// Create a StepVerifier that initially requests all values and expect 4 values
	// to be received
	StepVerifier requestAllExpectFour(Flux<User> flux) {
		// return
		// StepVerifier.create(flux).thenRequest(4).expectNextCount(4).expectComplete();
		return StepVerifier.create(flux).expectSubscription().expectNextCount(4).expectComplete();
	}

	// ========================================================================================

	// Create a StepVerifier that initially requests 1 value and expects
	// User.SKYLER then requests another value and expects User.JESSE.
	StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
		// User.SKYLER, User.JESSE, User.WALTER, User.SAUL
		return StepVerifier.create(flux).thenRequest(1).expectNext(User.SKYLER).thenRequest(1).expectNext(User.JESSE)
				.thenRequest(1).expectNext(User.WALTER).thenRequest(1).expectNext(User.SAUL).expectComplete();
		// return
		// StepVerifier.create(flux).thenRequest(1).expectNext(User.SKYLER).thenRequest(1).expectNext(User.JESSE)
		// .thenCancel();

		// return
		// StepVerifier.create(flux).expectNext(User.SKYLER).expectNext(User.JESSE).expectNext(User.WALTER)
		// .expectNext(User.SAUL).expectComplete();
	}

	// ========================================================================================

	// Return a Flux with all users stored in the repository that prints
	// automatically logs for all Reactive Streams signals
	Flux<User> fluxWithLog() {
		// log()方法可以寫在任何位置，日誌都能輸出，而且日誌一樣
		// return repository.findAll().takeWhile(u -> true).log().takeWhile(u ->
		// true).takeWhile(u -> true)
		// .takeWhile(u -> true);

		// return repository.findAll().takeWhile(u -> true).takeWhile(u ->
		// true).takeWhile(u -> true)
		// .takeWhile(u -> true).log();

		return repository.findAll().takeWhile(u -> true).takeWhile(u -> true).takeWhile(u -> true).log()
				.takeWhile(u -> true);

	}

	// ========================================================================================

	// Return a Flux with all users stored in the repository that prints
	// "Starring:" on subscribe, "firstname lastname" for all values and "The end!"
	// on complete
	Flux<User> fluxWithDoOnPrintln() {
		return repository.findAll().doOnSubscribe(x -> System.out.println("Starring: ")).doOnNext(x -> {
			System.out.println(x.getFirstname() + "-" + x.getLastname());
		}).doFinally(x -> System.out.println("The End"));
	}

	// return repository.findAll().doOnSubscribe(x -> System.out.println("Starring:
	// ")).doOnEach(x -> {
	// if (x.hasValue()) {
	// System.out.println(x.get().getFirstname() + "-" + x.get().getLastname());
	// }
	// }).doFinally(x -> System.out.println("The End"));
}
