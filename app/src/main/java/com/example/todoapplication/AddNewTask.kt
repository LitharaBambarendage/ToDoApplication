package com.example.todoapplication;

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.todoapplication.DialogCloseListner
import com.example.todoapplication.Model.ToDoModel
import com.example.todoapplication.R
import com.example.todoapplication.Utils.DatabaseHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewTask : BottomSheetDialogFragment() {
    private var newTaskText: EditText? = null
    private var newTaskSaveButton: Button? = null
    private var db: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_task, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newTaskText = view.findViewById(R.id.newTaskText)
        newTaskSaveButton = view.findViewById(R.id.newTaskButton)
        db = DatabaseHandler(requireContext())
        db?.openDatabase()

        var isUpdate = false
        arguments?.let { bundle ->
            isUpdate = true
            val task = bundle.getString("task")
            newTaskText?.setText(task)
            if (task?.length ?: 0 > 0) {
                newTaskSaveButton?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimaryDark
                    )
                )
            }
        }

        newTaskText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newTaskSaveButton?.isEnabled = !s.isNullOrBlank()
                newTaskSaveButton?.setTextColor(
                    if (!s.isNullOrBlank()) {
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimaryDark
                        )
                    } else {
                        Color.GRAY
                    }
                )
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val finalIsUpdate = isUpdate
        newTaskSaveButton?.setOnClickListener {
            val text = newTaskText?.text.toString()
            if (finalIsUpdate) {
                arguments?.let { bundle ->
                    db?.updateTask(bundle.getInt("id"), text)
                }
            } else {
                val task = ToDoModel().apply {
                    this.task = text
                    status = 0
                }
                db?.insertTask(task)
            }
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        val activity: Activity? = activity
        if (activity is DialogCloseListner) {
            (activity as DialogCloseListner).handleDialogClose(dialog)
        }
    }

    companion object {
        const val TAG = "ActionBottomDialog"
        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }
}
