package com.siziksu.marvel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiKeysUnitTest {

    @Test
    public void apiPublicKey_isCorrect() throws Exception {
        String apiKey = BuildConfig.API_PUBLIC_KEY;
        assertEquals("527f7da15e6c2b72e526061271eb7617", apiKey);
    }

    @Test
    public void apiPrivateKey_isCorrect() throws Exception {
        String apiKey = BuildConfig.API_PRIVATE_KEY;
        assertEquals("d6bdd8caa9e088ab47f9e848772572a294c4154b", apiKey);
    }
}