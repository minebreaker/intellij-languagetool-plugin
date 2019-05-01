package rip.deadcode.intellij.languagetool

import org.languagetool.JLanguageTool
import org.languagetool.language.AmericanEnglish
import org.languagetool.rules.RuleMatch


private val language = AmericanEnglish()  // TODO locale

fun checkGrammar(text: String): List<RuleMatch> {

    val tool = JLanguageTool(language)
    return tool.check(text)
}

fun main() {
    checkGrammar("this guy fucks").forEach {
        println(it.message)
    }
}
