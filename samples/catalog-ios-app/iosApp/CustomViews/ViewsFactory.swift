import UIKit
import Foundation
import appShared

public class ViewsFactory: NSObject, IViewsFactory {
    
    public func create(type: UIViewControllerType) -> UIViewController {
        // Add implementations here when needed
        return UIViewController()
    }
    
    public func create(type_ type: UIViewType) -> UIView {
        // Add implementations here when needed
        return UIView()
    }
}