package in.sethiya.bizzbots.bfsi.finces.merchant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ModelSelectionBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.model.States;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {
    private final Context context;
    private final List<States> selectionList;
    private final OnClickInterface onClickInterface;
    int requestId;

    public SelectionAdapter(int requestId, List<States>selectionList, Context context){
        this.selectionList = selectionList;
        this.requestId = requestId;
        this.context = context;

        try{
            this.onClickInterface = ((OnClickInterface)context);
        }catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ModelSelectionBinding binding = ModelSelectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.text1.setText(selectionList.get(position).getSelectionName());

        holder.binding.main.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("selection_name", selectionList.get(position).getSelectionName());
            intent.putExtra("selection_code", selectionList.get(position).getSelectionCode());
            intent.putExtra("request_id", String.valueOf(requestId));
            onClickInterface.onClickInterface(intent);
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public int getItemCount() {
        return selectionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ModelSelectionBinding binding;

        public ViewHolder(ModelSelectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickInterface{
        public void onClickInterface(Intent intent);
    }
}
