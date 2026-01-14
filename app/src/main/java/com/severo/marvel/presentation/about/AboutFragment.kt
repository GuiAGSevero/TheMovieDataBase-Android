package com.severo.marvel.presentation.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.severo.marvel.BuildConfig
import com.severo.marvel.R
import com.severo.marvel.databinding.FragmentAboutBinding
import com.severo.marvel.databinding.FragmentCharactersBinding
import androidx.core.net.toUri

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding: FragmentAboutBinding get() = _binding!!

    private val email by lazy { getString(R.string.gmail) }
    private val githubUrl by lazy { getString(R.string.github_url) }
    private val linkedinUrl by lazy { getString(R.string.linkedin_url) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAboutBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVersion()
        setupLinks()
    }

    private fun setupVersion() {
        val versionText = getString(
            R.string.version,
            BuildConfig.VERSION_NAME
        )
        binding.textVersion.text = versionText
    }

    private fun setupLinks() {
        binding.buttonGithub.setOnClickListener {
            openLink(githubUrl)
        }

        binding.buttonLinkedin.setOnClickListener {
            openLink(linkedinUrl)
        }

        binding.buttonEmail.setOnClickListener {
            sendEmail()
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "$MAILTO_SCHEME$email".toUri()
        }
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        private const val MAILTO_SCHEME = "mailto:"
    }
}