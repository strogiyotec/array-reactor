package org.example.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author Almas Abdrazak (almas337519@gmail.com)
 */
final class ArrayPublisherTest {

    @Test
    void firstTest() {
        var publisher = new ArrayPublisher(new String[]{"first", "second", "third"});
        //a reference that holds a subscription
        final AtomicReference<Subscription> subscription = new AtomicReference<>();
        //a storage of elements
        final List<String> elements = new ArrayList<>(3);
        publisher.subscribe(
            new Subscriber<>() {
                @Override
                public void onSubscribe(final Subscription sub) {
                    subscription.set(sub);
                }

                @Override
                public void onNext(final String s) {
                    elements.add(s);
                }

                @Override
                public void onError(final Throwable throwable) {
                    throw new UnsupportedOperationException("#onError()");
                }

                @Override
                public void onComplete() {
                }
            });
        //request all elements
        subscription.get().request(3L);
        Assertions.assertEquals(3, elements.size());
    }

}
