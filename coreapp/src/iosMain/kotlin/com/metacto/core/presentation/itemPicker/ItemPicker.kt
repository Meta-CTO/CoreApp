package com.metacto.core.presentation.itemPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.metacto.core.presentation.theme.CoreTheme.spacings
import com.metacto.core.utils.extensions.noRippleClickable
import com.metacto.coreApp.MR
import com.multiplatform.webview.util.toUIColor
import dev.icerock.moko.resources.compose.stringResource
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UILabel
import platform.UIKit.UIPickerView
import platform.UIKit.UIPickerViewDataSourceProtocol
import platform.UIKit.UIPickerViewDelegateProtocol
import platform.UIKit.UITextAlignmentCenter
import platform.UIKit.UIView
import platform.darwin.NSInteger
import platform.darwin.NSObject

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun ItemPicker(
    modifier: Modifier,
    items: List<PickerItem>,
    selectedItem: PickerItem?,
    onItemSelected: ((PickerItem) -> Unit)?,
    onDismissRequested: () -> Unit
) {
    // Main stuff
    val selectedItemIndex = remember(items, selectedItem) {
        items.indexOfFirst { it.key == selectedItem?.key }.takeIf { it != -1 }
    }

    // Create picker delegate
    val pickerDelegate = remember(items) {
        object : NSObject(), UIPickerViewDelegateProtocol {
            override fun pickerView(
                pickerView: UIPickerView,
                titleForRow: NSInteger,
                forComponent: NSInteger
            ): String {
                return items[titleForRow.toInt()].title
            }

            override fun pickerView(
                pickerView: UIPickerView,
                didSelectRow: NSInteger,
                inComponent: NSInteger
            ) {
                val itemIndex = didSelectRow.toInt()
                if (itemIndex < items.size) {
                    onItemSelected?.invoke(items[itemIndex])
                }
            }

            override fun pickerView(
                pickerView: UIPickerView,
                viewForRow: NSInteger,
                forComponent: NSInteger,
                reusingView: UIView?
            ): UIView {
                // Reuse view if possible
                val label = (reusingView as? UILabel) ?: UILabel()

                label.apply {
                    text = items[viewForRow.toInt()].title
                    textAlignment = UITextAlignmentCenter
                    textColor = UIColor.blackColor
                    font = UIFont.systemFontOfSize(20.0)
                }
                return label
            }

            // Add width and height for better touch area
            override fun pickerView(
                pickerView: UIPickerView,
                rowHeightForComponent: NSInteger
            ): Double = 44.0

            override fun pickerView(
                pickerView: UIPickerView,
                widthForComponent: NSInteger
            ): Double = 280.0
        }
    }

    // Create picker data source
    val pickerDataSource = remember(items) {
        object : NSObject(), UIPickerViewDataSourceProtocol {
            override fun numberOfComponentsInPickerView(pickerView: UIPickerView) = 1L

            override fun pickerView(
                pickerView: UIPickerView,
                numberOfRowsInComponent: NSInteger
            ): NSInteger = items.size.toLong()
        }
    }

    // Create the picker view
    val pickerBgColor = colors.nativeItemPicker.pickerBackground
    val pickerView = remember(items) {
        UIPickerView().apply {
            delegate = pickerDelegate
            dataSource = pickerDataSource
            setBackgroundColor(pickerBgColor.toUIColor())

            // Set initial selection
            selectedItemIndex?.let {
                selectRow(it.toLong(), inComponent = 0, animated = false)
            }

            setNeedsLayout()
        }
    }

    // Container column
    Column(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable {  }
    ) {
        // Rest of your code remains the same...
        HeaderDivider()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.nativeItemPicker.headerBg)
                .padding(vertical = spacings.nativeItemPicker.headerVerticalPadding)
                .padding(horizontal = spacings.nativeItemPicker.headerHorizontalPadding)
        ) {
            Text(
                text = stringResource(MR.strings.cancel),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    color = colors.nativeItemPicker.headerLabel,
                    fontSize = spacings.nativeItemPicker.headerLabelFontSize
                ),
                modifier = Modifier.noRippleClickable(
                    onClick = onDismissRequested
                )
            )

            Text(
                text = stringResource(MR.strings.action_done),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = colors.nativeItemPicker.headerLabel,
                    fontSize = spacings.nativeItemPicker.headerLabelFontSize
                ),
                modifier = Modifier.noRippleClickable(
                    onClick = onDismissRequested
                )
            )
        }

        HeaderDivider()

        UIKitView(
            factory = {
                pickerView.apply {
                    // Set initial setup
                    delegate = pickerDelegate
                    dataSource = pickerDataSource
                    userInteractionEnabled = true

                    // Set initial selected row
                    selectedItemIndex?.let {
                        selectRow(it.toLong(), inComponent = 0, animated = false)
                    }

                    // Trigger layout update once during setup
                    setNeedsLayout()
                    layoutIfNeeded()
                }
            },
            properties = UIKitInteropProperties(
                isNativeAccessibilityEnabled = true
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
private fun HeaderDivider() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = spacings.nativeItemPicker.headerDividerThickness,
        color = colors.nativeItemPicker.headerDivider
    )
}