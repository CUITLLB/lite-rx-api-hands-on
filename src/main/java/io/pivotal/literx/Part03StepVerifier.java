/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.literx;

import java.time.Duration;
import java.util.function.Supplier;

import io.pivotal.literx.domain.User;
import reactor.test.StepVerifier;
import reactor.core.publisher.Flux;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of
 * Reactive Streams Publisher.
 *
 * @author Sebastien Deleuze
 * @see <a href=
 *      "https://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier
 *      Javadoc</a>
 */
public class Part03StepVerifier {

	// ========================================================================================

	// Use StepVerifier to check that the flux parameter emits "foo" and "bar"
	// elements then completes successfully.
	void expectFooBarComplete(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo", "bar").verifyComplete();
	}

	// ========================================================================================

	// Use StepVerifier to check that the flux parameter emits "foo" and "bar"
	// elements then a RuntimeException error.
	void expectFooBarError(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo").expectNext("bar").verifyError(RuntimeException.class);
	}

	// ========================================================================================

	// Use StepVerifier to check that the flux parameter emits a User with
	// "swhite"username
	// and another one with "jpinkman" then completes successfully.
	void expectSkylerJesseComplete(Flux<User> flux) {
		StepVerifier.create(flux).expectNext(new User("swhite", "", "")).expectNext(new User("jpinkman", "", ""))
				.verifyComplete();
	}

	// ========================================================================================

	// Expect 10 elements then complete and notice how long the test takes.
	void expect10Elements(Flux<Long> flux) {
		StepVerifier.create(flux).expectNext(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L).thenAwait(Duration.ofSeconds(1))
				.verifyComplete();
	}

	// ========================================================================================

	// Expect 3600 elements at intervals of 1 second, and verify quicker than
	// 3600s
	// by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice
	// how long the test takes
	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		StepVerifier.withVirtualTime(supplier).expectNextCount(3600).thenAwait(Duration.ofSeconds(1)).verifyComplete();
	}

	@SuppressWarnings("unused")
	private void fail() {
		throw new AssertionError("workshop not implemented");
	}

}
