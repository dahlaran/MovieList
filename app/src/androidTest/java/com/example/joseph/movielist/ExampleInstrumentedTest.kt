package com.example.joseph.movielist

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.joseph.movielist", appContext.packageName)
    }
    @Test
    fun uselessTest() {
        // Useless test
        assertEquals(1,1)
    }

    @Test
    fun otherUselessTest() {
        // Useless test
        assertEquals(true,true)
    }
}
