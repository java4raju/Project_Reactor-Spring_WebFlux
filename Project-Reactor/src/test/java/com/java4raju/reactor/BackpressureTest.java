package com.java4raju.reactor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class BackpressureTest {
	
	
	@Test
	public void testSubscription() {
		
		Flux<Integer> flux = Flux.range(1, 100).log();
		
		flux.subscribe(num -> {
			log.info("Number is {}: ", num);
			
		});
	}
	
	@Test
	public void testBackPressure() {
		Flux<Integer> flux = Flux.range(1, 100).log();
		
		flux.subscribe(new BaseSubscriber<Integer>() {
			
			@Override
			protected void hookOnSubscribe(Subscription subscription) {
				super.hookOnSubscribe(subscription);
			}
			
			@Override
			protected void hookOnNext(Integer value) {
				log.info("hookOnNext: ", value);
				//if(value==3)
					//cancel();
			}
			
			@Override
			protected void hookOnComplete() {
				super.hookOnComplete();
			}
			
			
			@Override
			protected void hookOnCancel() {
				log.info("Calceled event");
			}
			
			@Override
			protected void hookOnError(Throwable throwable) {
				super.hookOnError(throwable);
			}
			
		});
		
		
	}
	
}
