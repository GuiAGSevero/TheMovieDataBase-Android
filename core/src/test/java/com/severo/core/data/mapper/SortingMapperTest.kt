package com.severo.core.data.mapper

import com.severo.core.data.StorageConstants
import com.severo.core.domain.model.SortingType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortingMapperTest {

    private lateinit var mapper: SortingMapper

    @Before
    fun setUp() {
        mapper = SortingMapper()
    }

    @Test
    fun `should map ORDER_BY_NAME_ASCENDING to pair correctly`() {
        val result = mapper.mapToPair(StorageConstants.ORDER_BY_NAME_ASCENDING)
        assertEquals(SortingType.ORDER_BY_NAME.value to SortingType.ORDER_ASCENDING.value, result)
    }

    @Test
    fun `should map ORDER_BY_NAME_DESCENDING to pair correctly`() {
        val result = mapper.mapToPair(StorageConstants.ORDER_BY_NAME_DESCENDING)
        assertEquals(SortingType.ORDER_BY_NAME.value to SortingType.ORDER_DESCENDING.value, result)
    }

    @Test
    fun `should map ORDER_BY_MODIFIED_ASCENDING to pair correctly`() {
        val result = mapper.mapToPair(StorageConstants.ORDER_BY_MODIFIED_ASCENDING)
        assertEquals(SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_ASCENDING.value, result)
    }

    @Test
    fun `should map ORDER_BY_MODIFIED_DESCENDING to pair correctly`() {
        val result = mapper.mapToPair(StorageConstants.ORDER_BY_MODIFIED_DESCENDING)
        assertEquals(SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_DESCENDING.value, result)
    }

    @Test
    fun `should return default pair when mapToPair receives invalid input`() {
        val result = mapper.mapToPair("invalid_sorting")
        assertEquals(SortingType.ORDER_BY_NAME.value to SortingType.ORDER_ASCENDING.value, result)
    }

    @Test
    fun `should map pair name ascending to StorageConstants correctly`() {
        val result = mapper.mapFromPair(SortingType.ORDER_BY_NAME.value to SortingType.ORDER_ASCENDING.value)
        assertEquals(StorageConstants.ORDER_BY_NAME_ASCENDING, result)
    }

    @Test
    fun `should map pair name descending to StorageConstants correctly`() {
        val result = mapper.mapFromPair(SortingType.ORDER_BY_NAME.value to SortingType.ORDER_DESCENDING.value)
        assertEquals(StorageConstants.ORDER_BY_NAME_DESCENDING, result)
    }

    @Test
    fun `should map pair modified ascending to StorageConstants correctly`() {
        val result = mapper.mapFromPair(SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_ASCENDING.value)
        assertEquals(StorageConstants.ORDER_BY_MODIFIED_ASCENDING, result)
    }

    @Test
    fun `should map pair modified descending to StorageConstants correctly`() {
        val result = mapper.mapFromPair(SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_DESCENDING.value)
        assertEquals(StorageConstants.ORDER_BY_MODIFIED_DESCENDING, result)
    }

    @Test
    fun `should return default StorageConstants when mapFromPair receives invalid orderBy`() {
        val result = mapper.mapFromPair("invalid_orderBy" to SortingType.ORDER_ASCENDING.value)
        assertEquals(StorageConstants.ORDER_BY_NAME_ASCENDING, result)
    }

    @Test
    fun `should return default StorageConstants when mapFromPair receives invalid order`() {
        val result = mapper.mapFromPair(SortingType.ORDER_BY_NAME.value to "invalid_order")
        assertEquals(StorageConstants.ORDER_BY_NAME_ASCENDING, result)
    }

    @Test
    fun `should return default StorageConstants when mapFromPair receives completely invalid pair`() {
        val result = mapper.mapFromPair("invalid_orderBy" to "invalid_order")
        assertEquals(StorageConstants.ORDER_BY_NAME_ASCENDING, result)
    }
}
