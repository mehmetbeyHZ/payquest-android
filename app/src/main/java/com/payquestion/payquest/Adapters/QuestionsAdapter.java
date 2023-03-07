package com.payquestion.payquest.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.payquestion.payquest.Models.Question;
import com.payquestion.payquest.R;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder>{

    public boolean isShowPlay() {
        return showPlay;
    }

    public void setShowPlay(boolean showPlay) {
        this.showPlay = showPlay;
    }

    public boolean showPlay = true;
    public List<Question> mData;
    private Context context;
    private QuestionListener questionListener;
    public QuestionsAdapter(Context context, List<Question> mData,QuestionListener questionListener)
    {
        this.context = context;
        this.mData = mData;
        this.questionListener = questionListener;
        this.setShowPlay(true);
    }

    public void setMyData(List<Question> newData)
    {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new QuestionDiffUtil(newData, mData));
        diffResult.dispatchUpdatesTo(this);
        mData.clear();
        mData.addAll(newData);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_question_item,parent,false);
        ViewHolder holder = new ViewHolder(view,questionListener);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = mData.get(position);
        holder.gameImage.setImageResource(R.drawable.ic_task_gradient);

        if (!this.isShowPlay())
        {
            holder.startGame.setVisibility(View.GONE);
        }

        if(question.getMissionDetail() == null)
        {
            holder.gameImage.setImageResource(R.drawable.close);
            holder.gameTitle.setText(R.string.question);
            holder.gameDescription.setText(R.string.game_check_error);
            holder.gameXP.setText("XP: 0");
            holder.gamePrice.setText("0 ₺");
            return;
        }

        if (question.getMissionDetail().getIsQuestion() == 2)
        {
            holder.gameImage.setImageResource(R.drawable.gift);
            holder.gameTitle.setText(R.string.private_mission);
            holder.gameDescription.setText(R.string.private_mission_desc);
        }
        holder.gamePrice.setText(question.getMissionDetail().getMissionValue()+ "₺");
        holder.gameXP.setText("XP: " + String.valueOf(question.getMissionDetail().getMissionXp()));


        if (question.getIsCompleted() == 1)
        {
            holder.gameImage.setImageResource(R.drawable.tick);
            holder.gameTitle.setText(R.string.mission_completed_title);
            holder.gameDescription.setText(R.string.mission_completed_desc);
            holder.startGame.setEnabled(false);
            holder.startGame.setClickable(false);
        }else if (question.getIsCompleted() == 2)
        {
            holder.gameImage.setImageResource(R.drawable.close);
            holder.gameTitle.setText(R.string.mission_complete_failed_title);
            holder.gameDescription.setText(R.string.mission_complete_failed_desc);
        }else if(question.getIsCompleted() == 3)
        {
            holder.gameImage.setImageResource(R.drawable.close);
            holder.gameTitle.setText(R.string.mission_cancel);
            holder.gameDescription.setText(R.string.mission_cancel_desc);
            holder.startGame.setEnabled(false);
            holder.startGame.setClickable(false);
        }else if (question.getIsCompleted() == 4)
        {
            holder.gameImage.setImageResource(R.drawable.timer);
            holder.gameTitle.setText(R.string.mission_checking);
            holder.gameDescription.setText(R.string.mission_checking_desc);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gameTitle,gameDescription,gamePrice,gameXP;
        ImageView gameImage;
        LinearLayout startGame,gameLayout;
        QuestionListener questionListener;
        public ViewHolder(@NonNull View itemView,QuestionListener questionListener) {
            super(itemView);
            gameLayout = itemView.findViewById(R.id.gameLayout);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameDescription = itemView.findViewById(R.id.gameDescription);
            gamePrice = itemView.findViewById(R.id.gamePrice);
            gameImage = itemView.findViewById(R.id.gameImage);
            startGame = itemView.findViewById(R.id.startGame);
            gameXP    = itemView.findViewById(R.id.gameXP);
            startGame.setOnClickListener(this);
            gameLayout.setOnClickListener(this);
            this.questionListener = questionListener;
        }

        @Override
        public void onClick(View v) {
            questionListener.questionClick(getAdapterPosition());
        }
    }

    public interface QuestionListener{
        void questionClick(int position);
    }
}
