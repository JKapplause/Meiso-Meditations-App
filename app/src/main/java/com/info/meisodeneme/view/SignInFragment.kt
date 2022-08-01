package com.info.meisodeneme.view


import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.info.meisodeneme.R
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_in, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btn_signin = view.findViewById<Button>(R.id.signin_button)
        val signin_remember = signin_checkbox.isChecked

        val currentUser = auth.currentUser




            btn_signin?.setOnClickListener {
                val mail = signin_emailET.text.toString()
                val pass = signin_passwordET.text.toString()
                if(mail.isNotEmpty() && pass.isNotEmpty()) {

                    auth.signInWithEmailAndPassword(
                        signin_emailET.text.toString(),
                        signin_passwordET.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val last = auth.currentUser?.email.toString()
                            makeText(getActivity(), "Welcome: ${last}", Toast.LENGTH_LONG).show()
                            val action = SliderFragmentDirections.actionSliderFragmentToHomeFragment()
                            findNavController(it).navigate(action)

                        }
                    }.addOnFailureListener { exception ->
                        makeText(getActivity(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }

                }else if(mail.isEmpty() || pass.isEmpty()) {
                    showCustomToast()
                }

                }
                }

    private fun showCustomToast() {
        val ll = view?.findViewById<LinearLayout>(R.id.info_layout)
        val layout = layoutInflater.inflate(R.layout.custom_signin_toast_message, ll)

        with(Toast(getActivity()?.getApplicationContext())) {
            duration = Toast.LENGTH_LONG
            setGravity(Gravity.TOP, 10,290)
            view = layout
            show()
        }
    }


}





