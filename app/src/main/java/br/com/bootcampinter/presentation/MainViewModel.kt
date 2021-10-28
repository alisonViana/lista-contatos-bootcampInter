package br.com.bootcampinter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.data.repositories.ContactRepository

class MainViewModel(private val contactRepository: ContactRepository): ViewModel() {

    private var filteredStatus: Boolean = false
    private var filteredList: List<Contact> = listOf()

    fun getContactList(): LiveData<List<Contact>> {
        return contactRepository.getAll()
    }

    fun setFilteredList(list: List<Contact>) {
        filteredList = list
        filteredStatus = true
    }

    fun cleanFilteredList() {
        filteredList = listOf()
        filteredStatus = false
    }

    fun getFilteredList(): List<Contact> = filteredList

    fun getFilteredStatus(): Boolean = filteredStatus

}