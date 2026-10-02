package com.example.hackormyth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hackormyth.ui.theme.HackOrMythTheme



import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackormyth.ui.theme.HackOrMythTheme

// Represents one flashcard question
data class LifeHack(
    val statement: String,
    val isHack: Boolean,
    val explanation: String
)

// Main activity for the application
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            HackOrMythTheme {
                LifeHackApp()
            }
        }
    }
}

// Controls the main app screens and quiz logic
@androidx.compose.runtime.Composable
fun LifeHackApp() {

    // Collection of life hacks and urban myths
    val questions = remember {
        listOf(

            LifeHack(
                statement = "Putting a wooden spoon across a boiling pot helps reduce overflowing.",
                isHack = true,
                explanation = "A wooden spoon can disrupt bubbles reaching the edge of the pot and may temporarily reduce foaming. It is not a guarantee, so cooking should still be supervised."
            ),

            LifeHack(
                statement = "Drinking coffee immediately makes you completely immune to tiredness.",
                isHack = false,
                explanation = "Coffee can temporarily increase alertness, but it does not eliminate tiredness or replace adequate sleep."
            ),

            LifeHack(
                statement = "Using keyboard shortcuts can make common computer tasks faster.",
                isHack = true,
                explanation = "Keyboard shortcuts can reduce the number of mouse movements and clicks required for many common tasks."
            ),

            LifeHack(
                statement = "Charging your phone in the freezer makes it charge significantly faster.",
                isHack = false,
                explanation = "Extreme temperatures can damage batteries and electronics. A freezer should not be used as a charging method."
            ),

            LifeHack(
                statement = "Making a short to-do list can help organise tasks.",
                isHack = true,
                explanation = "A short, prioritised task list can help people organise their work and focus on what needs to be completed."
            ),

            LifeHack(
                statement = "You can safely remove every computer virus by deleting random system files.",
                isHack = false,
                explanation = "Deleting system files can damage an operating system. Security software and proper troubleshooting should be used instead."
            )
        )
    }

    // Controls which screen is displayed
    var currentScreen by remember {
        mutableStateOf("welcome")
    }

    // Stores the current question
    var currentQuestion by remember {
        mutableStateOf(0)
    }

    // Stores the user's score
    var score by remember {
        mutableStateOf(0)
    }

    // Stores whether the user has selected an answer
    var answered by remember {
        mutableStateOf(false)
    }

    // Stores whether the selected answer was correct
    var answerCorrect by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = Color(0xFFF7F5FC)
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Switches between the different app screens
            when (currentScreen) {

                // Welcome screen
                "welcome" -> {

                    WelcomeScreen(
                        onStart = {
                            currentQuestion = 0
                            score = 0
                            answered = false
                            currentScreen = "quiz"
                        }
                    )
                }

                // Flashcard quiz screen
                "quiz" -> {

                    QuizScreen(
                        question = questions[currentQuestion],
                        questionNumber = currentQuestion + 1,
                        totalQuestions = questions.size,
                        answered = answered,
                        answerCorrect = answerCorrect,
                        // Checks the user's selected answer
                        onAnswer = { userAnswer ->

                            // Prevent the score from changing twice
                            if (!answered) {

                                answerCorrect =
                                    userAnswer == questions[currentQuestion].isHack

                                if (answerCorrect) {
                                    score++
                                }

                                answered = true
                            }
                        },

                        onNext = {
                            // Add one point for a correct answer
                            if (currentQuestion < questions.lastIndex) {

                                currentQuestion++
                                answered = false
                                answerCorrect = false

                            } else {

                                currentScreen = "score"
                            }
                        }
                    )
                }

                // Score screen
                "score" -> {

                    ScoreScreen(
                        score = score,
                        totalQuestions = questions.size,

                        onReview = {
                            currentScreen = "review"
                        },

                        onRestart = {
                            currentQuestion = 0
                            score = 0
                            answered = false
                            answerCorrect = false
                            currentScreen = "quiz"
                        }
                    )
                }

                // Review screen
                "review" -> {

                    ReviewScreen(
                        questions = questions,

                        onBack = {
                            currentScreen = "score"
                        }
                    )
                }
            }
        }
    }
}


// ------------------------------------------------------------
// WELCOME SCREEN
// ------------------------------------------------------------

