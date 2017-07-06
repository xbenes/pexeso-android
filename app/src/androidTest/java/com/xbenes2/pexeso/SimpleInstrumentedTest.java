package com.xbenes2.pexeso;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SimpleInstrumentedTest {
    @Test
    public void test_use_app_context() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.xbenes2.pexeso", appContext.getPackageName());
    }
}
