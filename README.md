💡 Project Overview
The application provides a single-screen interface capable of handling both simple arithmetic and complex scientific calculations. The primary technical focus is on the custom implementation of the expression evaluation logic, which ensures correct results by strictly following the mathematical Order of Operations (Precedence).
🔑 Key FeaturesComprehensive
Comprehensive Functionality: Provides all four basic arithmetic operations (+, -, *, /)$and a suite of scientific functions including sin, cos, tan, log, square root , percentage, and power edge.
Order of Operations (Precedence): The application correctly evaluates complex expressions (2 + 3 *4) by respecting precedence rules (PEMDAS/BODMAS) using a custom two-stack algorithm.
Input Handling: Features specialized logic to handle unary minus signs and correctly parse tokens from the input string using Java's Pattern and Matcher classes.
Error Management: Includes built-in error checking for scenarios like Division by Zero, Square Root of a Negative Number, and Invalid Logarithmic Input (<0).
Dynamic UI: Uses a standard button-based layout with a non-editable EditText for the display, ensuring a familiar user experience.
🛠️ Tools & Technologies 
UsedComponentTool/TechnologyPurposeIDEAndroid StudioMain environment for coding, debugging, and building.
LanguageJavaUsed for all application logic, including the complex expression evaluator.
Key AlgorithmTwo-Stack EvaluationA custom implementation of an algorithm (similar to Shunting-Yard) using Stack<Double> and Stack<String> to process expressions according to precedence.
Key Java Classesjava.util.regex.Pattern & MatcherUsed for tokenizing the input string (parsing numbers and operators).
UI MarkupXMLUsed for defining the grid-like layout of the calculator buttons and display.
🪜 Steps Performed (Development Workflow)
Project Setup & UI Design: Created the project and designed the activity_main.
xml layout, structuring buttons into LinearLayout rows to represent a standard calculator
keypad.Button Binding: Programmatically iterated through all Button elements within the layout rows and assigned a single OnClickListener (this) to the activity.Input & Display Logic: Implemented the onClick() method to handle button presses, appending numbers and operators to the currentInput string and managing the isResultDisplayed state.Scientific Function Implementation: Created separate branches in onClick() to handle unary operations (sqrt, sin, cos, tan) by parsing the current input, applying Math class methods, and displaying the result.
Core Evaluation (evaluateExpression): This is the core logical component. It was built using:
Tokenization: Using regex (Pattern and Matcher) to separate numbers and operators.Precedence
 Logic: Implemented hasPrecedence() to define the hierarchy of operators (> ,*,  +,-).
Calculation: Implemented applyOp() to perform the specific calculation based on the operator popped from the stack.
Error Handling: Integrated try-catch blocks and specific checks (e.g., dividing by zero) to display appropriate error Toast messages and reset the calculator state.
Directory/File	Content	Description
app/src/main/java/com.example.calculatoroasis1/MainActivity.javaCore Logic.
Contains all UI setup, event handlers, and the crucial expression evaluation logic (evaluateExpression, applyOp, hasPrecedence).
app/src/main/res/layout/activity_main.xml
User Interface.
Defines the calculator's keypad layout, including the rows for scientific functions and basic arithmetic.
app/src/main/res/values/Resources.
Stores application constants, colors, and styles.
✅ Outcome
The project successfully delivers a robust, highly functional Scientific Calculator that addresses the complexity of expression parsing and evaluation. It demonstrates advanced proficiency in:

Algorithm Implementation: Successfully implementing a custom two-stack algorithm for parsing and evaluating expressions, adhering to strict mathematical precedence rules.

Android Development: Integrating Java logic with a complex, button-intensive UI.

Error Handling: Providing specific feedback for various mathematical and parsing errors.
____________________
submitted by:Anuraj Vijayan k
Intenship Role:Android Studio
Task:2
