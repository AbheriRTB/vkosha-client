package abheri.com.vaijayantikosha;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private String[] listdata;
    Context context;
    String listType ="";
    String padam = "";

    // RecyclerView recyclerView;
    public ListAdapter(String[] listdata, Context con, String lstTyp, String padam_text) {
        this.listdata = listdata;
        context = con;
        listType = lstTyp;
        padam = padam_text;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_view_cell, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String myListData = listdata[position];
        holder.textView.setText(listdata[position]);
        /* --- Code to gray out the all the items except first one
        if(position > 0){
            holder.textView.setTextColor(context.getResources().getColor(R.color.lightGray));
            return;
        }
        */
        String txt = (String)holder.textView.getText();
        if(txt != null &&
                (txt.equalsIgnoreCase("WX-alphabetic") || txt.equalsIgnoreCase("Roman-Diacritic"))){
            holder.textView.setTextColor(context.getResources().getColor(R.color.lightGray));
            return;
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("PADAM", padam);
                switch (listType){
                    case "1": intent.putExtra("InputType",myListData);break;
                    case "2": intent.putExtra("Relation", myListData);break;
                    case "3": intent.putExtra("OutputType",myListData);break;
                }

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }

}