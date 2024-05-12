package com.example.todoapplication

import android.content.DialogInterface

interface DialogCloseListner {
    fun handleDialogClose(dialog: DialogInterface?)
}
