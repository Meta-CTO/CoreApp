@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.metacto.core.ui.components.pickers

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.readValue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitSecond
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSLocale
import platform.Foundation.NSSelectorFromString
import platform.Foundation.NSTimeZone
import platform.Foundation.defaultTimeZone
import platform.UIKit.UIColor
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIView
import platform.UIKit.labelColor
import platform.UIKit.systemBackgroundColor

/**
 * Date picker mode wrapper for better Kotlin DSL
 */
enum class DatePickerDisplayMode {
    DATE_ONLY,
    TIME_ONLY,
    DATE_AND_TIME,
    COUNTDOWN_TIMER;

    fun toUIDatePickerMode(): UIDatePickerMode = when (this) {
        DATE_ONLY -> UIDatePickerMode.UIDatePickerModeDate
        TIME_ONLY -> UIDatePickerMode.UIDatePickerModeTime
        DATE_AND_TIME -> UIDatePickerMode.UIDatePickerModeDateAndTime
        COUNTDOWN_TIMER -> UIDatePickerMode.UIDatePickerModeCountDownTimer
    }
}

/**
 * Date picker style wrapper
 */
enum class DatePickerUIStyle {
    AUTOMATIC,
    WHEELS,
    COMPACT,
    INLINE;

    fun toUIDatePickerStyle(): UIDatePickerStyle = when (this) {
        AUTOMATIC -> UIDatePickerStyle.UIDatePickerStyleAutomatic
        WHEELS -> UIDatePickerStyle.UIDatePickerStyleWheels
        COMPACT -> UIDatePickerStyle.UIDatePickerStyleCompact
        INLINE -> UIDatePickerStyle.UIDatePickerStyleInline
    }
}

/**
 * Customization options for NativeDatePicker
 */
data class DatePickerStyle(
    // Colors
    val backgroundColor: UIColor = UIColor.systemBackgroundColor,
    val textColor: UIColor = UIColor.labelColor,
    val tintColor: UIColor? = null,

    // Style
    val pickerStyle: DatePickerUIStyle = DatePickerUIStyle.WHEELS,
    val pickerMode: DatePickerDisplayMode = DatePickerDisplayMode.DATE_ONLY,

    // Locale and timezone
    val locale: NSLocale? = null, // null = US English (en_US)
    val timeZone: NSTimeZone? = null, // null = system default
    val calendar: NSCalendar? = null, // null = system default

    // Interface style (light/dark mode)
    val interfaceStyle: UIUserInterfaceStyle = UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified,

    // Layout
    val cornerRadius: Double = 0.0,
    val borderWidth: Double = 0.0,
    val borderColor: UIColor? = null,

    // Constraints
    val minDate: LocalDate? = null,
    val maxDate: LocalDate? = null,

    // Animation
    val animateChanges: Boolean = true,

    // Minute interval (for time-based pickers, 1-30)
    val minuteInterval: Long = 1
)

/**
 * Callback interface for date picker events
 */
interface DatePickerCallback {
    fun onDateChanged(date: LocalDate)
    fun onTimeChanged(time: LocalTime) {}
    fun onDateTimeChanged(dateTime: LocalDateTime) {}
}

/**
 * Native iOS date picker implementation with enhanced customization
 */
