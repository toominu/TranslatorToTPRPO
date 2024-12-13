package com.example.translator.ui.theme
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.translator.R


class HelpDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Создаем представление для диалогового окна
        val view = inflater.inflate(R.layout.fragment_help_dialog, container, false)

        // Устанавливаем текст справки из strings.xml
        val helpTextView: TextView = view.findViewById(R.id.helpTextView)
        helpTextView.text = getString(R.string.name_help_dialog) +
                getString(R.string.version_help_dialog) +
                getString(R.string.description_of_programm_help_dialog)+
                getString(R.string.instruction_help_dialog) +
                getString(R.string.contacts_help_dialog)

        val closeButton: ImageButton = view.findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            dismiss() // Закрываем диалог
        }

        return view
    }

    // Устанавливаем размеры диалогового окна
    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    // Закрываем диалог при касании за пределами
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }
}