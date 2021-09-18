package br.com.bootcampinter

/**
 * Simula um banco de dados para testes
 */
class DataBaseContacts(
    private val dataBaseList: MutableList<Contact> = mutableListOf()
) {
    fun addContacts(list: List<Contact>) {
        dataBaseList.addAll(list)
    }
    fun deleteContacts(list: List<Contact>) {
        dataBaseList.removeAll(list)
    }

    fun dbItems(): MutableList<Contact> {
        return dataBaseList
    }
}