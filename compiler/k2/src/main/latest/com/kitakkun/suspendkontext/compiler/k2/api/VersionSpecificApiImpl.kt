package com.kitakkun.suspendkontext.compiler.k2.api

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.resolve.providers.getRegularClassSymbolByClassId
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.name.ClassId

class VersionSpecificApiImpl : VersionSpecificApi {
    override fun getRegularClassSymbolByClassId(classId: ClassId, session: FirSession): FirRegularClassSymbol? {
        return session.getRegularClassSymbolByClassId(classId)
    }
}
