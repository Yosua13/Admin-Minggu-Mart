package com.example.adminminggumart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminminggumart.adapter.DeliveryAdapter
import com.example.adminminggumart.adapter.PendingOrderAdapter
import com.example.adminminggumart.databinding.ActivityPendingOrderBinding
import com.example.adminminggumart.databinding.PendingOrdersItemBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        val orderedCustomerName = arrayListOf(
            "Yosua Reynaldi",
            "Kezia Gurning",
            "Elia Roynaldi"
        )
        val orderedQuanty = arrayListOf(
            "5",
            "8",
            "9"
        )
        val orderedFoodImage = arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3)
        val adapter = PendingOrderAdapter(orderedCustomerName, orderedQuanty, orderedFoodImage, this)
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pendingOrderRecyclerView.adapter = adapter
    }
}