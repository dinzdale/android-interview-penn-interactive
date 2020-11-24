package com.png.interview.ui.heroes

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.png.interview.api.models.heroes.Hero
import com.png.interview.api.models.heroes.HeroRole
import com.png.interview.databinding.DataboundViewHolder
import com.png.interview.databinding.ViewBasicHeroListItemBinding
import com.png.interview.databinding.ViewDetailedHeroListItemBinding
import com.png.interview.extensions.getLayoutInflater
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import javax.inject.Provider

@FlowPreview
@ExperimentalCoroutinesApi
class HeroesAdapter
@Inject constructor(
    private val viewBinders: Provider<BasicHeroItemViewBinder>,
    private val detailedViewBinders: Provider<DetailedHeroItemViewBinder>

) : RecyclerView.Adapter<DataboundViewHolder<ViewDataBinding>>() {

    var data = listOf<HeroAdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataboundViewHolder<ViewDataBinding> {
        return DataboundViewHolder(
            when (viewType) {
                HeroRole.SUPPORT.ordinal -> ViewDetailedHeroListItemBinding.inflate(parent.getLayoutInflater(), parent, false).apply {
                    viewBinder = detailedViewBinders.get()
                }
                else -> ViewBasicHeroListItemBinding.inflate(parent.getLayoutInflater(), parent, false).apply {
                    viewBinder = viewBinders.get()
                }
            }

        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DataboundViewHolder<ViewDataBinding>, position: Int) {
        when (holder.binding) {
            is ViewBasicHeroListItemBinding -> {
                (data[position] as? HeroAdapterItem.HeroItem)?.hero.let {
                    holder.binding.viewBinder?.bind(it)
                }
            }
            is ViewDetailedHeroListItemBinding -> {
                (data[position] as? HeroAdapterItem.HeroItem)?.hero.let {
                    holder.binding.viewBinder?.bind(it)
                }
            }
        }
        holder.binding.invalidateAll()
    }

    override fun getItemViewType(position: Int): Int {
        return when (val hero = data[position]) {
            is HeroAdapterItem.HeroItem -> {
                hero.hero.role?.ordinal ?: TYPE_UKNOWN
            }
            is HeroAdapterItem.Footer -> TYPE_FOOTER
            else -> TYPE_UKNOWN
        }
    }

    fun bindData(heroes: List<Hero>) {
        this.data = heroes.map { HeroAdapterItem.HeroItem(it) }
        notifyDataSetChanged()
    }

    sealed class HeroAdapterItem {
        data class HeroItem(val hero: Hero) : HeroAdapterItem()
        object Header : HeroAdapterItem()
        object Footer : HeroAdapterItem()
    }

    companion object {
        private const val TYPE_UKNOWN = -1
        private const val TYPE_FOOTER = 100
    }
}
