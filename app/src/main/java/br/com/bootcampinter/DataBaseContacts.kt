package br.com.bootcampinter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Simula um banco de dados para testes
 */

class DataBaseContacts() {

    companion object{
        val dataBaseList: MutableList<Contact> = mutableListOf()
    }
}