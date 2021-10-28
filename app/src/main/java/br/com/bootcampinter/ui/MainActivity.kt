package br.com.bootcampinter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.doAfterTextChanged
import br.com.bootcampinter.*
import br.com.bootcampinter.data.model.Contact
import br.com.bootcampinter.databinding.DrawerMenuBinding
import br.com.bootcampinter.presentation.MainViewModel
import br.com.bootcampinter.ui.DetailActivity.Companion.EXTRA_CONTACT
import br.com.bootcampinter.ui.EditActivity.Companion.NEW_CONTACT
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var contactList: List<Contact> = mutableListOf()

    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { ContactListAdapter()}
    private val binding by lazy { DrawerMenuBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activityMain.rvList.adapter = adapter

        setToolbar()
        setOnClickListeners()
        fetchListContact()

    }

    private fun setToolbar() {
        setSupportActionBar(binding.activityMain.toolbar)
        initDrawerMenu()
        setSearchListeners()
    }

    /**
     * Inicializa o drawer menu
     */
    private fun initDrawerMenu() {
        ActionBarDrawerToggle(this, binding.drawerLayout , binding.activityMain.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        ).apply {
            binding.drawerLayout.addDrawerListener(this)
            syncState()
        }

        // Listener dos cliques nas opções do drawer menu
        binding.navView.setNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.menu_about_app -> {
                    showToast("Em breve: Sobre o app")
                    true
                }
                R.id.menu_about_dev -> {
                    showToast("Em breve: Sobre o desenvolvedor")
                    true
                }
                else -> { false }
            }
        }
    }

    /**
     * Métodos responsáveis pelo gerenciamento da barra de busca
     */
    private fun setSearchListeners() {

        binding.activityMain.searchBar.btnSearch.setOnClickListener {
            val searchText: String = binding.activityMain.searchBar.etSearch.text.toString().lowercase()
            val filteredList = contactList.filter {
                it.name.lowercase().contains(searchText)
            }
            //TODO - updateList(filteredList)
            if (filteredList.isEmpty()) showToast("Nenhum contato encontrado")
            else showToast("Encontrado ${filteredList.size} contato(s)")
        }

        binding.activityMain.searchBar.etSearch.doAfterTextChanged {
            if (binding.activityMain.searchBar.etSearch.text.toString() == "") true // TODO - updateList(contactList)
        }
    }

    /**
     * Função responsável pela criação do Options Menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Função responsável pelo Listener de clique nas opções do options menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options_menu_sort_name -> {
                showToast("Em breve: ordena por nome")
                return true
            }
            R.id.options_menu_sort_id -> {
                showToast("Em breve: ordena por id")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Função responsável pelos Listeners do floatingButton de novo contato e
     * o de seleção do contato para exibição dos detalhes
     */
    private fun setOnClickListeners() {
        // Listener do Floating Button, quando pressionado inicializa a nova Activity com a categoria NEW_CONTACT
        binding.activityMain.fbtnAddContact.setOnClickListener {
            Intent(this, EditActivity::class.java).apply {
                addCategory(NEW_CONTACT)
                startActivity(this)
            }
        }

        // Responsável por iniciar a activity com os detalhes do contato selecionado
        adapter.contactItemClickListener = {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(EXTRA_CONTACT, it)
                startActivity(this)
            }
        }
    }

    /**
     * Faz a busca dos contatos no banco de dados
     * Toda consulta ao banco de dados deve ser feita dentro de Try-Catch
     * e fora da Thread principal
     * Cria um observer para a função que retorna um LiveData
     * quando houver mudanças na lista, ele atualiza o valor a recycleView
     * e atualiza as views
     */
    private fun fetchListContact() {
        binding.activityMain.progressBar.visibility = View.VISIBLE

        viewModel.getContactList().observe(this){ list ->
            adapter.submitList(list)
            binding.activityMain.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
