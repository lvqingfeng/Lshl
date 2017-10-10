package com.lshl.ui.me.dscs.adapter;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.AddGuaranteeItemBinding;
import com.lshl.utils.LogInUtils;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月23日
 * 时间：19:16
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AddGuaranteeAdapter extends RecyclerView.Adapter<BindingViewHolder<AddGuaranteeItemBinding>> {

    private List<GuaranteeBean> mListData;

    public List<GuaranteeBean> getListData() {
        return mListData;
    }

    public void setListData(List<GuaranteeBean> listData) {
        mListData = listData;
        notifyDataSetChanged();
    }

    @Override

    public BindingViewHolder<AddGuaranteeItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_add_guarantee, parent, false);
        return new BindingViewHolder<>(AddGuaranteeItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<AddGuaranteeItemBinding> holder, final int position) {
        holder.getBinding().setGuaranteeBean(mListData.get(position));
        holder.getBinding().tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuaranteeBean bean = mListData.get(getItemCount() - 1);
                String name = holder.getBinding().editName.getText().toString();
                String idCard = holder.getBinding().editIdCard.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(idCard)) {
                    Toast.makeText(holder.mContext, "请将信息填写完整再添加", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!LogInUtils.IDCardValidate(idCard)) {
                    Toast.makeText(holder.mContext, "身份证信息不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                bean.setSuc(true);
                bean.setName(name);
                bean.setIdCard(idCard);
                notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListData.add(new GuaranteeBean());
                        notifyItemInserted(getItemCount());
                    }
                }, 200);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public static class GuaranteeBean implements Parcelable {
        private String name;
        private String idCard;
        private boolean isSuc;

        public boolean isSuc() {
            return isSuc;
        }

        public void setSuc(boolean isSuc) {
            this.isSuc = isSuc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.idCard);
            dest.writeByte(this.isSuc ? (byte) 1 : (byte) 0);
        }

        public GuaranteeBean() {
        }

        protected GuaranteeBean(Parcel in) {
            this.name = in.readString();
            this.idCard = in.readString();
            this.isSuc = in.readByte() != 0;
        }

        public static final Parcelable.Creator<GuaranteeBean> CREATOR = new Parcelable.Creator<GuaranteeBean>() {
            @Override
            public GuaranteeBean createFromParcel(Parcel source) {
                return new GuaranteeBean(source);
            }

            @Override
            public GuaranteeBean[] newArray(int size) {
                return new GuaranteeBean[size];
            }
        };
    }

}
