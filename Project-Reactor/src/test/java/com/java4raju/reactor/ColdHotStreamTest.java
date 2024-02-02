package com.java4raju.reactor;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ColdHotStreamTest {

	/**
	 * Description: Cold publisher do not publish from beginning
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void coldStream() throws InterruptedException {

		Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
				.map(String::toUpperCase);

		source.subscribe(d -> System.out.println("Subscriber 1: " + d));
		source.subscribe(d -> System.out.println("Subscriber 2: " + d));
	
	}

	/**
	 * Description: Hot publisher do not publish from beginning
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void hotStream() throws InterruptedException {

		Flux<Integer> flux = Flux.range(1, 10);

		ConnectableFlux<Integer> cFlux = flux.publish();
		cFlux.connect();

		cFlux.subscribe(System.out::print);
		Thread.sleep(Duration.ofSeconds(2).toMillis());
		// System.out.println();
		cFlux.subscribe(System.out::print);

	}

}
