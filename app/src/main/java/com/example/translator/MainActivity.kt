package com.example.translator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    val fragmentTransaction = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottomNavigation)
        //binding = ActivityMainBinding.inflate(layoutInflater)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_translate -> {
                    replaceFragment(TranslatorFragment())
                    Toast.makeText(this, "Переводчик!", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true

                }

                R.id.navigation_history -> {
                    replaceFragment(HistoryFragment())
                    Toast.makeText(this, "История!", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true

                }

                R.id.navigation_favorite -> {
                    replaceFragment(FavoriteFragment())
                    Toast.makeText(this, "Избранное!", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true

                }

                else -> false
            }
        }
        replaceFragment(TranslatorFragment())
    }


    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
//    }
//    fragmentTransaction.replace(R.id.fragment_container, newFragment)
//    fragmentTransaction.addToBackStack(null) // Добавляем в стек возврата
//    fragmentTransaction.commit()
}
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var sourceLanguageEnter : EditText
//    private lateinit var targetLanguage : TextView
//    private lateinit var sourceLanguageBtn : Button
//    private lateinit var targetLanguageBtn : Button
//    private lateinit var translateBtn : Button
//
//    private var languageArrayList: ArrayList<ModelLanguage>? = null
//
//    private var sourceLanguageCode = "en"
//    private var sourceLanguageTitle = "English"
//    private var targetLanguageCode = "rus"
//    private var targetLanguageTitle = "Russian"
//
//    private lateinit var translatorOptions: TranslatorOptions
//    private lateinit var translator: Translator
////    private lateinit var progressBar: ProgressBar
//
//    companion object{
//        //printing logs
//        private const val TAG = "MAIN_TAG"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//
//        sourceLanguageEnter = findViewById(R.id.sourceLanguageEnter)
//        targetLanguage = findViewById(R.id.targetLanguage)
//        sourceLanguageBtn = findViewById(R.id.sourceLanguageBtn)
//        targetLanguageBtn = findViewById(R.id.targetLanguageBtn)
//        translateBtn = findViewById(R.id.translateBtn)
//
////        progressBar = ProgressBar(this )
////        progressBar.setTitle("Please wait")
////        progressBar.setCanceledOnTouchOutside(false)
//
//        loadAvailableLanguages()
//
//        sourceLanguageBtn.setOnClickListener {
//            sourceLanguageChoose()
//        }
//        targetLanguageBtn.setOnClickListener {
//            targetLanguageChoose()
//        }
//        translateBtn.setOnClickListener {
//            validateData()
//        }
//    }
//
//    private var sourceLanguageText = ""
//    private fun validateData() {
//        sourceLanguageText = sourceLanguageEnter.text.toString().trim()
//        Log.d( TAG, "validateData: sourceLanguageText: $sourceLanguageText")
//        if (sourceLanguageText.isEmpty()){
//            showToast("Enter text to translate ...")
//        } else{
//            starTranslation()
//        }
//    }
//
//    private fun starTranslation() {
////        progressBar.setMessage("Processing language")
////        progressBar.show()
//
//        translatorOptions = TranslatorOptions.Builder()
//            .setSourceLanguage(sourceLanguageCode)
//            .setTargetLanguage(targetLanguageCode)
//            .build()
//        translator = Translation.getClient(translatorOptions)
//        val downloadConditions = DownloadConditions.Builder()
//            .requireWifi()
//            .build()
//
//        translator.downloadModelIfNeeded(downloadConditions)
//            .addOnSuccessListener {
//                Log.d(TAG, "startTranslation: model's ready, start translating ...")
////                progressBar.setMessage("Translating")
//                translator.translate(sourceLanguageText)
//                    .addOnSuccessListener { translatedText ->
//                        Log.d(TAG, "startTranslation: translatedText: $translatedText")
////                        progressBar.dismiss()
//
//
//                    }
//                    .addOnFailureListener { e ->
////                        progressBar.dismiss()
//                        Log.e( TAG, "startTranslation: ", e)
//                        showToast("Failed to translate due to ${e.message}")
//                    }
//            }
//            .addOnFailureListener{ e ->
////                progressBar.dismiss()
//                Log.e( TAG, "startTranslation: ", e)
//                showToast("Failed to translate due to ${e.message}")
//            }
//    }
//
//    private fun loadAvailableLanguages(){
//        languageArrayList  = ArrayList()
//
//        val languageCodeList = TranslateLanguage.getAllLanguages()
//
//        for ( languageCode in languageCodeList){
//            val languageTitle = Locale(languageCode).displayLanguage
//            Log.d(TAG, "loadAvailableLanguages: languageCode: $languageCode")
//            Log.d(TAG, "loadAvailableLanguages: languageTitle $languageTitle")
//
//            val modelLanguage = ModelLanguage(languageCode, languageTitle)
//
//            languageArrayList!!.add(modelLanguage)
//        }
//    }
//
//    private  fun sourceLanguageChoose(){
//
//        val popupMenu = PopupMenu( this, sourceLanguageBtn)
//
//        for ( i in languageArrayList!!.indices){
//            popupMenu.menu.add(Menu.NONE, i, i, languageArrayList!![i].languageTitle)
//        }
//
//        popupMenu.show()
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//
//            val position = menuItem.itemId
//            sourceLanguageCode = languageArrayList!![position].languageCode
//            sourceLanguageTitle = languageArrayList!![position].languageTitle
//
//            sourceLanguageBtn.text = sourceLanguageTitle
//            sourceLanguageEnter.hint = "Enter $sourceLanguageTitle"
//
//            Log.d( TAG, "sourceLanguage: sourceLanguageCode: $sourceLanguageCode")
//            Log.d( TAG, "sourceLanguage: sourceLanguageTitle: $sourceLanguageTitle")
//            false
//        }
//    }
//
//    private fun targetLanguageChoose(){
//
//        val popupMenu = PopupMenu( this, targetLanguageBtn)
//
//        for ( i in languageArrayList!!.indices){
//            popupMenu.menu.add( Menu.NONE, i, i, languageArrayList!![i].languageTitle)
//        }
//
//        popupMenu.show()
//        popupMenu.setOnMenuItemClickListener {menuItem ->
//
//            val position = menuItem.itemId
//            targetLanguageCode = languageArrayList!![position].languageCode
//            targetLanguageTitle = languageArrayList!![position].languageTitle
//
//            targetLanguageBtn.text = targetLanguageTitle
//
//            Log.d( TAG, "targetLanguage: targetLanguageCode: $targetLanguageCode")
//            Log.d( TAG, "targetLanguage: targetLanguageTitle: $targetLanguageTitle")
//            false
//        }
//    }
//    private fun showToast( message: String){
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//    }
//
//
//}