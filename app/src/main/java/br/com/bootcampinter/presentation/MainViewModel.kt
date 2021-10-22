package br.com.bootcampinter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.data.repositories.ContactRepository

class MainViewModel(private val contactRepository: ContactRepository): ViewModel() {

    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>> = _contactList

    fun getContactList(contactId: Int? = null){
        _contactList.postValue(contactRepository.getAll())
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