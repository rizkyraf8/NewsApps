package id.rizkyraf.bankmandiri.course.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    abstract fun didMount()
    abstract fun onClick()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        didMount()
        onClick()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}