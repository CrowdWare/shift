package at.crowdware.shift.ui
import android.app.Activity
import android.content.Context
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import at.crowdware.shift.R

class TransactionListAdapter (private val context: Context, val inflater: LayoutInflater, val list: MutableList<ListItem>):
    ArrayAdapter<ColorSpace.Model>(context, R.layout.list_row) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_row, null, true)

        val date = rowView.findViewById(R.id.date) as TextView
        val text = rowView.findViewById(R.id.text) as TextView
        val value = rowView.findViewById(R.id.value) as TextView

        date.text = list[position].date
        text.text = list[position].text
        value.text = list[position].value
        println(date.text)
        return rowView
    }

    override fun getCount(): Int {
        return list.size
    }
}
