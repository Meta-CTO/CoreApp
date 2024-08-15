package com.metacto.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CoreColors(
    val transparent: Color = Color.Transparent,
    val black: Color = Color(0xFF000000),
    val white: Color = Color(0xFFFFFFFF),
    val gray: Color = Color(0xFF6F6F6F),
    val lightGray: Color = Color(0xFFB7B7B7),
    val lightGray20: Color = lightGray.copy(alpha = 0.2f),
    val lightGray50: Color = Color(0xFFEEEEEE),
    val darkGreen: Color = Color(0xFF00863A),
    val lightGreen: Color = Color(0xFFECFDF3),
    val red: Color = Color(0xFFD92D20),
    val lightRed: Color = Color(0xFFFEF3F2),
    val nectarine: Color = Color(0xFFF1C3A9),
    val nectarineDark: Color = Color(0xFFF1AA82),
    val miniPeach: Color = Color(0xFFFAF4F0),
    val midnight: Color = Color(0xFF271E41),
    val lavender: Color = Color(0xFFD5D8FC),
    val terra: Color = Color(0xFFC55415),

    val primary: Color = nectarine,
    val primaryDark: Color = nectarineDark,
    val primaryStrongDark: Color = terra,
    val onPrimary: Color = midnight,
    val onStrongDarkPrimary: Color = white,
    val primaryContainer: Color = miniPeach,
    val onPrimaryContainer: Color = midnight,

    val secondary: Color = midnight,
    val secondaryDisabled: Color = secondary.copy(alpha = 0.5f),
    val onSecondary: Color = white,
    val secondaryContainer: Color = lavender,
    val onSecondaryContainer: Color = midnight,

    val tertiary: Color = gray,
    val onTertiary: Color = white,
    val tertiaryBorder: Color = lightGray50,

    val danger: Color = red,
    val dangerContainer: Color = lightRed,

    val success: Color = darkGreen,
    val successContainer: Color = lightGreen,

    val sheetPrimary: Color = midnight,
    val sheetBackground: Color = white,

    val background: Color = miniPeach,
    val outline: Color = lightGray20,

    val placeholder: Color = Color(0xFF8F8F8F),
    val optionsArrow: Color = black,
    val divider: Color = Color(0xFFA5A5A5),
    val pickerItem: Color = midnight,

    val pullRefreshIndicator: Color = primary,
    val pullRefreshIndicatorBackground: Color = white,

    val switchBtnToggled: Color = primary,
    val switchBtnNonToggled: Color = tertiary,

    val switchThumbToggled: Color = onPrimary,
    val switchThumbNonToggled: Color = onTertiary,

    val wheelPickerItemBg: Color = primary.copy(alpha = 0.5f),
    val wheelPickerItemStroke: Color = primary,
    val itemPickerItemBg: Color = primary.copy(alpha = 0.3f),
    val itemPickerItemStroke: Color = primary,

    // PrimaryFilledButton
    val primaryFilledButton: PrimaryFilledButtonColors = PrimaryFilledButtonColors(
        primary = primary,
        onPrimary = onPrimary
    ),

    // DangerFilledButton
    val dangerFilledButton: DangerFilledButtonColors = DangerFilledButtonColors(
        danger = danger,
        onPrimary = onPrimary
    ),

    // FloatingButton
    val floatingButton: FloatingButtonColors = FloatingButtonColors(
        onSecondary = onSecondary,
        secondary = secondary
    ),

    // OnSecondaryTransparentFilledButton
    val onSecondaryTransparentFilledButton: OnSecondaryTransparentFilledButtonColors = OnSecondaryTransparentFilledButtonColors(
        onSecondary = onSecondary
    ),

    // PrimaryCheckableButton
    val primaryCheckableButton: PrimaryCheckableButtonColors = PrimaryCheckableButtonColors(
        primaryStrongDark = primaryStrongDark,
        primary = primary,
        onStrongDarkPrimary = onStrongDarkPrimary,
        onPrimary = onPrimary
    ),

    // PrimaryStrokedButton
    val primaryStrokedButton: PrimaryStrokedButtonColors = PrimaryStrokedButtonColors(
        background = background,
        primary = primary
    ),

    // PrimaryTextButton
    val primaryTextButton: PrimaryTextButtonColors = PrimaryTextButtonColors(
        primary = primary
    ),

    // SecondaryFilledButton
    val secondaryFilledButton: SecondaryFilledButtonColors = SecondaryFilledButtonColors(
        secondary = secondary,
        onSecondary = onSecondary
    ),

    // SecondaryStrokedButton
    val secondaryStrokedButton: SecondaryStrokedButtonColors = SecondaryStrokedButtonColors(
        background = background,
        secondary = secondary
    ),

    // SecondaryTextButton
    val secondaryTextButton: SecondaryTextButtonColors = SecondaryTextButtonColors(
        secondary = secondary
    ),

    // SocialButton
    val socialButton: SocialButtonColors = SocialButtonColors(
        black = black,
        onPrimary = onPrimary
    ),

    // TertiaryFilledButton
    val tertiaryFilledButton: TertiaryFilledButtonColors = TertiaryFilledButtonColors(
        tertiary = tertiary,
        onTertiary = onTertiary
    ),

    // TertiaryStrokedButton
    val tertiaryStrokedButton: TertiaryStrokedButtonColors = TertiaryStrokedButtonColors(
        background = background,
        tertiary = tertiary
    ),

    // TertiaryTextButton
    val tertiaryTextButton: TertiaryTextButtonColors = TertiaryTextButtonColors(
        tertiary = tertiary
    ),

    // TransparentStrokedButton
    val transparentStrokedButton: TransparentStrokedButtonColors = TransparentStrokedButtonColors(
        transparent = transparent,
        onPrimary = onPrimary
    ),

    // HorizontalDivider
    val horizontalDivider: HorizontalDividerColors = HorizontalDividerColors(
        outline = outline
    ),

    // VerticalDivider
    val verticalDivider: VerticalDividerColors = VerticalDividerColors(
        outline = outline
    ),

    // InlineInputField
    val inlineInputField: InlineInputFieldColors = InlineInputFieldColors(
        black = black,
        gray = gray
    ),

    // LinedOtpInputField
    val linedOtpInputField: LinedOtpInputFieldColors = LinedOtpInputFieldColors(
        secondary = secondary,
        primary = primary
    ),

    // OutlinedOtpInputField
    val outlinedOtpInputField: OutlinedOtpInputFieldColors = OutlinedOtpInputFieldColors(
        primary = primary,
        tertiary = tertiary
    ),

    // PickerInputField
    val pickerInputField: PickerInputFieldColors = PickerInputFieldColors(
        black = black,
        secondary = secondary,
        secondaryContainer = secondaryContainer,
        danger = danger
    ),

    // PriceTextInputField
    val priceTextInputField: PriceTextInputFieldColors = PriceTextInputFieldColors(
        white = white,
        black = black,
        placeholder = placeholder,
        secondary = secondary,
        danger = danger,
        tertiary = tertiary,
        primaryDark = primaryDark,
        primary = primary
    ),

    // PrimaryTextInputField
    val primaryTextInputField: PrimaryTextInputFieldColors = PrimaryTextInputFieldColors(
        secondary = secondary,
        background = background,
        primaryDark = primaryDark,
        primary = primary,
        placeholder = placeholder,
        danger = danger
    ),

    // TertiaryTextInputField
    val tertiaryTextInputField: TertiaryTextInputFieldColors = TertiaryTextInputFieldColors(
        tertiary = tertiary,
        background = background,
        tertiaryBorder = tertiaryBorder,
        black = black,
        secondaryContainer = secondaryContainer,
        danger = danger
    ),

    // PrimaryProgressIndicator
    val primaryProgressIndicator: PrimaryProgressIndicatorColors = PrimaryProgressIndicatorColors(
        primary = primary
    ),

    // SecondaryProgressIndicator
    val secondaryProgressIndicator: SecondaryProgressIndicatorColors = SecondaryProgressIndicatorColors(
        secondary = secondary
    ),

    // SmallProgressIndicator
    val smallProgressIndicator: SmallProgressIndicatorColors = SmallProgressIndicatorColors(
        primary = primary
    ),

    // SuccessSnackBar
    val successSnackBar: SuccessSnackBarColors = SuccessSnackBarColors(
        success = success,
        successContainer = successContainer
    ),

    // ErrorSnackBar
    val errorSnackBar: ErrorSnackBarColors = ErrorSnackBarColors(
        danger = danger,
        dangerContainer = dangerContainer
    ),

    // SecondaryStepBar
    val secondaryStepBar: SecondaryStepBarColors = SecondaryStepBarColors(
        secondary = secondary
    ),

    // IconText
    val iconText: IconTextColors = IconTextColors(
        tertiary = tertiary
    ),

    // TextDivider
    val textDivider: TextDividerColors = TextDividerColors(
        divider = divider
    ),

    // Toolbar
    val toolbar: ToolbarColors = ToolbarColors(
        secondary = secondary
    ),

    // OptionItem
    val optionItem: OptionItemColors = OptionItemColors(
        secondary = secondary
    ),

    // ConfirmationDialog
    val confirmationDialog: ConfirmationDialogColors = ConfirmationDialogColors(
        secondary = secondary
    ),

    // DialogToolbar
    val dialogToolbar: DialogToolbarColors = DialogToolbarColors(
        secondary = secondary
    ),

    // MessageDialog
    val messageDialog: MessageDialogColors = MessageDialogColors(
        secondary = secondary
    ),

    // OverrideUserDialog
    val overrideUserDialog: OverrideUserDialogColors = OverrideUserDialogColors(
        secondary = secondary
    ),

    // ItemPicker
    val itemPicker: ItemPickerColors = ItemPickerColors(
        pickerItem = pickerItem,
        itemPickerItemBg = itemPickerItemBg,
        itemPickerItemStroke = itemPickerItemStroke
    ),

    // AppDialog
    val appDialog: AppDialogColors = AppDialogColors(
        background = background
    ),

    // SecondaryNumberItem
    val secondaryNumberItem: SecondaryNumberItemColors = SecondaryNumberItemColors(
        onSecondary = onSecondary,
        secondary = secondary,
        secondaryContainer = secondaryContainer
    ),

    // WheelTextPicker
    val wheelTextPicker: WheelTextPickerColors = WheelTextPickerColors(
        pickerItem = pickerItem
    ),

    // SelectorProperties
    val selectorProperties: SelectorPropertiesColors = SelectorPropertiesColors(
        wheelPickerItemBg = wheelPickerItemBg,
        wheelPickerItemStroke = wheelPickerItemStroke
    ),

    // TabItem
    val tapItem: TapItemColors = TapItemColors(
        primaryContainer = primaryContainer,
        background = background,
        onPrimaryContainer = onPrimaryContainer,
        tertiary = tertiary
    )
)

