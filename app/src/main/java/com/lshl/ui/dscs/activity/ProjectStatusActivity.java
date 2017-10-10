package com.lshl.ui.dscs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.bean.DscsProjectDetailsBean;
import com.lshl.databinding.ActivityProjectStatusBinding;
import com.lshl.ui.dscs.adapter.ProjectStatusAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import org.json.JSONObject;

/**
 * 项目状态
 */
public class ProjectStatusActivity extends BaseActivity<ActivityProjectStatusBinding> {

    private static final int REQUEST_CODE_FIRST_VOTE = 0x000300;
    private static final int REQUEST_CODE_SECOND_VOTE = 0x000301;
    private static final int REQUEST_CODE_THIRD_VOTE = 0x000302;


    public static final int FROM_MA = 0x0000123;//来自于互助页面
    public static final int FROM_MB = 0x0000124;//来自于公益页面

    private int mFromWhere;//来自于哪个页面
    private int mProjectStatus;//项目的状态
    private String mProjectId;//
    private DscsProjectDetailsBean mProjectDetailsBean;
    private ProjectStatusAdapter mAdapter;


    /**
     * @param activity      当前的Activity
     * @param projectId     项目的id
     * @param projectStatus 项目的状态
     * @param fromWhere     当前的项目来自于哪里{@value FROM_MA 来自于互助界面}，{@value FROM_MB 来自于公益界面}
     */
    public static void actionStart(Activity activity, String projectId, int projectStatus, int fromWhere) {
        Intent intent = new Intent(activity, ProjectStatusActivity.class);
        intent.putExtra("id", projectId);
        intent.putExtra("status", projectStatus);
        intent.putExtra("where", fromWhere);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("状态", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /*DscsProjectDetailsBean.VoteList voteInfo = (DscsProjectDetailsBean.VoteList) data.getSerializableExtra("resultBean");
            switch (requestCode) {
                case REQUEST_CODE_FIRST_VOTE:
                    mProjectDetailsBean.setFistVote(voteInfo);
                    break;
                case REQUEST_CODE_SECOND_VOTE:
                    mProjectDetailsBean.setSecondVote(voteInfo);
                    break;
                case REQUEST_CODE_THIRD_VOTE:
                    mProjectDetailsBean.setThirdVote(voteInfo);
                    break;
            }
            mAdapter.notifyDataSetChanged();*/
        }
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        mFromWhere = intent.getIntExtra("where", -1);
        mProjectId = intent.getStringExtra("id");
        mProjectStatus = intent.getIntExtra("status", -1);

        mProjectDetailsBean = new DscsProjectDetailsBean();
    }

    @Override
    protected void initViews() {

        mAdapter = new ProjectStatusAdapter(mProjectDetailsBean, mProjectStatus + 1, mFromWhere);

        mDataBinding.recyclerStatus.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerStatus.addItemDecoration(new DividerGridItemDecoration(mContext));


 /*       mDataBinding.recyclerStatus.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerStatus) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                switch (vh.getLayoutPosition()) {
                    case 0:
                        ProjectDetailsActivity.actionStart(ProjectStatusActivity.this, mFromWhere, mProjectDetailsBean.getProjectInfo(), mProjectId, vh.getLayoutPosition());
                        break;
                    case 1:
                        VoteActivity.actionStart(ProjectStatusActivity.this, mProjectDetailsBean.getFistVote(), mProjectStatus, mFromWhere, REQUEST_CODE_FIRST_VOTE);
                        break;
                    case 2:
                        VoteActivity.actionStart(ProjectStatusActivity.this, mProjectDetailsBean.getSecondVote(), mProjectStatus, mFromWhere, REQUEST_CODE_SECOND_VOTE);
                        break;
                    case 3:
                        VoteActivity.actionStart(ProjectStatusActivity.this, mProjectDetailsBean.getThirdVote(), mProjectStatus, mFromWhere, REQUEST_CODE_THIRD_VOTE);
                        break;
                    case 4:
                        ProjectDetailsActivity.actionStart(ProjectStatusActivity.this, mFromWhere, mProjectDetailsBean.getProjectInfo(), mProjectId, vh.getLayoutPosition());
                        break;
                    case 5:
                        if (mFromWhere == FROM_MA)
                            GuarantorListActivity.actionStart(ProjectStatusActivity.this, mProjectDetailsBean);
                        else if (mFromWhere == FROM_MB)
                            ProjectDetailsActivity.actionStart(ProjectStatusActivity.this, mFromWhere, mProjectDetailsBean.getProjectInfo(), mProjectId, vh.getLayoutPosition());
                        break;
                }
            }
        });*/
        loadData();
    }

