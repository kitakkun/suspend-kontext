package com.kitakkun.suspendkontext.compiler.backend

import org.jetbrains.kotlin.backend.common.ir.addExtensionReceiver
import org.jetbrains.kotlin.backend.common.ir.moveBodyTo
import org.jetbrains.kotlin.backend.common.lower.createIrBuilder
import org.jetbrains.kotlin.backend.common.lower.irBlockBody
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irExprBody
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
import org.jetbrains.kotlin.ir.util.getPropertyGetter
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.SpecialNames

class CoroutineContextTransformer(
    private val irContext: SuspendKontextIrContext,
) : IrElementTransformerVoid() {
    override fun visitFunction(declaration: IrFunction): IrStatement {
        val dispatcherName = when {
            declaration.hasAnnotation(SuspendKontextConsts.ioContextAnnotationClassId) -> "IO"
            declaration.hasAnnotation(SuspendKontextConsts.defaultContextAnnotationClassId) -> "Default"
            declaration.hasAnnotation(SuspendKontextConsts.unconfinedContextAnnotationClassId) -> "Unconfined"
            declaration.hasAnnotation(SuspendKontextConsts.mainContextAnnotationClassId) -> "Main"
            else -> return super.visitDeclaration(declaration)
        }

        val body = declaration.body
        val irBuilder = irContext.irBuiltIns.createIrBuilder(declaration.symbol)

        val functionReturnType = declaration.returnType

        val getDispatcherCall = with(irBuilder) {
            irCall(irContext.dispatchers.owner.getPropertyGetter(dispatcherName)!!).apply {
                dispatchReceiver = irGetObject(irContext.dispatchers)
            }
        }

        val withContextBlockFunction = irContext.irFactory.createSimpleFunction(
            startOffset = declaration.startOffset,
            endOffset = declaration.endOffset,
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
            addExtensionReceiver(type = irContext.coroutineScopeClass.defaultType)
            this.body = declaration.moveBodyTo(target = this, arguments = emptyMap())
        }

        val withContextBlockFunctionReference = IrFunctionExpressionImpl(
            startOffset = declaration.startOffset,
            endOffset = declaration.endOffset,
            origin = IrStatementOrigin.LAMBDA,
            type = functionReturnType,
            function = withContextBlockFunction,
        ).apply {
            this.type = irContext.irBuiltIns.suspendFunctionN(1).typeWith(
                listOf(
                    irContext.coroutineScopeClass.defaultType,
                    functionReturnType
                )
            )
        }

        val bodyWrappedByWithContext = with(irBuilder) {
            irCall(
                callee = irContext.withContextFunction,
                type = functionReturnType,
                typeArguments = listOf(functionReturnType)
            ).apply {
                putValueArgument(index = 0, valueArgument = getDispatcherCall)
                putValueArgument(index = 1, valueArgument = withContextBlockFunctionReference)
            }
        }

        when (body) {
            is IrBlockBody -> {
                declaration.body = irBuilder.irBlockBody(declaration) { +bodyWrappedByWithContext }
            }

            is IrExpressionBody -> {
                declaration.body = irBuilder.irExprBody(bodyWrappedByWithContext)
            }

            is IrSyntheticBody, null -> Unit
        }

        return super.visitFunction(declaration)
    }
}