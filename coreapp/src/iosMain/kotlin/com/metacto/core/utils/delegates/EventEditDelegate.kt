package com.metacto.core.utils.delegates

import platform.EventKitUI.EKEventEditViewAction
import platform.EventKitUI.EKEventEditViewController
import platform.EventKitUI.EKEventEditViewDelegateProtocol
import platform.darwin.NSObject

class EventEditDelegate: NSObject(), EKEventEditViewDelegateProtocol {
    override fun eventEditViewController(controller: EKEventEditViewController, didCompleteWithAction: EKEventEditViewAction) {
        controller.dismissViewControllerAnimated(true, null)
    }
}