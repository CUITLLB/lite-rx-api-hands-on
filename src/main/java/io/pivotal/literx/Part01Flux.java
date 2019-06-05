package io.pivotal.literx;

import java.time.Duration;
import java.util.ArrayList;

import reactor.core.publisher.Flux;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href=
 *      "https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux
 *      Javadoc</a>
 */
public class Part01Flux {

	// ========================================================================================

	// Return an empty Flux
	Flux<String> emptyFlux() {
		return Flux.<String>empty();
	}

	// ========================================================================================

	// Return a Flux that contains 2 values "foo" and "bar" without using an array
	// or a collection
	Flux<String> fooBarFluxFromValues() {
		return Flux.<String>just("foo", "bar");
	}

	// ========================================================================================

	// Create a Flux from a List that contains 2 values "foo" and "bar"
	Flux<String> fooBarFluxFromList() {
		return Flux.<String>fromIterable(new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				add("foo");
				add("bar");
			}
		});
	}

	// ========================================================================================

	// Create a Flux that emits an IllegalStateException
	Flux<String> errorFlux() {
		return Flux.<String>error(new IllegalStateException());
	}

	// ========================================================================================

	// Create a Flux that emits increasing values from 0 to 9 each 100ms
	@SuppressWarnings("static-access")
	Flux<Long> counter() {
		// interval方法要放在前面，放在后面还是产生无限流
		// return Flux.interval(Duration.ofMillis(100))
		// .fromStream(Stream.<Long>of(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L));

		return Flux.interval(Duration.ofMillis(100)).just(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
	}

}
