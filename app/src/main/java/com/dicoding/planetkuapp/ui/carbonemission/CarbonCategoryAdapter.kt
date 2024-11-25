package com.dicoding.planetkuapp.ui.carbonemission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.planetkuapp.R

data class CarbonCategory(
    val categoryName: String,
    val totalEmission: String
)

class CarbonCategoryAdapter(
    private val onCategoryClick: (CarbonCategory) -> Unit
) : RecyclerView.Adapter<CarbonCategoryAdapter.ViewHolder>() {

    private val categories = mutableListOf<CarbonCategory>()

    fun submitList(newCategories: List<CarbonCategory>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carbon_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position], onCategoryClick)
    }

    override fun getItemCount(): Int = categories.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        private val tvTotalEmission: TextView = itemView.findViewById(R.id.tvTotalEmission)

        fun bind(category: CarbonCategory, onClick: (CarbonCategory) -> Unit) {
            tvCategoryName.text = category.categoryName
            tvTotalEmission.text = category.totalEmission
            itemView.setOnClickListener { onClick(category) }
        }
    }
}