// PrimaryFilledButton
data class PrimaryFilledButtonColors(
    private val primary: Color,
    private val onPrimary: Color,
    val bgColor: Color = primary,
    val textColor: Color = onPrimary,
    val iconColor: Color = onPrimary,
)

// DangerFilledButton
data class DangerFilledButtonColors(
    private val danger: Color,
    private val onPrimary: Color,
    val bgColor: Color = danger,
    val textColor: Color = onPrimary,
    val iconColor: Color = onPrimary,
)

// FloatingButton
data class FloatingButtonColors(
    private val onSecondary: Color,
    private val secondary: Color,
    val bgColor: Color = onSecondary,
    val textColor: Color = secondary,
    val iconColor: Color = secondary,
)

// OnSecondaryTransparentFilledButton
data class OnSecondaryTransparentFilledButtonColors(
    private val onSecondary: Color,
    val bgColor: Color = onSecondary.copy(alpha = 0.1f),
    val textColor: Color = onSecondary.copy(alpha = 0.9f),
    val iconColor: Color = onSecondary,
)

// PrimaryCheckableButton
data class PrimaryCheckableButtonColors(
    private val primaryStrongDark: Color,
    private val primary: Color,
    private val onStrongDarkPrimary: Color,
    private val onPrimary: Color,
    val checkedBgColor: Color = primaryStrongDark,
    val uncheckedBgColor: Color = primary,
    val checkedTextColor: Color = onStrongDarkPrimary,
    val uncheckedTextColor: Color = onPrimary,
)

