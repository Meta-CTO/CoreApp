@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.IResourceProvider
import com.metacto.core.utils.extensions.orZero
import com.metacto.core.utils.extensions.uiColor
import com.metacto.coreApp.MR
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSSelectorFromString
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIApplication
import platform.UIKit.UIButton
import platform.UIKit.UIButtonTypeSystem
import platform.UIKit.UIControlEventTouchUpInside
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIPickerView
import platform.UIKit.UIPickerViewDataSourceProtocol
import platform.UIKit.UIPickerViewDelegateProtocol
import platform.UIKit.UIView
import platform.darwin.NSInteger

internal actual class NativeItemPicker(
    private val resourceProvider: IResourceProvider
) {
    private val pickerView by lazy {
        PopupPickerView(resourceProvider)
    }

    actual fun display(
        items: List<PickerItem>,
        selectedItem: PickerItem?,
        onItemSelected: (PickerItem) -> Unit
    ) {
        // Prepare selected item index
        val selectedItemIndex = items
            .takeIf { selectedItem != null }
            ?.indexOfFirst { it.key == selectedItem?.key }
            ?.takeIf { it != -1 }
            .orZero()

        pickerView.display(
            items = items.map { it.title },
            defaultIndex = selectedItemIndex,
            onDone = { selectedIndex ->
                items.getOrNull(selectedIndex)?.let {
                    onItemSelected.invoke(it)
                }
            }
        )
    }
}

private data object Colors {
    val viewBg = uiColor(0x33000000)
    val headerBg = uiColor(0xFFF7F7F7)
    val headerLabel = uiColor(0xFF007AFF)
    val pickerBackground = uiColor(0xFFD1D4D9)
}

