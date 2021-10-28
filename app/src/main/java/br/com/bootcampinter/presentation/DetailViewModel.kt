package br.com.bootcampinter.presentation

import androidx.lifecycle.ViewModel
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.data.repositories.ContactRepository

class DetailViewModel(private val contactRepository: ContactRepository): ViewModel() {

    private var extrasStatus: Boolean = true
    private var contactHolder: Contact = Contact(-1, "Contato", "000000000")

    fun deleteContact(contact: Contact){
        contactRepository.delete(contact)
    }

    fun setContact(contact: Contact) {
        contactHolder = contact
        extrasStatus = false
    }

    fun getContact(): Contact = contactHolder

    fun initExtras(): Boolean = extrasStatus

}