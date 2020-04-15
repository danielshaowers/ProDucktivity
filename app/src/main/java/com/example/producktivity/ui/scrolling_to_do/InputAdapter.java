package com.example.producktivity.ui.scrolling_to_do;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.todo.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

//import static com.example.flashcards.TaskFragment.GET_FROM_GALLERY; //not sure if it should be input activity

//this class creates views for data, and replaces the content of views when they are no longer available
//dang it i can't figure out how to make the inputviewHolder work as a static class instead of nonstatic class
public class InputAdapter extends RecyclerView.Adapter<InputAdapter.TaskViewHolder> {

    private final DateFormat dateFormat = new SimpleDateFormat("mm-dd");

    private LayoutInflater mInflater;
    private List<Task> tasks; //cached copy of words
    private OnTaskItemClick onTaskItemClick;


    //obtains the layoutinflater from the given context. layoutinflater converts the xml file into its corresponding view
    InputAdapter(Fragment frag, List<Task> tasks) {
        Objects.requireNonNull(tasks);

        mInflater = LayoutInflater.from(frag.getContext());
        this.tasks = tasks;

        onTaskItemClick = (OnTaskItemClick)frag;
    }

    @Override @NonNull
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.single_task_rcyclr,  parent, false);
        return new TaskViewHolder(itemView);
    }

    //TaskViewHolder is the current view of our cardview
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position){
        if (tasks == null) {
            holder.title.setText("Enter tasks and View Tasks Here");
        }
        Task current = tasks.get(position);
        System.out.println("current task at position " + position + " is " + current.getTitle());
        holder.title.setText(current.getTitle());

        if (current.getDueDate() != null)
            holder.date.setText(dateFormat.format(current.getDueDate())); //formats in mm/dd form //not sure if this one is correct
            //MainActivity.makeCalendar(holder.date, this.mContext);
        holder.desc.setText(current.getDesc());
        if (current.getReminderTime() != null)
            holder.reminder.setText(dateFormat.format(current.getReminderTime())); //formats in mm/dd form


        switch(current.getPriority()) {
            case HIGH:
                holder.priority.setText("High Priority");
            case MED:
                holder.priority.setText("Medium Priority");
            case LOW:
                holder.priority.setText("Low Priority");
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
    }

    public interface OnTaskItemClick {
        void onTaskClick(int pos);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView date;
        private TextView desc;
        private TextView reminder;
        private TextView priority;

        private TaskViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.todo_title_view);
            date = itemView.findViewById(R.id.todo_date_view);
            desc = itemView.findViewById(R.id.todo_description_view);
            reminder = itemView.findViewById(R.id.todo_reminder_view);
            priority = itemView.findViewById(R.id.priority_view);

        } //could set onclick listeners for the calendars here if i was motivated

        //Clicking pulls up the other menu
        @Override
        public void onClick(View v) {
            onTaskItemClick.onTaskClick(getAdapterPosition());
        }

    }




