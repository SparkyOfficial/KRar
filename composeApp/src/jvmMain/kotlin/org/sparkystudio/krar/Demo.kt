package org.sparkystudio.krar

import org.sparkystudio.krar.archive.KrarWriterImpl
import org.sparkystudio.krar.archive.KrarReaderImpl
import java.io.File

/**
 * Demonstration of KRar enhanced features
 *
 * @author Андрій Будильников
 */
fun main() {
    println("KRar Enhanced Features Demonstration")
    println("===================================")
    
    // Create test files
    println("\n1. Creating test files...")
    val testDir = File("demo_files")
    testDir.mkdirs()
    
    val testFile1 = File(testDir, "document.txt")
    testFile1.writeText("This is a sample document with some text content for compression testing.")
    
    val testFile2 = File(testDir, "data.csv")
    testFile2.writeText("Name,Age,City\nJohn,25,New York\nJane,30,London\nBob,35,Paris")
    
    val testFile3 = File(testDir, "readme.md")
    testFile3.writeText("# Sample README\n\nThis is a sample README file for demonstration purposes.")
    
    println("Created test files:")
    listOf(testFile1, testFile2, testFile3).forEach { file ->
        println("  - ${file.name} (${file.length()} bytes)")
    }
    
    // Demonstrate archive creation with compression
    println("\n2. Creating compressed archive with Zstandard...")
    val writer = KrarWriterImpl()
    val archivePath = "demo.krar"
    
    val success = writer.createArchive(
        listOf(testFile1.absolutePath, testFile2.absolutePath, testFile3.absolutePath),
        archivePath
    )
    
    if (success) {
        val archiveFile = File(archivePath)
        println("Archive created successfully!")
        println("  Archive size: ${archiveFile.length()} bytes")
        println("  Archive path: $archivePath")
    } else {
        println("Failed to create archive!")
        return
    }
    
    // Demonstrate file listing
    println("\n3. Listing archive contents...")
    val reader = KrarReaderImpl()
    val entries = reader.listFiles(archivePath)
    
    println("Archive contains ${entries.size} files:")
    entries.forEach { entry ->
        val compressionRatio = if (entry.originalSize > 0u) {
            "%.1f%%".format((1.0 - (entry.compressedSize.toDouble() / entry.originalSize.toDouble())) * 100)
        } else "0%"
        println("  - ${entry.fileName}")
        println("    Original: ${entry.originalSize} bytes")
        println("    Compressed: ${entry.compressedSize} bytes (Compression: $compressionRatio)")
        println("    CRC32: ${entry.crc32}")
    }
    
    // Demonstrate full extraction
    println("\n4. Extracting all files...")
    val extractDir = File("extracted_demo")
    extractDir.mkdirs()
    
    val extractSuccess = reader.extractArchive(archivePath, extractDir.absolutePath)
    
    if (extractSuccess) {
        println("Extraction completed successfully!")
        println("Extracted files to: ${extractDir.absolutePath}")
        
        // List extracted files
        extractDir.listFiles()?.forEach { file ->
            println("  - ${file.name} (${file.length()} bytes)")
        }
    } else {
        println("Extraction failed!")
    }
    
    // Demonstrate partial extraction
    println("\n5. Extracting specific files...")
    val partialExtractDir = File("partial_extracted")
    partialExtractDir.mkdirs()
    
    val partialExtractSuccess = reader.extractFiles(
        archivePath,
        listOf("document.txt", "readme.md"),
        partialExtractDir.absolutePath
    )
    
    if (partialExtractSuccess) {
        println("Partial extraction completed!")
        println("Extracted files to: ${partialExtractDir.absolutePath}")
        
        partialExtractDir.listFiles()?.forEach { file ->
            println("  - ${file.name} (${file.length()} bytes)")
        }
    } else {
        println("Partial extraction failed!")
    }
    
    // Verify extracted content
    println("\n6. Verifying extracted content...")
    val extractedDoc = File(extractDir, "document.txt")
    if (extractedDoc.exists() && extractedDoc.readText() == testFile1.readText()) {
        println("✓ Document content verified")
    } else {
        println("✗ Document content verification failed")
    }
    
    val extractedReadme = File(extractDir, "readme.md")
    if (extractedReadme.exists() && extractedReadme.readText() == testFile3.readText()) {
        println("✓ README content verified")
    } else {
        println("✗ README content verification failed")
    }
    
    // Cleanup
    println("\n7. Cleaning up...")
    testDir.deleteRecursively()
    File(archivePath).delete()
    extractDir.deleteRecursively()
    partialExtractDir.deleteRecursively()
    
    println("\nDemonstration completed successfully!")
    println("\nKey Features Demonstrated:")
    println("✓ Zstandard compression integration")
    println("✓ Okio efficient file I/O")
    println("✓ CRC32 integrity verification")
    println("✓ Full archive extraction")
    println("✓ Partial file extraction")
    println("✓ Archive content listing")
    println("✓ Compression ratio reporting")
}