// PrimaryStrokedButton
data class PrimaryStrokedButtonColors(
    private val background: Color,
    private val primary: Color,
    val bgColor: Color = background,
    val strokeColor: Color = primary,
    val textColor: Color = primary,
    val iconColor: Color = primary,
)

// PrimaryTextButton
data class PrimaryTextButtonColors(
    private val primary: Color,
    val textColor: Color = primary,
    val iconColor: Color = primary,
)

// SecondaryFilledButton
data class SecondaryFilledButtonColors(
    private val secondary: Color,
    private val onSecondary: Color,
    val bgColor: Color = secondary,
    val textColor: Color = onSecondary,
    val iconColor: Color = onSecondary
)

// SecondaryStrokedButton
data class SecondaryStrokedButtonColors(
    private val secondary: Color,
    private val background: Color,
    val bgColor: Color = background,
    val strokeColor: Color = secondary,
    val textColor: Color = secondary,
    val iconColor: Color = secondary,
)

// SecondaryTextButton
data class SecondaryTextButtonColors(
    private val secondary: Color,
    val textColor: Color = secondary,
    val iconColor: Color = secondary,
)

// SocialButton
data class SocialButtonColors(
    private val black: Color,
    private val onPrimary: Color,
    val bgColor: Color = black,
    val textColor: Color = onPrimary,
    val strokeColor: Color = onPrimary,
)

