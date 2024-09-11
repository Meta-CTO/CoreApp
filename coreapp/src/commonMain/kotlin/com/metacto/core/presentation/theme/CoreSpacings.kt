package com.metacto.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CoreSpacings(
    val noSpacing: Dp = 0.dp,
    val stroke: Dp = 1.dp,
    val tabIndicatorSize: Dp = 8.dp,
    val dotIndicatorActiveSize: Dp = 10.dp,
    val dotIndicatorInActiveSize: Dp = 8.dp,

    val backBtnPadding: Dp = 8.dp,

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
    val inputFieldElevation: Dp = 0.dp,
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

    val defaultWheelPickerHeight: Dp = 128.dp,
    val defaultWheelPickerWidth: Dp = 256.dp,
    val datePickerWheelHeight: Dp = 270.dp,
    val itemPickerHeight: Dp = 300.dp,
    val wheelPickerItemStroke: Dp = 1.dp,
    val itemPickerItemStroke: Dp = 0.dp,

    // PrimaryFilledButton
    val primaryFilledButton: PrimaryFilledButtonSpacings = PrimaryFilledButtonSpacings(
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnPaddingVertical = btnPaddingVertical,
        btnMinHeightNormal = btnMinHeightNormal,
        btnMinHeightSmall = btnMinHeightSmall,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // DangerFilledButton
    val dangerFilledButton: DangerFilledButtonSpacings = DangerFilledButtonSpacings(
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnPaddingVertical = btnPaddingVertical,
        btnMinHeightNormal = btnMinHeightNormal,
        btnMinHeightSmall = btnMinHeightSmall,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // FloatingButton
    val floatingButton: FloatingButtonSpacings = FloatingButtonSpacings(
        iconSmall = iconSmall,
        btnMinHeightSmall = btnMinHeightSmall,
        paddingSmall = paddingSmall,
        floatingBtnElevation = floatingBtnElevation
    ),

    // OnSecondaryTransparentFilledButton
    val onSecondaryTransparentFilledButton: OnSecondaryTransparentFilledButtonSpacings = OnSecondaryTransparentFilledButtonSpacings(
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnPaddingVertical = btnPaddingVertical,
        btnMinHeightSmall = btnMinHeightSmall,
        noSpacing = noSpacing,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge
    ),

    // PrimaryStrokedButton
    val primaryStrokedButton: PrimaryStrokedButtonSpacings = PrimaryStrokedButtonSpacings(
        btnPaddingVertical = btnPaddingVertical,
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnMinHeightSmall = btnMinHeightSmall,
        btnMinHeightNormal = btnMinHeightNormal,
        stroke = stroke,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // PrimaryTextButton
    val primaryTextButton: PrimaryTextButtonSpacings = PrimaryTextButtonSpacings(
        iconMedium = iconMedium,
        textBtnSpacing = textBtnSpacing,
        btnPaddingVertical = btnPaddingVertical
    ),

    // SecondaryFilledButton
    val secondaryFilledButton: SecondaryFilledButtonSpacings = SecondaryFilledButtonSpacings(
        btnPaddingVertical = btnPaddingVertical,
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnMinHeightSmall = btnMinHeightSmall,
        btnMinHeightNormal = btnMinHeightNormal,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // SecondaryStrokedButton
    val secondaryStrokedButton: SecondaryStrokedButtonSpacings = SecondaryStrokedButtonSpacings(
        btnPaddingVertical = btnPaddingVertical,
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnMinHeightSmall = btnMinHeightSmall,
        btnMinHeightNormal = btnMinHeightNormal,
        stroke = stroke,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // SecondaryTextButton
    val secondaryTextButton: SecondaryTextButtonSpacings = SecondaryTextButtonSpacings(
        iconMedium = iconMedium,
        textBtnSpacing = textBtnSpacing,
        btnPaddingVertical = btnPaddingVertical
    ),

    // SocialButton
    val socialButton: SocialButtonSpacings = SocialButtonSpacings(
        btnPaddingVertical = btnPaddingVertical,
        btnPaddingHorizontal = btnPaddingHorizontal,
        stroke = stroke,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // TertiaryFilledButton
    val tertiaryFilledButton: TertiaryFilledButtonSpacings = TertiaryFilledButtonSpacings(
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnPaddingVertical = btnPaddingVertical,
        btnMinHeightNormal = btnMinHeightNormal,
        btnMinHeightSmall = btnMinHeightSmall,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // TertiaryStrokedButton
    val tertiaryStrokedButton: TertiaryStrokedButtonSpacings = TertiaryStrokedButtonSpacings(
        btnPaddingVertical = btnPaddingVertical,
        btnPaddingHorizontal = btnPaddingHorizontal,
        btnMinHeightSmall = btnMinHeightSmall,
        btnMinHeightNormal = btnMinHeightNormal,
        stroke = stroke,
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing
    ),

    // TertiaryTextButton
    val tertiaryTextButton: TertiaryTextButtonSpacings = TertiaryTextButtonSpacings(
        iconMedium = iconMedium,
        textBtnSpacing = textBtnSpacing,
        btnPaddingVertical = btnPaddingVertical
    ),

    // TransparentStrokedButton
    val transparentStrokedButton: TransparentStrokedButtonSpacings = TransparentStrokedButtonSpacings(
        btnPaddingVertical = btnPaddingVertical,
        btnPaddingHorizontal = btnPaddingHorizontal,
        noSpacing = noSpacing,
        stroke = stroke,
        iconLarge = iconLarge,
        btnMinHeightNormal = btnMinHeightNormal,
        btnMinHeightSmall = btnMinHeightSmall,
        paddingXLarge = paddingXLarge
    ),

    // DatePickerDialog
    val datePickerDialog: DatePickerDialogSpacings = DatePickerDialogSpacings(
        paddingXLarge = paddingXLarge,
        noSpacing = noSpacing,
        paddingXXXLarge = paddingXXXLarge,
        datePickerHeight = datePickerHeight,
        pickerPadding = pickerPadding,
        wheelPickerItemStroke = wheelPickerItemStroke
    ),

    // TimePickerDialog
    val timePickerDialog: TimePickerDialogSpacings = TimePickerDialogSpacings(
        paddingXLarge = paddingXLarge,
        paddingXXLarge = paddingXXLarge,
        paddingXXXLarge = paddingXXXLarge,
        datePickerWheelHeight = datePickerWheelHeight,
        pickerPadding = pickerPadding,
        datePickerHeight = datePickerHeight,
        wheelPickerItemStroke = wheelPickerItemStroke
    ),

    // IconText
    val iconText: IconTextSpacings = IconTextSpacings(
        paddingXLarge = paddingXLarge,
        paddingMedium = paddingMedium
    ),

    // TextDivider
    val textDivider: TextDividerSpacings = TextDividerSpacings(
        paddingXLarge = paddingXLarge,
        stroke = stroke
    ),

    // AppImage
    val appImage: AppImageSpacings = AppImageSpacings(
        noSpacing = noSpacing
    ),

    // GrayAppImage
    val grayAppImage: GrayAppImageSpacings = GrayAppImageSpacings(
        noSpacing = noSpacing
    ),

    // WhiteAppImage
    val whiteAppImage: WhiteAppImageSpacings = WhiteAppImageSpacings(
        noSpacing = noSpacing
    ),

    // LinedOtpInputField
    val linedOtpInputField: LinedOtpInputFieldSpacings = LinedOtpInputFieldSpacings(
        paddingSmall = paddingSmall
    ),

    // OutlinedOtpInputField
    val outlinedOtpInputField: OutlinedOtpInputFieldSpacings = OutlinedOtpInputFieldSpacings(
        paddingLarge = paddingLarge
    ),

    // PrimaryTextInputField
    val primaryTextInputField: PrimaryTextInputFieldSpacings = PrimaryTextInputFieldSpacings(
        noSpacing = noSpacing,
        iconSmall = iconSmall,
        inputFieldElevation = inputFieldElevation
    ),

    // SecondaryTextInputField
    val secondaryTextInputField: SecondaryTextInputFieldSpacings = SecondaryTextInputFieldSpacings(
        noSpacing = noSpacing,
        iconSmall = iconSmall,
        inputFieldElevation = inputFieldElevation
    ),

    // TertiaryTextInputField
    val tertiaryTextInputField: TertiaryTextInputFieldSpacings = TertiaryTextInputFieldSpacings(
        noSpacing = noSpacing,
        iconSmall = iconSmall,
        inputFieldElevation = inputFieldElevation
    ),

    // OptionItem
    val optionItem: OptionItemSpacings = OptionItemSpacings(
        paddingXXXLarge = paddingXXXLarge,
        paddingXLarge = paddingXLarge,
        iconLarge = iconLarge,
        iconMedium = iconMedium
    ),

    // HorizontalDotsIndicator
    val horizontalDotsIndicator: HorizontalDotsIndicatorSpacings = HorizontalDotsIndicatorSpacings(
        paddingLarge = paddingLarge
    ),

    // HorizontalPagerIndicator
    val horizontalPagerIndicator: HorizontalPagerIndicatorSpacings = HorizontalPagerIndicatorSpacings(
        paddingMedium = paddingMedium
    ),

    // HorizontalPagerTabItem
    val horizontalPagerTabItem: HorizontalPagerTabItemSpacings = HorizontalPagerTabItemSpacings(
        paddingMedium = paddingMedium,
        paddingXLarge = paddingXLarge
    ),

    // HorizontalPagerTabs
    val horizontalPagerTabs: HorizontalPagerTabsSpacings = HorizontalPagerTabsSpacings(
        paddingXLarge = paddingXLarge
    ),

    // SmallProgressIndicator
    val smallProgressIndicator: SmallProgressIndicatorSpacings = SmallProgressIndicatorSpacings(
        paddingLarge = paddingLarge
    ),

    // AppSnackBar
    val appSnackBar: AppSnackBarSpacings = AppSnackBarSpacings(
        paddingXXLarge = paddingXXLarge,
        paddingXXXLarge = paddingXXXLarge,
        iconLarge = iconLarge,
        paddingMedium = paddingMedium
    ),

    // AppDialog
    val appDialog: AppDialogSpacings = AppDialogSpacings(
        paddingXLarge = paddingXLarge,
        popupPadding = popupPadding
    ),

    // ChoicesDialog
    val choicesDialog: ChoicesDialogSpacings = ChoicesDialogSpacings(
        paddingSmall = paddingSmall
    ),

    // DialogToolbar
    val dialogToolbar: DialogToolbarSpacings = DialogToolbarSpacings(
        paddingXLarge = paddingXLarge,
        iconMedium = iconMedium,
        paddingMedium = paddingMedium
    ),

    // OtpDigit
    val otpDigit: OtpDigitSpacings = OtpDigitSpacings(
        paddingXLarge = paddingXLarge,
        stroke = stroke
    ),

    // MessageDialog
    val messageDialog: MessageDialogSpacings = MessageDialogSpacings(
        noSpacing = noSpacing,
        paddingXLarge = paddingXLarge,
        popupSpacingLarge = popupSpacingLarge
    ),

    // OverrideUserDialog
    val overrideUserDialog: OverrideUserDialogSpacings = OverrideUserDialogSpacings(
        paddingXLarge = paddingXLarge,
        paddingXXXLarge = paddingXXXLarge,
        paddingLarge = paddingLarge
    ),

    // BottomSheetToolbar
    val bottomSheetToolbar: BottomSheetToolbarSpacings = BottomSheetToolbarSpacings(
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        paddingXXSmall = paddingXXSmall,
        paddingLarge = paddingLarge
    ),

    // ConfirmationDialog
    val confirmationDialog: ConfirmationDialogSpacings = ConfirmationDialogSpacings(
        noSpacing = noSpacing,
        paddingXLarge = paddingXLarge,
        popupSpacingLarge = popupSpacingLarge
    ),

    // force update Dialog
    val forceUpdateDialog: ForceUpdateDialogSpacings = ForceUpdateDialogSpacings(
        paddingXLarge = paddingXLarge,
        popupSpacingLarge = popupSpacingLarge
    ),

    // SuccessDialog
    val successDialog: SuccessDialogSpacings = SuccessDialogSpacings(
        popupIconLarge = popupIconLarge,
        popupSpacingMedium = popupSpacingMedium,
        popupSpacingLarge = popupSpacingLarge
    ),

    // PriceTextInputField
    val priceTextInputField: PriceTextInputFieldSpacings = PriceTextInputFieldSpacings(
        iconSmall = iconSmall,
        noSpacing = noSpacing,
        inputFieldElevation = inputFieldElevation
    ),

    // Dot
    val dot: DotSpacings = DotSpacings(
        noSpacing = noSpacing
    ),

    // ItemPicker
    val itemPicker: ItemPickerSpacings = ItemPickerSpacings(
        paddingXLarge = paddingXLarge,
        itemPickerItemStroke = itemPickerItemStroke
    ),

    // Toolbar
    val toolbar: ToolbarSpacings = ToolbarSpacings(
        paddingMedium = paddingMedium,
        toolbarIconMinSize = toolbarIconMinSize
    ),

    // WheelTextPicker
    val wheelTextPicker: WheelTextPickerSpacings = WheelTextPickerSpacings(
        defaultWheelPickerWidth = defaultWheelPickerWidth,
        defaultWheelPickerHeight = defaultWheelPickerHeight
    ),

    // WheelPicker
    val wheelPicker: WheelPickerSpacings = WheelPickerSpacings(
        defaultWheelPickerWidth = defaultWheelPickerWidth,
        defaultWheelPickerHeight = defaultWheelPickerHeight
    ),

    // SelectorProperties
    val selectorProperties: SelectorPropertiesSpacings = SelectorPropertiesSpacings(
        wheelPickerItemStroke = wheelPickerItemStroke
    ),

    // WheelTimePicker
    val wheelTimePicker: WheelTimePickerSpacings = WheelTimePickerSpacings(
        defaultWheelPickerHeight = defaultWheelPickerHeight,
        defaultWheelPickerWidth = defaultWheelPickerWidth
    ),

    // WheelDateTimePicker
    val wheelDateTimePicker: WheelDateTimePickerSpacings = WheelDateTimePickerSpacings(
        defaultWheelPickerHeight = defaultWheelPickerHeight,
        defaultWheelPickerWidth = defaultWheelPickerWidth
    ),

    // WheelDatePicker
    val wheelDatePicker: WheelDatePickerSpacings = WheelDatePickerSpacings(
        defaultWheelPickerHeight = defaultWheelPickerHeight,
        defaultWheelPickerWidth = defaultWheelPickerWidth
    ),

    // DefaultWheelTimePicker
    val defaultWheelTimePicker: DefaultWheelTimePickerSpacings = DefaultWheelTimePickerSpacings(
        defaultWheelPickerHeight = defaultWheelPickerHeight,
        defaultWheelPickerWidth = defaultWheelPickerWidth
    ),

    // DefaultWheelDateTimePicker
    val defaultWheelDateTimePicker: DefaultWheelDateTimePickerSpacings = DefaultWheelDateTimePickerSpacings(
        defaultWheelPickerHeight = defaultWheelPickerHeight,
        defaultWheelPickerWidth = defaultWheelPickerWidth
    ),

    // DefaultWheelDatePicker
    val defaultWheelDatePicker: DefaultWheelDatePickerSpacings = DefaultWheelDatePickerSpacings(
        defaultWheelPickerHeight = defaultWheelPickerHeight,
        defaultWheelPickerWidth = defaultWheelPickerWidth
    ),

    // TabItem
    val tabItem: TabItemSpacings = TabItemSpacings(),

    // SwitchButton
    val switchButton: SwitchButtonSpacings = SwitchButtonSpacings()
)

// PrimaryFilledButton
data class PrimaryFilledButtonSpacings(
    private val btnPaddingHorizontal: Dp,
    private val btnPaddingVertical: Dp,
    private val btnMinHeightNormal: Dp,
    private val btnMinHeightSmall: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val paddingVertical: Dp = btnPaddingVertical,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val iconSize: Dp = iconLarge,
    val elevation: Dp = noSpacing,
    val contentSpacing: Dp = paddingXLarge
)

// DangerFilledButton
data class DangerFilledButtonSpacings(
    private val btnPaddingHorizontal: Dp,
    private val btnPaddingVertical: Dp,
    private val btnMinHeightNormal: Dp,
    private val btnMinHeightSmall: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val paddingVertical: Dp = btnPaddingVertical,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val iconSize: Dp = iconLarge,
    val contentSpacing: Dp = paddingXLarge,
    val elevation: Dp = noSpacing
)

// FloatingButton
data class FloatingButtonSpacings(
    private val iconSmall: Dp,
    private val btnMinHeightSmall: Dp,
    private val paddingSmall: Dp,
    private val floatingBtnElevation: Dp,
    val iconSize: Dp = iconSmall,
    val minHeight: Dp = btnMinHeightSmall,
    val contentSpacing: Dp = paddingSmall,
    val elevation: Dp = floatingBtnElevation
)

// OnSecondaryTransparentFilledButton
data class OnSecondaryTransparentFilledButtonSpacings(
    private val btnPaddingHorizontal: Dp,
    private val btnPaddingVertical: Dp,
    private val btnMinHeightSmall: Dp,
    private val noSpacing: Dp,
    private val iconLarge: Dp,
    private val paddingXLarge: Dp,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val paddingVertical: Dp = btnPaddingVertical,
    val minHeight: Dp = btnMinHeightSmall,
    val elevation: Dp = noSpacing,
    val iconSize: Dp = iconLarge,
    val contentSpacing: Dp = paddingXLarge
)

// PrimaryStrokedButton
data class PrimaryStrokedButtonSpacings(
    private val btnPaddingVertical: Dp,
    private val btnPaddingHorizontal: Dp,
    private val btnMinHeightSmall: Dp,
    private val btnMinHeightNormal: Dp,
    private val stroke: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val paddingVertical: Dp = btnPaddingVertical,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val strokeWidth: Dp = stroke,
    val iconSize: Dp = iconLarge,
    val elevation: Dp = noSpacing,
    val contentSpacing: Dp = paddingXLarge
)

// PrimaryTextButton
data class PrimaryTextButtonSpacings(
    private val iconMedium: Dp,
    private val textBtnSpacing: Dp,
    private val btnPaddingVertical: Dp,
    val iconSize: Dp = iconMedium,
    val spacing: Dp = textBtnSpacing,
    val paddingVertical: Dp = btnPaddingVertical
)

// SecondaryFilledButton
data class SecondaryFilledButtonSpacings(
    private val btnPaddingVertical: Dp,
    private val btnPaddingHorizontal: Dp,
    private val btnMinHeightSmall: Dp,
    private val btnMinHeightNormal: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingVertical: Dp = btnPaddingVertical,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val iconSize: Dp = iconLarge,
    val elevation: Dp = noSpacing,
    val contentSpacing: Dp = paddingXLarge
)

// SecondaryStrokedButton
data class SecondaryStrokedButtonSpacings(
    private val btnPaddingVertical: Dp,
    private val btnPaddingHorizontal: Dp,
    private val btnMinHeightSmall: Dp,
    private val btnMinHeightNormal: Dp,
    private val stroke: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingVertical: Dp = btnPaddingVertical,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val strokeWidth: Dp = stroke,
    val iconSize: Dp = iconLarge,
    val elevation: Dp = noSpacing,
    val contentSpacing: Dp = paddingXLarge
)

// SecondaryTextButton
data class SecondaryTextButtonSpacings(
    private val iconMedium: Dp,
    private val textBtnSpacing: Dp,
    private val btnPaddingVertical: Dp,
    val iconSize: Dp = iconMedium,
    val spacing: Dp = textBtnSpacing,
    val paddingVertical: Dp = btnPaddingVertical
)

// SocialButton
data class SocialButtonSpacings(
    private val btnPaddingVertical: Dp,
    private val btnPaddingHorizontal: Dp,
    private val stroke: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingVertical: Dp = btnPaddingVertical,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val strokeWidth: Dp = stroke,
    val iconSize: Dp = iconLarge,
    val elevation: Dp = noSpacing,
    val contentSpacing: Dp = paddingXLarge
)

// TertiaryFilledButton
data class TertiaryFilledButtonSpacings(
    private val btnPaddingHorizontal: Dp,
    private val btnPaddingVertical: Dp,
    private val btnMinHeightNormal: Dp,
    private val btnMinHeightSmall: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val paddingVertical: Dp = btnPaddingVertical,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val iconSize: Dp = iconLarge,
    val elevation: Dp = noSpacing,
    val contentSpacing: Dp = paddingXLarge
)

// TertiaryStrokedButton
data class TertiaryStrokedButtonSpacings(
    private val btnPaddingVertical: Dp,
    private val btnPaddingHorizontal: Dp,
    private val btnMinHeightSmall: Dp,
    private val btnMinHeightNormal: Dp,
    private val stroke: Dp,
    private val iconLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    val paddingVertical: Dp = btnPaddingVertical,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val strokeWidth: Dp = stroke,
    val iconSize: Dp = iconLarge,
    val contentSpacing: Dp = paddingXLarge,
    val elevation: Dp = noSpacing
)

// TertiaryTextButton
data class TertiaryTextButtonSpacings(
    private val iconMedium: Dp,
    private val textBtnSpacing: Dp,
    private val btnPaddingVertical: Dp,
    val iconSize: Dp = iconMedium,
    val spacing: Dp = textBtnSpacing,
    val paddingVertical: Dp = btnPaddingVertical
)

// TransparentStrokedButton
data class TransparentStrokedButtonSpacings(
    private val btnPaddingVertical: Dp,
    private val btnPaddingHorizontal: Dp,
    private val noSpacing: Dp,
    private val stroke: Dp,
    private val iconLarge: Dp,
    private val btnMinHeightNormal: Dp,
    private val btnMinHeightSmall: Dp,
    private val paddingXLarge: Dp,
    val minHeightSmall: Dp = btnMinHeightSmall,
    val minHeightNormal: Dp = btnMinHeightNormal,
    val paddingVertical: Dp = btnPaddingVertical,
    val paddingHorizontal: Dp = btnPaddingHorizontal,
    val elevation: Dp = noSpacing,
    val strokeWidth: Dp = stroke,
    val iconSize: Dp = iconLarge,
    val contentSpacing: Dp = paddingXLarge
)

// DatePickerDialog
data class DatePickerDialogSpacings(
    private val paddingXLarge: Dp,
    private val noSpacing: Dp,
    private val paddingXXXLarge: Dp,
    private val datePickerHeight: Dp,
    private val pickerPadding: Dp,
    private val wheelPickerItemStroke: Dp,
    val paddingVertical: Dp = paddingXLarge,
    val paddingHorizontal: Dp = noSpacing,
    val btnPaddingHorizontal: Dp = paddingXLarge,
    val btnPaddingTop: Dp = paddingXXXLarge,
    val wheelHeight: Dp = datePickerHeight,
    val padding: Dp = pickerPadding,
    val showToolbar: Boolean = false,
    val selectorBorderWidth: Dp = wheelPickerItemStroke
)

// TimePickerDialog
data class TimePickerDialogSpacings(
    private val paddingXLarge: Dp,
    private val paddingXXLarge: Dp,
    private val paddingXXXLarge: Dp,
    private val datePickerWheelHeight: Dp,
    private val pickerPadding: Dp,
    private val datePickerHeight: Dp,
    private val wheelPickerItemStroke: Dp,
    val showToolbar: Boolean = false,
    val padding: Dp = paddingXLarge,
    val wheelPaddingHorizontal: Dp = paddingXXLarge,
    val btnPaddingTop: Dp = paddingXXXLarge,
    val wheelHeight: Dp = datePickerWheelHeight,
    val pickPadding: Dp = pickerPadding,
    val height: Dp = datePickerHeight,
    val selectorBorderWidth: Dp = wheelPickerItemStroke
)

// IconText
data class IconTextSpacings(
    private val paddingXLarge: Dp,
    private val paddingMedium: Dp,
    val iconSize: Dp = paddingXLarge,
    val iconSpacing: Dp = paddingMedium
)

// TextDivider
data class TextDividerSpacings(
    private val paddingXLarge: Dp,
    private val stroke: Dp,
    val paddingHorizontal: Dp = paddingXLarge,
    val strokeWidth: Dp = stroke
)

// AppImage
data class AppImageSpacings(
    private val noSpacing: Dp,
    val elevation: Dp = noSpacing
)

// GrayAppImage
data class GrayAppImageSpacings(
    private val noSpacing: Dp,
    val elevation: Dp = noSpacing
)

// WhiteAppImage
data class WhiteAppImageSpacings(
    private val noSpacing: Dp,
    val elevation: Dp = noSpacing
)

// LinedOtpInputField
data class LinedOtpInputFieldSpacings(
    private val paddingSmall: Dp,
    val horizontalSpacing: Dp = paddingSmall
)

// OutlinedOtpInputField
data class OutlinedOtpInputFieldSpacings(
    private val paddingLarge: Dp,
    val horizontalSpacing: Dp = paddingLarge
)

// PrimaryTextInputField
data class PrimaryTextInputFieldSpacings(
    private val noSpacing: Dp,
    private val iconSmall: Dp,
    private val inputFieldElevation: Dp,
    val minHeight: Dp = noSpacing,
    val startIconSize: Dp = iconSmall,
    val endIconSize: Dp = iconSmall,
    val elevation: Dp = inputFieldElevation
)

// SecondaryTextInputField
data class SecondaryTextInputFieldSpacings(
    private val noSpacing: Dp,
    private val iconSmall: Dp,
    private val inputFieldElevation: Dp,
    val minHeight: Dp = noSpacing,
    val startIconSize: Dp = iconSmall,
    val endIconSize: Dp = iconSmall,
    val elevation: Dp = inputFieldElevation
)

// TertiaryTextInputField
data class TertiaryTextInputFieldSpacings(
    private val noSpacing: Dp,
    private val iconSmall: Dp,
    private val inputFieldElevation: Dp,
    val minHeight: Dp = noSpacing,
    val startIconSize: Dp = iconSmall,
    val endIconSize: Dp = iconSmall,
    val elevation: Dp = inputFieldElevation
)

// OptionItem
data class OptionItemSpacings(
    private val paddingXXXLarge: Dp,
    private val paddingXLarge: Dp,
    private val iconLarge: Dp,
    private val iconMedium: Dp,
    val paddingVertical: Dp = paddingXXXLarge,
    val paddingHorizontal: Dp = paddingXXXLarge,
    val textSpacing: Dp = paddingXLarge,
    val iconSize: Dp = iconLarge,
    val arrowSize: Dp = iconMedium
)

// HorizontalDotsIndicator
data class HorizontalDotsIndicatorSpacings(
    private val paddingLarge: Dp,
    val spacing: Dp = paddingLarge
)

// HorizontalPagerIndicator
data class HorizontalPagerIndicatorSpacings(
    private val paddingMedium: Dp,
    val spacing: Dp = paddingMedium
)

// HorizontalPagerTabItem
data class HorizontalPagerTabItemSpacings(
    private val paddingMedium: Dp,
    private val paddingXLarge: Dp,
    val textPaddingVertical: Dp = paddingMedium,
    val textPaddingHorizontal: Dp = paddingXLarge
)

// HorizontalPagerTabs
data class HorizontalPagerTabsSpacings(
    private val paddingXLarge: Dp,
    val horizontalSpacing: Dp = paddingXLarge
)

// SmallProgressIndicator
data class SmallProgressIndicatorSpacings(
    private val paddingLarge: Dp,
    val padding: Dp = paddingLarge
)

// AppSnackBar
data class AppSnackBarSpacings(
    private val paddingXXLarge: Dp,
    private val paddingXXXLarge: Dp,
    private val iconLarge: Dp,
    private val paddingMedium: Dp,
    val paddingVertical: Dp = paddingXXLarge,
    val paddingHorizontal: Dp = paddingXXXLarge,
    val iconSize: Dp = iconLarge,
    val horizontalSpacing: Dp = paddingMedium
)

// AppDialog
data class AppDialogSpacings(
    private val paddingXLarge: Dp,
    private val popupPadding: Dp,
    val contentPadding: Dp = paddingXLarge,
    val containerPadding: Dp = popupPadding
)

// ChoicesDialog
data class ChoicesDialogSpacings(
    private val paddingSmall: Dp,
    val verticalSpacing: Dp = paddingSmall,
    val showToolbar: Boolean = true
)

// DialogToolbar
data class DialogToolbarSpacings(
    private val paddingXLarge: Dp,
    private val iconMedium: Dp,
    private val paddingMedium: Dp,
    val closeSize: Dp = paddingXLarge,
    val paddingHorizontal: Dp = paddingXLarge,
    val paddingTop: Dp = iconMedium,
    val paddingBottom: Dp = paddingMedium
)

// OtpDigit
data class OtpDigitSpacings(
    private val paddingXLarge: Dp,
    private val stroke: Dp,
    val paddingVertical: Dp = paddingXLarge,
    val lineSize: Dp = stroke
)

// MessageDialog
data class MessageDialogSpacings(
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    private val popupSpacingLarge: Dp,
    val noTitlePadding: Dp = noSpacing,
    val titlePadding: Dp = paddingXLarge,
    val btnPaddingTop: Dp = popupSpacingLarge,
    val showToolbar: Boolean = true,
    val bodyTextAlign: TextAlign = TextAlign.Center
)

// OverrideUserDialog
data class OverrideUserDialogSpacings(
    private val paddingXLarge: Dp,
    private val paddingXXXLarge: Dp,
    private val paddingLarge: Dp,
    val msgSpacing: Dp = paddingXLarge,
    val overrideBtnPadding: Dp = paddingXXXLarge,
    val resetBtnPadding: Dp = paddingLarge,
    val idTextPadding: Dp = paddingLarge,
    val showToolbar: Boolean = true,
    val bodyTextAlign: TextAlign = TextAlign.Center
)

// BottomSheetToolbar
data class BottomSheetToolbarSpacings(
    private val iconLarge: Dp,
    private val paddingXLarge: Dp,
    private val paddingXXSmall: Dp,
    private val paddingLarge: Dp,
    val iconSize: Dp = iconLarge,
    val boxPadding: Dp = paddingXLarge,
    val startIconPadding: Dp = paddingXXSmall,
    val endIconPadding: Dp = paddingXXSmall,
    val titlePadding: Dp = paddingLarge
)

// ConfirmationDialog
data class ConfirmationDialogSpacings(
    private val noSpacing: Dp,
    private val paddingXLarge: Dp,
    private val popupSpacingLarge: Dp,
    val noTitlePadding: Dp = noSpacing,
    val titlePadding: Dp = paddingXLarge,
    val buttonsSpacings: Dp = paddingXLarge,
    val buttonsTopPadding: Dp = popupSpacingLarge,
    val showToolbar: Boolean = true,
    val bodyTextAlign: TextAlign = TextAlign.Center
)

// ForceUpdateDialog
data class ForceUpdateDialogSpacings(
    private val paddingXLarge: Dp,
    private val popupSpacingLarge: Dp,
    val imageSize: Dp = 120.dp,
    val textPadding: Dp = paddingXLarge,
    val dialogPadding: Dp = paddingXLarge,
    val dialogSpacings: Dp = paddingXLarge,
    val horizontalButtonsPadding: Dp = popupSpacingLarge,
)

// SuccessDialog
data class SuccessDialogSpacings(
    private val popupIconLarge: Dp,
    private val popupSpacingMedium: Dp,
    private val popupSpacingLarge: Dp,
    val iconSize: Dp = popupIconLarge,
    val bodyPaddingTop: Dp = popupSpacingMedium,
    val btnPaddingTop: Dp = popupSpacingLarge,
    val showToolbar: Boolean = true,
    val bodyTextAlign: TextAlign = TextAlign.Center
)

// PriceTextInputField
data class PriceTextInputFieldSpacings(
    private val iconSmall: Dp,
    private val noSpacing: Dp,
    private val inputFieldElevation: Dp,
    val startIconSize: Dp = iconSmall,
    val endIconSize: Dp = iconSmall,
    val minHeight: Dp = noSpacing,
    val elevation: Dp = inputFieldElevation
)

// Dot
data class DotSpacings(
    private val noSpacing: Dp,
    val elevation: Dp = noSpacing
)

// ItemPicker
data class ItemPickerSpacings(
    private val paddingXLarge: Dp,
    private val itemPickerItemStroke: Dp,
    val searchFieldPadding: Dp = paddingXLarge,
    val selectorBorderWidth: Dp = itemPickerItemStroke,
    val wheelHeight: Dp = 270.dp
)

// Toolbar
data class ToolbarSpacings(
    private val paddingMedium: Dp,
    private val toolbarIconMinSize: Dp,
    val height: Dp = 62.dp,
    val containerPaddingStart: Dp = paddingMedium,
    val startIconMinHeight: Dp = toolbarIconMinSize,
    val startIconMinWidth: Dp = toolbarIconMinSize,
    val endIconMinHeight: Dp = toolbarIconMinSize,
    val endIconMinWidth: Dp = toolbarIconMinSize
)

// WheelTextPicker
data class WheelTextPickerSpacings(
    private val defaultWheelPickerWidth: Dp,
    private val defaultWheelPickerHeight: Dp,
    val pickerWidth: Dp = defaultWheelPickerWidth,
    val pickerHeight: Dp = defaultWheelPickerHeight
)

// WheelPicker
data class WheelPickerSpacings(
    private val defaultWheelPickerWidth: Dp,
    private val defaultWheelPickerHeight: Dp,
    val pickerWidth: Dp = defaultWheelPickerWidth,
    val pickerHeight: Dp = defaultWheelPickerHeight
)

// SelectorProperties
data class SelectorPropertiesSpacings(
    private val wheelPickerItemStroke: Dp,
    val borderWidth: Dp = wheelPickerItemStroke
)

// WheelTimePicker
data class WheelTimePickerSpacings(
    private val defaultWheelPickerWidth: Dp,
    private val defaultWheelPickerHeight: Dp,
    val pickerWidth: Dp = defaultWheelPickerWidth,
    val pickerHeight: Dp = defaultWheelPickerHeight
)

// WheelDateTimePicker
data class WheelDateTimePickerSpacings(
    private val defaultWheelPickerHeight: Dp,
    private val defaultWheelPickerWidth: Dp,
    val pickerHeight: Dp = defaultWheelPickerHeight,
    val pickerWidth: Dp = defaultWheelPickerWidth
)


// WheelDatePicker
data class WheelDatePickerSpacings(
    private val defaultWheelPickerHeight: Dp,
    private val defaultWheelPickerWidth: Dp,
    val pickerHeight: Dp = defaultWheelPickerHeight,
    val pickerWidth: Dp = defaultWheelPickerWidth
)

// DefaultWheelTimePicker
data class DefaultWheelTimePickerSpacings(
    private val defaultWheelPickerHeight: Dp,
    private val defaultWheelPickerWidth: Dp,
    val pickerHeight: Dp = defaultWheelPickerHeight,
    val pickerWidth: Dp = defaultWheelPickerWidth
)

// DefaultWheelDateTimePicker
data class DefaultWheelDateTimePickerSpacings(
    private val defaultWheelPickerHeight: Dp,
    private val defaultWheelPickerWidth: Dp,
    val pickerHeight: Dp = defaultWheelPickerHeight,
    val pickerWidth: Dp = defaultWheelPickerWidth
)

// DefaultWheelDatePicker
data class DefaultWheelDatePickerSpacings(
    private val defaultWheelPickerHeight: Dp,
    private val defaultWheelPickerWidth: Dp,
    val pickerHeight: Dp = defaultWheelPickerHeight,
    val pickerWidth: Dp = defaultWheelPickerWidth
)

// TabItem
data class TabItemSpacings(
    val activeIndicatorThickness: Dp = 3.dp,
    val inactiveIndicatorThickness: Dp = 1.dp,
    val textPadding: Dp = 4.dp,
    val iconSize: Dp = 24.dp,
    val itemMinWidth: Dp = 120.dp
)

// SwitchButton
data class SwitchButtonSpacings(
    val thumbElevation: Dp = 0.dp,
    val thumbPadding: Dp = 0.dp
)

val LocalCoreSpacings = staticCompositionLocalOf { CoreSpacings() }