package br.com.bootcampinter.database

import br.com.bootcampinter.contact.Contact

/**
 * Simula um banco de dados para testes
 */

class DataBaseContacts() {

    companion object{
        val dataBaseList: MutableList<Contact> = mutableListOf()
    }
}