package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.inputFields.CurrencyAmountInputVisualTransformation
import com.metacto.core.presentation.components.inputFields.PickerInputField
import com.metacto.core.presentation.components.inputFields.PriceTextInputField
import com.metacto.core.presentation.components.inputFields.PrimaryTextInputField
import com.metacto.core.presentation.components.tabsLayout.TabItemModel
import com.metacto.core.presentation.components.tabsLayout.TabsLayout
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.CountDownTimer
import com.metacto.core.utils.asCommon
import com.sampleApp.app.MR
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SplashContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val modifier = if (state.isWelcome) Modifier.background(Color.Blue) else Modifier
    var currentVideo by remember {
        mutableStateOf(videosList()[0])
    }
    val tabs = listOf(
        TabItemModel(
            title = "Ahmed",
            activeIcon = MR.images.ic_star_filled.asCommon(),
            inactiveIcon = MR.images.ic_star_empty.asCommon()
        ),
        TabItemModel(
            title = "Shamy",
            activeIcon = MR.images.ic_star_filled.asCommon(),
            inactiveIcon = MR.images.ic_star_empty.asCommon()
        ),
//        TabItemModel(
//            title = "Shamy",
//            activeIcon = MR.images.ic_star_filled.asCommon(),
//            inactiveIcon = MR.images.ic_star_empty.asCommon()
//        ),
//        TabItemModel(
//            title = "Shamy",
//            activeIcon = MR.images.ic_star_filled.asCommon(),
//            inactiveIcon = MR.images.ic_star_empty.asCommon()
//        ),
//        TabItemModel(
//            title = "Shamy",
//            activeIcon = MR.images.ic_star_filled.asCommon(),
//            inactiveIcon = MR.images.ic_star_empty.asCommon()
//        ),
//        TabItemModel(
//            title = "Shamy",
//            activeIcon = MR.images.ic_star_filled.asCommon(),
//            inactiveIcon = MR.images.ic_star_empty.asCommon()
//        ),
    )
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val countDownTimer = remember {
        CountDownTimer(
            seconds = 10,
            onSecondsTick = {
                println("Timer ticked: $it")
            },
            onStopped = {
                println("Timer stopped")
            },
            onEnded = {
                println("Timer ended")
            }
        )
    }
    val coroutineScope = rememberCoroutineScope()

    ScreenColumn(
        isRefreshable = false,
        isScrollable = false,
        isRefreshing = false,
        enableSafeInsets = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        topPadding = 4.dp,
        startPadding = 0.dp,
        endPadding = 0.dp
    ) {

        // Community search tabs
        TabsLayout(
            tabModels = tabs.toImmutableList(),
            currentPage = pagerState.currentPage,
            showIndicator = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onTabClicked = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        )
        // Screens pager
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 20.dp)
        ) { page ->
            when (page) {
                1 -> {
                    PrimaryFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Click Me!",
                        onClick = {
                            onEvent(Event.ClickMeClicked)
                        }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .background(Color.Red)
                    ) {

                        VideoPlayer(
                            url = currentVideo,
                            autoPlay = true,
                            scaleToCrop = false,
                            enablePip = true,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PrimaryFilledButton(
                            modifier = Modifier.weight(1f),
                            text = "Video 1",
                            onClick = {
                                //currentVideo = videosList()[0]
                                coroutineScope.launch {
                                    countDownTimer.start()
                                }
                            }
                        )

                        PrimaryFilledButton(
                            modifier = Modifier.weight(1f),
                            text = "Video 2",
                            onClick = {
//                    currentVideo = videosList()[1]
                                coroutineScope.launch {
                                    countDownTimer.stop()
                                }
                            }
                        )

                        PrimaryFilledButton(
                            modifier = Modifier.weight(1f),
                            text = "Video 3",
                            onClick = {
//                    currentVideo = videosList()[2]
                            }
                        )
                    }
                }

                2 -> {
                    var price: Int? by remember {
                        mutableStateOf(null)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                            .padding(16.dp)
                    ) {

                        PriceTextInputField(
                            modifier = Modifier.fillMaxWidth(),
                            price = price,
                            isStaticLabel = false,
                            label = "Hello Ahmed",
                            placeholder = "test",
                            onPriceChange = { price = it }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                            .padding(16.dp)
                    ) {
                        PickerInputField(
                            modifier = Modifier.fillMaxWidth(),
                            isStaticLabel = true,
                            label = "Test Picker",
                            placeholder = "test",
                            text = "Ahmed",
                            shape = CoreTheme.shapes.xLarge,
                            onClick = { onEvent(Event.ClickMeClicked) }
                        )
                    }

                }

                else -> {
                    var fieldText by remember {
                        mutableStateOf("")
                    }
                    val visualTransformation = remember {
                        CurrencyAmountInputVisualTransformation()
                    }
                    PrimaryTextInputField(
                        modifier = Modifier.fillMaxWidth(),
                        text = fieldText,
                        onValueChange = {
                            fieldText = it
                        },
                        visualTransformation = visualTransformation,
                        allowDigitsOnly = true,
                        keyboardType = KeyboardType.Number
                    )

                    PrimaryFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Navigate to Youtube",
                        onClick = {
                            onEvent(Event.NavigateToYoutube)
                        }
                    )
                }
            }

        }
    }
}

private fun videosList() = listOf(
    "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
    "https://storage.sardius.media/1088c54907d9370/archives/f15706391E53Fb46B0B52E92786B/media/playlist.m3u8",
    "https://storage.sardius.media/1088c54907d9370/archives/f15706391E53Fb46B0B52E92786B/media/playlist.m3u8?feedId=CA8a1f507a"
)