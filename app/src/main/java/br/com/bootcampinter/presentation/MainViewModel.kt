package br.com.bootcampinter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.data.repositories.ContactRepository

class MainViewModel(private val contactRepository: ContactRepository): ViewModel() {

    fun getContactList(): LiveData<List<Contact>> {
        return contactRepository.getAll()
    }

    fun addContact(contact: Contact){
        contactRepository.insert(contact)
    }

    fun editContact(contact: Contact) {
        contactRepository.update(contact)
    }

    fun deleteContact(contact: Contact){
        contactRepository.delete(contact)
    }

}