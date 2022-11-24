package com.example.practicalwisdomleaf

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.practicalwisdomleaf.Adapter.DataAdapter
import com.example.practicalwisdomleaf.Repository.MainRepository
import com.example.practicalwisdomleaf.Repository.MyViewModelFactory
import com.example.practicalwisdomleaf.databinding.ActivityMainBinding
import com.example.practicalwisdomleaf.services.RetrofitService
import com.example.practicalwisdomleaf.viewModel.MainViewModel
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private  var mBinding: ActivityMainBinding? = null
    private lateinit var adapter: DataAdapter
    var downloadUrls:String=""
    var newid:Int=0
    lateinit var viewModel: MainViewModel
    private val REQUEST_CODE_ASK_PERMISSIONS = 1010
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(mBinding!!.root);
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        // viewModel initialization
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)
        // API call for get Data
        viewModel.getAllData()
        // get MutableLiveData for API response and set to Adapter
        viewModel.data.observe(this, {
            adapter = DataAdapter(it!!, this@MainActivity)
            mBinding!!.rcList.adapter = adapter
        })
        // Swipe refresh data
        mBinding!!.swipe.setOnRefreshListener {
            viewModel.getAllData()
            mBinding!!.swipe.isRefreshing = false
        }
        }


    fun checkPermissions(downloadUrl: String, id: Int) {
        downloadUrls=downloadUrl
        newid=id
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            downloadUrl(downloadUrl)
        } else {
            val hasWriteStoragePermission =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {

                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Please grant storage permission")
                    builder.setCancelable(false)
                    builder.setPositiveButton(
                        "Yes"
                    ) { dialog, id ->
                        dialog.cancel()
                        requestPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            REQUEST_CODE_ASK_PERMISSIONS
                        )
                    }

                    builder.setNegativeButton(
                        "No"
                    ) { dialog, id ->
                        dialog.cancel()

                    }

                    val alert = builder.create()
                    alert.setOnShowListener { arg0: DialogInterface? ->
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(resources.getColor(R.color.purple_200))
                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(resources.getColor(R.color.purple_200))
                    }
                    alert.show()

                } else {
                    requestPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        REQUEST_CODE_ASK_PERMISSIONS
                    )
                }
            } else {     //Permission granted
                downloadUrl(downloadUrl)
            }
        }
    }

    private fun downloadUrl(downloadUrl: String) {
        val myExecutor = Executors.newSingleThreadExecutor()
        val myHandler = Handler(Looper.getMainLooper())
        var mImage: Bitmap?
        myExecutor.execute {
            mImage = mLoad(downloadUrl)
            myHandler.post {
                if (mImage != null) {
                    mSaveMediaToStorage(mImage!!)
                }
            }
        }
    }

    private fun mSaveMediaToStorage(mImage: Bitmap) {
        val filename = "${newid}.jpg"
        val file = File(
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .toString()  + filename
        )
        if (!file.exists()) {
            var fos: OutputStream? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
            } else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)
                fos = FileOutputStream(image)
            }
            fos?.use {
                mImage.compress(Bitmap.CompressFormat.JPEG, 80, it)
                Toast.makeText(this, filename+" Downloaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mLoad(string: String): Bitmap? {
        val url: URL = mStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this , "error" , Toast.LENGTH_SHORT).show()
        }
        return null
    }

    private fun mStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    private fun requestPermission(permissionName: String, permissionRequestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(permissionName), permissionRequestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        try{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadUrl(downloadUrls)
                } else {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }catch (error: Throwable) {
            error.printStackTrace()
        }
    }
}