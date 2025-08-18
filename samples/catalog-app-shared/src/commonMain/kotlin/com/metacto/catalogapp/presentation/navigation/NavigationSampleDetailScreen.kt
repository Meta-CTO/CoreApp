package com.metacto.catalogapp.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.buttons.SecondaryFilledButton
import com.metacto.core.ui.components.cards.SimpleCard
import com.metacto.core.ui.components.texts.SimpleText
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.ui.navigation.NavScreen
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

internal data class NavigationSampleDetailScreen(
    val title: String,
    val description: String,
    val canSendResult: Boolean = false
) : BaseScreen<NavigationSampleDetailViewModel>(), NavScreen {

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<NavigationSampleDetailViewModel>()
        val navManager = koinInject<NavManager>()
        
        AppScreenColumn(
            title = title,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacings.spacing16),
                verticalArrangement = Arrangement.spacedBy(spacings.spacing16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(spacings.spacing16),
                        verticalArrangement = Arrangement.spacedBy(spacings.spacing8)
                    ) {
                        SubtitleText(
                            text = "Screen Details"
                        )
                        
                        BodyText(
                            text = description
                        )
                        
                        CaptionText(
                            text = "Screen class: ${this@NavigationSampleDetailScreen::class.simpleName}"
                        )
                    }
                }
                
                PrimaryFilledButton(
                    text = "Navigate to Another Detail",
                    onClick = {
                        navManager.navigate(
                            NavigationSampleDetailScreen(
                                title = "Another Detail Screen",
                                description = "Navigated from: $title"
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                SecondaryFilledButton(
                    text = "Get Last Screen Info",
                    onClick = {
                        viewModel.getLastScreenInfo()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (canSendResult) {
                    PrimaryFilledButton(
                        text = "Send Result Back",
                        onClick = {
                            navManager.sendResult(
                                source = NavigationSampleDetailScreen::class.simpleName,
                                result = "Result from: $title"
                            )
                            navManager.goBack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                SecondaryFilledButton(
                    text = "Go Back",
                    onClick = {
                        navManager.goBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                viewModel.viewState.value.lastScreenInfo?.let { info ->
                    DefaultCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BodyText(
                            text = info,
                            modifier = Modifier.padding(spacings.spacing12)
                        )
                    }
                }
            }
        }
    }
}

internal class NavigationSampleDetailViewModel(
    private val navManager: NavManager
) : BaseViewModel<NavigationSampleDetailState, NavigationSampleDetailEvent, NavigationSampleDetailEffect>() {
    
    override fun setInitialState() = NavigationSampleDetailState()
    
    override fun handleEvents(event: NavigationSampleDetailEvent): Any = when (event) {
        NavigationSampleDetailEvent.GetLastScreen -> getLastScreenInfo()
    }
    
    fun getLastScreenInfo() {
        screenModelScope.launch {
            val lastScreen = navManager.getLastScreen()
            val info = lastScreen?.let {
                "Last screen in stack: ${it::class.simpleName}"
            } ?: "No previous screen found"
            
            setState {
                copy(lastScreenInfo = info)
            }
        }
    }
}

internal data class NavigationSampleDetailState(
    val lastScreenInfo: String? = null
) : ViewState

internal sealed interface NavigationSampleDetailEvent : ViewEvent {
    data object GetLastScreen : NavigationSampleDetailEvent
}

internal sealed interface NavigationSampleDetailEffect : ViewSideEffect