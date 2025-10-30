package org.sparkystudio.krar.archive

/**
 * Interface for writing KRar archives
 *
 * @author Андрій Будильников
 */
interface KrarWriter {
    /**
     * Creates a KRar archive from a list of files
     *
     * @param files List of file paths to include in the archive
     * @param archivePath Path where the archive should be created
     */
    fun createArchive(
        files: List<String>,
        archivePath: String
    ): Boolean
    
    /**
     * Adds files to an existing KRar archive
     *
     * @param files List of file paths to add to the archive
     * @param archivePath Path to the existing archive
     */
    fun addFilesToArchive(
        files: List<String>,
        archivePath: String
    ): Boolean
}