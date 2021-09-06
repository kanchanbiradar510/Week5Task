package com.example.week5task.fragments.add

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import com.example.week5task.MainActivity
import com.example.week5task.R
import kotlinx.android.synthetic.main.fragment_profit.view.*


class ProfitFragment : Fragment() {

    private var name: TextView? = null
    private var email: TextView? = null
    private var age: TextView? = null
    private var gender: TextView? = null
    private var dob: TextView? = null
    private var time: TextView? = null
    private var homeBtn: TextView? = null
    private var deleteBtn: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profit, container, false)
        name = view.findViewById(R.id.txName)
        email = view.findViewById(R.id.txEmail)
        age = view.findViewById(R.id.txAge)
        dob = view.findViewById(R.id.txDob)
        time = view.findViewById(R.id.txTime)
        deleteBtn = view.findViewById(R.id.deleteBtn)
        homeBtn = view.findViewById(R.id.homeBtn)

        var dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val b = arguments
                        if (b != null) {
                            val uid: String? = b.getString("uid")
                            val ma1 = activity as MainActivity?
                            if (uid != null) {
                                ma1!!.deleteUser(uid)
                            }
                            ma1!!.listView()
                        }
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
        val b = arguments
        if (b != null) {
            val uid: String? = b.getString("uid")
            println("displayview:"+id)
            val ma1 = activity as MainActivity?
            var user: User? = null
            if (uid != null) {
                user  = ma1!!.getUser(id)
            }
            if (user != null) {
               name!!.text = "Username : " + user.firstName
                email!!.text = "Email : " + user.lastName
                age!!.text = "Age : " + user.age
            }
            deleteBtn!!.setOnClickListener(View.OnClickListener { v ->
                val builder = AlertDialog.Builder(v.context)
                val dialogClickListener = null
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show()
            })
            homeBtn!!.setOnClickListener(View.OnClickListener {
                val ma = activity as MainActivity?
                ma!!.listView()
            })
        }

        return view
    }

}
