package org.sparkystudio.krar.archive

import com.github.luben.zstd.Zstd
import okio.*
import java.io.File

/**
 * Implementation of KRar archive reader
 *
 * @author Андрій Будильников
 */
class KrarReaderImpl : KrarReader {
    override fun extractArchive(
        archivePath: String,
        destinationPath: String
    ): Boolean {
        try {
            println("Extracting archive: $archivePath")
            println("Destination: $destinationPath")
            
            val archiveFile = File(archivePath)
            if (!archiveFile.exists()) {
                println("Error: Archive file not found: $archivePath")
                return false
            }
            
            // Read and validate header
            val header = readHeader(archiveFile)
            if (header == null) {
                println("Error: Invalid archive format")
                return false
            }
            
            println("Archive contains ${header.fileCount} files")
            
            // Parse central directory
            val fileEntries = parseCentralDirectory(archiveFile, header)
            
            // Extract each file
            val destinationDir = File(destinationPath)
            if (!destinationDir.exists()) {
                destinationDir.mkdirs()
            }
            
            var successCount = 0
            for (entry in fileEntries) {
                if (extractFile(archiveFile, entry, destinationPath)) {
                    successCount++
                }
            }
            
            println("Extracted $successCount of ${fileEntries.size} files successfully")
            return true
        } catch (e: Exception) {
            println("Error extracting archive: ${e.message}")
            e.printStackTrace()
            return false
        }
    }
    
    override fun listFiles(
        archivePath: String
    ): List<KrarFileEntry> {
        try {
            println("Listing contents of: $archivePath")
            
            val archiveFile = File(archivePath)
            if (!archiveFile.exists()) {
                println("Error: Archive file not found: $archivePath")
                return emptyList()
            }
            
            // Read and validate header
            val header = readHeader(archiveFile)
            if (header == null) {
                println("Error: Invalid archive format")
                return emptyList()
            }
            
            // Parse central directory and return file entries
            val entries = parseCentralDirectory(archiveFile, header)
            println("Archive contains ${entries.size} files:")
            entries.forEach { entry ->
                println("  ${entry.fileName} (${entry.originalSize} bytes)")
            }
            
            return entries
        } catch (e: Exception) {
            println("Error listing archive contents: ${e.message}")
            e.printStackTrace()
            return emptyList()
        }
    }
    
    override fun extractFiles(
        archivePath: String,
        filesToExtract: List<String>,
        destinationPath: String
    ): Boolean {
        try {
            println("Extracting specific files from: $archivePath")
            println("Files to extract: ${filesToExtract.joinToString(", ")}")
            println("Destination: $destinationPath")
            
            val archiveFile = File(archivePath)
            if (!archiveFile.exists()) {
                println("Error: Archive file not found: $archivePath")
                return false
            }
            
            // Read and validate header
            val header = readHeader(archiveFile)
            if (header == null) {
                println("Error: Invalid archive format")
                return false
            }
            
            // Parse central directory
            val fileEntries = parseCentralDirectory(archiveFile, header)
            
            // Extract only specified files
            val destinationDir = File(destinationPath)
            if (!destinationDir.exists()) {
                destinationDir.mkdirs()
            }
            
            var successCount = 0
            for (entry in fileEntries) {
                if (entry.fileName in filesToExtract) {
                    if (extractFile(archiveFile, entry, destinationPath)) {
                        successCount++
                    }
                }
            }
            
            println("Extracted $successCount of ${filesToExtract.size} requested files")
            return true
        } catch (e: Exception) {
            println("Error extracting specific files: ${e.message}")
            e.printStackTrace()
            return false
        }
    }
    
    /**
     * Read and validate the archive header
     * читаємо заголовок архіву для перевірки формату
     */
    private fun readHeader(file: File): KrarHeader? {
        return try {
            file.source().buffer().use { source ->
                // Read signature
                val signature = source.readUIntLe()
                if (signature != KrarFormatUtils.SIGNATURE.toUInt()) {
                    println("Invalid signature: expected ${KrarFormatUtils.SIGNATURE}, got $signature")
                    return null
                }
                
                // Read version
                val version = source.readByte().toUByte()
                if (version.toInt() != KrarFormatUtils.VERSION) {
                    println("Unsupported version: ${version.toInt()}")
                    return null
                }
                
                // Read file count
                val fileCount = source.readUIntLe()
                
                KrarHeader(signature, version, fileCount)
            }
        } catch (e: Exception) {
            println("Error reading header: ${e.message}")
            null
        }
    }
    
