package com.example.translator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translator.request.TextHistory.textHistory
import com.example.translator.request.TextHistoryManager
import com.example.translator.request.TextTranslated
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale



/**
 * A simple [Fragment] subclass.
 * Use the [TranslatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TranslatorFragment : Fragment() {

    private lateinit var sourceLanguageEnter : EditText
    private lateinit var targetLanguage : TextView
    private lateinit var sourceLanguageBtn : Button
    private lateinit var targetLanguageBtn : Button
    private lateinit var translateBtn : Button

    private var languageArrayList: ArrayList<ModelLanguage>? = null
//    private lateinit var viewModel: MyViewModel
//    private var myData: String? = null

    private var sourceLanguageCode = "en"
    private var sourceLanguageTitle = "English"
    private var targetLanguageCode = "rus"
    private var targetLanguageTitle = "Russian"

    private lateinit var translatorOptions: TranslatorOptions
    private lateinit var translator: Translator

//    private lateinit var progressBar: ProgressBar

    companion object{
        //printing logs
        private const val TAG = "MAIN_TAG"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        val view = inflater.inflate(R.layout.fragment_translator, container, false)
//        val context = view.context

            sourceLanguageEnter = view.findViewById(R.id.sourceLanguageEnter)
            targetLanguage = view.findViewById(R.id.targetLanguage)
            sourceLanguageBtn = view.findViewById(R.id.sourceLanguageBtn)
            targetLanguageBtn = view.findViewById(R.id.targetLanguageBtn)
            translateBtn = view.findViewById(R.id.translateBtn)

    //        progressBar = ProgressBar(this )
    //        progressBar.setTitle("Please wait")
    //        progressBar.setCanceledOnTouchOutside(false)
//        if (savedInstanceState != null) {
//            myData = savedInstanceState.getString("my_key")
//        }
//            viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
////
////            // Восстановление данных из ViewModel
//            val myData = viewModel.myData
//            // Создание представления фрагмента
//            // Используйте myData для заполнения UI




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
//        progressBar.setMessage("Processing language")
//        progressBar.show()

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
//                progressBar.setMessage("Translating")
                translator.translate(sourceLanguageText)
                    .addOnSuccessListener { translatedText ->
                        Log.d(TAG, "startTranslation: translatedText: $translatedText")
                        textHistory.add(TextTranslated(sourceLanguageText, translatedText))
//                        context?.let { it1 -> TextHistoryManager.saveTextHistory(it1, textHistory) }

                        targetLanguage.text = translatedText
//                        progressBar.dismiss()
                    }
                    .addOnFailureListener { e ->
//                        progressBar.dismiss()
                        Log.e( TAG, "startTranslation: ", e)
                        showToast("Не удалось из-за ${e.message}")
                    }
            }
            .addOnFailureListener{ e ->
//                progressBar.dismiss()
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

//    class MyViewModel : ViewModel() {
//        var myData: String? = null
//    }
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        // Сохранение данных в Bundle
//        outState.putString("my_key", viewModel.myData)
//    }
}