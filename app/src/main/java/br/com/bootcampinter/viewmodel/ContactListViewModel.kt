package br.com.bootcampinter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.bootcampinter.contact.Contact

class ContactListViewModel: ViewModel() {

    val contactList = MutableLiveData<List<Contact>>()
}