private class PopupPickerView(
    private val resourceProvider: IResourceProvider
) : UIView(
    frame = CGRectZero.readValue()
), UIPickerViewDataSourceProtocol, UIPickerViewDelegateProtocol {
    // Items and handlers
    private var items: List<String>? = null
    private var onDone: ((Int) -> Unit)? = null

    // Views
    private var pickerViewConstraint: NSLayoutConstraint? = null
    private val pickerView by lazy {
        UIPickerView().apply {
            translatesAutoresizingMaskIntoConstraints = false
            backgroundColor = Colors.pickerBackground
            dataSource = this@PopupPickerView
            delegate = this@PopupPickerView
        }
    }
    private val doneButton by lazy {
        UIButton.buttonWithType(buttonType = UIButtonTypeSystem)
    }
    private val cancelButton by lazy {
        UIButton.buttonWithType(buttonType = UIButtonTypeSystem)
    }
    private val headerView by lazy {
        UIView().apply { backgroundColor = Colors.headerBg }
    }

    init {
        setupPickerView()
        setupHeaderView()
        setupCancelArea()
    }

    private fun setupPickerView() {
        backgroundColor = Colors.viewBg
        addSubview(pickerView)
        pickerView.let {
            pickerViewConstraint = it.bottomAnchor.constraintEqualToAnchor(bottomAnchor, constant = 500.0)
            pickerViewConstraint?.active = true
            it.leftAnchor.constraintEqualToAnchor(leftAnchor).active = true
            it.rightAnchor.constraintEqualToAnchor(rightAnchor).active = true
        }
    }

    private fun setupHeaderView() {
        addSubview(headerView)

        // Config header view
        headerView.let {
            it.translatesAutoresizingMaskIntoConstraints = false
            it.leftAnchor.constraintEqualToAnchor(leftAnchor).active = true
            it.rightAnchor.constraintEqualToAnchor(rightAnchor).active = true
            it.bottomAnchor.constraintEqualToAnchor(pickerView.topAnchor).active = true
            it.heightAnchor.constraintEqualToConstant(40.0).active = true
        }

        // Config cancel button
        doneButton.let {
            it.setTitle(
                title = resourceProvider.getString(MR.strings.action_done),
                forState = UIControlStateNormal
            )
            it.setTitleColor(
                color = Colors.headerLabel,
                forState = UIControlStateNormal
            )
            it.addTarget(
                target = this,
                action = NSSelectorFromString("done"),
                forControlEvents = UIControlEventTouchUpInside
            )
            headerView.addSubview(it)
            it.translatesAutoresizingMaskIntoConstraints = false
            it.rightAnchor.constraintEqualToAnchor(headerView.rightAnchor, constant = -16.0).active = true
            it.centerYAnchor.constraintEqualToAnchor(headerView.centerYAnchor).active = true
        }

        // Config cancel button
        cancelButton.let {
            it.setTitle(
                title = resourceProvider.getString(MR.strings.cancel),
                forState = UIControlStateNormal
            )
            it.setTitleColor(
                color = Colors.headerLabel,
                forState = UIControlStateNormal
            )
            it.addTarget(
                target = this,
                action = NSSelectorFromString("cancel"),
                forControlEvents = UIControlEventTouchUpInside
            )
            headerView.addSubview(it)
            it.translatesAutoresizingMaskIntoConstraints = false
            it.leftAnchor.constraintEqualToAnchor(headerView.leftAnchor, constant = 16.0).active = true
            it.centerYAnchor.constraintEqualToAnchor(headerView.centerYAnchor).active = true
        }
    }

    private fun setupCancelArea() {
        UIButton().let {
            this@PopupPickerView.addSubview(it)
            it.translatesAutoresizingMaskIntoConstraints = false
            it.topAnchor.constraintEqualToAnchor(topAnchor).active = true
            it.leftAnchor.constraintEqualToAnchor(leftAnchor).active = true
            it.rightAnchor.constraintEqualToAnchor(rightAnchor).active = true
            it.bottomAnchor.constraintEqualToAnchor(headerView.topAnchor).active = true
            it.addTarget(
                target = this@PopupPickerView,
                action = NSSelectorFromString("cancel"),
                forControlEvents = UIControlEventTouchUpInside
            )
        }
    }

    @ObjCAction
    fun done() {
        val selectedIndex = pickerView.selectedRowInComponent(0).toInt()
        onDone?.invoke(selectedIndex)
        onDone = null
        animateDismiss()
    }

    @ObjCAction
    fun cancel() {
        onDone = null
        animateDismiss()
    }

    fun display(items: List<String>, defaultIndex: Int = 0, onDone: (Int) -> Unit) {
        this.items = items
        this.onDone = onDone
        pickerView.reloadAllComponents()
        pickerView.selectRow(defaultIndex.toLong(), inComponent = 0, animated = true)
        addToKeyWindow()
        animateDisplay()
    }

    private fun addToKeyWindow() {
        val keyWindow = UIApplication.sharedApplication.keyWindow ?: return
        translatesAutoresizingMaskIntoConstraints = false
        keyWindow.addSubview(this)
        NSLayoutConstraint.activateConstraints(
            listOf(
                topAnchor.constraintEqualToAnchor(keyWindow.topAnchor),
                leftAnchor.constraintEqualToAnchor(keyWindow.leftAnchor),
                bottomAnchor.constraintEqualToAnchor(keyWindow.bottomAnchor),
                rightAnchor.constraintEqualToAnchor(keyWindow.rightAnchor)
            )
        )
        layoutIfNeeded()
    }

    private fun animateDisplay() {
        pickerViewConstraint?.constant = 0.0
        UIView.animateWithDuration(0.25) {
            this.layoutIfNeeded()
        }
    }

    fun animateDismiss() {
        pickerViewConstraint?.constant = 500.0
        UIView.animateWithDuration(
            duration = 0.25,
            animations = {
                this.layoutIfNeeded()
            },
            completion = { _ ->
                this.removeFromSuperview()
            }
        )
    }

    override fun numberOfComponentsInPickerView(pickerView: UIPickerView): Long = 1

    override fun pickerView(pickerView: UIPickerView, numberOfRowsInComponent: Long): Long {
        return (items?.size ?: 0).toLong()
    }

    override fun pickerView(
        pickerView: UIPickerView,
        titleForRow: NSInteger,
        forComponent: NSInteger
    ): String? {
        return items?.get(titleForRow.toInt())
    }
}