// TertiaryFilledButton
data class TertiaryFilledButtonColors(
    private val tertiary: Color,
    private val onTertiary: Color,
    val bgColor: Color = tertiary,
    val textColor: Color = onTertiary,
    val iconColor: Color = onTertiary,
)

// TertiaryStrokedButton
data class TertiaryStrokedButtonColors(
    private val background: Color,
    private val tertiary: Color,
    val bgColor: Color = background,
    val strokeColor: Color = tertiary,
    val textColor: Color = tertiary,
    val iconColor: Color = tertiary
)

// TertiaryTextButton
data class TertiaryTextButtonColors(
    private val tertiary: Color,
    val textColor: Color = tertiary,
    val iconColor: Color = tertiary
)

// TransparentStrokedButton
data class TransparentStrokedButtonColors(
    private val transparent: Color,
    private val onPrimary: Color,
    val bgColor: Color = transparent,
    val strokeColor: Color = onPrimary,
    val textColor: Color = onPrimary,
    val iconColor: Color = onPrimary
)

// HorizontalDividerColors
data class HorizontalDividerColors(
    private val outline: Color,
    val dividerColor: Color = outline
)

// VerticalDivider
data class VerticalDividerColors(
    private val outline: Color,
    val dividerColor: Color = outline
)

// InlineInputField
data class InlineInputFieldColors(
    private val black: Color,
    private val gray: Color,
    val textColor: Color = black,
    val placeholderColor: Color = gray,
)

// LinedOtpInputField
data class LinedOtpInputFieldColors(
    private val secondary: Color,
    private val primary: Color,
    val textColor: Color = secondary,
    val borderColor: Color = primary,
)

// OutlinedOtpInputField
data class OutlinedOtpInputFieldColors(
    private val primary: Color,
    private val tertiary: Color,
    val textColor: Color = primary,
    val bgColor: Color = tertiary,
)

// PickerInputField
data class PickerInputFieldColors(
    private val black: Color,
    private val secondary: Color,
    private val secondaryContainer: Color,
    private val danger: Color,
    val textColor: Color = black,
    val iconColor: Color = secondary,
    val placeholderColor: Color = secondaryContainer,
    val labelColor: Color = black,
    val errorColor: Color = danger,
)

// PriceTextInputField
data class PriceTextInputFieldColors(
    private val white: Color,
    private val black: Color,
    private val placeholder: Color,
    private val secondary: Color,
    private val danger: Color,
    private val tertiary: Color,
    private val primaryDark: Color,
    private val primary: Color,
    val bgColor: Color = white,
    val textColor: Color = black,
    val placeholderColor: Color = placeholder,
    val labelColor: Color = secondary,
    val errorColor: Color = danger,
    val iconColor: Color = tertiary,
    val focusedBorderColor: Color = primaryDark,
    val unFocusedBorderColor: Color = primary,
)

// PrimaryTextInputField
data class PrimaryTextInputFieldColors(
    private val secondary: Color,
    private val background: Color,
    private val primaryDark: Color,
    private val primary: Color,
    private val placeholder: Color,
    private val danger: Color,
    val iconColor: Color = secondary,
    val bgColor: Color = background,
    val focusedBorderColor: Color = primaryDark,
    val unFocusedBorderColor: Color = primary,
    val textColor: Color = secondary,
    val placeholderColor: Color = placeholder,
    val labelColor: Color = secondary,
    val errorColor: Color = danger,
)

