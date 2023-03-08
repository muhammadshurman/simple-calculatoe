package mu.project.maycalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


    class MainActivity : AppCompatActivity() {
        // Represent whether the lastly pressed key is numeric or not
        var lastNumeric: Boolean = false

        // If true, do not allow to add another DOT
        var lastDot: Boolean = false

        private var tvInput:TextView?=null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            tvInput = findViewById(R.id.tvInput)
        }

        fun onDigit(view: View){
            tvInput?.append((view as Button).text)
            lastNumeric = true
        }

        fun onClear(view: View){
            tvInput?.text = ""
            lastNumeric = false
            lastDot = false
        }
        /**
         * Append . to the TextView
         */
        fun onDecimalPoint(view: View) {

            // If the last appended value is numeric then append(".") or don't.
            if (lastNumeric && !lastDot) {
                tvInput?.append(".")
                lastNumeric = false // Update the flag
                lastDot = true // Update the flag
            }
        }

        /**
         * Append +,-,*,/ operators to the TextView as per the Button.Text
         */
        fun onOperator(view: View) {
            tvInput?.text?.let {
                if (lastNumeric && !isOperatorAdded(it.toString())) {
                    tvInput?.append((view as Button).text)
                    lastNumeric = false // Update the flag
                    lastDot = false    // Reset the DOT flag
                }
            }

        }

        /**
         * Calculate the output
         */
        fun onEqual(view: View) {
            // If the last input is a number only, solution can be found.
            if (lastNumeric) {
                // Read the textView value
                var tvValue = tvInput?.text.toString()
                var prefix = ""
                try {

                    // Here if the value starts with '-' then we will separate it and perform the calculation with value.
                    if (tvValue.startsWith("-")) {
                        prefix = "-"
                        tvValue = tvValue.substring(1);
                    }

                    // If the inputValue contains the Division operator
                    when {
                        tvValue.contains("/") -> {
                            // Will split the inputValue using Division operator
                            val splitedValue = tvValue.split("/")

                            var one = splitedValue[0] // Value One
                            val two = splitedValue[1] // Value Two

                            if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                                one = prefix + one
                            }

                            /*Here as the value one and two will be calculated based on the operator and
                                    if the result contains the zero after decimal point will remove it.
                                    And display the result to TextView*/
                            tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                        }
                        tvValue.contains("*") -> {
                            // If the inputValue contains the Multiplication operator
                            // Will split the inputValue using Multiplication operator
                            val splitedValue = tvValue.split("*")

                            var one = splitedValue[0] // Value One
                            val two = splitedValue[1] // Value Two

                            if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                                one = prefix + one
                            }

                            /** Here as the value one and two will be calculated based on the operator and
                            if the result contains the zero after decimal point will remove it.
                            And display the result to TextView
                             */
                            tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                        }
                        tvValue.contains("-") -> {

                            // If the inputValue contains the Subtraction operator
                            // Will split the inputValue using Subtraction operator
                            val splitedValue = tvValue.split("-")

                            var one = splitedValue[0] // Value One
                            val two = splitedValue[1] // Value Two

                            if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                                one = prefix + one
                            }

                            /** Here as the value one and two will be calculated based on the operator and
                            if the result contains the zero after decimal point will remove it.
                            And display the result to TextView
                             */
                            tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                        }
                        tvValue.contains("+") -> {
                            // If the inputValue contains the Addition operator
                            // Will split the inputValue using Addition operator
                            val splitedValue = tvValue.split("+")

                            var one = splitedValue[0] // Value One
                            val two = splitedValue[1] // Value Two

                            if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                                one = prefix + one
                            }

                            /**Here as the value one and two will be calculated based on the operator and
                            if the result contains the zero after decimal point will remove it.
                            And display the result to TextView
                             */
                            tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                        }
                    }
                } catch (e: ArithmeticException) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * Remove the zero after decimal point
         */
        private fun removeZeroAfterDot(result: String): String {

            var value = result

            if (result.contains(".0")) {
                value = result.substring(0, result.length - 2)
            }

            return value
        }

        /**
         * It is used to check whether any of the operator is used or not.
         */
        private fun isOperatorAdded(value: String): Boolean {

            /**
             * Here first we will check that if the value starts with "-" then will ignore it.
             * As it is the result value and perform further calculation.
             */

            return if (value.startsWith("-")) {
                false
            } else {
                (value.contains("/")
                        || value.contains("*")
                        || value.contains("-")
                        || value.contains("+"))
            }
        }
    }