class NativeDatePicker(
    private val initialDate: LocalDate = LocalDate(2024, 1, 1),
    private val style: DatePickerStyle = DatePickerStyle(),
    private val callback: DatePickerCallback
) : UIView(frame = CGRectZero.readValue()) {

    // Alternative constructor for simple date-only callback
    constructor(
        initialDate: LocalDate = LocalDate(2024, 1, 1),
        style: DatePickerStyle = DatePickerStyle(),
        onDateChanged: (LocalDate) -> Unit
    ) : this(
        initialDate = initialDate,
        style = style,
        callback = object : DatePickerCallback {
            override fun onDateChanged(date: LocalDate) = onDateChanged(date)
        }
    )

    private val datePicker by lazy {
        UIDatePicker().apply {
            translatesAutoresizingMaskIntoConstraints = false
            configurePickerStyle()
            configurePickerDates()
            configurePickerAppearance()
            addDateChangeListener()
        }
    }

    init {
        setupDatePickerView()
        applyContainerStyling()
    }

    private fun setupDatePickerView() {
        backgroundColor = style.backgroundColor
        addSubview(datePicker)

        // Setup constraints
        datePicker.let {
            it.topAnchor.constraintEqualToAnchor(topAnchor).active = true
            it.leftAnchor.constraintEqualToAnchor(leftAnchor).active = true
            it.rightAnchor.constraintEqualToAnchor(rightAnchor).active = true
            it.bottomAnchor.constraintEqualToAnchor(bottomAnchor).active = true
        }
    }

    private fun UIDatePicker.configurePickerStyle() {
        // Set picker style and mode
        preferredDatePickerStyle = style.pickerStyle.toUIDatePickerStyle()
        datePickerMode = style.pickerMode.toUIDatePickerMode()

        // Set locale, timezone, and calendar (use US locale as default)
        locale = style.locale ?: NSLocale("en_US")
        timeZone = style.timeZone ?: NSTimeZone.defaultTimeZone
        calendar = style.calendar ?: NSCalendar.currentCalendar

        // Set minute interval for time pickers (1-30)
        minuteInterval = style.minuteInterval.coerceIn(1, 30)
    }

    private fun UIDatePicker.configurePickerDates() {
        val calendar = this.calendar ?: NSCalendar.currentCalendar

        // Set initial date
        date = initialDate.toNSDate(calendar)

        // Set min/max dates if provided
        style.minDate?.let {
            minimumDate = it.toNSDate(calendar)
        }

        style.maxDate?.let {
            maximumDate = it.toNSDate(calendar)
        }
    }

    private fun UIDatePicker.configurePickerAppearance() {
        // Set background color
        backgroundColor = style.backgroundColor

        // Set tint color (affects selected components and text color)
        style.tintColor?.let {
            tintColor = it
        }

        // Set interface style (light/dark mode)
        if (style.interfaceStyle != UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified) {
            overrideUserInterfaceStyle = style.interfaceStyle
        }
    }

    private fun applyContainerStyling() {
        // Apply corner radius
        if (style.cornerRadius > 0.0) {
            layer.cornerRadius = style.cornerRadius
            clipsToBounds = true
        }

        // Apply border
        if (style.borderWidth > 0.0) {
            layer.borderWidth = style.borderWidth
            style.borderColor?.let {
                layer.borderColor = it.CGColor
            }
        }
    }

    private fun UIDatePicker.addDateChangeListener() {
        addTarget(
            target = this@NativeDatePicker,
            action = NSSelectorFromString("datePickerValueChanged"),
            forControlEvents = UIControlEventValueChanged
        )
    }

    @ObjCAction
    fun datePickerValueChanged() {
        val calendar = datePicker.calendar ?: NSCalendar.currentCalendar

        when (style.pickerMode) {
            DatePickerDisplayMode.DATE_ONLY -> {
                val date = datePicker.date.toLocalDate(calendar)
                callback.onDateChanged(date)
            }
            DatePickerDisplayMode.TIME_ONLY -> {
                val time = datePicker.date.toLocalTime(calendar)
                callback.onTimeChanged(time)
            }
            DatePickerDisplayMode.DATE_AND_TIME -> {
                val dateTime = datePicker.date.toLocalDateTime(calendar)
                callback.onDateTimeChanged(dateTime)
            }
            DatePickerDisplayMode.COUNTDOWN_TIMER -> {
                // For countdown timer, countDownDuration is in seconds
                // You can add a specific callback for this if needed
            }
        }
    }
}

// Extension functions for date/time conversion

private fun LocalDate.toNSDate(calendar: NSCalendar): NSDate {
    val components = NSDateComponents().apply {
        year = this@toNSDate.year.toLong()
        month = this@toNSDate.monthNumber.toLong()
        day = this@toNSDate.dayOfMonth.toLong()
    }
    return calendar.dateFromComponents(components) ?: NSDate()
}

private fun NSDate.toLocalDate(calendar: NSCalendar): LocalDate {
    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        this
    )
    return LocalDate(
        year = components.year.toInt(),
        monthNumber = components.month.toInt(),
        dayOfMonth = components.day.toInt()
    )
}

private fun NSDate.toLocalTime(calendar: NSCalendar): LocalTime {
    val components = calendar.components(
        NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond,
        this
    )
    return LocalTime(
        hour = components.hour.toInt(),
        minute = components.minute.toInt(),
        second = components.second.toInt()
    )
}

private fun NSDate.toLocalDateTime(calendar: NSCalendar): LocalDateTime {
    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay or
        NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond,
        this
    )
    return LocalDateTime(
        year = components.year.toInt(),
        monthNumber = components.month.toInt(),
        dayOfMonth = components.day.toInt(),
        hour = components.hour.toInt(),
        minute = components.minute.toInt(),
        second = components.second.toInt()
    )
}
