package com.metacto.catalogapp.presentation.app.viewsFactory

import platform.UIKit.UIView
import platform.UIKit.UIViewController

interface IViewsFactory {
    fun create(type: UIViewControllerType): UIViewController
    fun create(type: UIViewType): UIView
}