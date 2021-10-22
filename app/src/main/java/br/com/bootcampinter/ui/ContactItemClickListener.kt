package br.com.bootcampinter.ui

import br.com.bootcampinter.data.model.Contact

interface ContactItemClickListener {
    fun onClickItemContact(contact: Contact)
}