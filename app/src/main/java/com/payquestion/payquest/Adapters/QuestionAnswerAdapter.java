package com.payquestion.payquest.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.payquestion.payquest.R;
import java.util.List;

public class QuestionAnswerAdapter  extends RecyclerView.Adapter<QuestionAnswerAdapter.ViewHolder>{

    List<String> mData;
    public AnswerListener answerListener;

    public QuestionAnswerAdapter(List<String> mData, AnswerListener listener){
        this.mData = mData;
        this.answerListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_question_answer_item,parent,false);
        ViewHolder holder = new ViewHolder(view,answerListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.questionText.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView questionText;
        AnswerListener answerListener;
        public ViewHolder(@NonNull View itemView,AnswerListener answerListener) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            itemView.setOnClickListener(this);
            this.answerListener = answerListener;
        }

        @Override
        public void onClick(View v) {
            answerListener.answerClick(getAdapterPosition());
        }
    }



    public interface AnswerListener{
        void answerClick(int position);
    }

}
