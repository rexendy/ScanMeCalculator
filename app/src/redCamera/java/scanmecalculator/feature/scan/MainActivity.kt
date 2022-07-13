package scanmecalculator.feature.scan

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val CAMERA_PERMISSION_REQUEST_CODE: Int = 1000
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
            if (checkPermission()) capturePhoto() else requestPermission()
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
     * Handles data from image capture
     */
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            var bitmap = intent?.extras?.get("data")  as Bitmap
            viewModel.setBitmap(bitmap)
            viewModel.setContext(this)
            viewModel.readTextFromImage()
        }
    }

    /**
     * Handles when request permission is shown to the user
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // check for requested permission
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                capturePhoto()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Shows the device camera
     */
    private fun capturePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startForResult.launch(cameraIntent)
    }

    /***
     * Check user permission
     * @return Boolean
     */
    private fun checkPermission(): Boolean {
        // checks if permission is allowed by the user
        return (ContextCompat.checkSelfPermission(this, CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    /**
     * Request a permission
     * Shows the user the pop up permission
     */
    private fun requestPermission() {
        // show request permission if the camera permission is not yet allowed by user
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    }
}

