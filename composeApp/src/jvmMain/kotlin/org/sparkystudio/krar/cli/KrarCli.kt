package org.sparkystudio.krar.cli

import org.sparkystudio.krar.archive.KrarReaderImpl
import org.sparkystudio.krar.archive.KrarWriterImpl

/**
 * Command-line interface for KRar archive utility
 *
 * @author Андрій Будильников
 */
object KrarCli {
    private val krarWriter = KrarWriterImpl()
    private val krarReader = KrarReaderImpl()
    
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) {
            printHelp()
            return
        }
        
        when (args[0].lowercase()) {
            "c", "create" -> handleCreateCommand(args)
            "x", "extract" -> handleExtractCommand(args)
            "l", "list" -> handleListCommand(args)
            "h", "help", "-h", "--help" -> printHelp()
            else -> {
                println("Unknown command: ${args[0]}")
                printHelp()
            }
        }
    }
    
    private fun handleCreateCommand(args: Array<String>) {
        if (args.size < 3) {
            println("Usage: krar c[reate] archive.krar file1 [file2...]")
            return
        }
        
        val archivePath = args[1]
        val files = args.drop(2)
        
        println("Creating archive: $archivePath")
        println("Files to include: ${files.joinToString(", ")}")
        
        // Implement archive creation using KrarWriter
        val success = krarWriter.createArchive(files, archivePath)
        if (success) {
            println("Archive created successfully!")
        } else {
            println("Failed to create archive!")
        }
    }
    
    private fun handleExtractCommand(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: krar x[tract] archive.krar [destination]")
            return
        }
        
        val archivePath = args[1]
        val destination = if (args.size > 2) args[2] else "."
        
        println("Extracting archive: $archivePath")
        println("Destination: $destination")
        
        // Implement archive extraction using KrarReader
        val success = krarReader.extractArchive(archivePath, destination)
        if (success) {
            println("Archive extracted successfully!")
        } else {
            println("Failed to extract archive!")
        }
    }
    
    private fun handleListCommand(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: krar l[ist] archive.krar")
            return
        }
        
        val archivePath = args[1]
        println("Listing contents of: $archivePath")
        
        // Implement file listing using KrarReader
        val files = krarReader.listFiles(archivePath)
        println("Archive contents:")
        if (files.isEmpty()) {
            println("  (no files)")
        } else {
            files.forEach { entry ->
                println("  ${entry.fileName} (${entry.originalSize} bytes)")
            }
        }
    }
    
    private fun printHelp() {
        println("KRar Archive Utility - A modern cross-platform archiver")
        println("")
        println("Usage:")
        println("  krar c[reate] archive.krar file1 [file2...]  Create archive")
        println("  krar x[tract] archive.krar [destination]     Extract archive")
        println("  krar l[ist] archive.krar                     List archive contents")
        println("  krar h[elp]                                  Show this help")
        println("")
        println("Examples:")
        println("  krar c archive.krar file1.txt folder/")
        println("  krar x archive.krar")
        println("  krar l archive.krar")
    }
}