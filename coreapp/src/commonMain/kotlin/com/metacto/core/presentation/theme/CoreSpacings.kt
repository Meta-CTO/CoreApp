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

    val tabActiveIndicatorThickness: Dp = 3.dp,
    val tabInactiveIndicatorThickness: Dp = 1.dp,
    val tabTextPadding: Dp = 4.dp,
    val tabIconSize: Dp = 24.dp
)

val LocalCoreSpacings = staticCompositionLocalOf { CoreSpacings() }