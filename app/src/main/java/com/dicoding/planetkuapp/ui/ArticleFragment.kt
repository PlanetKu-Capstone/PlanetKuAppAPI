package com.dicoding.planetkuapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.planetkuapp.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val articles = listOf(
        Article("Apa itu Planetku?", "Planetku adalah aplikasi untuk ..."),
        Article("Cara Mengelola Sampah", "Mengelola sampah adalah ...")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArticleAdapter(articles) { article ->
            val intent = Intent(requireContext(), DetailArticleActivity::class.java)
            intent.putExtra(DetailArticleActivity.EXTRA_TITLE, article.title)
            intent.putExtra(DetailArticleActivity.EXTRA_CONTENT, article.content)
            startActivity(intent)
        }

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}