package org.sparkystudio.krar.archive

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import java.io.File

/**
 * Test for KRar archive functionality
 *
 * @author Андрій Будильников
 */
class ArchiveTest {
    @Test
    fun testCreateAndExtractArchive() {
        // Create test files
        val testDir = File("test_archive")
        testDir.mkdirs()
        
        val testFile1 = File(testDir, "test1.txt")
        testFile1.writeText("This is test file 1 content")
        
        val testFile2 = File(testDir, "test2.txt")
        testFile2.writeText("This is test file 2 content with more data to compress")
        
        // Create archive
        val writer = KrarWriterImpl()
        val archivePath = "test.krar"
        val success = writer.createArchive(
            listOf(testFile1.absolutePath, testFile2.absolutePath),
            archivePath
        )
        
        assertTrue(success, "Archive creation should succeed")
        
        // Check that archive was created
        val archiveFile = File(archivePath)
        assertTrue(archiveFile.exists(), "Archive file should exist")
        
        // List files in archive
        val reader = KrarReaderImpl()
        val entries = reader.listFiles(archivePath)
        assertEquals(2, entries.size, "Archive should contain 2 files")
        
        // Extract archive
        val extractDir = File("extracted")
        extractDir.mkdirs()
        
        val extractSuccess = reader.extractArchive(archivePath, extractDir.absolutePath)
        assertTrue(extractSuccess, "Archive extraction should succeed")
        
        // Verify extracted files
        val extractedFile1 = File(extractDir, "test1.txt")
        assertTrue(extractedFile1.exists(), "Extracted file 1 should exist")
        assertEquals("This is test file 1 content", extractedFile1.readText(), "File 1 content should match")
        
        val extractedFile2 = File(extractDir, "test2.txt")
        assertTrue(extractedFile2.exists(), "Extracted file 2 should exist")
        assertEquals("This is test file 2 content with more data to compress", extractedFile2.readText(), "File 2 content should match")
        
        // Cleanup
        testDir.deleteRecursively()
        archiveFile.delete()
        extractDir.deleteRecursively()
    }
}