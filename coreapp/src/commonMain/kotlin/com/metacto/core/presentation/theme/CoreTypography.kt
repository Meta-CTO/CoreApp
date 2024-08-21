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
    val primaryFilledButton: PrimaryFilledButtonTypography = PrimaryFilledButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // PrimaryCheckableButton
    val primaryCheckableButton: PrimaryCheckableButtonTypography = PrimaryCheckableButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // OnSecondaryTransparentFilledButton
    val onSecondaryTransparentFilledButton: OnSecondaryTransparentFilledButtonTypography = OnSecondaryTransparentFilledButtonTypography(
        btnLabelSmall = btnLabelSmall
    ),

    // FloatingButton
    val floatingButton: FloatingButtonTypography = FloatingButtonTypography(
        btnLabelMedium = btnLabelMedium,
    ),

    // DangerFilledButton
    val dangerFilledButton: DangerFilledButtonTypography = DangerFilledButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // PrimaryStrokedButton
    val primaryStrokedButton: PrimaryStrokedButtonTypography = PrimaryStrokedButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // PrimaryTextButton
    val primaryTextButton: PrimaryTextButtonTypography = PrimaryTextButtonTypography(
        btnLabelSmall = btnLabelSmall
    ),

    // SecondaryFilledButton
    val secondaryFilledButton: SecondaryFilledButtonTypography = SecondaryFilledButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // SecondaryStrokedBtn
    val secondaryStrokedBtn: SecondaryStrokedBtnTypography = SecondaryStrokedBtnTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // SecondaryTextButton
    val secondaryTextButton: SecondaryTextButtonTypography = SecondaryTextButtonTypography(
        btnLabelSmall = btnLabelSmall
    ),

    // SocialButton
    val socialButton: SocialButtonTypography = SocialButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // TertiaryFilledButton
    val tertiaryFilledButton: TertiaryFilledButtonTypography = TertiaryFilledButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // TertiaryStrokedButton
    val tertiaryStrokedButton: TertiaryStrokedButtonTypography = TertiaryStrokedButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // TertiaryTextButton
    val tertiaryTextButton: TertiaryTextButtonTypography = TertiaryTextButtonTypography(
        btnLabelSmall = btnLabelSmall
    ),

    // TransparentStrokedButton
    val transparentStrokedButton: TransparentStrokedButtonTypography = TransparentStrokedButtonTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // ConfirmationDialog
    val confirmationDialog: ConfirmationDialogTypography = ConfirmationDialogTypography(
        bodyLarge = bodyLarge,
        btnLabelMedium = btnLabelMedium
    ),

    // forceUpdateDialog
    val forceUpdateDialog: ForceUpdateDialogTypography = ForceUpdateDialogTypography(
        bodyLarge = bodyLarge,
        btnLabelMedium = btnLabelMedium
    ),

    // DialogToolbar
    val dialogToolbar: DialogToolbarTypography = DialogToolbarTypography(
        bodyMedium = bodyMedium
    ),

    // MessageDialog
    val messageDialog: MessageDialogTypography = MessageDialogTypography(
        btnLabelMedium = btnLabelMedium,
        bodyLarge = bodyLarge
    ),

    // OverrideUserDialog
    val overrideUserDialog: OverrideUserDialogTypography = OverrideUserDialogTypography(
        btnLabelMedium = btnLabelMedium,
        bodyLarge = bodyLarge
    ),

    // SuccessDialog
    val successDialog: SuccessDialogTypography = SuccessDialogTypography(
        bodyLarge = bodyLarge,
        btnLabelMedium = btnLabelMedium,
    ),

    // InlineInputField
    val inlineInputField: InlineInputFieldTypography = InlineInputFieldTypography(
        bodyMedium = bodyMedium
    ),

    // LinedOtpInputField
    val linedOtpInputField: LinedOtpInputFieldTypography = LinedOtpInputFieldTypography(
        headline = headline
    ),

    // OutlinedOtpInputField
    val outlinedOtpInputField: OutlinedOtpInputFieldTypography = OutlinedOtpInputFieldTypography(
        labelSmall = labelSmall
    ),

    // PickerInputField
    val pickerInputField: PickerInputFieldTypography = PickerInputFieldTypography(
        bodyMedium = bodyMedium,
        labelMedium = labelMedium
    ),

    // PriceTextInputField
    val priceTextInputField: PriceTextInputFieldTypography = PriceTextInputFieldTypography(
        bodyMedium = bodyMedium,
        labelMedium = labelMedium
    ),

    // PrimaryTextInputField
    val primaryTextInputField: PrimaryTextInputFieldTypography = PrimaryTextInputFieldTypography(
        inputFieldText = inputFieldText,
        labelMedium = labelMedium
    ),

    // TertiaryTextInputField
    val tertiaryTextInputField: TertiaryTextInputFieldTypography = TertiaryTextInputFieldTypography(
        bodyMedium = bodyMedium,
        labelMedium = labelMedium
    ),

    // NumberItem
    val numberItem: NumberItemTypography = NumberItemTypography(
        numberSelector = numberSelector
    ),

    // OptionItem
    val optionItem: OptionItemTypography = OptionItemTypography(
        bodyLarge = bodyLarge
    ),

    // HorizontalPagerTabItem
    val horizontalPagerTabItem: HorizontalPagerTabItemTypography = HorizontalPagerTabItemTypography(
        bodySmall = bodySmall
    ),

    // AppSnackBar
    val appSnackBar: AppSnackBarTypography = AppSnackBarTypography(
        snackBarMsg = snackBarMsg
    ),

    // IconText
    val iconText: IconTextTypography = IconTextTypography(
        bodySmall = bodySmall
    ),

    // SingleLineText
    val singleLineText: SingleLineTextTypography = SingleLineTextTypography(
        bodyMedium = bodyMedium
    ),

    // TextDivider
    val textDivider: TextDividerTypography = TextDividerTypography(
        labelLarge = labelLarge
    ),

    // WheelTextPicker
    val wheelTextPicker: WheelTextPickerTypography = WheelTextPickerTypography(
        pickerItem = pickerItem
    ),

    // ItemPicker
    val itemPicker: ItemPickerTypography = ItemPickerTypography(
        pickerItem = pickerItem
    ),

    // Toolbar
    val toolbar: ToolbarTypography = ToolbarTypography(
        titleBold = titleBold
    ),

    // TabItem
    val tabItem: TabItemTypography = TabItemTypography(
        primaryBold = primaryBold.copy(
            fontSize = 14.sp
        )
    ),

    // ChoicesDialog
    val choicesDialog: ChoicesDialogTypography = ChoicesDialogTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // TimePickerDialog
    val timePickerDialog: TimePickerDialogTypography = TimePickerDialogTypography(
        btnLabelMedium = btnLabelMedium
    ),

    // DatePickerDialog
    val datePickerDialog: DatePickerDialogTypography = DatePickerDialogTypography(
        btnLabelMedium = btnLabelMedium
    )
)

