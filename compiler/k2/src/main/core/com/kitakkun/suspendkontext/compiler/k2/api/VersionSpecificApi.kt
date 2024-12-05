package com.kitakkun.suspendkontext.compiler.k2.api

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.name.ClassId

interface VersionSpecificApi {
    companion object {
        lateinit var INSTANCE: VersionSpecificApi
    }

    fun getRegularClassSymbolByClassId(classId: ClassId, session: FirSession): FirRegularClassSymbol?
}
