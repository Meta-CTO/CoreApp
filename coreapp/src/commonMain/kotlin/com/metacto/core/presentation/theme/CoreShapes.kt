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
    val itemPickerItem: RoundedCornerShape = RoundedCornerShape(0),
    val sheet: RoundedCornerShape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp
    ),

    // PrimaryFilledButton
    val primaryFilledBtnShapeSmall: RoundedCornerShape = xSmall,
    val primaryFilledBtnShapeNormal: RoundedCornerShape = small,

    // DangerFilledButton
    val dangerFilledButtonShapeSmall: RoundedCornerShape = xSmall,
    val dangerFilledButtonShapeNormal: RoundedCornerShape = small,

    // FloatingButton
    val floatingBtnShape: RoundedCornerShape = xxxLarge,

    // OnSecondaryTransparentFilledButton
    val onSecondaryTransparentFilledBtnShape: RoundedCornerShape = small,

    // PrimaryCheckableButton
    val primaryCheckableBtnShape: RoundedCornerShape = small,

    // PrimaryStrokedButton
    val primaryStrokedBtnShapeSmall: RoundedCornerShape = xSmall,
    val primaryStrokedBtnShapeNormal: RoundedCornerShape = small,

    // SecondaryFilledButton
    val secondaryFilledBtnShapeSmall: RoundedCornerShape = xSmall,
    val secondaryFilledBtnShapeNormal: RoundedCornerShape = small,

    // SecondaryStrokedButton
    val secondaryStrokedBtnShapeSmall: RoundedCornerShape = xSmall,
    val secondaryStrokedBtnShapeNormal: RoundedCornerShape = small,

    // SwitchButton
    val switchBtnShape: RoundedCornerShape = circle,

    // tertiaryFilledButton
    val tertiaryFilledBtnShapeSmall: RoundedCornerShape = xSmall,
    val tertiaryFilledBtnShapeNormal: RoundedCornerShape = small,

    // TertiaryStrokedButton
    val tertiaryStrokedBtnShapeSmall: RoundedCornerShape = xSmall,
    val tertiaryStrokedBtnShapeNormal: RoundedCornerShape = small,

    // AppDialog
    val appDialogShape: RoundedCornerShape = xLarge,

    // OtpDigit
    val otpDigitShape: RoundedCornerShape = xLarge,

    // PickerInputField
    val pickerInputFieldShape: RoundedCornerShape = small,

    // PriceTextInputField
    val priceInputFieldBgShape: RoundedCornerShape = small,

    // PrimaryTextInputField
    val primaryInputFieldShape: RoundedCornerShape = small,

    // TertiaryTextInputField
    val tertiaryInputFieldShape: RoundedCornerShape = small,

    // NumberItem
    val numberItemShape: RoundedCornerShape = circle,

    // Dot
    val dotShape: RoundedCornerShape = circle,

    // HorizontalPagerIndicator
    val horizontalPagerIndicatorShape: RoundedCornerShape = circle,

    // HorizontalPagerTabItem
    val horizontalPagerTabItemShape: RoundedCornerShape = medium,

    // SecondaryStepBar
    val secondaryStepBarProgressShape: RoundedCornerShape = large,

    // ItemPicker
    val itemPickerShape: RoundedCornerShape = itemPickerItem,

    // SelectorProperties
    val selectorPropertiesShape: RoundedCornerShape = wheelPickerItem,
)

val LocalCoreShapes = staticCompositionLocalOf { CoreShapes() }