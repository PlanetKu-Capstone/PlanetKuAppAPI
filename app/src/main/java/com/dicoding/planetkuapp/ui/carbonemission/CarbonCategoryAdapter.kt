package com.dicoding.planetkuapp.ui.carbonemission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.planetkuapp.R

// Data model yang sesuai dengan API
data class CarbonCategory(
    val electriccity: String,
    val gas: String,
    val transportation: String,
    val food: String,
    val organic_waste: String,
    val inorganic_waste: String,
    val carbon_footprint: String
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
        private val tvElectricity: TextView = itemView.findViewById(R.id.tvElectricity)
        private val tvGas: TextView = itemView.findViewById(R.id.tvGas)
        private val tvTransportation: TextView = itemView.findViewById(R.id.tvTransportation)
        private val tvFood: TextView = itemView.findViewById(R.id.tvFood)
        private val tvOrganicWaste: TextView = itemView.findViewById(R.id.tvOrganicWaste)
        private val tvInorganicWaste: TextView = itemView.findViewById(R.id.tvInorganicWaste)
        private val tvCarbonFootprint: TextView = itemView.findViewById(R.id.tvCarbonFootprint)

        fun bind(category: CarbonCategory, onClick: (CarbonCategory) -> Unit) {
            tvElectricity.text = "Electricity: ${category.electriccity} kg CO2"
            tvGas.text = "Gas: ${category.gas} kg CO2"
            tvTransportation.text = "Transportation: ${category.transportation} kg CO2"
            tvFood.text = "Food: ${category.food} kg CO2"
            tvOrganicWaste.text = "Organic Waste: ${category.organic_waste} kg CO2"
            tvInorganicWaste.text = "Inorganic Waste: ${category.inorganic_waste} kg CO2"
            tvCarbonFootprint.text = "Total: ${category.carbon_footprint} kg CO2"

            itemView.setOnClickListener { onClick(category) }
        }
    }
}