/*
    private ArrayList<Task> allData;
    private Context mContext;
    private int focusPosition = 0;
    private View.OnFocusChangeListener focusChangeListener;
    private int previousPosition;
    private TextInputEditText oldEditText;


    public int getPos() {
        return focusPosition;
    }

    public InputAdapter(ArrayList<Task> input) {
        allData = input;
    }
    //creates new views (is invoked by the layout manager)
    //remember that viewgroup is a view that can be recycled into a different view
    //sets InputViewHolder to store the view given in the parameter
  /*  @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do, parent, false);
        mContext = parent.getContext();
        return new InputViewHolder(view);
    } */ //DANIEL: COMMENTED OUT WHILE CREATING NEW

   /* @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Info current = allData.get(position);
        TextInputEditText et = ((InputViewHolder) holder).term;
        et.setText(current.getText());
        et.setTag(position);

    }
    //replace the contents (data) of a view (invoked by the layout manager) when the view needs to be used again
    //get element from your dataset at this position
    //replace the contents of the view with that element
    //((InputViewHolder)holder).term.setOnFocusChangeListener(null);
    // this way, the FocusChangeListener can't be called WHILE onBindViewHolder is preparing info
    //this catches callback first. if no payload, then passes it on to onBindViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (!payloads.isEmpty()) {
            if (payloads.get(0) instanceof String) {
                ((InputViewHolder) holder).fraction.setText((String) payloads.get(0));
            }
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }
    //https://github.com/android/architecture-components-samples/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/ui/ProductAdapter.java#L44
    //detects changes in a single row without changing EVERYTHING
    public void setDataSet(final ArrayList<Info> ds) {
        //if the dataset is empty, then we just update it
        if (allData == null) {
            allData = ds;
            notifyItemRangeInserted(0, ds.size());
        }
        else{ //if the dataset has changed, we check the differences
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return allData.size();
                }

                @Override
                public int getNewListSize() {
                    return ds.size();
                }

                @Override //unsure how these two are suppossed to be different
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return allData.get(oldItemPosition).equals(ds.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return allData.get(oldItemPosition).equals(ds.get(newItemPosition));
                }
            });
            allData = ds;
            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public int getItemCount() {
        return allData.size();
    }

    public class InputViewHolder extends RecyclerView.ViewHolder {

        public TextInputEditText term;
        public ImageView image;
        public ImageButton imageButton, audioButton;
        public MediaStore.Audio audio; //this one i just yoloed. don't know what it actually is
        public TextView fraction;

*/ //DANIEL: COMMENTED OUT WHILE CREATING ENTIRELY NEW BOI
     /*   public InputViewHolder(final View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            //  this.fraction = (TextView) itemView.findViewById(R.id.fraction); if i want fraction
            this.term = itemView.findViewById(R.id.task_input);  //very important. sets the string of the viewHolder equal to the string in the edit text
            //imageButton = itemView.findViewById(R.id.photoButton); both these if i want images
            //imageButton.setOnClickListener(new AddImageListener());
            term.setFocusable(true);*/ //DANIEL: COMMENTED OUT TO CREATE NEW BOI
           /* term.setOnClickListener(new View.OnClickListener() { //already originally commented out
                @Override
                public void onClick(View v) {
                    System.out.println("onclick called");
                    term.setOnFocusChangeListener(focusChangeListener); //THIS IS WHAT I JUST CHANGED
                    addNewRow(getLayoutPosition());
                }
            }); */

          /*  term.setOnTouchListener(new myListener());
            focusChangeListener = new View.OnFocusChangeListener() { //i might have to specifically do each textview, edittext, imageview
                @Override
                //called when the focus state of a view has changed. when it gets focus, and when it loses focus
                public void onFocusChange(final View v, boolean hasFocus) { //i guess this isn't used when you click directly
                    if (hasFocus) {
                        /*    focusPosition = getAdapterPosition(); //why the heck does this give a different position. ALRDY COMMENTED
                          focusPosition = (int)v.getTag();
                          System.out.println(focusPosition + " hasFocus");
                             looks like adapterposition is different from the click position :(
                    */}
                   /* if (!hasFocus) { //so my problem is that it loses focus while it's still being edited for some reason
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                focusPosition = (int) v.getTag(); // perfect EXCEPT when a new row is added, this !hasFocus is called twice...?
                                if (focusPosition >= 0 && allData.get(focusPosition) != null && allData != null) {
                                    oldEditText = v.findViewById(R.id.task_input);
                                    Info lastTerm = allData.get(focusPosition).copyInfo(allData.get(focusPosition));   //sets lastterm to what it was before updating the arraylist
                                    if (!lastTerm.getText().equals(oldEditText.getText().toString())) {  //if the edittext changed, update the array's text
                                        allData.get(focusPosition).setText(oldEditText.getText().toString());
                                        // updateFraction(allData.get(focusPosition), focusPosition, lastTerm);// commented out bc update fraction doesnt exist
                                    }


                                    oldEditText.setOnFocusChangeListener(null);
                                }
                            }
                        });
                    }
                }
            };
        }


        public class myListener implements View.OnTouchListener {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setOnFocusChangeListener(focusChangeListener); //sets it so that when the view loses focus, this is all captured
                    // focusPosition = (int)v.getTag();
                    addNewRow((int) v.getTag());
                }
                return false;
            }

        }

        //if I want to give image
  /*      public class AddImageListener implements ImageButton.OnClickListener {
            @Override
            public void onClick(View view) {
                //starts an activity that opens the gallery
                focusPosition = getAdapterPosition(); //I think this one is unnecessary, since I have a focuschangelistener that sets the position on focus
                ((Activity) mContext).startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                //don't i need to override the method...in this class?
                //get the image and set the image
                if (allData.get(focusPosition).getImage() != null)
                    view.findViewById(R.id.image).setVisibility(View.VISIBLE); //does this after startactivityforresult and onactivityresult both finish executing I HOPE
                //if this for some reason doesn't work, try to do it in set1. i could get the view based on the position in the adapter somehow, then setvis from there
                if (allData.get(focusPosition).getUri() != null) {
                }
            }
        }
    }
*/ //ADD IMAGE LISTENER ALREADY COMMENTED OUT, THE REST NOT
/*        public void addNewRow(int position) {
            if ((position >= allData.size() - 1)) { //when it's the last position in the dataset, and it's not empty, we add two more infos
                allData.add(new Info(""));
                notifyItemInserted(position + 1);
            }
        }


/*
    //returns index and place of the location storer
    public int getTermIndex(Info term) {
        for (int i = 0; i < locationStore.size(); i++) {
            if (term.equals(locationStore.get(i).getInfo())) {
                return i;
            }
        }
        return -1;
    }
*/ /*
  //if i care about the fractions
    //position = the position of the current term in adapter
    //returns whether the item range was updated or not
    public void updateFraction(Info term, int position, Info lastTerm) {
        //i also need an if statement, that if the focus position is less than the size - 2, update fraction is called and it's not "1/1", then i need to update previous terms too. what a pain
        System.out.println("focus position in updated fraction is " + focusPosition);
        ArrayList<Integer> trackPosition = new ArrayList<>();

        if (focusPosition % 2 == 0) {
            int currentTermIndex = getTermIndex(term), previousTermIndex = getTermIndex((lastTerm));
            //i might want to check this in a different way
            if (currentTermIndex == -1) {  //if the current term added is new, then we add it to the end of the arraylist. also need to check if the position used to be something else
                locationStore.add(new LocationStore(term, position));
                currentTermIndex = locationStore.size() - 1;
            } else {
                if (previousTermIndex == -1){
                    locationStore.get(currentTermIndex).getIndex().add(position);
                }
                else {
                    if (!term.equals(lastTerm)) { //if the term already exists but is different from its original term, remove the previous and update the new term
                        int pos = -1;
                        boolean notFound = true;
                        ArrayList<Integer> previousTerm = locationStore.get(previousTermIndex).getIndex();
                        for (int i = 0; i < previousTerm.size() && notFound; i++) {
                            if (previousTerm.get(i) == position) {
                                pos = i;
                                notFound = false;
                            }
                        }
                        locationStore.get(previousTermIndex).getIndex().remove(pos);
                        locationStore.get(currentTermIndex).getIndex().add(position);
                    }
                }
                //if the term already exists and is the same from its original, do nothing

            }
            //if lastTerm is the same as the current term, do nothing.
            //if lastterm does not exist, just add it
            //if lastterm is different, then delete the previous

            //adds the location to our arraylist
            for (int i = 0; i < allData.size(); i += 2) {
                System.out.println("print all text " + allData.get(i).getText());
                if (term.equals(allData.get(i))) {
                    trackPosition.add(i);
                }
            }
            //sets fraction

            ArrayList<Integer> currentLocation = locationStore.get(currentTermIndex).getIndex();  //seems like there's some problem with how this is being loaded up?
            //gotta set the fraction of all the current words
            for (int i = 0; i < currentLocation.size(); i++){
                System.out.println("fraction being updated. i = " + i);
                allData.get(currentLocation.get(i)).setFraction(i + 1, currentLocation.size());
                allData.get(currentLocation.get(i) + 1).setFraction(i + 1, currentLocation.size());
                notifyItemRangeChanged(currentLocation.get(i), 2, allData.get(currentLocation.get(i)).getFraction());

            }

            if ((previousTermIndex = getTermIndex(lastTerm)) == -1){

            }
            else{
                ArrayList<Integer> previousLocation = locationStore.get(previousTermIndex).getIndex();
                for (int i = 0; i < previousLocation.size(); i++){
                    Info previousTerm = allData.get(previousLocation.get(i));
                    if (previousLocation.get(i) > position) {
                        previousTerm.setNumerator(previousTerm.getNumerator() - 1);
                        allData.get(previousLocation.get(i) + 1).setNumerator(previousTerm.getNumerator());
                    }
                    previousTerm.setDenominator(previousTerm.getDenominator() - 1);
                    allData.get(previousLocation.get(i) + 1).setDenominator(previousTerm.getDenominator());
                    notifyItemRangeChanged(previousLocation.get(i), 2, allData.get(previousLocation.get(i)).getFraction());
                }
            }
            */
              /*  if (oldTracker == null)
                    oldTracker = trackPosition;
            System.out.println("oldtracker size:" + oldTracker.size());
            System.out.println("current size" + tracker); */
        /*    for (Integer i : trackPosition)
                notifyItemRangeChanged(i, 2, (allData.get(i).getFraction())); // this is the issue apparently. maybe i can look into payloads so only the fraction is adjusted
            //  System.out.println("how many identical term" + trackPosition.size());
            //  System.out.println("position is " + position);
                if (oldTracker.size() >= tracker) {  //the issue is oldTracker corrsponds to the previous term, not the current word, since it's updated every time this method is called
                    if (!allData.get(position).getText().equals(allData.get(oldTracker.get(tracker - 1)).getText()))   //if the current text string is different from the text string in the past
                        //above if statement very possibly doesn't work. need to think about oldtracker a bit more
                        for (int i = 0; i < oldTracker.size(); i++) {
                            if (i != tracker) {
                                Info current = allData.get(oldTracker.get(i));
                                Info currentDef = allData.get(oldTracker.get(i + 1));
                                current.setDenominator(current.getDenominator() - 1);
                                currentDef.setDenominator(current.getDenominator());
                                if (i > tracker) {
                                    current.setNumerator(current.getNumerator() - 1);
                                    currentDef.setNumerator(current.getNumerator());
                                }
                                notifyItemRangeChanged(oldTracker.get(i), 2, allData.get(oldTracker.get(i)).getFraction());
                            }
                        }
                }
            oldTracker = trackPosition; */
   // }
//}
    //}
//}
//}








