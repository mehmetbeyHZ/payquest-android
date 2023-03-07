package com.payquestion.payquest.Adapters;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.payquestion.payquest.Models.Question;

import java.util.List;

public class QuestionDiffUtil extends DiffUtil.Callback {
    List<Question> oldList;
    List<Question> newList;

    public QuestionDiffUtil(List<Question> oldList, List<Question> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newList.get(newItemPosition).getMissionHandleId().compareTo(oldList.get(oldItemPosition).getMissionHandleId());
        return result == 0;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }


}
