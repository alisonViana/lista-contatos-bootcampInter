package br.com.bootcampinter.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.bootcampinter.*
import br.com.bootcampinter.activitys.DetailActivity.Companion.EXTRA_CONTACT
import br.com.bootcampinter.application.ContactApplication
import br.com.bootcampinter.contact.Contact
import br.com.bootcampinter.contact.ContactAdapter
import br.com.bootcampinter.contact.ContactItemClickListener
import br.com.bootcampinter.database.DataBaseContacts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), ContactItemClickListener {
    /**
     * variável que recebe a RecyclerView
     */
    private val rvList: RecyclerView by lazy {
        findViewById(R.id.rv_list)
    }

    private val adapter = ContactAdapter(this)

    private var contactList: List<Contact> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        setNavigationViewListener()
        fetchListContact()
        bindViews()
        setSearchListeners()
        setOnClickNewContactListener()
    }

    /**
     * Inicializa o drawer menu
     */
    private fun initDrawer() {
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    /**
     * Função responsável pelo Listener dos cliques nas opções do drawer menu
     */
    private fun setNavigationViewListener() {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.item_menu_1 -> {
                    showToast("MENU 1")
                    true
                }
                R.id.item_menu_2 -> {
                    showToast("MENU 2")
                    true
                }
                else -> { false }
            }
        }
    }

    /**
     * Faz a busca dos contatos no banco de dados
     */
    private fun fetchListContact() {
        try {
            contactList = ContactApplication.instance.helperDB?.searchContacts() ?: mutableListOf()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)

        updateList(contactList)
    }

    private fun updateList(list: List<Contact>) {
        //val list = getListContacts()
        adapter.updateList(list)
    }

    /**
     * Métodos responsáveis pelo gerenciamento da barra de busca
     */
    private fun setSearchListeners() {
        val btnSearch = findViewById<ImageView>(R.id.btn_search)
        val etSearch = findViewById<EditText>(R.id.et_search)

        btnSearch.setOnClickListener {
            val searchText: String = etSearch.text.toString().lowercase()
            val filteredList = contactList.filter {
                it.name.lowercase().contains(searchText)
            }
            updateList(filteredList)
            if (filteredList.isEmpty()) showToast("Nenhum contato encontrado")
        }

        etSearch.doAfterTextChanged {
            if (etSearch.text.toString() == "") updateList(contactList)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Função responsável pela criação do Options Menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    /**
     * Função responsável pelo Listener de clique nas opções do options menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_1 -> {
                showToast("Selecionado o item 1")
                return true
            }
            R.id.item_menu_2 -> {
                showToast("Selecionado o item 2")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    /**
     * Função responsável por iniciar a activity com os detalhes do contato selecionado
     */
    override fun onClickItemContact(contact: Contact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }

    /**
     * Listener do Floating Button, quando pressionado inicializa a nova Activity com a categoria
     * NEW_CONTACT
     */
    private fun setOnClickNewContactListener() {
        val btnAddContact = findViewById<FloatingActionButton>(R.id.fbtn_add_contact)

        btnAddContact.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)

            intent.addCategory(EditActivity.NEW_CONTACT)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        fetchListContact()
        updateList(contactList)
    }
}