    /**
     * Parse the central directory to get file entries
     * парсимо центральну директорію для отримання списку файлів
     */
    private fun parseCentralDirectory(file: File, header: KrarHeader): List<KrarFileEntry> {
        val entries = mutableListOf<KrarFileEntry>()
        
        try {
            file.source().buffer().use { source ->
                // Skip to the end of the file data to read the central directory
                // In a real implementation, we would need to calculate the exact position
                // For now, we'll read from the end based on the number of files
                val directorySize = header.fileCount.toInt() * (
                    KrarFormatUtils.FILE_NAME_LENGTH_SIZE +
                    KrarFormatUtils.ORIGINAL_SIZE_SIZE +
                    KrarFormatUtils.COMPRESSED_SIZE_SIZE +
                    KrarFormatUtils.CRC32_SIZE +
                    KrarFormatUtils.DATA_OFFSET_SIZE
                )
                
                // Seek to the directory position (this is simplified)
                // In a real implementation, we would store the directory offset in the header
                source.skip(file.length() - directorySize)
                
                // Read each file entry
                for (i in 0 until header.fileCount.toInt()) {
                    val fileNameLength = source.readUShortLe().toInt()
                    val fileName = source.readUtf8(fileNameLength.toLong())
                    val originalSize = source.readULongLe()
                    val compressedSize = source.readULongLe()
                    val crc32 = source.readUIntLe()
                    val dataOffset = source.readULongLe()
                    
                    entries.add(
                        KrarFileEntry(
                            fileNameLength.toUShort(),
                            fileName,
                            originalSize,
                            compressedSize,
                            crc32,
                            dataOffset
                        )
                    )
                }
            }
        } catch (e: Exception) {
            println("Error parsing central directory: ${e.message}")
        }
        
        return entries
    }
    
    /**
     * Extract a single file from the archive
     * витягуємо окремий файл з архіву
     */
    private fun extractFile(archiveFile: File, entry: KrarFileEntry, destinationPath: String): Boolean {
        return try {
            println("Extracting: ${entry.fileName}")
            
            archiveFile.source().buffer().use { source ->
                // Seek to the file data offset
                source.skip(entry.dataOffset.toLong())
                
                // Read compressed data
                val compressedData = ByteArray(entry.compressedSize.toInt())
                source.readFully(compressedData)
                
                // Decompress with Zstandard
                val decompressedData = Zstd.decompress(
                    compressedData,
                    entry.originalSize.toInt()
                )
                
                // Verify CRC32
                val calculatedCrc32 = calculateCRC32(decompressedData)
                if (calculatedCrc32 != entry.crc32) {
                    println("  CRC32 mismatch for ${entry.fileName}: expected ${entry.crc32}, got $calculatedCrc32")
                    return false
                }
                
                // Write to destination
                val outputFile = File(destinationPath, entry.fileName)
                outputFile.parentFile?.mkdirs()
                outputFile.sink().buffer().use { sink ->
                    sink.write(decompressedData)
                }
                
                println("  Extracted successfully (${entry.originalSize} bytes)")
                true
            }
        } catch (e: Exception) {
            println("  Error extracting ${entry.fileName}: ${e.message}")
            false
        }
    }
    
    /**
     * Calculate CRC32 hash for byte array
     * обчислюємо хеш для перевірки цілісності файлу
     */
    private fun calculateCRC32(data: ByteArray): UInt {
        var crc = 0xFFFFFFFFu
        for (byte in data) {
            crc = crc xor byte.toUInt()
            for (i in 0 until 8) {
                if (crc and 1u != 0u) {
                    crc = crc shr 1 xor 0xEDB88320u
                } else {
                    crc = crc shr 1
                }
            }
        }
        return crc.inv()
    }
}