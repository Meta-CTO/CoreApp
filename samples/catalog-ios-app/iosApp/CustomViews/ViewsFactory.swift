import UIKit
import Foundation
import appShared

public class ViewsFactory: NSObject, IViewsFactory {
    
    public func create(type: UIViewControllerType) -> UIViewController {
        // Add implementations here when needed
        return UIViewController()
    }
    
    public func create(type_ type: UIViewType) -> UIView {
        switch type {
        case is UIViewType.ApplePayButton:
            return ApplePayButton(
                frame: .zero,
                config: type as! UIViewType.ApplePayButton
            )
            
            
        default:
            return UIView()
        }
    }
}
