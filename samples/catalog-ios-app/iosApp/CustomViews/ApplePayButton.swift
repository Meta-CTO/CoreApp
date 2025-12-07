//
//  ApplePayButton.swift
//  iosApp
//
//  Created by Mahmoud Elshamy on 06/10/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import UIKit
import PassKit
import appShared

final class ApplePayButton: UIView {
    private var config: UIViewType.ApplePayButton?
    private var paymentButton: PKPaymentButton!

    init(frame: CGRect, config: UIViewType.ApplePayButton) {
        self.config = config
        super.init(frame: frame)
        setupButton()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setupButton()
    }

    private func setupButton() {
        paymentButton = PKPaymentButton(
            paymentButtonType: .continue,
            paymentButtonStyle: .black
        )

        paymentButton.translatesAutoresizingMaskIntoConstraints = false
        paymentButton.addTarget(self, action: #selector(handlePaymentButtonTapped), for: .touchUpInside)

        addSubview(paymentButton)

        NSLayoutConstraint.activate([
            paymentButton.topAnchor.constraint(equalTo: topAnchor),
            paymentButton.leadingAnchor.constraint(equalTo: leadingAnchor),
            paymentButton.trailingAnchor.constraint(equalTo: trailingAnchor),
            paymentButton.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }

    @objc private func handlePaymentButtonTapped() {
        config?.onClick()
    }
}
