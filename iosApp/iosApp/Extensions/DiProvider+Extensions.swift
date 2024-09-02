//
//  DiProvider+Extensions.swift
//  iosApp
//
//  Created by Mahmoud Elshamy on 03/09/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import sampleAppShared

extension DiProvider {

    func get<T: AnyObject>(clazz: KotlinKClass) -> T {
        return getDependency(clazz: clazz) as! T
    }
}
