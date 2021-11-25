package org.example.reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Publisher that publishes elements from given array.
 */
public final class ArrayPublisher implements Publisher<String> {

    /**
     * Source.
     */
    private final String[] source;

    /**
     * Ctor.
     *
     * @param source Source
     */
    public ArrayPublisher(final String[] source) {
        this.source = source;
    }

    @Override
    public void subscribe(final Subscriber<? super String> subscriber) {
        subscriber.onSubscribe(new ArrayPublisher.ArraySubscription(this.source, subscriber));
    }

    public static class ArraySubscription implements Subscription {

        private final String[] source;

        private final Subscriber<? super String> subscriber;

        private int index = 0;

        public ArraySubscription(
            final String[] source,
            final Subscriber<? super String> subscriber
        ) {
            this.source = source;
            this.subscriber = subscriber;
        }

        @Override
        public void request(final long n) {
            int step = 0;
            while (step < n && this.index < this.source.length) {
                this.subscriber.onNext(this.source[this.index]);
                this.index++;
                step++;
            }
            if (this.index == this.source.length) {
                this.subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            throw new UnsupportedOperationException("#cancel()");
        }
    }
}
