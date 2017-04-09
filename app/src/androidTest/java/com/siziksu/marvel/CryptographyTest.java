package com.siziksu.marvel;

import android.support.test.runner.AndroidJUnit4;

import com.siziksu.marvel.common.Cryptography;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CryptographyTest {

    @Test
    public void hashEncryptionIsCorrect() throws Exception {
        long ts = 12345678;
        String string = ts + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY;
        String hash = Cryptography.md5(string);
        assertEquals("8a277aaa7cac06ba2233a9f7f6073c72", hash);
    }
}
