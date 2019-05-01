package rip.deadcode.intellij.languagetool

import com.intellij.openapi.util.TextRange


fun TextRange.curLeft(n: Int): TextRange {

    if (this.length < n) throw IllegalArgumentException()

    return TextRange(this.startOffset + n, this.endOffset)
}


fun TextRange.curRight(n: Int): TextRange {

    if (this.length < n) throw IllegalArgumentException()

    return TextRange(this.startOffset, this.endOffset - n)
}
