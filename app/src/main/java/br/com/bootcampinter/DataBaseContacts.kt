package br.com.bootcampinter

import br.com.bootcampinter.contacthelpers.Contact

/**
 * Simula um banco de dados para testes
 */

class DataBaseContacts() {

    companion object{
        val dataBaseList: MutableList<Contact> = mutableListOf()
    }
}