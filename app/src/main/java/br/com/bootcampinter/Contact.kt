package br.com.bootcampinter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Classe que representa cada contato da lista
 *
 * Parcelize - torna a classe apata a trafegar entre activitys diferentes
 */
@Parcelize
data class Contact(
    val name: String,
    val phone: String,
    val photograph: Int = R.drawable.default_avatar
) : Parcelable