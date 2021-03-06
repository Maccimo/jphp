package org.develnext.jphp.core.compiler.jvm.statement.expr.value;

import org.develnext.jphp.core.compiler.jvm.statement.ExpressionStmtCompiler;
import org.develnext.jphp.core.compiler.jvm.statement.expr.BaseExprCompiler;
import org.develnext.jphp.core.tokenizer.token.expr.value.HexExprValue;
import php.runtime.memory.LongMemory;

public class HexValueCompiler extends BaseExprCompiler<HexExprValue> {
    public HexValueCompiler(ExpressionStmtCompiler exprCompiler) {
        super(exprCompiler);
    }

    @Override
    public void write(HexExprValue token, boolean returnValue) {
        expr.writePushMemory(new LongMemory(token.getValue()));
    }
}
