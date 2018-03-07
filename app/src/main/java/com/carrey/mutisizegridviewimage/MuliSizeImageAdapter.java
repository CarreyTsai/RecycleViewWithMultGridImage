package com.carrey.mutisizegridviewimage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pccai on 2018/3/7.
 */

public class MuliSizeImageAdapter extends RecyclerView.Adapter<MuliSizeImageAdapter.ViewHolder> {

    private List<ImageBean> data;

    public MuliSizeImageAdapter(List<ImageBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    /**
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        ImageBean imageBean = data.get(position);
        if (imageBean.images == null) {
            return 1;
        } else if (imageBean.images.size() == 1) {
            return 2;
        } else if (imageBean.images.size() == 4 || imageBean.images.size() == 7) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private FixScrollViewGridView mGridView;

        private int imageItemHeight = 0;

        private ImageAdapter mImageAdapter;
        int viewType;
        private LayoutInflater mInflater;

        public ViewHolder(View view, int viewType) {
            super(view);
            mInflater = LayoutInflater.from(view.getContext());
            this.viewType = viewType;

            title = view.findViewById(R.id.title);
            mGridView = view.findViewById(R.id.view_comments_item_gv_images);

            int screenPixelsWidth = getScreenPixelsWidth(itemView.getContext());
            // 总宽度等于 屏幕宽度- 左右边距宽度- 间隙宽度X3
            int gvImageWidth = screenPixelsWidth - dip2px(itemView.getContext(), 12 + 12)
                    - mGridView.getVerticalSpacing() * 3;
            int itemWidth = gvImageWidth / 4;


            switch (viewType) {
                default:
                case 1:
                    mGridView.setVisibility(View.GONE);
                    break;
                case 2:
                    imageItemHeight = (int) (itemWidth * 1.5);
                    mGridView.setNumColumns(1);
                    break;
                case 3:
                    imageItemHeight = itemWidth;
                    mGridView.setNumColumns(4);
                    break;
                case 4:
                    imageItemHeight = itemWidth;
                    mGridView.setNumColumns(3);
                    break;
            }
            mGridView.setColumnWidth(imageItemHeight);
        }

        public void bindData(ImageBean imageBean) {

            title.setText(imageBean.name);

            bindImage(imageBean.images);

        }

        private void bindImage(List<Integer> images) {
            if (images == null || images.size() == 0) {
                return;
            }
            if (viewType == 1) {
                return;
            }

            //是否重新加载
            Object tag = mGridView.getTag();
            if (tag != null && tag.equals(images)) {
                return;
            }

            mGridView.setTag(images);

            int lastCount = 0;
            if (mImageAdapter == null) {
                mImageAdapter = new ImageAdapter(imageItemHeight, images, mInflater);
                mGridView.setAdapter(mImageAdapter);
            } else {
                lastCount = mImageAdapter.getCount();
                mImageAdapter.setImages(images);
            }
            //如果缓存了高度
            if (imageItemHeight != 0) {
                switch (viewType) {
                    //4列和三列 可能存在高度问题
                    case 3:
                        if (lastCount != images.size()) {//行数不同重新计算
                            calculateGridViewheight(images, 4);
                        }
                        break;
                    case 4:
                        if (lastCount != images.size()) {//行数不同重新计算
                            calculateGridViewheight(images, 4);
                        }


                        break;
                    case 2:
                        mGridView.getLayoutParams().height = imageItemHeight +
                                mGridView.getPaddingTop() + mGridView.getPaddingBottom();
                        break;

                }
            }
        }

        private void calculateGridViewheight(@NonNull List<Integer> imageList, int column) {
            int rowNum = imageList.size() + 1 / column;//...9/3=3 余0 所以判断行数需要加1
            //条目高度* 行数 + 行间距*（行数-1）
            int height = imageItemHeight * rowNum + (rowNum - 1) * mGridView.getHorizontalSpacing() +
                    mGridView.getPaddingTop() + mGridView.getPaddingBottom();
            mGridView.getLayoutParams().height = height;
        }

    }

    static class ImageAdapter extends BaseAdapter {

        private List<Integer> imageList;
        private int imageHeight;
        private LayoutInflater mLayoutInflater;

        ImageAdapter(int imageItemHeight, List<Integer> list, LayoutInflater mLayoutInflater) {
            imageList = list;
            this.imageHeight = imageItemHeight;
            this.mLayoutInflater = mLayoutInflater;
        }

        @Override
        public int getCount() {

            if (imageList == null) {
                return 0;
            } else if (imageList.size() >= 9) {
                return 9;
            } else {
                return imageList.size();
            }
        }

        @Override
        public Integer getItem(int position) {
            return imageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Nullable
        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            ImageAdapter.ViewHoler viewHoler = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.image_item, parent,
                        false);
                if (imageHeight != 0) {
                    convertView.setLayoutParams(new AbsListView.LayoutParams(imageHeight, imageHeight));
                }
                viewHoler = new ImageAdapter.ViewHoler(convertView);
                convertView.setTag(viewHoler);
            } else {
                viewHoler = (ImageAdapter.ViewHoler) convertView.getTag();
            }

            if (imageList != null && imageList.size() != 0) {
                viewHoler.sv.setImageResource(imageList.get(position));
            }
            return convertView;
        }

        public void setImages(List<Integer> imageList) {
            this.imageList = imageList;
            notifyDataSetChanged();

        }

        static class ViewHoler {
            ImageView sv;

            ViewHoler(@NonNull View convertView) {
                sv = convertView.findViewById(R.id.item_gv);
            }
        }
    }


    public static int getScreenPixelsWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenPixelsHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * getScreenScale(context) + 0.5F);
    }

    private static float getScreenScale(Context context) {
        try {
            return context.getResources().getDisplayMetrics().density;
        } catch (Exception var2) {
            return 1.0F;
        }
    }

}
