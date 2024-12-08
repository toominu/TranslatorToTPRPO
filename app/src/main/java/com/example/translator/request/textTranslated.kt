package com.example.translator.request

class TextTranslated ( var sourceText: String,
                        var targetText: String )
{
    fun SourceText () : String{
        return sourceText
    }
    fun TargetText () : String{
        return targetText
    }
}