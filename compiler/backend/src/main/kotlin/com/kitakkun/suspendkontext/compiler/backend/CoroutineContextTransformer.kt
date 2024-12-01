package com.kitakkun.suspendkontext.compiler.backend

import org.jetbrains.kotlin.backend.common.ir.addExtensionReceiver
import org.jetbrains.kotlin.backend.common.ir.moveBodyTo
import org.jetbrains.kotlin.backend.common.lower.createIrBuilder
import org.jetbrains.kotlin.backend.common.lower.irBlockBody
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.fir.backend.utils.defaultTypeWithoutArguments
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irGetObject
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.ir.types.typeWith
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.getPropertyGetter
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.SpecialNames

class CoroutineContextTransformer(
    private val irContext: SuspendKontextIrContext,
) : IrElementTransformerVoid() {
    override fun visitFunction(declaration: IrFunction): IrStatement {
        if (!declaration.hasAnnotation(SuspendKontextConsts.ioContextAnnotationClassId)) return super.visitFunction(declaration)

        val body = declaration.body
        val irBuilder = irContext.irBuiltIns.createIrBuilder(declaration.symbol)

        val functionReturnType = declaration.returnType

        when (body) {
            is IrBlockBody -> {
                declaration.body = irBuilder.irBlockBody(declaration) {
                    +irCall(
                        callee = irContext.withContextFunction,
                        type = functionReturnType,
                        typeArguments = listOf(functionReturnType)
                    ).apply {
                        putValueArgument(
                            index = 0,
                            valueArgument = irCall(irContext.dispatchers.owner.getPropertyGetter("IO")!!).apply {
                                dispatchReceiver = irGetObject(irContext.dispatchers)
                            }
                        )
                        putValueArgument(
                            index = 1,
                            valueArgument = IrFunctionExpressionImpl(
                                startOffset = startOffset,
                                endOffset = endOffset,
                                origin = IrStatementOrigin.LAMBDA,
                                type = functionReturnType,
                                function = irContext.irFactory.createSimpleFunction(
                                    startOffset = startOffset,
                                    endOffset = endOffset,
                                    origin = IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA,
                                    name = SpecialNames.ANONYMOUS,
                                    visibility = DescriptorVisibilities.LOCAL,
                                    isInline = false,
                                    isExpect = false,
                                    returnType = functionReturnType,
                                    modality = Modality.FINAL,
                                    symbol = IrSimpleFunctionSymbolImpl(),
                                    isTailrec = false,
                                    isSuspend = true,
                                    isOperator = false,
                                    isInfix = false,
                                ).apply {
                                    parent = declaration
                                    addExtensionReceiver(type = irContext.coroutineScopeClass.defaultType).apply {
//                                        this.name = Name.identifier("\$this\$withContext")
                                    }
                                    this.body = declaration.moveBodyTo(target = this, arguments = emptyMap())
                                }
                            ).apply {
                                this.type = irContext.irBuiltIns.suspendFunctionN(1).typeWith(
                                    listOf(
                                        irContext.coroutineScopeClass.defaultTypeWithoutArguments,
                                        functionReturnType
                                    )
                                )
                            }
                        )
                    }
                }
            }

            is IrExpressionBody -> {

            }

            is IrSyntheticBody, null -> Unit
        }

//        irContext.messageCollector.report(
//            severity = CompilerMessageSeverity.STRONG_WARNING,
//            message = "BEFORE::" + body?.dump().toString()
//        )
//        irContext.messageCollector.report(
//            severity = CompilerMessageSeverity.STRONG_WARNING,
//            message = "AFTER::" + declaration.body?.dump().toString()
//        )

        return super.visitFunction(declaration)
    }
}