package org.sparkystudio.krar.archive

/**
 * Utility functions for handling KRar file format
 *
 * @author Андрій Будильников
 */
object KrarFormatUtils {
    const val SIGNATURE = 0x5241524B // "KRAR" in little-endian
    const val VERSION = 1
    const val SIGNATURE_SIZE = 4
    const val VERSION_SIZE = 1
    const val FILE_COUNT_SIZE = 4
    const val HEADER_SIZE = SIGNATURE_SIZE + VERSION_SIZE + FILE_COUNT_SIZE
    
    // File entry fields sizes (in bytes)
    const val FILE_NAME_LENGTH_SIZE = 2
    const val ORIGINAL_SIZE_SIZE = 8
    const val COMPRESSED_SIZE_SIZE = 8
    const val CRC32_SIZE = 4
    const val DATA_OFFSET_SIZE = 8
    
    /**
     * Validates if a file has the KRar signature
     */
    fun isValidKrarSignature(signature: UInt): Boolean {
        return signature == SIGNATURE.toUInt()
    }
    
    /**
     * Calculates the size of a file entry in the central directory
     */
    fun calculateFileEntrySize(fileNameLength: Int): Int {
        return FILE_NAME_LENGTH_SIZE + 
               fileNameLength + 
               ORIGINAL_SIZE_SIZE + 
               COMPRESSED_SIZE_SIZE + 
               CRC32_SIZE + 
               DATA_OFFSET_SIZE
    }
}