@androidx.compose.runtime.Composable
fun WelcomeScreen(
    onStart: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Application icon
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFEB3B),
                            Color(0xFFFF5722)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "Life Hack",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Life Hack or\n\nUrban Myth?",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF282230)
        )

        Spacer(modifier = Modifier.height(14.dp))
        // Briefly explains the purpose of the game
        Text(
            text = "Test your knowledge and find out which popular life hacks are useful and which ones are basically myths.",
            fontSize = 17.sp,
            lineHeight = 25.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF6F6878)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "How to play",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Read each statement and decide whether it is a real life hack or an urban myth.",
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = Color(0xFF625C69)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BCD4)
            )
        ) {

            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Start"
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Start Quiz",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ------------------------------------------------------------
// QUIZ SCREEN
// ------------------------------------------------------------

@androidx.compose.runtime.Composable
fun QuizScreen(
    question: LifeHack,
    questionNumber: Int,
    totalQuestions: Int,
    answered: Boolean,
    answerCorrect: Boolean,
    onAnswer: (Boolean) -> Unit,
    onNext: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))
        // Shows the user's progress through the quiz
        Text(
            text = "Question $questionNumber of $totalQuestions",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Hack or Myth?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF292431)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Flashcard
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Hack",
                    tint = Color(0xFFFFEB3B),
                    modifier = Modifier.size(35.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = question.statement,
                    fontSize = 21.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF332D39)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Provides the two answer choices
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {
                    onAnswer(true)
                },
                enabled = !answered,
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {

                Text(
                    text = "Hack ✓",
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    onAnswer(false)
                },
                enabled = !answered,
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE85D5D)
                )
            ) {

                Text(
                    text = "Myth ✕",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Shows feedback after the user answers
        if (answered) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        if (answerCorrect)
                            Color(0xFFE8F5E9)
                        else
                            Color(0xFFFFEBEE)
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text =
                            if (answerCorrect)
                                "Correct! Great job."
                            else
                                "Wrong! That's an urban myth.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = question.explanation,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            // Moves to the next question or score screen
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                )
            ) {

                Text(
                    text =
                        if (questionNumber == totalQuestions)
                            "View Score"
                        else
                            "Next Question",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(8.dp))

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }
    }
}


// ------------------------------------------------------------
// SCORE SCREEN
// ------------------------------------------------------------

@androidx.compose.runtime.Composable
fun ScoreScreen(
    score: Int,
    totalQuestions: Int,
    onReview: () -> Unit,
    onRestart: () -> Unit
) {
    // Calculates the user's final percentage
    val percentage =
        (score.toFloat() / totalQuestions.toFloat()) * 100
    // Gives feedback based on the final result
    val feedback = when {
        percentage >= 80 -> "Master Hacker!"
        percentage >= 50 -> "Great job!"
        else -> "Keep practising!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Score",
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Quiz Complete!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Displays the final score
        Text(
            text = "$score / $totalQuestions",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6C42D9)
        )

        Text(
            text = "Correct Answers",
            fontSize = 16.sp,
            color = Color(0xFF706977)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = feedback,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))
        // Gives the user additional feedback
        Text(
            text =
                if (percentage >= 80)
                    "You have a great eye for spotting useful life hacks!"
                else if (percentage >= 50)
                    "You know quite a few useful tricks. Keep learning!"
                else
                    "Don't worry. Reviewing the explanations can help you improve.",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color(0xFF6F6878)
        )

        Spacer(modifier = Modifier.height(30.dp))
        // Opens the answer review
        Button(
            onClick = onReview,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {

            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = "Review"
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Review Answers",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        // Allows the user to play the quiz again
        OutlinedButton(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Restart"
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Try Again",
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ------------------------------------------------------------
// REVIEW SCREEN
// ------------------------------------------------------------

@androidx.compose.runtime.Composable
fun ReviewScreen(
    questions: List<LifeHack>,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Review Answers",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Learn why each statement is a hack or a myth.",
            fontSize = 15.sp,
            color = Color(0xFF706977)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Loops through all questions and displays their answers
        Column(
            modifier = Modifier.weight(1f)
        ) {

            questions.forEachIndexed { index, question ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "Question ${index + 1}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6C42D9)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = question.statement,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        // Shows the correct answer
                        Text(
                            text =
                                if (question.isHack)
                                    "Answer: HACK"
                                else
                                    "Answer: MYTH",
                            fontWeight = FontWeight.Bold,
                            color =
                                if (question.isHack)
                                    Color(0xFF388E3C)
                                else
                                    Color(0xFFD32F2F)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        HorizontalDivider()

                        Spacer(modifier = Modifier.height(8.dp))
                        // Shows the explanation for the answer
                        Text(
                            text = question.explanation,
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            color = Color(0xFF625C69)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        // Returns to the score screen
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "Back to Score",
                fontWeight = FontWeight.Bold
            )
        }
    }
}