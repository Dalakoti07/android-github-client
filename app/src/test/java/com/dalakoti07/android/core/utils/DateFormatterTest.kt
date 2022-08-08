package com.dalakoti07.android.core.utils

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DateFormatterTest {

    private lateinit var dateFormatter: DateFormatter

    @Before
    fun before() {
        dateFormatter = DateFormatter()
    }

    @Test
    fun `correct date assertion for Jan`() {
        val result = dateFormatter.formatDate("2021-01-20T17:39:14Z")
        Assert.assertEquals("20 Jan 2021, 17:39:14", result)
    }

    @Test
    fun `correct date assertion for Feb`() {
        val result = dateFormatter.formatDate("2021-02-20T17:39:14Z")
        Assert.assertEquals("20 Feb 2021, 17:39:14", result)
    }

    /**
        we can write for all months that would
        increase the coverage for [DateFormatter] class
     */
}