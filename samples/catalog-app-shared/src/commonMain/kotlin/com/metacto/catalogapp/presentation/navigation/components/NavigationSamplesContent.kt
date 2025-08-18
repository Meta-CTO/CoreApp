package com.metacto.catalogapp.presentation.navigation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.navigation.NavigationSamplesContract.Event
import com.metacto.catalogapp.presentation.navigation.NavigationSamplesContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.buttons.SecondaryFilledButton
import com.metacto.core.ui.components.cards.DefaultCard
import com.metacto.core.ui.components.dividers.DefaultDivider
import com.metacto.core.ui.components.texts.BodyText
import com.metacto.core.ui.components.texts.SubtitleText

@Composable
internal fun NavigationSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    AppScreenColumn(
        title = "Navigation Samples",
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(spacings.spacing16),
            verticalArrangement = Arrangement.spacedBy(spacings.spacing16)
        ) {
            // Basic Navigation Section
            DefaultCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(spacings.spacing12),
                    verticalArrangement = Arrangement.spacedBy(spacings.spacing8)
                ) {
                    SubtitleText(
                        text = "Basic Navigation"
                    )
                    
                    PrimaryFilledButton(
                        text = "Navigate to First Sample",
                        onClick = { onEvent(Event.NavigateToFirstSample) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    PrimaryFilledButton(
                        text = "Navigate to Second (SingleTop)",
                        onClick = { onEvent(Event.NavigateToSecondSample) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    PrimaryFilledButton(
                        text = "Navigate to Third Sample",
                        onClick = { onEvent(Event.NavigateToThirdSample) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Advanced Navigation Section
            DefaultCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(spacings.spacing12),
                    verticalArrangement = Arrangement.spacedBy(spacings.spacing8)
                ) {
                    SubtitleText(
                        text = "Advanced Navigation"
                    )
                    
                    SecondaryFilledButton(
                        text = "Navigate & Pop Current",
                        onClick = { onEvent(Event.NavigateAndPopCurrent) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    SecondaryFilledButton(
                        text = "Clear & Navigate",
                        onClick = { onEvent(Event.ClearAndNavigate) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    SecondaryFilledButton(
                        text = "Pop to First Screen",
                        onClick = { onEvent(Event.PopToFirst) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    SecondaryFilledButton(
                        text = "Pop By 2 Screens",
                        onClick = { onEvent(Event.PopByTwo) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Screen Info Section
            DefaultCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(spacings.spacing12),
                    verticalArrangement = Arrangement.spacedBy(spacings.spacing8)
                ) {
                    SubtitleText(
                        text = "Screen Information"
                    )
                    
                    PrimaryFilledButton(
                        text = "Get Last Screen",
                        onClick = { onEvent(Event.GetLastScreen) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    PrimaryFilledButton(
                        text = "Check Screens in Stack",
                        onClick = { onEvent(Event.CheckCurrentScreen) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    if (state.lastScreen != null) {
                        BodyText(
                            text = "Last Screen: ${state.lastScreen::class.simpleName}",
                            modifier = Modifier.padding(top = spacings.spacing4)
                        )
                    }
                    
                    if (state.currentScreenInfo.isNotEmpty()) {
                        BodyText(
                            text = state.currentScreenInfo,
                            modifier = Modifier.padding(top = spacings.spacing4)
                        )
                    }
                }
            }

            // Navigation with Results Section
            DefaultCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(spacings.spacing12),
                    verticalArrangement = Arrangement.spacedBy(spacings.spacing8)
                ) {
                    SubtitleText(
                        text = "Navigation with Results"
                    )
                    
                    PrimaryFilledButton(
                        text = "Navigate for Result",
                        onClick = { onEvent(Event.NavigateWithResult) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    SecondaryFilledButton(
                        text = "Go Back with Result",
                        onClick = { onEvent(Event.GoBackWithResult) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    SecondaryFilledButton(
                        text = "Go Back",
                        onClick = { onEvent(Event.GoBack) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Navigation History
            if (state.navigationHistory.isNotEmpty()) {
                DefaultCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(spacings.spacing12),
                        verticalArrangement = Arrangement.spacedBy(spacings.spacing4)
                    ) {
                        SubtitleText(
                            text = "Navigation History"
                        )
                        
                        DefaultDivider(modifier = Modifier.padding(vertical = spacings.spacing8))
                        
                        state.navigationHistory.forEach { action ->
                            BodyText(
                                text = "• $action"
                            )
                        }
                    }
                }
            }
        }
    }
}