package com.setoh.sample.droidkaigi2021

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setoh.sample.droidkaigi2021.databinding.FragmentFirstBinding
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            loadData("https://google.com")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData(url: String) {
        binding.textviewFirst.setText(R.string.loading)
        object : AsyncTask<Unit, Unit, String>() {
            override fun doInBackground(vararg p0: Unit?): String {
                val client = OkHttpClient()

                val request: Request = Request.Builder()
                    .url(url)
                    .build()

                return client.newCall(request).execute()
                    .use { response -> response.code.toString() }
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                binding.textviewFirst.text = result
            }
        }.execute(Unit)
    }
}