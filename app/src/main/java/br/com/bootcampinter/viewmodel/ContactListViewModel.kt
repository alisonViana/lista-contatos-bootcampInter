package br.com.bootcampinter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bootcampinter.application.ContactApplication
import br.com.bootcampinter.contact.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel: ViewModel() {

    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>>
    get() = _contactList

    fun getContactList(contactId: Int? = null){
        // Cria uma nova corrotina e move a execução para fora da Thread principal
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(300) // Tempo de simulação de requisição
            try {
                _contactList.postValue(ContactApplication.instance.helperDB?.searchContacts(contactId))
            }catch (ex: Exception) {ex.printStackTrace()}
        }
    }

    fun addContact(contact: Contact){
        // Cria uma nova corrotina e move a execução para fora da Thread principal
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ContactApplication.instance.helperDB?.newContact(contact)
            }catch (ex: Exception) {ex.printStackTrace()}
        }
    }

    fun editContact(contact: Contact) {
        // Cria uma nova corrotina e move a execução para fora da Thread principal
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ContactApplication.instance.helperDB?.editContact(contact)
            }catch (ex: Exception){ex.printStackTrace()}
        }
    }

    fun deleteContact(contactId: Int? = null){
        // Cria uma nova corrotina e move a execução para fora da Thread principal
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ContactApplication.instance.helperDB?.deleteContact(contactId)
            }catch (ex: Exception) {ex.printStackTrace()}
        }
    }

}