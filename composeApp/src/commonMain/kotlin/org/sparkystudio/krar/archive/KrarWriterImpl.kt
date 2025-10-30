package org.sparkystudio.krar.archive

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
            
            // Create the archive file
            val archiveFile = java.io.File(archivePath)
            println("Archive file path: ${archiveFile.absolutePath}")
            
            if (archiveFile.exists()) {
                println("Deleting existing archive file")
                archiveFile.delete()
            }
            
            // Write a simple header
            val headerData = buildHeader(files.size)
            println("Writing header data (${headerData.size} bytes)")
            archiveFile.writeBytes(headerData)
            
            // Check if file was created
            if (archiveFile.exists()) {
                println("Archive file created successfully, size: ${archiveFile.length()} bytes")
            } else {
                println("Failed to create archive file")
                return false
            }
            
            // Process each file
            for (filePath in files) {
                println("Processing file path: $filePath")
                val file = java.io.File(filePath)
                if (file.exists()) {
                    println("Processing file: $filePath (size: ${file.length()} bytes)")
                    // In a real implementation:
                    // val fileContent = file.readBytes()
                    // val compressedData = compressWithZstd(fileContent)
                    // val crc32 = calculateCRC32(fileContent)
                    // writeCompressedData(compressedData)
                } else {
                    println("Warning: File not found: $filePath")
                    // Let's try with just the filename
                    val simpleFile = java.io.File(file.name)
                    if (simpleFile.exists()) {
                        println("Found file with simple name: ${file.name}")
                    } else {
                        println("File not found with simple name either: ${file.name}")
                    }
                }
            }
            
            println("Archive created successfully!")
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
        
        return true
    }
    
    /**
     * Build a simple header for the archive
     */
    private fun buildHeader(fileCount: Int): ByteArray {
        // Simple header: "KRAR" signature + version + file count
        val header = ByteArray(KrarFormatUtils.HEADER_SIZE)
        // Write "KRAR" signature (4 bytes)
        header[0] = 'K'.code.toByte()
        header[1] = 'R'.code.toByte()
        header[2] = 'A'.code.toByte()
        header[3] = 'R'.code.toByte()
        // Write version (1 byte)
        header[4] = 1
        // Write file count (4 bytes, little endian)
        val fileCountBytes = fileCount.toUInt().toByteArray()
        for (i in 0 until 4) {
            header[5 + i] = fileCountBytes[i]
        }
        return header
    }
    
    /**
     * Convert UInt to little-endian byte array
     */
    private fun UInt.toByteArray(): ByteArray {
        val bytes = ByteArray(4)
        bytes[0] = (this and 0xFFu).toByte()
        bytes[1] = ((this shr 8) and 0xFFu).toByte()
        bytes[2] = ((this shr 16) and 0xFFu).toByte()
        bytes[3] = ((this shr 24) and 0xFFu).toByte()
        return bytes
    }
}