package org.sparkystudio.krar.archive

import com.github.luben.zstd.Zstd
import okio.*
import kotlin.math.absoluteValue

/**
 * Implementation of KRar archive writer
 *
 * @author Андрій Будильников
 */
class KrarWriterImpl : KrarWriter {
    override fun createArchive(
        files: List<String>,
        archivePath: String
    ): Boolean {
        try {
            println("Creating archive: $archivePath")
            println("Files to include: ${files.joinToString(", ")}")
            
            // Create the archive file using Okio
            val archiveFile = File(archivePath)
            if (archiveFile.exists()) {
                archiveFile.delete()
            }
            
            // Create a buffered sink for efficient writing
            archiveFile.sink().buffer().use { sink ->
                // Write the header
                val header = KrarHeader(fileCount = files.size.toUInt())
                sink.writeUIntLe(header.signature)
                sink.writeByte(header.version.toInt())
                sink.writeUIntLe(header.fileCount)
                
                // Process each file and collect entries
                val fileEntries = mutableListOf<KrarFileEntry>()
                var currentOffset = KrarFormatUtils.HEADER_SIZE.toLong()
                
                // First pass: collect file metadata and write compressed data
                for (filePath in files) {
                    val file = File(filePath)
                    if (!file.exists()) {
                        println("Warning: File not found: $filePath")
                        continue
                    }
                    
                    println("Processing file: $filePath (size: ${file.length()} bytes)")
                    
                    // Read file content using Okio
                    val fileContent = file.source().buffer().use { source ->
                        source.readByteArray()
                    }
                    
                    // Compress with Zstandard
                    val compressedData = Zstd.compress(fileContent)
                    println("  Original size: ${fileContent.size} bytes")
                    println("  Compressed size: ${compressedData.size} bytes")
                    
                    // Calculate CRC32
                    val crc32 = calculateCRC32(fileContent)
                    
                    // Create file entry
                    val entry = KrarFileEntry(
                        fileNameLength = file.name.length.toUShort(),
                        fileName = file.name,
                        originalSize = fileContent.size.toULong(),
                        compressedSize = compressedData.size.toULong(),
                        crc32 = crc32,
                        dataOffset = currentOffset.toULong()
                    )
                    
                    fileEntries.add(entry)
                    
                    // Write compressed data to archive
                    sink.write(compressedData)
                    
                    // Update offset for next file
                    currentOffset += compressedData.size
                }
                
                // Write the central directory (file entries)
                for (entry in fileEntries) {
                    sink.writeUShortLe(entry.fileNameLength)
                    sink.writeUtf8(entry.fileName)
                    sink.writeULongLe(entry.originalSize)
                    sink.writeULongLe(entry.compressedSize)
                    sink.writeUIntLe(entry.crc32)
                    sink.writeULongLe(entry.dataOffset)
                }
                
                println("Archive created successfully with ${fileEntries.size} files!")
            }
            
            return true
        } catch (e: Exception) {
            println("Error creating archive: ${e.message}")
            e.printStackTrace()
            return false
        }
    }
    
    override fun addFilesToArchive(
        files: List<String>,
        archivePath: String
    ): Boolean {
        println("Adding files to archive: $archivePath")
        println("Files to add: ${files.joinToString(", ")}")
        
        // TODO: Implement adding files to existing archive
        // This would involve:
        // 1. Reading existing archive
        // 2. Appending new files
        // 3. Updating header and directory
        
        return true
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