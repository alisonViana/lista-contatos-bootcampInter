package br.com.bootcampinter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
     * Método responsável por trazer uma lista de uma classe externa para dentro do Adapter
     */
    fun updateList(list: List<Contact>) {
        this.list.clear()
        this.list.addAll(list)
        // Notifica o Adapter de que a lista utilizada foi atualizada
        // passando por todos os métodos para popular novamente a lista
        notifyDataSetChanged()
    }
    /**
     * Classe responsável pelo gerenciamento de cada item
     */
    class ContactAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val ivPhotograph: ImageView = itemView.findViewById(R.id.iv_photograph)

        fun bind(contact: Contact) {
            tvName.text = contact.name
            tvPhone.text = contact.phone
        }
    }
}