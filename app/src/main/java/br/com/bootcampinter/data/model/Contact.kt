package br.com.bootcampinter.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.bootcampinter.R
import kotlinx.android.parcel.Parcelize

/**
 * Classe que representa cada contato da lista
 *
 * Parcelize - torna a classe apata a trafegar entre activitys diferentes
 * Entity - torna a classe uma entidade do Room, ou seja, transforma cada atributo
 * em uma coluna da tabela
 */
@Entity
@Parcelize
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    var name: String,
    var phone: String,
    var photograph: Int = R.drawable.default_avatar
) : Parcelable