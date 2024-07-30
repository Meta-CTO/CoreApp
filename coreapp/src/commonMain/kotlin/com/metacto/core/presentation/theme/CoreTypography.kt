package com.metacto.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Define core typography
@Immutable
data class CoreTypography(
    val primary: TextStyle = TextStyle(),
    val primaryBold: TextStyle = primary.copy(
        fontWeight = FontWeight.Bold
    ),
    val primarySemiBold: TextStyle = primary.copy(
        fontWeight = FontWeight.SemiBold
    ),
    val primaryMedium: TextStyle = primary.copy(
        fontWeight = FontWeight.Medium
    ),
    val primaryRegular: TextStyle = primary.copy(
        fontWeight = FontWeight.Normal
    ),
    val primaryLight: TextStyle = primary.copy(
        fontWeight = FontWeight.Light
    ),

    val headline: TextStyle = primaryBold.copy(
        fontSize = 24.sp
    ),
    val sheetTitle: TextStyle = primaryRegular.copy(
        fontSize = 16.sp
    ),

    val btnLabelMedium: TextStyle = primarySemiBold.copy(
        fontSize = 20.sp
    ),
    val btnLabelSmall: TextStyle = primarySemiBold.copy(
        fontSize = 16.sp
    ),

    val labelSmall: TextStyle = primaryMedium.copy(
        fontSize = 14.sp
    ),
    val labelMedium: TextStyle = primaryMedium.copy(
        fontSize = 16.sp
    ),
    val labelLarge: TextStyle = primaryMedium.copy(
        fontSize = 18.sp
    ),

    val bodyXXLarge: TextStyle = primaryMedium.copy(
        fontSize = 24.sp
    ),
    val bodyXLarge: TextStyle = primaryMedium.copy(
        fontSize = 20.sp
    ),
    val bodyLarge: TextStyle = primaryMedium.copy(
        fontSize = 16.sp
    ),
    val bodyMedium: TextStyle = primaryMedium.copy(
        fontSize = 14.sp
    ),
    val bodySmall: TextStyle = primaryMedium.copy(
        fontSize = 12.sp
    ),
    val snackBarMsg: TextStyle = primaryRegular.copy(
        fontSize = 16.sp
    ),
    val inputFieldText: TextStyle = primaryMedium.copy(
        fontSize = 18.sp
    ),
    val numberSelector: TextStyle = primaryBold.copy(
        fontSize = 20.sp
    ),
    val titleBold: TextStyle = primaryBold.copy(
        fontSize = 18.sp
    ),
    val pickerItem: TextStyle = primaryMedium.copy(
        fontSize = 20.sp
    ),

    // PrimaryFilledButton
    val primaryFilledBtnTextStyle: TextStyle = btnLabelMedium,

    // PrimaryCheckableButton
    val primaryCheckableBtnTextStyle: TextStyle = btnLabelMedium,

    // OnSecondaryTransparentFilledButton
    val onSecondaryTransparentFilledBtnTextStyle: TextStyle = btnLabelSmall,

    // FloatingButton
    val floatingBtnTextStyle: TextStyle = btnLabelMedium,

    // DangerFilledButton
    val dangerFilledBtnTextStyle: TextStyle = btnLabelMedium,

    // PrimaryStrokedButton
    val primaryStrokedBtnTextStyle: TextStyle = btnLabelMedium,

    // PrimaryTextButton
    val primaryTextBtnTextStyle: TextStyle = btnLabelSmall,

    // SecondaryFilledButton
    val secondaryFilledBtnTextStyle: TextStyle = btnLabelMedium,

    // SecondaryStrokedBtn
    val secondaryStrokedBtnTextStyle: TextStyle = btnLabelMedium,

    // SecondaryTextButton
    val secondaryTextBtnTextStyle: TextStyle = btnLabelSmall,

    // SocialButton
    val socialBtnTextStyle: TextStyle = btnLabelMedium,

    // TertiaryFilledButton
    val tertiaryFilledBtnTextStyle: TextStyle = btnLabelMedium,

    // TertiaryStrokedButton
    val tertiaryStrokedBtnTextStyle: TextStyle = btnLabelMedium,

    // TertiaryTextButton
    val tertiaryTextBtnTextStyle: TextStyle = btnLabelSmall,

    // TransparentStrokedButton
    val transparentStrokedBtn: TextStyle = btnLabelMedium,

    // ConfirmationDialog
    val confirmationDialogBodyTextStyle: TextStyle = bodyLarge,

    // DialogToolbar
    val dialogToolbarTextStyle: TextStyle = bodyMedium,

    // MessageDialog
    val messageDialogBodyTextStyle: TextStyle = bodyLarge,

    // OverrideUserDialog
    val overrideUserDialogBodyTextStyle: TextStyle = bodyLarge,

    // SuccessDialog
    val successDialogBodyTextStyle: TextStyle = bodyLarge,

    // InlineInputField
    val inlineInputFieldTextStyle: TextStyle = bodyMedium,

    // LinedOtpInputField
    val linedOtpInputFieldTextStyle: TextStyle = headline,

    // OutlinedOtpInputField
    val outlinedOtpInputFieldTextStyle: TextStyle = labelSmall,

    // PickerInputField
    val pickerInputFieldTextStyle: TextStyle = bodyMedium,
    val pickerInputFieldLabelTextStyle: TextStyle = labelMedium,
    val pickerInputFieldErrorTextStyle: TextStyle = labelMedium,
    val pickerInputFieldPlaceholderTextStyle: TextStyle = labelMedium,

    // PriceTextInputField
    val priceTextInputFieldTextStyle: TextStyle = bodyMedium,
    val priceTextInputFieldLabelTextStyle: TextStyle = labelMedium,
    val priceTextInputFieldErrorTextStyle: TextStyle = labelMedium,
    val priceTextInputFieldPlaceholderTextStyle: TextStyle = labelMedium,

    // PrimaryTextInputField
    val primaryTextInputFieldTextStyle: TextStyle = inputFieldText,
    val primaryTextInputFieldLabelTextStyle: TextStyle = labelMedium,
    val primaryTextInputFieldErrorTextStyle: TextStyle = labelMedium,
    val primaryTextInputFieldPlaceholderTextStyle: TextStyle = labelMedium,

    // TertiaryTextInputField
    val tertiaryTextInputFieldTextStyle: TextStyle = bodyMedium,
    val tertiaryTextInputFieldLabelTextStyle: TextStyle = labelMedium,
    val tertiaryTextInputFieldErrorTextStyle: TextStyle = labelMedium,
    val tertiaryTextInputFieldPlaceholderTextStyle: TextStyle = labelMedium,

    // NumberItem
    val numberItemTextStyle: TextStyle = numberSelector,

    // OptionItem
    val optionItemTextStyle: TextStyle = bodyLarge,

    // HorizontalPagerTabItem
    val horizontalPagerTabItemTextStyle: TextStyle = bodySmall,

    // AppSnackBar
    val appSnackBarTextStyle: TextStyle = snackBarMsg,

    // IconText
    val iconTextTextStyle: TextStyle = bodySmall,

    // SingleLineText
    val singleLineTextStyle: TextStyle = bodyMedium,

    // TextDivider
    val textDividerTextStyle: TextStyle = labelLarge,

    // WheelTextPicker
    val wheelTextPickerTextStyle: TextStyle = pickerItem,

    // ItemPickerContent
    val itemPickerWheelTextStyle: TextStyle = pickerItem,


)

val LocalCoreTypography = staticCompositionLocalOf { CoreTypography() }