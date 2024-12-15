package com.example.translator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.fragment.app.DialogFragment
//import com.example.translator.request.AppDatabase
//import com.example.translator.request.HistoryDbEntity
import com.example.translator.request.TextTranslated
import com.example.translator.request.TranslationFavoriteManager
import com.example.translator.request.TranslationHistoryManager
import com.example.translator.ui.theme.HelpDialogFragment
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

import java.util.Locale

class TranslatorFragment : Fragment() {

    private lateinit var sourceLanguageEnter : EditText
    private lateinit var targetText : TextView
    private lateinit var sourceLanguageBtn : Button
    private lateinit var targetLanguageBtn : Button
    private lateinit var translateBtn : Button
    private lateinit var infoBtn : ImageButton
    private lateinit var copyBtn : ImageButton
    private lateinit var clearBtn : ImageButton
    private lateinit var favBtn : ImageButton

    private var languageArrayList: ArrayList<ModelLanguage>? = null

    private var sourceLanguageCode = "en"
    private var sourceLanguageTitle = "English"
    private var targetLanguageCode = "rus"
    private var targetLanguageTitle = "Russian"

    private lateinit var translatorOptions: TranslatorOptions
    private lateinit var translator: Translator

    private lateinit var historyManager: TranslationHistoryManager
    private lateinit var favoriteManager: TranslationFavoriteManager

    companion object{
        private const val TAG = "MAIN_TAG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyManager = TranslationHistoryManager(requireContext())
        favoriteManager = TranslationFavoriteManager(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_translator, container, false)

            sourceLanguageEnter = view.findViewById(R.id.sourceLanguageEnter)
            targetText = view.findViewById(R.id.targetLanguage)
            sourceLanguageBtn = view.findViewById(R.id.sourceLanguageBtn)
            targetLanguageBtn = view.findViewById(R.id.targetLanguageBtn)
            translateBtn = view.findViewById(R.id.translateBtn)


            loadAvailableLanguages()

            sourceLanguageBtn.setOnClickListener {
                sourceLanguageChoose()
            }
            targetLanguageBtn.setOnClickListener {
                targetLanguageChoose()
            }
            translateBtn.setOnClickListener {
                validateData()
            }
            return view
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoBtn = view.findViewById(R.id.infoBttn)
        infoBtn.setOnClickListener {
            val helpDialog = HelpDialogFragment()
            helpDialog.isCancelable = true // Позволяем закрывать при касании за пределами
            helpDialog.show(parentFragmentManager, "HelpDialog")
        }
        copyBtn = view.findViewById(R.id.copyBttn)
        copyBtn.setOnClickListener {
            copyTextToClipboard(targetText.text.toString())
        }
        clearBtn = view.findViewById(R.id.emptyBttn)
        clearBtn.setOnClickListener {
            sourceLanguageEnter.text.clear()
            targetText.text=""
        }
        favBtn = view.findViewById(R.id.favoriteBttn)
        favBtn.setOnClickListener {
            val favorite = TextTranslated(sourceLanguageTitle, targetLanguageTitle, sourceLanguageText, targetText.text.toString())
            favoriteManager.saveTranslation(favorite)
        }
    }

    private var sourceLanguageText = ""
    private fun validateData() {
        sourceLanguageText = sourceLanguageEnter.text.toString().trim()
        Log.d( TAG, "validateData: sourceLanguageText: $sourceLanguageText")
        if (sourceLanguageText.isEmpty()){
            showToast("Введите текст для перевода ...")
        } else{
            starTranslation()
        }
    }

    private fun starTranslation() {
     translatorOptions = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguageCode)
            .setTargetLanguage(targetLanguageCode)
            .build()
        translator = Translation.getClient(translatorOptions)
        val downloadConditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        translator.downloadModelIfNeeded(downloadConditions)
            .addOnSuccessListener {
                Log.d(TAG, "startTranslation: model's ready, start translating ...")
                translator.translate(sourceLanguageText)
                    .addOnSuccessListener { translatedText ->
                        Log.d(TAG, "startTranslation: translatedText: $translatedText")
                        targetText.text = translatedText
                        val history = TextTranslated(sourceLanguageTitle, targetLanguageTitle, sourceLanguageText, translatedText)
                        historyManager.saveTranslation(history)

                    }
                    .addOnFailureListener { e ->
                        Log.e( TAG, "startTranslation: ", e)
                        showToast("Не удалось из-за ${e.message}")
                    }
            }
            .addOnFailureListener{ e ->
                Log.e( TAG, "startTranslation: ", e)
                showToast("Не удалось из-за ${e.message}")
            }
    }

    private fun loadAvailableLanguages(){
        languageArrayList  = ArrayList()

        val languageCodeList = TranslateLanguage.getAllLanguages()

        for ( languageCode in languageCodeList){
            val languageTitle = Locale(languageCode).displayLanguage
            Log.d(TAG, "loadAvailableLanguages: languageCode: $languageCode")
            Log.d(TAG, "loadAvailableLanguages: languageTitle $languageTitle")

            val modelLanguage = ModelLanguage(languageCode, languageTitle)

            languageArrayList!!.add(modelLanguage)
        }
    }

    private  fun sourceLanguageChoose(){

        val popupMenu = PopupMenu( getActivity(), sourceLanguageBtn)

        for ( i in languageArrayList!!.indices){
            popupMenu.menu.add(Menu.NONE, i, i, languageArrayList!![i].languageTitle)
        }

        popupMenu.show()
        popupMenu.setOnMenuItemClickListener { menuItem ->

            val position = menuItem.itemId
            sourceLanguageCode = languageArrayList!![position].languageCode
            sourceLanguageTitle = languageArrayList!![position].languageTitle

            sourceLanguageBtn.text = sourceLanguageTitle
            sourceLanguageEnter.hint = "Введите $sourceLanguageTitle"

            Log.d( TAG, "sourceLanguage: sourceLanguageCode: $sourceLanguageCode")
            Log.d( TAG, "sourceLanguage: sourceLanguageTitle: $sourceLanguageTitle")
            false
        }
    }

    private fun targetLanguageChoose(){

        val popupMenu = PopupMenu( getActivity(), targetLanguageBtn)

        for ( i in languageArrayList!!.indices){
            popupMenu.menu.add( Menu.NONE, i, i, languageArrayList!![i].languageTitle)
        }

        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {menuItem ->

            val position = menuItem.itemId
            targetLanguageCode = languageArrayList!![position].languageCode
            targetLanguageTitle = languageArrayList!![position].languageTitle

            targetLanguageBtn.text = targetLanguageTitle

            Log.d( TAG, "targetLanguage: targetLanguageCode: $targetLanguageCode")
            Log.d( TAG, "targetLanguage: targetLanguageTitle: $targetLanguageTitle")
            false
        }
    }
    private fun showToast( message: String){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show()
    }
    private fun copyTextToClipboard(text: String) {
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_SHORT).show()
    }

}