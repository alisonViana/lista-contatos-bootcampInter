package br.com.bootcampinter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.databinding.ContactItemBinding

/**
 * Classe responsável por gerenciar a lista de contatos
 */
class ContactListAdapter: ListAdapter<Contact, ContactListAdapter.ViewHolder>(DiffCallBack()) {

    var contactItemClickListener: (Contact) -> Unit = {}

    /**
     * Método responsável por criar cada item visual na tela
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    /**
     * Método responsável por percorrer cada item do array, obter o valor e preencher na tela
     *
     * ou seja, ler o item no array e popular no RecycleView
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Classe responsável pelo gerenciamento de cada item
     */
    inner class ViewHolder(
        private val binding: ContactItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Contact) {
            binding.tvName.text = item.name
            binding.tvPhone.text = item.phone
            binding.ivPhotograph.setImageResource(item.photograph)
            binding.cardContactItem.setOnClickListener {
                contactItemClickListener(item)
            }
        }

    }
}

class DiffCallBack: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean = oldItem.id == newItem.id
}
