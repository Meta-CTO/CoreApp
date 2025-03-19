package com.metacto.core.utils.extensions

import kotlinx.datetime.LocalDateTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents

fun LocalDateTime.toNSDateComponent(): NSDateComponents {
				val components = NSDateComponents()
				components.year = year.toLong()
				components.month = this.month.ordinal.toLong()
				components.day = this.dayOfMonth.toLong()
				components.hour = this.hour.toLong()
				components.minute = this.minute.toLong()
				components.second = this.second.toLong()
				components.calendar = NSCalendar.currentCalendar()
				return components
}