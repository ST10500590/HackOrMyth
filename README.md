# HackOrMyth
A native Android app using Kotlin within Android Studio to help users study and verify life hacks with flashcards
Youtube link: https://youtube.com/shorts/d7fK1wJ_OI8?feature=share

1. Purpose of the Application

The Life Hack or Urban Myth? application is a native Android flashcard quiz designed to help users distinguish between useful life hacks and common internet myths. Users are presented with statements and must decide whether each statement is a Hack (True) or Myth (False). The application provides immediate feedback and explanations to support learning.

2. Design Considerations

The application was developed using Kotlin and Jetpack Compose in Android Studio. The interface was designed to be simple, modern, and easy to navigate.

Key design considerations include:
A Welcome Screen introducing the application and providing a Start button.
A Quiz Screen displaying one statement at a time with Hack and Myth options.
Immediate feedback and explanations after each answer.
A Score Screen showing the user's total score and personalised performance feedback.
A Review Screen allowing users to view all questions, correct answers, and explanations.
Consistent colours, cards, icons, spacing, and rounded buttons to create a professional and user-friendly interface.
The application uses Compose state variables to update the interface dynamically as the user progresses.

3. Application Logic

The questions are stored in a Kotlin list and accessed sequentially using a question counter. If statements are used where appropriate to determine answer correctness and provide feedback. The score is increased for every correct answer. Once all questions have been completed, the application calculates and displays the final result.

The Review feature uses a loop to display all questions and their corresponding correct answers and explanations.

4. GitHub Utilisation

GitHub was used for version control and project management. The Android Studio project was uploaded to a GitHub repository, allowing changes to the application to be tracked throughout development.

GitHub was used to:

Store and back up the source code.
Track changes using commits.
Maintain different versions of the project.
Provide a central repository for the application.
Support collaboration and project management.

5. Conclusion

The Life Hack or Urban Myth? application meets its objective by providing an interactive and educational way to test users' knowledge of common life hacks. Kotlin, Jetpack Compose, GitHub, and GitHub Actions were combined to create a maintainable application with a modern interface, functional quiz logic, score tracking, and answer-review functionality.
