package space.rodionov.porosenokpetr.feature_driller.presentation.driller

//class DrillerViewHolder(parent: ViewGroup) :
//    BaseViewHolder(parent, R.layout.item_word_card) {
//
//    lateinit var binding: ItemWordCardBinding
//
//    fun bind(word: Word) {
//        binding = ItemWordCardBinding.bind(itemView)
//        binding.apply {
//            tvUpper.text = if(mNativeToForeign) word.nativ else word.foreign
//            tvDowner.text = if(mNativeToForeign) word.foreign else word.nativ
//
//            (binding.root as ViewGroup).redrawViewGroup(isNightBaseAdapter)
//        }
//    }
//}
//
//class DrillerDelegate: AdapterDelegate {
//
//    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun isValidType(model: BaseModel): Boolean = model is Word
//}