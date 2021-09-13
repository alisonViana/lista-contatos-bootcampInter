package br.com.bootcampinter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {

    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_detail)

        initToolbar()
        getExtras()
        bindView()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getExtras() {
        contact = intent.getParcelableExtra(EXTRA_CONTACT)
    }

    private fun bindView() {
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvPhone = findViewById<TextView>(R.id.tv_phone)
        val ivPhoto = findViewById<ImageView>(R.id.iv_photograph)

        tvName.text = contact?.name
        tvPhone.text = contact?.phone
        ivPhoto.setImageResource(contact!!.photograph)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_CONTACT: String = "EXTRA_CONTACT"
    }
}