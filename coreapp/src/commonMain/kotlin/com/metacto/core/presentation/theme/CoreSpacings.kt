package com.metacto.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CoreSpacings(
    val noSpacing: Dp = 0.dp,
    val stroke: Dp = 1.dp,
    val tabIndicatorSize: Dp = 8.dp,
    val dotIndicatorActiveSize: Dp = 10.dp,
    val dotIndicatorInActiveSize: Dp = 8.dp,

    val paddingXXSmall: Dp = 2.dp,
    val paddingXSmall: Dp = 4.dp,
    val paddingSmall: Dp = 6.dp,
    val paddingMedium: Dp = 10.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingXLarge: Dp = 16.dp,
    val paddingXXLarge: Dp = 20.dp,
    val paddingXXXLarge: Dp = 24.dp,
    val screenPadding: Dp = 26.dp,
    val pickerPadding: Dp = 40.dp,

    val iconSmall: Dp = 18.dp,
    val iconMedium: Dp = 20.dp,
    val iconLarge: Dp = 24.dp,

    val switchBtnHeight: Dp = 20.dp,
    val switchBtnWidth: Dp = 40.dp,
    val btnMinHeightSmall: Dp = 44.dp,
    val btnMinHeightNormal: Dp = 52.dp,
    val btnElevation: Dp = 0.dp,
    val btnPaddingVertical: Dp = 4.dp,
    val btnPaddingHorizontal: Dp = 16.dp,
    val btnCheckablePaddingVertical: Dp = 14.dp,
    val btnCheckablePaddingHorizontal: Dp = 20.dp,
    val btnLoadingSize: Dp = 36.dp,
    val floatingBtnElevation: Dp = 6.dp,
    val textBtnSpacing: Dp = 8.dp,

    val lottieProgressSize: Dp = 70.dp,
    val progressSizeNormal: Dp = 50.dp,
    val progressSizeSmall: Dp = 24.dp,
    val progressStrokeNormal: Dp = 8.dp,
    val progressStrokeSmall: Dp = 6.dp,

    val popupIconLarge: Dp = 100.dp,
    val popupPadding: Dp = 12.dp,
    val popupSpacingLarge: Dp = 32.dp,
    val popupSpacingMedium: Dp = 24.dp,

    val genericListEmptyViewIconSize: Dp = 60.dp,
    val genericListEmptyViewActionBtnWidth: Dp = 140.dp,

    val appBarIconMinSize: Dp = 40.dp,
    val appBarPadding: Dp = 16.dp,
    val appBarDefaultHeight: Dp = 56.dp,
    val appBarDefaultElevation: Dp = 3.dp,

    val sheetElevation: Dp = 12.dp,
    val pickerItemSize: Dp = 40.dp,
    val datePickerHeight: Dp = 300.dp,
    val stepBarHeight: Dp = 8.dp,
    val stepBarStroke: Dp = 1.5.dp,
    val numberSelectorSize: Dp = 44.dp,

    val toolbarIconMinSize: Dp = 40.dp,
    val toolbarHeight: Dp = 62.dp,

    val defaultWheelPickerHeight: Dp = 128.dp,
    val defaultWheelPickerWidth: Dp = 256.dp,
    val datePickerWheelHeight: Dp = 270.dp,
    val itemPickerHeight: Dp = 300.dp,
    val itemPickerWheelHeight: Dp = 270.dp,
    val wheelPickerItemStroke: Dp = 1.dp,
    val itemPickerItemStroke: Dp = 0.dp,

    // PrimaryFilledButton
    val primaryFilledBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val primaryFilledBtnPaddingVertical: Dp = btnPaddingVertical,
    val primaryFilledBtnMinHeightNormal: Dp = btnMinHeightNormal,
    val primaryFilledBtnMinHeightSmall: Dp = btnMinHeightSmall,

    // DangerFilledButton
    val dangerFilledBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val dangerFilledBtnPaddingVertical: Dp = btnPaddingVertical,
    val dangerFilledBtnMinHeightNormal: Dp = btnMinHeightNormal,
    val dangerFilledBtnMinHeightSmall: Dp = btnMinHeightSmall,

    // FloatingButton
    val floatingBtnIconSize: Dp = iconSmall,
    val floatingBtnMinHeight: Dp = btnMinHeightSmall,
    val floatingBtnContentSpacing: Dp = paddingSmall,

    // OnSecondaryTransparentFilledButton
    val onSecondaryTransparentFilledBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val onSecondaryTransparentFilledBtnPaddingVertical: Dp = btnPaddingVertical,
    val onSecondaryTransparentFilledBtnMinHeight: Dp = btnMinHeightSmall,
    val onSecondaryTransparentFilledBtnElevation: Dp = noSpacing,

    // PrimaryStrokedButton
    val primaryStrokedBtnPaddingVertical: Dp = btnPaddingVertical,
    val primaryStrokedBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val primaryStrokedBtnMinHeightSmall: Dp = btnMinHeightSmall,
    val primaryStrokedBtnMinHeightNormal: Dp = btnMinHeightNormal,
    val primaryStrokedBtnStrokeWidth: Dp = stroke,

    // PrimaryTextButton
    val primaryTextBtnIconSize: Dp = iconMedium,
    val primaryTextBtnSpacing: Dp = textBtnSpacing,
    val primaryTextBtnPaddingVertical: Dp = btnPaddingVertical,

    // SecondaryFilledButton
    val secondaryFilledBtnPaddingVertical: Dp = btnPaddingVertical,
    val secondaryFilledBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val secondaryFilledBtnMinHeightSmall: Dp = btnMinHeightSmall,
    val secondaryFilledBtnMinHeightNormal: Dp = btnMinHeightNormal,

    // SecondaryStrokedButton
    val secondaryStrokedBtnPaddingVertical: Dp = btnPaddingVertical,
    val secondaryStrokedBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val secondaryStrokedBtnMinHeightSmall: Dp = btnMinHeightSmall,
    val secondaryStrokedBtnMinHeightNormal: Dp = btnMinHeightNormal,
    val secondaryStrokedBtnStrokeWidth: Dp = stroke,

    // SecondaryTextButton
    val secondaryTextBtnIconSize: Dp = iconMedium,
    val secondaryTextBtnSpacing: Dp = textBtnSpacing,
    val secondaryTextBtnPaddingVertical: Dp = btnPaddingVertical,

    // SocialButton
    val socialBtnPaddingVertical: Dp = btnPaddingVertical,
    val socialBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val socialBtnStrokeWidth: Dp = stroke,

    // TertiaryFilledButton
    val tertiaryFilledBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val tertiaryFilledBtnPaddingVertical: Dp = btnPaddingVertical,
    val tertiaryFilledBtnMinHeightNormal: Dp = btnMinHeightNormal,
    val tertiaryFilledBtnMinHeightSmall: Dp = btnMinHeightSmall,

    // TertiaryStrokedButton
    val tertiaryStrokedBtnPaddingVertical: Dp = btnPaddingVertical,
    val tertiaryStrokedBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val tertiaryStrokedBtnMinHeightSmall: Dp = btnMinHeightSmall,
    val tertiaryStrokedBtnMinHeightNormal: Dp = btnMinHeightNormal,
    val tertiaryStrokedBtnStrokeWidth: Dp = stroke,

    // TertiaryTextButton
    val tertiaryTextBtnIconSize: Dp = iconMedium,
    val tertiaryTextBtnSpacing: Dp = textBtnSpacing,
    val tertiaryTextBtnPaddingVertical: Dp = btnPaddingVertical,

    // TransparentStrokedButton
    val transparentStrokedBtnPaddingVertical: Dp = btnPaddingVertical,
    val transparentStrokedBtnPaddingHorizontal: Dp = btnPaddingHorizontal,
    val transparentStrokedBtnElevation: Dp = noSpacing,
    val transparentStrokedBtnStrokeWidth: Dp = stroke,

    // DatePickerDialog
    val datePickerDialogPaddingVertical: Dp = paddingXLarge,
    val datePickerDialogPaddingHorizontal: Dp = noSpacing,

    // IconText
    val iconTextIconSize: Dp = paddingXLarge,
    val iconTextIconSpacing: Dp = paddingMedium,

    // TextDivider
    val textDividerHorizontalPadding: Dp = paddingXLarge,
    val textDividerStrokeWidth: Dp = stroke,

    // AppImage
    val appImageElevation: Dp = noSpacing,

    // GrayAppImage
    val grayAppImageElevation: Dp = noSpacing,

    // WhiteAppImage
    val whiteAppImageElevation: Dp = noSpacing,

    // LinedOtpInputField
    val linedOtpInputFieldPinSpacing: Dp = paddingSmall,

    // OutlinedOtpInputField
    val outlinedOtpInputFieldPinSpacing: Dp = paddingLarge,

    // PrimaryTextInputField
    val primaryTextInputFieldMinHeight: Dp = noSpacing,
    val primaryTextInputFieldEndIconSize: Dp = iconSmall,
    val primaryTextInputFieldStartIconSize: Dp = iconSmall,

    // TertiaryTextInputField
    val tertiaryTextInputFieldMinHeight: Dp = noSpacing,
    val tertiaryTextInputFieldEndIconSize: Dp = iconSmall,
    val tertiaryTextInputFieldStartIconSize: Dp = iconSmall,

    // OptionItem
    val optionItemPaddingVertical: Dp = paddingXXXLarge,
    val optionItemPaddingHorizontal: Dp = paddingXXXLarge,
    val optionItemPaddingTextSpacing: Dp = paddingXLarge,
    val optionItemPaddingIconSize: Dp = iconLarge,
    val optionItemPaddingArrowSize: Dp = iconMedium,

    // HorizontalDotsIndicator
    val horizontalDotsIndicatorSpacing: Dp = paddingLarge,

    // HorizontalPagerIndicator
    val horizontalPagerIndicatorSpacing: Dp = paddingMedium,

    // HorizontalPagerTabItem
    val horizontalPagerTabItemTextPaddingVertical: Dp = paddingMedium,
    val horizontalPagerTabItemTextPaddingHorizontal: Dp = paddingXLarge,

    // HorizontalPagerTabs
    val horizontalPagerTabsHorizontalSpacing: Dp = paddingXLarge,

    // SmallProgressIndicator
    val smallProgressIndicator: Dp =paddingLarge,

    // AppSnackBar
    val appSnackBarPaddingVertical :Dp = paddingXXLarge,
    val appSnackBarPaddingHorizontal :Dp = paddingXXXLarge,
    val appSnackBarIconSize :Dp = iconLarge,
    val appSnackBarHorizontalSpacing:Dp = paddingMedium,

    // AppDialog
    val appDialogContentPadding :Dp = paddingXLarge,

    // ChoicesDialog
    val choicesDialogVerticalSpacing :Dp = paddingSmall,

    // DialogToolbar
    val dialogToolbarCloseSize :Dp = paddingXLarge,
    val dialogToolbarHorizontalPadding :Dp = paddingXLarge,
    val dialogToolbarTopPadding :Dp = iconMedium,
    val dialogToolbarBottomPadding :Dp = paddingMedium,
)

val LocalCoreSpacings = staticCompositionLocalOf { CoreSpacings() }