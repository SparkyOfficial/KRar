package org.sparkystudio.krar.archive

/**
 * Represents a file entry in a KRar archive
 *
 * @author Андрій Будильников
 */
data class KrarFileEntry(
    val fileNameLength: UShort,
    val fileName: String,
    val originalSize: ULong,
    val compressedSize: ULong,
    val crc32: UInt,
    val dataOffset: ULong
)