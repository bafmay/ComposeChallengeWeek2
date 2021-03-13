package com.bafmay.challengeweek2.utils

import com.bafmay.challengeweek2.R
import com.bafmay.challengeweek2.models.Exercise

object ExerciseManager {
    val exercises by lazy { generateExercise() }

    private fun generateExercise(): List<Exercise> {
        val items = mutableListOf<Exercise>()

        items.add(
            Exercise(
                1,
                "Salto de soga",
                "Los músculos de los brazos y de las piernas (pantorrillas, muslos y glúteos) se desarrollan y fortalecen, perdiendo su flacidez y mejorando su forma, mientras el cuello, los hombros y el pecho se ensanchan y se vuelven firmes.",
                time = 30000,
                drawable = R.drawable.rope_exercise
            )
        )

        items.add(
            Exercise(
                2,
                "Abdominales",
                "Más allá de contribuir a lucir mejor aspecto, estos ejercicios esconden otros beneficios para la salud.",
                time = 3.toMillis(),
                drawable = R.drawable.abs_exercise
            )
        )

        return items
    }
}