package com.dicoding.rotie.presentation.ui.common

import java.text.NumberFormat
import java.util.Locale

class Converter {
	companion object {
		fun rupiah(number: Int): String{
			val localeID =  Locale("in", "ID")
			val numberFormat = NumberFormat.getCurrencyInstance(localeID)
			return numberFormat.format(number).toString()
		}
	}
}