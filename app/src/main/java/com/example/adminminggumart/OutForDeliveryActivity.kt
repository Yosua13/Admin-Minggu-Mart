package com.example.adminminggumart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminminggumart.adapter.DeliveryAdapter
import com.example.adminminggumart.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        val customerName = arrayListOf(
            "Yosua Reynaldi",
            "Kezia Gurning",
            "Elia Roynaldi"
        )
        val moneyStatus = arrayListOf(
            "received",
            "notReceived",
            "pending"
        )
        val adapter = DeliveryAdapter(customerName, moneyStatus)
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deliveryRecyclerView.adapter = adapter
    }
}