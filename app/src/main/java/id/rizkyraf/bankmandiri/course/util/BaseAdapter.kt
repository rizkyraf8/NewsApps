package id.rizkyraf.bankmandiri.course.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, B : ViewBinding>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> B,
    var list: ArrayList<T> = ArrayList()
) : RecyclerView.Adapter<BaseAdapter.ViewHolder<B>>() {

    var onClickListener: OnItemClickListener<T>? = null
    lateinit var mContext: Context
    private lateinit var binding: B

    interface OnItemClickListener<T> {
        fun onItemClick(item: T, position: Int)
    }

    fun setListener(listener: OnItemClickListener<T>) {
        this.onClickListener = listener
    }

    fun reCreate(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun reCreate(list: ArrayList<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<T> {
        return list
    }

    fun getItem(postion: Int): T {
        return list[postion]
    }

    fun addItem(list: T) {
        this.list.add(list)
        notifyDataSetChanged()
    }

    fun removeItem(list: T) {
        this.list.remove(list)
        notifyDataSetChanged()
    }

    fun addItem(list: T, pos: Int) {
        this.list.add(pos, list)
        notifyDataSetChanged()
    }

    fun addItem(list: List<T>) {
        val countList = list.count()
        val start = this.list.count()

        this.list.addAll(list)
        notifyItemRangeInserted(start, countList)
    }

    fun clearData() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun searchList(list: ArrayList<T>, length: Int) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        mContext = parent.context

        binding = inflateMethod.invoke(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder<B : ViewBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)
}