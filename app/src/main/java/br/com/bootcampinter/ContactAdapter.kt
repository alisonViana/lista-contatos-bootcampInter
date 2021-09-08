package br.com.bootcampinter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Classe responsável por gerenciar a lista de contatos como um todo
 */
class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>() {

    private val list: MutableList<Contact> = mutableListOf()

    /**
     * Método responsável por criar cada item visual na tela
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactAdapterViewHolder(view)
    }

    /**
     * Método responsável por percorrer cada item do array, obter o valor e preencher na tela
     *
     * ou seja, ler o item no array e popular no RecycleView
     */
    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    /**
     * Requisito da classe Adapter: quantos itens tem na lista
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Classe responsável pelo gerenciamento de cada item
     */
    class ContactAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contact: Contact) {

        }
    }
}