// PrimaryFilledButton
data class PrimaryFilledButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// PrimaryCheckableButton
data class PrimaryCheckableButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// OnSecondaryTransparentFilledButton
data class OnSecondaryTransparentFilledButtonTypography(
    private val btnLabelSmall: TextStyle,
    val textStyle: TextStyle = btnLabelSmall
)

// FloatingButton
data class FloatingButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// DangerFilledButton
data class DangerFilledButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// PrimaryStrokedButton
data class PrimaryStrokedButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// PrimaryTextButton
data class PrimaryTextButtonTypography(
    private val btnLabelSmall: TextStyle,
    val textStyle: TextStyle = btnLabelSmall
)

// SecondaryFilledButton
data class SecondaryFilledButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// SecondaryStrokedBtn
data class SecondaryStrokedBtnTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// SecondaryTextButton
data class SecondaryTextButtonTypography(
    private val btnLabelSmall: TextStyle,
    val textStyle: TextStyle = btnLabelSmall
)

// SocialButton
data class SocialButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// TertiaryFilledButton
data class TertiaryFilledButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// TertiaryStrokedButton
data class TertiaryStrokedButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// TertiaryTextButton
data class TertiaryTextButtonTypography(
    private val btnLabelSmall: TextStyle,
    val textStyle: TextStyle = btnLabelSmall
)

// TransparentStrokedButton
data class TransparentStrokedButtonTypography(
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = btnLabelMedium
)

// ConfirmationDialog
data class ConfirmationDialogTypography(
    private val bodyLarge: TextStyle,
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = bodyLarge,
    val positiveBtnTextStyle: TextStyle = btnLabelMedium,
    val negativeBtnTextStyle: TextStyle = btnLabelMedium
)

// forceUpdateDialog
data class ForceUpdateDialogTypography(
    private val bodyLarge: TextStyle,
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = bodyLarge,
    val positiveBtnTextStyle: TextStyle = btnLabelMedium,
    val negativeBtnTextStyle: TextStyle = btnLabelMedium
)

// DialogToolbar
data class DialogToolbarTypography(
    private val bodyMedium: TextStyle,
    val textStyle: TextStyle = bodyMedium
)

// MessageDialog
data class MessageDialogTypography(
    private val bodyLarge: TextStyle,
    private val btnLabelMedium: TextStyle,
    val textStyle: TextStyle = bodyLarge,
    val btnTextStyle: TextStyle = btnLabelMedium
)

