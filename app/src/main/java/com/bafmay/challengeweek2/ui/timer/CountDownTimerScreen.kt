package com.bafmay.challengeweek2.ui.timer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.bafmay.challengeweek2.R
import com.bafmay.challengeweek2.models.Exercise
import com.bafmay.challengeweek2.models.Timer
import com.bafmay.challengeweek2.ui.theme.MyTheme
import com.bafmay.challengeweek2.ui.widgets.RedButton
import com.bafmay.challengeweek2.utils.ExerciseManager
import com.bafmay.challengeweek2.utils.toTimeString

@Composable
fun CountDownTimerScreen(viewModel: CountDownTimerViewModel) {

    val exercise = ExerciseManager.exercises.first()
    val secondExercise = ExerciseManager.exercises[1]

    viewModel.init(exercise.time)
    val state = viewModel.state.observeAsState(
        initial = Timer(exercise.time, exercise.time.toTimeString())
    )

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedExercise(
                isPlaying = state.value.state == TimerState.RUNNING,
                onPlayPressed = {
                    viewModel.startTimer(exercise.time)
                },
                onStopPressed = {
                    viewModel.stopTimer()
                },
                onReplayPressed = {
                    viewModel.resetTimer(exercise.time)
                }
            )
            TimerScreenDetail(
                timer = state.value,
                exercise = exercise,
                nextExercise = secondExercise
            )
        }
    }
}

@Composable
fun AnimatedExercise(
    isPlaying: Boolean = false,
    onPlayPressed: () -> Unit,
    onStopPressed: () -> Unit,
    onReplayPressed: () -> Unit
) {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.rope_routine) }
    val animationState =
        rememberLottieAnimationState(autoPlay = isPlaying, repeatCount = Integer.MAX_VALUE)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        RedButton(
            imageVector = Icons.Outlined.Replay,
            onPressed = onReplayPressed
        )

        Box(
            modifier = Modifier.size(200.dp, 200.dp)
        ) {
            LottieAnimation(
                spec = animationSpec,
                animationState = animationState
            )
        }
        RedButton(
            imageVector = if (isPlaying) Icons.Outlined.Stop else Icons.Outlined.PlayArrow,
            onPressed = if (isPlaying) onStopPressed else onPlayPressed
        )
    }

}

@Composable
fun TimerScreenDetail(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    nextExercise: Exercise,
    timer: Timer
) {

    Column(modifier = modifier.fillMaxWidth()) {
        ExerciseDescription(exercise)
        DownTimerCounter(modifier = Modifier.weight(1f), exercise, timer)
        NextExercise(exercise = nextExercise)
    }
}

@Composable
fun DownTimerCounter(
    modifier: Modifier,
    exercise: Exercise,
    time: Timer
) {
    val angle by animateFloatAsState(time.timeInMillis * 360 / exercise.time.toFloat())
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val minDimension = this.size.minDimension
            val currentRadius = minDimension / 2f
            drawArc(
                color = Color(0xFFF9DDD7),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(
                    x = this.center.x - currentRadius,
                    y = this.center.y - currentRadius
                ),
                size = Size(width = minDimension, height = minDimension),
                style = Stroke(width = 30.dp.value, cap = StrokeCap.Round)
            )

            drawArc(
                color = Color(0xFFEB4A2A),
                startAngle = -90f,
                sweepAngle = angle,
                useCenter = false,
                topLeft = Offset(
                    x = this.center.x - currentRadius,
                    y = this.center.y - currentRadius
                ),
                size = Size(width = minDimension, height = minDimension),
                style = Stroke(width = 30.dp.value)
            )
        }
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time.timeFormatted,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun NextExercise(exercise: Exercise) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.next_exercise),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF736B6A)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(100.dp),
                painter = painterResource(exercise.drawable),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = exercise.time.toTimeString(),
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun ExerciseDescription(exercise: Exercise) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = exercise.description,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Justify
        )
        Text(
            text = stringResource(id = R.string.exercise_time, exercise.time.toTimeString()),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Justify
        )
    }
}


@Preview("Light Theme")
@Composable
fun LightPreview() {
    MyTheme {
        CountDownTimerScreen(CountDownTimerViewModel())
    }
}