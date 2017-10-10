package com.lshl.ui.dscs.activity;

/**
 * 担保协议列表
 */
/*public class AgreementListActivity extends BaseActivity<ActivityAgreementListBinding> {

    private DscsProjectDetailsBean mProjectDetailsBean;

    public static void actionStart(Activity activity, DscsProjectDetailsBean bean) {
        Intent intent = new Intent(activity, AgreementListActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("担保协议", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mProjectDetailsBean = (DscsProjectDetailsBean) getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void initViews() {
        mDataBinding.recyclerImage.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerImage.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerImage.setAdapter(new ImageAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement_list;
    }

    private class ImageAdapter extends RecyclerView.Adapter<BindingViewHolder<ImageBinding>> {

        @Override
        public BindingViewHolder<ImageBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = getLayoutInflater().inflate(R.layout.item_layout_image_fill, parent, false);
            return new BindingViewHolder<>(ImageBinding.bind(rootView));
        }

        @Override
        public void onBindViewHolder(BindingViewHolder<ImageBinding> holder, int position) {
            holder.getBinding().setImageBean(mProjectDetailsBean.getGuaranteeimg().get(position));
        }

        @Override
        public int getItemCount() {
            return mProjectDetailsBean.getGuaranteeimg().size();
        }
    }

}*/
