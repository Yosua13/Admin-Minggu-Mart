package com.example.adminminggumart

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminminggumart.databinding.ActivityAddItemBinding
import com.example.adminminggumart.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {
    //food item detail
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImageUri: Uri? = null

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase
        auth = FirebaseAuth.getInstance()
        //initialize firebase database instance
        database = FirebaseDatabase.getInstance()

        binding.addItemButton.setOnClickListener {
            //get data fill
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient = binding.ingredient.text.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())) {
                uploadData()
                Toast.makeText(this, "Upload Item Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill All The Details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

//        binding.selectImage.setOnClickListener {
//            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        //get a reference to the "menu" node in the database
        val menuRef = database.getReference("menu")
        //generate a unique key for the new menu item
        val newItemKey = menuRef.push().key

        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    //create a new menu item
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUrl.toString(),
                    )
                    newItemKey?.let {key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "data uploaded failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
                .addOnFailureListener {
                    Toast.makeText(this, "data uploaded failed", Toast.LENGTH_SHORT).show()
                }
        } else {
                Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show()
            }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}