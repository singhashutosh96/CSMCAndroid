package cn.fanrunqi.materiallogin.Activity.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.fanrunqi.materiallogin.Activity.Model.Project;
import cn.fanrunqi.materiallogin.R;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

/**
 * Created by Akanksha on 3/26/2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewholder> {

    private Context mctx;
    private List<Project> projectList;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public ProjectAdapter(Context mctx, List<Project> projectList) {
        this.mctx = mctx;
        this.projectList = projectList;

        for(int i=0;i<projectList.size();i++)
            expandState.append(i,false);
    }

    @Override
    public ProjectViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.recycler_project,null);
        return new ProjectViewholder(view);
    }

    @Override
    public void onBindViewHolder(final ProjectViewholder holder, final int position) {

        Project project = projectList.get(position);

        holder.setIsRecyclable(false);
        holder.pname.setText(project.getPname());
        holder.pdesc.setText(project.getPdesc());

        int m = Integer.parseInt(project.getPmilestones());

        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {

            @Override
            public void onPreOpen() {
                changeRotate(holder.dropdown,0f,180f).start();
                expandState.put(position,true);

            }

            @Override
            public void onPreClose() {

                changeRotate(holder.dropdown,180f,0f).start();
                expandState.put(position,false);

            }

        });

        holder.dropdown.setRotation(expandState.get(position)?180f:0f);
        holder.dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Expandable Child View

                holder.expandableLayout.toggle();
            }
        });


    }

    private ObjectAnimator changeRotate(RelativeLayout dropdown, float from, float to) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(dropdown,"rotation",from,to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }


    public class ProjectViewholder extends RecyclerView.ViewHolder{


        TextView pname,pdesc,pmilestone;
        RelativeLayout dropdown;
        ExpandableLinearLayout expandableLayout;

        public ProjectViewholder(View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.tvprojname);
            pdesc = itemView.findViewById(R.id.tvprojdesc);
            pmilestone = itemView.findViewById(R.id.tvmilestone);
            dropdown= itemView.findViewById(R.id.dropdown);
            expandableLayout=itemView.findViewById(R.id.expandableLayout);

        }
    }

}
