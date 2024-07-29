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
    val primaryBtnBg: Color = primary,
    val primaryBtnTextColor: Color = onPrimary,
    val primaryBtnIconColor: Color = onPrimary,

    // DangerFilledButton
    val dangerBtnBg: Color = danger,
    val dangerBtnTextColor: Color = onPrimary,
    val dangerBtnIconColor: Color = onPrimary,

    // FloatingButton
    val floatingBtnBg: Color = onSecondary,
    val floatingBtnTextColor: Color = secondary,
    val floatingBtnIconColor: Color = secondary,

    // PrimaryCheckableButton
    val checkedBtnBg: Color = primaryStrongDark,
    val uncheckedBtnBg: Color = primary,
    val checkedBtnTextColor: Color = onStrongDarkPrimary,
    val uncheckedBtnTextColor: Color = onPrimary,

    // PrimaryStrokedButton
    val strokedBtnBg: Color = background,
    val strokedBtnStrokeColor: Color = primary,
    val strokedBtnTextColor: Color = primary,
    val strokedBtnIconColor: Color = primary,

    // PrimaryTextButton
    val primaryTextBtnTextColor: Color = primary,
    val primaryTextBtnIconColor: Color = primary,

    // SecondaryFilledButton
    val secondaryFilledBtnBg: Color = secondary,
    val secondaryFilledBtnTextColor: Color = onSecondary,
    val secondaryFilledBtnIconColor: Color = onSecondary,

    // SecondaryStrokedButton
    val secondaryStrokedBtnBg: Color = background,
    val secondaryStrokedBtnStrokeColor: Color = secondary,
    val secondaryStrokedBtnTextColor: Color = secondary,
    val secondaryStrokedBtnIconColor: Color = secondary,

    // SecondaryTextButton
    val secondaryTextBtnTextColor: Color = secondary,
    val secondaryTextBtnIconColor: Color = secondary,

    // SocialButton
    val socialBtnBg: Color = black,
    val socialBtnTextColor: Color = onPrimary,
    val socialBtnStrokeColor: Color = onPrimary,

    // TertiaryFilledButton
    val tertiaryFilledBtnBg: Color = tertiary,
    val tertiaryFilledBtnTextColor: Color = onTertiary,
    val tertiaryFilledBtnIconColor: Color = onTertiary,

    // TertiaryStrokedButton
    val tertiaryStrokedBtnBg: Color = background,
    val tertiaryStrokedBtnStrokeColor: Color = tertiary,
    val tertiaryStrokedBtnTextColor: Color = tertiary,
    val tertiaryStrokedBtnIconColor: Color = tertiary,

    // TertiaryTextButton
    val tertiaryTextBtnTextColor: Color = tertiary,
    val tertiaryTextBtnIconColor: Color = tertiary,

    // TransparentStrokedButton
    val transparentStrokedBtnBg: Color = transparent,
    val transparentStrokedBtnStrokeColor: Color = onPrimary,
    val transparentStrokedBtnTextColor: Color = onPrimary,
    val transparentStrokedBtnIconColor: Color = onPrimary,

    // Divider
    val dividerColor: Color = outline,

    // InlineInputField
    val inlineInputFieldTextColor: Color = black,
    val inlineInputFieldPlaceholderColor: Color = gray,

    // LinedOtpInputField
    val linedOtpInputFieldTextColor: Color = secondary,
    val linedOtpInputFieldBorderColor: Color = primary,

    // OutlinedOtpInputField
    val outlinedOtpInputFieldTextColor: Color = primary,
    val outlinedOtpInputFieldBackgroundColor: Color = tertiary,

    // PickerInputField
    val pickerInputFieldTextColor: Color = black,
    val pickerInputFieldIconColor: Color = secondary,
    val pickerInputFieldPlaceholderColor: Color = secondaryContainer,
    val pickerInputFieldLabelColor: Color = black,
    val pickerInputFieldErrorColor: Color = danger,

    // PriceTextInputField
    val priceTextInputFieldBg: Color = white,
    val priceTextInputFieldTextColor: Color = black,

    // PrimaryTextInputField
    val primaryTextInputFieldIconColor: Color = secondary,
    val primaryTextInputFieldBg: Color = background,
    val primaryTextInputFieldFocusedBorderColor: Color = primaryDark,
    val primaryTextInputFieldUnFocusedBorderColor: Color = primary,
    val primaryTextInputFieldTextColor: Color = secondary,
    val primaryTextInputFieldPlaceholderColor: Color = placeholder,
    val primaryTextInputFieldLabelColor: Color = secondary,
    val primaryTextInputFieldErrorColor: Color = danger,

    // TertiaryTextInputField
    val tertiaryTextInputFieldIconColor: Color = tertiary,
    val tertiaryTextInputFieldBg: Color = background,
    val tertiaryTextInputFieldFocusedBorderColor: Color = tertiaryBorder,
    val tertiaryTextInputFieldUnFocusedBorderColor: Color = tertiaryBorder,
    val tertiaryTextInputFieldTextColor: Color = black,
    val tertiaryTextInputFieldPlaceholderColor: Color = secondaryContainer,
    val tertiaryTextInputFieldLabelColor: Color = black,
    val tertiaryTextInputFieldErrorColor: Color = danger,

    // PrimaryProgressIndicator
    val primaryProgressColor: Color = primary,

    // SecondaryProgressIndicator
    val secondaryProgressColor: Color = secondary,

    // SmallProgressIndicator
    val smallProgressColor: Color = primary,

    // SuccessSnackBar
    val successSnackBarColor: Color = success,
    val successSnackBarBgColor: Color = successContainer,

    // ErrorSnackBar
    val errorSnackBarColor: Color = danger,
    val errorSnackBarBgColor: Color = dangerContainer,

    // SecondaryStepBar
    val secondaryStepBarColor: Color = secondary,
    val secondaryStepBarBorderColor: Color = secondary,

    // IconText
    val iconTextColor: Color = tertiary,

    // TextDivider
    val textDividerColor:Color = divider,

    // Toolbar
    val toolbarStartIconColor:Color = secondary,
    val toolbarEndIconColor:Color = secondary,
    val toolbarTitleTextColor : Color = secondary
)

val LocalCoreColors = staticCompositionLocalOf { CoreColors() }