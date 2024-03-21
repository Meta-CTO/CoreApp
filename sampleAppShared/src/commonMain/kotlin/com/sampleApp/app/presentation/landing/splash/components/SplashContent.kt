package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.images.WhiteAppImage
import com.metacto.core.presentation.components.inputFields.TertiaryTextInputField
import com.metacto.core.utils.PhoneNumberVisualTransformation
import com.metacto.core.utils.extensions.rememberPhoneNumberUtil
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import com.sampleApp.app.presentation.theme.AppTheme
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun SplashContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val modifier = if (state.isWelcome) Modifier.background(Color.Blue) else Modifier

    ScreenColumn(
        isRefreshable = true,
        isRefreshing = true
    ) {

//        WheelDatePicker(
//            size = DpSize(getScreenSize().first.toDp(), 128.dp),
//            maxDate = LocalDate.now()
//        ) { date ->
//
//        }
//
//        WheelTimePicker(
//            size = DpSize(getScreenSize().first.toDp(), 128.dp),
//            timeFormat = TimeFormat.AM_PM
//        ) {
//            println("Selected time: $it")
//        }




        Text(
            text = if (state.isWelcome) "Welcome" else "Sample App",
            style = AppTheme.typography.fenwickBold24,
            color = Color.Red,
            modifier = modifier
                .clickable { onEvent(Event.TextClicked) }
        )

        Text(
            text = "Share Hello World!",
            style = AppTheme.typography.fenwickBold24,
            color = Color.Red,
            modifier = modifier
                .clickable { onEvent(Event.ShareHelloWorldClicked) }
        )

        Text(
            text = "Share https://parrotclub.co",
            style = AppTheme.typography.fenwickBold24,
            color = Color.Red,
            modifier = modifier
                .clickable { onEvent(Event.ShareParrotClub1) }
        )

        Text(
            text = "Share www.parrotclub.co",
            style = AppTheme.typography.fenwickBold24,
            color = Color.Red,
            modifier = modifier
                .clickable { onEvent(Event.ShareParrotClub2) }
        )

        // Phone number field
        val phoneNumberUtil = rememberPhoneNumberUtil()
        val phoneVisualTransformation = remember {
            PhoneNumberVisualTransformation(phoneNumberUtil)
        }
        var phoneNumber by remember {
            mutableStateOf("")
        }
        TertiaryTextInputField(
            text = phoneNumber,
            requestFocus = true,
            visualTransformation = phoneVisualTransformation,
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done,
            onValueChange = {
                phoneNumber = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.spacings.spacing32)
        )

//        WhiteAppImage(
//            modifier = Modifier.size(100.dp),
//            image = state.image
//        )

//        Text(
//            text = "Pick Image",
//            style = AppTheme.typography.fenwickBold24,
//            color = Color.Red,
//            modifier = modifier
//                .clickable { onEvent(Event.PickImageClicked) }
//        )
//
//        Text(
//            text = "Selected item: ${state.selectedItem}",
//            style = AppTheme.typography.fenwickBold18,
//            color = Color.Red
//        )

//        VideoPlayer(
//            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
//            autoPlay = true,
//            modifier = Modifier
//                .background(AppTheme.colors.black)
//                .fillMaxWidth()
//                .height(200.dp)
//        )
    }
//
//    // Container column
//    ScreenColumn(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxSize(),
//        enableSafeInsets = true,
//        isScrollable = false
//    ) {
//        Text(
//            text = if (state.isWelcome) "Welcome" else "Sample App",
//            style = AppTheme.typography.fenwickBold24,
//            color = AppTheme.colors.midnight,
//            modifier = modifier
//                .clickable { onEvent(Event.TextClicked) }
//        )
//
//        if (state.isWelcome) {
//            val composition by rememberLottieComposition(MR.assets.people_lottie_anim)
//            val progress = animateLottieCompositionAsState(
//                composition = composition,
//                iterations = LottieConstants.IterateForever,
//            )
//            LottieAnimation(
//                composition = composition,
//                progress = { progress.value },
//                contentScale = ContentScale.FillHeight,
//                alignment = Alignment.Center,
//                clipToCompositionBounds = true,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//                    .noRippleClickable {
//                        onEvent(Event.AnimClicked)
//                    }
//            )
//        } else {
//            val composition by rememberLottieComposition(MR.assets.search_lottie_anim)
//            val progress = animateLottieCompositionAsState(
//                composition = composition,
//                iterations = LottieConstants.IterateForever,
//            )
//            LottieAnimation(
//                composition = composition,
//                progress = { progress.value },
//                modifier = Modifier
//                    .width(300.dp)
//                    .height(200.dp)
//                    .align(Alignment.CenterHorizontally)
//                    .background(Color.Gray)
//                    .noRippleClickable {
//                        onEvent(Event.AnimClicked)
//                    }
//            )
//
//            RatingBar(
//                value = 3.5f,
////                style = RatingBarStyle.Fill(),
//                isIndicator = true,
//                stepSize = StepSize.HALF,
//                size = AppTheme.spacings.spacing30,
//                painterFilled = painterResource(MR.images.ic_star_filled),
//                painterEmpty = painterResource(MR.images.ic_star_empty),
//            )
//        }
//    }
}