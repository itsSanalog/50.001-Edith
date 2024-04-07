//package com.example.edith.activities;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.graphics.Canvas;
//import android.graphics.Color;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.edith.R;
//import com.example.edith.adapters.TaskAdapter;
//import com.google.firebase.database.collection.LLRBNode;
//
//import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
//
//public class TouchHelper extends ItemTouchHelper.SimpleCallback {
//
//    private TaskAdapter adapter;
//
//    public TouchHelper(TaskAdapter adapter){
//        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
//        this.adapter = adapter;
//    }
//
//
//    @Override
//    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        return false;
//    }
//
//    @Override
//    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//        final int position = viewHolder.getAdapterPosition();
//        if(direction == ItemTouchHelper.RIGHT){
//            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
//            builder.setMessage("Are you sure you want to delete this task?")
//                    .setTitle("Delete Task")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            adapter.deleteTask(position);
//                        }
//                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            adapter.notifyItemChanged(position);
//                        }
//           });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        } else {
//            adapter.editTask(position);
//        }
//    }
//
//    @Override
//    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
//        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                .addSwipeRightActionIcon(R.drawable.delete_icon)
//                .addSwipeRightBackgroundColor(Color.RED)
//                .addSwipeLeftActionIcon(R.drawable.edit_icon)
//                .addSwipeLeftBackgroundColor(ContextCompat.getColor(adapter.getContext(), R.color.spacegrey))
//                .create()
//                .decorate();
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//    }
//}
