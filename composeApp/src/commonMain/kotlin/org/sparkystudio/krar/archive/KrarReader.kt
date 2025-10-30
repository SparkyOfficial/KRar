package org.sparkystudio.krar.archive

/**
 * Interface for reading KRar archives
 *
 * @author Андрій Будильников
 */
interface KrarReader {
    /**
     * Extracts all files from a KRar archive
     *
     * @param archivePath Path to the archive file
     * @param destinationPath Directory where files should be extracted
     */
    fun extractArchive(
        archivePath: String,
        destinationPath: String
    ): Boolean
    
    /**
     * Lists all files in a KRar archive
     *
     * @param archivePath Path to the archive file
     * @return List of file entries in the archive
     */
    fun listFiles(
        archivePath: String
    ): List<KrarFileEntry>
    
    /**
     * Extracts specific files from a KRar archive
     *
     * @param archivePath Path to the archive file
     * @param filesToExtract List of file names to extract
     * @param destinationPath Directory where files should be extracted
     */
    fun extractFiles(
        archivePath: String,
        filesToExtract: List<String>,
        destinationPath: String
    ): Boolean
}