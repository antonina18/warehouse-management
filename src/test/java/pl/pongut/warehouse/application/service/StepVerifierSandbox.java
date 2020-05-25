package pl.pongut.warehouse.application.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class StepVerifierSandbox {

    @Test
    public void StepvVerifierSandbox() {
        //given
        //when
        Flux<String> flux = Flux.just("a", "b");

        //then
        StepVerifier.create(flux)
                .expectNext("a")
                .expectNext("b")
                .expectNext()
                .expectComplete()
                .verify();
    }

    @Test
    public void StepvVerifierSandbox2() {
        //given
        //when
        Flux<String> flux = Flux.just("a", "b");

        //then
        StepVerifier.create(flux)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }


    @Test
    public void StepvVerifierSandbox3() {
        //given
        //when
        Flux<String> flux = Flux.just("a", "b", "c");
        //then
        StepVerifier.create(flux)
                .recordWith(ArrayList::new)
                .expectNextCount(3)
                .consumeRecordedWith(founds -> {
                    assertThat(founds.size(), equalTo(3));
                    assertThat(founds, hasItems("b", "a"));
                })
                .verifyComplete();
    }
}
