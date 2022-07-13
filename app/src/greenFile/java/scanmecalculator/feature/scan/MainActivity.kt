package scanmecalculator.feature.scan

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.scanmecalculator.R
import com.test.scanmecalculator.databinding.ActivityMainBinding
import com.test.scanmecalculator.feature.scan.ScanCalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity
 * Initializes the needed variables and bind the layout in this activity
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ScanCalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(ScanCalculatorViewModel::class.java)
        bindingViews()
        viewModelObservers()
    }

    /**
     * Bind the views from the layout
     * and when the view receives  an interaction
     * from the user, it will do the desired function
     */
    private fun bindingViews() {
        binding.btnAddInput.setOnClickListener {
            // launch device gallery
            getContent.launch("image/*")
        }
    }

    /**
     * View model observers
     * Observe the live data from the view model
     * when the value is updated
     */
    private fun viewModelObservers() {
        viewModel.result.observe(this, Observer {
            binding.txtResult.text = "${resources.getString(R.string.result)}$it"
        })

        viewModel.expression.observe(this, Observer {
            binding.txtInput.text = "${resources.getString(R.string.input)}$it"
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    /**
     * Handles returned Uri from the selected image
     */
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.setUri(it)
            viewModel.setContext(this)
            viewModel.readTextFromImage()
        }
    }
}