// OverrideUserDialog
data class OverrideUserDialogTypography(
    private val bodyLarge: TextStyle,
    private val btnLabelMedium: TextStyle,
    val bodyTextStyle: TextStyle = bodyLarge,
    val overrideTextStyle: TextStyle = btnLabelMedium,
    val resetTextStyle: TextStyle = btnLabelMedium
)

// SuccessDialog
data class SuccessDialogTypography(
    private val bodyLarge: TextStyle,
    private val btnLabelMedium: TextStyle,
    val bodyTextStyle: TextStyle = bodyLarge,
    val btnTextStyle: TextStyle = btnLabelMedium
)

// InlineInputField
data class InlineInputFieldTypography(
    private val bodyMedium: TextStyle,
    val textStyle: TextStyle = bodyMedium
)

// LinedOtpInputField
data class LinedOtpInputFieldTypography(
    private val headline: TextStyle,
    val textStyle: TextStyle = headline
)

// OutlinedOtpInputField
data class OutlinedOtpInputFieldTypography(
    private val labelSmall: TextStyle,
    val textStyle: TextStyle = labelSmall
)

// PickerInputField
data class PickerInputFieldTypography(
    private val bodyMedium: TextStyle,
    private val labelMedium: TextStyle,
    val textStyle: TextStyle = bodyMedium,
    val labelTextStyle: TextStyle = labelMedium,
    val errorTextStyle: TextStyle = labelMedium,
    val placeholderTextStyle: TextStyle = labelMedium
)

// PriceTextInputField
data class PriceTextInputFieldTypography(
    private val bodyMedium: TextStyle,
    private val labelMedium: TextStyle,
    val textStyle: TextStyle = bodyMedium,
    val labelTextStyle: TextStyle = labelMedium,
    val errorTextStyle: TextStyle = labelMedium,
    val placeholderTextStyle: TextStyle = labelMedium
)

// PrimaryTextInputField
data class PrimaryTextInputFieldTypography(
    private val inputFieldText: TextStyle,
    private val labelMedium: TextStyle,
    val textStyle: TextStyle = inputFieldText,
    val labelTextStyle: TextStyle = labelMedium,
    val errorTextStyle: TextStyle = labelMedium,
    val placeholderTextStyle: TextStyle = labelMedium
)

// TertiaryTextInputField
data class TertiaryTextInputFieldTypography(
    private val bodyMedium: TextStyle,
    private val labelMedium: TextStyle,
    val textStyle: TextStyle = bodyMedium,
    val labelTextStyle: TextStyle = labelMedium,
    val errorTextStyle: TextStyle = labelMedium,
    val placeholderTextStyle: TextStyle = labelMedium
)

// NumberItem
data class NumberItemTypography(
    private val numberSelector: TextStyle,
    val textStyle: TextStyle = numberSelector
)

// OptionItem
data class OptionItemTypography(
    private val bodyLarge: TextStyle,
    val textStyle: TextStyle = bodyLarge
)

// HorizontalPagerTabItem
data class HorizontalPagerTabItemTypography(
    private val bodySmall: TextStyle,
    val textStyle: TextStyle = bodySmall
)

// AppSnackBar
data class AppSnackBarTypography(
    private val snackBarMsg: TextStyle,
    val textStyle: TextStyle = snackBarMsg
)

// IconText
data class IconTextTypography(
    private val bodySmall: TextStyle,
    val textStyle: TextStyle = bodySmall
)

// SingleLineText
data class SingleLineTextTypography(
    val bodyMedium: TextStyle,
    val textStyle: TextStyle = bodyMedium
)

// TextDivider
data class TextDividerTypography(
    private val labelLarge: TextStyle,
    val textStyle: TextStyle = labelLarge
)

// WheelTextPicker
data class WheelTextPickerTypography(
    private val pickerItem: TextStyle,
    val textStyle: TextStyle = pickerItem
)

// ItemPicker
data class ItemPickerTypography(
    private val pickerItem: TextStyle,
    val textStyle: TextStyle = pickerItem
)

// Toolbar
data class ToolbarTypography(
    private val titleBold: TextStyle,
    val titleStyle: TextStyle = titleBold
)

// TabItem
data class TabItemTypography(
    private val primaryBold: TextStyle,
    val textStyle: TextStyle = primaryBold
)

// choicesDialog
data class ChoicesDialogTypography(
    private val btnLabelMedium: TextStyle,
    val btnTextStyle: TextStyle = btnLabelMedium
)

// TimePickerDialog
data class TimePickerDialogTypography(
    private val btnLabelMedium: TextStyle,
    val btnTextStyle: TextStyle = btnLabelMedium
)

// DatePickerDialog
data class DatePickerDialogTypography(
    private val btnLabelMedium: TextStyle,
    val btnTextStyle: TextStyle = btnLabelMedium
)


val LocalCoreTypography = staticCompositionLocalOf { CoreTypography() }