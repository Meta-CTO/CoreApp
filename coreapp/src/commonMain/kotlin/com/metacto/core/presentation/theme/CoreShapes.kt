package com.metacto.core.presentation.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class CoreShapes(
    val xSmall: RoundedCornerShape = RoundedCornerShape(8.dp),
    val small: RoundedCornerShape = RoundedCornerShape(10.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(14.dp),
    val xLarge: RoundedCornerShape = RoundedCornerShape(16.dp),
    val xxLarge: RoundedCornerShape = RoundedCornerShape(20.dp),
    val xxxLarge: RoundedCornerShape = RoundedCornerShape(24.dp),
    val circle: RoundedCornerShape = CircleShape,
    val wheelPickerItem: RoundedCornerShape = RoundedCornerShape(16.dp),
    val iosWheelPickerItem: RoundedCornerShape = RoundedCornerShape(0),
    val itemPickerItem: RoundedCornerShape = RoundedCornerShape(0),
    val sheet: RoundedCornerShape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp
    ),

    // PrimaryFilledButton
    val primaryFilledButton: PrimaryFilledButtonShapes = PrimaryFilledButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // Social Button
    val socialButton: SocialButtonShapes = SocialButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // TransparentStrokedButton
    val transparentStrokedButton: TransparentStrokedButtonShapes = TransparentStrokedButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // DangerFilledButton
    val dangerFilledButton: DangerFilledButtonShapes = DangerFilledButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // FloatingButton
    val floatingButton: FloatingButtonShapes = FloatingButtonShapes(
        xxxLarge = xxxLarge
    ),

    // OnSecondaryTransparentFilledButton
    val onSecondaryTransparentFilledButton: OnSecondaryTransparentFilledButtonShapes = OnSecondaryTransparentFilledButtonShapes(
        small = small
    ),

    // PrimaryCheckableButton
    val primaryCheckableButton: PrimaryCheckableButtonShapes = PrimaryCheckableButtonShapes(
        small = small
    ),

    // PrimaryStrokedButton
    val primaryStrokedButton: PrimaryStrokedButtonShapes = PrimaryStrokedButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // SecondaryFilledButton
    val secondaryFilledButton: SecondaryFilledButtonShapes = SecondaryFilledButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // SecondaryStrokedButton
    val secondaryStrokedButton: SecondaryStrokedButtonShapes = SecondaryStrokedButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // SwitchButton
    val switchButton: SwitchButtonShapes = SwitchButtonShapes(
        circle = circle
    ),

    // TertiaryFilledButton
    val tertiaryFilledButton: TertiaryFilledButtonShapes = TertiaryFilledButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // TertiaryStrokedButton
    val tertiaryStrokedButton: TertiaryStrokedButtonShapes = TertiaryStrokedButtonShapes(
        xSmall = xSmall,
        small = small
    ),

    // AppDialog
    val appDialog: AppDialogShapes = AppDialogShapes(
        xLarge = xLarge
    ),

    // OtpDigit
    val otpDigit: OtpDigitShapes = OtpDigitShapes(
        xLarge = xLarge
    ),

    // PickerInputField
    val pickerInputField: PickerInputFieldShapes = PickerInputFieldShapes(
        small = small
    ),

    // PriceTextInputField
    val priceTextInputField: PriceTextInputFieldShapes = PriceTextInputFieldShapes(
        small = small
    ),

    // PrimaryTextInputField
    val primaryTextInputField: PrimaryTextInputFieldShapes = PrimaryTextInputFieldShapes(
        small = small
    ),

    // SecondaryTextInputField
    val secondaryTextInputField: SecondaryTextInputFieldShapes = SecondaryTextInputFieldShapes(
        small = small
    ),

    // TertiaryTextInputField
    val tertiaryTextInputField: TertiaryTextInputFieldShapes = TertiaryTextInputFieldShapes(
        small = small
    ),

    // PasswordTextInputField
    val passwordTextInputField: PasswordTextInputFieldShapes = PasswordTextInputFieldShapes(
        small = small
    ),

    // NumberItem
    val numberItem: NumberItemShapes = NumberItemShapes(
        circle = circle
    ),

    // Dot
    val dot: DotShapes = DotShapes(
        circle = circle
    ),

    // HorizontalPagerIndicator
    val horizontalPagerIndicator: HorizontalPagerIndicatorShapes = HorizontalPagerIndicatorShapes(
        circle = circle
    ),

    // HorizontalPagerTabItem
    val horizontalPagerTabItem: HorizontalPagerTabItemShapes = HorizontalPagerTabItemShapes(
        medium = medium
    ),

    // SecondaryStepBar
    val secondaryStepBar: SecondaryStepBarShapes = SecondaryStepBarShapes(
        large = large
    ),

    // ItemPicker
    val itemPicker: ItemPickerShapes = ItemPickerShapes(
        itemPickerItem = itemPickerItem
    ),

    // SelectorProperties
    val selectorProperties: SelectorPropertiesShapes = SelectorPropertiesShapes(
        wheelPickerItem = wheelPickerItem
    ),

    // WheelDatePicker
    val wheelDatePicker: WheelDatePickerShapes = WheelDatePickerShapes(
        xSmall = xSmall
    )
)

