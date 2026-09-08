package cz.valleyman.bakalari.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cz.valleyman.bakalari.R
import cz.valleyman.bakalari.data.HomeworkEntity

class HomeworkAdapter(
    private var items: List<HomeworkEntity>,
    private val onCompletedChanged: (HomeworkEntity, Boolean) -> Unit
) : RecyclerView.Adapter<HomeworkAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSubject: TextView = view.findViewById(R.id.tv_subject)
        val tvText: TextView = view.findViewById(R.id.tv_text)
        val tvDueDate: TextView = view.findViewById(R.id.tv_due_date)
        val cbCompleted: CheckBox = view.findViewById(R.id.cb_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_homework, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvSubject.text = item.subject
        holder.tvText.text = item.text
        holder.tvDueDate.text = item.dueDate ?: "No due date"
        
        holder.cbCompleted.setOnCheckedChangeListener(null)
        holder.cbCompleted.isChecked = item.completed
        
        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            onCompletedChanged(item, isChecked)
        }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<HomeworkEntity>) {
        items = newItems
        notifyDataSetChanged()
    }
}
