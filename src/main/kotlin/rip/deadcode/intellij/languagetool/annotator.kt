package rip.deadcode.intellij.languagetool

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

class LtAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        if (element !is PsiComment) return

        val commentText = extractText(element)
        val checkResults = checkGrammar(commentText)

        checkResults.forEach { checkResult ->
            holder.createWeakWarningAnnotation(
                    element,
                    checkResult.message
            )
        }
    }
}

fun extractText(psiComment: PsiComment): String {

    // TODO canonical way to remove opening token?

    val text = psiComment.text
    return removeTodos(removeCommentToken(text))
}

// TODO use `CharSequence`
fun removeCommentToken(text: String): String {
    return when {
        text.startsWith("//") -> text.substring(2).trim()
        text.startsWith("/**") && text.endsWith("*/") -> text.substring(3, text.length - 2).trim()
        text.startsWith("/*") && text.endsWith("*/") -> text.substring(2, text.length - 2).trim()
        else -> throw IllegalStateException("Unknown comment format: ${text}")
    }
}

fun removeTodos(text: String): String {
    return when {
        // TODO any way to get candidates from IDE?
        text.startsWith("TODO") -> text.substring(4).trim()
        text.startsWith("FIX") -> text.substring(3).trim()
        text.startsWith("XXX") -> text.substring(3).trim()
        else -> text
    }
}
