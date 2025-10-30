package org.sparkystudio.krar.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.sparkystudio.krar.archive.KrarReader
import org.sparkystudio.krar.archive.KrarReaderImpl
import org.sparkystudio.krar.archive.KrarWriter
import org.sparkystudio.krar.archive.KrarWriterImpl

/**
 * Koin modules for dependency injection
 *
 * @author Андрій Будильников
 */
object KoinModules {
    val archiveModule: Module = module {
        single<KrarWriter> { KrarWriterImpl() }
        single<KrarReader> { KrarReaderImpl() }
    }
    
    val allModules = listOf(
        archiveModule
    )
}