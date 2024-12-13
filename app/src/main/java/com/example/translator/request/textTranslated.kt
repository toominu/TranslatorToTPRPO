package com.example.translator.request

class TextTranslated (
    val sourceLanguage: String,
    val targetLanguage: String,
    val sourceText: String,
    val translatedText: String
)
{
    fun SourceLanguage () : String{
        return sourceLanguage
    }
    fun TargetLanguage () : String{
        return targetLanguage
    }
    fun SourceText () : String{
        return sourceText
    }
    fun TargetText () : String{
        return translatedText
    }
}