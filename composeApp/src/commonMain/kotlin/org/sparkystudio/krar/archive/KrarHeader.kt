package org.sparkystudio.krar.archive

/**
 * KRar file format header structure
 *
 * @author Андрій Будильников
 */
data class KrarHeader(
    val signature: UInt = 0x5241524Bu, // "KRAR" in little-endian
    val version: UByte = 1u,
    val fileCount: UInt = 0u
)