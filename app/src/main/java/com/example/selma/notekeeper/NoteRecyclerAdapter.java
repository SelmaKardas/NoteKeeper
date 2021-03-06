package com.example.selma.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.selma.notekeeper.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.example.selma.notekeeper.NoteKeeperDatabaseContract.NoteInfoEntry;


public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private  Cursor mCursor;
    private final LayoutInflater mLayoutInflater;
    private int mCoursepos;
    private int mNoteTitlepos;
    private int m_iDpos;

    public NoteRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mLayoutInflater = LayoutInflater.from(mContext);
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor==null)
            return;
        mCoursepos = mCursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_TITLE);
        mNoteTitlepos = mCursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);
        m_iDpos = mCursor.getColumnIndex(NoteInfoEntry._ID);


    }
    public void changeCursor(Cursor cursor){
        if(mCursor!=null)
            mCursor.close();
        mCursor=cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_note_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String course=mCursor.getString(mCoursepos);
        String title=mCursor.getString(mNoteTitlepos);
        int id=mCursor.getInt(m_iDpos);

        holder.mTextCourse.setText(course);
        holder.mTextTitle.setText(title);
        holder.mId =id;
    }

    @Override
    public int getItemCount() {
        return mCursor==null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextCourse;
        public final TextView mTextTitle;
        public int mId;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextCourse = (TextView) itemView.findViewById(R.id.text_course);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NoteActivity.class);
                    intent.putExtra(NoteActivity.NOTE_ID, mId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}







