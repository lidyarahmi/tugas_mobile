import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tugasmobile.R
import com.example.tugasmobile.network.Character

class MainAdapter(private val characterList: List<Character>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name)
        private val statusTextView: TextView = itemView.findViewById(R.id.status)
        private val genderTextView: TextView = itemView.findViewById(R.id.gender)
        private val imageView: ImageView = itemView.findViewById(R.id.image)

        fun bindData(character: Character) {
            nameTextView.text = character.name
            statusTextView.text = character.status
            genderTextView.text = character.gender
            Glide.with(imageView.context)
                .load(character.image)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(characterList[position])
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}
