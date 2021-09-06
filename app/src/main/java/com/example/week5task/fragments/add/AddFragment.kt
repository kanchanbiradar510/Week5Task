package com.example.roomapp.fragments.add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import com.example.week5task.R
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.util.*

class AddFragment : Fragment() {

private lateinit var mUserViewModel: UserViewModel

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.N)
override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_add, container, false)

    mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

    val formatdate= android.icu.text.SimpleDateFormat("dd MMMM yyyy", Locale.US)
    val formattime= android.icu.text.SimpleDateFormat("HH:mm aa")

    val dobBtn = view.findViewById<Button>(R.id.dobBtn)
    val dobTxt = view.findViewById<TextView>(R.id.dobTxt)

    val timeBtn = view.findViewById<Button>(R.id.timeBtn)
    val timeTxt = view.findViewById<TextView>(R.id.timeTxt)
    dobBtn.setOnClickListener(View.OnClickListener {
        val getdate = java.util.Calendar.getInstance()
        val datePicker=
            DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val selectdate= java.util.Calendar.getInstance()
                selectdate.set(java.util.Calendar.YEAR,year)
                selectdate.set(java.util.Calendar.MONTH,month)
                selectdate.set(java.util.Calendar.DAY_OF_MONTH,day)
                dobTxt.text=formatdate.format(selectdate.time)
            },getdate.get(java.util.Calendar.YEAR),getdate.get(java.util.Calendar.MONTH),getdate.get(
                java.util.Calendar.DAY_OF_MONTH))
        datePicker.show()

    })

    timeBtn.setOnClickListener(View.OnClickListener {
        val gettime= java.util.Calendar.getInstance()
        val timepicker= TimePickerDialog(this.requireContext(),TimePickerDialog.OnTimeSetListener { time, i, i2 ->
            val selecttime= java.util.Calendar.getInstance()
            selecttime.set(java.util.Calendar.HOUR_OF_DAY,i)
            selecttime.set(java.util.Calendar.MINUTE,i2)
            timeTxt.text=formattime.format(selecttime.time)
        },gettime.get(java.util.Calendar.HOUR_OF_DAY),gettime.get(java.util.Calendar.MINUTE),true)
        timepicker.show()
    })


    view.okBtn.setOnClickListener {


        if ((PersonNameCheckField() and validateEmail() and FirstCheckAllField() and Checkage() and SecondCheckAllField() and CheckDOB() and CheckTime())) {

            insertDataToDatabase()
        }
    }

    return view
}

private fun insertDataToDatabase() {
    val name = personName.text.toString()
    val email= emailAddress.text.toString()
    val age = age.text

    //   val radioGroup = radioGroup.checkedRadioButtonId
    // val gender = view?.findViewById<RadioButton>(radioGroup)


    if(inputCheck(name, email, age)){
        // Create User Object
        val user = User(
            0,
            name,
            email,
            Integer.parseInt(age.toString())
        )
        // Add Data to Database
        mUserViewModel.addUser(user)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        // Navigate Back
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }else{
        Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
    }
}

private fun inputCheck(name: String, email: String, age: Editable): Boolean{
    return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && age.isEmpty())
}


fun PersonNameCheckField(): Boolean {
    val personInput = personName.text.toString().trim()
    if (personInput.length == 0) {
        personName.error = "Please Enter Valid Name"
        return false
    } else if (personInput.length < 5) {
        personName.error = "minimum length of name is 5 "
        return false
    } else if (personInput.length > 30) {
        personName.error = "maximum length of name is 30"
        return false
    } else {
        personName.error = null
        return true
    }
}

fun validateEmail(): Boolean {
    val emailInput1 = emailAddress.text.toString().trim()
    if (emailInput1.isEmpty()) {
        emailAddress.error = "Please enter valid Email"
        return false
    } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput1).matches()) {
        emailAddress.error = "Please enter valid Email"
        return false
    } else {
        emailAddress.error = null
        return true
    }
}

fun FirstCheckAllField(): Boolean{
    if (!maleRadioBtn.isChecked() and !femaleRadioBtn.isChecked()) {
        // Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}

fun SecondCheckAllField(): Boolean{
    when{(!checkBox.isChecked) ->{
        // Toast.makeText(this@AddFragment,"Please Accept Term and condition ", Toast.LENGTH_LONG).show()
        return false}
    }
    return true
}

fun CheckDOB(): Boolean {
    val name: String = dobTxt.text.toString()
    return if (name.isEmpty()) {
        dobTxt.error = "Please Select Date of Birth"
        false
    } else {
        dobTxt.error = null
        true
    }
}

fun CheckTime(): Boolean {
    val name: String = timeTxt.text.toString()
    return if (name.isEmpty()) {
        timeTxt.error = "Please select Time"
        false
    } else {
        timeTxt.error = null
        true
    }
}

fun Checkage(): Boolean {
    val ageof = age.text.toString().trim()
    val ageval="/^[1-9]?[0-9]{1}\$|^100\$/"
    return if (ageof.isEmpty()) {
        age.error = "Please enter Age"
        false
    }
    else {
        age.error = null
        true
    }
}

}