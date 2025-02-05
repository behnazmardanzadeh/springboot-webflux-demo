package com.algocoding.webflux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono(){
        /*
           As you see in reactor.core.publisher.Mono,
           Mono belongs to publisher.
           So my publisher is monoString
        */
        /*
        After running test, to make sure that it follows publisher subscriber in Reactive we add log()
        As you'll see in console the order of calling methods is
        onSubscribe(),
        request(unbounded),
        onNext(AlgoCoding),[prints data],
        onComplete()
        */
        Mono<?> monoString1 = Mono.just("AlgoCoding")
                .log();

        monoString1.subscribe(System.out::println);
        /*
        Now let's see if onError is called when exception happens.
        After adding Mono.error, the order of invoked methods are:
        onSubscribe()
        request()
        onError()
         */
        Mono<?> monoString = Mono.just("AlgoCoding")
                .then(Mono.error(new RuntimeException("Exception occurred.")))
                .log();
        /*
          Now I need to call subscribe() method from publisher.
          View image at doc/SpringReactive.jpg
          As you'll see subscriber is invoking subscribe() method in publisher.

          There are a lot of overloads of subscribe method.
          Now I just want to print the published event:
         */
        monoString.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux(){
        Flux<String> fluxString = Flux.just("Spring", "Spring Boot", "Hibernate", "Microservice").log();
        /*
        onNext is called 4 times.
         */
        fluxString.subscribe(System.out::println);

        Flux<String> fluxString1 = Flux.just("One", "Two", "Three", "Four")
                .concatWithValues("Five!!")
                .concatWith(Flux.error(new RuntimeException("Exception occurred in Flux.")))
                .log();
        /*
        onNext is called 5 times.
         */
        fluxString1.subscribe(System.out::println, e -> System.out.println(e.getMessage()));

        Flux<String> fluxString2 = Flux.just("One", "Two", "Three", "Four")
                .concatWith(Flux.error(new RuntimeException("Exception occurred in Flux.")))
                .log();
        /*
        onNext is called 4 times.
        error occurs
         */
        fluxString2.subscribe(System.out::println);

        Flux<String> fluxString3 = Flux.just("One", "Two", "Three", "Four")
                .concatWith(Flux.error(new RuntimeException("Exception occurred in Flux.")))
                .concatWithValues("AWS")
                .log();
        /*
        onNext is called 4 times.
        concatWithValues() won't add AWS because error is occurred
         */
        fluxString3.subscribe(System.out::println);
    }
}