// PrimaryFilledButton
data class PrimaryFilledButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// Social Button
data class SocialButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// TransparentStrokedButton
data class TransparentStrokedButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// DangerFilledButton
data class DangerFilledButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// FloatingButton
data class FloatingButtonShapes(
    private val xxxLarge: RoundedCornerShape,
    val shape: RoundedCornerShape = xxxLarge
)

// OnSecondaryTransparentFilledButton
data class OnSecondaryTransparentFilledButtonShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// PrimaryCheckableButton
data class PrimaryCheckableButtonShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// PrimaryStrokedButton
data class PrimaryStrokedButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// SecondaryFilledButton
data class SecondaryFilledButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// SecondaryStrokedButton
data class SecondaryStrokedButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// SwitchButton
data class SwitchButtonShapes(
    private val circle: RoundedCornerShape,
    val shape: RoundedCornerShape = circle
)

// tertiaryFilledButton
data class TertiaryFilledButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// TertiaryStrokedButton
data class TertiaryStrokedButtonShapes(
    private val xSmall: RoundedCornerShape,
    private val small: RoundedCornerShape,
    val shapeSmall: RoundedCornerShape = xSmall,
    val shapeNormal: RoundedCornerShape = small
)

// AppDialog
data class AppDialogShapes(
    private val xLarge: RoundedCornerShape,
    val shape: RoundedCornerShape = xLarge
)

// OtpDigit
data class OtpDigitShapes(
    private val xLarge: RoundedCornerShape,
    val shape: RoundedCornerShape = xLarge
)

// PickerInputField
data class PickerInputFieldShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// PriceTextInputField
data class PriceTextInputFieldShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// PrimaryTextInputField
data class PrimaryTextInputFieldShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// SecondaryTextInputField
data class SecondaryTextInputFieldShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// TertiaryTextInputField
data class TertiaryTextInputFieldShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// PasswordTextInputField
data class PasswordTextInputFieldShapes(
    private val small: RoundedCornerShape,
    val shape: RoundedCornerShape = small
)

// NumberItem
data class NumberItemShapes(
    private val circle: RoundedCornerShape,
    val shape: RoundedCornerShape = circle
)

// Dot
data class DotShapes(
    private val circle: RoundedCornerShape,
    val shape: RoundedCornerShape = circle
)

// HorizontalPagerIndicator
data class HorizontalPagerIndicatorShapes(
    private val circle: RoundedCornerShape,
    val indicatorShape: RoundedCornerShape = circle
)

// HorizontalPagerTabItem
data class HorizontalPagerTabItemShapes(
    private val medium: RoundedCornerShape,
    val shape: RoundedCornerShape = medium
)

// SecondaryStepBar
data class SecondaryStepBarShapes(
    private val large: RoundedCornerShape,
    val progressShape: RoundedCornerShape = large
)

// ItemPicker
data class ItemPickerShapes(
    private val itemPickerItem: RoundedCornerShape,
    val selectorShape: RoundedCornerShape = itemPickerItem
)

// SelectorProperties
data class SelectorPropertiesShapes(
    private val wheelPickerItem: RoundedCornerShape,
    val shape: RoundedCornerShape = wheelPickerItem
)

// WheelDatePicker
data class WheelDatePickerShapes(
    private val xSmall: RoundedCornerShape,
    val selectorShape: RoundedCornerShape = xSmall
)

val LocalCoreShapes = staticCompositionLocalOf { CoreShapes() }