    private void loadData() {
       /* if (mFromWhere == FROM_MA) {//来自于互助页面
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.String
                    ).maProjectDetails(mProjectId, String.valueOf(mProjectStatus))
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody result) {
                            try {
                                String resultStr = result.string();
                                JSONObject object = new JSONObject(resultStr);
                                readJson(object, R.array.ma_project_info, R.array.ma_first_vote, R.array.ma_second_vote, R.array.ma_third_vote);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
            );
        } else if (mFromWhere == FROM_MB) {//来自于公益界面
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.String
                    ).mbProjectDetails(mProjectId, String.valueOf(mProjectStatus))
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody result) {
                            try {
                                String resultStr = result.string();
                                JSONObject object = new JSONObject(resultStr);
                                readJson(object, R.array.mb_project_info, R.array.mb_first_vote, R.array.mb_second_vote, R.array.mb_third_vote);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
            );
        }*/
    }

    private void readJson(JSONObject object, int... arrayConfig) {

        try {
            String status = object.getString("status");
           /* if (status.equals(ApiService.STATUS_SUC)) {
                JSONObject infoObject = object.getJSONObject("info");
                String projectStatus = String.valueOf(mProjectStatus);
                DscsProjectDetailsBean.ProjectInfo info = JsonUtils.json2Object(mContext, infoObject, arrayConfig[0], DscsProjectDetailsBean.ProjectInfo.class);
                info.setStatus(projectStatus);

                DscsProjectDetailsBean.VoteList firstVote = JsonUtils.json2Object(mContext, infoObject, arrayConfig[1], DscsProjectDetailsBean.VoteList.class);
                firstVote.setStatus("1");
                firstVote.setId(mProjectId);
                mProjectDetailsBean.setFistVote(firstVote);

                if (mProjectStatus != 1) {
                    DscsProjectDetailsBean.VoteList secondVote = JsonUtils.json2Object(mContext, infoObject, arrayConfig[2], DscsProjectDetailsBean.VoteList.class);
                    secondVote.setStatus("2");
                    secondVote.setId(mProjectId);
                    mProjectDetailsBean.setSecondVote(secondVote);
                }
                if (mProjectStatus >= 3) {
                    DscsProjectDetailsBean.VoteList thirdVote = JsonUtils.json2Object(mContext, infoObject, arrayConfig[3], DscsProjectDetailsBean.VoteList.class);
                    thirdVote.setStatus("3");
                    thirdVote.setId(mProjectId);
                    mProjectDetailsBean.setThirdVote(thirdVote);
                }
                if (mProjectStatus >= 4 && mFromWhere == FROM_MB) {
                    JSONArray zxrArray = infoObject.getJSONArray("zxr");
                    List<DscsProjectDetailsBean.ZxrBean> zxrList = JSON.parseArray(zxrArray.toString(), DscsProjectDetailsBean.ZxrBean.class);
                    mProjectDetailsBean.setZxr(zxrList);
                }
                if (mProjectStatus >= 5 && mFromWhere == FROM_MA) {
                    JSONArray guaranteeArray = infoObject.getJSONArray("guarantee");
                    List<DscsProjectDetailsBean.GuaranteeBean> guaranteeList = JSON.parseArray(guaranteeArray.toString(), DscsProjectDetailsBean.GuaranteeBean.class);
                    JSONArray guaranteeimgArray = infoObject.getJSONArray("guaranteeimg");
                    List<DscsProjectDetailsBean.GuaranteeimgBean> guaranteeimgArrayList = JSON.parseArray(guaranteeimgArray.toString(), DscsProjectDetailsBean.GuaranteeimgBean.class);
                    mProjectDetailsBean.setGuaranteeimg(guaranteeimgArrayList);
                    mProjectDetailsBean.setGuarantee(guaranteeList);
                }
                mProjectDetailsBean.setProjectInfo(info);
                mProjectDetailsBean.setId(mProjectId);

                mDataBinding.recyclerStatus.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else if (status.equals(ApiService.TOKEN_EX)) {
                showMessage("登陆信息异常，请重新登陆");
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("数据异常请重试");
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_status;
    }
}
