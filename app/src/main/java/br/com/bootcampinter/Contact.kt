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
    var name: String,
    var phone: String,
    var photograph: Int = R.drawable.default_avatar
) : Parcelable