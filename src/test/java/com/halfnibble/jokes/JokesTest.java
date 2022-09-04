package com.halfnibble.jokes;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class JokesTest {
    @Test
    public void testGetJoke() {
        String joke = Jokes.getJoke();
        assertNotEquals(joke, Jokes.errorMessage);
        assert joke.length() > 0;
    }
}