// TertiaryTextInputField
data class TertiaryTextInputFieldColors(
    private val tertiary: Color,
    private val background: Color,
    private val tertiaryBorder: Color,
    private val black: Color,
    private val secondaryContainer: Color,
    private val danger: Color,
    val iconColor: Color = tertiary,
    val bgColor: Color = background,
    val focusedBorderColor: Color = tertiaryBorder,
    val unFocusedBorderColor: Color = tertiaryBorder,
    val textColor: Color = black,
    val placeholderColor: Color = secondaryContainer,
    val labelColor: Color = black,
    val errorColor: Color = danger,
)

// PrimaryProgressIndicator
data class PrimaryProgressIndicatorColors(
    private val primary: Color,
    val progressColor: Color = primary
)

// SecondaryProgressIndicator
data class SecondaryProgressIndicatorColors(
    private val secondary: Color,
    val progressColor: Color = secondary
)

// SmallProgressIndicator
data class SmallProgressIndicatorColors(
    private val primary: Color,
    val progressColor: Color = primary,
)

// SuccessSnackBar
data class SuccessSnackBarColors(
    private val success: Color,
    private val successContainer: Color,
    val color: Color = success,
    val bgColor: Color = successContainer,
)

// ErrorSnackBar
data class ErrorSnackBarColors(
    private val danger: Color,
    private val dangerContainer: Color,
    val color: Color = danger,
    val bgColor: Color = dangerContainer,
)

// SecondaryStepBar
data class SecondaryStepBarColors(
    private val secondary: Color,
    val color: Color = secondary,
    val borderColor: Color = secondary,
)

// IconText
data class IconTextColors(
    private val tertiary: Color,
    val color: Color = tertiary,
)

// TextDivider
data class TextDividerColors(
    private val divider: Color,
    val dividerColor: Color = divider,
)

// Toolbar
data class ToolbarColors(
    private val secondary: Color,
    val startIconColor: Color = secondary,
    val endIconColor: Color = secondary,
    val titleTextColor: Color = secondary,
)

// OptionItem
data class OptionItemColors(
    private val secondary: Color,
    val defaultColor: Color = secondary,
)

// ConfirmationDialog
data class ConfirmationDialogColors(
    private val secondary: Color,
    val bodyTextColor: Color = secondary
)

// DialogToolbar
data class DialogToolbarColors(
    private val secondary: Color,
    val closeColor: Color = secondary,
    val textColor: Color = secondary,
)

// MessageDialog
data class MessageDialogColors(
    private val secondary: Color,
    val bodyTextColor: Color = secondary
)

// OverrideUserDialog
data class OverrideUserDialogColors(
    private val secondary: Color,
    val bodyTextColor: Color = secondary
)

// ItemPicker
data class ItemPickerColors(
    private val pickerItem: Color,
    private val itemPickerItemBg: Color,
    private val itemPickerItemStroke: Color,
    val textColor: Color = pickerItem,
    val selectorColor: Color = itemPickerItemBg,
    val selectorBorderColor: Color = itemPickerItemStroke
)

// AppDialog
data class AppDialogColors(
    private val background: Color,
    val containerBgColor: Color = background
)

// SecondaryNumberItem
data class SecondaryNumberItemColors(
    private val onSecondary: Color,
    private val secondary: Color,
    private val secondaryContainer: Color,
    val selectedTextColor: Color = onSecondary,
    val selectedBgColor: Color = secondary,
    val unSelectedTextColor: Color = secondary,
    val unSelectedBgColor: Color = secondaryContainer
)

// WheelTextPicker
data class WheelTextPickerColors(
    private val pickerItem: Color,
    val textColor: Color = pickerItem
)

// SelectorProperties
data class SelectorPropertiesColors(
    private val wheelPickerItemBg: Color,
    private val wheelPickerItemStroke: Color,
    val color: Color = wheelPickerItemBg,
    val strokeColor: Color = wheelPickerItemStroke
)

// TabItem
data class TapItemColors(
    private val primaryContainer: Color,
    private val background: Color,
    private val onPrimaryContainer: Color,
    private val tertiary: Color,
    val activeBgColor: Color = primaryContainer,
    val inactiveBgColor: Color = background,
    val activeTextColor: Color = onPrimaryContainer,
    val inactiveTextColor: Color = tertiary,
    val activeIndicatorColor: Color = onPrimaryContainer,
    val inactiveIndicatorColor: Color = tertiary,
)

val LocalCoreColors = staticCompositionLocalOf { CoreColors() }