package org.sparkystudio.krar.archive

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests for KRar file format structures
 *
 * @author Андрій Будильников
 */
class KrarFormatTest {
    
    @Test
    fun testKrarHeaderDefaultValues() {
        val header = KrarHeader()
        assertEquals(0x5241524Bu, header.signature.toLong())
        assertEquals(1u, header.version)
        assertEquals(0u, header.fileCount)
    }
    
    @Test
    fun testKrarFileEntryCreation() {
        val entry = KrarFileEntry(
            fileNameLength = 10u,
            fileName = "test.txt",
            originalSize = 1024u,
            compressedSize = 512u,
            crc32 = 0x12345678u,
            dataOffset = 100u
        )
        
        assertEquals(10u, entry.fileNameLength)
        assertEquals("test.txt", entry.fileName)
        assertEquals(1024u, entry.originalSize)
        assertEquals(512u, entry.compressedSize)
        assertEquals(0x12345678u, entry.crc32)
        assertEquals(100u, entry.dataOffset)
    }
    
    @Test
    fun testKrarFormatUtils() {
        assertTrue(KrarFormatUtils.isValidKrarSignature(0x5241524Bu))
        assertFalse(KrarFormatUtils.isValidKrarSignature(0x5241524Au))
        
        val entrySize = KrarFormatUtils.calculateFileEntrySize(10)
        assertEquals(34, entrySize) // 2 + 10 + 8 + 8 + 4 + 8
    }
}