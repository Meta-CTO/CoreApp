package com.metacto.core.presentation.components.refreshLayout

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.math.abs

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : 下拉刷新的刷新组件
 *          Refresh component for pull down refresh
 * warning:
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun RefreshLayoutState.PullToRefreshContent() {
    val refreshContentState by remember {
        getRefreshContentState()
    }
    Row(
        Modifier
            .fillMaxWidth()
            .height(35.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        when (refreshContentState) {
            RefreshContentStateEnum.Stop -> {
                //no image
            }

            RefreshContentStateEnum.Refreshing -> {
                //循环旋转动画
                val infiniteTransition = rememberInfiniteTransition()
                val rotate by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                Image(
                    painter = painterResource(MR.images.ic_play),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(rotate)
                )
                Spacer(Modifier.width(10.dp))
            }

            RefreshContentStateEnum.Dragging -> {
                //旋转动画
                val isCannotRefresh =
                    abs(getRefreshContentOffset()) < getRefreshContentThreshold()
                val rotate by animateFloatAsState(targetValue = if (isCannotRefresh) 0f else 180f)
                Image(
                    painter = painterResource(MR.images.ic_play),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(rotate)
                )
                Spacer(Modifier.width(10.dp))
            }
        }
        Text(
            text = when (refreshContentState) {
                RefreshContentStateEnum.Stop -> "Refresh Complete"
                RefreshContentStateEnum.Refreshing -> "Refreshing"
                RefreshContentStateEnum.Dragging -> {
                    if (abs(getRefreshContentOffset()) < getRefreshContentThreshold()) {
                        "Pull to refresh"
                    } else {
                        "Release refresh"
                    }
                }
            },
            fontSize = 14.sp,
            color = CoreTheme.colors.red,
        )
    }
}